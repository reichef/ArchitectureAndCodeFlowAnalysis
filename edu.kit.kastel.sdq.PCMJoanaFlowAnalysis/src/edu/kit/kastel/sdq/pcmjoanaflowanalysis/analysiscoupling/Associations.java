package edu.kit.kastel.sdq.pcmjoanaflowanalysis.analysiscoupling;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import edu.kit.joana.component.connector.Method;
import edu.kit.joana.component.connector.ProgramPart;

public class Associations {
	private final Collection<Association> associations;

	  public Associations() {
	    associations = new HashSet<Association>();
	  }
	  public Method getMethod(String id){
	    for(Association association : associations) {
	    	if(association.equals(id)) {
	    		return association.getMethod();
	    	}
	    }
	    return null;
	  }
	  public String getId(ProgramPart joanaCLIMethod){
		  for(Association association : associations) {
		    	if(association.equals(joanaCLIMethod.getOwningMethod())) {
		    		return association.getId();
		    	}
		    }
		    return null;
	  }
	  public void associate(String id, Method method){
		  
		associations.add(new Association(id, method));
	  }
	  public boolean containsId(String id){
	    return getMethod(id) != null;
	  }
	  public boolean containsMethod(Method method){
	    return getId(method) != null;
	  }
}
