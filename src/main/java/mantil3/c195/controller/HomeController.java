package mantil3.c195.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import mantil3.c195.DBAccess.DBAppointments;
import mantil3.c195.interfaces.Upcoming;
import mantil3.c195.helper.Utils;
import mantil3.c195.model.Appointments;
import mantil3.c195.model.User;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

/**
 * Controller for home-view
 * @see LoginController
 * @see CustomerController
 * @see ApptController
 */
public class HomeController implements Initializable {

    public static User user;
    public static boolean loggedInFlag;
    Upcoming context = appointment -> {
        if (appointment.getId() > 0) {
            return "Appointment: " + appointment.getId()
                    + "\nDate: " + appointment.getStart().format(DateTimeFormatter.ofPattern("MM-dd-yyyy"))
                    + "\nTime: " + appointment.getStart().format(DateTimeFormatter.ofPattern("hh:mm"));
        }
        else {
            return "No upcoming appointments";
        }
    }; //Lambda

    /**
     * Logs the current user out and navigates to login-view
     * @param actionEvent   the button click
     * @throws IOException  thrown when navigating screens
     * @see Utils#navigate(String, String, ActionEvent)
     */
    @FXML private void logout(ActionEvent actionEvent) throws IOException {
        loggedOut();
        Utils.navigate("login-view.fxml", "Login!", actionEvent);
    }

    /**
     * Navigates to customer-view
     * @param actionEvent   the button click
     * @throws IOException  thrown when navigating screens
     * @see Utils#navigate(String, String, ActionEvent)
     */
    @FXML private void toCustomers(ActionEvent actionEvent) throws IOException {
        Utils.navigate("customer-view.fxml", "Customers", actionEvent);
    }

    /**
     * Navigates to appt-view
     * @param actionEvent   the button click
     * @throws IOException  thrown when nevigating screens
     * @see Utils#navigate(String, String, ActionEvent)
     */
    @FXML private void toSchedule(ActionEvent actionEvent) throws IOException {
        Utils.navigate("appt-view.fxml", "Appointments", actionEvent);
    }

    /**
     * Sets the user object to the passed user
     * @param u the user
     */
    public static void setUser(User u) {
        user = u;
    }

    /**
     * Switches the loggedInFlag boolean to true
     */
    public static void loggedIn() {
        loggedInFlag = true;
    }

    /**
     * Switches the loggedInFlag boolean to false
     */
    public static void loggedOut() {
        loggedInFlag = false;
    }

    /**
     * Shows an alert for the users upcoming appointments</br>
     * A lambda function was used here to increase the readability of the function
     * @param u the user
     * @see DBAppointments#getUserAppointmentsIn15Min(User)
     */
    private void appointmentAlert(User u) {
        Appointments appointment = DBAppointments.getUserAppointmentsIn15Min(u);
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Upcoming Appointment");
        alert.setContentText(context.upcoming(appointment));
        alert.showAndWait();
    }

    /**
     * Exits the application
     */
    @FXML private void exit() {
        System.exit(0);
    }

    /**
     * Initializes HomeController by setting the logged in flag to true on login and showing upcoming appointments on login
     * @param url               The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resourceBundle    The resources used to localize the root object, or null if the root object was not localized.
     */
    @Override public void initialize(URL url, ResourceBundle resourceBundle) {
        if (!loggedInFlag){
            appointmentAlert(user);
        }
        loggedIn();
    }
}
