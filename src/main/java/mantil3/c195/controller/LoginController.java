package mantil3.c195.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import mantil3.c195.DBAccess.DBUser;
import mantil3.c195.interfaces.Translation;
import mantil3.c195.model.User;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ResourceBundle;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import static mantil3.c195.helper.Utils.navigate;

/**
 * Controller for login-view
 * @see HomeController
 */
public class LoginController implements Initializable {

    public Label errorMessage;
    public Label country;
    public Label userLabel;
    public Label PWLabel;
    public Button loginBtn;
    public Button exitBtn;
    @FXML private TextField username;
    @FXML private TextField password;
    private ResourceBundle rb;

    Logger log = Logger.getLogger("login_activity.txt");
    Translation translation = id -> rb.getString(id);

    /**
     * Tries to log in with the entered username and password</br>
     * Shows error message if login unsuccessful</br>
     * Navigates to home-view
     * @param actionEvent   the button click
     * @throws IOException  thrown when navigating screens
     * @see DBUser#checkUsernamePassword(String, String)
     * @see DBUser#getUserFromUsername(String)
     * @see HomeController#setUser(User)
     * @see mantil3.c195.helper.Utils#navigate(String, String, ActionEvent)
     */
    @FXML private void login(ActionEvent actionEvent) throws IOException {

        try {
            FileHandler fh = new FileHandler("login_activity.txt", true);
            SimpleFormatter sf = new SimpleFormatter();
            fh.setFormatter(sf);
            log.addHandler(fh);
        } catch (IOException ioException) {
            Logger.getLogger(mantil3.c195.controller.LoginController.class.getName()).log(Level.SEVERE, null, ioException);
        } catch (SecurityException securityException) {
            Logger.getLogger(mantil3.c195.controller.LoginController.class.getName()).log(Level.SEVERE, null, securityException);
        }

        log.severe("Login attempt -> U: " + username.getText() + " P: " + password.getText());

        if(DBUser.checkUsernamePassword(username.getText(), password.getText())){
            //check for appointments in next 15 minutes
            //popup list saying if there are or not
            log.severe("Login Successful");
            log.severe(LocalDateTime.now().toString());
            HomeController.setUser(DBUser.getUserFromUsername(username.getText()));
            navigate("home-view.fxml", "Home", actionEvent);
        }
        else {
            log.severe("Login Unsuccessful");
            log.severe(LocalDateTime.now().toString());
            errorMessage.setText(rb.getString("E"));
        }
    }

    /**
     * Exits the application
     */
    @FXML private void exit(){
        System.exit(0);
    }


    /**
     * Initializes LoginController by setting GUI attributes to the correct language</br>
     * A lambda function was used here to better show that the GUI labels were being translated
     * @param url   The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param rb    The resources used to localize the root object, or null if the root object was not localized.
     */
    @Override public void initialize(URL url, ResourceBundle rb) {
        rb = ResourceBundle.getBundle("languageFiles/rb");
        this.rb = rb;
        String location = ZoneId.systemDefault().getId();
        country.setText(location);
        userLabel.setText(translation.translate("U"));
        PWLabel.setText(translation.translate("P"));
        loginBtn.setText(translation.translate("L"));
        exitBtn.setText(translation.translate("X"));
    }
}
