package edu.kit.kastel.sdq.pcmjoanaflowanalysis.pcmflow.fixpoint

import java.util.Queue
import edu.kit.kastel.sdq.pcmjoanaflowanalysis.datastructure.hierarchical.AssemblyComponentContext
import java.util.PriorityQueue
import edu.kit.kastel.sdq.pcmjoanaflowanalysis.analysiscoupling.AnalysisCoupler
import org.palladiosimulator.pcm.repository.OperationProvidedRole
import org.palladiosimulator.pcm.repository.OperationSignature
import edu.kit.kastel.sdq.pcmjoanaflowanalysis.pcmutil.PCMRepositoryElementResolver
import org.palladiosimulator.pcm.repository.OperationRequiredRole
import edu.kit.kastel.sdq.pcmjoanaflowanalysis.pcmflow.SignatureIdentifyingRoleElement
import org.palladiosimulator.pcm.repository.RequiredRole
import edu.kit.kastel.sdq.pcmjoanaflowanalysis.pcmflow.CallInformation
import edu.kit.kastel.sdq.pcmjoanaflowanalysis.datastructure.hierarchical.AssemblyRepresentationContainer

//For ease of problem, we assume a flat system at the moment until fully integrating flat data structure.  
class FixpointAnalysis {
	private Queue<SystemOperationIdentifying> worklist;
	private AnalysisCoupler coupler;
	
	new(){
		worklist = new PriorityQueue;
	}
	
	def void analyzeIntraComponentFlow(AssemblyComponentContext representation, OperationProvidedRole sourceFlowRole,
			OperationSignature sourceSignature) {

		var representationContext = representation.getContext();

		// Call JOANA via coupler
		var methodIDsOfFlows = coupler.analyzeIntraComponentFlow(
				representationContext.getEncapsulatedComponent__AssemblyContext(), sourceFlowRole, sourceSignature,
				representation.getClassPath().get());

		var sourceIdentifying = new SignatureIdentifyingRoleElement(
				representationContext.getEncapsulatedComponent__AssemblyContext(), sourceFlowRole, sourceSignature);
		
		if(methodIDsOfFlows.isEmpty()) {
			//System.out.printf("Flow Finished in Component %s, Method %s\n", sourceIdentifying.getComponent().getEntityName(), sourceIdentifying.getSignature().getEntityName());
		}
		
		
		//System.out.println("!!!!");
		//System.out.printf("From Source %s.%s \n", sourceIdentifying.getComponent().getEntityName(), sourceIdentifying.getSignature().getEntityName());

		// fill Sinks
		for (String id : methodIDsOfFlows) {
			for (RequiredRole role : representationContext.getEncapsulatedComponent__AssemblyContext()
					.getRequiredRoles_InterfaceRequiringEntity()) {
				if (role instanceof OperationRequiredRole) {
					var opRole = role as OperationRequiredRole;
					var requiredOpSig = PCMRepositoryElementResolver.getOperationSignatureOfInterfaceById(
							opRole.getRequiredInterface__OperationRequiredRole(), id);

					if (requiredOpSig.isPresent()) {
						var sinkIdentifying = new SignatureIdentifyingRoleElement(
								representationContext.getEncapsulatedComponent__AssemblyContext(), opRole,
								requiredOpSig.get());
						
						//System.out.printf("To Sink %s.%s \n", sinkIdentifying.getComponent().getEntityName(), sinkIdentifying.getSignature().getEntityName());
						
						representation.addIntraComponentFlow(sourceIdentifying, sinkIdentifying);

						tryAssemblyStepFromSink(representation, sinkIdentifying,requiredOpSig.get());
					}
				}
			}
		}
	}
	
	def private boolean tryAssemblyStepFromSink(AssemblyRepresentationContainer representation,
			SignatureIdentifyingRoleElement sinkIdentifying, OperationSignature signature) {
		var assemblyConnector = representation
				.getAssemblyConnectorRepresentationForSink(sinkIdentifying);
		var stepPossible = assemblyConnector.isPresent();
		if (stepPossible) {
			var callInfo = new CallInformation(assemblyConnector.get(), signature);
			processCallInformation(callInfo);
		}

		return stepPossible;
	}
	
	def void processCallInformation(CallInformation callInformation) {
		var connector = callInformation.getAssemblyConnector();
		var signature = callInformation.getCalledOperation();

		if (connector.getProviding().encapsulatedContextProvidesRole(connector.getProvidedRole())) {
			analyzeIntraComponentFlow(connector.getProviding(), connector.getProvidedRole(), signature);
		}
	}
	
}