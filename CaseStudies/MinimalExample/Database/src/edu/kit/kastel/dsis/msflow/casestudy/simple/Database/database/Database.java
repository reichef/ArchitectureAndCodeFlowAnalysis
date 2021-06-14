package edu.kit.kastel.dsis.msflow.casestudy.simple.Database.database;

public class Database implements DbCallCosts, DbCallCredentials{

	@Override
	public String callCredentials(int id) {
		return "MyCredentials";
	}

	@Override
	public int dbCallCosts(String token) {
		return 1;
	}

	
	
}
