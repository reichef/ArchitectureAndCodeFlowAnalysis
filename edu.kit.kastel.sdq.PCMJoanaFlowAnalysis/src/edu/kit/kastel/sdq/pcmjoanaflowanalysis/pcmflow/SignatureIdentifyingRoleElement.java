package edu.kit.kastel.sdq.pcmjoanaflowanalysis.pcmflow;

import org.palladiosimulator.pcm.repository.OperationSignature;
import org.palladiosimulator.pcm.repository.RepositoryComponent;
import org.palladiosimulator.pcm.repository.Role;

public class SignatureIdentifyingRoleElement<ConcreteRole extends Role> {
	private final RepositoryComponent component;
	private final ConcreteRole role;
	private final OperationSignature signature;
	
	public SignatureIdentifyingRoleElement(RepositoryComponent component, ConcreteRole role, OperationSignature signature){
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

	public RepositoryComponent getComponent() {
		return component;
	}
	
	
	public boolean identyfyingEquals(RepositoryComponent component, Role role) {
		return this.component.getId().equals(component.getId()) && this.role.getId().equals(role.getId());
	}
	
	public boolean identyfyingEquals(RepositoryComponent component, Role role, OperationSignature signature) {
		return identyfyingEquals(component, role) && this.signature.getId().equals(signature.getId());
	}
	
	public boolean identyfyingEquals(SignatureIdentifyingRoleElement signatureIdentifying) {
		return identyfyingEquals(signatureIdentifying.getComponent(), signatureIdentifying.getRole(), signatureIdentifying.getSignature());
	}
	
}
