package edu.kit.kastel.sdq.pcmjoanaflowanalysis.datastructure.visitors;

public interface VisitableGraphElement {

	void accept(FlowGraphVisitor visitor);

}