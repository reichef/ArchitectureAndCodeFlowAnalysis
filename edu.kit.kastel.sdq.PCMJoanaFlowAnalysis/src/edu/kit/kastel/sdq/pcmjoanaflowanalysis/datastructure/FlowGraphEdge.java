package edu.kit.kastel.sdq.pcmjoanaflowanalysis.datastructure;

import java.util.Map;

import edu.kit.kastel.sdq.pcmjoanaflowanalysis.datastructure.refactored.AssemblyComponentContext;
import edu.kit.kastel.sdq.pcmjoanaflowanalysis.pcmflow.fixpoint.SystemOperationIdentifying;

public interface FlowGraphEdge extends VisitableGraphElement {
	public String getId();
	public String getName();
	public enum Direction {ASSEMBLY, OPPOSITE};
	/**
	 * The endpoint vertex from which the edge is directed.
	 */
	public FlowGraphVertex getTail();
	
	/**
	 * The endpoint vertex to which the edge is directed.
	 */
	public FlowGraphVertex getHead();
	
	public Map<SystemOperationIdentifying, SystemOperationIdentifying> getFlows();
	public SystemOperationIdentifying getFlow(SystemOperationIdentifying source);
	public void addFlow(SystemOperationIdentifying source, SystemOperationIdentifying sink);
	public boolean fitting(AssemblyComponentContext providing, AssemblyComponentContext requiring);
	public Direction getDirection(SystemOperationIdentifying sink);
	
}
