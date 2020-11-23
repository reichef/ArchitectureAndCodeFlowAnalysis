package edu.kit.kastel.sdq.pcmjoanaflowanalysis.pcmflow;

import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;

import org.eclipse.emf.ecore.util.EcoreUtil;
import org.palladiosimulator.pcm.core.composition.AssemblyConnector;
import org.palladiosimulator.pcm.core.composition.AssemblyContext;
import org.palladiosimulator.pcm.core.composition.ProvidedDelegationConnector;
import org.palladiosimulator.pcm.core.composition.RequiredDelegationConnector;
import org.palladiosimulator.pcm.core.entity.ComposedProvidingRequiringEntity;
import org.palladiosimulator.pcm.core.entity.InterfaceProvidingRequiringEntity;
import org.palladiosimulator.pcm.core.entity.InterfaceRequiringEntity;
import org.palladiosimulator.pcm.repository.BasicComponent;
import org.palladiosimulator.pcm.repository.OperationInterface;
import org.palladiosimulator.pcm.repository.OperationProvidedRole;
import org.palladiosimulator.pcm.repository.OperationRequiredRole;
import org.palladiosimulator.pcm.repository.OperationSignature;
import org.palladiosimulator.pcm.repository.Repository;
import org.palladiosimulator.pcm.repository.RequiredRole;
import org.palladiosimulator.pcm.usagemodel.EntryLevelSystemCall;

import JOANA.FlowSpecification;
import JOANA.JOANAFactory;
import JOANA.JOANARoot;
import JOANA.MethodIdentifying;

import edu.kit.ipd.sdq.composition.securityanalyses.coupling.correspondences.PCM2SourceCode.ElementCorrespondences;
import edu.kit.ipd.sdq.composition.securityanalyses.coupling.structure.SourceCode.SourceCodeRoot;
import edu.kit.kastel.sdq.pcmjoanaflowanalysis.Config;
import edu.kit.kastel.sdq.pcmjoanaflowanalysis.Datastructure.AssemblyComponent;
import edu.kit.kastel.sdq.pcmjoanaflowanalysis.Datastructure.AssemblyConnectorRepresentation;
import edu.kit.kastel.sdq.pcmjoanaflowanalysis.Datastructure.AssemblyRepresentationContainer;
import edu.kit.kastel.sdq.pcmjoanaflowanalysis.Datastructure.CompositeConnectorRepresentation;
import edu.kit.kastel.sdq.pcmjoanaflowanalysis.Datastructure.TopmostAssemblyEntity;
import edu.kit.kastel.sdq.pcmjoanaflowanalysis.analysiscoupling.AnalysisCoupler;
import edu.kit.kastel.sdq.pcmjoanaflowanalysis.correspondences.PCM2SourceCodeCorrespondenceResolver;

import org.palladiosimulator.pcm.core.composition.Connector;

public class PCMComposedEntityFlowAnalyzer {

	private AnalysisCoupler coupler;
	private Collection<IntraComponentFlow> completeFlows;
	private Collection<AssemblyComponent> representation;

	public PCMComposedEntityFlowAnalyzer(Config config) {
		this.coupler = new AnalysisCoupler(config);
		this.completeFlows = new HashSet<IntraComponentFlow>();
	}

	public void flowCalculationForEntryLevelSystemCall(TopmostAssemblyEntity startingEntity,
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
			AssemblyComponent inner = connector.get().getInner();
			OperationProvidedRole innerRole = ((ProvidedDelegationConnector) connector.get().getConnector())
					.getInnerProvidedRole_ProvidedDelegationConnector();
			if (inner.isComposite()) {
				traverseProvidedDelegation(inner, innerRole, operationSignature);
			} else {
				analyzeIntraComponentFlow(inner, innerRole, operationSignature);
			}
		}
	}

	private void analyzeIntraComponentFlow(AssemblyComponent representation, OperationProvidedRole sourceFlowRole,
			OperationSignature sourceSignature) {

		AssemblyContext representationContext = representation.getContext();

		// Call JOANA via coupler
		Collection<String> methodIDsOfFlows = coupler.analyzeIntraComponentFlow(
				representationContext.getEncapsulatedComponent__AssemblyContext(), sourceFlowRole, sourceSignature,
				representation.getClassPath().get());

		SignatureIdentifyingRoleElement<OperationProvidedRole> sourceIdentifying = new SignatureIdentifyingRoleElement<OperationProvidedRole>(
				representationContext.getEncapsulatedComponent__AssemblyContext(), sourceFlowRole, sourceSignature);
		IntraComponentFlow intraComponentFlow = new IntraComponentFlow(sourceIdentifying);
		representation.addIntraComponentFlow(intraComponentFlow);

		// fill Sinks
		for (String id : methodIDsOfFlows) {
			for (RequiredRole role : representationContext.getEncapsulatedComponent__AssemblyContext()
					.getRequiredRoles_InterfaceRequiringEntity()) {
				if (role instanceof OperationRequiredRole) {
					OperationRequiredRole opRole = (OperationRequiredRole) role;
					Optional<OperationSignature> requiredOpSig = getOperationSignatureOfInterfaceById(
							opRole.getRequiredInterface__OperationRequiredRole(), id);

					if (requiredOpSig.isPresent()) {
						SignatureIdentifyingRoleElement<OperationRequiredRole> sinkIdentifying = new SignatureIdentifyingRoleElement<OperationRequiredRole>(
								representationContext.getEncapsulatedComponent__AssemblyContext(), opRole,
								requiredOpSig.get());
						intraComponentFlow.addSink(sinkIdentifying);

						boolean couldMakeAssemblyStep = tryAssemblyStepFromSink(representation, sinkIdentifying,
								requiredOpSig.get());

						boolean couldMakeRequiredDelegationSep = false;
						if (!couldMakeAssemblyStep) {
							 couldMakeAssemblyStep = tryDelegationStepFromSink(representation, sinkIdentifying, requiredOpSig.get());
						}
						
						if(!couldMakeRequiredDelegationSep) {
							System.out.println(String.format("Flow Finished in Component %s, Method %s", sinkIdentifying.getComponent().getEntityName(), sinkIdentifying.getSignature().getEntityName()));
						}
					}
				}
			}
		}
	}

	private boolean tryAssemblyStepFromSink(AssemblyRepresentationContainer representation,
			SignatureIdentifyingRoleElement<OperationRequiredRole> sinkIdentifying, OperationSignature signature) {
		Optional<AssemblyConnectorRepresentation> assemblyConnector = representation
				.getAssemblyConnectorRepresentationForSink(sinkIdentifying);
		boolean stepPossible = assemblyConnector.isPresent();
		if (stepPossible) {
			CallInformation callInfo = new CallInformation(assemblyConnector.get(), signature);
			processCallInformation(callInfo);
		}

		return stepPossible;
	}

	private boolean tryDelegationStepFromSink(AssemblyComponent representation,
			SignatureIdentifyingRoleElement<OperationRequiredRole> sinkIdentifying, OperationSignature signature) {

		Optional<CompositeConnectorRepresentation> compositeConnector = representation
				.getDelegationConnectorRepresentationForRequiredRoleIdentifyingFromInnerRole(sinkIdentifying);
		boolean stepPossible = compositeConnector.isPresent();

		if (stepPossible) {
			AssemblyRepresentationContainer parentComponent = compositeConnector.get().getOuter();
			RequiredDelegationConnector requiredDelegation = (RequiredDelegationConnector) compositeConnector.get()
					.getConnector();
			SignatureIdentifyingRoleElement<OperationRequiredRole> outerIdentifyingRoleElement = new SignatureIdentifyingRoleElement<OperationRequiredRole>(
					(InterfaceProvidingRequiringEntity) requiredDelegation
							.getOuterRequiredRole_RequiredDelegationConnector().getRequiringEntity_RequiredRole(),
					requiredDelegation.getOuterRequiredRole_RequiredDelegationConnector(), signature);
			
			stepPossible = tryAssemblyStepFromSink(parentComponent, outerIdentifyingRoleElement, signature);
			

		}

		return stepPossible;
	}

	private Optional<OperationSignature> getOperationSignatureOfInterfaceById(OperationInterface operationInterface,
			String operationSignatureId) {
		Optional<OperationSignature> opSig = Optional.empty();
		for (OperationSignature sig : operationInterface.getSignatures__OperationInterface()) {
			if (sig.getId().equals(operationSignatureId)) {
				opSig = Optional.ofNullable(sig);
				return opSig;
			}
		}
		return opSig;
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
