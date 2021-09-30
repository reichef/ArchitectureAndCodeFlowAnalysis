package edu.kit.kastel.sdq.pcmjoanaflowanalysis.datastructure.refactored;

import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;

import org.palladiosimulator.pcm.repository.BasicComponent;

import edu.kit.kastel.sdq.pcmjoanaflowanalysis.pcmflow.IntraComponentFlow;

public class FlowBasicComponent extends Annotated {

	private final BasicComponent component;
	private Collection<IntraComponentFlow> componentInternalFlows;
	
	public FlowBasicComponent(BasicComponent component) {
		super();
		this.component = Objects.requireNonNull(component, "Component must not be null");
		this.componentInternalFlows = new HashSet<>();
	}
	
	public String getName() {
		return component.getEntityName();
	}
	
	public String getId() {
		return component.getId();
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
		if (classPath == null || classPath.isEmpty()) {
			System.err.println("No ClassPath available for component");
			return Optional.empty();
		}
		return Optional.of(classPath);
	}
	
	public boolean isClassPathAvailable() {
		return getClassPath().isPresent();
	}
	
}
