package PCC_Reduced.components.VideoService;

import PCC_Reduced.contracts.interfaces.AccessControl;
import PCC_Reduced.contracts.datatypes.File;
import java.lang.Iterable;
import PCC_Reduced.contracts.interfaces.IVideoService;
import PCC_Reduced.contracts.interfaces.VideoPersistor;
		
public class VideoService implements IVideoService {
	
	private AccessControl accessControl;
	private VideoPersistor videoPersistor;
	private IVideoService iVideoService;
	
	public VideoService(AccessControl accessControl, VideoPersistor videoPersistor, IVideoService iVideoService) {
		// TODO: implement and verify auto-generated constructor.
	    this.accessControl = accessControl;
	    this.videoPersistor = videoPersistor;
	    this.iVideoService = iVideoService;
	}
	
	public Iterable<Integer> getVideoIds(int userId){
		// TODO: implement and verify auto-generated method stub
		throw new UnsupportedOperationException("TODO: auto-generated method stub");
	}
	
	public void postVideo(File encryptedVideo, File encryptedMetadata, Iterable<Byte> encryptedKeyData, int userId){
		// TODO: implement and verify auto-generated method stub
		throw new UnsupportedOperationException("TODO: auto-generated method stub");
	}
	
	public File getVideo(int videoId, int userId){
		// TODO: implement and verify auto-generated method stub
		throw new UnsupportedOperationException("TODO: auto-generated method stub");
	}
	
	public File getMetadata(int videoId, int userId){
		// TODO: implement and verify auto-generated method stub
		throw new UnsupportedOperationException("TODO: auto-generated method stub");
	}
	
	public void deleteVideo(int videoId, int userId){
		// TODO: implement and verify auto-generated method stub
		throw new UnsupportedOperationException("TODO: auto-generated method stub");
	}
	
}