package PCC_Reduced.contracts.datatypes;

import PCC_Reduced.contracts.datatypes.File;
		
public class VideoProcessingContext {
	
	private File encryptedVideo;
	private File encryptedMetaData;
	private File encryptedKey;
	private File metaData;
	private File anonymizedVideo;
	private int targetVideoId;
	private int userId;
	private File decryptedVideo;
	
	public VideoProcessingContext() {
		// TODO: Implement and verify auto-generated constructor.
		this.encryptedVideo = new File();
		this.encryptedMetaData = new File();
		this.encryptedKey = new File();
		this.metaData = new File();
		this.anonymizedVideo = new File();
		this.decryptedVideo = new File();
	}
	
	public VideoProcessingContext(File encryptedVideo, File encryptedMetaData, File encryptedKey, File metaData, File anonymizedVideo, int targetVideoId, int userId, File decryptedVideo) {
		// TODO: Implement and verify auto-generated constructor.
		this.encryptedVideo = encryptedVideo;
		this.encryptedMetaData = encryptedMetaData;
		this.encryptedKey = encryptedKey;
		this.metaData = metaData;
		this.anonymizedVideo = anonymizedVideo;
		this.targetVideoId = targetVideoId;
		this.userId = userId;
		this.decryptedVideo = decryptedVideo;
	}
	
	public File getEncryptedVideo() {
	    return encryptedVideo;
	}
	
	public void setEncryptedVideo(File encryptedVideo) {
	    this.encryptedVideo = encryptedVideo;
	}
	
	public File getEncryptedMetaData() {
	    return encryptedMetaData;
	}
	
	public void setEncryptedMetaData(File encryptedMetaData) {
	    this.encryptedMetaData = encryptedMetaData;
	}
	
	public File getEncryptedKey() {
	    return encryptedKey;
	}
	
	public void setEncryptedKey(File encryptedKey) {
	    this.encryptedKey = encryptedKey;
	}
	
	public File getMetaData() {
	    return metaData;
	}
	
	public void setMetaData(File metaData) {
	    this.metaData = metaData;
	}
	
	public File getAnonymizedVideo() {
	    return anonymizedVideo;
	}
	
	public void setAnonymizedVideo(File anonymizedVideo) {
	    this.anonymizedVideo = anonymizedVideo;
	}
	
	public int getTargetVideoId() {
	    return targetVideoId;
	}
	
	public void setTargetVideoId(int targetVideoId) {
	    this.targetVideoId = targetVideoId;
	}
	
	public int getUserId() {
	    return userId;
	}
	
	public void setUserId(int userId) {
	    this.userId = userId;
	}
	
	public File getDecryptedVideo() {
	    return decryptedVideo;
	}
	
	public void setDecryptedVideo(File decryptedVideo) {
	    this.decryptedVideo = decryptedVideo;
	}
	
}