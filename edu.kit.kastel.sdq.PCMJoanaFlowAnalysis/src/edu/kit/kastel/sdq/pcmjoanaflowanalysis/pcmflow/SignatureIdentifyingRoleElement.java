package edu.kit.kastel.sdq.pcmjoanaflowanalysis.pcmflow;

import org.palladiosimulator.pcm.core.entity.InterfaceProvidingRequiringEntity;
import org.palladiosimulator.pcm.repository.OperationSignature;
import org.palladiosimulator.pcm.repository.Role;

public class SignatureIdentifyingRoleElement {
	private final InterfaceProvidingRequiringEntity component;
	private final Role role;
	private final OperationSignature signature;
	
	public SignatureIdentifyingRoleElement(InterfaceProvidingRequiringEntity component, Role role, OperationSignature signature){
		this.component = component;
		this.role = role;
		this.signature = signature;
	}

	public OperationSignature getSignature() {
		return signature;
	}

	public Role getRole() {
		return role;
	}

	public InterfaceProvidingRequiringEntity getComponent() {
		return component;
	}
	
	
	public boolean identyfyingEquals(InterfaceProvidingRequiringEntity component, Role role) {
		return this.component.getId().equals(component.getId()) && this.role.getId().equals(role.getId());
	}
	
	public boolean identyfyingEquals(InterfaceProvidingRequiringEntity component, Role role, OperationSignature signature) {
		return identyfyingEquals(component, role) && this.signature.getId().equals(signature.getId());
	}
	
	public boolean identyfyingEquals(SignatureIdentifyingRoleElement signatureIdentifying) {
		return identyfyingEquals(signatureIdentifying.getComponent(), signatureIdentifying.getRole(), signatureIdentifying.getSignature());
	}
	
}
