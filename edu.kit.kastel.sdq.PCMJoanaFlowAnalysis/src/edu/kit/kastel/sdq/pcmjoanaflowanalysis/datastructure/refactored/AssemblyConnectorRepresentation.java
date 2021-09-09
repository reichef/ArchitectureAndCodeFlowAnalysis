package edu.kit.kastel.sdq.pcmjoanaflowanalysis.datastructure.refactored;

import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;

import org.palladiosimulator.pcm.core.composition.AssemblyConnector;

import edu.kit.kastel.sdq.pcmjoanaflowanalysis.datastructure.FlowGraphEdge;
import edu.kit.kastel.sdq.pcmjoanaflowanalysis.datastructure.visitors.FlowGraphVisitor;
import edu.kit.kastel.sdq.pcmjoanaflowanalysis.pcmflow.IntraComponentFlow;

public class AssemblyConnectorRepresentation implements FlowGraphEdge {
	
	private AssemblyComponentContext providing;
	private AssemblyComponentContext requiring;
	private AssemblyConnector connector;
	private Collection<IntraComponentFlow> interComponentFlows;
	
	public AssemblyConnectorRepresentation(AssemblyComponentContext requiring, AssemblyComponentContext providing, AssemblyConnector connector) {
		this.providing = Objects.requireNonNull(requiring, "Requiring vertex must not be null");
		this.requiring = Objects.requireNonNull(providing, "Providing vertex must not be null");
		this.connector = Objects.requireNonNull(connector, "Connector must not be null");
		this.interComponentFlows = new HashSet<>();
		
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

	@Override
	public Collection<IntraComponentFlow> getInterComponentFlows() {
		return interComponentFlows;
	}
	
	@Override
	public void addInterComponentFlow(IntraComponentFlow flow) {
		interComponentFlows.add(flow);
	}
	
	public AssemblyConnector getConnector() {
		return connector;
	}

}
