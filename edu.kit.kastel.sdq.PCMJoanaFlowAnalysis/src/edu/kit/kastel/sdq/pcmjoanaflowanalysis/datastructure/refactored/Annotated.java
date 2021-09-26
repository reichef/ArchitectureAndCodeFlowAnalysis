package edu.kit.kastel.sdq.pcmjoanaflowanalysis.datastructure.refactored;

import java.util.HashMap;
import java.util.Map;

public abstract class Annotated {

	protected Map<String, String> annotations;
	
	protected Annotated() {
		this.annotations = new HashMap<>();
	}
	
	public Map<String, String> getAnnotations() {
		return annotations;
	}
	
	public String getAnnotation(String type) {
		return annotations.get(type);
	}
	
	public void setAnnotation(String type, String content) {
		annotations.put(type, content);
	}
	
	public boolean containsAnnotation(String type) {
		return annotations.containsKey(type);
	}
	
}
