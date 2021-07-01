package edu.kit.kastel.sdq.pcmjoanaflowanalysis.analysiscoupling;

import edu.kit.kastel.sdq.cosa.quality.JOANA.FlowSpecification;
import edu.kit.kastel.sdq.cosa.quality.JOANA.JOANAFactory;
import edu.kit.kastel.sdq.cosa.quality.JOANA.JOANARoot;
import edu.kit.kastel.sdq.cosa.quality.JOANA.MethodTargetingSink;
import edu.kit.kastel.sdq.cosa.quality.JOANA.MethodTargetingSource;
import edu.kit.kastel.sdq.cosa.quality.JOANA.Source;
import edu.kit.kastel.sdq.cosa.structure.SourceCode.Interface;
import edu.kit.kastel.sdq.cosa.structure.SourceCode.Class;
import edu.kit.kastel.sdq.pcmjoanaflowanalysis.correspondences.PCM2SourceCodeCorrespondenceResolver;
import edu.kit.kastel.sdq.pcmjoanaflowanalysis.pcmflow.fixpoint.SystemOperationIdentifying;
import edu.kit.kastel.sdq.pcmjoanaflowanalysis.pcmutil.PCMRepositoryElementResolver;

import org.palladiosimulator.pcm.repository.OperationInterface;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.palladiosimulator.pcm.repository.OperationSignature;
import org.palladiosimulator.pcm.repository.RepositoryComponent;

import java.util.Collection;
import java.util.HashSet;
import edu.kit.joana.component.connector.Method;

public class PCM2JOANAModelTransformator {

	private final JOANARoot jRoot;

	public JOANARoot getjRoot() {
		return jRoot;
	}

	private final PCM2SourceCodeCorrespondenceResolver resolver;

	public PCM2JOANAModelTransformator(PCM2SourceCodeCorrespondenceResolver resolver) {
		this.resolver = resolver;
		jRoot = JOANAFactory.eINSTANCE.createJOANARoot();
	}

	public Pair<FlowSpecification, Associations> generateFlowForProvidedOperationAndComponent(
			SystemOperationIdentifying identifying) {

		Associations associations = new Associations();
		FlowSpecification flowSpec = JOANAFactory.eINSTANCE.createFlowSpecification();
		flowSpec.setId(EcoreUtil.generateUUID());

		RepositoryComponent sourcePCMComponent = identifying.getContext().getComponent().getComponent();
		MethodTargetingSource source = generateMethodTargetingSource(sourcePCMComponent, identifying.getOpInterface(),
				identifying.getSignature());

		Method joanaCLISource = new Method(source.getClass_().getEntityName(), source.getMethod().getEntityName());
		associations.associate(source.getMethod().getId(), joanaCLISource);

		flowSpec.getSource().add(source);

		// Generate Sinks
		Collection<Pair<String, Method>> sinks = generateLocalSinksForComponent(flowSpec, sourcePCMComponent);

		jRoot.getFlowspecification().add(flowSpec);

		sinks.stream().forEach(sink -> associations.associate(sink.getFirst(), sink.getSecond()));

		return new Pair<FlowSpecification, Associations>(flowSpec, associations);
	}

	private Collection<Pair<String, Method>> generateLocalSinksForComponent(FlowSpecification flow,
			RepositoryComponent component) {

		Collection<Pair<String, Method>> joanaCLISinks = new HashSet<Pair<String, Method>>();
		Collection<OperationSignature> operations = PCMRepositoryElementResolver
				.getAllOperationSignaturesOfComponent(component);

		for (OperationSignature signature : operations) {
			for (Source source : flow.getSource()) {
				// Initially we only consider methods as sources
				if (source instanceof MethodTargetingSource) {
					
					MethodTargetingSource mtSource = ((MethodTargetingSource) source);
					MethodTargetingSink sink = generateMethodTargetingSink(component, signature.getInterface__OperationSignature(), signature);
					
					if (!isSourceAndSinkEqual(mtSource, sink)) {
						flow.getSink().add(sink);
						
						Method joanaCLISink = new Method(sink.getInterface().getEntityName(), sink.getMethod().getEntityName());
						joanaCLISinks.add(new Pair<String, Method>(sink.getMethod().getId(), joanaCLISink));
					}
				}
			}
		}
		return joanaCLISinks;
	}

	private MethodTargetingSink generateMethodTargetingSink(Class scClass, Interface scInterface,
			edu.kit.kastel.sdq.cosa.structure.SourceCode.Method scMethod) {

		MethodTargetingSink sink = JOANAFactory.eINSTANCE.createMethodTargetingSink();

		sink.setId(EcoreUtil.generateUUID());
		sink.setClass(scClass);
		sink.setInterface(scInterface);
		sink.setMethod(scMethod);

		return sink;
	}

	private MethodTargetingSource generateMethodTargetingSource(RepositoryComponent component, OperationInterface iface,
			OperationSignature signature) {
		return generateMethodTargetingSource(resolver.getClass(component), resolver.getInterface(iface),
				resolver.getMethod(signature));
	}
	
	private MethodTargetingSink generateMethodTargetingSink(RepositoryComponent component, OperationInterface iface, OperationSignature signature) {
		return generateMethodTargetingSink(resolver.getClass(component), resolver.getInterface(iface), resolver.getMethod(signature));
	}

	private MethodTargetingSource generateMethodTargetingSource(Class scClass, Interface scInterface,
			edu.kit.kastel.sdq.cosa.structure.SourceCode.Method scMethod) {
		MethodTargetingSource source = JOANAFactory.eINSTANCE.createMethodTargetingSource();
		source.setId(EcoreUtil.generateUUID());
		source.setClass(scClass);
		source.setInterface(scInterface);
		source.setMethod(scMethod);

		return source;
	}

	private boolean isSourceAndSinkEqual(MethodTargetingSource source, MethodTargetingSink sink) {
		return source.getClass_().getId().equals(sink.getClass_().getId())
				&& source.getInterface().getId().equals(sink.getInterface().getId())
				&& source.getMethod().getId().equals(sink.getMethod().getId());
	}

}
