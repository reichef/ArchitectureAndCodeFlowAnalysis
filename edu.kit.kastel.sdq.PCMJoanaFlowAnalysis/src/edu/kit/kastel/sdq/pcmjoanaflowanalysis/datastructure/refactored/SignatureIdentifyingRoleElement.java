package edu.kit.kastel.sdq.pcmjoanaflowanalysis.datastructure.refactored;

import org.palladiosimulator.pcm.core.entity.InterfaceProvidingRequiringEntity;
import org.palladiosimulator.pcm.repository.OperationSignature;
import org.palladiosimulator.pcm.repository.RepositoryComponent;
import org.palladiosimulator.pcm.repository.Role;

public class SignatureIdentifyingRoleElement<ConcreteRole extends Role> {
	private final InterfaceProvidingRequiringEntity component;
	private final ConcreteRole role;
	private final OperationSignature signature;
	
	public SignatureIdentifyingRoleElement(InterfaceProvidingRequiringEntity component, ConcreteRole role, OperationSignature signature){
		this.component = component;
		this.role = role;
		this.signature = signature;
	}

	public OperationSignature getSignature() {
		return signature;
	}

	public ConcreteRole getRole() {
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
