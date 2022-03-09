package edu.kit.kastel.sdq.pcmjoanaflowanalysis;

import java.io.IOException;
import java.util.Collection;

import org.eclipse.core.runtime.CoreException;

import edu.kit.kastel.sdq.PCMJoanaFlowAnalysisDiagramModel.JOANAFlowAnalysisDiagram;
import edu.kit.kastel.sdq.pcmjoanaflowanalysis.datastructure.hierarchical.AssemblyComponentContext;

public interface PCMJOANAFlowAnalysisView {

	
	public JOANAFlowAnalysisDiagram drawDiagramInstance(Collection<AssemblyComponentContext> diagram_assemblies, boolean intra_componend_end_point) throws CoreException, IOException;
}
