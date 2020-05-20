package edu.kit.kastel.sdq.pcmjoanaflowanalysis.Datastructure;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;

import org.palladiosimulator.pcm.core.composition.ProvidedDelegationConnector;
import org.palladiosimulator.pcm.repository.OperationProvidedRole;
import org.palladiosimulator.pcm.repository.OperationSignature;

import edu.kit.kastel.sdq.ecoreannotations.AnnotationRepository;

public abstract class AssemblyRepresentationContainer {
	protected Collection<AssemblyConnectorRepresentation> assemblyConnectorRepresentation;
	protected String id;
	protected String name;
	protected Collection<AssemblyComponent> containedRepresentations;
	protected Collection<CompositeConnectorRepresentation> compositeConnectorRepresentation;
	protected Map<String, String> annotations;
	
	protected AssemblyRepresentationContainer(String id, String name) {
		this.id = id;
		this.name = name;
		
		this.assemblyConnectorRepresentation = new HashSet<AssemblyConnectorRepresentation>();
		this.compositeConnectorRepresentation = new HashSet<CompositeConnectorRepresentation>();
		this.containedRepresentations = new HashSet<AssemblyComponent> ();
		this.annotations = new HashMap<String, String>();
	}
	
	public void addAssemblyConnector(AssemblyConnectorRepresentation connector) {
		this.assemblyConnectorRepresentation.add(connector);
	}
	
	public void addCompositeConnector(CompositeConnectorRepresentation connector) {
		this.compositeConnectorRepresentation.add(connector);
	}
	
	public void addContainedComponentRepresentation(AssemblyComponent representation) {
		this.containedRepresentations.add(representation);
	}
	
	public String getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setAnnotation(String type, String content) {
		annotations.put(type, content);
	}
	
	protected String getAnnotation(String type) {
		if(annotations.containsKey(type)) {
			return annotations.get(type);
		} 
		
		return "";
	}
	
	public boolean isComposite() {
		return containedRepresentations.size() > 0;
	}
	
	public abstract void fillWithClassPath(AnnotationRepository repository, Optional<String> latestClassPath);
	
	public Optional<String> getClassPath() {
		String classPath = getAnnotation("ClassPath");
		if(classPath.isBlank()) {
			return Optional.empty();
		}
		
		return Optional.ofNullable(classPath);
	}
	
	public boolean isClassPathAvailable() {
		return getClassPath().isPresent();
	}
	
	public void printRepresentation() {
		System.out.println(String.format("----Representation Name: %s, Id: %s ----", name, id));
		System.out.println("----Inner Representations----");
		for(AssemblyComponent representation : containedRepresentations) {
			System.out.println(String.format("InnerRepresentation Name: %s, Id: %s", representation.getName(), representation.getId()));
		}
		System.out.println("----Connector Representations----");
		for(AssemblyConnectorRepresentation assemblyConnector : assemblyConnectorRepresentation) {
			System.out.println(assemblyConnector.connectorRepresentation());
		}
		
		for(CompositeConnectorRepresentation compositeConnector : compositeConnectorRepresentation) {
			System.out.println(compositeConnector.connectorRepresentation());
		}
		
		System.out.println("---- Annotations ----");
		
		for(Entry<String, String> entry : annotations.entrySet()) {
			System.out.println(String.format("Type: %s, Content: %s", entry.getKey(), entry.getValue()));
		}
		
		if(isComposite()) {
			for(AssemblyComponent representation : containedRepresentations) {
				representation.printRepresentation();
			}
		}
		
	}

	public Optional<CompositeConnectorRepresentation> searchProvidedDelegationInOuterForSigAndProvRole(OperationProvidedRole role){
		for(CompositeConnectorRepresentation connector : compositeConnectorRepresentation) {
			if(connector.getConnector() instanceof ProvidedDelegationConnector) {
				ProvidedDelegationConnector castedConnector = (ProvidedDelegationConnector) connector.getConnector();
				if(castedConnector.getOuterProvidedRole_ProvidedDelegationConnector().getId().equals(role.getId())) {
					return Optional.ofNullable(connector);
				}
			}
		}
		
		return Optional.empty();
	}
}
