package PCC_Reduced.contracts.interfaces;

public interface AccountManagerInterface {
			
	boolean createAccount(String mail, String password); 
	boolean authenticateAccount(String mail, String password); 
	boolean changeAccount(String password, String mailNew, String passwordNew); 
	boolean deleteAccount(); 

}