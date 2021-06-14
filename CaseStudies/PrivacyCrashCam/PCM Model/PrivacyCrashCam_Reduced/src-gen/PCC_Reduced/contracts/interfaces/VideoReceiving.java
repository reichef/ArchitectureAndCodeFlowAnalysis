package PCC_Reduced.contracts.interfaces;

import PCC_Reduced.contracts.datatypes.File;
		
public interface VideoReceiving {
			
	void receiveVideo(File encryptedVideo, File encryptedMetadata, File encryptedKey, String authenticationToken); 

}