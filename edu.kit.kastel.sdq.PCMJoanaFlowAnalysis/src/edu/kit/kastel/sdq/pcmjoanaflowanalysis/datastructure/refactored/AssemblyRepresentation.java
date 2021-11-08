package edu.kit.kastel.sdq.pcmjoanaflowanalysis.datastructure.refactored;

import java.util.Objects;

public abstract class AssemblyRepresentation extends Annotated {

	protected final String id;
	protected final String name;
	
	protected AssemblyRepresentation(String id, String name) {
		super();
		this.id = Objects.requireNonNull(id, "Id must not be null");
		this.name = Objects.requireNonNull(name, "Name must not be null");
	}
	
	public String getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}
	
}
