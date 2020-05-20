package edu.kit.kastel.sdq.pcmjoanaflowanalysis.Datastructure;

import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;

import org.palladiosimulator.pcm.core.composition.AssemblyConnector;
import org.palladiosimulator.pcm.core.composition.AssemblyContext;
import org.palladiosimulator.pcm.repository.OperationProvidedRole;
import org.palladiosimulator.pcm.repository.OperationRequiredRole;

import edu.kit.kastel.sdq.ecoreannotations.AnnotationRepository;
import edu.kit.kastel.sdq.pcmjoanaflowanalysis.pcmflow.CallInformation;
import edu.kit.kastel.sdq.pcmjoanaflowanalysis.pcmflow.IntraComponentFlow;
import edu.kit.kastel.sdq.pcmjoanaflowanalysis.pcmflow.SignatureIdentifyingRoleElement;

public class AssemblyComponent extends AssemblyRepresentationContainer {

	private AssemblyContext context;
	private Collection<IntraComponentFlow> flows;
	
	public AssemblyComponent(AssemblyContext context) {
		super(context.getId(), context.getEncapsulatedComponent__AssemblyContext().getEntityName());
		this.context = context;
		this.flows = new HashSet<IntraComponentFlow>();
	}
	
	public boolean encapsulatesContext(AssemblyContext context) {
		return this.context.getId().equals(context.getId());
	}
	
	public AssemblyContext getContext() {
		return context;
	}
	
	public void addIntraComponentFlow(IntraComponentFlow flow) {
		flows.add(flow);
	}
	
	
	
	public Collection<AssemblyComponent> getAssemblyComponentsRecursive() {
		Collection<AssemblyComponent> components = new HashSet<AssemblyComponent>();
		components.add(this);
		if(isComposite()) {
			for(AssemblyComponent component : containedRepresentations) {
				Collection<AssemblyComponent> innerComponents = component.getAssemblyComponentsRecursive();
				innerComponents.forEach(innerComponent -> components.add(innerComponent));				
			}
		}
		return components;
	}

	@Override
	public void fillWithClassPath(AnnotationRepository repository, Optional<String> latestClassPath) {
		
		boolean isClassPathAvailable = isClassPathAvailable();
		
		if(!isClassPathAvailable) {
			if(latestClassPath.isPresent()) {
				setAnnotation("ClassPath", latestClassPath.get());
				isClassPathAvailable = true;
			} else {
				System.out.println(String.format("Component %s got no ClassPath", getName()));
			}
		}
		
		if(isComposite()) {
			for(AssemblyComponent representation : containedRepresentations) {
				if(isClassPathAvailable) {
					representation.fillWithClassPath(repository, getClassPath());
				} else {
					representation.fillWithClassPath(repository, latestClassPath);
				}
			}
		}
	}

	public Collection<AssemblyConnectorRepresentation> getAssemblyConnectorRepresentationsForSourceFromFlows(
			SignatureIdentifyingRoleElement<OperationProvidedRole> source) {
		Collection<AssemblyConnectorRepresentation> assemblyConnectors = new HashSet<AssemblyConnectorRepresentation>();
		
		for(IntraComponentFlow flow : flows) {
			if(flow.getSource().identyfyingEquals(source.getComponent(), source.getRole(), source.getSignature())) {
				for(SignatureIdentifyingRoleElement<OperationRequiredRole> sink : flow.getSinks()) {
					Optional<AssemblyConnectorRepresentation> sinkRepresentation = findAssemblyConnectorRepresentationForSink(sink);
					if(sinkRepresentation.isPresent()) {
						assemblyConnectors.add(sinkRepresentation.get());
					}
				}
			}
		}
		
		return assemblyConnectors;
	}
	
	private Optional<AssemblyConnectorRepresentation> findAssemblyConnectorRepresentationForSink(SignatureIdentifyingRoleElement<OperationRequiredRole> sink) {
		for(AssemblyConnectorRepresentation connectorRepresentation : assemblyConnectorRepresentation) {
			if(sink.identyfyingEquals(connectorRepresentation.getRequiringContext().getEncapsulatedComponent__AssemblyContext(),
					connectorRepresentation.getRequiredRole())) {
				return Optional.ofNullable(connectorRepresentation);
			}
		}
		
		return Optional.empty();
	}
	
	public boolean encapsulatedContextProvidesRole(OperationProvidedRole searchedRole) {
		return context.getEncapsulatedComponent__AssemblyContext().getProvidedRoles_InterfaceProvidingEntity().stream().anyMatch(role -> role.getId().equals(searchedRole.getId()));
	}
}