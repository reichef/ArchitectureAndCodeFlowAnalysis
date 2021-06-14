package PCC_Reduced.components.PersecutionWebinterface;

import PCC_Reduced.contracts.interfaces.PersecutionInformationProvision;
import PCC_Reduced.contracts.datatypes.StreamResource;
import PCC_Reduced.contracts.interfaces.VideoManagerInterface;
import PCC_Reduced.contracts.interfaces.IUserManagement;
import PCC_Reduced.contracts.datatypes.Video;
import java.lang.Iterable;
import PCC_Reduced.contracts.interfaces.JudgeAccess;
import PCC_Reduced.contracts.interfaces.AccountManagerInterface;
		
public class PersecutionWebinterface implements VideoManagerInterface, AccountManagerInterface {
	
	private PersecutionInformationProvision persecutionInformationProvision;
	private JudgeAccess judgeAccess;
	private IUserManagement iUserManagement;
	
	public PersecutionWebinterface(PersecutionInformationProvision persecutionInformationProvision, JudgeAccess judgeAccess, IUserManagement iUserManagement) {
		// TODO: implement and verify auto-generated constructor.
	    this.persecutionInformationProvision = persecutionInformationProvision;
	    this.judgeAccess = judgeAccess;
	    this.iUserManagement = iUserManagement;
	}
	
	public StreamResource downloadVideo(int videoID, String videoName){
		// TODO: implement and verify auto-generated method stub
		throw new UnsupportedOperationException("TODO: auto-generated method stub");
	}
	
	public Iterable<Video> getVideos(){
		// TODO: implement and verify auto-generated method stub
		throw new UnsupportedOperationException("TODO: auto-generated method stub");
	}
	
	public boolean createAccount(String mail, String password){
		// TODO: implement and verify auto-generated method stub
		throw new UnsupportedOperationException("TODO: auto-generated method stub");
	}
	
	public boolean authenticateAccount(String mail, String password){
		// TODO: implement and verify auto-generated method stub
		throw new UnsupportedOperationException("TODO: auto-generated method stub");
	}
	
	public boolean changeAccount(String password, String mailNew, String passwordNew){
		// TODO: implement and verify auto-generated method stub
		throw new UnsupportedOperationException("TODO: auto-generated method stub");
	}
	
	public boolean deleteAccount(){
		// TODO: implement and verify auto-generated method stub
		throw new UnsupportedOperationException("TODO: auto-generated method stub");
	}
	
}