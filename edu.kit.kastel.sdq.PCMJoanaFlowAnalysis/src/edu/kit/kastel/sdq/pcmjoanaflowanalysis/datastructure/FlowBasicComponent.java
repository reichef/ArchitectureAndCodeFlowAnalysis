package edu.kit.kastel.sdq.pcmjoanaflowanalysis.datastructure;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;

import org.palladiosimulator.pcm.repository.BasicComponent;
import org.palladiosimulator.pcm.repository.OperationProvidedRole;
import org.palladiosimulator.pcm.repository.OperationRequiredRole;

import edu.kit.kastel.sdq.ecoreannotations.AnnotationRepository;
import edu.kit.kastel.sdq.pcmjoanaflowanalysis.pcmflow.IntraComponentFlow;
import edu.kit.kastel.sdq.pcmjoanaflowanalysis.pcmutil.ModelsSubtypeResolver;

public class FlowBasicComponent {

	private final String name;
	private final String id;
	private Collection<IntraComponentFlow> componentInternalFlows;
	private Map<String, String> annotations;
	private final BasicComponent component;

	private FlowBasicComponent(String name, String id, BasicComponent component) {
		this.name = name;
		this.id = id;
		this.component = component;
		this.componentInternalFlows = new HashSet<IntraComponentFlow>();
		this.annotations = new HashMap<>();
	}

	public FlowBasicComponent(BasicComponent component) {
		this(component.getEntityName(), component.getId(), component);
	}


	public void fillWithClassPath(String classPath) {

		boolean isClassPathAvailable = isClassPathAvailable();

		if (!isClassPathAvailable) {
			setAnnotation("ClassPath", classPath);
		}
	}

	public Optional<String> getClassPath() {
		String classPath = getAnnotation("ClassPath");
		if (classPath.isEmpty()) {
			System.err.println("No ClassPath Available for Component");
			return Optional.empty();
		}

		return Optional.ofNullable(classPath);
	}

	public boolean isClassPathAvailable() {
		return getClassPath().isPresent();
	}

	public void setAnnotation(String type, String content) {
		annotations.put(type, content);
	}

	protected String getAnnotation(String type) {
		if (annotations.containsKey(type)) {
			return annotations.get(type);
		}

		return "";
	}

	public String getName() {
		return name;
	}

	public String getId() {
		return id;
	}

	public void addIntraComponentFlow(IntraComponentFlow intraComponentFlow) {
		this.componentInternalFlows.add(intraComponentFlow);
	}

	public BasicComponent getComponent() {
		return component;
	}

	public Collection<OperationProvidedRole> getOperationProvidedRoles() {
		return ModelsSubtypeResolver.filterOperationProvidedRoles(component.getProvidedRoles_InterfaceProvidingEntity());
	}

	public Collection<OperationRequiredRole> getOperationRequiredRoles() {
		return ModelsSubtypeResolver.filterOperationRequiredRoles(component.getRequiredRoles_InterfaceRequiringEntity());
	}
}
