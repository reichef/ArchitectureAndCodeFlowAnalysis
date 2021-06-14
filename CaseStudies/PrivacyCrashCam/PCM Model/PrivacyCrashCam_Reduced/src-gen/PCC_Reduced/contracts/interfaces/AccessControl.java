package PCC_Reduced.contracts.interfaces;

public interface AccessControl {
			
	boolean hasPermission(int userId, String permissionName); 
	boolean canAccessUnanonymized(int userId); 

}