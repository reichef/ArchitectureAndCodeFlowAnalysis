package edu.kit.kastel.sdq.pcmjoanaflowanalysis.datastructure.refactored;

import java.util.Collection;
import java.util.HashSet;

import org.palladiosimulator.pcm.core.composition.AssemblyContext;
import org.palladiosimulator.pcm.core.composition.ComposedStructure;
import org.palladiosimulator.pcm.core.composition.Connector;
import org.palladiosimulator.pcm.core.entity.ComposedProvidingRequiringEntity;
import org.palladiosimulator.pcm.repository.BasicComponent;
import org.palladiosimulator.pcm.repository.CompositeComponent;
import org.palladiosimulator.pcm.repository.RepositoryComponent;
import org.palladiosimulator.pcm.repository.util.RepositorySwitch;

class RepositoryVisitor extends RepositorySwitch<Collection<AssemblyComponentContext>> {
	
	private final PCMJoanaFlowDatastructureFactory factory;

	RepositoryVisitor(PCMJoanaFlowDatastructureFactory factory) {
		this.factory = factory;
	}

	/**
	 * <p>Base-case of the recursion. {@link BasicComponent}s are of interest for the information-flow analysis and must therefore
	 * be represented as vertex in the graph.</p>
	 */
	@Override
	public Collection<AssemblyComponentContext> caseBasicComponent(BasicComponent component) {
		factory.setCurrentBasicComponent(component);
		
		Collection<AssemblyComponentContext> vertices = new HashSet<>(1);
		
		AssemblyComponentContext vertex = factory.createFlowGraphVertexRepresentation();
		if (vertex != null) {
			vertices.add(vertex);
		}
		
		return vertices;
	}

	/**
	 * <p>{@link CompositeComponent}s build up the hierarchical structure of the model and are not represented in the graph.
	 * They need to be resolved, i.e. all inner components and connectors need to be processed.</p>
	 * 
	 * @see RepositoryVisitor#caseComposedStructure(ComposedStructure)
	 */
	@Override
	public Collection<AssemblyComponentContext> caseCompositeComponent(CompositeComponent component) {
		AssemblyContext context = factory.getCurrentAssemblyContext();
		
		Collection<AssemblyComponentContext> vertices = caseComposedProvidingRequiringEntity((ComposedProvidingRequiringEntity) component);
		
		factory.setCurrentAssemblyContext(context);
		
		for (Connector connector : component.getConnectors__ComposedStructure()) {
			factory.visitCompositionElement(connector);
		}
		
		return vertices;
	}

	/**
	 * <p>Processing of a the hierarchical structured {@link ComposedStructure}. Inner {@link RepositoryComponent}s are processed recursively.</p>
	 */
	@Override
	public Collection<AssemblyComponentContext> caseComposedStructure(ComposedStructure entity) {
		Collection<AssemblyComponentContext> vertices = new HashSet<AssemblyComponentContext>();
		for (AssemblyContext context : entity.getAssemblyContexts__ComposedStructure()) {
			factory.setCurrentAssemblyContext(context);
			
			RepositoryComponent component = context.getEncapsulatedComponent__AssemblyContext();
			vertices.addAll(doSwitch(component));
		}
		
		return vertices;
	}
	
	
	
}
