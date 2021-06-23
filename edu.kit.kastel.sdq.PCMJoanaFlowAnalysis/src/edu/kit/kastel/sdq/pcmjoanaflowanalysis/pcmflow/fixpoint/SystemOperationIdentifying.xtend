package edu.kit.kastel.sdq.pcmjoanaflowanalysis.pcmflow.fixpoint


import edu.kit.kastel.sdq.pcmjoanaflowanalysis.datastructure.hierarchical.AssemblyComponentContext
import org.palladiosimulator.pcm.repository.OperationSignature
import org.palladiosimulator.pcm.repository.Role
import org.eclipse.xtend.lib.annotations.Data
import edu.kit.kastel.sdq.pcmjoanaflowanalysis.pcmflow.SignatureIdentifyingRoleElement

@Data class SystemOperationIdentifying {
		AssemblyComponentContext context;
		Role role;
		OperationSignature signature;
		
		def SignatureIdentifyingRoleElement toSignatureIdentifying(){
			return new SignatureIdentifyingRoleElement(context.component.component, role, signature); 
		}
}