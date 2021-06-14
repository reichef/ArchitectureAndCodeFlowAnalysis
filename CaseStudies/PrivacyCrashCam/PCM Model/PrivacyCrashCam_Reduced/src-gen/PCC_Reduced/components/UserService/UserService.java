package PCC_Reduced.components.UserService;

import PCC_Reduced.contracts.interfaces.CryptographicHashing;
import PCC_Reduced.contracts.interfaces.IUserIdProvider;
import PCC_Reduced.contracts.interfaces.IUserManagement;
import PCC_Reduced.contracts.interfaces.IUserSessionDB;
import PCC_Reduced.contracts.interfaces.PasswordAuthentication;
import PCC_Reduced.contracts.interfaces.IUserDB;
		
public class UserService implements IUserIdProvider, IUserManagement {
	
	private IUserDB iUserDB;
	private IUserSessionDB iUserSessionDB;
	private CryptographicHashing cryptographicHashing;
	private PasswordAuthentication passwordAuthentication;
	
	public UserService(IUserDB iUserDB, IUserSessionDB iUserSessionDB, CryptographicHashing cryptographicHashing, PasswordAuthentication passwordAuthentication) {
		// TODO: implement and verify auto-generated constructor.
	    this.iUserDB = iUserDB;
	    this.iUserSessionDB = iUserSessionDB;
	    this.cryptographicHashing = cryptographicHashing;
	    this.passwordAuthentication = passwordAuthentication;
	}
	
	public int getUserId(String authenticationToken){
		// TODO: implement and verify auto-generated method stub
		throw new UnsupportedOperationException("TODO: auto-generated method stub");
	}
	
	public boolean createAccount(String email, String password){
		// TODO: implement and verify auto-generated method stub
		throw new UnsupportedOperationException("TODO: auto-generated method stub");
	}
	
	public void deleteAccount(String authenticationToken){
		// TODO: implement and verify auto-generated method stub
		throw new UnsupportedOperationException("TODO: auto-generated method stub");
	}
	
	public String login(String email, String password){
		// TODO: implement and verify auto-generated method stub
		throw new UnsupportedOperationException("TODO: auto-generated method stub");
	}
	
}