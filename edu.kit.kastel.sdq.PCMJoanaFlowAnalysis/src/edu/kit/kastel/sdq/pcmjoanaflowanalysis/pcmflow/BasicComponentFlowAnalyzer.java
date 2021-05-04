package edu.kit.kastel.sdq.pcmjoanaflowanalysis.pcmflow;

import java.util.Collection;
import java.util.Optional;

import org.palladiosimulator.pcm.repository.OperationProvidedRole;
import org.palladiosimulator.pcm.repository.OperationRequiredRole;
import org.palladiosimulator.pcm.repository.OperationSignature;
import org.palladiosimulator.pcm.repository.ProvidedRole;
import org.palladiosimulator.pcm.repository.RequiredRole;

import edu.kit.kastel.sdq.pcmjoanaflowanalysis.Config;
import edu.kit.kastel.sdq.pcmjoanaflowanalysis.analysiscoupling.AnalysisCoupler;
import edu.kit.kastel.sdq.pcmjoanaflowanalysis.datastructure.hierarchical.FlowBasicComponent;
import edu.kit.kastel.sdq.pcmjoanaflowanalysis.pcmutil.PCMRepositoryElementResolver;
import edu.kit.kastel.sdq.pcmjoanaflowanalysis.pcmutil.PCMSubtypeResolver;

public class BasicComponentFlowAnalyzer {

	private AnalysisCoupler coupler;

	public BasicComponentFlowAnalyzer(Config config) {
		this.coupler = new AnalysisCoupler(config);
	}

	public BasicComponentFlowAnalyzer(AnalysisCoupler coupler) {
		this.coupler = coupler;
	}

	public void analyseAllComponents(Collection<FlowBasicComponent> components) {
		components.stream().forEach(componet -> analyseAllIntraComponentFlowsForComponent(componet));
	}
	
	private void analyseAllIntraComponentFlowsForComponent(FlowBasicComponent component) {

		for (ProvidedRole provRole : component.getComponent().getProvidedRoles_InterfaceProvidingEntity()) {
			if (provRole instanceof OperationProvidedRole) {
				OperationProvidedRole opProvRole = (OperationProvidedRole) provRole;
				for (OperationSignature signature : opProvRole.getProvidedInterface__OperationProvidedRole()
						.getSignatures__OperationInterface()) {
					analyseIntraComponentFlow(component, opProvRole, signature);
				}
			}
		}
	}

	private void analyseIntraComponentFlow(FlowBasicComponent component, OperationProvidedRole opProvRole,
			OperationSignature signature) {
		Collection<String> methodIDsOfFlows = coupler.analyzeIntraComponentFlow(component.getComponent(), opProvRole,
				signature, component.getClassPath().get());

		SignatureIdentifyingRoleElement<OperationProvidedRole> sourceIdentifying = new SignatureIdentifyingRoleElement<OperationProvidedRole>(
				component.getComponent(), opProvRole, signature);

		IntraComponentFlow intraComponentFlow = new IntraComponentFlow(sourceIdentifying);

		if (!methodIDsOfFlows.isEmpty()) {
			for (String id : methodIDsOfFlows) {
				for (OperationRequiredRole role : PCMSubtypeResolver.filterOperationRequiredRoles(
						component.getComponent().getRequiredRoles_InterfaceRequiringEntity())) {
					OperationRequiredRole opRole = (OperationRequiredRole) role;
					Optional<OperationSignature> requiredOpSig = PCMRepositoryElementResolver
							.getOperationSignatureOfInterfaceById(opRole.getRequiredInterface__OperationRequiredRole(),
									id);

					if (requiredOpSig.isPresent()) {
						SignatureIdentifyingRoleElement<OperationRequiredRole> sinkIdentifying = new SignatureIdentifyingRoleElement<OperationRequiredRole>(
								component.getComponent(), opRole, requiredOpSig.get());

						intraComponentFlow.addSink(sinkIdentifying);
					}
				}
			}
		}

		component.addIntraComponentFlow(intraComponentFlow);

	}

}
