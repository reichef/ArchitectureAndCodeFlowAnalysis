package edu.kit.kastel.dsis.msflow.casestudy.simple.Server.server;

public class Server implements UserCall, DbCallCredentials, ClientCallBack, ClientCall {

	DbCallCredentials credentials;
	ClientCall clientcall;


	@Override
	public String callCredentials(int id) {
		return credentials.callCredentials(id);
	}


	@Override
	public String requestUserSpecs(int id) {
		return callCredentials(id);
	}

	@Override
	public int calculateCost(int id) {
		return clientcall.calculateCost(id);
	}


	@Override
	public int requestMyCost(int id) {
		return calculateCost(id);
	}


}
