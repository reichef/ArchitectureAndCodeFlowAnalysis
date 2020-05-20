package edu.kit.joana.ui.ifc.wala.console.console.component_based;

import java.util.List;

/**
 * Basic flow analyzer
 */
public class BasicFlowAnalyzer extends FlowAnalyzer {
  public BasicFlowAnalyzer(Association association, Flows knownFlows) {
		super(association, knownFlows);
		// TODO Auto-generated constructor stub
	}

@Override public Flows analyze(List<Method> sources, List<Method> sinks) {
    return null;
  }
}
