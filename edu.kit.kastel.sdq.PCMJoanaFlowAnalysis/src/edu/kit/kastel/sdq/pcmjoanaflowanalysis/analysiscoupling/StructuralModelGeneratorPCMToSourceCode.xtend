package edu.kit.kastel.sdq.pcmjoanaflowanalysis.analysiscoupling

import edu.kit.ipd.sdq.composition.securityanalyses.coupling.structure.SourceCode.SourceCodeRoot
import edu.kit.ipd.sdq.composition.securityanalyses.coupling.structure.SourceCode.Package
import org.palladiosimulator.pcm.repository.Repository
import edu.kit.ipd.sdq.composition.securityanalyses.coupling.structure.SourceCode.SourceCodeFactory
import org.palladiosimulator.pcm.repository.OperationInterface
import edu.kit.ipd.sdq.composition.securityanalyses.coupling.structure.SourceCode.BuiltInTypes
import org.palladiosimulator.pcm.repository.DataType
import org.palladiosimulator.pcm.repository.PrimitiveTypeEnum
import org.palladiosimulator.pcm.repository.PrimitiveDataType
import edu.kit.ipd.sdq.composition.securityanalyses.coupling.structure.SourceCode.Type
import org.palladiosimulator.pcm.repository.CompositeDataType
import edu.kit.ipd.sdq.composition.securityanalyses.coupling.structure.SourceCode.TopLevelType
import org.palladiosimulator.pcm.repository.CollectionDataType
import edu.kit.ipd.sdq.composition.securityanalyses.coupling.correspondences.PCM2SourceCode.ElementCorrespondences
import edu.kit.ipd.sdq.composition.securityanalyses.coupling.correspondences.PCM2SourceCode.PCM2SourceCodeFactory
import org.eclipse.emf.ecore.util.EcoreUtil
import org.palladiosimulator.pcm.repository.OperationSignature
import edu.kit.ipd.sdq.composition.securityanalyses.coupling.structure.SourceCode.Method
import edu.kit.ipd.sdq.composition.securityanalyses.coupling.structure.SourceCode.Interface
import edu.kit.ipd.sdq.composition.securityanalyses.coupling.structure.SourceCode.Parameter
import edu.kit.ipd.sdq.composition.securityanalyses.coupling.structure.SourceCode.Class
import org.palladiosimulator.pcm.repository.RepositoryComponent
import edu.kit.ipd.sdq.composition.securityanalyses.coupling.correspondences.PCM2SourceCode.Interface2Interface
import org.palladiosimulator.pcm.repository.OperationProvidedRole
import org.palladiosimulator.pcm.repository.OperationRequiredRole
import edu.kit.ipd.sdq.composition.securityanalyses.coupling.correspondences.PCM2SourceCode.Component2Class
import org.eclipse.xtend.lib.annotations.Accessors
import org.palladiosimulator.pcm.repository.BasicComponent

class StructuralModelGeneratorPCMToSourceCode {
	@Accessors(PUBLIC_GETTER) SourceCodeRoot sourceCodeModel;
	private Repository workingRepository;
	private String workingPackageId = null;
	@Accessors(PUBLIC_GETTER) ElementCorrespondences correspondences;

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
			sourceCodeModel.name = repo.getEntityName() + "_SourceCode";
			sourceCodeModel.id = EcoreUtil.generateUUID;
			correspondences = PCM2SourceCodeFactory.eINSTANCE.createElementCorrespondences();
			correspondences.name = repo.getEntityName() + "_PCM2SourceCodeCorrespondences"
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
			pack.name = workingRepository.entityName;
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
		for (opInt : repo.interfaces__Repository) {
			if (opInt instanceof OperationInterface) {

				var interf = SourceCodeFactory.eINSTANCE.createInterface;
				interf.name = opInt.entityName;
				interf.id = EcoreUtil.generateUUID;
				addInterface2InterfaceCorrespondence(opInt, interf);
				var pckg = determinePackage();
				pckg.topleveltype.add(interf);
				interf.package = pckg;
			}
		}

		// generate for each basic component its own "facade" class
		// ignore composite components
		for (component : repo.components__Repository) {
			if (component instanceof BasicComponent) {
				var scClass = SourceCodeFactory.eINSTANCE.createClass;
				scClass.name = component.entityName;
				scClass.id = EcoreUtil.generateUUID;
				addComponent2ClassCorrespondence(component, scClass);

				var pckg = determinePackage();
				pckg.topleveltype.add(scClass);
				scClass.package = pckg;
			}
		}

		for (dataType : repo.dataTypes__Repository) {
			if (dataType instanceof CompositeDataType) {
				// TODO Implement case of composite datatype (as DTClass) [priority: minor]
				// currently only class without instance variables. Could improved by use every datatype and append 
				// find inner datatype instances
				var scClass = SourceCodeFactory.eINSTANCE.createClass;
				scClass.name = dataType.entityName;
				scClass.id = EcoreUtil.generateUUID;

				var pckg = determinePackage();
				pckg.topleveltype.add(scClass);
				scClass.package = pckg;
			}
		}
	}

	private def addContentToInterfacesAndClasses() {
		for (interfaceCorrespondence : correspondences.interface2interface) {
			processOperationInterfaceAndAddToInterface(interfaceCorrespondence.operationInterface,
				interfaceCorrespondence.interface);
		}

		for (componentClassCorrespondence : correspondences.toclasscorrespondence) {
			if (componentClassCorrespondence instanceof Component2Class)
				processClassContent(componentClassCorrespondence.component, componentClassCorrespondence.class_);
		}
	}

	private def processClassContent(RepositoryComponent component, Class scClass) {
		addImplementedInterfacesToClassByProvidedRoles(component, scClass);
		addInterfaceFieldsToClassByRequiredRoles(component, scClass);
	}

	private def addImplementedInterfacesToClassByProvidedRoles(RepositoryComponent component, Class scClass) {
		for (providedRole : component.providedRoles_InterfaceProvidingEntity) {
			if (providedRole instanceof OperationProvidedRole) {
				var i2i = (providedRole as OperationProvidedRole).providedInterface__OperationProvidedRole.
					lookUpInterfaceCorrespondence;
				scClass.implementedInterface.add(i2i.interface);
			}
		}
	}

	private def addInterfaceFieldsToClassByRequiredRoles(RepositoryComponent component, Class scClass) {
		for (requiredRole : component.requiredRoles_InterfaceRequiringEntity) {
			if (requiredRole instanceof OperationRequiredRole) {
				var i2i = (requiredRole as OperationRequiredRole).requiredInterface__OperationRequiredRole.
					lookUpInterfaceCorrespondence;
				var variable = SourceCodeFactory.eINSTANCE.createVariable;
				variable.name = i2i.interface.name.toFirstLower;
				variable.id = EcoreUtil.generateUUID;

				var referenceType = SourceCodeFactory.eINSTANCE.createReferenceType;
				referenceType.topleveltype = i2i.interface;

				variable.type = referenceType;

				scClass.field.add(variable);
			}
		}
	}

	private def Interface2Interface lookUpInterfaceCorrespondence(OperationInterface opInt) {
		for (i2i : correspondences.interface2interface) {
			if (i2i.operationInterface.id.equals(opInt.id)) {
				return i2i;
			}
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
		op.name = signature.entityName;
		op.id = EcoreUtil.generateUUID;

		addOperationSignature2MethodCorrespondence(signature, op);

		for (parameter : signature.parameters__OperationSignature) {
			var scParam = SourceCodeFactory.eINSTANCE.createParameter;
			scParam.name = parameter.parameterName;
			scParam.type = parameter.dataType__Parameter.createSourceCodeModelDataTypeFromPalladioDataType
			op.parameter.add(scParam);
			addParameterCorrespondence(parameter, scParam);
		}

		if (signature.returnType__OperationSignature != null) {
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
				if (topLevelType.name.equals((dataType as CompositeDataType).entityName)) {
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
			collectionType.type = (dataType as CollectionDataType).innerType_CollectionDataType.createSourceCodeModelDataTypeFromPalladioDataType;
			return collectionType;
		}

		return null;
	}

	private def addOperationSignature2MethodCorrespondence(OperationSignature opSig, Method method) {
		var o2m = PCM2SourceCodeFactory.eINSTANCE.createOperationSignature2Method;
		o2m.operationSignature = opSig;
		o2m.method = method;

		correspondences.operationsignature2method.add(o2m);
	}

	private def addInterface2InterfaceCorrespondence(OperationInterface opInt, Interface interf) {
		var i2i = PCM2SourceCodeFactory.eINSTANCE.createInterface2Interface;
		i2i.operationInterface = opInt;
		i2i.interface = interf;

		correspondences.interface2interface.add(i2i);
	}

	private def addParameterCorrespondence(org.palladiosimulator.pcm.repository.Parameter palladioParam,
		Parameter sourceCodeParam) {
		var p2p = PCM2SourceCodeFactory.eINSTANCE.createParameter2Parameter;
		p2p.pcmParameter = palladioParam;
		p2p.sourceCodeParameter = sourceCodeParam;
		correspondences.parameter2parameter.add(p2p);
	}

	private def addComponent2ClassCorrespondence(RepositoryComponent component, Class scClass) {
		var c2c = PCM2SourceCodeFactory.eINSTANCE.createComponent2Class;
		c2c.component = component;
		c2c.class = scClass;
		correspondences.toclasscorrespondence.add(c2c);
	}
}
