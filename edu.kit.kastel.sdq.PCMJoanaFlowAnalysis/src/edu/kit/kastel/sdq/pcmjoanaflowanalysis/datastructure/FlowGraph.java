package edu.kit.kastel.sdq.pcmjoanaflowanalysis.datastructure;

import java.util.Collection;
import java.util.Map;

public interface FlowGraph extends VisitableGraphElement {

	public Map<FlowGraphVertex, Collection<FlowGraphVertex>> getAdjacencyList();
	
	public Collection<FlowGraphVertex> getVertices();
	
	public Collection<FlowGraphEdge> getEdges();
	
}
