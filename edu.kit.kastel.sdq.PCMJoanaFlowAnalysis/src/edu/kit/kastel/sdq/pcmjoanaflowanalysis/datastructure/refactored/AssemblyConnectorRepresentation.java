package edu.kit.kastel.sdq.pcmjoanaflowanalysis.datastructure.refactored;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;

import org.palladiosimulator.pcm.core.composition.AssemblyConnector;

import edu.kit.kastel.sdq.pcmjoanaflowanalysis.datastructure.FlowGraphEdge;
import edu.kit.kastel.sdq.pcmjoanaflowanalysis.datastructure.FlowGraphEdge.Direction;
import edu.kit.kastel.sdq.pcmjoanaflowanalysis.datastructure.visitors.FlowGraphVisitor;
import edu.kit.kastel.sdq.pcmjoanaflowanalysis.pcmflow.fixpoint.SystemOperationIdentifying;


public class AssemblyConnectorRepresentation implements FlowGraphEdge {
	
	private final AssemblyComponentContext providing;
	private final AssemblyComponentContext requiring;
	private final AssemblyConnector connector;
	private Map<SystemOperationIdentifying, SystemOperationIdentifying> flowsForOperations;
	
	public AssemblyConnectorRepresentation(AssemblyComponentContext requiring, AssemblyComponentContext providing, AssemblyConnector connector) {
		this.providing = Objects.requireNonNull(requiring, "Requiring vertex must not be null");
		this.requiring = Objects.requireNonNull(providing, "Providing vertex must not be null");
		this.connector = Objects.requireNonNull(connector, "Connector must not be null");
		this.flowsForOperations = new HashMap<>();
		
		this.providing.addEdge(this);
		this.requiring.addEdge(this);
	}

	@Override
	public void accept(FlowGraphVisitor visitor) {
		Objects.requireNonNull(visitor, "visitor must not be null");
		
		visitor.visit(this);
	}

	@Override
	public AssemblyComponentContext getTail() {
		return requiring;
	}

	@Override
	public AssemblyComponentContext getHead() {
		return providing;
	}



	
	public AssemblyConnector getConnector() {
		return connector;
	}

	@Override
	public Map<SystemOperationIdentifying, SystemOperationIdentifying> getFlows() {
		return flowsForOperations;
	}

	@Override
	public SystemOperationIdentifying getFlow(SystemOperationIdentifying source) {
		return flowsForOperations.get(source);
	}

	@Override
	public void addFlow(SystemOperationIdentifying source, SystemOperationIdentifying sink) {
		flowsForOperations.put(source, sink);
	}

	@Override
	public boolean fitting( AssemblyComponentContext providing, AssemblyComponentContext requiring) {
		return this.providing.getId().equals(providing.getId()) && this.requiring.getId().equals(requiring.getId());
	}


	@Override
	public String getId() {
		return connector.getId();
	}

	@Override
	public String getName() {
		return connector.getEntityName();
	}

	@Override
	public Direction getDirection(
			SystemOperationIdentifying sink) {
		if(requiring.id.equals(sink.getContext().getId())) {
			return Direction.ASSEMBLY;
		} else if(providing.id.equals(sink.getContext().getId())) {
			return Direction.OPPOSITE;
		}
		return null;
	}


}
