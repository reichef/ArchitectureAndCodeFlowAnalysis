package edu.kit.kastel.sdq.pcmjoanaflowanalysis.datastructure;

public interface FlowGraphVisitor {

	public void visit(VisitableGraphElement graph);
	
	public void visit(FlowGraphVertex vertex);
	
	public void visit(FlowGraphEdge edge);
	
}
