package edu.kit.kastel.sdq.pcmjoanaflowanalysis.joana;

import java.util.ArrayList;
import java.util.List;

import JOANA.FlowSpecification;
import JOANA.ParameterSink;
import JOANA.Sink;
import JOANA.Source;
import edu.kit.joana.ui.ifc.wala.console.console.component_based.Association;
import edu.kit.joana.ui.ifc.wala.console.console.component_based.Method;

public class JOANAModelToAnalysisTransformer {

	private Association association;
	
	public JOANAModelToAnalysisTransformer(Association association) {
		this.association = association;
	}

	public List<Method> transformSourcesOfFlowSpecToJOANAMethods(FlowSpecification flowSpec) {
		List<Method> sources = new ArrayList<Method>();
		
		for(Source source : flowSpec.getSource()) {
			sources.add(association.getMethod(source.getMethod().getId()));
		}
		
		return sources;
	}
	
	public List<Method> transformSinksOfFlowSpecToJOANASourcesFormat(FlowSpecification flowSpec){
		List<Method> sinks = new ArrayList<Method>();
		
		for(Sink sink : flowSpec.getSink()) {
			if(sink instanceof ParameterSink)
			sinks.add(association.getMethod(((ParameterSink)sink).getMethod().getId()));
		}
		
		return sinks;
	}
	
}
