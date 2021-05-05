package edu.kit.kastel.sdq.pcmjoanaflowanalysis.datastructure.flat;

import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;

import edu.kit.kastel.sdq.pcmjoanaflowanalysis.datastructure.FlowBasicComponent;
import edu.kit.kastel.sdq.pcmjoanaflowanalysis.datastructure.hierarchical.AssemblyComponentContext;
import org.palladiosimulator.pcm.system.System;


public class FlatSystem {
	private final String id;
	private final String name;
	private final Collection<Context> contexts;
	private final Collection<AssemblyConnectorRepresentation> connectors;
	private System system;
	
	
	public FlatSystem(System system) {
		contexts = new HashSet<Context>();
		connectors = new HashSet<AssemblyConnectorRepresentation>();
		this.system = system;
		this.id = system.getId();
		this.name = system.getEntityName();
	}
	
	public void addContext(Context context) {
		contexts.add(context);
	}
	
	public String getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}
	
	public System getSystem() {
		return system;
	}
	
	public void addConnector(AssemblyConnectorRepresentation connector) {
		connectors.add(connector);
	}
	
	public Optional<Context> getContext(String id) {
		return contexts.stream().filter(context -> context.getId().equals(id)).findFirst();
	}
	

}
