package edu.kit.kastel.sdq.pcmjoanaflowanalysis.datastructure.refactored;

import edu.kit.kastel.sdq.ecoreannotations.GenericTargetStringContentAnnotation;
import edu.kit.kastel.sdq.ecoreannotations.util.EcoreannotationsSwitch;

class AnnotationsVisitor extends EcoreannotationsSwitch<Object> {
	
	private final PCMJoanaFlowDatastructureFactory factory;
	
	AnnotationsVisitor(PCMJoanaFlowDatastructureFactory factory) {
		this.factory = factory;
	}

	@Override
	public Object caseGenericTargetStringContentAnnotation(GenericTargetStringContentAnnotation object) {
		// TODO Auto-generated method stub
		return super.caseGenericTargetStringContentAnnotation(object);
	}

}
