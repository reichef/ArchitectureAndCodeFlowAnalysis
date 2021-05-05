package edu.kit.kastel.sdq.pcmjoanaflowanalysis.pcmutil

import org.palladiosimulator.pcm.repository.OperationInterface
import java.util.Optional
import org.palladiosimulator.pcm.repository.OperationSignature
import org.palladiosimulator.pcm.repository.RepositoryComponent
import org.palladiosimulator.pcm.core.composition.AssemblyContext
import org.palladiosimulator.pcm.repository.OperationProvidedRole
import org.palladiosimulator.pcm.repository.Repository
import org.palladiosimulator.pcm.repository.OperationRequiredRole
import org.palladiosimulator.pcm.system.System;
import org.palladiosimulator.pcm.core.entity.ComposedProvidingRequiringEntity
import org.palladiosimulator.pcm.repository.BasicComponent
import org.palladiosimulator.pcm.core.composition.ComposedStructure

class PCMRepositoryElementResolver {
	
	private new(){}
	
	def static Optional<OperationSignature> getOperationSignatureOfInterfaceById(OperationInterface operationInterface,
			String operationSignatureId) {		
		
		var opSig = Optional.empty();
		for (OperationSignature sig : operationInterface.getSignatures__OperationInterface()) {
			if (sig.getId().equals(operationSignatureId)) {
				opSig = Optional.ofNullable(sig);
				return opSig;
			}
		}
		return opSig;
	}
	
	def static boolean interfaceContainsSignature(OperationInterface operationInterface, OperationSignature operationSignature){
		return operationInterface.signatures__OperationInterface.contains(operationSignature);
	}
	
	def static OperationProvidedRole getOperationProvidedRoleById(Repository repository, String searchedId){
		return repository.components__Repository.map[it.providedRoles_InterfaceProvidingEntity].flatten.filter(OperationProvidedRole).findFirst[opProvRole | opProvRole.id.equals(searchedId)];
	}
	
	def static OperationRequiredRole getOperationRequiredRoleById(Repository repository, String searchedId){
		return repository.components__Repository.map[it.requiredRoles_InterfaceRequiringEntity].flatten.filter(OperationRequiredRole).findFirst[opReqRole | opReqRole.id.equals(searchedId)];
	}
	
	def static AssemblyContext getAssemblyContextById(ComposedProvidingRequiringEntity composed, String contextId){
		var context = getContainedAssemblyContextById(composed, contextId);
		if(context === null){
			var composedContexts = composed.assemblyContexts__ComposedStructure.filter[localContext | localContext.encapsulatedComponent__AssemblyContext instanceof ComposedProvidingRequiringEntity].toList;
			var recursive = composedContexts.map[composedContext | (composedContext.encapsulatedComponent__AssemblyContext as ComposedProvidingRequiringEntity).getAssemblyContextById(contextId)].toList;
			context = recursive.findFirst[ recursiveContext | recursiveContext.contextEqualsId(contextId)];
		}
		return context;
	}
	
	def static AssemblyContext getContainedAssemblyContextById(ComposedProvidingRequiringEntity composed, String contextId){
		return composed.assemblyContexts__ComposedStructure.findFirst[ localContext | localContext.contextEqualsId(contextId)];
	}
	
	def static boolean contextEqualsId(AssemblyContext context, String contextId){
		if(context === null){
			return false;
		} 
		
		return context.id.equals(contextId);
	} 
	
	def static AssemblyContext getInnerAssemblyContextForOperationProvidedRole(ComposedStructure composed, OperationProvidedRole role){
		
		for(context : composed.assemblyContexts__ComposedStructure){
			if(context.encapsulatedComponent__AssemblyContext.providedRoles_InterfaceProvidingEntity.exists[provRole | role.equals(provRole)]){
				return context;
			}
		}
		
		return null;
	}
}