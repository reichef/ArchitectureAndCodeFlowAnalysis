package edu.kit.kastel.sdq.pcmjoanaflowanalysis.datastructure.hierarchical;

import org.eclipse.ocl.util.Tuple;
import org.palladiosimulator.pcm.core.composition.AssemblyConnector;
import org.palladiosimulator.pcm.core.composition.AssemblyContext;
import org.palladiosimulator.pcm.repository.OperationProvidedRole;
import org.palladiosimulator.pcm.repository.OperationRequiredRole;

import edu.kit.kastel.sdq.pcmjoanaflowanalysis.pcmflow.fixpoint.SystemOperationIdentifying;
/**TODO: include the methods in the analysis*/
import edu.kit.joana.component.connector.Method;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;


public class AssemblyConnectorRepresentation {
	
	public enum Direction {ASSEMBLY, OPPOSITE};
	
	private AssemblyComponentContext providing;
	private AssemblyComponentContext requiring;
	private AssemblyConnector assemblyConnector;
	private ArrayList<String> methodHistory; /*TODO: include the methods in the analysis*/

	
	public AssemblyConnectorRepresentation(AssemblyConnector connector, AssemblyComponentContext requiring,AssemblyComponentContext providing) {
		this.assemblyConnector = connector;
		this.providing = providing;
		this.requiring = requiring;
		methodHistory = new ArrayList<String>();
	
	}
	
	/*TODO: include the methods in the analysis*/
	public ArrayList<String> getMethodHistory() {
		return methodHistory;
	}
	
	/*TODO: include the methods in the analysis*/
	public void setMethodHistory(ArrayList<String> methodHistory) {
		this.methodHistory = methodHistory;
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
	
	public AssemblyComponentContext getProvidingContext() {
		return providing;
	}
	
	public AssemblyComponentContext getRequiringContext() {
		return requiring;
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
	
	public Direction getDirection(SystemOperationIdentifying sink) {
		if(requiring.id.equals(sink.getContext().id)) {
			return Direction.ASSEMBLY;
		} else if(providing.id.equals(sink.getContext().id)) {
			return Direction.OPPOSITE;
		}
		return null;
	}
	
	public boolean fitting(AssemblyComponentContext providing, AssemblyComponentContext requiring) {
		return this.providing.getId().equals(providing.getId()) && this.requiring.getId().equals(requiring.getId());
	}
	
	public String connectorRepresentation() {
		return String.format(" %s.%s -( -> O- %s.%s", requiring.getName(), assemblyConnector.getRequiredRole_AssemblyConnector().getRequiredInterface__OperationRequiredRole().getEntityName(), providing.getName(), assemblyConnector.getProvidedRole_AssemblyConnector().getProvidedInterface__OperationProvidedRole().getEntityName());
	}

}
