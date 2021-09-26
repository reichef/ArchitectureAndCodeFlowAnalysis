package edu.kit.kastel.sdq.pcmjoanaflowanalysis.utils;

import java.util.Collection;
import java.util.Optional;

import org.eclipse.emf.ecore.EObject;
import org.palladiosimulator.pcm.repository.RepositoryComponent;
import org.palladiosimulator.pcm.repository.util.RepositorySwitch;

import edu.kit.kastel.sdq.ecoreannotations.Annotation;
import edu.kit.kastel.sdq.ecoreannotations.GenericTargetStringContentAnnotation;
import edu.kit.kastel.sdq.ecoreannotations.util.EcoreannotationsSwitch;
import edu.kit.kastel.sdq.pcmjoanaflowanalysis.datastructure.refactored.Annotated;
import edu.kit.kastel.sdq.pcmjoanaflowanalysis.datastructure.refactored.AssemblyComponentContext;

public class AnnotationHelper {
	
	private AnnotationHelper() {
	}

	private static final RepositorySwitch<Optional<RepositoryComponent>> componentFilterSwitch = new RepositorySwitch<Optional<RepositoryComponent>>() {

		@Override
		public Optional<RepositoryComponent> caseRepositoryComponent(RepositoryComponent object) {
			return Optional.of(object);
		}

		@Override
		public Optional<RepositoryComponent> defaultCase(EObject object) {
			return Optional.empty();
		}
		
	};
	
	private static final EcoreannotationsSwitch<Optional<GenericTargetStringContentAnnotation>> annotationFilterSwitch = new EcoreannotationsSwitch<Optional<GenericTargetStringContentAnnotation>>() {

		@Override
		public Optional<GenericTargetStringContentAnnotation> caseGenericTargetStringContentAnnotation(
				GenericTargetStringContentAnnotation object) {
			return Optional.of(object);
		}

		@Override
		public Optional<GenericTargetStringContentAnnotation> defaultCase(EObject object) {
			return Optional.empty();
		}
		
	};
	
	public static void fillFlowGraphRepresentationsWithAnnotations(Collection<AssemblyComponentContext> representations, Collection<Annotation> annotations) {
		for (AssemblyComponentContext representation : representations) {
			fillFlowGraphRepresentationWithAnnotations(representation, annotations);
		}
	}
	
	private static void fillFlowGraphRepresentationWithAnnotations(AssemblyComponentContext representation, Collection<Annotation> annotations) {
		for (Annotation annotation : annotations) {
			fillFlowGraphRepresentationWithAnnotation(representation, annotation);
		}
	}

	private static void fillFlowGraphRepresentationWithAnnotation(AssemblyComponentContext representation,
			Annotation annotation) {
		Optional<GenericTargetStringContentAnnotation> possibleCastedAnnotation = annotationFilterSwitch.doSwitch(annotation);
		if (possibleCastedAnnotation.isEmpty()) {
			return;
		}
		
		GenericTargetStringContentAnnotation castedAnnotation = possibleCastedAnnotation.get();
		Optional<RepositoryComponent> possibleRepositoryComponent = componentFilterSwitch.doSwitch(castedAnnotation.getTarget());
		if (possibleRepositoryComponent.isEmpty()) {
			return;
		}
		
		RepositoryComponent target = possibleRepositoryComponent.get();
		if (target.getId().equals(representation.getComponent().getId())) {
			if (castedAnnotation.getAnnotationType().equals("ClassPath")) {
				Annotated component = representation.getComponent();
				component.setAnnotation(castedAnnotation.getAnnotationType(), castedAnnotation.getContent());
			} else {
				representation.setAnnotation(castedAnnotation.getAnnotationType(), castedAnnotation.getContent());
			}
		}
	}
	
}
