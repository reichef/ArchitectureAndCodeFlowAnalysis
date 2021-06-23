package edu.kit.kastel.sdq.pcmjoanaflowanalysis.datastructure.hierarchical;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.palladiosimulator.pcm.core.composition.ProvidedDelegationConnector;
import org.palladiosimulator.pcm.repository.OperationProvidedRole;
import org.palladiosimulator.pcm.repository.OperationRequiredRole;
import org.palladiosimulator.pcm.repository.RepositoryComponent;

import edu.kit.kastel.sdq.ecoreannotations.AnnotationRepository;
import edu.kit.kastel.sdq.pcmjoanaflowanalysis.pcmflow.SignatureIdentifyingRoleElement;

public abstract class AssemblyRepresentationContainer {
	protected String id;
	protected String name;
	protected Collection<AssemblyComponentContext> containedRepresentations;
	protected Collection<CompositeConnectorRepresentation> compositeConnectorRepresentation;
	protected Collection<AssemblyConnectorRepresentation> assemblyConnectorRepresentation;
	protected Map<String, String> annotations;
	
	protected AssemblyRepresentationContainer(String id, String name) {
		this.id = id;
		this.name = name;
		
		this.assemblyConnectorRepresentation = new HashSet<AssemblyConnectorRepresentation>();
		this.compositeConnectorRepresentation = new HashSet<CompositeConnectorRepresentation>();
		this.containedRepresentations = new HashSet<AssemblyComponentContext> ();
		this.annotations = new HashMap<String, String>();
	}
	
	
	public void addAssemblyConnector(AssemblyConnectorRepresentation connector) {
		this.assemblyConnectorRepresentation.add(connector);
	}
	
	public void addCompositeConnector(CompositeConnectorRepresentation connector) {
		this.compositeConnectorRepresentation.add(connector);
	}
	
	public void addContainedComponentRepresentation(AssemblyComponentContext representation) {
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
	

	public abstract void fillWithClassPath(AnnotationRepository repository, Optional<String> latestClassPath);
	

	public Optional<AssemblyConnectorRepresentation> getAssemblyConnectorRepresentationForSink(SignatureIdentifyingRoleElement sink) {
		for(AssemblyConnectorRepresentation connectorRepresentation : assemblyConnectorRepresentation) {
			if(sink.identyfyingEquals(connectorRepresentation.getRequiringContext().getEncapsulatedComponent__AssemblyContext(),
					connectorRepresentation.getRequiredRole())) {
				return Optional.ofNullable(connectorRepresentation);
			}
		}
		
		return Optional.empty();
	}
	
	
	public abstract void printRepresentation();

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
	
	public Collection<CompositeConnectorRepresentation> getProvidedDelegationConectors(){
		return compositeConnectorRepresentation.stream().filter(connector -> connector.getOuter().getId().equals(this.getId())).collect(Collectors.toUnmodifiableSet());
	}
	
	public Collection<CompositeConnectorRepresentation> getRequiredDelegationConnectors(){
		return compositeConnectorRepresentation.stream().filter(connector -> connector.getInner().getId().equals(this.getId())).collect(Collectors.toUnmodifiableSet());
	}
	
	public Collection<AssemblyComponentContext> getContainedRepresentations() {
		return containedRepresentations;
	}
	
	protected void collectFlowBasicComponents(Set<FlowBasicComponent> components) {
		if(this.isComposite()) {
			containedRepresentations.forEach(representation -> representation.collectFlowBasicComponents(components));
		} else {
			components.add(((AssemblyComponentContext)this).getComponent());
		}
	}
	
	public boolean isComposite() {
		return containedRepresentations.size() > 0;
	}

	
}
