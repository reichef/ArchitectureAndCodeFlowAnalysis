package edu.kit.kastel.sdq.pcmjoanaflowanalysis.joana;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.eclipse.core.runtime.IPath;

import JOANA.FlowSpecification;
import edu.kit.joana.component.connector.Flows;
import edu.kit.joana.component.connector.JoanaCall;
import edu.kit.joana.component.connector.JoanaCallReturn;
import edu.kit.joana.component.connector.Method;
import edu.kit.kastel.sdq.pcmjoanaflowanalysis.Config;
import edu.kit.kastel.sdq.pcmjoanaflowanalysis.analysiscoupling.Association;


//this class mocks an joana analyzer 
public class JOANAAnalyzer {

	
	private JOANAModelToAnalysisTransformer transformer;
	private Config config;
	private static final String EXECTUING_JOANA_JAR_NAME = "joana.ui.ifc.wala.console.jar";
	
	public JOANAAnalyzer(Config config) {
		this.transformer = new JOANAModelToAnalysisTransformer();
		this.config = config;
	}
	
	public Flows analyzeFlow(FlowSpecification flowSpec, Association association, String classPath) {
		
		if(!Paths.get(config.getJoanaCLIFolderPath() + IPath.SEPARATOR + EXECTUING_JOANA_JAR_NAME).toFile().exists()) {
			throw new RuntimeException("JOANA jar not on specified location");
		}
		
		
		List<Method> sources = transformer.transformSourcesOfFlowSpecToJOANAMethods(flowSpec, association);
		List<Method> sinks = transformer.transformSinksOfFlowSpecToJOANASourcesFormat(flowSpec, association);
	
		
		
		JoanaCall call = new JoanaCall(classPath, new Flows(), sources, sinks);
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
		
		System.out.println(processBuilder.directory().getAbsolutePath());
		
		
		
		try {
		processBuilder.command(
//				"cmd.exe",
//				"/c",
//				"dir"
				"java",
				"-cp", 
				EXECTUING_JOANA_JAR_NAME , 
				"edu.kit.joana.ui.ifc.wala.console.console.component_based.CLI",
				"analyze", 
				tmpFile.toAbsolutePath().toString(), 
				config.getJoanaOutputFolderPath() + "helloWorld.json"
				);
		
		Process process = processBuilder.start();
		
		BufferedReader errorReader = new BufferedReader(
				new InputStreamReader(process.getErrorStream()));
		
		BufferedReader inputReader = new BufferedReader(
				new InputStreamReader(process.getInputStream()));
		
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

		int exitVal = process.waitFor();
		
		
		} catch (IOException e) {
			System.out.println("IOException");
			e.printStackTrace();
		} catch (InterruptedException e) {
			System.out.println("InterruptedException");
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			
		}
		
//		JoanaCallReturn callReturn = new JoanaCallReturn(new Flows());
//		JoanaCallReturn.load(Paths.get("helloWorld.json"));
		
		
//		analyzer = new BasicFlowAnalyzer(association, false);
//		analyzer.setClassPath(classPath);
//		System.out.println(System.getProperty("java.class.version"));
		//TODO: Call JOANAConnector 
		return null;
	}

}
