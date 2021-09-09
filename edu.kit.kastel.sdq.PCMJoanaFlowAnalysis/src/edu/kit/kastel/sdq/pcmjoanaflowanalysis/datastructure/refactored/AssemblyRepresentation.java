package edu.kit.kastel.sdq.pcmjoanaflowanalysis.datastructure.refactored;

public abstract class AssemblyRepresentation extends Annotated {

	protected final String id;
	protected final String name;
	
	protected AssemblyRepresentation(String id, String name) {
		super();
		this.id = id;
		this.name = name;
	}
	
	public String getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}
	
}
