package edu.kit.kastel.sdq.pcmjoanaflowanalysis.Datastructure;

import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;

import org.palladiosimulator.pcm.core.composition.AssemblyConnector;
import org.palladiosimulator.pcm.core.composition.AssemblyContext;
import org.palladiosimulator.pcm.core.composition.ComposedStructure;
import org.palladiosimulator.pcm.core.composition.Connector;
import org.palladiosimulator.pcm.core.composition.ProvidedDelegationConnector;
import org.palladiosimulator.pcm.core.composition.RequiredDelegationConnector;
import org.palladiosimulator.pcm.core.entity.ComposedProvidingRequiringEntity;
import org.palladiosimulator.pcm.repository.BasicComponent;
import org.palladiosimulator.pcm.repository.CompositeComponent;
import org.palladiosimulator.pcm.repository.OperationProvidedRole;
import org.palladiosimulator.pcm.repository.OperationRequiredRole;
import org.palladiosimulator.pcm.repository.ProvidedRole;
import org.palladiosimulator.pcm.repository.RepositoryComponent;
import org.palladiosimulator.pcm.repository.RequiredRole;
import org.palladiosimulator.pcm.system.System;

import edu.kit.kastel.sdq.ecoreannotations.Annotation;
import edu.kit.kastel.sdq.ecoreannotations.AnnotationRepository;
import edu.kit.kastel.sdq.ecoreannotations.GenericTargetStringContentAnnotation;

public class DataStructureBuilder {

	private Collection<AssemblyComponent> workingRepresentations;
	private AnnotationRepository currentWorkingAnnotations;

	public DataStructureBuilder() {
		workingRepresentations = new HashSet<AssemblyComponent>();
	}

	public TopmostAssemblyEntity buildRepresentationsForSystem(System system, AnnotationRepository annotationRepository) {
		
		currentWorkingAnnotations = annotationRepository;
		
		TopmostAssemblyEntity systemRepresentation = new TopmostAssemblyEntity(system);
		
		Collection<AssemblyComponent> encapsulatedEntities = generateRepresentations(system);
		encapsulatedEntities.forEach(representation -> systemRepresentation.addContainedComponentRepresentation(representation));
		
		fillRepresentationWithCompositeConnectors(systemRepresentation, system);
		systemRepresentation.fillWithClassPath(annotationRepository, Optional.empty());
		systemRepresentation.printRepresentation();
		
		
		
		workingRepresentations.clear();
		currentWorkingAnnotations = null;
		return systemRepresentation;
	}

	private Collection<AssemblyComponent> generateRepresentations(ComposedProvidingRequiringEntity entity) {
		Collection<AssemblyComponent> representations = new HashSet<AssemblyComponent>();
		// first Pass: generate representations from contexts
		for (AssemblyContext context : entity.getAssemblyContexts__ComposedStructure()) {
			representations.add(generateRepresentations(context));
		}

		// secondPass: generate Connectors
		for (AssemblyContext context : entity.getAssemblyContexts__ComposedStructure()) {
			Optional<AssemblyComponent> currentOptional = getEncapsulatingComponent(context);
			if (currentOptional.isPresent()) {
				fillRepresentationWithAssemblyConnectors(currentOptional.get(), context);
			}
		}

		return representations;
	}

	private Collection<AssemblyConnector> getAssemblyConnectorsForOperationRequiredRole(RequiredRole role, ComposedStructure parentContext) {
		Collection<AssemblyConnector> connectors = new HashSet<AssemblyConnector>();

		if (role instanceof OperationRequiredRole) {
			for (Connector connector : parentContext.getConnectors__ComposedStructure()) {
				if (connector instanceof AssemblyConnector) {
					AssemblyConnector tmp = (AssemblyConnector) connector;
					if (tmp.getRequiredRole_AssemblyConnector().getId().equals(role.getId())) {
						connectors.add(tmp);
					}
				}
			}
		}

		return connectors;
	}

	private Collection<AssemblyConnector> getAssemblyConnectorsForOperationProvidedRole(ProvidedRole role, ComposedStructure parentContext) {
		Collection<AssemblyConnector> connectors = new HashSet<AssemblyConnector>();
		if (role instanceof OperationProvidedRole) {
			for (Connector connector : parentContext.getConnectors__ComposedStructure()) {
				if (connector instanceof AssemblyConnector) {
					AssemblyConnector tmp = (AssemblyConnector) connector;
					if (tmp.getProvidedRole_AssemblyConnector().getId().equals(role.getId())) {
						connectors.add(tmp);
					}
				}
			}
		}

		return connectors;
	}

	private Optional<AssemblyComponent> getEncapsulatingComponent(AssemblyContext context) {
		for (AssemblyComponent representation : workingRepresentations) {
			if (representation.encapsulatesContext(context)) {
				return Optional.ofNullable(representation);
			}
		}
		return Optional.empty();
	}

	private AssemblyComponent generateRepresentations(AssemblyContext context) {

		AssemblyComponent representation = new AssemblyComponent(context);
		if (context.getEncapsulatedComponent__AssemblyContext() instanceof BasicComponent) {
			workingRepresentations.add(representation);
		} else if (context.getEncapsulatedComponent__AssemblyContext() instanceof CompositeComponent) {
		
			CompositeComponent component = (CompositeComponent) context.getEncapsulatedComponent__AssemblyContext();

			Collection<AssemblyComponent> compositeRepresentations = generateRepresentations(component);
			compositeRepresentations.forEach(
					innerRepresentation -> representation.addContainedComponentRepresentation(innerRepresentation));
			workingRepresentations.add(representation);

			fillRepresentationWithCompositeConnectors(representation, (ComposedProvidingRequiringEntity) context.getEncapsulatedComponent__AssemblyContext());

		}
		fillAssemblyComponentWithAnnotations(representation);
		return representation;
	}

	private void fillRepresentationWithAssemblyConnectors(AssemblyComponent representation, AssemblyContext context) {

		for (RequiredRole role : context.getEncapsulatedComponent__AssemblyContext()
				.getRequiredRoles_InterfaceRequiringEntity()) {
			Collection<AssemblyConnector> connectors = getAssemblyConnectorsForOperationRequiredRole(role, context.getParentStructure__AssemblyContext());

			for (AssemblyConnector connector : connectors) {

				AssemblyComponent target = getEncapsulatingComponent(
						connector.getProvidingAssemblyContext_AssemblyConnector()).orElseThrow();
				if (!representation.getId().equals(target.getId())) {
					AssemblyConnectorRepresentation connectorRepresentation = new AssemblyConnectorRepresentation(
							connector, representation, target);
					representation.addAssemblyConnector(connectorRepresentation);
				
				}
			}
		}

		for (ProvidedRole role : context.getEncapsulatedComponent__AssemblyContext()
				.getProvidedRoles_InterfaceProvidingEntity()) {
			Collection<AssemblyConnector> connectors = getAssemblyConnectorsForOperationProvidedRole(role, context.getParentStructure__AssemblyContext());

			for (AssemblyConnector connector : connectors) {
				AssemblyComponent target = getEncapsulatingComponent(
						connector.getProvidingAssemblyContext_AssemblyConnector()).orElseThrow();

				if (!representation.getId().equals(target.getId())) {
					AssemblyConnectorRepresentation connectorRepresentation = new AssemblyConnectorRepresentation(
							connector, representation, target);
					representation.addAssemblyConnector(connectorRepresentation);
					
				}
			}
		}
	}

	private void fillRepresentationWithCompositeConnectors(AssemblyRepresentationContainer representation, ComposedProvidingRequiringEntity composedEntity) {
	
			for (Connector connector : composedEntity.getConnectors__ComposedStructure()) {
				if (connector instanceof ProvidedDelegationConnector) {
					ProvidedDelegationConnector tmp = (ProvidedDelegationConnector) connector;
					AssemblyComponent innerRepresentation = getEncapsulatingComponent(
							tmp.getAssemblyContext_ProvidedDelegationConnector()).orElseThrow();

					CompositeConnectorRepresentation connectorRepresentation = new CompositeConnectorRepresentation(tmp,
							representation, innerRepresentation);
					
					representation.addCompositeConnector(connectorRepresentation);
				}

				if (connector instanceof RequiredDelegationConnector) {
					RequiredDelegationConnector tmp = (RequiredDelegationConnector) connector;
					AssemblyComponent innerRepresentation = getEncapsulatingComponent(
							tmp.getAssemblyContext_RequiredDelegationConnector()).orElseThrow();

					CompositeConnectorRepresentation connectorRepresentation = new CompositeConnectorRepresentation(tmp,
							representation, innerRepresentation);
				
					representation.addCompositeConnector(connectorRepresentation);
				}
			
		}
	}
	
	private void fillAssemblyComponentWithAnnotations(AssemblyComponent representaition) {
		
		for(Annotation annotation : currentWorkingAnnotations.getAnnotations()) {
			if(annotation instanceof GenericTargetStringContentAnnotation) {
				GenericTargetStringContentAnnotation castedAnnotation = (GenericTargetStringContentAnnotation)annotation;
				
				if(castedAnnotation.getTarget() instanceof RepositoryComponent &&
						((RepositoryComponent)castedAnnotation.getTarget()).getId().equals(representaition.getContext().getEncapsulatedComponent__AssemblyContext().getId())) {
					representaition.setAnnotation(castedAnnotation.getAnnotationType(), castedAnnotation.getContent());
				}
			}
		}
		
	}
}
