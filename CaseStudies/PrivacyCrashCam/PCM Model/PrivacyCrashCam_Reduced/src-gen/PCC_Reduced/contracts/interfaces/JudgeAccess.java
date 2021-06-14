package PCC_Reduced.contracts.interfaces;

import PCC_Reduced.contracts.datatypes.File;
		
public interface JudgeAccess {
			
	File getConfidentialVideo(int videoId, int userId, String authenticationToken); 

}