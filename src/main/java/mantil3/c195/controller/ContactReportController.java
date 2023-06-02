package mantil3.c195.controller;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import mantil3.c195.DBAccess.DBAppointments;
import mantil3.c195.helper.Utils;
import mantil3.c195.model.Appointments;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controller for contact-report-view
 */
public class ContactReportController implements Initializable {
    public ComboBox contactCbo;
    public Label scheduleBody;

    /**
     * Shows the schedule of the selected contact
     * @see DBAppointments#getAllContactsAppointments(int)
     */
    @FXML private void fillSchedule() {
        String schedule = "";
        ObservableList<Appointments> apptList = DBAppointments.getAllContactsAppointments(contactCbo.getSelectionModel().getSelectedIndex()+1);

        for (int i = 0; i < apptList.size(); i++) {
            schedule = schedule +
                    "Appt ID: " + apptList.get(i).getId() + " | " +
                    "Title: " + apptList.get(i).getTitle() + " | " +
                    "Type: " + apptList.get(i).getType() + " | " +
                    "Description: " + apptList.get(i).getDescription() + " | " +
                    "Start: " + apptList.get(i).getStart().toString() + " | " +
                    "End: " + apptList.get(i).getEnd().toString() + " | " +
                    "Customer ID: " + apptList.get(i).getCustomerID() + "\n";
        }

        scheduleBody.setText(schedule);
    }

    /**
     * Populates the contact combobox with all contacts
     * @see Utils#populateContactCombo(ComboBox)
     */
    private void populateCombo() {
        Utils.populateContactCombo(contactCbo);
    }

    /**
     * Navigates back to appt-view
     * @param actionEvent   the button click
     * @throws IOException  thrown when navigating screens
     */
    @FXML private void back(ActionEvent actionEvent) throws IOException {
        Utils.navigate("appt-view.fxml", "Appointments", actionEvent);
    }

    /**
     * Initializes ContactReportController by populating the combobox
     * @param url               The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resourceBundle    The resources used to localize the root object, or null if the root object was not localized.
     */
    @Override public void initialize(URL url, ResourceBundle resourceBundle) {
        populateCombo();
    }
}
