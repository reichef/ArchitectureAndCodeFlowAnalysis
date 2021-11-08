package edu.kit.kastel.sdq.pcmjoanaflowanalysis.datastructure.refactored;

import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;

import org.eclipse.emf.ecore.EObject;
import org.palladiosimulator.pcm.core.composition.AssemblyConnector;
import org.palladiosimulator.pcm.core.composition.AssemblyContext;
import org.palladiosimulator.pcm.repository.BasicComponent;
import org.palladiosimulator.pcm.system.System;

import edu.kit.kastel.sdq.ecoreannotations.AnnotationRepository;
import edu.kit.kastel.sdq.pcmjoanaflowanalysis.datastructure.AbstractJoanaFlowDatastructureFactory;
import edu.kit.kastel.sdq.pcmjoanaflowanalysis.utils.AnnotationHelper;

public class PCMJoanaFlowDatastructureFactory extends AbstractJoanaFlowDatastructureFactory {
	
	private System system;
	private AnnotationRepository currentWorkingAnnotations;
	
	private final SystemVisitor systemVisitor;
	private final RepositoryVisitor repositoryVisitor;
	private final CompositionVisitor compositionVisitor;
	
	private Collection<AssemblyComponentContext> vertices;
	
	private AssemblyContext currentAssemblyContext;
	private BasicComponent currentBasicComponent;

	private AssemblyConnector currentAssemblyConnector;
	private AssemblyComponentContext currentRequiringVertex;
	private AssemblyComponentContext currentProvidingVertex;
	
	private PCMJoanaFlowDatastructureFactory() {
		systemVisitor = new SystemVisitor(this);
		repositoryVisitor = new RepositoryVisitor(this);
		compositionVisitor = new CompositionVisitor(this);
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
	
		Collection<AssemblyComponentContext> vertices = systemVisitor.doSwitch(system);	
		AnnotationHelper.fillFlowGraphRepresentationsWithAnnotations(vertices, currentWorkingAnnotations.getAnnotations());
		
		systemRepresentation.addVertices(vertices);
		
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
	
	Object visitCompositionElement(EObject object) {
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
