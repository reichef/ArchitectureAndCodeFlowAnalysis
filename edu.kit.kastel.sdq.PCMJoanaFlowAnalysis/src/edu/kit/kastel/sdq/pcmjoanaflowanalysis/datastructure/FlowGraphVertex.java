package edu.kit.kastel.sdq.pcmjoanaflowanalysis.datastructure;

import java.util.Collection;

import edu.kit.kastel.sdq.pcmjoanaflowanalysis.datastructure.visitors.VisitableGraphElement;
import edu.kit.kastel.sdq.pcmjoanaflowanalysis.pcmflow.IntraComponentFlow;

public interface FlowGraphVertex extends VisitableGraphElement {
	
	public String getId();
	
	public String getName();
	
	public Collection<IntraComponentFlow> getIntraComponentFlows();
	
	public Collection<FlowGraphEdge> getEdges();
	public Collection<FlowGraphEdge> getInEdges();
	public Collection<FlowGraphEdge> getOutEdges();
	
	/**
	 * @return All successing vertices directly reachable from this vertex.
	 */
	public Collection<FlowGraphVertex> getSuccessors();
	
	/**
	 * @return All predecessing vertices directly.
	 */
	public Collection<FlowGraphVertex> getPredecessors();
}
