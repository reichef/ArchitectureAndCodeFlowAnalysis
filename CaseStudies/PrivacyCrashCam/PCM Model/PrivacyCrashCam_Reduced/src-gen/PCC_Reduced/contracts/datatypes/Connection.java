package PCC_Reduced.contracts.datatypes;

public class Connection {
	
	private String URL;
	
	public Connection() {
		// TODO: Implement and verify auto-generated constructor.
		this.URL = "";
	}
	
	public Connection(String URL) {
		// TODO: Implement and verify auto-generated constructor.
		this.URL = URL;
	}
	
	public String getURL() {
	    return URL;
	}
	
	public void setURL(String URL) {
	    this.URL = URL;
	}
	
}