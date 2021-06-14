package PCC_Reduced.contracts.interfaces;

import PCC_Reduced.contracts.datatypes.StreamResource;
import PCC_Reduced.contracts.datatypes.Video;
import java.lang.Iterable;
		
public interface VideoManagerInterface {
			
	StreamResource downloadVideo(int videoID, String videoName); 
	Iterable<Video> getVideos(); 

}