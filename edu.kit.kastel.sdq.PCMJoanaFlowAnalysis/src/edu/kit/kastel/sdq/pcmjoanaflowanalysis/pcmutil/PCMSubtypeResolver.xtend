package edu.kit.kastel.sdq.pcmjoanaflowanalysis.pcmutil

import org.palladiosimulator.pcm.repository.OperationRequiredRole
import org.palladiosimulator.pcm.repository.RequiredRole
import java.util.Collection
import org.palladiosimulator.pcm.repository.ProvidedRole
import org.palladiosimulator.pcm.repository.OperationProvidedRole
import org.palladiosimulator.pcm.repository.RepositoryComponent
import org.palladiosimulator.pcm.repository.Role
import java.util.HashSet
import org.palladiosimulator.pcm.repository.OperationInterface
import org.palladiosimulator.pcm.repository.Interface
import org.palladiosimulator.pcm.repository.BasicComponent
import org.palladiosimulator.pcm.repository.CompositeDataType
import org.palladiosimulator.pcm.repository.DataType
import org.palladiosimulator.pcm.usagemodel.EntryLevelSystemCall
import org.palladiosimulator.pcm.usagemodel.AbstractUserAction
import org.palladiosimulator.pcm.core.composition.Connector
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
	
	def static Collection<OperationInterface> filterOperationInterfaces(Collection<Interface> interfaces){
		return interfaces.filter(OperationInterface).toList;
	}
	
	def static Collection<BasicComponent> filterBasicComponents(Collection<RepositoryComponent> components){
		return components.filter(BasicComponent).toList;
	}
	
	def static Collection<CompositeComponent> filterCompositeComponents(Collection<RepositoryComponent> repositoryComponents){
		return repositoryComponents.filter(CompositeComponent).toList;
	}
	
	def static Collection<CompositeDataType> filterCompositeDataTypes(Collection<DataType> datatypes){
		return datatypes.filter(CompositeDataType).toList;
	}
	
	def static Collection<EntryLevelSystemCall> filterEntryLevelSystemCalls(Collection<AbstractUserAction> actions){
		return actions.filter(EntryLevelSystemCall).toList;
	}
	
	def static Collection<AssemblyConnector> filterAssemblyConnectors(Collection<Connector> connectors){
		return connectors.filter(AssemblyConnector).toList;
	}
	
	def static Collection<Role> collectOperationRelatingRoles(RepositoryComponent component){
		var roles = new HashSet<Role>();
		
		roles.addAll(component.providedRoles_InterfaceProvidingEntity);
		roles.addAll(component.requiredRoles_InterfaceRequiringEntity);
		
		return roles;
	}
}