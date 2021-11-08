package edu.kit.kastel.sdq.pcmjoanaflowanalysis.datastructure;

public abstract class AbstractJoanaFlowDatastructureFactory {

	public abstract FlowGraph createFlowGraphRepresentation();
	
	public abstract FlowGraphVertex createFlowGraphVertexRepresentation();
	
	public abstract FlowGraphEdge createFlowGraphEdgeRepresentation();
	
}
