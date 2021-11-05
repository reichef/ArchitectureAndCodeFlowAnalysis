package edu.kit.kastel.sdq.pcmjoanaflowanalysis.pcmutil

import org.palladiosimulator.pcm.repository.OperationRequiredRole
import org.palladiosimulator.pcm.repository.RequiredRole
import java.util.Collection
import java.util.ArrayList
import org.palladiosimulator.pcm.repository.ProvidedRole
import org.palladiosimulator.pcm.repository.OperationProvidedRole
import org.palladiosimulator.pcm.repository.RepositoryComponent
import org.palladiosimulator.pcm.core.composition.Connector
import org.palladiosimulator.pcm.repository.BasicComponent
import org.palladiosimulator.pcm.repository.CompositeComponent
import org.palladiosimulator.pcm.core.composition.AssemblyConnector

class PCMSubtypeResolver {
	private new(){}
	
	def static Collection<OperationRequiredRole> filterOperationRequiredRoles(Collection<RequiredRole> requiredRoles){
		return requiredRoles.filter(OperationRequiredRole).toList;
	} 
	
	def static Collection<OperationProvidedRole> filterOperationProvidedRoles(Collection<ProvidedRole> providedRoles){
		return providedRoles.filter(OperationProvidedRole).toList;
	} 
	
	def static Collection<BasicComponent> filterBasicComponents(Collection<RepositoryComponent> repositoryComponents){
		return repositoryComponents.filter(BasicComponent).toList;
	}
	
	def static Collection<CompositeComponent> filterCompositeComponents(Collection<RepositoryComponent> repositoryComponents){
		return repositoryComponents.filter(CompositeComponent).toList;
	}
	
	def static Collection<AssemblyConnector> filterAssemblyConnectors(Collection<Connector> connectors){
		return connectors.filter(AssemblyConnector).toList;
	}
	
}