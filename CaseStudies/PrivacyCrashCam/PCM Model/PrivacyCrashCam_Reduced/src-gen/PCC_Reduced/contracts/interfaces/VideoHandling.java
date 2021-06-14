package PCC_Reduced.contracts.interfaces;

import PCC_Reduced.contracts.datatypes.File;
import java.lang.Iterable;
		
public interface VideoHandling {
			
	Iterable<Integer> getVideoIds(String authenticationToken); 
	File downloadVideo(int videoId, String authenticationToken); 
	File getMetadata(int videoId, String authenticationToken); 
	void deleteVideo(int videoId, String authenticationToken); 

}