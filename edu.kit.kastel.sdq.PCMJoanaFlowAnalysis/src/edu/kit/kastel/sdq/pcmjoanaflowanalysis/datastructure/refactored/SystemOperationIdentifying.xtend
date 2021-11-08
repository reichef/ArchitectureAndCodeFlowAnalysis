package edu.kit.kastel.sdq.pcmjoanaflowanalysis.datastructure.refactored


import edu.kit.kastel.sdq.pcmjoanaflowanalysis.datastructure.hierarchical.AssemblyComponentContext
import org.palladiosimulator.pcm.repository.OperationSignature
import org.eclipse.xtend.lib.annotations.Data
import org.palladiosimulator.pcm.repository.OperationInterface

@Data class SystemOperationIdentifying {
		AssemblyComponentContext context;
		OperationInterface opInterface;
		OperationSignature signature;
		
	
	def boolean identyfyingEquals(AssemblyComponentContext context, OperationInterface opInt, OperationSignature signature) {
		return this.context.id.equals(context.id) && this.opInterface.id.equals(opInt.id) &&this.signature.getId().equals(signature.getId());
	}
	
	def boolean identyfyingEquals(SystemOperationIdentifying signatureIdentifying) {
		return identyfyingEquals(signatureIdentifying.context, signatureIdentifying.opInterface, signatureIdentifying.getSignature());
	}
	
	
	def boolean identyfyingEqualsWithoutSignature(SystemOperationIdentifying signatureIdentifying){
		return identyfiyingEqualsWithoutSignature(signatureIdentifying.context, signatureIdentifying.opInterface);
	}
	
	def boolean identyfiyingEqualsWithoutSignature(AssemblyComponentContext context, OperationInterface opInt){
		return this.context.id.equals(context.id) && this.opInterface.id.equals(opInt.id)
	}
}
