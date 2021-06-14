package PCC_Reduced.contracts.interfaces;

import PCC_Reduced.contracts.datatypes.File;
import java.lang.Iterable;
		
public interface IVideoService {
			
	Iterable<Integer> getVideoIds(int userId); 
	void postVideo(File encryptedVideo, File encryptedMetadata, Iterable<Byte> encryptedKeyData, int userId); 
	File getVideo(int videoId, int userId); 
	File getMetadata(int videoId, int userId); 
	void deleteVideo(int videoId, int userId); 

}