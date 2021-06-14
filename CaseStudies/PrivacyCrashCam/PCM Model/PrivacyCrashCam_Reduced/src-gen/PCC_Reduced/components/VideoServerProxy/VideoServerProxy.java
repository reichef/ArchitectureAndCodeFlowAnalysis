package PCC_Reduced.components.VideoServerProxy;

import PCC_Reduced.contracts.interfaces.JudgeAccessWeb;
import PCC_Reduced.contracts.interfaces.PersecutionInformationProvision;
import PCC_Reduced.contracts.interfaces.JudgeAccess;
import PCC_Reduced.contracts.interfaces.PersecutionInformationProvisionWeb;
import PCC_Reduced.contracts.datatypes.Response;
		
public class VideoServerProxy implements JudgeAccessWeb, PersecutionInformationProvisionWeb {
	
	private JudgeAccess judgeAccess;
	private PersecutionInformationProvision persecutionInformationProvision;
	
	public VideoServerProxy(JudgeAccess judgeAccess, PersecutionInformationProvision persecutionInformationProvision) {
		// TODO: implement and verify auto-generated constructor.
	    this.judgeAccess = judgeAccess;
	    this.persecutionInformationProvision = persecutionInformationProvision;
	}
	
	public Response getConfidentialVideo(int videoId, int targetUserId, String authenticationToken){
		// TODO: implement and verify auto-generated method stub
		throw new UnsupportedOperationException("TODO: auto-generated method stub");
	}
	
	public Response getMetaData(int videoId, int targetUserId, String authenticationToken){
		// TODO: implement and verify auto-generated method stub
		throw new UnsupportedOperationException("TODO: auto-generated method stub");
	}
	
	public String getAllVideoIds(String authenticationToken){
		// TODO: implement and verify auto-generated method stub
		throw new UnsupportedOperationException("TODO: auto-generated method stub");
	}
	
	public String getUserIdForVideoId(int videoId, String authenticationToken){
		// TODO: implement and verify auto-generated method stub
		throw new UnsupportedOperationException("TODO: auto-generated method stub");
	}
	
}