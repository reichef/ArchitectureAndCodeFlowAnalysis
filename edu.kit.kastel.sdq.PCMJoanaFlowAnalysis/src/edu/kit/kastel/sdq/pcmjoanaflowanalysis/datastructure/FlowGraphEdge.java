package edu.kit.kastel.sdq.pcmjoanaflowanalysis.datastructure;

import java.util.Collection;

import edu.kit.kastel.sdq.pcmjoanaflowanalysis.pcmflow.IntraComponentFlow;

public interface FlowGraphEdge extends VisitableGraphElement {
	
	/*
	 * (non-javadoc)
	 * 
	 * Tail - the endpoint vertex where the edge is directed from.
	 */
	public FlowGraphVertex getTail();
	
	/*
	 * (non-javadoc)
	 * 
	 * Head - The endpoint vertex where the edge is directed to.
	 */
	public FlowGraphVertex getHead();
	
	public Collection<IntraComponentFlow> getInterComponentFlows();
	
}
