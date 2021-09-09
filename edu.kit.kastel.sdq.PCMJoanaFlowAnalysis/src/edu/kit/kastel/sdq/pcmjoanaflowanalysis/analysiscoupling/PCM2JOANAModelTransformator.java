package edu.kit.kastel.sdq.pcmjoanaflowanalysis.analysiscoupling;


import edu.kit.kastel.sdq.cosa.quality.JOANA.FlowSpecification;
import edu.kit.kastel.sdq.cosa.quality.JOANA.JOANAFactory;
import edu.kit.kastel.sdq.cosa.quality.JOANA.JOANARoot;
import edu.kit.kastel.sdq.cosa.quality.JOANA.MethodTargetingSink;
import edu.kit.kastel.sdq.cosa.quality.JOANA.MethodTargetingSource;
import edu.kit.kastel.sdq.cosa.structure.SourceCode.Interface;
import edu.kit.kastel.sdq.cosa.structure.SourceCode.Class;
import edu.kit.kastel.sdq.pcmjoanaflowanalysis.correspondences.PCM2SourceCodeCorrespondenceResolver;
import edu.kit.kastel.sdq.pcmjoanaflowanalysis.pcmutil.PCMSubtypeResolver;
import edu.kit.kastel.sdq.pcmjoanaflowanalysis.utils.Pair;

import org.palladiosimulator.pcm.repository.OperationRequiredRole;

import org.palladiosimulator.pcm.repository.OperationInterface;
import org.palladiosimulator.pcm.repository.OperationProvidedRole;
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
		OperationProvidedRole providedRole, OperationSignature signature, RepositoryComponent component) {

		Associations associations = new Associations();
		FlowSpecification flowSpec = JOANAFactory.eINSTANCE.createFlowSpecification();
		flowSpec.setId(EcoreUtil.generateUUID());

	
		MethodTargetingSource source = generateMethodTargetingSource(component, providedRole.getProvidedInterface__OperationProvidedRole(), signature);

		Method joanaCLISource = new Method(source.getClass_().getEntityName(), source.getMethod().getEntityName());
		associations.associate(source.getMethod().getId(), joanaCLISource);

		flowSpec.getSource().add(source);

		//Generate Sinks
		Collection<Pair<String, Method>> sinks = transformBasicComponentRequiredToLocalSink(flowSpec, component);

		jRoot.getFlowspecification().add(flowSpec);

		sinks.stream().forEach(sink -> associations.associate(sink.getFirst(), sink.getSecond()));

		return new Pair<FlowSpecification, Associations>(flowSpec, associations);
	}

	private Collection<Pair<String, Method>> transformBasicComponentRequiredToLocalSink(FlowSpecification flow,
		RepositoryComponent component) {

		Collection<Pair<String, Method>> joanaCLISinks = new HashSet<Pair<String, Method>>();

		Class scClass = resolver.getClass(component);
		Collection<OperationRequiredRole> opReqRoles = PCMSubtypeResolver.filterOperationRequiredRoles(component.getRequiredRoles_InterfaceRequiringEntity());
		for (OperationRequiredRole requiredRole : opReqRoles) {
				for (OperationSignature signature : requiredRole.getRequiredInterface__OperationRequiredRole().getSignatures__OperationInterface()) {
					//MethodTargetingSink sink = JOANAFactory.eINSTANCE.createMethodTargetingSink();
					Interface scInterface = resolver.getInterface(requiredRole.getRequiredInterface__OperationRequiredRole());
					edu.kit.kastel.sdq.cosa.structure.SourceCode.Method scMethod = resolver.getMethod(signature);

					boolean isSource = flow.getSource().get(0) instanceof MethodTargetingSource;
					
					if (isSource) {
						MethodTargetingSource mtSource = ((MethodTargetingSource)flow.getSource().get(0));
						boolean methodIdOfSourceEqualsFoundMethod = mtSource.getMethod().getId().equals(scMethod.getId());
						if (!methodIdOfSourceEqualsFoundMethod) {
						MethodTargetingSink sink = generateMethodTargetingSink(scClass, scInterface, scMethod);

						flow.getSink().add(sink);

						Method joanaCLISink = new Method(scInterface.getEntityName(), scMethod.getEntityName());
						joanaCLISinks.add(new Pair<String, Method>(sink.getMethod().getId(), joanaCLISink));
						}
					}
				}
		}
		return joanaCLISinks;
	}
	
	private MethodTargetingSink generateMethodTargetingSink(RepositoryComponent component, OperationInterface iface, OperationSignature signature) {
		return generateMethodTargetingSink(resolver.getClass(component), resolver.getInterface(iface), resolver.getMethod(signature));
	}
	
	private MethodTargetingSink generateMethodTargetingSink(Class scClass, Interface scInterface, edu.kit.kastel.sdq.cosa.structure.SourceCode.Method scMethod) {
		
		MethodTargetingSink sink = JOANAFactory.eINSTANCE.createMethodTargetingSink();
		
		sink.setId(EcoreUtil.generateUUID());
		sink.setClass(scClass);
		sink.setInterface(scInterface);
		sink.setMethod(scMethod);
		
		return sink;
	}
	
	private MethodTargetingSource generateMethodTargetingSource(RepositoryComponent component, OperationInterface iface, OperationSignature signature) {
		return generateMethodTargetingSource(resolver.getClass(component), resolver.getInterface(iface), resolver.getMethod(signature));
	}
	
	private MethodTargetingSource generateMethodTargetingSource(Class scClass, Interface scInterface, edu.kit.kastel.sdq.cosa.structure.SourceCode.Method scMethod) {
		MethodTargetingSource source = JOANAFactory.eINSTANCE.createMethodTargetingSource();
		source.setId(EcoreUtil.generateUUID());
		source.setClass(scClass);
		source.setInterface(scInterface);
		source.setMethod(scMethod);
		
		return source;
	}
	
	
}
