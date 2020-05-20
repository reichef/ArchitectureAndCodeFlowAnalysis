package edu.kit.kastel.sdq.pcmjoanaflowanalysis.pcmflow;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.palladiosimulator.pcm.repository.OperationProvidedRole;
import org.palladiosimulator.pcm.repository.OperationRequiredRole;
import org.palladiosimulator.pcm.repository.Role;

public class IntraComponentFlow {
	
	private final SignatureIdentifyingRoleElement<OperationProvidedRole> source;
	private List<SignatureIdentifyingRoleElement<OperationRequiredRole>> sinks;
	
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
	
	
}
