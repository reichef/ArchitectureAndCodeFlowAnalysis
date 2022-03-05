package edu.kit.kastel.sdq.pcmjoanaflowanalysis;

import org.eclipse.swt.widgets.Shell;

public class PCMJOANAFlowAnalysisViewFactory {

	public PCMJOANAFlowAnalysisView getFlowAnalysisView(String path) {
		return new PCMJOANAFlowAnalysisViewImpl(path);
	}
	
	public PCMJOANAFlowAnalysisStartDialog getStartDialog(Shell parentShell) {
		return new PCMJOANAFlowAnalysisStartDialog(parentShell);
	}
	
	public PCMJOANAFlowAnalysisStartDialog getStartDialog(Shell parentShell, String joanaOutputPath, 
			String joanaServerIP, boolean intra_componend_end_point) {
		return new PCMJOANAFlowAnalysisStartDialog(parentShell, joanaOutputPath, joanaServerIP, 
				intra_componend_end_point);
	}
}
