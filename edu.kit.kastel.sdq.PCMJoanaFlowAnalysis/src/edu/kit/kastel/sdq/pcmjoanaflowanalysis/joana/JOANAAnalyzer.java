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
import edu.kit.kastel.sdq.pcmjoanaflowanalysis.Config;

public class JOANAAnalyzer {

	
	private Config config;
	private static final String JOANA_FILE_ENDING = "json";
	private static final String JOANA_RESULT_FILE_FORMAT = "%s_%s.%s";

	public JOANAAnalyzer(Config config) {
		this.config = config;
	}

	public Flows analyzeFlow(List<ProgramPart> sources, List<ProgramPart> sinks, String classPath) {

		String joanaResultFileName = String.format(JOANA_RESULT_FILE_FORMAT, sources.get(0).getOwningMethod().getClassName(),
				sources.get(0).getOwningMethod().methodName, JOANA_FILE_ENDING);
		System.out.println("Analysing:" + joanaResultFileName);
		Path joanaResultReturnPath = Paths
				.get(config.getJoanaOutputFolderPath() + IPath.SEPARATOR + joanaResultFileName);

		// if we either do not use persisted flows or the flow was not yet analyzed, JOANA is called
		if (!(config.usingPersistedFlows() && joanaResultReturnPath.toFile().exists())) {
			callJoanaRemote(classPath, joanaResultReturnPath, sources, sinks);
		}

		return JoanaCallReturn.load(joanaResultReturnPath).asFlows().flows;
	}

	private void callJoanaRemote(String classPath, Path joanaResultReturnPath, List<ProgramPart> sources,
			List<ProgramPart> sinks) {

		JoanaCall call = new JoanaCall(classPath, new Flows(), sources, sinks, Arrays.asList("edu.kit.kastel"),
				Level.ALL, Lattice.BASIC);

		JoanaCallReturn callReturn = call.processOnServer(config.getJoanaServerIP());

		if (callReturn != null) {
			callReturn.store(joanaResultReturnPath);
		}
	}
}
