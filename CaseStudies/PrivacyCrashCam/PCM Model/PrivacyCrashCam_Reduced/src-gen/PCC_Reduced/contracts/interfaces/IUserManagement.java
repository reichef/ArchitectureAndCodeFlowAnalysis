package PCC_Reduced.contracts.interfaces;

public interface IUserManagement {
			
	boolean createAccount(String email, String password); 
	void deleteAccount(String authenticationToken); 
	String login(String email, String password); 

}