package edu.kit.kastel.dsis.msflow.casestudy.simple.Client.client;

public class Client implements ClientCall, DbCallCosts, ClientCallBack{

	ClientCallBack clientCallback;
	DbCallCosts dbCallCosts;
	
	@Override
	public int calculateCost(int id) {
		String mySpecs = requestUserSpecs(id);
		String calculatedToke = calculateFromSpecs(mySpecs);
		int costs = dbCallCosts(calculatedToke);
		
		return costs;
	}

	@Override
	public String requestUserSpecs(int id) {
		return clientCallback.requestUserSpecs(id);
	}

	@Override
	public int dbCallCosts(String token) {
		return dbCallCosts.dbCallCosts(token);
	}
	
	private String calculateFromSpecs(String specs) {
		//calculate methods
		return specs;
	}
	
}
