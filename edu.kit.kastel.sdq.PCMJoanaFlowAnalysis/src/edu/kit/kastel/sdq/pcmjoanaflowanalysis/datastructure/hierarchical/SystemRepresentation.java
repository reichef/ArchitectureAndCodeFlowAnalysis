package edu.kit.kastel.sdq.pcmjoanaflowanalysis.datastructure.hierarchical;

import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.palladiosimulator.pcm.core.entity.ComposedProvidingRequiringEntity;

import edu.kit.kastel.sdq.ecoreannotations.AnnotationRepository;

public class SystemRepresentation extends AssemblyRepresentationContainer {

	private ComposedProvidingRequiringEntity entity;

	
	public SystemRepresentation(ComposedProvidingRequiringEntity topMostEntity) {
		super(topMostEntity.getId(), topMostEntity.getEntityName());
		this.entity = topMostEntity;
	}
	

	public ComposedProvidingRequiringEntity getEncapsulatedEntity() {
		return entity;
	}
	
	public Collection<AssemblyComponentContext> getAllAssemblyComponentsFlatList() {
		Collection<AssemblyComponentContext> components = new HashSet<AssemblyComponentContext>();
		for(AssemblyComponentContext component : containedRepresentations) {
			components.addAll(component.getAssemblyComponentsRecursive());
		}
		return components;
	}


	public Collection<FlowBasicComponent> collectFlowBasicComponents(){
		Set<FlowBasicComponent> components = new HashSet<>();
		collectFlowBasicComponents(components);
		return components;
	}
	
	@Override
	public void fillWithClassPath(AnnotationRepository repository, Optional<String> latestClassPath) {
		for(AssemblyComponentContext representation : containedRepresentations) {
			representation.fillWithClassPath(repository, latestClassPath);
		}
	}
	
	@Override
	public void printRepresentation() {
		containedRepresentations.forEach(a -> a.printRepresentation());
	}
}
