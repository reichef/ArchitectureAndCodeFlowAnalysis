package edu.kit.kastel.sdq.pcmjoanaflowanalysis.pcmflow.fixpoint

import java.util.Queue
import edu.kit.kastel.sdq.pcmjoanaflowanalysis.datastructure.hierarchical.AssemblyComponentContext
import java.util.PriorityQueue
import edu.kit.kastel.sdq.pcmjoanaflowanalysis.analysiscoupling.AnalysisCoupler
import edu.kit.kastel.sdq.pcmjoanaflowanalysis.pcmutil.PCMRepositoryElementResolver
import org.palladiosimulator.pcm.repository.OperationRequiredRole
import org.palladiosimulator.pcm.repository.Role
import org.palladiosimulator.pcm.repository.RepositoryFactory
import org.palladiosimulator.pcm.repository.OperationProvidedRole
import edu.kit.kastel.sdq.pcmjoanaflowanalysis.datastructure.hierarchical.AssemblyConnectorRepresentation
import java.util.Collection
import java.util.HashSet
import edu.kit.kastel.sdq.pcmjoanaflowanalysis.pcmutil.PCMSubtypeResolver

//For ease of problem, we assume a flat system at the moment until fully integrating flat data structure.  
class FixpointIteration {
	private Queue<SystemOperationIdentifying> componentPointsToProcess;
	private AnalysisCoupler coupler;

	new(AnalysisCoupler coupler) {
		componentPointsToProcess = new PriorityQueue;
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
			
			//There are found sinks, that are not yet considered
			if(foundSinksWithoutAlreadyFound.size > 0){
				foundSinksWithoutAlreadyFound.forEach[sink | componentPointsToProcess.add(resolveAssemblyStepFromSink(currentComponentSource.context, sink))]
			}
		}
	}

	def Collection<SystemOperationIdentifying> analyzeIntraComponentFlow(SystemOperationIdentifying source) {

		// TODO: Temporary Solution until new datastructure replaces current one to unify roles.  
		// Call JOANA via coupler
		var methodIDsOfFlows = coupler.analyzeIntraComponentFlow(source, source.context.getClassPath().get());

		val sinks = resolveSinkIdsToDataStructureIdentification(source, methodIDsOfFlows);
		
		return sinks;
	}
	
	def Collection<SystemOperationIdentifying> resolveSinkIdsToDataStructureIdentification(SystemOperationIdentifying source, Collection<String> methodIDsOfSinks){
		val sinks = new HashSet<SystemOperationIdentifying>;
		
		for (String id : methodIDsOfSinks) {
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
						requiredOpSig.get());

					source.context.addIntraComponentFlow(source, sinkIdentifying);
					sinks.add(sinkIdentifying);
				}
			}
		}
	}
	
	/**
	 * Removes elements from the sinks parameter when they already exist in the context flows for the source parameter.
	 * @param source the startpoint of the connections in the component to be examined 
	 * @param sinks all possible endpoints of the connections in the component coming from the source
	 * @return all elements not already in the flows for the source 
	 */
	def Collection<SystemOperationIdentifying> removeAlreadyFoundSinks(SystemOperationIdentifying source, Collection<SystemOperationIdentifying> sinks) {
		
		val existingSinksForSource = source.context.getAlreadyFoundSinksForSource(source);
		return sinks.filter[sink | !existingSinksForSource.exists[existingSink | sink.identyfyingEquals(existingSink)]].toList;
	}
	

	def private SystemOperationIdentifying resolveAssemblyStepFromSink(AssemblyComponentContext representation,
		SystemOperationIdentifying sinkIdentifying) {
		var assemblyConnectorOptional = representation.getAssemblyConnectorRepresentationForSink(sinkIdentifying);
		var stepPossible = assemblyConnectorOptional.isPresent();
		if (stepPossible) {
			val assemblyConnector = assemblyConnectorOptional.get();
			var resolvedDirection = assemblyConnector.getDirection(sinkIdentifying);

			if (resolvedDirection.equals(AssemblyConnectorRepresentation.Direction.ASSEMBLY)) {
				return new SystemOperationIdentifying(assemblyConnector.requiringContext,
					assemblyConnector.requiredRole.requiredInterface__OperationRequiredRole, sinkIdentifying.signature);
			} else if (resolvedDirection.equals(AssemblyConnectorRepresentation.Direction.OPPOSITE)) {
				return new SystemOperationIdentifying(assemblyConnector.providingContext,
					assemblyConnector.requiredRole.requiredInterface__OperationRequiredRole, sinkIdentifying.signature);
			}
		}
		return null;
	}
}
