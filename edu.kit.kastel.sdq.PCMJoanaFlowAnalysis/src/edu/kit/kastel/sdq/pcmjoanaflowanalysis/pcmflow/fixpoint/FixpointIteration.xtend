package edu.kit.kastel.sdq.pcmjoanaflowanalysis.pcmflow.fixpoint

import java.util.Queue
import edu.kit.kastel.sdq.pcmjoanaflowanalysis.datastructure.hierarchical.AssemblyComponentContext
import edu.kit.kastel.sdq.pcmjoanaflowanalysis.pcmutil.PCMRepositoryElementResolver
import org.palladiosimulator.pcm.repository.OperationRequiredRole
import org.palladiosimulator.pcm.repository.Role
import org.palladiosimulator.pcm.repository.RepositoryFactory
import org.palladiosimulator.pcm.repository.OperationProvidedRole
import edu.kit.kastel.sdq.pcmjoanaflowanalysis.datastructure.hierarchical.AssemblyConnectorRepresentation
import java.util.Collection
import java.util.HashSet
import edu.kit.kastel.sdq.pcmjoanaflowanalysis.pcmutil.PCMSubtypeResolver
import java.util.ArrayDeque
import edu.kit.kastel.sdq.pcmjoanaflowanalysis.analysiscoupling.PCMJOANACoupler
import java.util.Map
import java.util.HashMap
import org.eclipse.ocl.util.Tuple
import java.util.ArrayList
import org.palladiosimulator.pcm.core.composition.AssemblyContext
import org.eclipse.emf.ecore.EStructuralFeature

//For ease of problem, we assume a flat system at the moment until fully integrating flat data structure is available.  
class FixpointIteration {
	private Queue<SystemOperationIdentifying> componentPointsToProcess;
	private PCMJOANACoupler coupler;

	new(PCMJOANACoupler coupler) {
		componentPointsToProcess = new ArrayDeque;
		this.coupler = coupler;
	}

	public def void runAnalysis(SystemOperationIdentifying initialCall) {
		componentPointsToProcess.add(initialCall);
		worklist();
	}

	private def void worklist() {

		while (!componentPointsToProcess.isEmpty) {
			val currentComponentSource = componentPointsToProcess.poll;

			val foundSinks = analyzeIntraComponentFlow(currentComponentSource);
			val foundSinksWithoutAlreadyFound = removeAlreadyFoundSinks(currentComponentSource, foundSinks);

			// There are found sinks, that are not yet considered
			if (foundSinksWithoutAlreadyFound.size > 0) {
				for (sink : foundSinksWithoutAlreadyFound) {
					sink.context.addIntraComponentFlow(currentComponentSource, sink);
					componentPointsToProcess.add(resolveAssemblyStepFromSink(currentComponentSource.context, sink));

					/*
					 * 1. Add the sink-location as future source because we do not get the "return" as sink from JOANA for the"return-path".
					 * 2. Do this only for operations with a return-value
					 * TODO: Check if this approach covers all flow conditions (e.g. if 2. has to be removded)
					 */
					if (sink.signature.returnType__OperationSignature !== null) {
						componentPointsToProcess.add(sink);
					}
				}
			}
		}
	}

	def Collection<SystemOperationIdentifying> analyzeIntraComponentFlow(SystemOperationIdentifying source) {

		var methodIDsOfFlows = coupler.analyzeIntraComponentFlow(source, source.context.getClassPath().get());
		val sinks = resolveSinkIdsToDataStructureIdentification(source, methodIDsOfFlows);

		return sinks;
	}

	def Collection<SystemOperationIdentifying> resolveSinkIdsToDataStructureIdentification(
		SystemOperationIdentifying source, Collection<String> methodIDsOfSinks) {
		val sinks = new HashSet<SystemOperationIdentifying>;

		for (String id : methodIDsOfSinks) {
			// PCM Roles do not support a supertype for "Operation-Roles" to extract the interfaces, regardless of provided or required.
			for (Role role : PCMSubtypeResolver.collectOperationRelatingRoles(source.context.component.component)) {
				var sinkInterface = RepositoryFactory.eINSTANCE.createOperationInterface;
				if (role instanceof OperationRequiredRole) {
					sinkInterface = role.requiredInterface__OperationRequiredRole;
				} else if (role instanceof OperationProvidedRole) {
					sinkInterface = role.providedInterface__OperationProvidedRole;
				}

				var requiredOpSig = PCMRepositoryElementResolver.getOperationSignatureOfInterfaceById(
					sinkInterface, id);
				if (requiredOpSig.isPresent()) {
					var sinkIdentifying = new SystemOperationIdentifying(source.context, sinkInterface,
						requiredOpSig.get(), null);

					sinks.add(sinkIdentifying);
				}
			}
		}

		return sinks;
	}

	/**
	 * Removes elements from the sinks parameter when they already exist in the context flows for the source parameter.
	 * @param source the startpoint of the connections in the component to be examined 
	 * @param sinks all possible endpoints of the connections in the component coming from the source
	 * @return all elements not already in the flows for the source 
	 */
	def Collection<SystemOperationIdentifying> removeAlreadyFoundSinks(SystemOperationIdentifying source,
		Collection<SystemOperationIdentifying> sinks) {

		val existingSinksForSource = source.context.getAlreadyFoundSinksForSource(source);
		return sinks.filter[sink|!existingSinksForSource.exists[existingSink|sink.identyfyingEquals(existingSink)]].
			toList;
	}

	def private SystemOperationIdentifying resolveAssemblyStepFromSink(AssemblyComponentContext representation,
		SystemOperationIdentifying sinkIdentifying) {
		var assemblyConnectorOptional = representation.getAssemblyConnectorRepresentationForSink(sinkIdentifying);
		var stepPossible = assemblyConnectorOptional.isPresent();
		if (stepPossible) {
			val assemblyConnector = assemblyConnectorOptional.get();
			var resolvedDirection = assemblyConnector.getDirection(sinkIdentifying);

			/*TODO: Include the methods information assemblyconnectors*/
			var method_history = assemblyConnector.getMethodHistory();
			var method_history2 = new ArrayList<HashMap<SystemOperationIdentifying, AssemblyComponentContext>>();

			if (resolvedDirection.equals(AssemblyConnectorRepresentation.Direction.ASSEMBLY)) {
				// Get the interface method
				method_history.add(sinkIdentifying.context.context.entityName);
				method_history.add(sinkIdentifying.context.name);
				method_history.add(sinkIdentifying.signature.entityName);
				// method_history.add(sinkIdentifying.opInterface.entityName);
				method_history.add(assemblyConnector.connector.providingAssemblyContext_AssemblyConnector.entityName);
				var String component_origin = assemblyConnector.connector.providedRole_AssemblyConnector.entityName;
				component_origin = component_origin.split("\\.").get(0);
				method_history.add(component_origin);
				method_history.add(sinkIdentifying.opInterface.signatures__OperationInterface.get(0).entityName);

				var pair = new HashMap<SystemOperationIdentifying, AssemblyComponentContext>();
				var assemblyContext_ = assemblyConnector.providing;
				var AssemblyComponentContext new_context = new AssemblyComponentContext(assemblyContext_);
				pair.put(sinkIdentifying, new_context);
				method_history2.add(pair);
			} else if (resolvedDirection.equals(AssemblyConnectorRepresentation.Direction.OPPOSITE)) {
				// Get the interface method
				method_history.add(sinkIdentifying.context.context.entityName);
				method_history.add(sinkIdentifying.context.name);
				method_history.add(sinkIdentifying.signature.entityName);
				// method_history.add(sinkIdentifying.opInterface.entityName);
				method_history.add(assemblyConnector.connector.requiringAssemblyContext_AssemblyConnector.entityName);
				var String component_origin = assemblyConnector.connector.requiredRole_AssemblyConnector.entityName;
				component_origin = component_origin.split("\\.").get(0);
				method_history.add(component_origin);
				method_history.add(sinkIdentifying.opInterface.signatures__OperationInterface.get(0).entityName);

				var pair = new HashMap<SystemOperationIdentifying, AssemblyComponentContext>();
				var assemblyContext_ = assemblyConnector.requiringContext;
				var AssemblyComponentContext new_context = new AssemblyComponentContext(assemblyContext_);
				pair.put(sinkIdentifying, new_context);
				// Include it in assemblyconnectorrepresentation
				method_history2.add(pair);
			}

			// assemblyConnector.setMethodHistory(method_history);
			/*End of change */
			if (resolvedDirection.equals(AssemblyConnectorRepresentation.Direction.ASSEMBLY)) {
				return new SystemOperationIdentifying(assemblyConnector.providing,
					assemblyConnector.providedRole.providedInterface__OperationProvidedRole, sinkIdentifying.signature,
					method_history2);
			} else if (resolvedDirection.equals(AssemblyConnectorRepresentation.Direction.OPPOSITE)) {
				return new SystemOperationIdentifying(assemblyConnector.requiringContext,
					assemblyConnector.requiredRole.requiredInterface__OperationRequiredRole, sinkIdentifying.signature,
					method_history2);
			}
		}
		return null;
	}
}
