package edu.kit.kastel.sdq.pcmjoanaflowanalysis.pcmutil

import org.palladiosimulator.pcm.repository.OperationInterface
import java.util.Optional
import org.palladiosimulator.pcm.repository.OperationSignature

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
	
	
}