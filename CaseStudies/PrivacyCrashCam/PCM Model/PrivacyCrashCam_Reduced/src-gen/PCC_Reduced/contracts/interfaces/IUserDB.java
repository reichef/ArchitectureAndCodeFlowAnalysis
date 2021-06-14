package PCC_Reduced.contracts.interfaces;

public interface IUserDB {
			
	void createUser(String email, String password); 
	int getUserIdByMail(String email); 
	boolean validatePassword(int userId, String password); 
	void deleteAccount(int userId); 

}