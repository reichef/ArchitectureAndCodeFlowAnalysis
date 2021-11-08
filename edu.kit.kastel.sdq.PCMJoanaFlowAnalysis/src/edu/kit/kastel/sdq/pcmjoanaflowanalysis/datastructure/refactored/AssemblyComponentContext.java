package edu.kit.kastel.sdq.pcmjoanaflowanalysis.datastructure.refactored;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.palladiosimulator.pcm.core.composition.AssemblyContext;

import edu.kit.kastel.sdq.pcmjoanaflowanalysis.datastructure.FlowGraphEdge;
import edu.kit.kastel.sdq.pcmjoanaflowanalysis.datastructure.FlowGraphVertex;
import edu.kit.kastel.sdq.pcmjoanaflowanalysis.datastructure.visitors.FlowGraphVisitor;
import edu.kit.kastel.sdq.pcmjoanaflowanalysis.pcmflow.fixpoint.SystemOperationIdentifying;

public class AssemblyComponentContext extends AssemblyRepresentation implements FlowGraphVertex {

	private final AssemblyContext context;
	private Map<SystemOperationIdentifying, Collection<SystemOperationIdentifying>> intraComponentFlows;
	private final FlowBasicComponent component;
	private final Collection<FlowGraphEdge> edges;
	
	public AssemblyComponentContext(AssemblyContext context, FlowBasicComponent component) {
		super(context.getId(), context.getEncapsulatedComponent__AssemblyContext().getEntityName());
		this.context = Objects.requireNonNull(context, "Context must not be null");
		this.component = Objects.requireNonNull(component, "Component must not be null");
		this.edges = new HashSet<>();
	}

	@Override
	public void accept(FlowGraphVisitor visitor) {
		Objects.requireNonNull(visitor, "visitor must not be null");
		
		visitor.visit(this);
	}

	@Override
	public Map<SystemOperationIdentifying, Collection<SystemOperationIdentifying>> getIntraComponentFlows() {
		return intraComponentFlows;
	}

	@Override
	public Collection<FlowGraphEdge> getEdges() {
		return edges;
	}
	
	public void addEdge(AssemblyConnectorRepresentation edge) {
		edges.add(edge);
	}

	@Override
	public Collection<FlowGraphEdge> getInEdges() {
		return edges.stream().filter(edge -> edge.getHead().equals(this)).collect(Collectors.toSet());
	}

	@Override
	public Collection<FlowGraphEdge> getOutEdges() {
		return edges.stream().filter(edge -> edge.getTail().equals(this)).collect(Collectors.toSet());
	}

	@Override
	public Collection<FlowGraphVertex> getPredecessors() {
		return edges.stream().filter(edge -> edge.getHead().equals(this)).map(FlowGraphEdge::getTail).collect(Collectors.toSet());
	}
	
	@Override
	public Collection<FlowGraphVertex> getSuccessors() {
		return edges.stream().filter(edge -> edge.getTail().equals(this)).map(FlowGraphEdge::getHead).collect(Collectors.toSet());
	}
	
	@Override
	public boolean isSource() {
		return getInEdges().isEmpty();
	}

	@Override
	public boolean isSink() {
		return getOutEdges().isEmpty();
	}

	public AssemblyContext getContext() {
		return context;
	}
	
	public FlowBasicComponent getComponent() {
		return component;
	}
	
	public boolean encapsulatesContext(AssemblyContext context) {
		return this.context.equals(context);
	}
	
	public Optional<String> getClassPath() {
		return component.getClassPath();
	}
	
	public boolean isClassPathAvailable() {
		return component.isClassPathAvailable();
	}
	
}
