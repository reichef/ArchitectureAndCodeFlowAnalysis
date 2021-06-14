package PCC_Reduced.contracts.datatypes;

public class File {
	
	private String path;
	
	public File() {
		// TODO: Implement and verify auto-generated constructor.
		this.path = "";
	}
	
	public File(String path) {
		// TODO: Implement and verify auto-generated constructor.
		this.path = path;
	}
	
	public String getPath() {
	    return path;
	}
	
	public void setPath(String path) {
	    this.path = path;
	}
	
}