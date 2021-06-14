package PCC_Reduced.components.ServerCommunicator;

import PCC_Reduced.contracts.interfaces.JudgeAccessWeb;
import PCC_Reduced.contracts.interfaces.PersecutionInformationProvision;
import PCC_Reduced.contracts.datatypes.File;
import java.lang.Iterable;
import PCC_Reduced.contracts.interfaces.JudgeAccess;
import PCC_Reduced.contracts.interfaces.PersecutionInformationProvisionWeb;
		
public class ServerCommunicator implements PersecutionInformationProvision, JudgeAccess {
	
	private PersecutionInformationProvisionWeb persecutionInformationProvisionWeb;
	private JudgeAccessWeb judgeAccessWeb;
	
	public ServerCommunicator(PersecutionInformationProvisionWeb persecutionInformationProvisionWeb, JudgeAccessWeb judgeAccessWeb) {
		// TODO: implement and verify auto-generated constructor.
	    this.persecutionInformationProvisionWeb = persecutionInformationProvisionWeb;
	    this.judgeAccessWeb = judgeAccessWeb;
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
	
}