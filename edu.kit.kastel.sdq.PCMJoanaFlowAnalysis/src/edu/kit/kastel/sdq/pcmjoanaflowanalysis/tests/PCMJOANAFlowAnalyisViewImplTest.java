package edu.kit.kastel.sdq.pcmjoanaflowanalysis.tests;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.palladiosimulator.pcm.repository.RepositoryFactory;
import org.palladiosimulator.pcm.system.System;

import edu.kit.kastel.sdq.PCMJoanaFlowAnalysisDiagramModel.*;
import edu.kit.kastel.sdq.PCMJoanaFlowAnalysisDiagramModel.Class;
import edu.kit.kastel.sdq.ecoreannotations.AnnotationRepository;
import edu.kit.kastel.sdq.pcmjoanaflowanalysis.PCMJOANAFlowAnalysisViewImpl;
import edu.kit.kastel.sdq.pcmjoanaflowanalysis.datastructure.hierarchical.AssemblyComponentContext;
import edu.kit.kastel.sdq.pcmjoanaflowanalysis.datastructure.hierarchical.DataStructureBuilder;
import edu.kit.kastel.sdq.pcmjoanaflowanalysis.datastructure.hierarchical.SystemRepresentation;
import edu.kit.kastel.sdq.pcmjoanaflowanalysis.pcmflow.fixpoint.SystemOperationIdentifying;

class PCMJOANAFlowAnalyisViewImplTest {

	static SystemRepresentation SYSTEM_REPRESENTATION;
	
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		/**
        Load the minimum instance of a system representation
        The palladio assembly representation is necessary to run this test 
		 (clientServer.system and .ecoreannotations files)
		The .ecoreannotations file must be updated with the absolute path
		of the business case (i.e. the references to the .jar files of the palladio
		model). The first test was done with the model Client-Server-Database business 
		case; more information in the project documentation
		 * 
		 */
        String name = "edu.kit.kastel.dsis.msflow.casestudy.simple.ClientServerTest";
        IProjectDescription description = ResourcesPlugin.getWorkspace().newProjectDescription(name);
        IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject(name);
        project.create(description, new NullProgressMonitor());
        project.open(new NullProgressMonitor());

        ClassLoader loader = PCMJOANAFlowAnalyisViewImplTest.class.getClassLoader();
        var pack = PCMJOANAFlowAnalyisViewImplTest.class.getPackageName().replace(".","/");
        
		URI systemUri = URI.createURI(loader.getResource(pack + "/clientServer.system").toURI().toString());
		ResourceSetImpl resSet = new ResourceSetImpl();
		Resource resourceSystem = resSet.getResource(systemUri, true);
		EcoreUtil.resolveAll(resourceSystem);
		resourceSystem.load(null);
		System system = (System) resourceSystem.getContents().get(0);
		
		URI annotationsUri = URI.createURI(loader.getResource(pack + "/clientServer.ecoreannotations").toURI().toString());
		Resource resourceAnnotations = resSet.getResource(annotationsUri, true);
		EcoreUtil.resolveAll(resourceAnnotations);
		resourceAnnotations.load(null);
		AnnotationRepository annotationRepository = (AnnotationRepository) resourceAnnotations.getContents().get(0);
		
		DataStructureBuilder dataStructureBuilder = new DataStructureBuilder();
		SYSTEM_REPRESENTATION = dataStructureBuilder.buildRepresentationsForSystem(system,
				annotationRepository);
		
		Collection<AssemblyComponentContext> diagram_assemblies = SYSTEM_REPRESENTATION.getContainedRepresentations();
		
		//Fill out systemInducedConnection (For intra-component flows)
		for (var diagram_assembly : diagram_assemblies) {
			Map<SystemOperationIdentifying, Collection<SystemOperationIdentifying>> systemInducedConnection = 
					diagram_assembly.getSystemInducedConnection();
			var componentContext1 = new AssemblyComponentContext(diagram_assembly.getContext());
			var operationSignature1 = RepositoryFactory.eINSTANCE.createOperationSignature();
			operationSignature1.setEntityName(diagram_assembly.getName() + "method1");
			var systemOperationID1 = new SystemOperationIdentifying(componentContext1, null, operationSignature1);
			
			HashSet<SystemOperationIdentifying> value = new HashSet<SystemOperationIdentifying>();
			var componentContext2 = new AssemblyComponentContext(diagram_assembly.getContext());
			var operationSignature2 = RepositoryFactory.eINSTANCE.createOperationSignature();
			operationSignature2.setEntityName(diagram_assembly.getName() + "method2");
			var systemOperationID2 = new SystemOperationIdentifying(componentContext2, null, operationSignature2);
			value.add(systemOperationID2);
			systemInducedConnection.put(systemOperationID1, value);
			
			
		}
		
		//Fill method_history (For inter-component flows)
		for (var diagram_assembly : diagram_assemblies) {
			for (var assemblyConnectorRepr : diagram_assembly.getAssemblyConnectorRepresentation()) {
				var method_history = new ArrayList<String>();
				for (var diagram_assembly2 : diagram_assemblies) {
					if (diagram_assembly.getContext().getEntityName() != 
							diagram_assembly2.getContext().getEntityName()) {
						method_history.add(diagram_assembly.getContext().getEntityName());
						method_history.add(diagram_assembly.getName());
						method_history.add(diagram_assembly.getName() + "method1");
						method_history.add(diagram_assembly2.getContext().getEntityName());
						method_history.add(diagram_assembly2.getName());
						method_history.add(diagram_assembly2.getName() + "method2");
					}
					break;
				}
				assemblyConnectorRepr.setMethodHistory(method_history);
			}
		}
	}
	
	@BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {
	}
	

	
	@Test
	final void testGenerateClassObject() {
		var assembly_var = PCMJoanaFlowAnalysisDiagramModelFactory.eINSTANCE.createAssembly();
		Class class_Test = (new PCMJOANAFlowAnalysisViewImpl("")).generateClassObject(assembly_var.getClass_(),"Class1");
		assert(class_Test != null);
		assert(assembly_var.getClass_().contains(class_Test));
	}

	@Test
	final void testGenerateMethodObject() {
		var class_var = PCMJoanaFlowAnalysisDiagramModelFactory.eINSTANCE.createClass();
		Method method_Test = (new PCMJOANAFlowAnalysisViewImpl("")).generateMethodObject(class_var.getMethod(),"Method1");
		assert(method_Test != null);
		assert(class_var.getMethod().contains(method_Test));
	}

	@Test
	final void testCreateFlows() {
		var method_var = PCMJoanaFlowAnalysisDiagramModelFactory.eINSTANCE.createMethod();
		method_var.setName("method1");
		var method_var2 = PCMJoanaFlowAnalysisDiagramModelFactory.eINSTANCE.createMethod();
		method_var2.setName("method2");
		int flow_id = -1;
		Flow flow_var = (new PCMJOANAFlowAnalysisViewImpl("")).createFlows(method_var,method_var2,flow_id);
		assert(flow_var != null);
		assert(Integer.parseInt(flow_var.getId()) == flow_id);
		assert(method_var.getFlow().contains(flow_var));
		
		flow_var = (new PCMJOANAFlowAnalysisViewImpl("")).createFlows(method_var2,method_var,flow_id);
		assert(flow_var != null);
		assert(Integer.parseInt(flow_var.getId()) == flow_id);
		assert(method_var2.getFlow().contains(flow_var));
		
		flow_var = (new PCMJOANAFlowAnalysisViewImpl("")).createFlows(method_var,method_var,flow_id);
		assert(flow_var != null);
		assert(Integer.parseInt(flow_var.getId()) == flow_id);
		assert(method_var.getFlow().contains(flow_var));
	}
	
	@Test
	final void testCreateFlows_negative() {
		//Method with empty name
		var method_var = PCMJoanaFlowAnalysisDiagramModelFactory.eINSTANCE.createMethod();
		method_var.setName("method1");
		var method_var2 = PCMJoanaFlowAnalysisDiagramModelFactory.eINSTANCE.createMethod();
		int flow_id = 0;
		Flow flow_var = (new PCMJOANAFlowAnalysisViewImpl("")).createFlows(method_var,method_var2,flow_id);
		assert(flow_var == null);
		
		//Method with empty name
		method_var = PCMJoanaFlowAnalysisDiagramModelFactory.eINSTANCE.createMethod();
		method_var2 = PCMJoanaFlowAnalysisDiagramModelFactory.eINSTANCE.createMethod();
		method_var2.setName("method2");
		flow_id = 0;
		flow_var = (new PCMJOANAFlowAnalysisViewImpl("")).createFlows(method_var,method_var2,flow_id);
		assert(flow_var == null);
	}

	@Test
	final void testCreateIntraComponentFlows() {
		JOANAFlowAnalysisDiagram root = PCMJoanaFlowAnalysisDiagramModelFactory.eINSTANCE
				.createJOANAFlowAnalysisDiagram();
		int flow_id = 0;
		flow_id = (new PCMJOANAFlowAnalysisViewImpl("")).createIntraComponentFlows(SYSTEM_REPRESENTATION.getContainedRepresentations(),root.getAssembly(),flow_id);
		assert(flow_id != 0);

	}
	
	@Test
	final void testCreateInterComponentFlows() {
		JOANAFlowAnalysisDiagram root = PCMJoanaFlowAnalysisDiagramModelFactory.eINSTANCE
				.createJOANAFlowAnalysisDiagram();
		int flow_id = 0;
		//Test with intra_componend_end_point = false)
		flow_id = (new PCMJOANAFlowAnalysisViewImpl("")).createInterComponentFlows(SYSTEM_REPRESENTATION.getContainedRepresentations(),root.getAssembly(),flow_id,false);
		assert(flow_id != 0);
		
		flow_id = 0;
		//Test with intra_componend_end_point = true)
		flow_id = (new PCMJOANAFlowAnalysisViewImpl("")).createInterComponentFlows(SYSTEM_REPRESENTATION.getContainedRepresentations(),root.getAssembly(),flow_id,true);
		assert(flow_id != 0);
	}


	@Test
	final void testDrawDiagramInstance() throws CoreException, IOException  {

		
		//Test with intra_componend_end_point = true)
		JOANAFlowAnalysisDiagram case1 = (new PCMJOANAFlowAnalysisViewImpl("/edu.kit.kastel.dsis.msflow.casestudy.simple.ClientServerTest")).drawDiagramInstance(SYSTEM_REPRESENTATION.getContainedRepresentations(),true);
		assert(case1 != null);
		assert(!case1.getAssembly().isEmpty());
		java.lang.System.out.println(case1.toString());
		
		//Test with intra_componend_end_point = false)
		JOANAFlowAnalysisDiagram case2 = (new PCMJOANAFlowAnalysisViewImpl("/edu.kit.kastel.dsis.msflow.casestudy.simple.ClientServerTest")).drawDiagramInstance(SYSTEM_REPRESENTATION.getContainedRepresentations(),false);
		assert(case2 != null);
		assert(!case2.getAssembly().isEmpty());
		java.lang.System.out.println(case2.toString());
	}

}
