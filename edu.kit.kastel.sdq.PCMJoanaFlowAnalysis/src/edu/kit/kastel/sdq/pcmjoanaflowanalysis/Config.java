package edu.kit.kastel.sdq.pcmjoanaflowanalysis;

import java.nio.file.Path;

public class Config {
	
	private String joanaCLIFolderPath;
	private String joanaOutputFolderPath;


	public Config(String joanaCLIFolderString, String joanaOutputFolderString) {
		this.setJoanaCLIFolderPath(joanaCLIFolderString);
		this.setJoanaOutputFolderPath(joanaOutputFolderString);
	}
	
	public Config(Path joanaCLIFolderString, Path joanaOutputFolderString) {
		this.setJoanaCLIFolderPath(joanaCLIFolderString);
		this.setJoanaOutputFolderPath(joanaOutputFolderString);
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
}
