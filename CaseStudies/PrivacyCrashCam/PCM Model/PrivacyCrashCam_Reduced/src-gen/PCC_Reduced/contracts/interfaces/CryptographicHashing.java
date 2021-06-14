package PCC_Reduced.contracts.interfaces;

import java.lang.Iterable;
		
public interface CryptographicHashing {
			
	String hash(String input, Iterable<Byte> salt); 

}