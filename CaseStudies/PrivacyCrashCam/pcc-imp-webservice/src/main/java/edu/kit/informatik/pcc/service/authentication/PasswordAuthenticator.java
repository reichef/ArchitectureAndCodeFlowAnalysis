package edu.kit.informatik.pcc.service.authentication;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Logger;

public class PasswordAuthenticator implements PasswordAuthentication{

	@Override
	public boolean authenticatePassword(Connection connection, int userId, String inputHash) {
        String passwordHash = null;
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("select \"password_salt\",\"password\" from \"user\" where \"user\".\"id\"=" +
                    userId + ";");
            if (rs.next()) {
                passwordHash = rs.getString("password");
            }
            rs.close();
            stmt.close();
            connection.close();
        } 
        catch (NullPointerException | SQLException e) {
            Logger.getGlobal().severe("Validating password with database failed");
            return false;
        }
        
        if (passwordHash == null) {
        	return false;
        }
        
        return inputHash.equals(passwordHash);
	}
}
