package edu.kit.kastel.sdq.pcmjoanaflowanalysis.analysiscoupling

import JOANA.JOANARoot
import JOANA.JOANAFactory

import edu.kit.kastel.sdq.pcmjoanaflowanalysis.correspondences.PCM2SourceCodeCorrespondenceResolver
import org.palladiosimulator.pcm.repository.OperationRequiredRole
import JOANA.FlowSpecification
import org.palladiosimulator.pcm.repository.OperationProvidedRole
import org.eclipse.emf.ecore.util.EcoreUtil
import org.palladiosimulator.pcm.repository.OperationSignature
import org.eclipse.xtend.lib.annotations.Accessors
import org.palladiosimulator.pcm.repository.RepositoryComponent
import edu.kit.joana.ui.ifc.wala.console.console.component_based.Association
import edu.kit.joana.ui.ifc.wala.console.console.component_based.Method
import java.util.Collection
import java.util.HashSet

class PCM2JOANAModelTransformator {
	
	@Accessors(PUBLIC_GETTER)
	JOANARoot jRoot;
	PCM2SourceCodeCorrespondenceResolver resolver;
	
	new(PCM2SourceCodeCorrespondenceResolver resolver){
		jRoot = JOANAFactory.eINSTANCE.createJOANARoot;
	}
	
	def Pair<FlowSpecification, Association> generateFlowForProvidedOperationAndComponent(OperationProvidedRole providedRole, OperationSignature signature, RepositoryComponent component){

		val association = new Association();
		val flowSpec = JOANAFactory.eINSTANCE.createFlowSpecification;
		flowSpec.id = EcoreUtil.generateUUID;
		
		val source = JOANAFactory.eINSTANCE.createSource;
		source.id = EcoreUtil.generateUUID;
		source.class = resolver.getClass(component);
		source.interface = resolver.getInterface(providedRole.providedInterface__OperationProvidedRole);
		source.method = resolver.getMethod(signature);
		
		var joanaCLISource = new Method(source.class.name, source.method.name);
		association.associate(source.method.id, joanaCLISource);
		
		flowSpec.source.add(source);
		
		var sinks = transformBasicComponentRequiredToLocalSink(flowSpec, component);
				
		jRoot.flowspecification.add(flowSpec);
		
		sinks.forEach[sink | association.associate(sink.first, sink.second)]
		
		return new Pair(flowSpec, association);
	}
	
	def private Collection<Pair<String, Method>> transformBasicComponentRequiredToLocalSink(FlowSpecification flow, RepositoryComponent component){
		
		var joanaCLISinks = new HashSet<Pair<String,Method>>();
		
		val scClass = resolver.getClass(component);
		for(requiredRole : component.requiredRoles_InterfaceRequiringEntity){
			if(requiredRole instanceof OperationRequiredRole){	
				for(signature : requiredRole.requiredInterface__OperationRequiredRole.signatures__OperationInterface){
					var sink = JOANAFactory.eINSTANCE.createParameterSink();
					var scInterface = resolver.getInterface(requiredRole.requiredInterface__OperationRequiredRole);
					var scMethod = resolver.getMethod(signature);
	
					sink.class = scClass;
					sink.interface = scInterface;
					sink.method = scMethod;
					
					flow.sink.add(sink);
					
					var joanaCLISink = new Method(scInterface.name, scMethod.name);
					joanaCLISinks.add(new Pair(sink.method.id,joanaCLISink));
				}
			}
		}
		
		return joanaCLISinks;
	}
	
	def FlowSpecification getFlowSpecificationById(String id){
		return jRoot.flowspecification.findFirst[flowSpec | flowSpec.id.equals(id)];
	} 
}
