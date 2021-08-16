package edu.kit.kastel.sdq.pcmjoanaflowanalysis.datastructure.visitors;

import org.palladiosimulator.pcm.core.composition.AssemblyConnector;
import org.palladiosimulator.pcm.core.composition.AssemblyContext;
import org.palladiosimulator.pcm.core.composition.DelegationConnector;
import org.palladiosimulator.pcm.core.composition.ProvidedDelegationConnector;
import org.palladiosimulator.pcm.core.composition.RequiredDelegationConnector;
import org.palladiosimulator.pcm.core.composition.util.CompositionSwitch;

public class CompositionVisitor extends CompositionSwitch<Object> {
	
	public CompositionVisitor() {
		
	}

	@Override
	public Object caseDelegationConnector(DelegationConnector object) {
		// TODO Auto-generated method stub
		return super.caseDelegationConnector(object);
	}

	@Override
	public Object caseProvidedDelegationConnector(ProvidedDelegationConnector object) {
		// TODO Auto-generated method stub
		return super.caseProvidedDelegationConnector(object);
	}

	@Override
	public Object caseRequiredDelegationConnector(RequiredDelegationConnector object) {
		// TODO Auto-generated method stub
		return super.caseRequiredDelegationConnector(object);
	}

	@Override
	public Object caseAssemblyConnector(AssemblyConnector object) {
		// TODO Auto-generated method stub
		return super.caseAssemblyConnector(object);
	}

	@Override
	public Object caseAssemblyContext(AssemblyContext object) {
		// TODO Auto-generated method stub
		return super.caseAssemblyContext(object);
	}

}
