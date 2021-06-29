package edu.kit.kastel.sdq.pcmjoanaflowanalysis.datastructure.hierarchical;

import java.util.Optional;

import org.palladiosimulator.pcm.core.entity.ComposedProvidingRequiringEntity;
import org.palladiosimulator.pcm.usagemodel.EntryLevelSystemCall;

import edu.kit.kastel.sdq.ecoreannotations.AnnotationRepository;
import edu.kit.kastel.sdq.pcmjoanaflowanalysis.pcmflow.fixpoint.SystemOperationIdentifying;

public class SystemRepresentation extends AssemblyRepresentationContainer {

	private ComposedProvidingRequiringEntity entity;

	
	public SystemRepresentation(ComposedProvidingRequiringEntity topMostEntity) {
		super(topMostEntity.getId(), topMostEntity.getEntityName());
		this.entity = topMostEntity;
	}
	

	public ComposedProvidingRequiringEntity getEncapsulatedEntity() {
		return entity;
	}
	
	public SystemOperationIdentifying getOperationIdentifyingOfComponentForExternalCall(EntryLevelSystemCall call) {

		CompositeConnectorRepresentation connector= searchProvidedDelegationInOuterForSigAndProvRole(call.getProvidedRole_EntryLevelSystemCall()).get();

		
		
		return new SystemOperationIdentifying(connector.getInner(), call.getProvidedRole_EntryLevelSystemCall().getProvidedInterface__OperationProvidedRole(), call.getOperationSignature__EntryLevelSystemCall());
		
	}
	
	
	@Override
	public void fillWithClassPath(AnnotationRepository repository, Optional<String> latestClassPath) {
		for(AssemblyComponentContext representation : containedRepresentations) {
			representation.fillWithClassPath(repository, latestClassPath);
		}
	}
	
	@Override
	public void printRepresentation() {
		containedRepresentations.forEach(a -> a.printRepresentation());
	}
}
