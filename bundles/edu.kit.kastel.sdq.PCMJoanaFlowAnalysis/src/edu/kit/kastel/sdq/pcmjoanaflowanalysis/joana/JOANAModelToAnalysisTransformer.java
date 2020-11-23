package edu.kit.kastel.sdq.pcmjoanaflowanalysis.joana;

import java.util.ArrayList;
import java.util.List;

import JOANA.FlowSpecification;
import JOANA.MethodIdentifying;
import JOANA.Sink;
import JOANA.Source;
import edu.kit.joana.component.connector.Method;
import edu.kit.kastel.sdq.pcmjoanaflowanalysis.analysiscoupling.Associations;

public class JOANAModelToAnalysisTransformer {

	public List<Method> transformSourcesOfFlowSpecToJOANAMethods(FlowSpecification flowSpec, Associations association) {
		List<Method> sources = new ArrayList<Method>();

		for (Source source : flowSpec.getSource()) {
			if (source instanceof MethodIdentifying) {
				sources.add(association.getMethod(((MethodIdentifying)source).getMethod().getId()));
			}
		}

		return sources;
	}

	public List<Method> transformSinksOfFlowSpecToJOANASourcesFormat(FlowSpecification flowSpec,
			Associations association) {
		List<Method> sinks = new ArrayList<Method>();

		for (Sink sink : flowSpec.getSink()) {
			if (sink instanceof MethodIdentifying)
				sinks.add(association.getMethod(((MethodIdentifying) sink).getMethod().getId()));
		}

		return sinks;
	}

}
