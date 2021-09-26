package edu.kit.kastel.sdq.pcmjoanaflowanalysis.datastructure.refactored;

import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.palladiosimulator.pcm.core.composition.AssemblyContext;

import edu.kit.kastel.sdq.pcmjoanaflowanalysis.datastructure.FlowGraphEdge;
import edu.kit.kastel.sdq.pcmjoanaflowanalysis.datastructure.FlowGraphVertex;
import edu.kit.kastel.sdq.pcmjoanaflowanalysis.datastructure.visitors.FlowGraphVisitor;
import edu.kit.kastel.sdq.pcmjoanaflowanalysis.pcmflow.IntraComponentFlow;

public class AssemblyComponentContext extends AssemblyRepresentation implements FlowGraphVertex {

	private final AssemblyContext context;
	private FlowBasicComponent component;
	private final Collection<FlowGraphEdge> edges;
	
	public AssemblyComponentContext(AssemblyContext context, FlowBasicComponent component) {
		super(context.getId(), context.getEncapsulatedComponent__AssemblyContext().getEntityName());
		this.context = context;
		this.component = component;
		this.edges = new HashSet<>();
	}

	@Override
	public void accept(FlowGraphVisitor visitor) {
		Objects.requireNonNull(visitor, "visitor must not be null");
		
		visitor.visit(this);
	}

	@Override
	public Collection<IntraComponentFlow> getIntraComponentFlows() {
		return component.getComponentInternalFlows();
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
	public Collection<FlowGraphVertex> getSuccessors() {
		return edges.stream().filter(edge -> edge.getTail().equals(this)).map(FlowGraphEdge::getHead).collect(Collectors.toSet());
	}

	@Override
	public Collection<FlowGraphVertex> getPredecessors() {
		return edges.stream().filter(edge -> edge.getHead().equals(this)).map(FlowGraphEdge::getTail).collect(Collectors.toSet());
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
