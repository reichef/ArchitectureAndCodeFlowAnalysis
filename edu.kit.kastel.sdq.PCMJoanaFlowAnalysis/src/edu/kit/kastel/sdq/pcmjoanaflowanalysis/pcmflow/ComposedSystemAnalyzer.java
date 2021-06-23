package edu.kit.kastel.sdq.pcmjoanaflowanalysis.pcmflow;

import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;

import org.palladiosimulator.pcm.core.composition.AssemblyContext;
import org.palladiosimulator.pcm.core.composition.ProvidedDelegationConnector;
import org.palladiosimulator.pcm.core.composition.RequiredDelegationConnector;
import org.palladiosimulator.pcm.core.entity.InterfaceProvidingRequiringEntity;
import org.palladiosimulator.pcm.repository.OperationInterface;
import org.palladiosimulator.pcm.repository.OperationProvidedRole;
import org.palladiosimulator.pcm.repository.OperationRequiredRole;
import org.palladiosimulator.pcm.repository.OperationSignature;
import org.palladiosimulator.pcm.repository.ProvidedRole;
import org.palladiosimulator.pcm.repository.RequiredRole;
import org.palladiosimulator.pcm.usagemodel.EntryLevelSystemCall;
import org.palladiosimulator.pcm.system.System;

import edu.kit.kastel.sdq.pcmjoanaflowanalysis.Config;
import edu.kit.kastel.sdq.pcmjoanaflowanalysis.analysiscoupling.AnalysisCoupler;
import edu.kit.kastel.sdq.pcmjoanaflowanalysis.datastructure.hierarchical.AssemblyComponentContext;
import edu.kit.kastel.sdq.pcmjoanaflowanalysis.datastructure.hierarchical.AssemblyConnectorRepresentation;
import edu.kit.kastel.sdq.pcmjoanaflowanalysis.datastructure.hierarchical.AssemblyRepresentationContainer;
import edu.kit.kastel.sdq.pcmjoanaflowanalysis.datastructure.hierarchical.CompositeConnectorRepresentation;
import edu.kit.kastel.sdq.pcmjoanaflowanalysis.datastructure.hierarchical.FlowBasicComponent;
import edu.kit.kastel.sdq.pcmjoanaflowanalysis.datastructure.hierarchical.SystemRepresentation;
import edu.kit.kastel.sdq.pcmjoanaflowanalysis.pcmutil.PCMRepositoryElementResolver;


public class ComposedSystemAnalyzer {

	private AnalysisCoupler coupler;
	
	public ComposedSystemAnalyzer(AnalysisCoupler coupler) {
		this.coupler = coupler;
	}

	public void flowCalculationForEntryLevelSystemCall(SystemRepresentation startingEntity,
			EntryLevelSystemCall call) {
		OperationSignature startingSignature = call.getOperationSignature__EntryLevelSystemCall();
		OperationProvidedRole startingRole = call.getProvidedRole_EntryLevelSystemCall();

		traverseProvidedDelegation(startingEntity, startingRole, startingSignature);
	}
	

	private void traverseProvidedDelegation(AssemblyRepresentationContainer representation, OperationProvidedRole role,
			OperationSignature operationSignature) {
		// TODO: Following resolution could also be done representation internal, after
		// prototyping think about refactoring
		Optional<CompositeConnectorRepresentation> connector = representation
				.searchProvidedDelegationInOuterForSigAndProvRole(role);

		if (connector.isPresent()) {
			AssemblyComponentContext inner = connector.get().getInner();
			OperationProvidedRole innerRole = ((ProvidedDelegationConnector) connector.get().getConnector())
					.getInnerProvidedRole_ProvidedDelegationConnector();
			if (inner.isComposite()) {
				traverseProvidedDelegation(inner, innerRole, operationSignature);
			} else {
				analyzeIntraComponentFlow(inner, innerRole, operationSignature);
			}
		}
	}

	private void analyzeIntraComponentFlow(AssemblyComponentContext representation, OperationProvidedRole sourceFlowRole,
			OperationSignature sourceSignature) {

		AssemblyContext representationContext = representation.getContext();

		// Call JOANA via coupler
		Collection<String> methodIDsOfFlows = coupler.analyzeIntraComponentFlow(
				representationContext.getEncapsulatedComponent__AssemblyContext(), sourceFlowRole, sourceSignature,
				representation.getClassPath().get());

		SignatureIdentifyingRoleElement sourceIdentifying = new SignatureIdentifyingRoleElement(
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
					OperationRequiredRole opRole = (OperationRequiredRole) role;
					Optional<OperationSignature> requiredOpSig = PCMRepositoryElementResolver.getOperationSignatureOfInterfaceById(
							opRole.getRequiredInterface__OperationRequiredRole(), id);

					if (requiredOpSig.isPresent()) {
						SignatureIdentifyingRoleElement sinkIdentifying = new SignatureIdentifyingRoleElement(
								representationContext.getEncapsulatedComponent__AssemblyContext(), opRole,
								requiredOpSig.get());
						
						//System.out.printf("To Sink %s.%s \n", sinkIdentifying.getComponent().getEntityName(), sinkIdentifying.getSignature().getEntityName());
						
						representation.addIntraComponentFlow(sourceIdentifying, sinkIdentifying);

						boolean couldMakeAssemblyStep = tryAssemblyStepFromSink(representation, sinkIdentifying,
								requiredOpSig.get());

						boolean couldMakeRequiredDelegationSep = false;
						if (!couldMakeAssemblyStep) {
							 couldMakeAssemblyStep = tryDelegationStepFromSink(representation, sinkIdentifying, requiredOpSig.get());
						}
						
						if(!couldMakeRequiredDelegationSep) {
							//System.out.printf("Flow Finished in Component %s, Method %s\n", sinkIdentifying.getComponent().getEntityName(), sinkIdentifying.getSignature().getEntityName());
						}
					}
				}
			}
		}
	}

	private boolean tryAssemblyStepFromSink(AssemblyRepresentationContainer representation,
			SignatureIdentifyingRoleElement sinkIdentifying, OperationSignature signature) {
		Optional<AssemblyConnectorRepresentation> assemblyConnector = representation
				.getAssemblyConnectorRepresentationForSink(sinkIdentifying);
		boolean stepPossible = assemblyConnector.isPresent();
		if (stepPossible) {
			CallInformation callInfo = new CallInformation(assemblyConnector.get(), signature);
			processCallInformation(callInfo);
		}

		return stepPossible;
	}

	private boolean tryDelegationStepFromSink(AssemblyComponentContext representation,
			SignatureIdentifyingRoleElement sinkIdentifying, OperationSignature signature) {

		Optional<CompositeConnectorRepresentation> compositeConnector = representation
				.getDelegationConnectorRepresentationForRequiredRoleIdentifyingFromInnerRole(sinkIdentifying);
		boolean stepPossible = compositeConnector.isPresent();

		if (stepPossible) {
			AssemblyRepresentationContainer parentComponent = compositeConnector.get().getOuter();
			RequiredDelegationConnector requiredDelegation = (RequiredDelegationConnector) compositeConnector.get()
					.getConnector();
			SignatureIdentifyingRoleElement outerIdentifyingRoleElement = new SignatureIdentifyingRoleElement(
					(InterfaceProvidingRequiringEntity) requiredDelegation
							.getOuterRequiredRole_RequiredDelegationConnector().getRequiringEntity_RequiredRole(),
					requiredDelegation.getOuterRequiredRole_RequiredDelegationConnector(), signature);
			
			stepPossible = tryAssemblyStepFromSink(parentComponent, outerIdentifyingRoleElement, signature);
			

		}

		return stepPossible;
	}

	private void processCallInformation(CallInformation callInformation) {
		AssemblyConnectorRepresentation connector = callInformation.getAssemblyConnector();
		OperationSignature signature = callInformation.getCalledOperation();

		if (connector.getProviding().encapsulatedContextProvidesRole(connector.getProvidedRole())) {
			if (connector.getProviding().isComposite()) {
				traverseProvidedDelegation(connector.getProviding(), connector.getProvidedRole(), signature);
			} else {
				analyzeIntraComponentFlow(connector.getProviding(), connector.getProvidedRole(), signature);
			}
		}
	}
}
