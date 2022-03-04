package edu.kit.kastel.sdq.pcmjoanaflowanalysis;

import org.eclipse.swt.widgets.Shell;

public class PCMJOANAFlowAnalysisViewFactory {

	public PCMJOANAFlowAnalysisView getFlowAnalysisView(String path) {
		return new PCMJOANAFlowAnalysisViewImpl(path);
	}
	
	public PCMJOANAFlowAnalysisStartDialog getStartDialog(Shell parentShell) {
		return new PCMJOANAFlowAnalysisStartDialog(parentShell);
	}
}
