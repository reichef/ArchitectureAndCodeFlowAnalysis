package PCC_Reduced.components.Webinterface;

import PCC_Reduced.contracts.datatypes.File;
import PCC_Reduced.contracts.interfaces.VideoHandling;
import PCC_Reduced.contracts.interfaces.IUserManagement;
import java.lang.Iterable;
		
public class Webinterface implements IUserManagement, VideoHandling {
	
	private IUserManagement iUserManagement;
	private VideoHandling videoHandling;
	
	public Webinterface(IUserManagement iUserManagement, VideoHandling videoHandling) {
		// TODO: implement and verify auto-generated constructor.
	    this.iUserManagement = iUserManagement;
	    this.videoHandling = videoHandling;
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
	
	public Iterable<Integer> getVideoIds(String authenticationToken){
		// TODO: implement and verify auto-generated method stub
		throw new UnsupportedOperationException("TODO: auto-generated method stub");
	}
	
	public File downloadVideo(int videoId, String authenticationToken){
		// TODO: implement and verify auto-generated method stub
		throw new UnsupportedOperationException("TODO: auto-generated method stub");
	}
	
	public File getMetadata(int videoId, String authenticationToken){
		// TODO: implement and verify auto-generated method stub
		throw new UnsupportedOperationException("TODO: auto-generated method stub");
	}
	
	public void deleteVideo(int videoId, String authenticationToken){
		// TODO: implement and verify auto-generated method stub
		throw new UnsupportedOperationException("TODO: auto-generated method stub");
	}
	
}