package edu.kit.kastel.sdq.pcmjoanaflowanalysis.joana;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;

import org.eclipse.core.runtime.IPath;


import edu.kit.joana.component.connector.Flows;
import edu.kit.joana.component.connector.JoanaCall;
import edu.kit.joana.component.connector.JoanaCallReturn;
import edu.kit.joana.component.connector.Lattice;
import edu.kit.joana.component.connector.ProgramPart;
import edu.kit.kastel.sdq.cosa.quality.JOANA.FlowSpecification;
import edu.kit.kastel.sdq.pcmjoanaflowanalysis.Config;
import edu.kit.kastel.sdq.pcmjoanaflowanalysis.analysiscoupling.Associations;


public class JOANAAnalyzer {

	private JOANAModelToAnalysisTransformer transformer;
	private Config config;
	private static final String JOANA_FILE_ENDING = "json";

	public JOANAAnalyzer(Config config) {
		this.transformer = new JOANAModelToAnalysisTransformer();
		this.config = config;
	}

	public Flows analyzeFlow(FlowSpecification flowSpec, Associations association, String classPath) {

	

		List<ProgramPart> sources = transformer.transformSourcesOfFlowSpecToJOANAMethods(flowSpec, association);
		List<ProgramPart> sinks = transformer.transformSinksOfFlowSpecToJOANASourcesFormat(flowSpec, association);

		String joanaResultFileName = String.format("%s_%s.%s", sources.get(0).getOwningMethod().getClassName(), sources.get(0).getOwningMethod().methodName, JOANA_FILE_ENDING);
		System.out.println("Analysing:" + joanaResultFileName);
		Path joanaResultReturnPath = Paths.get(config.getJoanaOutputFolderPath() + IPath.SEPARATOR + joanaResultFileName);
		
			
		// if we either do not use persisted flows or the flow was not yet analyzed, we
		// call JOANA
		if (!(config.usingPersistedFlows() && joanaResultReturnPath.toFile().exists())) {
			if (config.isUseLocalJOANAComputation()) {
				throw new UnsupportedOperationException("Local Joana Calls are not supported anymore");
			} else {
				callJoanaRemote(classPath, joanaResultReturnPath, sources, sinks);
			}
		}

		return JoanaCallReturn.load(joanaResultReturnPath).asFlows().flows;
	}

	

	private void callJoanaRemote(String classPath, Path joanaResultReturnPath, List<ProgramPart> sources,
			List<ProgramPart> sinks) {

		JoanaCall call = new JoanaCall(classPath, new Flows(), sources, sinks, Arrays.asList("edu.kit.kastel"), Level.ALL, Lattice.BASIC);

		JoanaCallReturn callReturn = call.processOnServer(config.getJoanaServerIP());
		
		if(callReturn != null) {
			callReturn.store(joanaResultReturnPath);
		}
	}
}
