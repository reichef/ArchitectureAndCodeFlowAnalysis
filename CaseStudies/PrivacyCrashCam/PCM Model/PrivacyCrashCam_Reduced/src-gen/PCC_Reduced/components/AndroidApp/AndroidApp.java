package PCC_Reduced.components.AndroidApp;

import PCC_Reduced.contracts.interfaces.AndroidActions;
import PCC_Reduced.contracts.interfaces.VideoReceiving;
import PCC_Reduced.contracts.interfaces.IUserManagement;
		
public class AndroidApp implements IUserManagement, AndroidActions {
	
	private VideoReceiving videoReceiving;
	private IUserManagement iUserManagement;
	
	public AndroidApp(VideoReceiving videoReceiving, IUserManagement iUserManagement) {
		// TODO: implement and verify auto-generated constructor.
	    this.videoReceiving = videoReceiving;
	    this.iUserManagement = iUserManagement;
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
	
	public void recordVideo(){
		// TODO: implement and verify auto-generated method stub
		throw new UnsupportedOperationException("TODO: auto-generated method stub");
	}
	
	public void uploadVideo(int id){
		// TODO: implement and verify auto-generated method stub
		throw new UnsupportedOperationException("TODO: auto-generated method stub");
	}
	
}