package edu.kit.kastel.sdq.pcmjoanaflowanalysis.datastructure.visitors;

import edu.kit.kastel.sdq.pcmjoanaflowanalysis.datastructure.FlowGraphEdge;
import edu.kit.kastel.sdq.pcmjoanaflowanalysis.datastructure.FlowGraphVertex;
import edu.kit.kastel.sdq.pcmjoanaflowanalysis.datastructure.VisitableGraphElement;

public interface FlowGraphVisitor {

	public void visit(VisitableGraphElement graph);
	
	public void visit(FlowGraphVertex vertex);
	
	public void visit(FlowGraphEdge edge);
	
}
