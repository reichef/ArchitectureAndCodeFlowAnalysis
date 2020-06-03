package edu.kit.informatik.pcc.webinterface.datamanagement;

import java.util.LinkedList;
import java.util.Optional;

import com.vaadin.server.VaadinSession;

import edu.kit.informatik.pcc.webinterface.gui.MyUI;
import edu.kit.informatik.pcc.webinterface.serverconnection.ServerCommunicator;
//Facade for PCM Alignment Purposes
public class PersecutionWebinterface implements VideoManagerInterface, AccountManagerInterface {
	private AccountDataManager accountDataManager;
	private VideoDataManager videoDataManager;
	private VaadinSession currentSession;
	
	public PersecutionWebinterface(ServerCommunicator communicator) {
		this.videoDataManager = new VideoDataManager(communicator);
		this.accountDataManager = new AccountDataManager(communicator);
	}
	
	public void setCurrentSession(VaadinSession session) {
		this.currentSession = session;
	}
	
	public VaadinSession getCurrentSession() {
		return currentSession;
	}

	@Override
	public NamedInputStream downloadVideo(int videoID, String videoName) {
		return videoDataManager.downloadVideo(videoID, videoName, (String) currentSession.getAttribute(MyUI.SESSION_TOKEN));
	}

	@Override
	public LinkedList<Video> getVideos() {
		return videoDataManager.getVideos(((Account)currentSession.getAttribute(MyUI.SESSION_KEY_ACCOUNT)).getMail(),
				(String)currentSession.getAttribute(MyUI.SESSION_TOKEN));
	}

	@Override
	public boolean createAccount(String mail, String password) {
		if(accountDataManager.createAccount(mail, password)) {
			currentSession.setAttribute(MyUI.SESSION_TOKEN, new Account(mail,password));
			return true;
		}
		return false;
	}

	@Override
	public boolean authenticateAccount(String mail, String password) {
		Optional<String> token = accountDataManager.authenticateAccount(mail, password);
		
		if(token.isPresent()) {
			currentSession.setAttribute(MyUI.SESSION_KEY_ACCOUNT, new Account(mail, password));
            currentSession.setAttribute(MyUI.SESSION_TOKEN, token.get());
		}
		
		return token.isPresent();
	}

	@Override
	public boolean deleteAccount() {
		
		boolean accountDeleted = accountDataManager.deleteAccount((String)currentSession.getAttribute(MyUI.SESSION_TOKEN));
		
		if(accountDeleted) {
			currentSession.setAttribute(MyUI.SESSION_KEY_ACCOUNT, null);
		}
		
		return accountDeleted;
	}
}
