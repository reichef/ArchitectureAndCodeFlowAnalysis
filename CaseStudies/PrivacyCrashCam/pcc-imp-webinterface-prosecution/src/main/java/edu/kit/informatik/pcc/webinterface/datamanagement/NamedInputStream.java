package edu.kit.informatik.pcc.webinterface.datamanagement;

import java.io.InputStream;

import com.vaadin.server.StreamResource;


public class NamedInputStream {
	
	private final InputStream inputStream;
	private final String name;
	
	public NamedInputStream(InputStream inputStream, String name) {
		this.inputStream = inputStream;
		this.name = name;
	}
	public InputStream getInputStream() {
		return inputStream;
	}
	public String getName() {
		return name;
	}
	
	public StreamResource toStreamResource(){
	      return new StreamResource((StreamResource.StreamSource) () -> inputStream, name);
	}

	
}
