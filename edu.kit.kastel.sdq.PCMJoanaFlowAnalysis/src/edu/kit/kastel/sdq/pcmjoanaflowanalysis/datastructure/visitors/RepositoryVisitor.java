package edu.kit.kastel.sdq.pcmjoanaflowanalysis.datastructure.visitors;

import org.palladiosimulator.pcm.core.composition.ComposedStructure;
import org.palladiosimulator.pcm.core.entity.ComposedProvidingRequiringEntity;
import org.palladiosimulator.pcm.core.entity.InterfaceProvidingRequiringEntity;
import org.palladiosimulator.pcm.repository.BasicComponent;
import org.palladiosimulator.pcm.repository.CompositeComponent;
import org.palladiosimulator.pcm.repository.OperationProvidedRole;
import org.palladiosimulator.pcm.repository.OperationRequiredRole;
import org.palladiosimulator.pcm.repository.ProvidedRole;
import org.palladiosimulator.pcm.repository.RequiredRole;
import org.palladiosimulator.pcm.repository.Role;
import org.palladiosimulator.pcm.repository.util.RepositorySwitch;

public class RepositoryVisitor extends RepositorySwitch<Object> {

	public RepositoryVisitor() {
		
	}

	@Override
	public Object caseBasicComponent(BasicComponent object) {
		// TODO Auto-generated method stub
		return super.caseBasicComponent(object);
	}

	@Override
	public Object caseProvidedRole(ProvidedRole object) {
		// TODO Auto-generated method stub
		return super.caseProvidedRole(object);
	}

	@Override
	public Object caseRequiredRole(RequiredRole object) {
		// TODO Auto-generated method stub
		return super.caseRequiredRole(object);
	}

	@Override
	public Object caseOperationRequiredRole(OperationRequiredRole object) {
		// TODO Auto-generated method stub
		return super.caseOperationRequiredRole(object);
	}

	@Override
	public Object caseOperationProvidedRole(OperationProvidedRole object) {
		// TODO Auto-generated method stub
		return super.caseOperationProvidedRole(object);
	}

	@Override
	public Object caseCompositeComponent(CompositeComponent object) {
		// TODO Auto-generated method stub
		return super.caseCompositeComponent(object);
	}

	@Override
	public Object caseInterfaceProvidingRequiringEntity(InterfaceProvidingRequiringEntity object) {
		// TODO Auto-generated method stub
		return super.caseInterfaceProvidingRequiringEntity(object);
	}

	@Override
	public Object caseComposedStructure(ComposedStructure object) {
		// TODO Auto-generated method stub
		return super.caseComposedStructure(object);
	}

	@Override
	public Object caseComposedProvidingRequiringEntity(ComposedProvidingRequiringEntity object) {
		// TODO Auto-generated method stub
		return super.caseComposedProvidingRequiringEntity(object);
	}
	
}
