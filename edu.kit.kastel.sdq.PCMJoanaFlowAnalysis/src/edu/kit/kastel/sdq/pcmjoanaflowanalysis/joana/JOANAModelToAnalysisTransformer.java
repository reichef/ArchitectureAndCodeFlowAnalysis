package edu.kit.kastel.sdq.pcmjoanaflowanalysis.joana;

import java.util.ArrayList;
import java.util.List;

import JOANA.FlowSpecification;
import JOANA.ParameterSink;
import JOANA.Sink;
import JOANA.Source;
import edu.kit.joana.component.connector.Method;
import edu.kit.kastel.sdq.pcmjoanaflowanalysis.analysiscoupling.Association;

public class JOANAModelToAnalysisTransformer {

	public List<Method> transformSourcesOfFlowSpecToJOANAMethods(FlowSpecification flowSpec, Association association) {
		List<Method> sources = new ArrayList<Method>();
		
		for(Source source : flowSpec.getSource()) {
			sources.add(association.getMethod(source.getMethod().getId()));
		}
		
		return sources;
	}
	
	public List<Method> transformSinksOfFlowSpecToJOANASourcesFormat(FlowSpecification flowSpec, Association association){
		List<Method> sinks = new ArrayList<Method>();
		
		for(Sink sink : flowSpec.getSink()) {
			if(sink instanceof ParameterSink)
			sinks.add(association.getMethod(((ParameterSink)sink).getMethod().getId()));
		}
		
		return sinks;
	}
	
}
