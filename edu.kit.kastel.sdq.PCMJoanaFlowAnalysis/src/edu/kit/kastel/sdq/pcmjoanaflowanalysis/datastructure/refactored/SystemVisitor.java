package edu.kit.kastel.sdq.pcmjoanaflowanalysis.datastructure.refactored;

import java.util.Collection;
import java.util.HashSet;

import org.eclipse.emf.ecore.EObject;
import org.palladiosimulator.pcm.core.composition.AssemblyContext;
import org.palladiosimulator.pcm.core.composition.Connector;
import org.palladiosimulator.pcm.repository.RepositoryComponent;
import org.palladiosimulator.pcm.system.System;
import org.palladiosimulator.pcm.system.util.SystemSwitch;

public class SystemVisitor extends SystemSwitch<Collection<AssemblyComponentContext>> {

	private final PCMJoanaFlowDatastructureFactory factory;

	SystemVisitor(PCMJoanaFlowDatastructureFactory factory) {
		this.factory = factory;
	}

	@Override
	public Collection<AssemblyComponentContext> caseSystem(System system) {
		Collection<AssemblyComponentContext> vertices = new HashSet<AssemblyComponentContext>();
		
		// construction of the vertices
		for (AssemblyContext context : system.getAssemblyContexts__ComposedStructure()) {
			factory.setCurrentAssemblyContext(context);
			
			RepositoryComponent component = context.getEncapsulatedComponent__AssemblyContext();
			vertices.addAll(factory.visitRepositoryElement(component));
		}
		
		// construction of the edges
		for (Connector connector : system.getConnectors__ComposedStructure()) {
			factory.visitCompositionElement(connector);
		}
		
		return vertices;
	}

	@Override
	public Collection<AssemblyComponentContext> defaultCase(EObject object) {
		java.lang.System.err.printf("Unhandled System: " + String.valueOf(object));
		
		return new HashSet<>();
	}
	
	
	
}
