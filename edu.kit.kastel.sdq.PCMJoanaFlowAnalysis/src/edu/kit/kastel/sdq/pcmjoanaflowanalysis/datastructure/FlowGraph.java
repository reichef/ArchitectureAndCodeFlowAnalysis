package edu.kit.kastel.sdq.pcmjoanaflowanalysis.datastructure;

import java.util.Collection;
import java.util.Map;

import edu.kit.kastel.sdq.pcmjoanaflowanalysis.datastructure.visitors.VisitableGraphElement;

public interface FlowGraph extends VisitableGraphElement {

	public Map<FlowGraphVertex, Collection<FlowGraphEdge>> getAdjacencyList();
	
	public Collection<FlowGraphVertex> getVertices();
	
	public Collection<FlowGraphEdge> getEdges();
	
}
