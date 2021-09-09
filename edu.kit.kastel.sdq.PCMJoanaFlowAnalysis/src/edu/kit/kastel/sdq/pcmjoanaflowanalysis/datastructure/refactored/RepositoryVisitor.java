package edu.kit.kastel.sdq.pcmjoanaflowanalysis.datastructure.refactored;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;

import org.palladiosimulator.pcm.core.composition.AssemblyContext;
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

	@Override
	public Collection<AssemblyComponentContext> caseBasicComponent(BasicComponent component) {
		factory.setCurrentBasicComponent(component);
		
		AssemblyComponentContext vertex = factory.createFlowGraphVertexRepresentation();

		return Arrays.asList(vertex);
	}

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

	@Override
	public Collection<AssemblyComponentContext> caseComposedProvidingRequiringEntity(ComposedProvidingRequiringEntity entity) {
		Collection<AssemblyComponentContext> vertices = new HashSet<AssemblyComponentContext>();
		for (AssemblyContext context : entity.getAssemblyContexts__ComposedStructure()) {
			factory.setCurrentAssemblyContext(context);
			
			RepositoryComponent component = context.getEncapsulatedComponent__AssemblyContext();
			vertices.addAll(doSwitch(component));
		}
		
		return vertices;
	}
	
}
