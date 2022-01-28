package edu.kit.kastel.sdq.pcmjoanaflowanalysis;

import org.eclipse.core.commands.IHandler;
import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import java.util.Optional;
import java.util.ArrayList;

import org.palladiosimulator.pcm.system.System;

import edu.kit.kastel.sdq.pcmjoanaflowanalysis.analysiscoupling.PCMJOANACoupler;
import edu.kit.kastel.sdq.pcmjoanaflowanalysis.datastructure.hierarchical.DataStructureBuilder;
import edu.kit.kastel.sdq.pcmjoanaflowanalysis.datastructure.hierarchical.SystemRepresentation;
import edu.kit.kastel.sdq.pcmjoanaflowanalysis.pcmflow.fixpoint.FixpointIteration;
import edu.kit.kastel.sdq.pcmjoanaflowanalysis.pcmutil.PCMSubtypeResolver;

import org.palladiosimulator.pcm.usagemodel.UsageModel;
import org.eclipse.ui.handlers.HandlerUtil;

import edu.kit.kastel.sdq.ecoreannotations.AnnotationRepository;
import org.palladiosimulator.pcm.usagemodel.EntryLevelSystemCall;
import org.palladiosimulator.pcm.usagemodel.UsageScenario;
import org.palladiosimulator.pcm.usagemodel.AbstractUserAction;

import java.io.IOException;

//import edu.kit.kastel.sdq.PCMJoanaFlowAnalysisDiagram.*;
import org.eclipse.emf.common.util.URI;

public class PCMJOANAFlowAnalysisHandler extends AbstractHandler implements IHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		ISelection selection = HandlerUtil.getCurrentSelection(event);
		Optional<List<IFile>> list = getFilteredList(selection);
		if (list.isPresent()) {
			try {
				executeFlowAnalysis(list.get());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return list;
	}

	private Optional<List<IFile>> getFilteredList(ISelection selection) {
		List<IFile> files = new ArrayList<IFile>();

		if (selection instanceof IStructuredSelection) {
			IStructuredSelection structuredSelection = (IStructuredSelection) selection;

			Object[] filesTmp = structuredSelection.toArray();

			for (int i = 0; i < filesTmp.length; i++) {
				if (filesTmp[i] instanceof IFile) {
					files.add((IFile) filesTmp[i]);
				}
			}

			if (files.size() > 0) {
				return Optional.ofNullable(files);
			}
		}

		return Optional.empty();
	}

	private boolean executeFlowAnalysis(List<IFile> files) throws IOException {

		Models models = Models.extractFromFiles(files);

		if (!models.isSuccess()) {
			return false;
		}

		SystemRepresentation systemrepresentation = buildDataStructure(models.getSystem(),
				models.getAnnotationRepository());

		PCMJOANACoupler coupler = new PCMJOANACoupler(models.getConfig());
		FixpointIteration pcmAnalyzer = new FixpointIteration(coupler);
		analyseFlowsFromEntryLevelSystemCalls(models.getUsageModel(), pcmAnalyzer, systemrepresentation);
		
		//TODO: Generate the diagram model
		GenerateDiagramModelInstance diagram = new GenerateDiagramModelInstance();
		diagram.setupAndSaveEMFSampleInstanceResource(systemrepresentation);
		//end TODO
		
		java.lang.System.out.println("Finished Execution");

		return true;
	}

	private SystemRepresentation buildDataStructure(System system, AnnotationRepository annotationRepository) {
		DataStructureBuilder dataStructureBuilder = new DataStructureBuilder();
		SystemRepresentation systemrepresentation = dataStructureBuilder.buildRepresentationsForSystem(system,
				annotationRepository);

		return systemrepresentation;
	}

	private void analyseFlowsFromEntryLevelSystemCalls(UsageModel usageModel, FixpointIteration analyser,
			SystemRepresentation systemrepresentation) {
		for (UsageScenario scenario : usageModel.getUsageScenario_UsageModel()) {
			for (AbstractUserAction action : PCMSubtypeResolver.filterEntryLevelSystemCalls(
					scenario.getScenarioBehaviour_UsageScenario().getActions_ScenarioBehaviour())) {

				analyser.runAnalysis(systemrepresentation
						.getOperationIdentifyingOfComponentForExternalCall((EntryLevelSystemCall) action));

			}
		}
	}
}
