


import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceImpl;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.XMLResourceFactoryImpl;
import org.eclipse.emf.ecore.xmi.impl.XMLResourceImpl;


import edu.kit.kastel.sdq.PCMJoanaFlowAnalysisDiagramModel.FlowAnalysisDiagram;
import edu.kit.kastel.sdq.PCMJoanaFlowAnalysisDiagramModel.PCMJoanaFlowAnalysisDiagramModelFactory;

public class GenerateModelInstance {

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
		//Map<K, V> saveOptions = 
				((XMLResourceImpl)resource).getDefaultSaveOptions();
				//		     saveOptions.put(XMLResourceImpl.OPTION_CONFIGURATION_CACHE, Boolean.TRUE);
				//		     saveOptions.put(XMLResourceImpl.OPTION_USE_CACHED_LOOKUP_TABLE, new ArrayList<>());
				//		     try {
				//		        resource.save(saveOptions);
				//		     } catch (IOException e) {
				//		        throw new RuntimeException(e);
				//		     }
	}

	public void setupAndSaveEMFInstanceResource() {
		ResourceSet rs = new ResourceSetImpl();
		// Here the resource is created, with fileextensions "gast" and "xml" (adapt this to use your own file extension).
		Resource gastResource = createAndAddResource("C:/file.gast", new String[] {"gast", "xml"}, rs);
		// The root object is created by using (adapt this to create your own root object)
		FlowAnalysisDiagram root = PCMJoanaFlowAnalysisDiagramModelFactory.eINSTANCE.createJOANAFlowAnalysisDiagram();  
		gastResource.getContents().add(root);
		saveResource(gastResource);
	}

	public static void main () {
		GenerateModelInstance gen = new GenerateModelInstance();
		gen.setupAndSaveEMFInstanceResource();
	}
}
