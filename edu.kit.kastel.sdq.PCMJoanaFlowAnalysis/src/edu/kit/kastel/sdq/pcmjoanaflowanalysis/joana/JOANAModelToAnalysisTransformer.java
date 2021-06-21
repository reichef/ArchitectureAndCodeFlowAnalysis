package edu.kit.kastel.sdq.pcmjoanaflowanalysis.joana;

import java.util.ArrayList;
import java.util.List;

import edu.kit.joana.component.connector.ProgramPart;
import edu.kit.kastel.sdq.cosa.quality.JOANA.FlowSpecification;
import edu.kit.kastel.sdq.cosa.quality.JOANA.MethodTargetingSink;
import edu.kit.kastel.sdq.cosa.quality.JOANA.MethodTargetingSource;
import edu.kit.kastel.sdq.cosa.quality.JOANA.ParameterTargetingSink;
import edu.kit.kastel.sdq.cosa.quality.JOANA.ParameterTargetingSource;
import edu.kit.kastel.sdq.cosa.quality.JOANA.Sink;
import edu.kit.kastel.sdq.cosa.quality.JOANA.Source;
import edu.kit.kastel.sdq.pcmjoanaflowanalysis.analysiscoupling.Associations;

public class JOANAModelToAnalysisTransformer {

	public List<ProgramPart> transformSourcesOfFlowSpecToJOANAMethods(FlowSpecification flowSpec, Associations association) {
		List<ProgramPart> sources = new ArrayList<ProgramPart>();
		
		for(Source source : flowSpec.getSource()) {
			if(source instanceof MethodTargetingSource) {
				sources.add(association.getMethod(((MethodTargetingSource) source).getMethod().getId()));
			} else if (source instanceof ParameterTargetingSource) {
				//TODO: Add Capabilities to add Parameters? 
				//sources.add(association.getMethod(((ParameterTargetingSource) source).getMethod().getId()));
			}
			
		}
		
		return sources;
	}
	
	public List<ProgramPart> transformSinksOfFlowSpecToJOANASourcesFormat(FlowSpecification flowSpec, Associations association){
		List<ProgramPart> sinks = new ArrayList<ProgramPart>();
		
		for(Sink sink : flowSpec.getSink()) {
			if(sink instanceof MethodTargetingSink) {
				sinks.add(association.getMethod(((MethodTargetingSink)sink).getMethod().getId()));
			} else if (sink instanceof ParameterTargetingSink) {
				//TODO: Add Capabilities to add Parameters? 
			}
		}
		return sinks;
	}
	
}
