package edu.kit.kastel.sdq.pcmjoanaflowanalysis.datastructure.refactored;

import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;

import org.palladiosimulator.pcm.repository.BasicComponent;

import edu.kit.kastel.sdq.pcmjoanaflowanalysis.pcmflow.IntraComponentFlow;

public class FlowBasicComponent extends Annotated {

	private final String name;
	private final String id;
	private final BasicComponent component;
	private Collection<IntraComponentFlow> componentInternalFlows;
	
	public FlowBasicComponent(BasicComponent component) {
		super();
		this.id = component.getId();
		this.name = component.getEntityName();
		this.component = component;
		this.componentInternalFlows = new HashSet<>();
	}
	
	public String getName() {
		return name;
	}
	
	public String getId() {
		return id;
	}

	public BasicComponent getComponent() {
		return component;
	}
	
	public Collection<IntraComponentFlow> getComponentInternalFlows() {
		return componentInternalFlows;
	}
	
	public void addComponentInternalFlow(IntraComponentFlow flow) {
		componentInternalFlows.add(flow);
	}
	
	public Optional<String> getClassPath() {
		String classPath = getAnnotation("ClassPath");
		if(classPath.isEmpty()) {
			System.err.println("No ClassPath available for component");
			return Optional.empty();
		}
		return Optional.ofNullable(classPath);
	}
	
	public boolean isClassPathAvailable() {
		return getClassPath().isPresent();
	}
	
}
