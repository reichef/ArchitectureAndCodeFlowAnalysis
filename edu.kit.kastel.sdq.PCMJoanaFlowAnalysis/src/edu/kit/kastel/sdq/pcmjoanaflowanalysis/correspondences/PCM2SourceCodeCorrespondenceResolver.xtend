package edu.kit.kastel.sdq.pcmjoanaflowanalysis.correspondences

import org.palladiosimulator.pcm.repository.RepositoryComponent
import org.palladiosimulator.pcm.repository.OperationSignature
import org.palladiosimulator.pcm.repository.OperationInterface
import org.palladiosimulator.pcm.repository.Parameter
import org.palladiosimulator.pcm.repository.CompositeDataType
import edu.kit.ipd.sdq.composition.securityanalyses.coupling.structure.SourceCode.Interface
import edu.kit.ipd.sdq.composition.securityanalyses.coupling.structure.SourceCode.Method
import edu.kit.ipd.sdq.composition.securityanalyses.coupling.correspondences.PCM2SourceCode.PCM2SourceCodeCorrespondenceRepository
import edu.kit.ipd.sdq.composition.securityanalyses.coupling.correspondences.PCM2SourceCode.ComponentToClass
import edu.kit.ipd.sdq.composition.securityanalyses.coupling.correspondences.PCM2SourceCode.CompositeDataTypeToClass
import edu.kit.ipd.sdq.composition.securityanalyses.coupling.correspondences.PCM2SourceCode.OperationSignatureToMethod
import edu.kit.ipd.sdq.composition.securityanalyses.coupling.correspondences.PCM2SourceCode.InterfaceToInterface
import edu.kit.ipd.sdq.composition.securityanalyses.coupling.correspondences.PCM2SourceCode.ParameterToParameter

class PCM2SourceCodeCorrespondenceResolver {
	
	PCM2SourceCodeCorrespondenceRepository correspondences;
	
	new(PCM2SourceCodeCorrespondenceRepository correspondences){
		this.correspondences = correspondences;
	}
	
	def ComponentToClass lookupCorrespondence(
		RepositoryComponent component) {
		for (correspondence : correspondences.toclasscorrespondence) {
			if (correspondence instanceof ComponentToClass) {
				if ((correspondence as ComponentToClass).component.id.equals(component.id)) {
					return correspondence;
				}
			}
		}
	}
	
	def CompositeDataTypeToClass lookupCorrespondence(CompositeDataType dt){
		for (correspondence : correspondences.toclasscorrespondence) {
			if (correspondence instanceof CompositeDataTypeToClass) {
				if ((correspondence as CompositeDataTypeToClass).compositeDataType.id.equals(dt.id)) {
					return correspondence;
				}
			}
		}
	}
	
	def OperationSignatureToMethod lookupCorrespondence(OperationSignature opSig) {
		for (correspondence : correspondences.operationsignaturetomethod) {
			if (opSig.id.equals(correspondence.operationSignature.id)) {
				return correspondence;
			}
		}
	}

	def InterfaceToInterface lookupCorrespondence(OperationInterface opInt) {
		for (correspondence : correspondences.interfacetointerface) {
			if (correspondence.operationInterface.id.equals(opInt.id)) {
				return correspondence;
			}
		}
	}

	def ParameterToParameter lookupCorrespondence(OperationSignature opSig, Parameter parameter) {
		lookupCorrespondence(opSig, parameter.parameterName);
	}

	def ParameterToParameter lookupCorrespondence(OperationSignature opSig, String parameterName) {
		for (correspondence : correspondences.parametertoparameter) {
			if (correspondence.pcmParameter.parameterName.equals(parameterName) &&
				correspondence.pcmOperationSignature4Parameter.id.equals(opSig.id)) {
				return correspondence
			}
		}
	}
	
	def OperationSignatureToMethod lookupMethodCorrespondenceById(String id){
		for (correspondence : correspondences.operationsignaturetomethod){
			if(correspondence.method.id.equals(id)){
				return correspondence;
			}
		}
	}
	
	def OperationSignatureToMethod lookupCorrespondence(Method method){
		for (correspondence : correspondences.operationsignaturetomethod){
			if(correspondence.method.id.equals(method.id)){
				return correspondence;
			}
		}
	}
	
	def getClass(RepositoryComponent component){
		return lookupCorrespondence(component).class_;
	}
	
	def Interface getInterface(OperationInterface opInt){
		return lookupCorrespondence(opInt).interface
	}
	
	def Method getMethod(OperationSignature operationSignature){
		return lookupCorrespondence(operationSignature).method
	}
	
	def Method getMethodById(String id){
		return lookupMethodCorrespondenceById(id).method;
	}
	
	def OperationSignature getOperationSignature(Method method){
		return lookupCorrespondence(method).operationSignature;		
	}
}