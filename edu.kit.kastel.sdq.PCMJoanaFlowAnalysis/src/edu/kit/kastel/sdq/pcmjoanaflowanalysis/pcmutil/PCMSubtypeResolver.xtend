package edu.kit.kastel.sdq.pcmjoanaflowanalysis.pcmutil

import org.palladiosimulator.pcm.repository.OperationRequiredRole
import org.palladiosimulator.pcm.repository.RequiredRole
import java.util.Collection
import java.util.ArrayList
import org.palladiosimulator.pcm.repository.ProvidedRole
import org.palladiosimulator.pcm.repository.OperationProvidedRole
import org.palladiosimulator.pcm.repository.RepositoryComponent
import org.palladiosimulator.pcm.repository.Role
import java.util.HashSet

class PCMSubtypeResolver {
	private new(){}
	
	
	def static Collection<OperationRequiredRole> filterOperationRequiredRoles(Collection<RequiredRole> requiredRoles){
		return requiredRoles.filter(OperationRequiredRole).toList;
	} 
	
	def static Collection<OperationProvidedRole> filterOperationProvidedRoles(Collection<ProvidedRole> providedRoles){
		return providedRoles.filter(OperationProvidedRole).toList;
	} 
	
	def static Collection<Role> collectOperationRelatingRoles(RepositoryComponent component){
		var roles = new HashSet<Role>();
		
		roles.addAll(component.providedRoles_InterfaceProvidingEntity);
		roles.addAll(component.requiredRoles_InterfaceRequiringEntity);
		
		return roles;
	}
}