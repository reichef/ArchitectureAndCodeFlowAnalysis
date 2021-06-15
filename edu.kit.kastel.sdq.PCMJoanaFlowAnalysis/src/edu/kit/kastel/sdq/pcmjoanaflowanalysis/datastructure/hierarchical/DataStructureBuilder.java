package edu.kit.kastel.sdq.pcmjoanaflowanalysis.datastructure.hierarchical;

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
import org.palladiosimulator.pcm.repository.Repository;
import org.palladiosimulator.pcm.repository.RepositoryComponent;
import org.palladiosimulator.pcm.repository.RequiredRole;
import org.palladiosimulator.pcm.system.System;

import edu.kit.kastel.sdq.ecoreannotations.Annotation;
import edu.kit.kastel.sdq.ecoreannotations.AnnotationRepository;
import edu.kit.kastel.sdq.ecoreannotations.GenericTargetStringContentAnnotation;

//TODO: Rapid Prototype, refactor into visitor possible.
public class DataStructureBuilder {

	private Collection<AssemblyComponentContext> workingRepresentations;
	private AnnotationRepository currentWorkingAnnotations;
	private Collection<FlowBasicComponent> basicComponents;

	public DataStructureBuilder() {
		workingRepresentations = new HashSet<AssemblyComponentContext>();
		basicComponents = new HashSet<>();
	}

	public SystemRepresentation buildRepresentationsForSystem(System system,
			AnnotationRepository annotationRepository) {

		currentWorkingAnnotations = annotationRepository;
		workingRepresentations.clear();

		SystemRepresentation systemRepresentation = new SystemRepresentation(system);

		Collection<AssemblyComponentContext> encapsulatedEntities = generateRepresentations(system);
		encapsulatedEntities
				.forEach(representation -> systemRepresentation.addContainedComponentRepresentation(representation));

		fillRepresentationWithCompositeConnectors(systemRepresentation, system);

		fillFlowBasicComponentsWithClassPath();

		// systemRepresentation.fillWithClassPath(annotationRepository,
		// Optional.empty());
//		systemRepresentation.printRepresentation();

		return systemRepresentation;
	}

	public Collection<AssemblyComponentContext> generateRepresentations(ComposedProvidingRequiringEntity entity) {
		Collection<AssemblyComponentContext> representations = new HashSet<AssemblyComponentContext>();
		// first Pass: generate representations from contexts
		for (AssemblyContext context : entity.getAssemblyContexts__ComposedStructure()) {
			representations.add(generateRepresentations(context));
		}

		// secondPass: generate Connectors
		for (AssemblyContext context : entity.getAssemblyContexts__ComposedStructure()) {
			Optional<AssemblyComponentContext> currentOptional = getEncapsulatingComponent(context);
			if (currentOptional.isPresent()) {
				fillRepresentationWithAssemblyConnectors(currentOptional.get(), context);
			}
		}

		return representations;
	}

	private Collection<AssemblyConnector> getAssemblyConnectorsForOperationRequiredRole(RequiredRole role,
			ComposedStructure parentContext) {
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

	private Collection<AssemblyConnector> getAssemblyConnectorsForOperationProvidedRole(ProvidedRole role,
			ComposedStructure parentContext) {
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

	private Optional<AssemblyComponentContext> getEncapsulatingComponent(AssemblyContext context) {
		for (AssemblyComponentContext representation : workingRepresentations) {
			if (representation.encapsulatesContext(context)) {
				return Optional.ofNullable(representation);
			}
		}
		return Optional.empty();
	}

	private AssemblyComponentContext generateRepresentations(AssemblyContext context) {

		AssemblyComponentContext representation = new AssemblyComponentContext(context);

		if (context.getEncapsulatedComponent__AssemblyContext() instanceof BasicComponent) {
			BasicComponent bc = (BasicComponent) context.getEncapsulatedComponent__AssemblyContext();
			representation.setComponent(getOrCreate(bc));
			workingRepresentations.add(representation);
		} else if (context.getEncapsulatedComponent__AssemblyContext() instanceof CompositeComponent) {

			CompositeComponent component = (CompositeComponent) context.getEncapsulatedComponent__AssemblyContext();

			Collection<AssemblyComponentContext> compositeRepresentations = generateRepresentations(component);
			compositeRepresentations.forEach(
					innerRepresentation -> representation.addContainedComponentRepresentation(innerRepresentation));
			workingRepresentations.add(representation);

			fillRepresentationWithCompositeConnectors(representation,
					(ComposedProvidingRequiringEntity) context.getEncapsulatedComponent__AssemblyContext());

		}
		fillAssemblyComponentWithAnnotations(representation);
		return representation;
	}

	private FlowBasicComponent getOrCreate(BasicComponent component) {

		Optional<FlowBasicComponent> potentialFlowComponent = basicComponents.stream()
				.filter(bc -> bc.getId().equals(component.getId())).findFirst();

		return potentialFlowComponent.orElse(createBasicFlowComponent(component));
	}

	private FlowBasicComponent createBasicFlowComponent(BasicComponent component) {
		FlowBasicComponent flowComponent = new FlowBasicComponent(component);
		basicComponents.add(flowComponent);
		return flowComponent;
	}

	private void fillRepresentationWithAssemblyConnectors(AssemblyComponentContext representation,
			AssemblyContext context) {

		for (RequiredRole role : context.getEncapsulatedComponent__AssemblyContext()
				.getRequiredRoles_InterfaceRequiringEntity()) {
			Collection<AssemblyConnector> connectors = getAssemblyConnectorsForOperationRequiredRole(role,
					context.getParentStructure__AssemblyContext());

			for (AssemblyConnector connector : connectors) {

				AssemblyComponentContext target = getEncapsulatingComponent(
						connector.getProvidingAssemblyContext_AssemblyConnector()).get();
				if (!representation.getId().equals(target.getId())) {
					AssemblyConnectorRepresentation connectorRepresentation = new AssemblyConnectorRepresentation(
							connector, representation, target);
					representation.addAssemblyConnector(connectorRepresentation);

				}
			}
		}

		for (ProvidedRole role : context.getEncapsulatedComponent__AssemblyContext()
				.getProvidedRoles_InterfaceProvidingEntity()) {
			Collection<AssemblyConnector> connectors = getAssemblyConnectorsForOperationProvidedRole(role,
					context.getParentStructure__AssemblyContext());

			for (AssemblyConnector connector : connectors) {
				AssemblyComponentContext target = getEncapsulatingComponent(
						connector.getProvidingAssemblyContext_AssemblyConnector()).get();

				if (!representation.getId().equals(target.getId())) {
					AssemblyConnectorRepresentation connectorRepresentation = new AssemblyConnectorRepresentation(
							connector, representation, target);
					representation.addAssemblyConnector(connectorRepresentation);

				}
			}
		}
	}

	private void fillRepresentationWithCompositeConnectors(AssemblyRepresentationContainer representation,
			ComposedProvidingRequiringEntity composedEntity) {

		for (Connector connector : composedEntity.getConnectors__ComposedStructure()) {
			if (connector instanceof ProvidedDelegationConnector) {
				ProvidedDelegationConnector tmp = (ProvidedDelegationConnector) connector;
				AssemblyComponentContext innerRepresentation = getEncapsulatingComponent(
						tmp.getAssemblyContext_ProvidedDelegationConnector()).get();

				CompositeConnectorRepresentation connectorRepresentation = new CompositeConnectorRepresentation(tmp,
						representation, innerRepresentation);

				representation.addCompositeConnector(connectorRepresentation);
				innerRepresentation.addCompositeConnector(connectorRepresentation);
			}

			if (connector instanceof RequiredDelegationConnector) {
				RequiredDelegationConnector tmp = (RequiredDelegationConnector) connector;
				AssemblyComponentContext innerRepresentation = getEncapsulatingComponent(
						tmp.getAssemblyContext_RequiredDelegationConnector()).get();

				CompositeConnectorRepresentation connectorRepresentation = new CompositeConnectorRepresentation(tmp,
						representation, innerRepresentation);

				representation.addCompositeConnector(connectorRepresentation);
				innerRepresentation.addCompositeConnector(connectorRepresentation);
			}

		}
	}

	private void fillAssemblyComponentWithAnnotations(AssemblyComponentContext representaition) {

		for (Annotation annotation : currentWorkingAnnotations.getAnnotations()) {
			if (annotation instanceof GenericTargetStringContentAnnotation) {
				GenericTargetStringContentAnnotation castedAnnotation = (GenericTargetStringContentAnnotation) annotation;
				if (!castedAnnotation.getAnnotationType().equals("ClassPath")) {
					if (castedAnnotation.getTarget() instanceof RepositoryComponent
							&& ((RepositoryComponent) castedAnnotation.getTarget()).getId().equals(
									representaition.getContext().getEncapsulatedComponent__AssemblyContext().getId())) {
						representaition.setAnnotation(castedAnnotation.getAnnotationType(),
								castedAnnotation.getContent());
					}
				}
			}
		}
	}

	private void fillFlowBasicComponentsWithClassPath() {
		for (Annotation annotation : currentWorkingAnnotations.getAnnotations()) {
			if (annotation instanceof GenericTargetStringContentAnnotation) {
				GenericTargetStringContentAnnotation castedAnnotation = (GenericTargetStringContentAnnotation) annotation;
				if (castedAnnotation.getAnnotationType().equals("ClassPath")) {
					for (FlowBasicComponent flowBasicComponent : basicComponents) {
						if (castedAnnotation.getTarget() instanceof RepositoryComponent
								&& ((RepositoryComponent) castedAnnotation.getTarget()).getId()
										.equals(flowBasicComponent.getId())) {
							flowBasicComponent.fillWithClassPath(currentWorkingAnnotations, Optional.of(((GenericTargetStringContentAnnotation) annotation).getContent()));
						}
					}
				}
			}
		}
	}
}
