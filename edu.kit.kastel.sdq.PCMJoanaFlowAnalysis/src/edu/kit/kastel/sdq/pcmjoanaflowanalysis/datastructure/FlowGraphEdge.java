package edu.kit.kastel.sdq.pcmjoanaflowanalysis.datastructure;

import java.util.Collection;

import edu.kit.kastel.sdq.pcmjoanaflowanalysis.pcmflow.IntraComponentFlow;

public interface FlowGraphEdge extends VisitableGraphElement {
	
	/*
	 * (non-javadoc)
	 * 
	 * Tail - the endpoint vertex from which the edge is directed.
	 */
	public FlowGraphVertex getTail();
	
	/*
	 * (non-javadoc)
	 * 
	 * Head - The endpoint vertex to which the edge is directed.
	 */
	public FlowGraphVertex getHead();
	
	public Collection<IntraComponentFlow> getInterComponentFlows();
	
	public void addInterComponentFlow(IntraComponentFlow flow);
	
}
