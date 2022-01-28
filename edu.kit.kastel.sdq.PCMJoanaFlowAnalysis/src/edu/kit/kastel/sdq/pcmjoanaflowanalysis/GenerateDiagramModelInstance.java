
//Created by Isai Roman
//as a reference from the examples provided
//in KIT SDQ-Wiki (https://sdqweb.ipd.kit.edu/wiki/Creating_EMF_Model_instances_programmatically)

package edu.kit.kastel.sdq.pcmjoanaflowanalysis;



import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EStructuralFeature.Setting;
import org.eclipse.emf.ecore.impl.EStructuralFeatureImpl;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceImpl;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.XMLResourceFactoryImpl;
import org.eclipse.emf.ecore.xmi.impl.XMLResourceImpl;


import edu.kit.kastel.sdq.PCMJoanaFlowAnalysisDiagramModel.JOANAFlowAnalysisDiagram;
import edu.kit.kastel.sdq.PCMJoanaFlowAnalysisDiagramModel.Method;
import edu.kit.kastel.sdq.PCMJoanaFlowAnalysisDiagramModel.PCMJoanaFlowAnalysisDiagramModelFactory;
import edu.kit.kastel.sdq.pcmjoanaflowanalysis.datastructure.hierarchical.AssemblyComponentContext;
import edu.kit.kastel.sdq.pcmjoanaflowanalysis.datastructure.hierarchical.SystemRepresentation;
import edu.kit.kastel.sdq.pcmjoanaflowanalysis.pcmflow.fixpoint.SystemOperationIdentifying;

public class GenerateDiagramModelInstance {

	public static Resource createAndAddResource(String outputFile, String[] fileextensions, ResourceSet rs) {
		for (String fileext : fileextensions) {
			rs.getResourceFactoryRegistry().getExtensionToFactoryMap().put(fileext, new XMLResourceFactoryImpl());
		}		
		URI uri = URI.createFileURI(outputFile);
		Resource resource = rs.createResource(uri);
		((ResourceImpl)resource).setIntrinsicIDToEObjectMap(new HashMap<>());
		return resource;
	}

	public static void saveResource(Resource resource) {
		var saveOptions = ((XMLResourceImpl)resource).getDefaultSaveOptions();
		saveOptions.put(XMLResourceImpl.OPTION_CONFIGURATION_CACHE, Boolean.TRUE);
		saveOptions.put(XMLResourceImpl.OPTION_USE_CACHED_LOOKUP_TABLE, new ArrayList<>());
		try {
			resource.save(saveOptions);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public void setupAndSaveEMFSampleInstanceResource(SystemRepresentation systemrepresentation) {
		ResourceSet rs = new ResourceSetImpl();
		// Here the resource is created, with fileextensions "gast" and "xml" (adapt this to use your own file extension).
		Resource gastResource = createAndAddResource("/Users/isairoman/eclipse-workspace_122020/ArchitectureAndCodeFlowAnalysis/CaseStudies/MinimalClientServerExample/PCMModels/ClientServerTest/file.pcmjoanaflowanalysisdiagrammodel", new String[] {"pcmjoanaflowanalysisdiagrammodel", "xml"}, rs);
		// The root object is created by using (adapt this to create your own root object)
		
		// Create model
		JOANAFlowAnalysisDiagram root = PCMJoanaFlowAnalysisDiagramModelFactory.eINSTANCE.createJOANAFlowAnalysisDiagram();
		root.setName("Analysis Test");
		//Get the assembly_list
		var assembly_list = root.getAssembly();
		
		//Create assembly
		Collection<AssemblyComponentContext> diagram_assemblies = systemrepresentation.getContainedRepresentations();
		
		for (var assembly: diagram_assemblies) {
			//Create the assembly
			var assembly_var = PCMJoanaFlowAnalysisDiagramModelFactory.eINSTANCE.createAssembly();
			assembly_var.setName(assembly.getContext().getEntityName());
			//Add the assembly in the model assemblies' collection
			assembly_list.add(assembly_var);
			//Iterate systemInducedConnection to get the flows
			Map<SystemOperationIdentifying, Collection<SystemOperationIdentifying>> flows_collection = assembly.getSystemInducedConnection();
			
			int flow_id = 0;
			for (var flow : flows_collection.entrySet()) {
				//Get starting method
				var start_method = flow.getKey();
				//Create Method1
				var method_var = PCMJoanaFlowAnalysisDiagramModelFactory.eINSTANCE.createMethod();
				method_var.setName(start_method.getSignature().getEntityName());
				
				//Add the class in the assemblies' collection
				var class_list = assembly_var.getClass_();
				//Generate the class object
				edu.kit.kastel.sdq.PCMJoanaFlowAnalysisDiagramModel.Class class_var, class_contained = null;
				EList<edu.kit.kastel.sdq.PCMJoanaFlowAnalysisDiagramModel.Method> method_list = null ;
				if (class_list.isEmpty()) {
					//Create class of the method
					class_var = PCMJoanaFlowAnalysisDiagramModelFactory.eINSTANCE.createClass();
					class_var.setName(start_method.getContext().getName());
					class_list.add(class_var);
					//Get the methods list in the classes' collection
					method_list = class_var.getMethod();
				}
				else {
					//Check if the class is already contained
					for (var class_element:class_list) {
						if (class_element.getName().equals(start_method.getContext().getName())) {
							//Keep the class object
							class_contained = class_element;
						}
					}
					if (class_contained != null) {
						//take the class instance of the method
						class_var = class_contained;
						//Get the methods list in the classes' collection
						method_list = class_var.getMethod();
					} 
					else {
						//Create class of the method
						class_var = PCMJoanaFlowAnalysisDiagramModelFactory.eINSTANCE.createClass();
						class_var.setName(start_method.getContext().getName());
						class_list.add(class_var);
						//Get the methods list in the classes' collection
						method_list = class_var.getMethod();
					}
				}
				

				if (method_list != null ) {
					//Check if the method is already contained
					edu.kit.kastel.sdq.PCMJoanaFlowAnalysisDiagramModel.Method contained_method = null;
					if (method_list.isEmpty()) {
						//Add the method to the class list
						method_list.add(method_var);
					}
					else {

						for (var method : method_list )  {
							if (method.getName() == method_var.getName()) {
								//Get the method from the class list
								contained_method = method;
							}
						}
						if (contained_method != null)
							method_var = contained_method;
						else
							method_list.add(method_var);
					}

				}
				
				//Get end method
				var end_methods = flow.getValue();

				
				for (var end_method: end_methods) {
					
					//Create Method2
					var method_var2 = PCMJoanaFlowAnalysisDiagramModelFactory.eINSTANCE.createMethod();
					
					method_var2.setName(end_method.getSignature().getEntityName());
					
					//Generate the class object
					edu.kit.kastel.sdq.PCMJoanaFlowAnalysisDiagramModel.Class class_var2, class_contained2 = null;
					EList<edu.kit.kastel.sdq.PCMJoanaFlowAnalysisDiagramModel.Method> method_list2 = null;
					if (class_list.isEmpty()) {
						//Create class of the method
						class_var2 = PCMJoanaFlowAnalysisDiagramModelFactory.eINSTANCE.createClass();
						class_var2.setName(start_method.getContext().getName());
						class_list.add(class_var2);
						//Get the methods list in the classes' collection
						method_list2 = class_var2.getMethod();
					}
					else {
						//Check if the class is already contained
						for (var class_element:class_list) {
							if (class_element.getName().equals(start_method.getContext().getName())) {
								//Get the class found
								class_contained2 = class_element;
							}
						}
						if (class_contained2 != null ) {
							//Create class of the method
							class_var2 = class_contained2;
							//Get the methods list in the classes' collection
							method_list2 = class_var2.getMethod();
						}
						else {
							//Create class of the method
							class_var2 = PCMJoanaFlowAnalysisDiagramModelFactory.eINSTANCE.createClass();
							class_var2.setName(start_method.getContext().getName());
							class_list.add(class_var2);
							//Get the methods list in the classes' collection
							method_list2 = class_var2.getMethod();
						}
					}
					
					if (method_list2 != null ) {
						//Check if the method is already contained
						edu.kit.kastel.sdq.PCMJoanaFlowAnalysisDiagramModel.Method contained_method2 = null;
						if (method_list2.isEmpty()) {
							//Add the method to the class list
							method_list2.add(method_var2);
						}
						else {
							for (var method : method_list2 )  {
								if (method.getName() == method_var2.getName()) {
									//Get the method from the class list
									contained_method2 = method;
								}
							}
							if (contained_method2 != null)
								method_var2 = contained_method2;
							else
								method_list2.add(method_var2);
						}

					}
					
					if (method_var2.getName() != null && method_var.getName() != null) {
						//Create Flow
						var flow_var = PCMJoanaFlowAnalysisDiagramModelFactory.eINSTANCE.createFlow();
						flow_var.setId(String.valueOf(flow_id));
						flow_var.setMethod(method_var2);
						//Add each of the objects in the respective collections		
						//Add the flow in the method1 flows' collection
						var flow_list = method_var.getFlow();
						flow_list.add(flow_var);
						//Increment the flow id
						flow_id++;
					}

				}
				
			}
			
		}
		
		
		gastResource.getContents().add(root);
		saveResource(gastResource);
	}
	
//	public void setupAndSaveEMFInstanceResource() {
//		ResourceSet rs = new ResourceSetImpl();
//		// Here the resource is created, with fileextensions "gast" and "xml" (adapt this to use your own file extension).
//		Resource gastResource = createAndAddResource("/Users/isairoman/eclipse-workspace_122020/ArchitectureAndCodeFlowAnalysis/CaseStudies/MinimalClientServerExample/PCMModels/ClientServerTest/file.pcmjoanaflowanalysisdiagrammodel", new String[] {"pcmjoanaflowanalysisdiagrammodel", "xml"}, rs);
//		// The root object is created by using (adapt this to create your own root object)
//		
//		// Create model
//		JOANAFlowAnalysisDiagram root = PCMJoanaFlowAnalysisDiagramModelFactory.eINSTANCE.createJOANAFlowAnalysisDiagram();
//		root.setName("Analysis Test");
//		
//		//Create assembly
//		var assembly_var = PCMJoanaFlowAnalysisDiagramModelFactory.eINSTANCE.createAssembly();
//		assembly_var.setName("Server");
//		//Create class
//		var class_var = PCMJoanaFlowAnalysisDiagramModelFactory.eINSTANCE.createClass();
//		class_var.setName("Class1");
//		//Create Method1
//		var method_var = PCMJoanaFlowAnalysisDiagramModelFactory.eINSTANCE.createMethod();
//		method_var.setName("method1");
//		//Create Method2
//		var method_var2 = PCMJoanaFlowAnalysisDiagramModelFactory.eINSTANCE.createMethod();
//		method_var2.setName("method2");
//		//Create Flow
//		var flow_var = PCMJoanaFlowAnalysisDiagramModelFactory.eINSTANCE.createFlow();
//		flow_var.setId("01");
//		flow_var.setMethod(method_var2);
//		
//		//Add each of the objects in the respective collections		
//		//Add the flow in the method1 flows' collection
//		var flow_list = method_var.getFlow();
//		flow_list.add(flow_var);
//		//Add the methods in the classes' collection
//		var method_list = class_var.getMethod();
//		method_list.add(method_var);
//		method_list.add(method_var2);
//		//Add the class in the assemblies' collection
//		var class_list = assembly_var.getClass_();
//		class_list.add(class_var);
//		//Add the assembly in the model assemblies' collection
//		var assembly_list = root.getAssembly();
//		assembly_list.add(assembly_var);
//		
//		gastResource.getContents().add(root);
//		saveResource(gastResource);
//	}


}
