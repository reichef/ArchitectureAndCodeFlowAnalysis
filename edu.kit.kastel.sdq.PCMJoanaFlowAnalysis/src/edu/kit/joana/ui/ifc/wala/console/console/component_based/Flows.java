package edu.kit.joana.ui.ifc.wala.console.console.component_based;

import java.util.Map;
import java.util.Set;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;

/**
 * Found flows
 */
public class Flows {
  private final Map<Method, Set<Method>> flows;

  public Flows(Map<Method, Set<Method>> flows) {
    this.flows = flows;
  }

  public Map<Method, Set<Method>> flows(){
    return Collections.unmodifiableMap(flows);
  }

  public Set<Method> flows(Method method){
    return flows.getOrDefault(method, Collections.emptySet());
  }

  public boolean isEmpty(){
    return flows.isEmpty();
  }

  /**
   * @return this - other
   */
  public Flows remove(Flows other){
    Map<Method, Set<Method>> newFlows = new HashMap<>();
    for (Map.Entry<Method, Set<Method>> methodSetEntry : flows.entrySet()) {
      if (other.flows.containsKey(methodSetEntry.getKey())){
        Set<Method> methods = new HashSet<>();
        for (Method method : methodSetEntry.getValue()) {
          if (!other.flows.get(methodSetEntry.getKey()).contains(method)){
            methods.add(method);
          }
        }
        newFlows.put(methodSetEntry.getKey(), methods);
      } else {
        newFlows.put(methodSetEntry.getKey(), methodSetEntry.getValue());
      }
    }
    return new Flows(newFlows);
  }
}
