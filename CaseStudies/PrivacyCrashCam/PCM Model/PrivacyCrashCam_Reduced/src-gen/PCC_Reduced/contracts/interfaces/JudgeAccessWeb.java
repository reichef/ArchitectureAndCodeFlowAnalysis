package PCC_Reduced.contracts.interfaces;

import PCC_Reduced.contracts.datatypes.Response;
		
public interface JudgeAccessWeb {
			
	Response getConfidentialVideo(int videoId, int targetUserId, String authenticationToken); 

}