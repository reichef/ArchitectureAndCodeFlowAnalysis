
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
import java.util.HashSet;
import java.util.Map;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EStructuralFeature.Setting;
import org.eclipse.emf.ecore.impl.EStructuralFeatureImpl;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceImpl;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.XMLResourceFactoryImpl;
import org.eclipse.emf.ecore.xmi.impl.XMLResourceImpl;
import org.eclipse.sirius.business.api.componentization.ViewpointRegistry;
import org.eclipse.sirius.business.api.dialect.DialectManager;
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

public class GenerateDiagramModelInstance {

	private String diagram_path;

	public static Resource createAndAddResource(String outputFile, String[] fileextensions, ResourceSet rs) {
		for (String fileext : fileextensions) {
			rs.getResourceFactoryRegistry().getExtensionToFactoryMap().put(fileext, new XMLResourceFactoryImpl());
		}
		URI uri = URI.createFileURI(outputFile);
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
			URI uri = URI.createFileURI("/Users/isairoman/eclipse-workspace_122020/ArchitectureAndCodeFlowAnalysis/CaseStudies/MinimalClientServerExample/PCMModels/ClientServerTest/representations.aird");
			Session session = SessionManager.INSTANCE.getSession(uri, monitor);
			session.open(monitor);
			
			org.eclipse.emf.common.util.URI semanticResourcesURI = resource.getURI();
			session.getTransactionalEditingDomain().getCommandStack().execute(new AddSemanticResourceCommand(session, semanticResourcesURI, monitor));
			session.save(monitor);
			//Activate the diagram
			org.eclipse.sirius.ui.business.api.session.UserSession.from(session).selectViewpoint("JoanaFlowAnalysisViewpoint");
			var viewpoints_ = session.getSelectedViewpoints(true);
			RepresentationDescription representation_ = null;
			for (var viewpoint_ : viewpoints_ ) {
				if (viewpoint_.getName().equals("JoanaFlowAnalysisViewpoint"))
					for (var representationDescription_ : viewpoint_.getOwnedRepresentations()) {
						representation_ = representationDescription_;
					}
			}
			//var viewpointregistry_ = ViewpointRegistry.getInstance();
			
			DRepresentation representation = DialectManager.INSTANCE.createRepresentation("Joana Flow Analyis", null, representation_, session, monitor);
			session.save(monitor);
			
		} catch (IOException e) {
			throw new RuntimeException(e);
//		} catch (CoreException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
		}
	}

	public void setupAndSaveEMFSampleInstanceResource(SystemRepresentation systemrepresentation) {
		ResourceSet rs = new ResourceSetImpl();
		// Here the resource is created, with fileextensions "gast" and "xml" (adapt
		// this to use your own file extension).
		Resource gastResource = createAndAddResource(
				"/Users/isairoman/eclipse-workspace_122020/ArchitectureAndCodeFlowAnalysis/CaseStudies/MinimalClientServerExample/PCMModels/ClientServerTest/file.pcmjoanaflowanalysisdiagrammodel",
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

		int flow_id = 0;
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
				// Create Method1
				var method_var = PCMJoanaFlowAnalysisDiagramModelFactory.eINSTANCE.createMethod();
				method_var.setName(start_method.getSignature().getEntityName());

				// Add the class in the assemblies' collection
				var class_list = assembly_var.getClass_();
				// Generate the class object
				edu.kit.kastel.sdq.PCMJoanaFlowAnalysisDiagramModel.Class class_var, class_contained = null;
				EList<edu.kit.kastel.sdq.PCMJoanaFlowAnalysisDiagramModel.Method> method_list = null;
				if (class_list.isEmpty()) {
					// Create class of the method
					class_var = PCMJoanaFlowAnalysisDiagramModelFactory.eINSTANCE.createClass();
					class_var.setName(start_method.getContext().getName());
					class_list.add(class_var);
					// Get the methods list in the classes' collection
					method_list = class_var.getMethod();
				} else {
					// Check if the class is already contained
					for (var class_element : class_list) {
						if (class_element.getName().equals(start_method.getContext().getName())) {
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
						class_var.setName(start_method.getContext().getName());
						class_list.add(class_var);
						// Get the methods list in the classes' collection
						method_list = class_var.getMethod();
					}
				}

				if (method_list != null) {
					// Check if the method is already contained
					edu.kit.kastel.sdq.PCMJoanaFlowAnalysisDiagramModel.Method contained_method = null;
					if (method_list.isEmpty()) {
						// Add the method to the class list
						method_list.add(method_var);
					} else {

						for (var method : method_list) {
							if (method.getName() == method_var.getName()) {
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

				// Get end method
				var end_methods = flow.getValue();

				for (var end_method : end_methods) {
					// Create Method2
					var method_var2 = PCMJoanaFlowAnalysisDiagramModelFactory.eINSTANCE.createMethod();

					method_var2.setName(end_method.getSignature().getEntityName());

					// Generate the class object
					edu.kit.kastel.sdq.PCMJoanaFlowAnalysisDiagramModel.Class class_var2, class_contained2 = null;
					EList<edu.kit.kastel.sdq.PCMJoanaFlowAnalysisDiagramModel.Method> method_list2 = null;
					if (class_list.isEmpty()) {
						// Create class of the method
						class_var2 = PCMJoanaFlowAnalysisDiagramModelFactory.eINSTANCE.createClass();
						class_var2.setName(start_method.getContext().getName());
						class_list.add(class_var2);
						// Get the methods list in the classes' collection
						method_list2 = class_var2.getMethod();
					} else {
						// Check if the class is already contained
						for (var class_element : class_list) {
							if (class_element.getName().equals(start_method.getContext().getName())) {
								// Get the class found
								class_contained2 = class_element;
							}
						}
						if (class_contained2 != null) {
							// Create class of the method
							class_var2 = class_contained2;
							// Get the methods list in the classes' collection
							method_list2 = class_var2.getMethod();
						} else {
							// Create class of the method
							class_var2 = PCMJoanaFlowAnalysisDiagramModelFactory.eINSTANCE.createClass();
							class_var2.setName(start_method.getContext().getName());
							class_list.add(class_var2);
							// Get the methods list in the classes' collection
							method_list2 = class_var2.getMethod();
						}
					}

					if (method_list2 != null) {
						// Check if the method is already contained
						edu.kit.kastel.sdq.PCMJoanaFlowAnalysisDiagramModel.Method contained_method2 = null;
						if (method_list2.isEmpty()) {
							// Add the method to the class list
							method_list2.add(method_var2);
						} else {
							for (var method : method_list2) {
								if (method.getName() == method_var2.getName()) {
									// Get the method from the class list
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
						// Create intracomponent Flows
						var flow_var = PCMJoanaFlowAnalysisDiagramModelFactory.eINSTANCE.createFlow();
						flow_var.setId(String.valueOf(flow_id));
						flow_var.setMethod(method_var2);
						// Add each of the objects in the respective collections
						// Add the flow in the method1 flows' collection
						var flow_list = method_var.getFlow();
						flow_list.add(flow_var);
						// Increment the flow id
						flow_id++;
					}

				}

			}

		}

		// Create inter-component flows
		for (var assembly : diagram_assemblies) {

			// Iterate systemInducedConnection to get the flows
			Collection<AssemblyConnectorRepresentation> flows_collection = assembly
					.getAssemblyConnectorRepresentation();

			for (var flow : flows_collection) {

				// Create inter-component flows
				var key_history = flow.getMethodHistory();

				// Get the context
				String end_method_assembly = key_history.get(3);
				String end_method_class = key_history.get(4);

				// Search the assembly
				edu.kit.kastel.sdq.PCMJoanaFlowAnalysisDiagramModel.Assembly assembly_var = null;
				for (var ass_list_elem : assembly_list) {
					if (ass_list_elem.getName().equalsIgnoreCase(end_method_assembly)) {
						assembly_var = ass_list_elem;
					}
				}

				if (key_history != null) {
					// Create Method1
					var method_var = PCMJoanaFlowAnalysisDiagramModelFactory.eINSTANCE.createMethod();
					method_var.setName(key_history.get(5));

					// Add the class in the assemblies' collection
					var class_list = assembly_var.getClass_();

					// Generate the class object
					edu.kit.kastel.sdq.PCMJoanaFlowAnalysisDiagramModel.Class class_var, class_contained = null;
					EList<edu.kit.kastel.sdq.PCMJoanaFlowAnalysisDiagramModel.Method> method_list = null;
					if (class_list.isEmpty()) {
						// Create class of the method
						class_var = PCMJoanaFlowAnalysisDiagramModelFactory.eINSTANCE.createClass();
						class_var.setName(end_method_class);
						class_list.add(class_var);
						// Get the methods list in the classes' collection
						method_list = class_var.getMethod();
					} else {
						// Check if the class is already contained
						for (var class_element : class_list) {
							if (class_element.getName().equalsIgnoreCase(end_method_class)) {
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
							class_var.setName(end_method_class);
							class_list.add(class_var);
							// Get the methods list in the classes' collection
							method_list = class_var.getMethod();
						}
					}

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

					var start_method = key_history.get(2);
					var method_var2 = PCMJoanaFlowAnalysisDiagramModelFactory.eINSTANCE.createMethod();
					method_var2.setName(start_method);

					// Get the context
					// TODO: Enhance with the assembly
					String start_method_assembly = key_history.get(0);
					String start_method_class = key_history.get(1);

					// Search the assembly
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
					edu.kit.kastel.sdq.PCMJoanaFlowAnalysisDiagramModel.Class class_var2, class_contained2 = null;
					EList<edu.kit.kastel.sdq.PCMJoanaFlowAnalysisDiagramModel.Method> method_list2 = null;
					if (class_list.isEmpty()) {
						// Create class of the method
						class_var2 = PCMJoanaFlowAnalysisDiagramModelFactory.eINSTANCE.createClass();
						class_var2.setName(start_method_class);
						class_list.add(class_var2);
						// Get the methods list in the classes' collection
						method_list = class_var2.getMethod();
					} else {
						// Check if the class is already contained
						for (var class_element : class_list) {
							if (class_element.getName().equalsIgnoreCase(start_method_class)) {
								// Keep the class object
								class_contained2 = class_element;
							}
						}
						if (class_contained2 != null) {
							// take the class instance of the method
							class_var2 = class_contained2;
							// Get the methods list in the classes' collection
							method_list = class_var2.getMethod();
						} else {
							// Create class of the method
							class_var2 = PCMJoanaFlowAnalysisDiagramModelFactory.eINSTANCE.createClass();
							class_var2.setName(start_method);
							class_list.add(class_var2);
							// Get the methods list in the classes' collection
							method_list = class_var2.getMethod();
						}
					}

					if (method_list != null) {
						// Check if the method is already contained
						edu.kit.kastel.sdq.PCMJoanaFlowAnalysisDiagramModel.Method contained_method = null;
						if (method_list.isEmpty()) {
							// Add the method to the class list
							method_list.add(method_var2);
						} else {

							for (var method : method_list) {
								if (method.getName().equalsIgnoreCase(method_var2.getName())) {
									// Get the method from the class list
									contained_method = method;
								}
							}
							if (contained_method != null)
								method_var2 = contained_method;
							else
								method_list.add(method_var2);
						}

					}

					if (method_var2.getName() != null && method_var.getName() != null) {
						// Create intracomponent Flows
						var flow_var = PCMJoanaFlowAnalysisDiagramModelFactory.eINSTANCE.createFlow();
						flow_var.setId(String.valueOf(flow_id));
						flow_var.setMethod(method_var);
						// Add each of the objects in the respective collections
						// Add the flow in the method1 flows' collection
						var flow_list = method_var2.getFlow();
						flow_list.add(flow_var);
						// Increment the flow id
						flow_id++;
					}

				}

			}

		}

		gastResource.getContents().add(root);
		saveResource(gastResource);
	}

	public void setupAndSaveEMFSampleInstanceResource_2(SystemRepresentation systemrepresentation) {
		ResourceSet rs = new ResourceSetImpl();
		// Here the resource is created, with fileextensions "gast" and "xml" (adapt
		// this to use your own file extension).
		Resource gastResource = createAndAddResource(
				"/Users/isairoman/eclipse-workspace_122020/ArchitectureAndCodeFlowAnalysis/CaseStudies/MinimalClientServerExample/PCMModels/ClientServerTest/file.pcmjoanaflowanalysisdiagrammodel",
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

		int flow_id = 0;
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
				// Create Method1
				var method_var = PCMJoanaFlowAnalysisDiagramModelFactory.eINSTANCE.createMethod();
				method_var.setName(start_method.getSignature().getEntityName());

				// Add the class in the assemblies' collection
				var class_list = assembly_var.getClass_();
				// Generate the class object
				edu.kit.kastel.sdq.PCMJoanaFlowAnalysisDiagramModel.Class class_var, class_contained = null;
				EList<edu.kit.kastel.sdq.PCMJoanaFlowAnalysisDiagramModel.Method> method_list = null;
				if (class_list.isEmpty()) {
					// Create class of the method
					class_var = PCMJoanaFlowAnalysisDiagramModelFactory.eINSTANCE.createClass();
					class_var.setName(start_method.getContext().getName());
					class_list.add(class_var);
					// Get the methods list in the classes' collection
					method_list = class_var.getMethod();
				} else {
					// Check if the class is already contained
					for (var class_element : class_list) {
						if (class_element.getName().equals(start_method.getContext().getName())) {
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
						class_var.setName(start_method.getContext().getName());
						class_list.add(class_var);
						// Get the methods list in the classes' collection
						method_list = class_var.getMethod();
					}
				}

				if (method_list != null) {
					// Check if the method is already contained
					edu.kit.kastel.sdq.PCMJoanaFlowAnalysisDiagramModel.Method contained_method = null;
					if (method_list.isEmpty()) {
						// Add the method to the class list
						method_list.add(method_var);
					} else {

						for (var method : method_list) {
							if (method.getName() == method_var.getName()) {
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

				// Get end method
				var end_methods = flow.getValue();

				for (var end_method : end_methods) {
					// Create Method2
					var method_var2 = PCMJoanaFlowAnalysisDiagramModelFactory.eINSTANCE.createMethod();

					method_var2.setName(end_method.getSignature().getEntityName());

					// Generate the class object
					edu.kit.kastel.sdq.PCMJoanaFlowAnalysisDiagramModel.Class class_var2, class_contained2 = null;
					EList<edu.kit.kastel.sdq.PCMJoanaFlowAnalysisDiagramModel.Method> method_list2 = null;
					if (class_list.isEmpty()) {
						// Create class of the method
						class_var2 = PCMJoanaFlowAnalysisDiagramModelFactory.eINSTANCE.createClass();
						class_var2.setName(start_method.getContext().getName());
						class_list.add(class_var2);
						// Get the methods list in the classes' collection
						method_list2 = class_var2.getMethod();
					} else {
						// Check if the class is already contained
						for (var class_element : class_list) {
							if (class_element.getName().equals(start_method.getContext().getName())) {
								// Get the class found
								class_contained2 = class_element;
							}
						}
						if (class_contained2 != null) {
							// Create class of the method
							class_var2 = class_contained2;
							// Get the methods list in the classes' collection
							method_list2 = class_var2.getMethod();
						} else {
							// Create class of the method
							class_var2 = PCMJoanaFlowAnalysisDiagramModelFactory.eINSTANCE.createClass();
							class_var2.setName(start_method.getContext().getName());
							class_list.add(class_var2);
							// Get the methods list in the classes' collection
							method_list2 = class_var2.getMethod();
						}
					}

					if (method_list2 != null) {
						// Check if the method is already contained
						edu.kit.kastel.sdq.PCMJoanaFlowAnalysisDiagramModel.Method contained_method2 = null;
						if (method_list2.isEmpty()) {
							// Add the method to the class list
							method_list2.add(method_var2);
						} else {
							for (var method : method_list2) {
								if (method.getName() == method_var2.getName()) {
									// Get the method from the class list
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
						// Create intracomponent Flows
						var flow_var = PCMJoanaFlowAnalysisDiagramModelFactory.eINSTANCE.createFlow();
						flow_var.setId(String.valueOf(flow_id));
						flow_var.setMethod(method_var2);
						// Add each of the objects in the respective collections
						// Add the flow in the method1 flows' collection
						var flow_list = method_var.getFlow();
						flow_list.add(flow_var);
						// Increment the flow id
						flow_id++;
					}

				}

			}

		}

		// Create inter-component flows
		for (var assembly : diagram_assemblies) {

			// Get the context
			String end_method_assembly = assembly.getContext().getEntityName();
			String end_method_class = assembly.getName();

			// Search the assembly
			edu.kit.kastel.sdq.PCMJoanaFlowAnalysisDiagramModel.Assembly assembly_var = null;
			for (var ass_list_elem : assembly_list) {
				if (ass_list_elem.getName() == end_method_assembly) {
					assembly_var = ass_list_elem;
				}
			}
			// Iterate systemInducedConnection to get the flows
			Map<SystemOperationIdentifying, Collection<SystemOperationIdentifying>> flows_collection = assembly
					.getSystemInducedConnection();

			for (var flow : flows_collection.entrySet()) {
				// Get starting method
				var end_method = flow.getKey();

				// Create inter-component flows
				var key_history = end_method.getMethodHistory();

				if (key_history != null) {
					// Create Method1
					var method_var = PCMJoanaFlowAnalysisDiagramModelFactory.eINSTANCE.createMethod();
					method_var.setName(end_method.getSignature().getEntityName());

					// Add the class in the assemblies' collection
					var class_list = assembly_var.getClass_();

					// Generate the class object
					edu.kit.kastel.sdq.PCMJoanaFlowAnalysisDiagramModel.Class class_var, class_contained = null;
					EList<edu.kit.kastel.sdq.PCMJoanaFlowAnalysisDiagramModel.Method> method_list = null;
					if (class_list.isEmpty()) {
						// Create class of the method
						class_var = PCMJoanaFlowAnalysisDiagramModelFactory.eINSTANCE.createClass();
						class_var.setName(end_method.getContext().getName());
						class_list.add(class_var);
						// Get the methods list in the classes' collection
						method_list = class_var.getMethod();
					} else {
						// Check if the class is already contained
						for (var class_element : class_list) {
							if (class_element.getName().equals(end_method.getContext().getName())) {
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
							class_var.setName(end_method.getContext().getName());
							class_list.add(class_var);
							// Get the methods list in the classes' collection
							method_list = class_var.getMethod();
						}
					}

					if (method_list != null) {
						// Check if the method is already contained
						edu.kit.kastel.sdq.PCMJoanaFlowAnalysisDiagramModel.Method contained_method = null;
						if (method_list.isEmpty()) {
							// Add the method to the class list
							method_list.add(method_var);
						} else {

							for (var method : method_list) {
								if (method.getName() == method_var.getName()) {
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

					for (var val : key_history) {
						for (var key_history_pair : val.entrySet()) {
							var start_method = key_history_pair.getKey();
							var method_var2 = PCMJoanaFlowAnalysisDiagramModelFactory.eINSTANCE.createMethod();
							method_var2.setName(start_method.getSignature().getEntityName());

							// Get the context
							var start_method_context = key_history_pair.getValue();
							String start_method_assembly = start_method_context.getContext().getEntityName();
							String start_method_class = start_method_context.getName();

							// Search the assembly
							edu.kit.kastel.sdq.PCMJoanaFlowAnalysisDiagramModel.Assembly inter_assembly = null;
							for (var ass_list_elem : assembly_list) {
								if (ass_list_elem.getName() == start_method_assembly) {
									inter_assembly = ass_list_elem;
								}
							}

							if (inter_assembly != null) {
								// Add the class in the assemblies' collection
								class_list = inter_assembly.getClass_();

							}
							// Generate the class object
							edu.kit.kastel.sdq.PCMJoanaFlowAnalysisDiagramModel.Class class_var2,
									class_contained2 = null;
							EList<edu.kit.kastel.sdq.PCMJoanaFlowAnalysisDiagramModel.Method> method_list2 = null;
							if (class_list.isEmpty()) {
								// Create class of the method
								class_var2 = PCMJoanaFlowAnalysisDiagramModelFactory.eINSTANCE.createClass();
								class_var2.setName(start_method_class);
								class_list.add(class_var2);
								// Get the methods list in the classes' collection
								method_list = class_var2.getMethod();
							} else {
								// Check if the class is already contained
								for (var class_element : class_list) {
									if (class_element.getName().equals(start_method.getContext().getName())) {
										// Keep the class object
										class_contained2 = class_element;
									}
								}
								if (class_contained2 != null) {
									// take the class instance of the method
									class_var2 = class_contained2;
									// Get the methods list in the classes' collection
									method_list = class_var2.getMethod();
								} else {
									// Create class of the method
									class_var2 = PCMJoanaFlowAnalysisDiagramModelFactory.eINSTANCE.createClass();
									class_var2.setName(start_method.getContext().getName());
									class_list.add(class_var2);
									// Get the methods list in the classes' collection
									method_list = class_var2.getMethod();
								}
							}

							if (method_list != null) {
								// Check if the method is already contained
								edu.kit.kastel.sdq.PCMJoanaFlowAnalysisDiagramModel.Method contained_method = null;
								if (method_list.isEmpty()) {
									// Add the method to the class list
									method_list.add(method_var2);
								} else {

									for (var method : method_list) {
										if (method.getName() == method_var2.getName()) {
											// Get the method from the class list
											contained_method = method;
										}
									}
									if (contained_method != null)
										method_var2 = contained_method;
									else
										method_list.add(method_var2);
								}

							}

							if (method_var2.getName() != null && method_var.getName() != null) {
								// Create intracomponent Flows
								var flow_var = PCMJoanaFlowAnalysisDiagramModelFactory.eINSTANCE.createFlow();
								flow_var.setId(String.valueOf(flow_id));
								flow_var.setMethod(method_var);
								// Add each of the objects in the respective collections
								// Add the flow in the method1 flows' collection
								var flow_list = method_var2.getFlow();
								flow_list.add(flow_var);
								// Increment the flow id
								flow_id++;
							}

						}
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
