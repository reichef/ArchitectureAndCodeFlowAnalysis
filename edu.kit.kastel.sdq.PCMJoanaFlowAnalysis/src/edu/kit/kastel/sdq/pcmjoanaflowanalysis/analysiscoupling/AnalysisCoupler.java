package edu.kit.kastel.sdq.pcmjoanaflowanalysis.analysiscoupling;


import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;


import org.palladiosimulator.pcm.repository.OperationProvidedRole;
import org.palladiosimulator.pcm.repository.OperationSignature;
import org.palladiosimulator.pcm.repository.RepositoryComponent;

import JOANA.FlowSpecification;
import edu.kit.joana.component.connector.Flows;
import edu.kit.joana.component.connector.Method;
import edu.kit.kastel.sdq.pcmjoanaflowanalysis.Config;
import edu.kit.kastel.sdq.pcmjoanaflowanalysis.correspondences.PCM2SourceCodeCorrespondenceResolver;
import edu.kit.kastel.sdq.pcmjoanaflowanalysis.joana.JOANAAnalyzer;


public class AnalysisCoupler {

	private JOANAAnalyzer sourceCodeAnalyzer;
	private PCM2JOANAModelTransformator pcm2Joana;
	private StructuralModelGeneratorPCMToSourceCode pcm2SourceCode;
	private PCM2SourceCodeCorrespondenceResolver resolver;
	
	
	
	public AnalysisCoupler(Config config) {
		Map<Method, Set<Method>> flows = new HashMap<Method, Set<Method>>();
		this.pcm2SourceCode = new StructuralModelGeneratorPCMToSourceCode();
		sourceCodeAnalyzer = new JOANAAnalyzer(config);
	}
	
	public Set<String> analyzeIntraComponentFlow(RepositoryComponent component, OperationProvidedRole sourceRole, OperationSignature sourceSignature,  String classPath) {
		
		//generate source code and joana model
		pcm2SourceCode.processPCMRepo(component.getRepository__RepositoryComponent());
		if(pcm2Joana == null) {
			this.resolver = new PCM2SourceCodeCorrespondenceResolver(pcm2SourceCode.getCorrespondences());
			this.pcm2Joana = new PCM2JOANAModelTransformator(this.resolver);
		}
		
		Pair<FlowSpecification,Association> flowInformation = pcm2Joana.generateFlowForProvidedOperationAndComponent(sourceRole, sourceSignature, component);
		
		//run JOANA-Analysis
		
		Flows joanaFlowsResults = sourceCodeAnalyzer.analyzeFlow(flowInformation.getFirst(), flowInformation.getSecond(), classPath);
		
		//transform SourceCode Analysis Results back to PCM-Information
		//TODO: Potentially also possible to already create here SignatureIdentifying
		Set<String> pcmSinksIds = new HashSet<String>();
		
		for(Entry<Method, Set<Method>> flow : joanaFlowsResults.flows().entrySet()) {
			
			Collection<Method> sinks = flow.getValue();
			for(Method joanaCLIMethod : sinks) {
				edu.kit.ipd.sdq.composition.securityanalyses.coupling.structure.SourceCode.Method sourceCodeMethod = resolver.getMethodById(flowInformation.getSecond().getId(joanaCLIMethod));
				OperationSignature pcmSinkSignature = resolver.getOperationSignature(sourceCodeMethod);
				pcmSinksIds.add(pcmSinkSignature.getId());
			}

		}

		return pcmSinksIds;
	}
}
