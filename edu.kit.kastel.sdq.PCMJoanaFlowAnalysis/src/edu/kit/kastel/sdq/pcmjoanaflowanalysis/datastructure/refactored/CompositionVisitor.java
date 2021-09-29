package edu.kit.kastel.sdq.pcmjoanaflowanalysis.datastructure.refactored;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Optional;

import org.palladiosimulator.pcm.core.composition.AssemblyConnector;
import org.palladiosimulator.pcm.core.composition.AssemblyContext;
import org.palladiosimulator.pcm.core.composition.ComposedStructure;
import org.palladiosimulator.pcm.core.composition.ProvidedDelegationConnector;
import org.palladiosimulator.pcm.core.composition.RequiredDelegationConnector;
import org.palladiosimulator.pcm.core.composition.util.CompositionSwitch;
import org.palladiosimulator.pcm.repository.BasicComponent;
import org.palladiosimulator.pcm.repository.OperationProvidedRole;
import org.palladiosimulator.pcm.repository.OperationRequiredRole;

import edu.kit.kastel.sdq.pcmjoanaflowanalysis.utils.Pair;

class CompositionVisitor extends CompositionSwitch<Object> {
	
	/**
	 * <p>A non-null reference that could be used as default return value to terminate the switch, 
	 * in case no explicit non-null return value exists.</p>
	 */
	private static final Object NON_NULL_RETURN_VALUE = new Object();
	
	private final PCMJoanaFlowDatastructureFactory factory;
	
	private Collection<Pair<OperationRequiredRole, AssemblyComponentContext>> incompleteRequiredConnections;
	private Collection<Pair<AssemblyComponentContext, OperationProvidedRole>> incompleteProvidedConnections;
	
	CompositionVisitor(PCMJoanaFlowDatastructureFactory factory) {
		this.factory = factory;
		this.incompleteRequiredConnections = new HashSet<>();
		this.incompleteProvidedConnections = new HashSet<>();
	}

	/**
	 * <p>{@link ProvidedDelegationConnector}s must be resolved to connections between {@link BasicComponent}s, 
	 * where the component outside the {@link ComposedStructure} that owns the connector may not yet be represented in the graph.</p>
	 * 
	 * <p>There are two cases that need to be handled:</p>
	 * 
	 * <ul>
	 * <li>Case 1: The inner component is a BasicComponent, then a new incomplete connection is created.</li>
	 * <li>Case 2: the inner component is a CompositeComponent, then the matching incomplete connection is searched and extended.</li>
	 * </ul>
	 * 
	 * @see CompositionVisitor#caseRequiredDelegationConnector(RequiredDelegationConnector)
	 */
	@Override
	public Object caseProvidedDelegationConnector(ProvidedDelegationConnector connector) {
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
		
		return NON_NULL_RETURN_VALUE;
	}
	
	/**
	 * <p>Searches the incomplete provided connections for a connection matching the given role.</p>
	 * 
	 * @param role the unresolved part of the connection being searched for
	 * @return Optional of the result of the search
	 */
	private Optional<Pair<AssemblyComponentContext, OperationProvidedRole>> findMatchingConnection(OperationProvidedRole role) {
		return incompleteProvidedConnections.stream().filter(connection -> connection.getSecond().getId().equals(role.getId())).findFirst();
	}

	/**
	 * <p>{@link RequiredDelegationConnector}s must be resolved to connections between {@link BasicComponent}s, 
	 * where the component outside the {@link ComposedStructure} that owns the connector may not yet be represented in the graph.</p>
	 * 
	 * <p>There are two cases that need to be handled:</p>
	 * 
	 * <ul>
	 * <li>Case 1: The inner component is a BasicComponent, then a new incomplete connection is created.</li>
	 * <li>Case 2: the inner component is a CompositeComponent, then the matching incomplete connection is searched and extended.</li>
	 * </ul>
	 * 
	 * @see CompositionVisitor#caseProvidedDelegationConnector(RequiredDelegationConnector)
	 */
	@Override
	public Object caseRequiredDelegationConnector(RequiredDelegationConnector connector) {
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
	
		return NON_NULL_RETURN_VALUE;
	}
	
	/**
	 * <p>Searches the incomplete required connections for a connection matching the given role.</p>
	 * 
	 * @param role the unresolved part of the connection being searched for
	 * @return Optional of the result of the search
	 */
	private Optional<Pair<OperationRequiredRole, AssemblyComponentContext>> findMatchingConnection(OperationRequiredRole role) {
		return incompleteRequiredConnections.stream().filter(connection -> connection.getFirst().getId().equals(role.getId())).findFirst();
	}

	/** 
	 * <p>AssemblyConnectors must be represented as an edge in the graph.</p>
	 * 
	 * <p>Endpoints of this edge could be either fetched directly from already created vertices or from completing incomplete connections.</p>
	 */
	@Override
	public Object caseAssemblyConnector(AssemblyConnector connector) {
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
		
		return NON_NULL_RETURN_VALUE;
	}
	
	/**
	 * <p>Checks, if the {@link OperationRequiredRole} of the specific {@link AssemblyConnector} matches an incomplete required connection.
	 * In this case, remove this connection and give the associated vertex.</p>
	 * 
	 * @param connector specifies the {@link OperationRequiredRole}
	 * @return the endpoint vertex of the matching connection
	 * 
	 * @see CompositionVisitor#findMatchingProvidedConnection(AssemblyConnector)
	 */
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
	
	/**
	 * <p>Checks, if the {@link OperationProvidedRole} of the specific {@link AssemblyConnector} matches an incomplete provided connection.
	 * In this case, remove this connection and give the associated vertex.</p>
	 * 
	 * @param connector specifies the {@link OperationProvidedRole}
	 * @return the endpoint vertex of the matching connection
	 * 
	 * @see CompositionVisitor#findMatchingRequiredConnection(AssemblyConnector)
	 */
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

}
