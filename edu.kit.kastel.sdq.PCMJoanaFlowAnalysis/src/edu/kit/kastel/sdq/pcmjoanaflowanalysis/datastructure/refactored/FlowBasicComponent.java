package edu.kit.kastel.sdq.pcmjoanaflowanalysis.datastructure.refactored;

import java.util.HashMap;
import java.util.Optional;

import org.palladiosimulator.pcm.repository.BasicComponent;

public class FlowBasicComponent extends Annotated {

	private final String name;
	private final String id;
	private final BasicComponent component;

	
	private FlowBasicComponent(String name, String id, BasicComponent component) {
		this.name = name;
		this.id = id;
		this.component = component;
		this.annotations = new HashMap<String, String>();
	}
	
	public FlowBasicComponent(BasicComponent component) {
		this(component.getEntityName(), component.getId(), component);
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
