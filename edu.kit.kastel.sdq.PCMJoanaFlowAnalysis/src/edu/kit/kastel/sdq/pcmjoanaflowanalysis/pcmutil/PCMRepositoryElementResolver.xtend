package edu.kit.kastel.sdq.pcmjoanaflowanalysis.pcmutil

import org.palladiosimulator.pcm.repository.OperationInterface
import java.util.Optional
import org.palladiosimulator.pcm.repository.OperationSignature
import org.palladiosimulator.pcm.repository.RepositoryComponent
import java.util.Collection
import org.palladiosimulator.pcm.repository.OperationProvidedRole
import org.palladiosimulator.pcm.repository.OperationRequiredRole
import java.util.HashSet

class PCMRepositoryElementResolver {
	
	private new(){}
	
	def static Optional<OperationSignature> getOperationSignatureOfInterfaceById(OperationInterface operationInterface,
			String operationSignatureId) {
				
		var opSig = Optional.empty();
		for (OperationSignature sig : operationInterface.getSignatures__OperationInterface()) {
			if (sig.getId().equals(operationSignatureId)) {
				opSig = Optional.ofNullable(sig);
				return opSig;
			}
		}
		return opSig;
	}
	
	def static Optional<OperationSignature> getOperationSignature(RepositoryComponent component, String operationSignatureId){
		val opSig = getAllOperationSignaturesOfComponent(component).findFirst[signature | signature.id.equals(operationSignatureId)];
		
		return Optional.ofNullable(opSig);
	}

	
	def static Collection<OperationSignature> getAllOperationSignaturesOfComponent(RepositoryComponent component){
		var allOperationSignatures = new HashSet<OperationSignature>;
		
		allOperationSignatures.addAll(getAllProvidedOperationSignaturesOfComponent(component));
		allOperationSignatures.addAll(getAllRequiredOperationSignaturesOfComponent(component));
		
		
		return allOperationSignatures;
	}
		
	def static Collection<OperationSignature> getAllProvidedOperationSignaturesOfComponent(RepositoryComponent component){
		return component.providedRoles_InterfaceProvidingEntity
					.filter(OperationProvidedRole)
					.map[it.providedInterface__OperationProvidedRole]
					.map[it.signatures__OperationInterface]
					.flatten.toList;
	}
	
	def static Collection<OperationSignature> getAllRequiredOperationSignaturesOfComponent(RepositoryComponent component){
		return component.requiredRoles_InterfaceRequiringEntity
					.filter(OperationRequiredRole)
					.map[it.requiredInterface__OperationRequiredRole]
					.map[it.signatures__OperationInterface]
					.flatten.toList;
	}
}