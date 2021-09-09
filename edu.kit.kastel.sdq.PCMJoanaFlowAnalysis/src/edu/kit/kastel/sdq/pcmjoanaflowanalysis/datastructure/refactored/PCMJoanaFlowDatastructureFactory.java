package edu.kit.kastel.sdq.pcmjoanaflowanalysis.datastructure.refactored;

import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;

import org.eclipse.emf.ecore.EObject;
import org.palladiosimulator.pcm.core.composition.AssemblyConnector;
import org.palladiosimulator.pcm.core.composition.AssemblyContext;
import org.palladiosimulator.pcm.core.composition.Connector;
import org.palladiosimulator.pcm.core.composition.ProvidedDelegationConnector;
import org.palladiosimulator.pcm.core.composition.RequiredDelegationConnector;
import org.palladiosimulator.pcm.repository.BasicComponent;
import org.palladiosimulator.pcm.repository.OperationRequiredRole;
import org.palladiosimulator.pcm.repository.RepositoryComponent;
import org.palladiosimulator.pcm.repository.RequiredRole;
import org.palladiosimulator.pcm.system.System;

import edu.kit.kastel.sdq.ecoreannotations.AnnotationRepository;
import edu.kit.kastel.sdq.pcmjoanaflowanalysis.datastructure.AbstractJoanaFlowDatastructureFactory;
import edu.kit.kastel.sdq.pcmjoanaflowanalysis.datastructure.FlowGraphEdge;
import edu.kit.kastel.sdq.pcmjoanaflowanalysis.datastructure.FlowGraphVertex;

public class PCMJoanaFlowDatastructureFactory extends AbstractJoanaFlowDatastructureFactory {
	
	private System system;
	private AnnotationRepository currentWorkingAnnotations;
	
	private final RepositoryVisitor repositoryVisitor;
	private final CompositionVisitor compositionVisitor;
	private final AnnotationsVisitor annotationsVisitor;
	
	private Collection<AssemblyComponentContext> vertices;
	
	private AssemblyContext currentAssemblyContext;
	private BasicComponent currentBasicComponent;

	private AssemblyConnector currentAssemblyConnector;
	private AssemblyComponentContext currentRequiringVertex;
	private AssemblyComponentContext currentProvidingVertex;
	
	private PCMJoanaFlowDatastructureFactory() {
		repositoryVisitor = new RepositoryVisitor(this);
		compositionVisitor = new CompositionVisitor(this);
		annotationsVisitor = new AnnotationsVisitor(this);
		vertices = new HashSet<>();
	}
	
	public PCMJoanaFlowDatastructureFactory(System system, AnnotationRepository annotationRepository) {
		this();
		this.system = system;
		this.currentWorkingAnnotations = annotationRepository;
	}

	@Override
	public SystemRepresentation createFlowGraphRepresentation() {
		vertices.clear();
		SystemRepresentation systemRepresentation = new SystemRepresentation(system);
	
		Collection<AssemblyComponentContext> vertices = repositoryVisitor.doSwitch(system);	
		systemRepresentation.addVertices(vertices);
		
		// Could be done in RepositoryVisitor.caseComposedProvidingRequiringEntity() as well.
		// However, doing it this way provides option to treat system connectors specifically.
		for (Connector connector : system.getConnectors__ComposedStructure()) {
			compositionVisitor.doSwitch(connector);
		}
		
		// TODO: Annotations "classpath" einfügen
		
		return systemRepresentation;
	}

	@Override
	public AssemblyComponentContext createFlowGraphVertexRepresentation() {
		if (currentAssemblyContext == null || currentBasicComponent == null 
				|| !currentAssemblyContext.getEncapsulatedComponent__AssemblyContext().getId().equals(currentBasicComponent.getId())) {
			return null;
		}
		
		FlowBasicComponent component = getOrCreateFlowBasicComponent(currentBasicComponent);
		AssemblyComponentContext context = new AssemblyComponentContext(currentAssemblyContext, component);
		
		vertices.add(context);
		
		return context;
	}

	@Override
	public AssemblyConnectorRepresentation createFlowGraphEdgeRepresentation() {
		if (currentRequiringVertex == null || currentProvidingVertex == null || currentAssemblyConnector == null) {
			return null;
		}
		
		return new AssemblyConnectorRepresentation(currentRequiringVertex, currentProvidingVertex, currentAssemblyConnector);
	}
	
	AssemblyContext getCurrentAssemblyContext() {
		return currentAssemblyContext;
	}

	void setCurrentAssemblyContext(AssemblyContext currentAssemblyContext) {
		this.currentAssemblyContext = currentAssemblyContext;
	}

	BasicComponent getCurrentBasicComponent() {
		return currentBasicComponent;
	}

	void setCurrentBasicComponent(BasicComponent currentBasicComponent) {
		this.currentBasicComponent = currentBasicComponent;
	}

	AssemblyConnector getCurrentAssemblyConnector() {
		return currentAssemblyConnector;
	}

	void setCurrentAssemblyConnector(AssemblyConnector currentAssemblyConnector) {
		this.currentAssemblyConnector = currentAssemblyConnector;
	}
	
	AssemblyComponentContext getCurrentRequiringVertex() {
		return currentRequiringVertex;
	}

	void setCurrentRequiringVertex(AssemblyComponentContext currentRequiringVertex) {
		this.currentRequiringVertex = currentRequiringVertex;
	}

	AssemblyComponentContext getCurrentProvidingVertex() {
		return currentProvidingVertex;
	}

	void setCurrentProvidingVertex(AssemblyComponentContext currentProvidingVertex) {
		this.currentProvidingVertex = currentProvidingVertex;
	}

	Collection<AssemblyComponentContext> visitRepositoryElement(EObject object) {
		return repositoryVisitor.doSwitch(object);
	}
	
	Collection<AssemblyComponentContext> visitCompositionElement(EObject object) {
		return compositionVisitor.doSwitch(object);
	}
	
	Optional<AssemblyComponentContext> getEncapsulatingComponent(AssemblyContext context) {
		return vertices.stream().filter(vertex -> vertex.getId().equals(context.getId())).findFirst();
	}

	private FlowBasicComponent getOrCreateFlowBasicComponent(BasicComponent component) {
		Optional<FlowBasicComponent> potentialFlowBasicComponent = 
				vertices.stream().map(AssemblyComponentContext::getComponent).filter(wrapped -> wrapped.getId().equals(component.getId())).findFirst();
		
		return potentialFlowBasicComponent.orElseGet(() -> createFlowBasicComponent(component));
	}
	
	private FlowBasicComponent createFlowBasicComponent(BasicComponent component) {
		return new FlowBasicComponent(component);
	}
	
	

}
