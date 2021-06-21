package edu.kit.kastel.sdq.pcmjoanaflowanalysis.datastructure.hierarchical;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map.Entry;
import java.util.Optional;

import org.palladiosimulator.pcm.core.composition.AssemblyContext;
import org.palladiosimulator.pcm.core.composition.RequiredDelegationConnector;
import org.palladiosimulator.pcm.repository.BasicComponent;
import org.palladiosimulator.pcm.repository.OperationProvidedRole;
import org.palladiosimulator.pcm.repository.OperationRequiredRole;

import edu.kit.kastel.sdq.ecoreannotations.AnnotationRepository;
import edu.kit.kastel.sdq.pcmjoanaflowanalysis.pcmflow.IntraComponentFlow;
import edu.kit.kastel.sdq.pcmjoanaflowanalysis.pcmflow.SignatureIdentifyingRoleElement;

public class AssemblyComponentContext extends AssemblyRepresentationContainer {

	private AssemblyContext context;
	private Collection<IntraComponentFlow> systemInducedFlows;
	private FlowBasicComponent component;
	
	public void setComponent(FlowBasicComponent component) {
		this.component = component;
	}

	public AssemblyComponentContext(AssemblyContext context) {
		super(context.getId(), context.getEncapsulatedComponent__AssemblyContext().getEntityName());
		this.context = context;
		this.systemInducedFlows = new HashSet<IntraComponentFlow>();
	}
	
	public AssemblyComponentContext(AssemblyContext context, FlowBasicComponent component) {
		this(context);
		this.component = component;
	}
	
	public boolean encapsulatesContext(AssemblyContext context) {
		return this.context.getId().equals(context.getId());
	}
	
	public AssemblyContext getContext() {
		return context;
	}
	
	public void addIntraComponentFlow(IntraComponentFlow flow) {
		systemInducedFlows.add(flow);
	}
	
	
	
	public Collection<AssemblyComponentContext> getAssemblyComponentsRecursive() {
		Collection<AssemblyComponentContext> components = new HashSet<AssemblyComponentContext>();
		components.add(this);
		if(isComposite()) {
			for(AssemblyComponentContext component : containedRepresentations) {
				Collection<AssemblyComponentContext> innerComponents = component.getAssemblyComponentsRecursive();
				innerComponents.forEach(innerComponent -> components.add(innerComponent));				
			}
		}
		return components;
	}
	
	public Optional<String> getClassPath() {
		return component.getClassPath();
	}
	
	public boolean isClassPathAvailable() {
		return component.isClassPathAvailable();
	}
	

	@Override
	public void fillWithClassPath(AnnotationRepository repository, Optional<String> latestClassPath) {
		
		boolean isClassPathAvailable = isClassPathAvailable();
		
		if(!isClassPathAvailable) {
			component.fillWithClassPath(repository, latestClassPath);
		}
		
		if(isComposite()) {
			for(AssemblyComponentContext representation : containedRepresentations) {
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
		
		for(IntraComponentFlow flow : systemInducedFlows) {
			if(flow.getSource().identyfyingEquals(source)) {
				for(SignatureIdentifyingRoleElement<OperationRequiredRole> sink : flow.getSinks()) {
					Optional<AssemblyConnectorRepresentation> sinkRepresentation = getAssemblyConnectorRepresentationForSink(sink);
					if(sinkRepresentation.isPresent()) {
						assemblyConnectors.add(sinkRepresentation.get());
					}
				}
			}
		}
		
		return assemblyConnectors;
	}
	
	
	public Optional<CompositeConnectorRepresentation> getDelegationConnectorRepresentationForRequiredRoleIdentifyingFromInnerRole(SignatureIdentifyingRoleElement<OperationRequiredRole> requiredIdentifying){
		for(CompositeConnectorRepresentation connectorRepresentation : compositeConnectorRepresentation) {
			if(requiredIdentifying.identyfyingEquals(connectorRepresentation.getInner().getContext().getEncapsulatedComponent__AssemblyContext(), ((RequiredDelegationConnector)connectorRepresentation.getConnector()).getInnerRequiredRole_RequiredDelegationConnector())) {
				return Optional.ofNullable(connectorRepresentation);
			}
		}
		
		return Optional.empty();
	}

	public boolean encapsulatedContextProvidesRole(OperationProvidedRole searchedRole) {
		return context.getEncapsulatedComponent__AssemblyContext().getProvidedRoles_InterfaceProvidingEntity().stream().anyMatch(role -> role.getId().equals(searchedRole.getId()));
	}

	public FlowBasicComponent getComponent() {
		return component;
	}

	@Override
	public void printRepresentation() {
		System.out.println(String.format("----Representation Name: %s, Id: %s ----", name, id));
		System.out.println("----Inner Representations----");
		for(AssemblyComponentContext representation : containedRepresentations) {
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
			for(AssemblyComponentContext representation : containedRepresentations) {
				representation.printRepresentation();
			}
		}
	}
}