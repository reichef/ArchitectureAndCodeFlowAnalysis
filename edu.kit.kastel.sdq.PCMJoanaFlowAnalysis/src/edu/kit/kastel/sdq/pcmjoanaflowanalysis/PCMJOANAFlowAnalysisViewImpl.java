
//Created by Isai Roman
//as a reference from the examples provided
//in KIT SDQ-Wiki (https://sdqweb.ipd.kit.edu/wiki/Creating_EMF_Model_instances_programmatically)

package edu.kit.kastel.sdq.pcmjoanaflowanalysis;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceImpl;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.XMLResourceFactoryImpl;
import org.eclipse.emf.ecore.xmi.impl.XMLResourceImpl;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.sirius.business.api.componentization.ViewpointRegistry;
import org.eclipse.sirius.business.api.dialect.DialectManager;
import org.eclipse.sirius.business.api.session.DefaultLocalSessionCreationOperation;
import org.eclipse.sirius.business.api.session.Session;
import org.eclipse.sirius.business.api.session.SessionManager;
import org.eclipse.sirius.business.api.session.factory.SessionFactory;
import org.eclipse.sirius.tools.api.command.semantic.AddSemanticResourceCommand;
import org.eclipse.sirius.viewpoint.DRepresentation;
import org.eclipse.sirius.viewpoint.description.RepresentationDescription;

import edu.kit.kastel.sdq.PCMJoanaFlowAnalysisDiagramModel.Assembly;
import edu.kit.kastel.sdq.PCMJoanaFlowAnalysisDiagramModel.JOANAFlowAnalysisDiagram;
import edu.kit.kastel.sdq.PCMJoanaFlowAnalysisDiagramModel.Method;
import edu.kit.kastel.sdq.PCMJoanaFlowAnalysisDiagramModel.PCMJoanaFlowAnalysisDiagramModelFactory;
import edu.kit.kastel.sdq.pcmjoanaflowanalysis.datastructure.hierarchical.AssemblyComponentContext;
import edu.kit.kastel.sdq.pcmjoanaflowanalysis.datastructure.hierarchical.AssemblyConnectorRepresentation;
import edu.kit.kastel.sdq.pcmjoanaflowanalysis.datastructure.hierarchical.SystemRepresentation;
import edu.kit.kastel.sdq.pcmjoanaflowanalysis.pcmflow.fixpoint.SystemOperationIdentifying;

public class PCMJOANAFlowAnalysisViewImpl implements PCMJOANAFlowAnalysisView {

	private static String PROJECT_PATH;
	
	public PCMJOANAFlowAnalysisViewImpl(String project_path) {
		PCMJOANAFlowAnalysisViewImpl.PROJECT_PATH = project_path;
	}

	public static Resource createAndAddResource(String outputFile, String[] fileextensions, ResourceSet rs) {
		for (String fileext : fileextensions) {
			rs.getResourceFactoryRegistry().getExtensionToFactoryMap().put(fileext, new XMLResourceFactoryImpl());
		}
		URI uri = URI.createPlatformResourceURI(outputFile,true);
		//URI uri = URI.createFileURI(outputFile);
		Resource resource = rs.createResource(uri);
		((ResourceImpl) resource).setIntrinsicIDToEObjectMap(new HashMap<>());
		return resource;
	}

	public static void saveResource(Resource resource) {
		var saveOptions = ((XMLResourceImpl) resource).getDefaultSaveOptions();
		saveOptions.put(XMLResourceImpl.OPTION_CONFIGURATION_CACHE, Boolean.TRUE);
		saveOptions.put(XMLResourceImpl.OPTION_USE_CACHED_LOOKUP_TABLE, new ArrayList<>());
		try {
			resource.save(saveOptions);
			
			//Include it in the aird specs
			NullProgressMonitor monitor = new NullProgressMonitor();
			URI uri = URI.createPlatformResourceURI(PROJECT_PATH + "/"  + "representations.aird",true); 
//			URI uri = URI.createFileURI("/Users/isairoman/eclipse-workspace_122020/ArchitectureAndCodeFlowAnalysis/CaseStudies/MinimalClientServerExample/PCMModels/ClientServerTest/representations.aird");
			var o = new DefaultLocalSessionCreationOperation(uri, monitor);
			o.execute();
			Session session = o.getCreatedSession();
			//Session session = SessionManager.INSTANCE.getSession(uri, monitor);
			//Session session = SessionFactory.INSTANCE.createSession(uri, new NullProgressMonitor());
			//session.open(monitor);
			
			org.eclipse.emf.common.util.URI semanticResourcesURI = resource.getURI();
			session.getTransactionalEditingDomain().getCommandStack().execute(new AddSemanticResourceCommand(session, semanticResourcesURI, monitor));
			
			session.save(monitor);
			session.close(monitor);
			
			//Activate the viewpoint
			
			o = new DefaultLocalSessionCreationOperation(uri, monitor);
			o.execute();
			session = o.getCreatedSession();
			
			var session2 = org.eclipse.sirius.ui.business.api.session.UserSession.from(session).selectViewpoint("JoanaFlowAnalysisViewpoint");
			
			org.eclipse.sirius.ui.business.api.session.UserSession.from(session).selectViewpoint("JoanaFlowAnalysisViewpoint");
			
			session.save(monitor);
			session.close(monitor);
			
			//Refresh the project
			for(IProject project : ResourcesPlugin.getWorkspace().getRoot().getProjects()){
			    project.refreshLocal(IResource.DEPTH_INFINITE, new NullProgressMonitor());
			}
			
			//Generate the diagram
//			monitor = new NullProgressMonitor();
//			URI platform_uri = URI.createPlatformResourceURI(project_path + "representations.aird",true); 
//			o = new DefaultLocalSessionCreationOperation(platform_uri, monitor);
//			o.execute();
//			session = o.getCreatedSession();
//			
//			EObject eDiagram = resource.getContents().get(0);
////			var viewpoints_ = session.getSelectedViewpoints(true);
////			for (var viewpoint_ : viewpoints_ ) {
////				if (viewpoint_.getName().equals("JoanaFlowAnalysisViewpoint"))
////					for (var representationDescription_ : viewpoint_.getOwnedRepresentations()) {
////						DRepresentation representation = DialectManager.INSTANCE.createRepresentation("new FDiagram", eDiagram, representationDescription_, session, monitor);
////					}
////			}
//			Collection<RepresentationDescription> descs = DialectManager.INSTANCE.getAvailableRepresentationDescriptions(session.getSelectedViewpoints(false), eDiagram);
//			for (var representationDescription_ : descs) {
//				DRepresentation representation = DialectManager.INSTANCE.createRepresentation("new FDiagram", eDiagram, representationDescription_, session, monitor);
//			}
//			
//			
//			session.save(monitor);
//			session.close(monitor);
			
			//Refresh the project
			for(IProject project : ResourcesPlugin.getWorkspace().getRoot().getProjects()){
			    project.refreshLocal(IResource.DEPTH_INFINITE, new NullProgressMonitor());
			}
			

			
		} catch (IOException e) {
			throw new RuntimeException(e);
		} catch (CoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Generate the class object, if necessary, needed to represent the diagram; otherwise it 
	 * returns the already existing class. Returns a method list from the class
	 * @param class_list
	 * @param class_name
	 * @return method_list
	 */
	public EList<edu.kit.kastel.sdq.PCMJoanaFlowAnalysisDiagramModel.Method> generateClassObject(EList<edu.kit.kastel.sdq.PCMJoanaFlowAnalysisDiagramModel.Class> class_list,
			String class_name) {
		edu.kit.kastel.sdq.PCMJoanaFlowAnalysisDiagramModel.Class class_var, class_contained = null;
		EList<edu.kit.kastel.sdq.PCMJoanaFlowAnalysisDiagramModel.Method> method_list = null;
		if (class_list.isEmpty()) {
			// Create class of the method
			class_var = PCMJoanaFlowAnalysisDiagramModelFactory.eINSTANCE.createClass();
			class_var.setName(class_name);
			class_list.add(class_var);
			// Get the methods list in the classes' collection
			method_list = class_var.getMethod();
		} else {
			// Check if the class is already contained
			for (var class_element : class_list) {
				if (class_element.getName().equalsIgnoreCase(class_name)) {
					// Keep the class object
					class_contained = class_element;
				}
			}
			if (class_contained != null) {
				// take the class instance of the method
				class_var = class_contained;
				// Get the methods list in the classes' collection
				method_list = class_var.getMethod();
			} else {
				// Create class of the method
				class_var = PCMJoanaFlowAnalysisDiagramModelFactory.eINSTANCE.createClass();
				class_var.setName(class_name);
				class_list.add(class_var);
				// Get the methods list in the classes' collection
				method_list = class_var.getMethod();
			}
		}
		
		return method_list;
	}
	
	/**
	 * Generates a Method object needed in the diagram or an existing method in the class 
	 * from method_list. It returns the generated method
	 * @param method_list
	 * @param method_name
	 * @return method_var
	 */
	Method generateMethodObject(EList<edu.kit.kastel.sdq.PCMJoanaFlowAnalysisDiagramModel.Method> method_list,String method_name) {
		Method method_var = PCMJoanaFlowAnalysisDiagramModelFactory.eINSTANCE.createMethod();
		method_var.setName(method_name);
		
		if (method_list != null) {
			// Check if the method is already contained
			edu.kit.kastel.sdq.PCMJoanaFlowAnalysisDiagramModel.Method contained_method = null;
			if (method_list.isEmpty()) {
				// Add the method to the class list
				method_list.add(method_var);
			} else {

				for (var method : method_list) {
					if (method.getName().equalsIgnoreCase(method_var.getName())) {
						// Get the method from the class list
						contained_method = method;
					}
				}
				if (contained_method != null)
					method_var = contained_method;
				else
					method_list.add(method_var);
			}

		}
		return method_var;
	}
	
	/**
	 * Generates the flow from method_origin to method_destination; increments the flow count 
	 * by the flow created and returns the result in flow_id
	 * @param method_var
	 * @param method_var2
	 * @param flow_id
	 * @return flow_id
	 */
	int createFlows(Method method_origin,Method method_destination, int flow_id) {
		
		if (method_destination.getName() != null && method_origin.getName() != null) {
			var flow_var = PCMJoanaFlowAnalysisDiagramModelFactory.eINSTANCE.createFlow();
			flow_var.setId(String.valueOf(flow_id));
			flow_var.setMethod(method_destination);
			// Add each of the objects in the respective collections
			// Add the flow in the method1 flows' collection
			var flow_list = method_origin.getFlow();
			flow_list.add(flow_var);
			// Increment the flow id
			flow_id++;
		}
		
		return flow_id;
	}
	
	public int createIntraComponentFlows(Collection<AssemblyComponentContext> diagram_assemblies,EList<Assembly> assembly_list, int flow_id) {

		for (var assembly : diagram_assemblies) {
			// Create the assembly
			var assembly_var = PCMJoanaFlowAnalysisDiagramModelFactory.eINSTANCE.createAssembly();
			assembly_var.setName(assembly.getContext().getEntityName());
			// Add the assembly in the model assemblies' collection
			assembly_list.add(assembly_var);
			// Iterate systemInducedConnection to get the flows
			Map<SystemOperationIdentifying, Collection<SystemOperationIdentifying>> flows_collection = assembly
					.getSystemInducedConnection();

			for (var flow : flows_collection.entrySet()) {
				// Get starting method
				var start_method = flow.getKey();
				String class_name = start_method.getContext().getName();

				// Add the class in the assemblies' collection
				var class_list = assembly_var.getClass_();
				// Generate the class object
				EList<edu.kit.kastel.sdq.PCMJoanaFlowAnalysisDiagramModel.Method> method_list = generateClassObject(class_list, class_name);

				// Create Method1 
				String method_name = start_method.getSignature().getEntityName();
				Method method_var = generateMethodObject(method_list, method_name); /*TODO: Same as line 223??*/
				
				// Get end method
				var end_methods = flow.getValue();

				for (var end_method : end_methods) {
					// Generate the class
					EList<edu.kit.kastel.sdq.PCMJoanaFlowAnalysisDiagramModel.Method> method_list2 = generateClassObject(class_list, class_name);

					// Create Method2
					String method_name2 = end_method.getSignature().getEntityName();
					Method method_var2 = generateMethodObject(method_list2, method_name2); /*TODO: Same as line 223??*/

					// Create intra-component Flows
					flow_id = createFlows(method_var,method_var2,flow_id);

				}

			}

		}
		return flow_id;
	}

	
	public int createInterComponentFlows(Collection<AssemblyComponentContext> diagram_assemblies,EList<Assembly> assembly_list,
			int flow_id, boolean intra_componend_end_point) {
		// Create inter-component flows
			for (var assembly : diagram_assemblies) {

			// Iterate systemInducedConnection to get the flows
			Collection<AssemblyConnectorRepresentation> flows_collection = assembly
					.getAssemblyConnectorRepresentation();

			for (var flow : flows_collection) {
				// Create inter-component flows
				var key_history = flow.getMethodHistory();

				// Get the context (Get the necessary parameters)
				String end_method_assembly = key_history.get(3);
				String end_method_class = key_history.get(4);
				String end_method_name = key_history.get(5);
				String start_method_name = key_history.get(2);
				String start_method_assembly = key_history.get(0);
				String start_method_class = key_history.get(1);

				// Search for the assembly
				edu.kit.kastel.sdq.PCMJoanaFlowAnalysisDiagramModel.Assembly assembly_var = null;
				for (var ass_list_elem : assembly_list) {
					if (ass_list_elem.getName().equalsIgnoreCase(end_method_assembly)) {
						assembly_var = ass_list_elem;
					}
				}

				if (key_history != null) {
					// Add the class in the assemblies' collection
					var class_list = assembly_var.getClass_();

					// Generate the end class object
					EList<edu.kit.kastel.sdq.PCMJoanaFlowAnalysisDiagramModel.Method> method_list = generateClassObject(class_list, end_method_class);

					// Create Method1
					Method method_var = generateMethodObject(method_list, end_method_name); 
					
					// Search for the assembly
					edu.kit.kastel.sdq.PCMJoanaFlowAnalysisDiagramModel.Assembly inter_assembly = null;
					for (var ass_list_elem : assembly_list) {
						if (ass_list_elem.getName().equalsIgnoreCase(start_method_assembly)) {
							inter_assembly = ass_list_elem;
						}
					}
					if (inter_assembly != null) {
						// Add the class in the assemblies' collection
						class_list = inter_assembly.getClass_();

					}
					
					// Generate the class object
					EList<edu.kit.kastel.sdq.PCMJoanaFlowAnalysisDiagramModel.Method> method_list2 = generateClassObject(class_list, start_method_class);

					//Generate the start method
					Method method_var2 = generateMethodObject(method_list2, start_method_name);
					
					if (intra_componend_end_point) {
						//Find if start method exists in as flow in the
						Method new_method = null;
						for (var method:method_list2) {
							//Get the flows
							edu.kit.kastel.sdq.PCMJoanaFlowAnalysisDiagramModel.Flow del_flow = null;
							for (var flow_iter:method.getFlow()) {
								if (flow_iter.getMethod() == method_var2) {
									//Add it directly to the new flow
									new_method = method;
									del_flow = flow_iter;
								}
							}
							//Remove the intra-flow to the end-point
							if (del_flow != null)
								method.getFlow().remove(del_flow);
						}
						//Remove the end-point method from the analysis
						method_list2.remove(method_var2);
						//Sets the new start method
						method_var2 = new_method;
					}

					if (method_var2.getName() != null && method_var.getName() != null) {
						// Create intra-component Flows
						flow_id = createFlows(method_var2,method_var,flow_id);
					}
				}
			}
		}
		return flow_id;
	}
	
	
	public void setupAndSaveEMFSampleInstanceResource(SystemRepresentation systemrepresentation,boolean intra_componend_end_point) {
		ResourceSet rs = new ResourceSetImpl();
		// Here the resource is created, with fileextensions "gast" and "xml" (adapt
		// this to use your own file extension).
//		Resource gastResource = createAndAddResource(
//				"/Users/isairoman/eclipse-workspace_122020/ArchitectureAndCodeFlowAnalysis/CaseStudies/MinimalClientServerExample/PCMModels/ClientServerTest/file.pcmjoanaflowanalysisdiagrammodel",
//				new String[] { "pcmjoanaflowanalysisdiagrammodel", "xml" }, rs);
		//URI uri = URI.createPlatformResourceURI(project_path + "file.pcmjoanaflowanalysisdiagrammodel",true);
		Resource gastResource = createAndAddResource(PROJECT_PATH + "/" + "file.pcmjoanaflowanalysisdiagrammodel",
				new String[] { "pcmjoanaflowanalysisdiagrammodel", "xml" }, rs);
		// The root object is created by using (adapt this to create your own root
		// object)

		// Create model
		JOANAFlowAnalysisDiagram root = PCMJoanaFlowAnalysisDiagramModelFactory.eINSTANCE
				.createJOANAFlowAnalysisDiagram();
		root.setName("JOANA Component Analysis");
		// Get the assembly_list
		var assembly_list = root.getAssembly();

		// Create assembly
		Collection<AssemblyComponentContext> diagram_assemblies = systemrepresentation.getContainedRepresentations();

		//Create the flow_id_counter
		int flow_id = 0;
		//Create intra-component flows
		flow_id = createIntraComponentFlows(diagram_assemblies,assembly_list,flow_id);

		//Create inter-component flows
		flow_id = createInterComponentFlows(diagram_assemblies,assembly_list,flow_id, intra_componend_end_point);

		gastResource.getContents().add(root);
		saveResource(gastResource);
	}

	@Override
	public void drawDiagramInstance(Object systemrepresentation, boolean intra_componend_end_point) {
		// TODO Auto-generated method stub
		setupAndSaveEMFSampleInstanceResource((SystemRepresentation)systemrepresentation, intra_componend_end_point);
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
