package edu.kit.kastel.sdq.pcmjoanaflowanalysis.datastructure;

import java.util.Collection;
import java.util.Map;

public interface FlowGraph extends VisitableGraphElement {

	/**
	 * @return A representation of this Graph as adjacency list
	 */
	public Map<FlowGraphVertex, Collection<FlowGraphVertex>> getAdjacencyList();
	
	public Collection<FlowGraphVertex> getVertices();
	
	public Collection<FlowGraphEdge> getEdges();
	
	public Collection<FlowGraphVertex> getSources();
	
	public Collection<FlowGraphVertex> getSinks();
	
}
