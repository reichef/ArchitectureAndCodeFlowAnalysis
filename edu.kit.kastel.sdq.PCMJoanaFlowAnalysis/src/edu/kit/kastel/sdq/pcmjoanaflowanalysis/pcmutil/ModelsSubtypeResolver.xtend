package edu.kit.kastel.sdq.pcmjoanaflowanalysis.pcmutil

import org.palladiosimulator.pcm.repository.OperationRequiredRole
import org.palladiosimulator.pcm.repository.RequiredRole
import java.util.Collection
import java.util.ArrayList
import org.palladiosimulator.pcm.repository.ProvidedRole
import org.palladiosimulator.pcm.repository.OperationProvidedRole
import edu.kit.kastel.sdq.ecoreannotations.GenericTargetStringContentAnnotation
import edu.kit.kastel.sdq.ecoreannotations.AnnotationRepository
import org.palladiosimulator.pcm.repository.BasicComponent
import org.palladiosimulator.pcm.repository.RepositoryComponent
import org.palladiosimulator.pcm.core.composition.AssemblyConnector
import org.palladiosimulator.pcm.core.composition.Connector
import org.palladiosimulator.pcm.core.composition.ProvidedDelegationConnector

class ModelsSubtypeResolver {
	private new(){}
	
	
	def static Collection<OperationRequiredRole> filterOperationRequiredRoles(Collection<RequiredRole> requiredRoles){
		return requiredRoles.filter(OperationRequiredRole).toList;
	} 
	
	def static Collection<OperationProvidedRole> filterOperationProvidedRoles(Collection<ProvidedRole> providedRoles){
		return providedRoles.filter(OperationProvidedRole).toList;
	} 
	
	def static Collection<ProvidedDelegationConnector> filterProvidedDelegationConnectors(Collection<Connector> connectors){
		return connectors.filter(ProvidedDelegationConnector).toList;
	}
	
	def static Collection<GenericTargetStringContentAnnotation> filterGenericTargetStringContentAnnotations(AnnotationRepository repository){
		return repository.annotations.filter(GenericTargetStringContentAnnotation).toSet;
	}
	
	def static Collection<BasicComponent> filterBasicComponents(Collection<RepositoryComponent> components){
		return components.filter(BasicComponent).toSet;
	}
	
	def static Collection<AssemblyConnector> filterAssemblyConnector(Collection<Connector> connectors){
		return connectors.filter(AssemblyConnector).toSet;
	}
}