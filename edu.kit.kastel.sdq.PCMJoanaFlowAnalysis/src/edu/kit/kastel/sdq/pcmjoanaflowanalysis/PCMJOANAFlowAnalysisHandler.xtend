package edu.kit.kastel.sdq.pcmjoanaflowanalysis

import org.eclipse.core.commands.IHandler
import org.eclipse.core.commands.AbstractHandler
import org.eclipse.core.commands.ExecutionEvent
import org.eclipse.core.commands.ExecutionException
import java.util.List
import org.eclipse.core.resources.IFile
import org.eclipse.jface.viewers.ISelection
import org.eclipse.jface.viewers.IStructuredSelection
import java.util.Optional
import java.util.ArrayList
import org.palladiosimulator.pcm.repository.Repository
import org.palladiosimulator.pcm.system.System
import org.palladiosimulator.pcm.PcmPackage
import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl
import org.eclipse.emf.common.util.URI
import org.eclipse.emf.ecore.util.EcoreUtil
import edu.kit.kastel.sdq.pcmjoanaflowanalysis.pcmflow.PCMComposedEntityFlowAnalyzer
import java.util.Set
import java.util.HashSet
import org.palladiosimulator.pcm.usagemodel.UsageModel
import edu.kit.kastel.sdq.pcmjoanaflowanalysis.Datastructure.DataStructureBuilder
import org.eclipse.ui.handlers.HandlerUtil
import edu.kit.kastel.sdq.ecoreannotations.AnnotationRepository
import org.palladiosimulator.pcm.usagemodel.EntryLevelSystemCall
import org.palladiosimulator.pcm.usagemodel.UsageScenario
import org.palladiosimulator.pcm.usagemodel.AbstractUserAction
import edu.kit.kastel.sdq.pcmjoanaflowanalysis.Datastructure.TopmostAssemblyEntity

class PCMJOANAFlowAnalysisHandler extends AbstractHandler implements IHandler{
	
	val PCM_REPOSITORY_FILE_ENDING = "repository";
	val PCM_SYSTEM_FILE_ENDING = "system"; 
	val PCM_USAGEMODEL_FILE_ENDING = "usagemodel";
	val ECORE_ANNOTATIONS_FILE_ENDING = "ecoreannotations";
	Set<String> fileEndings;
	
	override execute(ExecutionEvent event) throws ExecutionException {
		
		fileEndings = generateFileEndingSet();
		val selection = HandlerUtil.getCurrentSelection(event);
		val list = getFilteredList(selection);
		
		if(list.present){
			executeFlowAnalysis(list.get);
		}
	}
	
	def generateFileEndingSet() {
		var fileEndings = new HashSet();
		fileEndings.add(PCM_REPOSITORY_FILE_ENDING);
		fileEndings.add(PCM_SYSTEM_FILE_ENDING);
		fileEndings.add(PCM_USAGEMODEL_FILE_ENDING);
		fileEndings.add(ECORE_ANNOTATIONS_FILE_ENDING);
		
		return fileEndings;
	}
	
	def Optional<List<IFile>> getFilteredList(ISelection selection){
		if (selection instanceof IStructuredSelection) {
			val structuredSelection = selection as IStructuredSelection
			var files = structuredSelection.toList.filter(typeof(IFile)).toList
			if (files.size > 0) {
				return Optional.ofNullable(files.filter[file | testFileForCorrectExtension(file)].toList);
			}
		}
		
		return Optional.empty;
	}
	
	
	def boolean testFileForCorrectExtension(IFile file){
		var fileExtension = file.fileExtension;
		return fileEndings.contains(fileExtension);
	} 
	
	
	def boolean executeFlowAnalysis(List<IFile> files){
		
		//TODO: Using this to ease testing with only 3 models, potentially 2 systems at once are strange 
		//	  	except for design-purposes :-\ 
		if(files.size != 4){
			return false;
		}
		
		var systemPath = "";
		var repositoryPath = "";
		var usageModelPath = "";
		var ecoreAnnotationsPath ="";
		
		for(file : files){
			switch file.fileExtension {
				case PCM_REPOSITORY_FILE_ENDING: repositoryPath = file.location.toString
				case PCM_SYSTEM_FILE_ENDING: systemPath = file.location.toString
				case PCM_USAGEMODEL_FILE_ENDING: usageModelPath = file.location.toString
				case ECORE_ANNOTATIONS_FILE_ENDING: ecoreAnnotationsPath = file.location.toString
			}
		}
		
		var registry = Resource.Factory.Registry.INSTANCE;
		var map = registry.getExtensionToFactoryMap();

		PcmPackage.eINSTANCE.eClass();
		
		
		map.put("repository", new XMIResourceFactoryImpl());
		map.put("system", new XMIResourceFactoryImpl());
		map.put("usagemodel", new XMIResourceFactoryImpl());
		map.put("ecoreannotations", new XMIResourceFactoryImpl());
		
		var resSet = new ResourceSetImpl();
		
		var systemUri = URI.createFileURI(systemPath);
		var repositoryUri = URI.createFileURI(repositoryPath);
		var usageModelUri = URI.createFileURI(usageModelPath);
		var ecoreannotationsUri = URI.createFileURI(ecoreAnnotationsPath);
	
		var resourceSystem = resSet.getResource(systemUri, true);
		var resourceRepository = resSet.getResource(repositoryUri, true);
		var resourceUsageModel = resSet.getResource(usageModelUri, true);
		var resourceEcoreAnnotations = resSet.getResource(ecoreannotationsUri, true);
		
		EcoreUtil.resolveAll(resourceRepository);
		EcoreUtil.resolveAll(resourceSystem);
		EcoreUtil.resolveAll(resourceUsageModel);
		EcoreUtil.resolveAll(resourceEcoreAnnotations);
		
		resourceRepository.load(null);
		resourceSystem.load(null);
		resourceUsageModel.load(null);
		resourceEcoreAnnotations.load(null);
		
		var repository = resourceRepository.getContents().get(0) as Repository;
		var system = resourceSystem.getContents().get(0) as System;
		var usageModel = resourceUsageModel.getContents().get(0) as UsageModel
		var ecoreAnnotationRepository = resourceEcoreAnnotations.getContents().get(0) as AnnotationRepository;
		
		var dataStructureBuilder = new DataStructureBuilder();
		var systemrepresentation = dataStructureBuilder.buildRepresentationsForSystem(system, ecoreAnnotationRepository);
	
		var pcmAnalyzer = new PCMComposedEntityFlowAnalyzer();
		
		for(UsageScenario scenario : usageModel.usageScenario_UsageModel){
			for(AbstractUserAction action : scenario.scenarioBehaviour_UsageScenario.actions_ScenarioBehaviour){
				if(action instanceof EntryLevelSystemCall){
					pcmAnalyzer.flowCalculationForEntryLevelSystemCall(systemrepresentation, action);
				}
			}
		}
		
		
		
		
		
		return false;
	}
}