package PCC_Reduced.components.AnonymizationService;

import PCC_Reduced.contracts.interfaces.PersecutionInformationProvision;
import PCC_Reduced.contracts.interfaces.IUserIdProvider;
import PCC_Reduced.contracts.datatypes.File;
import PCC_Reduced.contracts.interfaces.VideoReceiving;
import PCC_Reduced.contracts.interfaces.VideoHandling;
import PCC_Reduced.contracts.interfaces.IUserManagement;
import java.lang.Iterable;
import PCC_Reduced.contracts.interfaces.JudgeAccess;
import PCC_Reduced.contracts.interfaces.IVideoService;
		
public class AnonymizationService implements VideoReceiving, VideoHandling, PersecutionInformationProvision, JudgeAccess, IUserManagement {
	
	private IUserManagement iUserManagement;
	private IVideoService iVideoService;
	private IUserIdProvider iUserIdProvider;
	
	public AnonymizationService(IUserManagement iUserManagement, IVideoService iVideoService, IUserIdProvider iUserIdProvider) {
		// TODO: implement and verify auto-generated constructor.
	    this.iUserManagement = iUserManagement;
	    this.iVideoService = iVideoService;
	    this.iUserIdProvider = iUserIdProvider;
	}
	
	public void receiveVideo(File encryptedVideo, File encryptedMetadata, File encryptedKey, String authenticationToken){
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
	
	public File getMetaData(int videoId, int targetUserId, String authenticationToken){
		// TODO: implement and verify auto-generated method stub
		throw new UnsupportedOperationException("TODO: auto-generated method stub");
	}
	
	public Iterable<Integer> getAllVideoIds(){
		// TODO: implement and verify auto-generated method stub
		throw new UnsupportedOperationException("TODO: auto-generated method stub");
	}
	
	public int getUserIdForVideoId(int videoId, String authenticationToken){
		// TODO: implement and verify auto-generated method stub
		throw new UnsupportedOperationException("TODO: auto-generated method stub");
	}
	
	public File getConfidentialVideo(int videoId, int userId, String authenticationToken){
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