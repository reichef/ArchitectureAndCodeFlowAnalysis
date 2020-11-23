package edu.kit.kastel.sdq.pcmjoanaflowanalysis;

import org.eclipse.core.commands.IHandler;
import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import java.util.List;
import java.util.Map;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IPath;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import java.util.Optional;
import java.util.ArrayList;
import org.palladiosimulator.pcm.repository.Repository;
import org.palladiosimulator.pcm.system.System;
import org.palladiosimulator.pcm.PcmPackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.Resource.Factory.Registry;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.util.EcoreUtil;
import edu.kit.kastel.sdq.pcmjoanaflowanalysis.pcmflow.PCMComposedEntityFlowAnalyzer;
import org.palladiosimulator.pcm.usagemodel.UsageModel;
import edu.kit.kastel.sdq.pcmjoanaflowanalysis.Datastructure.DataStructureBuilder;
import org.eclipse.ui.handlers.HandlerUtil;

import edu.kit.joana.component.connector.Util;
import edu.kit.kastel.sdq.ecoreannotations.AnnotationRepository;
import org.palladiosimulator.pcm.usagemodel.EntryLevelSystemCall;
import org.palladiosimulator.pcm.usagemodel.UsageScenario;
import org.palladiosimulator.pcm.usagemodel.AbstractUserAction;
import edu.kit.kastel.sdq.pcmjoanaflowanalysis.Datastructure.TopmostAssemblyEntity;
import com.cedarsoftware.util.io.JsonReader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class PCMJOANAFlowAnalysisHandler extends AbstractHandler implements IHandler{
	private static final String PCM_REPOSITORY_FILE_ENDING = "repository";
	private static final String PCM_SYSTEM_FILE_ENDING = "system"; 
	private static final String PCM_USAGEMODEL_FILE_ENDING = "usagemodel";
	private static final String ECORE_ANNOTATIONS_FILE_ENDING = "ecoreannotations";
	private static final String CONFIG_NAME = "config.json";
	
	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		ISelection selection = HandlerUtil.getCurrentSelection(event);
		Optional<List<IFile>> list = getFilteredList(selection);
		
		if(list.isPresent()){
			try {
				executeFlowAnalysis(list.get());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return list;
	}

	
	private Optional<List<IFile>> getFilteredList(ISelection selection){
		if (selection instanceof IStructuredSelection) {
			IStructuredSelection structuredSelection = (IStructuredSelection)selection;
			
			Object[] filesTmp = structuredSelection.toArray();
			List<IFile> files = new ArrayList<IFile>();
			
			for(int i = 0; i < filesTmp.length; i++) {
				if(filesTmp[i] instanceof IFile) {
					files.add((IFile) filesTmp[i]);
				}
			}

			if (files.size() > 0) {
				return Optional.ofNullable(files);
			}
		}
		
		return Optional.empty();
	}
	
	private boolean executeFlowAnalysis(List<IFile> files) throws IOException{
		
		//TODO: Using this to ease testing with only 3 models, potentially 2 systems at once are strange 
		//	  	except for design-purposes :-\ 
		
		IPath systemPath = null; 
		IPath repositoryPath = null;
		IPath usageModelPath = null;
		IPath ecoreAnnotationsPath= null; 
		String configPath = "";
		
		for(IFile file : files){
			switch (file.getFileExtension()) {
				case PCM_REPOSITORY_FILE_ENDING: repositoryPath = file.getLocation(); break;
				case PCM_SYSTEM_FILE_ENDING: systemPath = file.getLocation(); break;
				case PCM_USAGEMODEL_FILE_ENDING: usageModelPath = file.getLocation();break;
				case ECORE_ANNOTATIONS_FILE_ENDING: ecoreAnnotationsPath = file.getLocation(); break;
				default: break;
			}
			
			if((file.getName()).equals(CONFIG_NAME)){
				configPath = file.getLocation().makeAbsolute().toString();
			}
		}
		
		if(systemPath == null || ecoreAnnotationsPath == null || usageModelPath == null) {
			return false;
		}
		
		Config config = null;
		
		if(configPath.isEmpty()) {
			config = new Config(Paths.get(""), Paths.get(""), true, true, "");
			String path = systemPath.removeFileExtension().removeLastSegments(1).makeAbsolute().toString();
			Util.store(Paths.get(path + IPath.SEPARATOR + CONFIG_NAME), config);
			java.lang.System.out.println("Default paths used, errors may occure");
		} else {
			config = (Config) JsonReader.jsonToJava(String.join("\n", Files.readAllLines(Paths.get(configPath))));
		}
		
		Registry registry = Resource.Factory.Registry.INSTANCE;
		Map<String, Object> map = registry.getExtensionToFactoryMap();

		PcmPackage.eINSTANCE.eClass();
		
		
		map.put("repository", new XMIResourceFactoryImpl());
		map.put("system", new XMIResourceFactoryImpl());
		map.put("usagemodel", new XMIResourceFactoryImpl());
		map.put("ecoreannotations", new XMIResourceFactoryImpl());
		
		ResourceSetImpl resSet = new ResourceSetImpl();
		
		URI systemUri = URI.createFileURI(systemPath.toString());
		URI repositoryUri = URI.createFileURI(repositoryPath.toString());
		URI usageModelUri = URI.createFileURI(usageModelPath.toString());
		URI ecoreannotationsUri = URI.createFileURI(ecoreAnnotationsPath.toString());
	
		Resource resourceSystem = resSet.getResource(systemUri, true);
		Resource resourceRepository = resSet.getResource(repositoryUri, true);
		Resource resourceUsageModel = resSet.getResource(usageModelUri, true);
		Resource resourceEcoreAnnotations = resSet.getResource(ecoreannotationsUri, true);
		
		EcoreUtil.resolveAll(resourceRepository);
		EcoreUtil.resolveAll(resourceSystem);
		EcoreUtil.resolveAll(resourceUsageModel);
		EcoreUtil.resolveAll(resourceEcoreAnnotations);
		
		resourceRepository.load(null);
		resourceSystem.load(null);
		resourceUsageModel.load(null);
		resourceEcoreAnnotations.load(null);
		
		Repository repository = (Repository) resourceRepository.getContents().get(0);
		System system = (System) resourceSystem.getContents().get(0);
		UsageModel usageModel = (UsageModel) resourceUsageModel.getContents().get(0);
		AnnotationRepository ecoreAnnotationRepository = (AnnotationRepository) resourceEcoreAnnotations.getContents().get(0);
		
		DataStructureBuilder dataStructureBuilder = new DataStructureBuilder();
		TopmostAssemblyEntity systemrepresentation = dataStructureBuilder.buildRepresentationsForSystem(system, ecoreAnnotationRepository);
	
		PCMComposedEntityFlowAnalyzer pcmAnalyzer = new PCMComposedEntityFlowAnalyzer(config);
		
		for(UsageScenario scenario : usageModel.getUsageScenario_UsageModel()){
			for(AbstractUserAction action : scenario.getScenarioBehaviour_UsageScenario().getActions_ScenarioBehaviour()){
				if(action instanceof EntryLevelSystemCall){
					pcmAnalyzer.flowCalculationForEntryLevelSystemCall(systemrepresentation, (EntryLevelSystemCall)action);
				}
			}
		}
		
		
		
		
		
		return false;
	}

}
