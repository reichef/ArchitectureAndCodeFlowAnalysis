package edu.kit.kastel.sdq.pcmjoanaflowanalysis.joana;

import java.util.List;

import JOANA.FlowSpecification;
import edu.kit.joana.ui.ifc.wala.console.console.component_based.Association;
import edu.kit.joana.ui.ifc.wala.console.console.component_based.BasicFlowAnalyzer;
import edu.kit.joana.ui.ifc.wala.console.console.component_based.FlowAnalyzer;
import edu.kit.joana.ui.ifc.wala.console.console.component_based.Flows;
import edu.kit.joana.ui.ifc.wala.console.console.component_based.Method;

public class JOANAAnalyzer {

	
	private FlowAnalyzer analyzer;
	private JOANAModelToAnalysisTransformer transformer;

	
	public JOANAAnalyzer(Association association) {
		this.analyzer = new BasicFlowAnalyzer();
		this.transformer = new JOANAModelToAnalysisTransformer(association);
	}
	
	public Flows analyzeFlow(FlowSpecification flowSpec, String classPath) {
		List<Method> sources = transformer.transformSourcesOfFlowSpecToJOANAMethods(flowSpec);
		List<Method> sinks = transformer.transformSinksOfFlowSpecToJOANASourcesFormat(flowSpec);
		analyzer.setClassPath(classPath);
		
		return analyzer.analyze(sources, sinks);
	}

}
