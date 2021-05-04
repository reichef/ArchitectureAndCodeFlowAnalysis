package edu.kit.kastel.sdq.pcmjoanaflowanalysis.datastructure.hierarchical;

import org.palladiosimulator.pcm.core.composition.DelegationConnector;
import org.palladiosimulator.pcm.core.composition.ProvidedDelegationConnector;
import org.palladiosimulator.pcm.core.composition.RequiredDelegationConnector;

public class CompositeConnectorRepresentation{
	
	private DelegationConnector delegationConnector;
	private AssemblyRepresentationContainer outer;
	private AssemblyComponentContext inner;
	
	public CompositeConnectorRepresentation(DelegationConnector connector, AssemblyRepresentationContainer outer, AssemblyComponentContext inner) {
		this.delegationConnector = connector;
		this.inner = inner;
		this.outer = outer;
	}

	public DelegationConnector getConnector() {
		return delegationConnector;
	}
	
	public AssemblyRepresentationContainer getOuter() {
		return outer;
	}
	
	public AssemblyComponentContext getInner() {
		return inner;
	}
	
	public String getId() {
		return delegationConnector.getId();
	}
	
	public String getName() {
		return delegationConnector.getEntityName();
	}
	
	public boolean fitting(AssemblyComponentContext outer,AssemblyComponentContext inner) {
		return this.outer.getId().equals(outer.getId()) && this.inner.getId().equals(inner.getId());
	}
	
	public String connectorRepresentation() {
		if(delegationConnector instanceof ProvidedDelegationConnector) {
			ProvidedDelegationConnector provDelCon = (ProvidedDelegationConnector) delegationConnector;
			String innerInterfName = provDelCon.getInnerProvidedRole_ProvidedDelegationConnector().getProvidedInterface__OperationProvidedRole().getEntityName();
			String outerInterfName = provDelCon.getOuterProvidedRole_ProvidedDelegationConnector().getProvidedInterface__OperationProvidedRole().getEntityName();
			return String.format(" %s.%s O-| -> O- %s.%s", outer.getName(),  outerInterfName, inner.getName(), innerInterfName );
		} else if (delegationConnector instanceof RequiredDelegationConnector){
			RequiredDelegationConnector reqDelCon = (RequiredDelegationConnector) delegationConnector;
			return String.format(" %s.%s -( -> |-( %s.%s", inner.getName(), reqDelCon.getInnerRequiredRole_RequiredDelegationConnector().getRequiredInterface__OperationRequiredRole().getEntityName() , outer.getName(), reqDelCon.getOuterRequiredRole_RequiredDelegationConnector().getRequiredInterface__OperationRequiredRole().getEntityName());
		}
		return "";
	}
}
