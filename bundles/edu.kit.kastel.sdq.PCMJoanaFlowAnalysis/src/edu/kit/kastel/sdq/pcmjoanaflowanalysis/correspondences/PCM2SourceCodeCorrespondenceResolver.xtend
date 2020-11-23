package edu.kit.kastel.sdq.pcmjoanaflowanalysis.correspondences

import edu.kit.ipd.sdq.composition.securityanalyses.coupling.correspondences.PCM2SourceCode.Component2Class
import org.palladiosimulator.pcm.repository.RepositoryComponent
import edu.kit.ipd.sdq.composition.securityanalyses.coupling.correspondences.PCM2SourceCode.ElementCorrespondences
import edu.kit.ipd.sdq.composition.securityanalyses.coupling.correspondences.PCM2SourceCode.CompositeDataType2Class
import edu.kit.ipd.sdq.composition.securityanalyses.coupling.correspondences.PCM2SourceCode.OperationSignature2Method
import org.palladiosimulator.pcm.repository.OperationSignature
import edu.kit.ipd.sdq.composition.securityanalyses.coupling.correspondences.PCM2SourceCode.Interface2Interface
import org.palladiosimulator.pcm.repository.OperationInterface
import edu.kit.ipd.sdq.composition.securityanalyses.coupling.correspondences.PCM2SourceCode.Parameter2Parameter
import org.palladiosimulator.pcm.repository.Parameter
import org.palladiosimulator.pcm.repository.CompositeDataType
import edu.kit.ipd.sdq.composition.securityanalyses.coupling.structure.SourceCode.Interface
import edu.kit.ipd.sdq.composition.securityanalyses.coupling.structure.SourceCode.Method

class PCM2SourceCodeCorrespondenceResolver {
	
	ElementCorrespondences correspondences;
	
	new(ElementCorrespondences correspondences){
		this.correspondences = correspondences;
	}
	
	def Component2Class lookupCorrespondence(
		RepositoryComponent component) {
		for (correspondence : correspondences.toclasscorrespondence) {
			if (correspondence instanceof Component2Class) {
				if ((correspondence as Component2Class).component.id.equals(component.id)) {
					return correspondence;
				}
			}
		}
	}
	
	def CompositeDataType2Class lookupCorrespondence(CompositeDataType dt){
		for (correspondence : correspondences.toclasscorrespondence) {
			if (correspondence instanceof CompositeDataType2Class) {
				if ((correspondence as CompositeDataType2Class).compositeDataType.id.equals(dt.id)) {
					return correspondence;
				}
			}
		}
	}
	
	def OperationSignature2Method lookupCorrespondence(OperationSignature opSig) {
		for (correspondence : correspondences.operationsignature2method) {
			if (opSig.id.equals(correspondence.operationSignature.id)) {
				return correspondence;
			}
		}
	}

	def Interface2Interface lookupCorrespondence(OperationInterface opInt) {
		for (correspondence : correspondences.interface2interface) {
			if (correspondence.operationInterface.id.equals(opInt.id)) {
				return correspondence;
			}
		}
	}

	def Parameter2Parameter lookupCorrespondence(OperationSignature opSig, Parameter parameter) {
		lookupCorrespondence(opSig, parameter.parameterName);
	}

	def Parameter2Parameter lookupCorrespondence(OperationSignature opSig, String parameterName) {
		for (correspondence : correspondences.parameter2parameter) {
			if (correspondence.pcmParameter.parameterName.equals(parameterName) &&
				correspondence.pcmOperationSignature4Parameter.id.equals(opSig.id)) {
				return correspondence
			}
		}
	}
	
	def OperationSignature2Method lookupMethodCorrespondenceById(String id){
		for (correspondence : correspondences.operationsignature2method){
			if(correspondence.method.id.equals(id)){
				return correspondence;
			}
		}
	}
	
	def OperationSignature2Method lookupCorrespondence(Method method){
		for (correspondence : correspondences.operationsignature2method){
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