package edu.kit.kastel.sdq.pcmjoanaflowanalysis.datastructure.refactored;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import org.palladiosimulator.pcm.core.entity.ComposedProvidingRequiringEntity;

import edu.kit.kastel.sdq.pcmjoanaflowanalysis.datastructure.FlowGraph;
import edu.kit.kastel.sdq.pcmjoanaflowanalysis.datastructure.FlowGraphEdge;
import edu.kit.kastel.sdq.pcmjoanaflowanalysis.datastructure.FlowGraphVertex;
import edu.kit.kastel.sdq.pcmjoanaflowanalysis.datastructure.visitors.FlowGraphVisitor;

public class SystemRepresentation extends AssemblyRepresentation implements FlowGraph {

	private final ComposedProvidingRequiringEntity entity;
	private final Collection<FlowGraphVertex> vertices;
	
	public SystemRepresentation(ComposedProvidingRequiringEntity topMostEntity) {
		super(topMostEntity.getId(), topMostEntity.getEntityName());
		this.entity = Objects.requireNonNull(topMostEntity, "System must not be null");
		this.vertices = new HashSet<>();
	}
	
	public ComposedProvidingRequiringEntity getContainedEntity() {
		return entity;
	}
	
	@Override
	public void accept(FlowGraphVisitor visitor) {
		Objects.requireNonNull(visitor, "visitor must not be null");
		
		visitor.visit(this);
	}
	
	public void addVertex(AssemblyComponentContext vertex) {
		vertices.add(vertex);
	}
	
	public void addVertices(Collection<AssemblyComponentContext> vertices) {
		this.vertices.addAll(vertices);
	}
	
	@Override
	public Map<FlowGraphVertex, Collection<FlowGraphVertex>> getAdjacencyList() {
		return vertices.stream().collect(Collectors.toMap(vertex -> vertex, vertex -> vertex.getSuccessors()));
	}

	@Override
	public Collection<FlowGraphVertex> getVertices() {
		return new HashSet<>(vertices);
	}

	@Override
	public Collection<FlowGraphEdge> getEdges() {
		return vertices.stream().map(FlowGraphVertex::getOutEdges).flatMap(Collection::stream).collect(Collectors.toSet());
	}

	@Override
	public Collection<FlowGraphVertex> getSources() {
		return vertices.stream().filter(FlowGraphVertex::isSource).collect(Collectors.toSet());
	}

	@Override
	public Collection<FlowGraphVertex> getSinks() {
		return vertices.stream().filter(FlowGraphVertex::isSink).collect(Collectors.toSet());
	}

}
