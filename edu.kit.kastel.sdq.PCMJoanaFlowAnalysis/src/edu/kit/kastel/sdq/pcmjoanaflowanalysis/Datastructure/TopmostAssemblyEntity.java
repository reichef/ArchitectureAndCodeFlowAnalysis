package edu.kit.kastel.sdq.pcmjoanaflowanalysis.Datastructure;

import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;

import org.palladiosimulator.pcm.core.entity.ComposedProvidingRequiringEntity;

import edu.kit.kastel.sdq.ecoreannotations.AnnotationRepository;

public class TopmostAssemblyEntity extends AssemblyRepresentationContainer {

	private ComposedProvidingRequiringEntity entity;

	
	public TopmostAssemblyEntity(ComposedProvidingRequiringEntity topMostEntity) {
		super(topMostEntity.getId(), topMostEntity.getEntityName());
		this.entity = topMostEntity;
	}
	

	public ComposedProvidingRequiringEntity getEncapsulatedEntity() {
		return entity;
	}
	
	public Collection<AssemblyComponent> getAllAssemblyComponentsFlatList() {
		Collection<AssemblyComponent> components = new HashSet<AssemblyComponent>();
		for(AssemblyComponent component : containedRepresentations) {
			components.addAll(component.getAssemblyComponentsRecursive());
		}
		return components;
	}


	@Override
	public void fillWithClassPath(AnnotationRepository repository, Optional<String> latestClassPath) {
		for(AssemblyComponent representation : containedRepresentations) {
			representation.fillWithClassPath(repository, latestClassPath);
		}
	}
}
