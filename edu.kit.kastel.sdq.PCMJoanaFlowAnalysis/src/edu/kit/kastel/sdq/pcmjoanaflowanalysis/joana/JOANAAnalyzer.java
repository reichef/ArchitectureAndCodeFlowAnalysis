package edu.kit.kastel.sdq.pcmjoanaflowanalysis.joana;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import JOANA.FlowSpecification;
import edu.kit.joana.component.connector.Flows;
import edu.kit.joana.component.connector.JoanaCall;
import edu.kit.joana.component.connector.Method;

import edu.kit.kastel.sdq.pcmjoanaflowanalysis.analysiscoupling.Association;


//this class mocks an joana analyzer 
public class JOANAAnalyzer {

	
	private JOANAModelToAnalysisTransformer transformer;

	
	public JOANAAnalyzer() {
		this.transformer = new JOANAModelToAnalysisTransformer();
	}
	
	public Flows analyzeFlow(FlowSpecification flowSpec, Association association, String classPath) {
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
		
		System.out.println(tmpFile.toAbsolutePath());
		//Runtime runtime = Runtime.getRuntime();
		boolean isWindows = System.getProperty("os.name")
				  .toLowerCase().startsWith("windows");
		
		
		ProcessBuilder processBuilder = new ProcessBuilder();
		processBuilder.directory(new File("C:\\Users\\Frederik Reiche\\git\\Diss\\ArchitectureAndCodeFlowAnalysis\\edu.kit.kastel.sdq.PCMJoanaFlowAnalysis"));
		try {
		processBuilder.command(
				"java", "-cp", "joana.ui.ifc.wala.console.jar" , "edu.kit.joana.ui.ifc.wala.console.console.component_based.CLI analyze", tmpFile.toAbsolutePath().toString(), "helloWorld");
		Process process = processBuilder.start();
		

		StringBuilder output = new StringBuilder();
		BufferedReader reader = new BufferedReader(
				new InputStreamReader(process.getInputStream()));
		
		String line;
		while ((line = reader.readLine()) != null) {
			output.append(line + "\n");
		}

		int exitVal = process.waitFor();
		
		if (exitVal == 0) {
			System.out.println("Success!");
			System.out.println(output);
			System.exit(0);
		} else {
			//abnormal...
		}
		
		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			
		}
		
		
//		analyzer = new BasicFlowAnalyzer(association, false);
//		analyzer.setClassPath(classPath);
//		System.out.println(System.getProperty("java.class.version"));
		//TODO: Call JOANAConnector 
		return null;
	}

}
