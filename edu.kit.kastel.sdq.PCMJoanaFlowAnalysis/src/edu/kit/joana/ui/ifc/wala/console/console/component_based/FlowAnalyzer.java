package edu.kit.joana.ui.ifc.wala.console.console.component_based;

//import edu.kit.joana.ui.ifc.wala.console.console.IFCConsole;
//import edu.kit.joana.ui.ifc.wala.console.io.PrintStreamConsoleWrapper;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;

public abstract class FlowAnalyzer {

  private final Association association;
  private final Flows knownFlows;
  //private final IFCConsole console;

  public FlowAnalyzer(Association association, Flows knownFlows){
    this.association = association;
    this.knownFlows = knownFlows;
    BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
//    this.console = new IFCConsole(in,
//        new PrintStreamConsoleWrapper(System.out, System.out, in, System.out, System.out));
//    this.console.setPointsTo("OBJECT_SENSITIVE"); // use a slower but more precise analysis by default
  }

  public void setClassPath(String classPath){
//    console.setClassPath(classPath);
  }

  /**
   * Find the connections between the given sources and sinks
   * @param sources
   * @param sinks
   * @return
   */
  public abstract Flows analyze(List<Method> sources, List<Method> sinks);

}
