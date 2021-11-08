package edu.kit.kastel.sdq.pcmjoanaflowanalysis.pcmflow;

import java.util.ArrayList;
import java.util.List;

import org.palladiosimulator.pcm.repository.OperationProvidedRole;
import org.palladiosimulator.pcm.repository.OperationRequiredRole;

import edu.kit.joana.component.connector.Flows;

public class IntraComponentFlow {
	
	private final SignatureIdentifyingRoleElement<OperationProvidedRole> source;
	private List<SignatureIdentifyingRoleElement<OperationRequiredRole>> sinks;
	private Flows joanaFlow;
	
	public IntraComponentFlow(SignatureIdentifyingRoleElement<OperationProvidedRole> source){
		this.source = source;
		this.sinks = new ArrayList<SignatureIdentifyingRoleElement<OperationRequiredRole>>();
	}
	
	public List<SignatureIdentifyingRoleElement<OperationRequiredRole>> getSinks() {
		return sinks;
	}

	public SignatureIdentifyingRoleElement<OperationProvidedRole> getSource() {
		return source;
	}
	
	public void addSink(SignatureIdentifyingRoleElement<OperationRequiredRole> sink) {
		sinks.add(sink);
	}
	
	public void setFlows(Flows joanaFlow) {
		this.joanaFlow = joanaFlow;
	}
	
	public Flows getFlows() {
		return joanaFlow;
	}
	
}
