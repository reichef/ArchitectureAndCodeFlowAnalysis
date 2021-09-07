package edu.kit.kastel.sdq.pcmjoanaflowanalysis.datastructure.refactored;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import org.palladiosimulator.pcm.repository.BasicComponent;

import edu.kit.kastel.sdq.pcmjoanaflowanalysis.pcmflow.IntraComponentFlow;

public class FlowBasicComponent {

	private final String name;
	private final String id;
	private Collection<IntraComponentFlow> componentInternalFlows;
	private final BasicComponent component;
	private Map<String, String> annotations;
	
	public FlowBasicComponent(BasicComponent component) {
		this.id = component.getId();
		this.name = component.getEntityName();
		this.component = component;
		this.componentInternalFlows = new HashSet<>();
		this.annotations = new HashMap<>();
	}
	
	public String getName() {
		return name;
	}
	
	public String getId() {
		return id;
	}
	
	public Collection<IntraComponentFlow> getComponentInternalFlows() {
		return componentInternalFlows;
	}
	
	public void setComponentInternalFlows(Collection<IntraComponentFlow> componentInternalFlows) {
		this.componentInternalFlows = componentInternalFlows;
	}

	public BasicComponent getComponent() {
		return component;
	}
	
	public String getAnnotation(String type) {
		return annotations.get(type);
	}
	
	public void setAnnotation(String type, String content) {
		annotations.put(type, content);
	}
	
}
