package edu.kit.kastel.sdq.pcmjoanaflowanalysis.correspondences

import org.palladiosimulator.pcm.repository.CompositeDataType
import org.palladiosimulator.pcm.repository.OperationInterface
import org.palladiosimulator.pcm.repository.OperationSignature
import org.palladiosimulator.pcm.repository.Parameter
import org.palladiosimulator.pcm.repository.RepositoryComponent
import edu.kit.kastel.sdq.cosa.correspondences.PCMtoSourceCode.CorrespondenceRepository
import edu.kit.kastel.sdq.cosa.correspondences.PCMtoSourceCode.ComponentToClass
import edu.kit.kastel.sdq.cosa.correspondences.PCMtoSourceCode.CompositeDataTypeToClass
import edu.kit.kastel.sdq.cosa.correspondences.PCMtoSourceCode.OperationSignatureToMethod
import edu.kit.kastel.sdq.cosa.correspondences.PCMtoSourceCode.InterfaceToInterface
import edu.kit.kastel.sdq.cosa.correspondences.PCMtoSourceCode.ParameterToParameter
import edu.kit.kastel.sdq.cosa.structure.SourceCode.Interface
import edu.kit.kastel.sdq.cosa.structure.SourceCode.Method

class PCM2SourceCodeCorrespondenceResolver {

	CorrespondenceRepository correspondences;

	new(CorrespondenceRepository correspondences) {
		this.correspondences = correspondences;
	}

	def ComponentToClass lookupCorrespondence(RepositoryComponent component) {

		return correspondences.toclass
			.filter(ComponentToClass)
			.findFirst [correspondence |correspondence.component.id.equals(component.id)];

	}

	def CompositeDataTypeToClass lookupCorrespondence(CompositeDataType dt) {
		return correspondences.toclass
			.filter(CompositeDataTypeToClass)
			.findFirst [correspondence |correspondence.compositeDataType.id.equals(dt.id)];
	}

	def OperationSignatureToMethod lookupCorrespondence(OperationSignature opSig) {
		
		return correspondences.operationsignaturetomethod
			.findFirst[correspondence | correspondence.operationSignature.id.equals(opSig.id)];
	}

	def InterfaceToInterface lookupCorrespondence(OperationInterface opInt) {
		
		return correspondences.interfacetointerface
			.findFirst[correspondence | correspondence.operationInterface.id.equals(opInt.id)];
			
	}

	def ParameterToParameter lookupCorrespondence(OperationSignature opSig, Parameter parameter) {
		lookupCorrespondence(opSig, parameter.parameterName);
	}

	def ParameterToParameter lookupCorrespondence(OperationSignature opSig, String parameterName) {
		
		return correspondences.parametertoparameter
			.findFirst[correspondence | correspondence.pcmParameter.parameterName.equals(parameterName) 
										&& correspondence.relatedOperationSignature.id.equals(opSig.id)
			];
	}

	def OperationSignatureToMethod lookupMethodCorrespondenceById(String id) {
		
		return correspondences.operationsignaturetomethod
			.findFirst[correspondence | correspondence.method.id.equals(id)];
	}

	def OperationSignatureToMethod lookupCorrespondence(Method method) {
		
		
		return correspondences.operationsignaturetomethod
			.findFirst[correspondence| correspondence.method.id.equals(method.id)];
	}

	def getClass(RepositoryComponent component) {
		return lookupCorrespondence(component).class_;
	}

	def Interface getInterface(OperationInterface opInt) {
		return lookupCorrespondence(opInt).scInterface
	}

	def Method getMethod(OperationSignature operationSignature) {
		return lookupCorrespondence(operationSignature).method
	}

	def Method getMethodById(String id) {
		return lookupMethodCorrespondenceById(id).method;
	}

	def OperationSignature getOperationSignature(Method method) {
		return lookupCorrespondence(method).operationSignature;
	}
}
