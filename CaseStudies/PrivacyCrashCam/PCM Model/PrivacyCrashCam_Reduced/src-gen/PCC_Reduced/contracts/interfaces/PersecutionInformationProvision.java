package PCC_Reduced.contracts.interfaces;

import PCC_Reduced.contracts.datatypes.File;
import java.lang.Iterable;
		
public interface PersecutionInformationProvision {
			
	File getMetaData(int videoId, int targetUserId, String authenticationToken); 
	Iterable<Integer> getAllVideoIds(); 
	int getUserIdForVideoId(int videoId, String authenticationToken); 

}