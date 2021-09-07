package edu.kit.kastel.sdq.pcmjoanaflowanalysis.datastructure.refactored;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Optional;

import org.palladiosimulator.pcm.core.composition.AssemblyConnector;
import org.palladiosimulator.pcm.core.composition.AssemblyContext;
import org.palladiosimulator.pcm.core.composition.DelegationConnector;
import org.palladiosimulator.pcm.core.composition.ProvidedDelegationConnector;
import org.palladiosimulator.pcm.core.composition.RequiredDelegationConnector;
import org.palladiosimulator.pcm.core.composition.util.CompositionSwitch;
import org.palladiosimulator.pcm.repository.OperationProvidedRole;
import org.palladiosimulator.pcm.repository.OperationRequiredRole;
import org.palladiosimulator.pcm.repository.RepositoryComponent;
import org.palladiosimulator.pcm.repository.RequiredRole;

import edu.kit.kastel.sdq.pcmjoanaflowanalysis.datastructure.FlowGraphVertex;

class CompositionVisitor extends CompositionSwitch<Collection<AssemblyComponentContext>> {
	
	private final PCMJoanaFlowDatastructureFactory factory;
	
	private Collection<Connection<OperationRequiredRole, AssemblyComponentContext>> incompleteRequiredConnections;
	private Collection<Connection<AssemblyComponentContext, OperationProvidedRole>> incompleteProvidedConnections;
	
	CompositionVisitor(PCMJoanaFlowDatastructureFactory factory) {
		this.factory = factory;
		this.incompleteRequiredConnections = new HashSet<>();
		this.incompleteProvidedConnections = new HashSet<>();
	}

	@Override
	public Collection<AssemblyComponentContext> caseProvidedDelegationConnector(ProvidedDelegationConnector connector) {
		AssemblyContext context = connector.getAssemblyContext_ProvidedDelegationConnector();
		Optional<AssemblyComponentContext> potentialVertex = factory.getEncapsulatingComponent(context);
		
		if (potentialVertex.isPresent()) {
			AssemblyComponentContext vertex = potentialVertex.get();
			OperationProvidedRole outerProvidedRole = connector.getOuterProvidedRole_ProvidedDelegationConnector();
			Connection<AssemblyComponentContext, OperationProvidedRole> connection = new Connection<>(vertex, outerProvidedRole);
			
			incompleteProvidedConnections.add(connection);
		} else {
			OperationProvidedRole innerProvidedRole = connector.getInnerProvidedRole_ProvidedDelegationConnector();
			for (Connection<AssemblyComponentContext, OperationProvidedRole> connection : incompleteProvidedConnections) {
				if (connection.getEndpoint2().getId().equals(innerProvidedRole.getId())) {
					OperationProvidedRole outerRequiredRole = connector.getOuterProvidedRole_ProvidedDelegationConnector();
					connection.setEndpoint2(outerRequiredRole);
					
					break;
				}
			}
		}
		
		return Collections.emptyList();
	}

	@Override
	public Collection<AssemblyComponentContext> caseRequiredDelegationConnector(RequiredDelegationConnector connector) {
		AssemblyContext context = connector.getAssemblyContext_RequiredDelegationConnector();
		Optional<AssemblyComponentContext> potentialVertex = factory.getEncapsulatingComponent(context);
		
		if (potentialVertex.isPresent()) {
			AssemblyComponentContext vertex = potentialVertex.get();
			OperationRequiredRole outerRequiredRole = connector.getOuterRequiredRole_RequiredDelegationConnector();
			Connection<OperationRequiredRole, AssemblyComponentContext> connection = new Connection<>(outerRequiredRole, vertex);
			
			incompleteRequiredConnections.add(connection);
		} else {
			OperationRequiredRole innerRequiredRole = connector.getInnerRequiredRole_RequiredDelegationConnector();
			for (Connection<OperationRequiredRole, AssemblyComponentContext> connection : incompleteRequiredConnections) {
				if (connection.getEndpoint1().getId().equals(innerRequiredRole.getId())) {
					OperationRequiredRole outerRequiredRole = connector.getOuterRequiredRole_RequiredDelegationConnector();
					connection.setEndpoint1(outerRequiredRole);
					
					break;
				}
			}
		}
	
		return Collections.emptyList();
	}

	@Override
	public Collection<AssemblyComponentContext> caseAssemblyConnector(AssemblyConnector connector) {
		factory.setCurrentAssemblyConnector(connector);
		
		Optional<AssemblyComponentContext> potentialRequiring = factory.getEncapsulatingComponent(connector.getRequiringAssemblyContext_AssemblyConnector());
		Optional<AssemblyComponentContext> potentialProviding = factory.getEncapsulatingComponent(connector.getProvidingAssemblyContext_AssemblyConnector());
		
		AssemblyComponentContext requiring = potentialRequiring.orElseGet(() -> findMatchingRequiredConnection(connector));
		AssemblyComponentContext providing = potentialProviding.orElseGet(() -> findMatchingProvidedConnection(connector));
		
		if (!requiring.getId().equals(providing.getId())) {
			factory.createFlowGraphEdgeRepresentation();
		}
		
		return Collections.emptyList();
	}
	
	private AssemblyComponentContext findMatchingRequiredConnection(AssemblyConnector connector) {
		OperationRequiredRole role = connector.getRequiredRole_AssemblyConnector();
		
		Iterator<Connection<OperationRequiredRole, AssemblyComponentContext>> iterator = incompleteRequiredConnections.iterator();
		while (iterator.hasNext()) {
			Connection<OperationRequiredRole, AssemblyComponentContext> connection = iterator.next();
			OperationRequiredRole delegatedRole = connection.getEndpoint1();
			if (role.getId().equals(delegatedRole.getId())) {
				iterator.remove();
				return connection.getEndpoint2();
			}
		}
		return null;
	}
	
	private AssemblyComponentContext findMatchingProvidedConnection(AssemblyConnector connector) {
		OperationProvidedRole role = connector.getProvidedRole_AssemblyConnector();
		
		Iterator<Connection<AssemblyComponentContext, OperationProvidedRole>> iterator = incompleteProvidedConnections.iterator();
		while (iterator.hasNext()) {
			Connection<AssemblyComponentContext, OperationProvidedRole> connection = iterator.next();
			OperationProvidedRole delegatedRole = connection.getEndpoint2();
			if (role.getId().equals(delegatedRole.getId())) {
				iterator.remove();
				return connection.getEndpoint1();
			}
		}
		return null;
	}

	@Override
	public Collection<AssemblyComponentContext> caseAssemblyContext(AssemblyContext context) {
		return null;
	}

}
