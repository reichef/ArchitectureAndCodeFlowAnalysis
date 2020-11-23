package edu.kit.kastel.sdq.pcmjoanaflowanalysis.pcmflow;

import org.palladiosimulator.pcm.core.composition.AssemblyContext;
import org.palladiosimulator.pcm.repository.OperationProvidedRole;

public class CompositeEntityIntraComponentFlow extends IntraComponentFlow{
	private AssemblyContext context;
	public CompositeEntityIntraComponentFlow(AssemblyContext context, SignatureIdentifyingRoleElement<OperationProvidedRole> source) {
		super(source);
		this.context = context;
	}


	
	public AssemblyContext getContext() {
		return context;
	}

	public void setContext(AssemblyContext context) {
		this.context = context;
	}
	
	
}
