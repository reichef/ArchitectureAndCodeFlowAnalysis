package edu.kit.kastel.sdq.pcmjoanaflowanalysis.datastructure;

public interface VisitableGraphElement {

	void accept(FlowGraphVisitor visitor);

}