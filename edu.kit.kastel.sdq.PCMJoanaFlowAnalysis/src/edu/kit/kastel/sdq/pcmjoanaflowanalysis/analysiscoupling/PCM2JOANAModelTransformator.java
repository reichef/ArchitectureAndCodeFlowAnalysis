package edu.kit.kastel.sdq.pcmjoanaflowanalysis.analysiscoupling;

import JOANA.JOANARoot;
import JOANA.ParameterSink;
import JOANA.Source;
import JOANA.JOANAFactory;

import edu.kit.kastel.sdq.pcmjoanaflowanalysis.correspondences.PCM2SourceCodeCorrespondenceResolver;
import org.palladiosimulator.pcm.repository.OperationRequiredRole;
import JOANA.FlowSpecification;
import org.palladiosimulator.pcm.repository.OperationProvidedRole;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.palladiosimulator.pcm.repository.OperationSignature;
import org.palladiosimulator.pcm.repository.RepositoryComponent;
import org.palladiosimulator.pcm.repository.RequiredRole;

import java.util.Collection;
import java.util.HashSet;
import edu.kit.joana.component.connector.Method;
import edu.kit.ipd.sdq.composition.securityanalyses.coupling.structure.SourceCode.Class;
import edu.kit.ipd.sdq.composition.securityanalyses.coupling.structure.SourceCode.Interface;

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
		OperationProvidedRole providedRole, OperationSignature signature, RepositoryComponent component) {

		Associations associations = new Associations();
		FlowSpecification flowSpec = JOANAFactory.eINSTANCE.createFlowSpecification();
		flowSpec.setId(EcoreUtil.generateUUID());

		Source source = JOANAFactory.eINSTANCE.createSource();
		source.setId(EcoreUtil.generateUUID());
		source.setClass(resolver.getClass(component));
		source.setInterface(resolver.getInterface(providedRole.getProvidedInterface__OperationProvidedRole()));
		source.setMethod(resolver.getMethod(signature));

		Method joanaCLISource = new Method(source.getClass_().getName(), source.getMethod().getName());
		associations.associate(source.getMethod().getId(), joanaCLISource);

		flowSpec.getSource().add(source);

		Collection<Pair<String, Method>> sinks = transformBasicComponentRequiredToLocalSink(flowSpec, component);

		jRoot.getFlowspecification().add(flowSpec);

		sinks.stream().forEach(sink -> associations.associate(sink.getFirst(), sink.getSecond()));

		return new Pair<JOANA.FlowSpecification, Associations>(flowSpec, associations);
	}

	private Collection<Pair<String, Method>> transformBasicComponentRequiredToLocalSink(FlowSpecification flow,
		RepositoryComponent component) {

		Collection<Pair<String, Method>> joanaCLISinks = new HashSet<Pair<String, Method>>();

		Class scClass = resolver.getClass(component);
		for (RequiredRole requiredRole : component.getRequiredRoles_InterfaceRequiringEntity()) {
			if (requiredRole instanceof OperationRequiredRole) {
				for (OperationSignature signature : ((OperationRequiredRole)requiredRole).getRequiredInterface__OperationRequiredRole().getSignatures__OperationInterface()) {
					ParameterSink sink = JOANAFactory.eINSTANCE.createParameterSink();
					Interface scInterface = resolver.getInterface(((OperationRequiredRole)requiredRole).getRequiredInterface__OperationRequiredRole());
					edu.kit.ipd.sdq.composition.securityanalyses.coupling.structure.SourceCode.Method scMethod = resolver.getMethod(signature);

					if (!flow.getSource().get(0).getMethod().getId().equals(scMethod.getId())) {
						sink.setClass(scClass);
						sink.setInterface(scInterface);
						sink.setMethod(scMethod);

						flow.getSink().add(sink);

						Method joanaCLISink = new Method(scInterface.getName(), scMethod.getName());
						joanaCLISinks.add(new Pair<String, Method>(sink.getMethod().getId(), joanaCLISink));
					}
				}
			}
		}
		return joanaCLISinks;
	}
}
