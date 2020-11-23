package edu.kit.informatik.pcc.webinterface.serverconnection;

import java.io.InputStream;

public interface JudgeAccess {
	InputStream getConfidentialVideo(int videoId, int targetUserId, String authenticationToken);
}
