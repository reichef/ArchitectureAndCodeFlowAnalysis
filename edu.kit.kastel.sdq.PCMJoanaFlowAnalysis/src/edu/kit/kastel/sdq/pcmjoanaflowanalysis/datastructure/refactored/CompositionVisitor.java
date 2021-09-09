package edu.kit.kastel.sdq.pcmjoanaflowanalysis.datastructure.refactored;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Optional;

import org.palladiosimulator.pcm.core.composition.AssemblyConnector;
import org.palladiosimulator.pcm.core.composition.AssemblyContext;
import org.palladiosimulator.pcm.core.composition.ProvidedDelegationConnector;
import org.palladiosimulator.pcm.core.composition.RequiredDelegationConnector;
import org.palladiosimulator.pcm.core.composition.util.CompositionSwitch;
import org.palladiosimulator.pcm.repository.OperationProvidedRole;
import org.palladiosimulator.pcm.repository.OperationRequiredRole;

import edu.kit.kastel.sdq.pcmjoanaflowanalysis.utils.Pair;

class CompositionVisitor extends CompositionSwitch<Collection<AssemblyComponentContext>> {
	
	private final PCMJoanaFlowDatastructureFactory factory;
	
	private Collection<Pair<OperationRequiredRole, AssemblyComponentContext>> incompleteRequiredConnections;
	private Collection<Pair<AssemblyComponentContext, OperationProvidedRole>> incompleteProvidedConnections;
	
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
			Pair<AssemblyComponentContext, OperationProvidedRole> connection = new Pair<>(vertex, outerProvidedRole);
			
			incompleteProvidedConnections.add(connection);
		} else {
			OperationProvidedRole innerProvidedRole = connector.getInnerProvidedRole_ProvidedDelegationConnector();
			Optional<Pair<AssemblyComponentContext, OperationProvidedRole>> matchingConnection = findMatchingConnection(innerProvidedRole);
			if (matchingConnection.isPresent()) {
				Pair<AssemblyComponentContext, OperationProvidedRole> connection = matchingConnection.get();
				OperationProvidedRole outerProvidedRole = connector.getOuterProvidedRole_ProvidedDelegationConnector();
				
				incompleteProvidedConnections.remove(connection);
				incompleteProvidedConnections.add(new Pair<>(connection.getFirst(), outerProvidedRole));
			}
		}
		
		return Collections.emptyList();
	}
	
	private Optional<Pair<AssemblyComponentContext, OperationProvidedRole>> findMatchingConnection(OperationProvidedRole role) {
		return incompleteProvidedConnections.stream().filter(connection -> connection.getSecond().getId().equals(role.getId())).findFirst();
	}

	@Override
	public Collection<AssemblyComponentContext> caseRequiredDelegationConnector(RequiredDelegationConnector connector) {
		AssemblyContext context = connector.getAssemblyContext_RequiredDelegationConnector();
		Optional<AssemblyComponentContext> potentialVertex = factory.getEncapsulatingComponent(context);
		
		if (potentialVertex.isPresent()) {
			AssemblyComponentContext vertex = potentialVertex.get();
			OperationRequiredRole outerRequiredRole = connector.getOuterRequiredRole_RequiredDelegationConnector();
			Pair<OperationRequiredRole, AssemblyComponentContext> connection = new Pair<>(outerRequiredRole, vertex);
			
			incompleteRequiredConnections.add(connection);
		} else {
			OperationRequiredRole innerRequiredRole = connector.getInnerRequiredRole_RequiredDelegationConnector();
			Optional<Pair<OperationRequiredRole, AssemblyComponentContext>> matchingConnection = findMatchingConnection(innerRequiredRole);
			if (matchingConnection.isPresent()) {
				Pair<OperationRequiredRole, AssemblyComponentContext> connection = matchingConnection.get();
				OperationRequiredRole outerRequiredRole = connector.getOuterRequiredRole_RequiredDelegationConnector();
				
				incompleteRequiredConnections.remove(connection);
				incompleteRequiredConnections.add(new Pair<>(outerRequiredRole, connection.getSecond()));
			}
		}
	
		return Collections.emptyList();
	}
	
	private Optional<Pair<OperationRequiredRole, AssemblyComponentContext>> findMatchingConnection(OperationRequiredRole role) {
		return incompleteRequiredConnections.stream().filter(connection -> connection.getFirst().getId().equals(role.getId())).findFirst();
	}

	@Override
	public Collection<AssemblyComponentContext> caseAssemblyConnector(AssemblyConnector connector) {
		factory.setCurrentAssemblyConnector(connector);
		
		Optional<AssemblyComponentContext> potentialRequiring = factory.getEncapsulatingComponent(connector.getRequiringAssemblyContext_AssemblyConnector());
		Optional<AssemblyComponentContext> potentialProviding = factory.getEncapsulatingComponent(connector.getProvidingAssemblyContext_AssemblyConnector());
		
		AssemblyComponentContext requiring = potentialRequiring.orElseGet(() -> findMatchingRequiredConnection(connector));
		AssemblyComponentContext providing = potentialProviding.orElseGet(() -> findMatchingProvidedConnection(connector));
		
		if (requiring != null && providing != null && !requiring.getId().equals(providing.getId())) {
			factory.setCurrentRequiringVertex(requiring);
			factory.setCurrentProvidingVertex(providing);
			factory.createFlowGraphEdgeRepresentation();
		}
		
		return Collections.emptyList();
	}
	
	private AssemblyComponentContext findMatchingRequiredConnection(AssemblyConnector connector) {
		OperationRequiredRole role = connector.getRequiredRole_AssemblyConnector();
		Optional<Pair<OperationRequiredRole, AssemblyComponentContext>> matchingConnection = findMatchingConnection(role);
		if (matchingConnection.isPresent()) {
			Pair<OperationRequiredRole, AssemblyComponentContext> connection = matchingConnection.get();
			incompleteRequiredConnections.remove(connection);
			return connection.getSecond();
		}
		return null;
	}
	
	private AssemblyComponentContext findMatchingProvidedConnection(AssemblyConnector connector) {
		OperationProvidedRole role = connector.getProvidedRole_AssemblyConnector();
		Optional<Pair<AssemblyComponentContext, OperationProvidedRole>> matchingConnection = findMatchingConnection(role);
		if (matchingConnection.isPresent()) {
			Pair<AssemblyComponentContext, OperationProvidedRole> connection = matchingConnection.get();
			incompleteProvidedConnections.remove(connection);
			return connection.getFirst();
		}
		return null;
	}

	@Override
	public Collection<AssemblyComponentContext> caseAssemblyContext(AssemblyContext context) {
		return null;
	}

}
