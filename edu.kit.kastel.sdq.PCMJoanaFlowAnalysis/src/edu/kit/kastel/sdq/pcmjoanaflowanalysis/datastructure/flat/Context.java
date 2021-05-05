package edu.kit.kastel.sdq.pcmjoanaflowanalysis.datastructure.flat;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import org.palladiosimulator.pcm.core.composition.AssemblyContext;

import edu.kit.kastel.sdq.pcmjoanaflowanalysis.datastructure.FlowBasicComponent;
import edu.kit.kastel.sdq.pcmjoanaflowanalysis.pcmflow.IntraComponentFlow;

public class Context {
	private final String id;
	private final String name;
	private final AssemblyContext context;
	private Collection<IntraComponentFlow> systemInducedFlows;
	private final FlowBasicComponent component;
	private Map<String, String> annotations;
	
	public Context(AssemblyContext context, FlowBasicComponent component) {
		this.id = context.getId();
		this.name = context.getEntityName();
		this.context = context;
		this.systemInducedFlows = new HashSet<IntraComponentFlow>();
		this.component = component;
		this.annotations = new HashMap<String, String>();
	}

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public AssemblyContext getContext() {
		return context;
	}

	public FlowBasicComponent getComponent() {
		return component;
	}

	
}
