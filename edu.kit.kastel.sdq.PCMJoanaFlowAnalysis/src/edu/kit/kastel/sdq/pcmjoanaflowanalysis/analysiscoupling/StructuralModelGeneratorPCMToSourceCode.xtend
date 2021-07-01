package edu.kit.kastel.sdq.pcmjoanaflowanalysis.analysiscoupling

import org.palladiosimulator.pcm.repository.Repository
import org.palladiosimulator.pcm.repository.OperationInterface
import org.palladiosimulator.pcm.repository.DataType
import org.palladiosimulator.pcm.repository.PrimitiveTypeEnum
import org.palladiosimulator.pcm.repository.PrimitiveDataType
import org.palladiosimulator.pcm.repository.CompositeDataType
import org.palladiosimulator.pcm.repository.CollectionDataType
import org.eclipse.emf.ecore.util.EcoreUtil
import org.palladiosimulator.pcm.repository.OperationSignature
import org.palladiosimulator.pcm.repository.RepositoryComponent
import org.eclipse.xtend.lib.annotations.Accessors
import edu.kit.kastel.sdq.cosa.structure.SourceCode.SourceCodeRoot
import edu.kit.kastel.sdq.cosa.structure.SourceCode.SourceCodeFactory
import edu.kit.kastel.sdq.cosa.structure.SourceCode.Class;
import edu.kit.kastel.sdq.cosa.correspondences.PCMtoSourceCode.CorrespondenceRepository
import edu.kit.kastel.sdq.cosa.structure.SourceCode.BuiltInTypes
import edu.kit.kastel.sdq.cosa.structure.SourceCode.TopLevelType
import edu.kit.kastel.sdq.cosa.structure.SourceCode.Parameter
import edu.kit.kastel.sdq.cosa.correspondences.PCMtoSourceCode.InterfaceToInterface
import edu.kit.kastel.sdq.cosa.correspondences.PCMtoSourceCode.PCMtoSourceCodeFactory
import edu.kit.kastel.sdq.cosa.correspondences.PCMtoSourceCode.ComponentToClass
import edu.kit.kastel.sdq.cosa.structure.SourceCode.Method
import edu.kit.kastel.sdq.cosa.structure.SourceCode.Type
import edu.kit.kastel.sdq.cosa.structure.SourceCode.Interface
import edu.kit.kastel.sdq.cosa.structure.SourceCode.Package
import edu.kit.kastel.sdq.pcmjoanaflowanalysis.pcmutil.PCMSubtypeResolver

class StructuralModelGeneratorPCMToSourceCode {
	@Accessors(PUBLIC_GETTER) SourceCodeRoot sourceCodeModel;
	private Repository workingRepository;
	private String workingPackageId = null;
	@Accessors(PUBLIC_GETTER) CorrespondenceRepository correspondences;

	enum ElementType {
		INTERFACE,
		CLASS,
		DATA_TYPE
	}

	def processPCMRepo(Repository repo) {

		this.workingRepository = repo;

		if (sourceCodeModel === null) {
			sourceCodeModel = SourceCodeFactory.eINSTANCE.createSourceCodeRoot;

			workingRepository = repo;
			sourceCodeModel = SourceCodeFactory.eINSTANCE.createSourceCodeRoot();
			sourceCodeModel.entityName = repo.getEntityName() + "_SourceCode";
			sourceCodeModel.id = EcoreUtil.generateUUID;
			correspondences = PCMtoSourceCodeFactory.eINSTANCE.createCorrespondenceRepository
			correspondences.entityName = repo.getEntityName() + "_PCM2SourceCodeCorrespondences"
			correspondences.id = EcoreUtil.generateUUID;
			repo.processComponentsAndInterfaces;
		}
	}

	// Several Ideas to determine Package
	// 1. Usage the ecore annotation model an search for "package" type annotation.
	// 2. Each component itself should be a selfdeployable entity, such it would be useful to only deliver for each component its own package
	// however, this approach would result in a widespread package base (TODO: Discuss if useful) 
	// 3. For one Repository, a single package exists 
	private def Package determinePackage() {
		// TODO Currently going way: one package for whole repsotiory.
		if (sourceCodeModel.package.size() == 1) {
			for (packg : sourceCodeModel.package) {
				if (packg.id.equals(workingPackageId)) {
					return packg;
				}
			}
		} else {
			var pack = SourceCodeFactory.eINSTANCE.createPackage;
			pack.entityName = workingRepository.entityName;
			pack.id = EcoreUtil.generateUUID();
			workingPackageId = pack.id;
			sourceCodeModel.package.add(pack);

			return pack;
		}

	}

	private def processComponentsAndInterfaces(Repository repo) {
		// Precreating interfaces and classes is necessary due to possible ReferenceDataTypes that reference a class or interface
		// Without precreation, the interface or class could not be retrieved even when its defined in the PCM-Model
		precreateInterfacesAndClasses(repo);
		addContentToInterfacesAndClasses();
	}

	private def precreateInterfacesAndClasses(Repository repo) {
		for (opInt : PCMSubtypeResolver.filterOperationInterfaces(repo.interfaces__Repository)) {
			var interf = SourceCodeFactory.eINSTANCE.createInterface;
			interf.entityName = opInt.entityName;
			interf.id = EcoreUtil.generateUUID;
			addInterfaceToInterfaceCorrespondence(opInt, interf);
			var pckg = determinePackage();
			pckg.topleveltype.add(interf);
			interf.package = pckg;
		}

		// generate for each basic component its own "facade" class
		// ignore composite components
		for (component : PCMSubtypeResolver.filterBasicComponents(repo.components__Repository)) {

			var scClass = SourceCodeFactory.eINSTANCE.createClass;
			scClass.entityName = component.entityName;
			scClass.id = EcoreUtil.generateUUID;
			addComponent2ClassCorrespondence(component, scClass);

			var pckg = determinePackage();
			pckg.topleveltype.add(scClass);
			scClass.package = pckg;

		}

		for (dataType : PCMSubtypeResolver.filterCompositeDataTypes(repo.dataTypes__Repository)) {

			// TODO Implement case of composite datatype (as DTClass) [priority: minor]
			// currently only class without instance variables. Could improved by use every datatype and append 
			// find inner datatype instances
			var scClass = SourceCodeFactory.eINSTANCE.createClass;
			scClass.entityName = dataType.entityName;
			scClass.id = EcoreUtil.generateUUID;

			var pckg = determinePackage();
			pckg.topleveltype.add(scClass);
			scClass.package = pckg;

		}
	}

	private def addContentToInterfacesAndClasses() {
		for (interfaceCorrespondence : correspondences.interfacetointerface) {
			processOperationInterfaceAndAddToInterface(interfaceCorrespondence.operationInterface,
				interfaceCorrespondence.scInterface);
		}

		for (componentClassCorrespondence : correspondences.toclass.filter(ComponentToClass)) {

			processClassContent(componentClassCorrespondence.component, componentClassCorrespondence.class_);
		}
	}

	private def processClassContent(RepositoryComponent component, Class scClass) {
		addImplementedInterfacesToClassByProvidedRoles(component, scClass);
		addInterfaceFieldsToClassByRequiredRoles(component, scClass);
	}

	private def addImplementedInterfacesToClassByProvidedRoles(RepositoryComponent component, Class scClass) {
		for (providedRole : PCMSubtypeResolver.filterOperationProvidedRoles(
			component.providedRoles_InterfaceProvidingEntity)) {

			var i2i = providedRole.providedInterface__OperationProvidedRole.lookUpInterfaceCorrespondence;
			scClass.implements.add(i2i.scInterface)
		}
	}

	private def addInterfaceFieldsToClassByRequiredRoles(RepositoryComponent component, Class scClass) {
		for (requiredRole : PCMSubtypeResolver.filterOperationRequiredRoles(
			component.requiredRoles_InterfaceRequiringEntity)) {

			var i2i = requiredRole.requiredInterface__OperationRequiredRole.lookUpInterfaceCorrespondence;
			var variable = SourceCodeFactory.eINSTANCE.createVariable;
			variable.entityName = i2i.scInterface.entityName.toFirstLower;
			variable.id = EcoreUtil.generateUUID;

			var referenceType = SourceCodeFactory.eINSTANCE.createReferenceType;
			referenceType.topleveltype = i2i.scInterface;

			variable.type = referenceType;

			scClass.field.add(variable);

		}
	}

	private def processOperationInterfaceAndAddToInterface(OperationInterface opInt, Interface interf) {
		for (signature : opInt.signatures__OperationInterface) {
			var op = generateMethodFromOperationSignature(signature)
			interf.operation.add(op);
		}
	}

	private def Method generateMethodFromOperationSignature(OperationSignature signature) {
		val op = SourceCodeFactory.eINSTANCE.createMethod;
		op.entityName = signature.entityName;
		op.id = EcoreUtil.generateUUID;

		addOperationSignature2MethodCorrespondence(signature, op);

		for (parameter : signature.parameters__OperationSignature) {
			var scParam = SourceCodeFactory.eINSTANCE.createParameter;
			scParam.entityName = parameter.parameterName;
			scParam.type = parameter.dataType__Parameter.createSourceCodeModelDataTypeFromPalladioDataType
			op.parameter.add(scParam);
			addParameterCorrespondence(parameter, scParam);
		}

		if (signature.returnType__OperationSignature !== null) {
			op.returnType = signature.returnType__OperationSignature.createSourceCodeModelDataTypeFromPalladioDataType
		}

		return op;
	}

	private def BuiltInTypes translatePalladio2SourceCodeModelBuiltInTypes(PrimitiveDataType primitiveDataType) {
		switch primitiveDataType.type.toString {
			case PrimitiveTypeEnum.BOOL.toString: return BuiltInTypes.BOOLEAN
			case PrimitiveTypeEnum.BYTE.toString: return BuiltInTypes.BYTE
			case PrimitiveTypeEnum.CHAR.toString: return BuiltInTypes.CHAR
			case PrimitiveTypeEnum.DOUBLE.toString: return BuiltInTypes.DOUBLE
			case PrimitiveTypeEnum.INT.toString: return BuiltInTypes.INT
			case PrimitiveTypeEnum.LONG.toString: return BuiltInTypes.LONG
			case PrimitiveTypeEnum.STRING.toString: return BuiltInTypes.STRING
			default: return null
		}
	}

	private def TopLevelType findTopLevelTypesInPackagesByForCompositeDataType(CompositeDataType dataType) {

		for (package : sourceCodeModel.package) {
			for (topLevelType : package.topleveltype) {
				if (topLevelType.entityName.equals((dataType as CompositeDataType).entityName)) {
					return topLevelType;
				}
			}
		}
	}

	private def Type createSourceCodeModelDataTypeFromPalladioDataType(DataType dataType) {
		if (dataType instanceof PrimitiveDataType) {
			var builtInType = SourceCodeFactory.eINSTANCE.createBuiltInType();
			builtInType.builtInType = (dataType as PrimitiveDataType).translatePalladio2SourceCodeModelBuiltInTypes;
			return builtInType;
		} else if (dataType instanceof CompositeDataType) {
			var referenceType = SourceCodeFactory.eINSTANCE.createReferenceType();
			referenceType.topleveltype = (dataType as CompositeDataType).
				findTopLevelTypesInPackagesByForCompositeDataType
			return referenceType;
		} else if (dataType instanceof CollectionDataType) {
			var collectionType = SourceCodeFactory.eINSTANCE.createCollectionType();
			collectionType.type = (dataType as CollectionDataType).innerType_CollectionDataType.
				createSourceCodeModelDataTypeFromPalladioDataType;
			return collectionType;
		}

		return null;
	}

	private def addOperationSignature2MethodCorrespondence(OperationSignature opSig, Method method) {
		var o2m = PCMtoSourceCodeFactory.eINSTANCE.createOperationSignatureToMethod;
		o2m.operationSignature = opSig;
		o2m.method = method;

		correspondences.operationsignaturetomethod.add(o2m);
	}

	private def addInterfaceToInterfaceCorrespondence(OperationInterface opInt, Interface interf) {
		var i2i = PCMtoSourceCodeFactory.eINSTANCE.createInterfaceToInterface;
		i2i.operationInterface = opInt;
		i2i.scInterface = interf;

		correspondences.interfacetointerface.add(i2i);
	}

	private def addParameterCorrespondence(org.palladiosimulator.pcm.repository.Parameter palladioParam,
		Parameter sourceCodeParam) {
		var p2p = PCMtoSourceCodeFactory.eINSTANCE.createParameterToParameter;
		p2p.pcmParameter = palladioParam;
		p2p.scParameter = sourceCodeParam;
		correspondences.parametertoparameter.add(p2p);
	}

	private def addComponent2ClassCorrespondence(RepositoryComponent component, Class scClass) {
		var c2c = PCMtoSourceCodeFactory.eINSTANCE.createComponentToClass;
		c2c.component = component;
		c2c.class = scClass;
		correspondences.toclass.add(c2c);
	}

	private def InterfaceToInterface lookUpInterfaceCorrespondence(OperationInterface opInt) {
		for (i2i : correspondences.interfacetointerface) {
			if (i2i.operationInterface.id.equals(opInt.id)) {
				return i2i;
			}
		}
	}

}
