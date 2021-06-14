package PCC_Reduced.contracts.interfaces;

public interface IUserSessionDB {
			
	void storeAuthenticationToken(String authenticationToken, int userId); 
	void deleteTokensForUserId(int userId); 

}