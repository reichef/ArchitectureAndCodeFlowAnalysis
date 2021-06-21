package edu.kit.kastel.sdq.pcmjoanaflowanalysis.joana;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
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
	private static final String EXECUTING_JOANA_JAR_NAME = "joana.ui.ifc.wala.console.jar";
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
				if (!Paths.get(config.getJoanaCLIFolderPath() + IPath.SEPARATOR + EXECUTING_JOANA_JAR_NAME).toFile().exists()) {
					throw new RuntimeException("JOANA jar not on specified location");
				}
				callJoanaLocal(classPath, joanaResultReturnPath, sources, sinks);
			} else {
				callJoanaRemote(classPath, joanaResultReturnPath, sources, sinks);
			}
		}

		return JoanaCallReturn.load(joanaResultReturnPath).asFlows().flows;
	}

	private void callJoanaLocal(String classPath, Path joanaResultReturnPath, List<ProgramPart> sources,
			List<ProgramPart> sinks) {
		JoanaCall call = new JoanaCall(classPath, new Flows(),sources, sinks, Level.ALL, Lattice.BASIC);
		Path tmpFile = null;
		try {
			tmpFile = Files.createTempFile("", ".zip");
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		call.storeWithClassPath(tmpFile);
		System.out.println(tmpFile);
		ProcessBuilder processBuilder = new ProcessBuilder();
		processBuilder.directory(Paths.get(config.getJoanaCLIFolderPath()).toFile());

		
			processBuilder.command("java", "-cp", EXECUTING_JOANA_JAR_NAME,
					"edu.kit.joana.ui.ifc.wala.console.console.component_based.CLI", "analyze",
					tmpFile.toAbsolutePath().toString(), joanaResultReturnPath.toString());
			
			Process process = null;
			try {
				process = processBuilder.start();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				return;
			}
			
			BufferedReader errorReader = new BufferedReader(new InputStreamReader(process.getErrorStream()));

			BufferedReader inputReader = new BufferedReader(new InputStreamReader(process.getInputStream()));

			new Thread(() -> {
				String line = null;
				while (true) {
					try {
						if (!((line = errorReader.readLine()) != null))
							break;
					} catch (IOException e) {
						e.printStackTrace();
					}
					System.err.println(line);
				}
			}).run();
			new Thread(() -> {
				String line = null;
				while (true) {
					try {
						if (!((line = inputReader.readLine()) != null))
							break;
					} catch (IOException e) {
						e.printStackTrace();
					}
					System.out.println(line);
				}
			}).run();

			try {
				process.waitFor();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return;
			}

	}

	private void callJoanaRemote(String classPath, Path joanaResultReturnPath, List<ProgramPart> sources,
			List<ProgramPart> sinks) {

		JoanaCall call = new JoanaCall(classPath, new Flows(), sources, sinks, Arrays.asList("edu.kastel"), Level.ALL, Lattice.BASIC);

		JoanaCallReturn callReturn = call.processOnServer(config.getJoanaServerIP());
		
		if(callReturn != null) {
			callReturn.store(joanaResultReturnPath);
		}
	}
}
