package edu.kit.informatik.pcc.webinterface.datamanagement;

import com.vaadin.server.VaadinSession;
import de.steinwedel.messagebox.MessageBox;
import edu.kit.informatik.pcc.webinterface.gui.MyUI;
import edu.kit.informatik.pcc.webinterface.mailservice.MailService;
import edu.kit.informatik.pcc.webinterface.serverconnection.ServerCommunicator;
import edu.kit.informatik.pcc.webinterface.serverconnection.ServerProxy;
import edu.kit.informatik.pcc.webinterface.serverconnection.ServerProxy.AuthenticateResult;

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
    private static ResourceBundle messages = ResourceBundle.getBundle("MessageBundle");

    //methods

    /**
     * This method creates an account by sending the order to the ServerProxy.
     * Then handles the answer.
     *
     * @param mail mail address
     * @param password password
     * @return true on success false else
     */
    public boolean createAccount(String mail, String password, VaadinSession session) {

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

        Account account = new Account(mail, password);
        Boolean accountCreated = communicator.createAccount(mail, password);
        
        if(accountCreated) {
        	session.setAttribute("account", account);
        }
        
        return accountCreated;
    }



    /**
     * This method sends the given parameters to ServerProxy to authenticate.
     *
     * @param mail mail address
     * @param password password
     * @return true on success false else
     */
    public boolean authenticateAccount(String mail, String password, VaadinSession session) {
        Account account = new Account(mail, password);
        String token = communicator.login(account.getMail(), account.getPassword());

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
            	session.setAttribute(MyUI.SESSION_KEY_ACCOUNT, account);
                session.setAttribute(MyUI.SESSION_TOKEN, token);
                return true;
        }

        return false;
    }

    /**
     * This method checks if the password is correct and then changes the account data
     * via the ServerProxy.
     *
     * @param mailNew new mail address
     * @param passwordNew new password
     * @param password password
     * @return true on success false else
     */
    public boolean changeAccount(String password, String mailNew,
                                        String passwordNew, VaadinSession session) {

        if (passwordNew.length() == 0 && mailNew.length() == 0) {
            MessageBox.createInfo()
                    .withMessage(errors.getString("noChanges"))
                    .open();
            return false;
        }

        Account account = (Account) session.getAttribute("account");
        Account newAccount = new Account(mailNew, passwordNew);

        if (passwordNew.length() == 0) {
            newAccount = new Account(mailNew, account.getPassword());
        } else if (mailNew.length() == 0) {
            newAccount = new Account(account.getMail(), passwordNew);
        }

        if (!MailService.isValidEmailAddress(newAccount.getMail())) {
            MessageBox.createInfo()
                    .withMessage(errors.getString("noLegitMail"))
                    .open();
            return false;
        }

        if (newAccount.getPassword().length() < PASSWORDMIN) {
            MessageBox.createInfo()
                    .withMessage(errors.getString("toShortPassword"))
                    .open();
            return false;
        }

        if (!password.equals(account.getPassword())) {
            MessageBox.createInfo()
                    .withMessage(errors.getString("wrongPassword"))
                    .open();
            return false;
        }


        return changeAccount(newAccount, session);

        
    }
    
    private boolean changeAccount(Account newAccount, VaadinSession session) {
    	String changeResult = communicator.login(newAccount.getMail(), newAccount.getPassword());
    	switch (changeResult) {
        case WRONGACCOUNT:
            MessageBox.createInfo()
                    .withMessage(errors.getString("wrongAccount"))
                    .open();
            break;
        case FAILURE:
        	MessageBox.createInfo()
            .withMessage(errors.getString("changeFail"))
            .open();
        	break;
        default:
            session.setAttribute(MyUI.SESSION_KEY_ACCOUNT, newAccount);
        	session.setAttribute(MyUI.SESSION_TOKEN, changeResult);
            MessageBox.createInfo()
                    .withMessage(errors.getString("accountChanged"))
                    .open();
            return true;
    }
    return false;
    }

    /**
     * This method sends a delete order for the account attribute to the ServerProxy.
     *
     * @return true on success false else
     */
    public boolean deleteAccount(VaadinSession session) {

    	String token = (String) session.getAttribute(MyUI.SESSION_TOKEN);
        Boolean accountDeleted = communicator.deleteAccount(token);
        if(accountDeleted) {
        	session.setAttribute("account", null);
        }
        return false;
    }

}
