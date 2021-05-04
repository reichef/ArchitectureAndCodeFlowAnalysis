package edu.kit.kastel.sdq.pcmjoanaflowanalysis.datastructure.hierarchical;

import org.palladiosimulator.pcm.core.composition.AssemblyConnector;
import org.palladiosimulator.pcm.core.composition.AssemblyContext;
import org.palladiosimulator.pcm.repository.OperationProvidedRole;
import org.palladiosimulator.pcm.repository.OperationRequiredRole;

public class AssemblyConnectorRepresentation {
	private AssemblyComponentContext providing;
	private AssemblyComponentContext requiring;
	private AssemblyConnector assemblyConnector;

	
	public AssemblyConnectorRepresentation(AssemblyConnector connector, AssemblyComponentContext requiring,AssemblyComponentContext providing) {
		this.assemblyConnector = connector;
		this.providing = providing;
		this.requiring = requiring;
	
	}
	
	public AssemblyConnector getConnector() {
		return assemblyConnector;
	}
	
	public OperationRequiredRole getRequiredRole(){
		return assemblyConnector.getRequiredRole_AssemblyConnector();
	}
	
	public OperationProvidedRole getProvidedRole() {
		return assemblyConnector.getProvidedRole_AssemblyConnector();
	}
	
	public AssemblyContext getProvidingContext() {
		return assemblyConnector.getProvidingAssemblyContext_AssemblyConnector();
	}
	
	public AssemblyContext getRequiringContext() {
		return assemblyConnector.getRequiringAssemblyContext_AssemblyConnector();
	}
	
	public String getId() {
		return assemblyConnector.getId();
	}
	
	public String getName() {
		return assemblyConnector.getEntityName();
	}
	
	public AssemblyComponentContext getRequring() {
		return requiring;
	}
	
	public AssemblyComponentContext getProviding() {
		return providing;
	}
	
	public boolean fitting(AssemblyComponentContext providing, AssemblyComponentContext requiring) {
		return this.providing.getId().equals(providing.getId()) && this.requiring.getId().equals(requiring.getId());
	}
	
	public String connectorRepresentation() {
		return String.format(" %s.%s -( -> O- %s.%s", requiring.getName(), assemblyConnector.getRequiredRole_AssemblyConnector().getRequiredInterface__OperationRequiredRole().getEntityName(), providing.getName(), assemblyConnector.getProvidedRole_AssemblyConnector().getProvidedInterface__OperationProvidedRole().getEntityName());
	}

}
