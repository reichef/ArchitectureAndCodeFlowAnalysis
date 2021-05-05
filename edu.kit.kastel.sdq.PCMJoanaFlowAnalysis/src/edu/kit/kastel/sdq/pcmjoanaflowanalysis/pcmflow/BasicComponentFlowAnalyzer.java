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
import edu.kit.kastel.sdq.pcmjoanaflowanalysis.datastructure.FlowBasicComponent;
import edu.kit.kastel.sdq.pcmjoanaflowanalysis.pcmutil.PCMRepositoryElementResolver;
import edu.kit.kastel.sdq.pcmjoanaflowanalysis.pcmutil.ModelsSubtypeResolver;

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

		for (OperationProvidedRole provRole : ModelsSubtypeResolver
				.filterOperationProvidedRoles(component.getComponent().getProvidedRoles_InterfaceProvidingEntity())) {
			OperationProvidedRole opProvRole = (OperationProvidedRole) provRole;
			for (OperationSignature signature : opProvRole.getProvidedInterface__OperationProvidedRole()
					.getSignatures__OperationInterface()) {
				analyseIntraComponentFlow(component, opProvRole, signature);
			}
		}
	}

	private void analyseIntraComponentFlow(FlowBasicComponent component, OperationProvidedRole opProvRole,
			OperationSignature signature) {
		Collection<OperationSignature> methodIDsOfFlows = coupler.analyzeIntraComponentFlow(component.getComponent(),
				opProvRole, signature, component.getClassPath().get());

		SignatureIdentifyingRoleElement<OperationProvidedRole> sourceIdentifying = new SignatureIdentifyingRoleElement<OperationProvidedRole>(
				component.getComponent(), opProvRole, signature);

		IntraComponentFlow intraComponentFlow = new IntraComponentFlow(sourceIdentifying);

		if (!methodIDsOfFlows.isEmpty()) {
			for (OperationSignature opSig : methodIDsOfFlows) {
				for (OperationRequiredRole role : ModelsSubtypeResolver.filterOperationRequiredRoles(
						component.getComponent().getRequiredRoles_InterfaceRequiringEntity())) {

					if (PCMRepositoryElementResolver
							.interfaceContainsSignature(role.getRequiredInterface__OperationRequiredRole(), opSig)) {
						SignatureIdentifyingRoleElement<OperationRequiredRole> sinkIdentifying = new SignatureIdentifyingRoleElement<OperationRequiredRole>(
								component.getComponent(), role, opSig);

						intraComponentFlow.addSink(sinkIdentifying);
					}
				}
			}
		}

		component.addIntraComponentFlow(intraComponentFlow);

	}

}
