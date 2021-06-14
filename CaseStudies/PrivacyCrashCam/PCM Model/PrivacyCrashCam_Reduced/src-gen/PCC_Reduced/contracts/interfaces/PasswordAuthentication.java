package PCC_Reduced.contracts.interfaces;

import PCC_Reduced.contracts.datatypes.Connection;
		
public interface PasswordAuthentication {
			
	boolean authenticatePassword(Connection connection, int userId, String inputHash); 

}