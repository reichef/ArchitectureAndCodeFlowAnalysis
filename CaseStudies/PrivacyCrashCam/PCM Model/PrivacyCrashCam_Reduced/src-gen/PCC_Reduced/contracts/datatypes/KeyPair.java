package PCC_Reduced.contracts.datatypes;

import java.util.ArrayList;
import java.lang.Iterable;
		
public class KeyPair {
	
	private Iterable<Byte> privateKey;
	private Iterable<Byte> publicKey;
	
	public KeyPair() {
		// TODO: Implement and verify auto-generated constructor.
		this.privateKey = new ArrayList<Byte>();
		this.publicKey = new ArrayList<Byte>();
	}
	
	public KeyPair(Iterable<Byte> privateKey, Iterable<Byte> publicKey) {
		// TODO: Implement and verify auto-generated constructor.
		this.privateKey = privateKey;
		this.publicKey = publicKey;
	}
	
	public Iterable<Byte> getPrivateKey() {
	    return privateKey;
	}
	
	public void setPrivateKey(Iterable<Byte> privateKey) {
	    this.privateKey = privateKey;
	}
	
	public Iterable<Byte> getPublicKey() {
	    return publicKey;
	}
	
	public void setPublicKey(Iterable<Byte> publicKey) {
	    this.publicKey = publicKey;
	}
	
}