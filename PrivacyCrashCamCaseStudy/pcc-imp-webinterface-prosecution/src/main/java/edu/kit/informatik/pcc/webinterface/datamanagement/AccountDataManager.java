package edu.kit.informatik.pcc.webinterface.datamanagement;

import com.vaadin.server.VaadinSession;
import de.steinwedel.messagebox.MessageBox;
import edu.kit.informatik.pcc.webinterface.gui.MyUI;
import edu.kit.informatik.pcc.webinterface.mailservice.MailService;
import edu.kit.informatik.pcc.webinterface.serverconnection.ServerCommunicator;
import edu.kit.informatik.pcc.webinterface.serverconnection.ServerProxy;
import edu.kit.informatik.pcc.webinterface.serverconnection.ServerProxy.AuthenticateResult;

import java.util.Optional;
import java.util.ResourceBundle;

/**
 * Created by chris on 17.01.2017.
 * The AccountDataManager manages all operations which contain account dates.
 *
 * @author chris
 */
public class AccountDataManager{

    // Constants for returns strings
    public static final String SUCCESS = "SUCCESS";
    public static final String FAILURE = "FAILURE";
    public static final String WRONGACCOUNT = "WRONG ACCOUNT";
    public static final String ACCOUNTEXISTS = "ACCOUNT EXISTS";
    public static final String NOTEXISTING = "NOT EXISTING";
    public static final String WRONGPASSWORD = "WRONG PASSWORD";
    
    public static final int PASSWORDMIN = 6;

    
    public AccountDataManager(ServerCommunicator communicator) {
    	this.communicator = communicator;
    }
    //attributes
    private ServerCommunicator communicator;
    // message bundles
    private static ResourceBundle errors = ResourceBundle.getBundle("ErrorMessages");

    //methods

    /**
     * This method creates an account by sending the order to the ServerProxy.
     * Then handles the answer.
     *
     * @param mail mail address
     * @param password password
     * @return true on success false else
     */
    public boolean createAccount(String mail, String password) {

        if (!MailService.isValidEmailAddress(mail)) {
            MessageBox.createInfo()
                    .withMessage(errors.getString("noLegitMail"))
                    .open();
            return false;
        }

        if (password.length() < PASSWORDMIN) {
            MessageBox.createInfo()
                    .withMessage(errors.getString("toShortPassword"))
                    .open();
            return false;
        }

        Boolean accountCreated = communicator.createAccount(mail, password);
        
        if(accountCreated) {
        	return true;
        } 
        
        return false;
    }



    /**
     * This method sends the given parameters to ServerProxy to authenticate.
     *
     * @param mail mail address
     * @param password password
     * @return true on success false else
     */
    public Optional<String> authenticateAccount(String mail, String password) {
        String token = communicator.login(mail, password);

        switch (token) {
            case NOTEXISTING:
                MessageBox.createInfo()
                        .withMessage(errors.getString("noSuchAccount"))
                        .open();
                break;
            case WRONGPASSWORD:
                MessageBox.createInfo()
                        .withMessage(errors.getString("wrongPassword"))
                        .open();
                break;
            case FAILURE:
            	   System.out.println(token);
                   MessageBox.createInfo()
                           .withMessage(errors.getString("authenticateFail"))
                           .open();
                   break;
            default:
                return Optional.ofNullable(token);
        }

        return Optional.empty();
    }

    /**
     * This method sends a delete order for the account attribute to the ServerProxy.
     *
     * @return true on success false else
     */
    public boolean deleteAccount(String sessionToken) {
        return communicator.deleteAccount(sessionToken);
    }

}
