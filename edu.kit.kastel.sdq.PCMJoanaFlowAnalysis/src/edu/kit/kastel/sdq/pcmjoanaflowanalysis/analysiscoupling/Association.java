package edu.kit.kastel.sdq.pcmjoanaflowanalysis.analysiscoupling;

import edu.kit.joana.component.connector.Method;

/**
 * Associate ids with methods (method with name "" can represent a class)
 */
public class Association extends Pair<String, Method>  {

	public Association(String id, Method method) {
		super(id, method);
	}
	
	public String getId() {
		return getFirst();
	}
	
	public Method getMethod() {
		return getSecond();
	}
	
	public boolean equals(String id) {
		return this.getId().equals(id);
	}
	
	public boolean equals(Method method) {
		return this.getMethod().className.equals(method.className) && this.getMethod().methodName.equals(method.methodName);
	}
}
