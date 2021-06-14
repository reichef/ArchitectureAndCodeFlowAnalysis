package PCC_Reduced.contracts.interfaces;

import PCC_Reduced.contracts.datatypes.Response;
		
public interface PersecutionInformationProvisionWeb {
			
	Response getMetaData(int videoId, int targetUserId, String authenticationToken); 
	String getAllVideoIds(String authenticationToken); 
	String getUserIdForVideoId(int videoId, String authenticationToken); 

}