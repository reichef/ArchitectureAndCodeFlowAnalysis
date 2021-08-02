package edu.kit.kastel.sdq.pcmjoanaflowanalysis.datastructure;

public abstract class AbstractJoanaFlowDatastructureFactory {

	public abstract VisitableGraphElement createFlowGraphRepresentation();
	
	public abstract FlowGraphVertex createFlowGraphVertexRepresentation();
	
	public abstract FlowGraphEdge createFlowGraphEdgeRepresentation();
	
}
