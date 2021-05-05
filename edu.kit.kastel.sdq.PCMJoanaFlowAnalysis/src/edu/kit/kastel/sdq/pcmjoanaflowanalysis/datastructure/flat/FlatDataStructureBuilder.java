package edu.kit.kastel.sdq.pcmjoanaflowanalysis.datastructure.flat;

import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;

import org.palladiosimulator.pcm.core.composition.AssemblyConnector;
import org.palladiosimulator.pcm.core.composition.AssemblyContext;
import org.palladiosimulator.pcm.core.composition.DelegationConnector;
import org.palladiosimulator.pcm.core.composition.ProvidedDelegationConnector;
import org.palladiosimulator.pcm.core.entity.ComposedProvidingRequiringEntity;
import org.palladiosimulator.pcm.repository.BasicComponent;
import org.palladiosimulator.pcm.repository.CompositeComponent;
import org.palladiosimulator.pcm.repository.OperationProvidedRole;
import org.palladiosimulator.pcm.repository.Repository;
import org.palladiosimulator.pcm.repository.RepositoryComponent;
import org.palladiosimulator.pcm.system.System;

import edu.kit.kastel.sdq.ecoreannotations.AnnotationRepository;
import edu.kit.kastel.sdq.ecoreannotations.GenericTargetStringContentAnnotation;
import edu.kit.kastel.sdq.pcmjoanaflowanalysis.StringPair;
import edu.kit.kastel.sdq.pcmjoanaflowanalysis.datastructure.FlowBasicComponent;
import edu.kit.kastel.sdq.pcmjoanaflowanalysis.datastructure.hierarchical.AssemblyComponentContext;
import edu.kit.kastel.sdq.pcmjoanaflowanalysis.datastructure.hierarchical.SystemRepresentation;
import edu.kit.kastel.sdq.pcmjoanaflowanalysis.pcmutil.ModelsSubtypeResolver;
import edu.kit.kastel.sdq.pcmjoanaflowanalysis.pcmutil.PCMRepositoryElementResolver;



public class FlatDataStructureBuilder {

	private Collection<Context> workingRepresentations;
	private Collection<AssemblyConnectorRepresentation> connectors;
	private AnnotationRepository currentWorkingAnnotations;
	private Collection<FlowBasicComponent> basicComponents;
	private System workingSystem;
	
	public FlatDataStructureBuilder() {
		workingRepresentations = new HashSet<Context>();
		basicComponents = new HashSet<FlowBasicComponent>();
	}
	
	public Collection<FlowBasicComponent> buildFlowBasicComponents(Repository repository, AnnotationRepository annotationRepo){
		currentWorkingAnnotations = annotationRepo;
		
		Collection<BasicComponent> basicComponents = ModelsSubtypeResolver.filterBasicComponents(repository.getComponents__Repository());
		Collection<FlowBasicComponent> flowComponents = new HashSet<FlowBasicComponent>();
		
		basicComponents.forEach(basicComponent -> flowComponents.add(new FlowBasicComponent(basicComponent)));
		
		flowComponents.forEach(flowComponent -> addAnnotationsToFlowComponent(flowComponent));
		
		return flowComponents;
		
	}

	private void addAnnotationsToFlowComponent(FlowBasicComponent flowComponent) {
		Collection<GenericTargetStringContentAnnotation> annotations = ModelsSubtypeResolver.filterGenericTargetStringContentAnnotations(currentWorkingAnnotations);
		
		for(GenericTargetStringContentAnnotation annotation : annotations) {
			if(annotation.getTarget() instanceof BasicComponent && ((BasicComponent)annotation.getTarget()).equals(flowComponent.getComponent())) {
				flowComponent.setAnnotation(annotation.getAnnotationType(), annotation.getContent());
			}
		}
	}
	
	
	private void buildStructure(System system) {
		this.workingSystem = system;
		
		//TODO Start building here;
	}
	
	private void generateContexts(ComposedProvidingRequiringEntity entity) {
		entity.getAssemblyContexts__ComposedStructure().forEach(context -> generateContexts(context));
	}

	private void generateContexts(AssemblyContext context) {
		if(context.getEncapsulatedComponent__AssemblyContext() instanceof BasicComponent) {
			FlowBasicComponent correspondingFlowComponent = findFlowBasicComponentForBasicComponent((BasicComponent)context.getEncapsulatedComponent__AssemblyContext());
			workingRepresentations.add(new Context(context, correspondingFlowComponent));
		} else if(context.getEncapsulatedComponent__AssemblyContext() instanceof CompositeComponent){
			generateContexts((CompositeComponent)context.getEncapsulatedComponent__AssemblyContext());
		}
	}
	
	private FlowBasicComponent findFlowBasicComponentForBasicComponent(BasicComponent component) {
		return basicComponents.stream().filter(flowComponent -> flowComponent.getComponent().equals(component)).findFirst().get();
	}
	
	private void createConnectors(ComposedProvidingRequiringEntity entity) {
		ModelsSubtypeResolver.filterAssemblyConnector(entity.getConnectors__ComposedStructure()).forEach(connector -> connectors.add(createConnectors(connector)));
	}

	// Assumptions: For Simplicity At first we do not have an assemblyconnector between two composite components
	private AssemblyConnectorRepresentation createConnectors(AssemblyConnector connector) {
		if(isBothComposite(connector)) {
			return null;
		} else if(isProvidingComposite(connector)) {
			StringPair providingEndPoint = traverseProvidingCompositeAssembly(connector);
			Context providingContext = findContext(providingEndPoint.getFirst());
			Context requiringContext = findContext(connector.getRequiringAssemblyContext_AssemblyConnector());
			return new AssemblyConnectorRepresentation(providingContext, requiringContext, connector.getProvidedRole_AssemblyConnector().getId(), providingEndPoint.getSecond());
		} else if (isRequiringComposite(connector)) {
			//TODO Implement Traversal of Requiring Delegates
		} else {
			return new AssemblyConnectorRepresentation(findContext(connector.getProvidingAssemblyContext_AssemblyConnector()), findContext(connector.getRequiringAssemblyContext_AssemblyConnector()), connector);
		}
		
		return null;
	}
	
	
	
	private StringPair traverseProvidingCompositeAssembly(AssemblyConnector connector) {
		StringPair providingInfo = null;
		
		if(isProvidingComposite(connector)) {
			
			CompositeComponent providingComposite = ((CompositeComponent)connector.getProvidingAssemblyContext_AssemblyConnector().getEncapsulatedComponent__AssemblyContext());
			ProvidedDelegationConnector correspondingDelegationConnector = getDelegationConnectorForOperationProvidedRoleForCompositeComponent(providingComposite, connector.getProvidedRole_AssemblyConnector());
			
			providingInfo = traverseProvidingDelegates(correspondingDelegationConnector);
			
		}
		
		
		
		return providingInfo;
	}
	
	private StringPair traverseProvidingDelegates(ProvidedDelegationConnector connector) {
		AssemblyContext innerContext = PCMRepositoryElementResolver.getInnerAssemblyContextForOperationProvidedRole(connector.getParentStructure__Connector(), connector.getInnerProvidedRole_ProvidedDelegationConnector());
		
		if(isComposite(innerContext.getEncapsulatedComponent__AssemblyContext())) {
			ProvidedDelegationConnector delegationConnector = getDelegationConnectorForOperationProvidedRoleForCompositeComponent((CompositeComponent)innerContext.getEncapsulatedComponent__AssemblyContext(), connector.getInnerProvidedRole_ProvidedDelegationConnector());
			return traverseProvidingDelegates(delegationConnector);
		} else {
			return new StringPair(innerContext.getId(), connector.getInnerProvidedRole_ProvidedDelegationConnector().getId());
		}
	}
	
	
	
	
	
	

	private ProvidedDelegationConnector getDelegationConnectorForOperationProvidedRoleForCompositeComponent(ComposedProvidingRequiringEntity entity, OperationProvidedRole role) {
		Collection<ProvidedDelegationConnector> connectors = ModelsSubtypeResolver.filterProvidedDelegationConnectors(entity.getConnectors__ComposedStructure());
		return  connectors.stream().filter(delegation -> delegation.getOuterProvidedRole_ProvidedDelegationConnector().getId().equals(role.getId())).findFirst().get();
		
	}

	private boolean isComposite(RepositoryComponent component) {
		return component instanceof CompositeComponent;
	}

	private Context findContext(AssemblyContext assemblyContext) {
		return findContext(assemblyContext.getId());
	}
	
	private Context findContext(String contextId) {
		return workingRepresentations.stream().filter(context -> context.getId().equals(contextId)).findAny().get();
	}

	private boolean isRequiringComposite(AssemblyConnector connector) {
		return !(isComposite(connector.getProvidingAssemblyContext_AssemblyConnector().getEncapsulatedComponent__AssemblyContext()) && isComposite(connector.getProvidingAssemblyContext_AssemblyConnector().getEncapsulatedComponent__AssemblyContext()));
	}

	private boolean isProvidingComposite(AssemblyConnector connector) {
		return isComposite(connector.getProvidingAssemblyContext_AssemblyConnector().getEncapsulatedComponent__AssemblyContext()) && !(isComposite(connector.getProvidingAssemblyContext_AssemblyConnector().getEncapsulatedComponent__AssemblyContext()));
	}

	private boolean isBothComposite(AssemblyConnector connector) {
		return isComposite(connector.getProvidingAssemblyContext_AssemblyConnector().getEncapsulatedComponent__AssemblyContext()) && isComposite(connector.getProvidingAssemblyContext_AssemblyConnector().getEncapsulatedComponent__AssemblyContext());
	}
}
