package edu.kit.kastel.sdq.pcmjoanaflowanalysis.datastructure.refactored;

import java.util.Collection;
import java.util.HashMap;
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
	private final Map<FlowGraphVertex, Collection<FlowGraphVertex>> adjacencyList;
	
	public SystemRepresentation(ComposedProvidingRequiringEntity topMostEntity) {
		super(topMostEntity.getId(), topMostEntity.getEntityName());
		this.entity = topMostEntity;
		this.adjacencyList = new HashMap<>();
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
		adjacencyList.put(vertex, vertex.getSuccessors());
	}
	
	public void addVertices(Collection<AssemblyComponentContext> vertices) {
		vertices.forEach(vertex -> addVertex(vertex));
	}
	
	@Override
	public Map<FlowGraphVertex, Collection<FlowGraphVertex>> getAdjacencyList() {
		return adjacencyList;
	}

	@Override
	public Collection<FlowGraphVertex> getVertices() {
		return adjacencyList.keySet();
	}

	@Override
	public Collection<FlowGraphEdge> getEdges() {
		return adjacencyList.keySet().stream().map(FlowGraphVertex::getOutEdges).flatMap(Collection::stream).collect(Collectors.toSet());
	}

}
