package edu.kit.informatik.pcc.webinterface.datamanagement;

import java.util.LinkedList;

public interface VideoManagerInterface {
	public NamedInputStream downloadVideo(int videoID, String videoName, String sessionToken);
	public LinkedList<Video> getVideos(String emailAddress, String sessionToken);
}
