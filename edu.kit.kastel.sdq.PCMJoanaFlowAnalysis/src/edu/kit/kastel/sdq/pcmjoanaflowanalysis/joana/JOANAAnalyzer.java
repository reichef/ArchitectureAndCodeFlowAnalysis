package edu.kit.kastel.sdq.pcmjoanaflowanalysis.joana;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;

import org.eclipse.core.runtime.IPath;

import JOANA.FlowSpecification;
import edu.kit.joana.component.connector.Flows;
import edu.kit.joana.component.connector.JoanaCall;
import edu.kit.joana.component.connector.JoanaCallReturn;
import edu.kit.joana.component.connector.Method;
import edu.kit.kastel.sdq.pcmjoanaflowanalysis.Config;
import edu.kit.kastel.sdq.pcmjoanaflowanalysis.analysiscoupling.Association;
import edu.kit.kastel.sdq.pcmjoanaflowanalysis.analysiscoupling.Associations;

//this class mocks an joana analyzer 
public class JOANAAnalyzer {

	private JOANAModelToAnalysisTransformer transformer;
	private Config config;
	private static final String EXECUTING_JOANA_JAR_NAME = "joana.ui.ifc.wala.console.jar";

	public JOANAAnalyzer(Config config) {
		this.transformer = new JOANAModelToAnalysisTransformer();
		this.config = config;
	}

	public Flows analyzeFlow(FlowSpecification flowSpec, Associations association, String classPath) {

		if (!Paths.get(config.getJoanaCLIFolderPath() + IPath.SEPARATOR + EXECUTING_JOANA_JAR_NAME).toFile().exists()) {
			throw new RuntimeException("JOANA jar not on specified location");
		}

		List<Method> sources = transformer.transformSourcesOfFlowSpecToJOANAMethods(flowSpec, association);
		List<Method> sinks = transformer.transformSinksOfFlowSpecToJOANASourcesFormat(flowSpec, association);

		String joanaResultFileName = sources.get(0).className + "_" + sources.get(0).methodName + JoanaCallReturn.FILE_ENDING;
		
		Path joanaResultReturnPath = Paths.get(config.getJoanaOutputFolderPath() + IPath.SEPARATOR + joanaResultFileName);

		//System.out.println(String.format("Analysis Method %s in Class %s", sources.get(0).methodName, sources.get(0).className));
	
		
		// if we either do not use persisted flows or the flow was not yet analyzed, we
		// call JOANA
		if (!(config.usingPersistedFlows() && joanaResultReturnPath.toFile().exists())) {
			if (config.isUseLocalJOANAComputation()) {
				callJoanaLocal(classPath, joanaResultReturnPath, sources, sinks);
			} else {
				callJoanaRemote(classPath, joanaResultReturnPath, sources, sinks);
			}
		}

		return JoanaCallReturn.load(joanaResultReturnPath).flows;
	}

	private void callJoanaLocal(String classPath, Path joanaResultReturnPath, List<Method> sources,
			List<Method> sinks) {
		JoanaCall call = new JoanaCall(classPath, new Flows(), sources, sinks, Level.ALL);
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

		try {
			processBuilder.command("java", "-cp", EXECUTING_JOANA_JAR_NAME,
					"edu.kit.joana.ui.ifc.wala.console.console.component_based.CLI", "analyze",
					tmpFile.toAbsolutePath().toString(), joanaResultReturnPath.toString());

			Process process = processBuilder.start();

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

			process.waitFor();

		} catch (IOException e) {
			System.out.println("IOException");
			e.printStackTrace();
		} catch (InterruptedException e) {
			System.out.println("InterruptedException");
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			// TODO: Normally clean up tmp file here, but in case we need to send it
			// manually to the server, not yet implemented.
			// tmpFile.toFile().delete();
		}
	}

	private void callJoanaRemote(String classPath, Path joanaResultReturnPath, List<Method> sources,
			List<Method> sinks) {

		JoanaCall call = new JoanaCall(classPath, new Flows(), sources, sinks, Arrays.asList("edu.kit"), Level.ALL);

		JoanaCallReturn callReturn = call.processOnServer(config.getJoanaServerIP());

		callReturn.store(joanaResultReturnPath);
	}
}
