package edu.kit.kastel.sdq.pcmjoanaflowanalysis.analysiscoupling;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import java.util.Set;

import org.palladiosimulator.pcm.repository.OperationSignature;
import org.palladiosimulator.pcm.repository.Repository;

import edu.kit.joana.component.connector.Flows;
import edu.kit.joana.component.connector.ProgramPart;
import edu.kit.kastel.sdq.cosa.quality.JOANA.FlowSpecification;
import edu.kit.kastel.sdq.pcmjoanaflowanalysis.Config;
import edu.kit.kastel.sdq.pcmjoanaflowanalysis.correspondences.PCM2SourceCodeCorrespondenceResolver;
import edu.kit.kastel.sdq.pcmjoanaflowanalysis.joana.JOANAAnalyzer;
import edu.kit.kastel.sdq.pcmjoanaflowanalysis.joana.JOANAModelToAnalysisTransformer;
import edu.kit.kastel.sdq.pcmjoanaflowanalysis.pcmflow.fixpoint.SystemOperationIdentifying;

//TODO: Refactor this class to only provide functionality to translate architecture-model (or datastructure) to JOANA Elements

public class PCMJOANACoupler {

	private JOANAModelToAnalysisTransformer transformer;
	private JOANAAnalyzer sourceCodeAnalyzer;
	private PCM2JOANAModelTransformator pcm2Joana;
	private StructuralModelGeneratorPCMToSourceCode pcm2SourceCode;
	private PCM2SourceCodeCorrespondenceResolver resolver;

	public PCMJOANACoupler(Config config) {
		this.pcm2SourceCode = new StructuralModelGeneratorPCMToSourceCode();
		sourceCodeAnalyzer = new JOANAAnalyzer(config);
		this.transformer = new JOANAModelToAnalysisTransformer();
	}

	public Set<String> analyzeIntraComponentFlow(SystemOperationIdentifying source, String classPath) {

		generateSourceCodeAndJoanaModel(
				source.getContext().getComponent().getComponent().getRepository__RepositoryComponent());

		// TODO in Prinicple every potential sink in a component should be provided by
		// the analyses, speaking: is there a flow from op A to op B, op C ....,
		// the coupler should only parse this.
		Pair<FlowSpecification, Associations> flowInformation = pcm2Joana
				.generateFlowForProvidedOperationAndComponent(source);

		if (flowInformation.getFirst().getSink().isEmpty()) {
			return new HashSet<String>();
		}

		List<ProgramPart> sources = transformer.transformSourcesOfFlowSpecToJOANAMethods(flowInformation.getFirst(),
				flowInformation.getSecond());
		List<ProgramPart> sinks = transformer.transformSinksOfFlowSpecToJOANASourcesFormat(flowInformation.getFirst(),
				flowInformation.getSecond());

		Flows joanaFlowsResults = sourceCodeAnalyzer.analyzeFlow(sources, sinks, classPath);

		// transform SourceCode Analysis Results back to PCM-Information
		// TODO: Potentially also possible to already create here SignatureIdentifying
		Collection<OperationSignature> pcmSinkSignatures = retrieveSinkSignatureIDsFromResultFlows(joanaFlowsResults,
				flowInformation);

		return retrieveOperationSignatureIDsFromOperationSignatures(pcmSinkSignatures);
	}

	private void generateSourceCodeAndJoanaModel(Repository repository) {
		pcm2SourceCode.processPCMRepo(repository);
		if (pcm2Joana == null) {
			this.resolver = new PCM2SourceCodeCorrespondenceResolver(pcm2SourceCode.getCorrespondences());
			this.pcm2Joana = new PCM2JOANAModelTransformator(this.resolver);
		}
	}

	private Collection<OperationSignature> retrieveSinkSignatureIDsFromResultFlows(Flows joanaFlowsResults,
			Pair<FlowSpecification, Associations> flowInformation) {
		Set<OperationSignature> pcmSinks = new HashSet<OperationSignature>();

		for (Entry<ProgramPart, Set<ProgramPart>> flow : joanaFlowsResults.flows().entrySet()) {

			Collection<ProgramPart> sinks = flow.getValue();
			for (ProgramPart joanaCLIMethod : sinks) {

				String methodId = flowInformation.getSecond().getId(joanaCLIMethod);
				if (methodId != null) {
					edu.kit.kastel.sdq.cosa.structure.SourceCode.Method sourceCodeMethod = resolver
							.getMethodById(methodId);
					pcmSinks.add(resolver.getOperationSignature(sourceCodeMethod));
				}
			}

		}
		return pcmSinks;
	}

	private Set<String> retrieveOperationSignatureIDsFromOperationSignatures(
			Collection<OperationSignature> signatures) {
		return signatures.stream().map(signature -> signature.getId()).collect(Collectors.toSet());
	}
}
