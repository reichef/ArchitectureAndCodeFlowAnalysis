package edu.kit.kastel.sdq.pcmjoanaflowanalysis.datastructure.flat;

import org.palladiosimulator.pcm.core.composition.AssemblyConnector;

public class AssemblyConnectorRepresentation {
	private final Context providing;
	private final Context requiring;
	private final String providedRoleId;
	private final String requiredRoleId;
	
	public AssemblyConnectorRepresentation(Context providing, Context requiring, AssemblyConnector connector) {
		this.providing = providing;
		this.requiring = requiring;
		this.providedRoleId = connector.getProvidedRole_AssemblyConnector().getId();
		this.requiredRoleId = connector.getRequiredRole_AssemblyConnector().getId();
	}
	
	public AssemblyConnectorRepresentation(Context providing, Context requiring, String providingRoleId, String requiringRoleId) {
		this.providing = providing;
		this.requiring = requiring;
		this.providedRoleId = providingRoleId;
		this.requiredRoleId = requiringRoleId;
	}

	public Context getProviding() {
		return providing;
	}

	public Context getRequiring() {
		return requiring;
	}

	public String getProvidedRoleId() {
		return providedRoleId;
	}

	public String getRequiredRoleId() {
		return requiredRoleId;
	}
	
}
