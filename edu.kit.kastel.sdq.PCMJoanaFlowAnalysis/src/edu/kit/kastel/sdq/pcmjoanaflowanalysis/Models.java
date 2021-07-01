package edu.kit.kastel.sdq.pcmjoanaflowanalysis;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IPath;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.Resource.Factory.Registry;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.eclipse.xtend.lib.macro.file.Path;
import org.palladiosimulator.pcm.PcmPackage;
import org.palladiosimulator.pcm.repository.Repository;
import org.palladiosimulator.pcm.usagemodel.UsageModel;

import com.cedarsoftware.util.io.JsonReader;
import edu.kit.joana.component.connector.Utils;

import org.palladiosimulator.pcm.system.System;
import edu.kit.kastel.sdq.ecoreannotations.AnnotationRepository;

public class Models {

	private static final String PCM_REPOSITORY_FILE_ENDING = "repository";
	private static final String PCM_SYSTEM_FILE_ENDING = "system"; 
	private static final String PCM_USAGEMODEL_FILE_ENDING = "usagemodel";
	private static final String ECORE_ANNOTATIONS_FILE_ENDING = "ecoreannotations";
	private static final String CONFIG_NAME = "config.json";
	
	private System system;
	private UsageModel usageModel;
	private Repository repository;
	private AnnotationRepository annotationRepository;
	private Config config;
	private boolean success;
	
	public Models(System system, UsageModel usageModel, Repository repository, AnnotationRepository annotationRepository, Config config, boolean success) {
		this.system = system;
		this.usageModel = usageModel;
		this.repository = repository;
		this.annotationRepository = annotationRepository;
		this.config = config;
		this.success = success;
	}
	
	public System getSystem() {
		return system;
	}
	public UsageModel getUsageModel() {
		return usageModel;
	}
	public Repository getRepository() {
		return repository;
	}
	public AnnotationRepository getAnnotationRepository() {
		return annotationRepository;
	}
	
	public Config getConfig() {
		return config;
	}
	
	
	public static Models extractFromFiles(List<IFile> files) throws IOException {
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
			java.lang.System.err.println("Did not found one of: Repository, System, UsageModel or EcoreAnnotations");
			return new Models(null,null, null, null, null, false);
		}
		
		Config config = null;
		
		if(configPath.isEmpty()) {
			String path = systemPath.removeFileExtension().removeLastSegments(1).makeAbsolute().toString();
			if(Paths.get(path).toFile().exists()) {
				java.lang.System.err.println("Config-File exists but was not selected properly. Start again with selected config-file.");
				return new Models(null,null, null, null, null, false);
			}
			config = new Config(Paths.get(""), Paths.get(""), true, true, "");
			Utils.store(Paths.get(path + IPath.SEPARATOR + CONFIG_NAME), config);
			java.lang.System.err.println("Created Config-File. Complete content and run analysis again");
			return new Models(null,null, null, null, null, false);
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
		AnnotationRepository annotationRepository = (AnnotationRepository) resourceEcoreAnnotations.getContents().get(0);
		
		return new Models(system, usageModel, repository, annotationRepository, config, true);
	}

	public boolean isSuccess() {
		return success;
	}
	
}
