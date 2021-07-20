package edu.kit.kastel.sdq.pcmjoanaflowanalysis;

import java.nio.file.Path;

public class Config {

	private String joanaCLIFolderPath;
	private String joanaOutputFolderPath;
	private boolean usePersistedFlows;
	private String joanaServerIP;

	public Config(String joanaCLIFolderPath, String joanaOutputFolderPath, boolean usePersistedFlows, String joanaServerIP) {
		this.joanaCLIFolderPath = joanaCLIFolderPath;
		this.joanaOutputFolderPath = joanaOutputFolderPath;
		this.usePersistedFlows = usePersistedFlows;
		this.joanaServerIP = joanaServerIP;
	}

	public Config(Path joanaCLIFolderString, Path joanaOutputFolderString, boolean usePersistedFlows, String joanaServerIP) {
		this.setJoanaCLIFolderPath(joanaCLIFolderString);
		this.setJoanaOutputFolderPath(joanaOutputFolderString);
		this.setUsingPersistedFlows(usePersistedFlows);
		this.joanaServerIP = joanaServerIP;
	}

	public String getJoanaCLIFolderPath() {
		return joanaCLIFolderPath;
	}

	public void setJoanaCLIFolderPath(String joanaCLIFolderString) {
		this.joanaCLIFolderPath = joanaCLIFolderString;
	}

	public void setJoanaCLIFolderPath(Path joanaCLIFolderString) {
		this.joanaCLIFolderPath = joanaCLIFolderString.toAbsolutePath().toString();
	}

	public String getJoanaOutputFolderPath() {
		return joanaOutputFolderPath;
	}

	public void setJoanaOutputFolderPath(String joanaOutputFolderPath) {
		this.joanaOutputFolderPath = joanaOutputFolderPath;
	}

	public void setJoanaOutputFolderPath(Path joanaOutputFolderPath) {
		this.joanaOutputFolderPath = joanaOutputFolderPath.toAbsolutePath().toString();
	}

	public boolean usingPersistedFlows() {
		return usePersistedFlows;
	}

	public void setUsingPersistedFlows(boolean isUsingPersistedFlows) {
		this.usePersistedFlows = isUsingPersistedFlows;
	}

	public String getJoanaServerIP() {
		return joanaServerIP;
	}

	public void setJoanaServerIP(String joanaServerIP) {
		this.joanaServerIP = joanaServerIP;
	}
}
