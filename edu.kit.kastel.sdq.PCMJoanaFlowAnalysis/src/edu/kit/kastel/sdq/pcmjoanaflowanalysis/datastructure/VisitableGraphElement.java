package edu.kit.kastel.sdq.pcmjoanaflowanalysis.datastructure;

import edu.kit.kastel.sdq.pcmjoanaflowanalysis.datastructure.visitors.FlowGraphVisitor;

public interface VisitableGraphElement {

	void accept(FlowGraphVisitor visitor);

}