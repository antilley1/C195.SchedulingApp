package mantil3.c195.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import mantil3.c195.DBAccess.*;
import mantil3.c195.helper.Utils;
import mantil3.c195.model.Appointments;

import java.io.IOException;
import java.net.URL;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoField;
import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;
import java.time.ZonedDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

/**
 * Controller for appt-edit-view
 * @see ApptController
 */
public class ApptEditController implements Initializable {
    public static Appointments selected;
    @FXML private ComboBox cboUser;
    @FXML private Label errorMessage;
    @FXML private ComboBox cboCustomer;
    @FXML private ComboBox cboContact;
    @FXML private TextField endHourField;
    @FXML private TextField endMinuteField;
    @FXML private TextField startHourField;
    @FXML private TextField startMinuteField;
    @FXML private DatePicker startDateField;
    @FXML private DatePicker endDateField;
    @FXML private TextField titleField;
    @FXML private TextField descField;
    @FXML private TextField locationField;
    @FXML private TextField typeField;
    @FXML private TextField IDField;

    /**
     * Sets the selected Appointment object
     * @param a the appointment to be selected
     */
    public static void setSelected(Appointments a) {
        selected = a;
    }

    /**
     * Navigates to appt-view
     * @param actionEvent   the button click
     * @throws IOException  thrown when navigating screens
     * @see Utils#navigate(String, String, ActionEvent)
     */
    public void cancel(ActionEvent actionEvent) throws IOException {
        Utils.navigate("appt-view.fxml", "Appointments", actionEvent);
    }

    /**
     * Saves the Appointment being edited to the database</br>
     * Checks that the appointment is in business hours and does not conflict with other appointments</br>
     * Then navigates back to appt-view
     * @param actionEvent   the button click
     * @throws IOException  thrown when navigating screens
     * @see DBAppointments#isInBusinessHours(LocalDateTime, LocalDateTime)
     * @see DBAppointments#noConflicts(LocalDateTime, LocalDateTime, int, int) 
     * @see Utils#navigate(String, String, ActionEvent)
     */
    public void save(ActionEvent actionEvent) throws IOException {

        // Create proposed times to check existing times against
        LocalDate startDate = startDateField.getValue();
        String startHour = startHourField.getText();
        String startMinute = startMinuteField.getText();
        //System.out.println("Proposed Start: " + startHour + ":" + startMinute);
        LocalDateTime proposedStart = LocalDateTime.of(startDate.getYear(), startDate.getMonthValue(), startDate.getDayOfMonth(), Integer.parseInt(startHour), Integer.parseInt(startMinute));

        LocalDate endDate = endDateField.getValue();
        String endHour = endHourField.getText();
        String endMinute = endMinuteField.getText();
        //System.out.println("Proposed End: " + endHour + ":" + endMinute);
        LocalDateTime proposedEnd = LocalDateTime.of(endDate.getYear(), endDate.getMonthValue(), endDate.getDayOfMonth(), Integer.parseInt(endHour), Integer.parseInt(endMinute));

        if(proposedStart.isEqual(proposedEnd) || proposedStart.isAfter(proposedEnd)){
            errorMessage.setText("Start time must be before end time");
        } //Start time is not before end time
        else {
            //checkBusinessHours
            if(DBAppointments.isInBusinessHours(proposedStart, proposedEnd)){
                //System.out.println("In business hours");

                //checkCustomerApptConflicts
                boolean noConflict = DBAppointments.noConflicts(proposedStart, proposedEnd, cboCustomer.getSelectionModel().getSelectedIndex()+1, selected.getId());
                //System.out.println("noConflict is " + noConflict);
                if (noConflict) {
//                    System.out.println("No Appointment Conflicts");

                    selected.setTitle(titleField.getText());
                    selected.setDescription(descField.getText());
                    selected.setLocation(locationField.getText());
                    selected.setType(typeField.getText());
                    selected.setStart(proposedStart);
                    selected.setEnd(proposedEnd);
                    selected.setCustomerID(cboCustomer.getSelectionModel().getSelectedIndex() + 1);
                    selected.setContactID(cboContact.getSelectionModel().getSelectedIndex() + 1);
                    selected.setUserID(cboUser.getSelectionModel().getSelectedIndex() + 1);

                    if (selected.getId() == 0) {
                        selected.setId(Integer.parseInt(IDField.getText()));
                        DBAppointments.addAppointmentToDatabase(selected);
                    }
                    else {
                        DBAppointments.modifyAppointmentInDatabase(selected);
                    }

                    Utils.navigate("appt-view.fxml", "Appointments", actionEvent);
                } //No conflicts, Save appointment
                else {
                    errorMessage.setText("Existing appointment conflict");
//                    System.out.println("Existing Appointment Conflict");
                } //Existing appt conflict
            } // In business hours
            else {
                errorMessage.setText("Not in business hours");
                //System.out.println("Not in business hours");
            } //Not in business hours
        } //start time is after end time


    }

    /**
     * Populates the customer combobox with all available customers</br>
     * Then pre-selects the customer of the selected appointment
     * @see Utils#populateCustomerCombo(ComboBox)
     * @see DBCustomer#getCustomerNameFromCustomerID(int)
     */
    private void populateCustomerCombo () {
        Utils.populateCustomerCombo(cboCustomer);
        cboCustomer.getSelectionModel().selectFirst();
        String customerName = DBCustomer.getCustomerNameFromCustomerID(selected.getCustomerID());
        while (selected.getCustomerID() != 0 && !cboCustomer.getSelectionModel().getSelectedItem().toString().contains(customerName)) {
            cboCustomer.getSelectionModel().selectNext();
        }
    }

    /**
     * Populates the contact combobox with all available contacts</br>
     * Then pre-selects the contact of the selected appointment
     * @see Utils#populateContactCombo(ComboBox)
     * @see DBContacts#getContactNameFromContactID(int)
     */
    private void populateContactCombo () {
        Utils.populateContactCombo(cboContact);
        cboContact.getSelectionModel().selectFirst();
        String contactName = DBContacts.getContactNameFromContactID(selected.getContactID());
        while (selected.getContactID() != 0 && !cboContact.getSelectionModel().getSelectedItem().toString().contains(contactName)) {
            cboContact.getSelectionModel().selectNext();
        }
    }

    /**
     * Populates the user combobox with all available users</br>
     * Then pre-selectes the user of the selected appointment
     * @see Utils#populateUserCombo(ComboBox)
     * @see DBUser
     */
    private void populateUserCombo () {
        Utils.populateUserCombo(cboUser);
        cboUser.getSelectionModel().selectFirst();
        int id = selected.getUserID();
        while (selected.getUserID() !=  0 && !cboUser.getSelectionModel().getSelectedItem().toString().contains(String.valueOf(id))) {
            cboUser.getSelectionModel().selectNext();
        }
    }

    /**
     * Initializes ApptEditCOntroller by populating all relevant appointment attribute fields
     * @param url               The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resourceBundle    The resources used to localize the root object, or null if the root object was not localized.
     * @see DBAppointments#getAllAppointments()
     */
    @Override public void initialize(URL url, ResourceBundle resourceBundle) {
        if (selected.getId() == 0) {
            IDField.setText(String.valueOf(DBAppointments.getAllAppointments().get(DBAppointments.getAllAppointments().size()-1).getId() + 1));
        }
        else {
            IDField.setText(String.valueOf(selected.getId()));
        }
        titleField.setText(selected.getTitle());
        descField.setText(selected.getDescription());
        locationField.setText(selected.getLocation());
        typeField.setText(selected.getType());

        startDateField.setValue(selected.getStart().toLocalDate());
        startHourField.setText(String.valueOf(selected.getStart().get(ChronoField.HOUR_OF_DAY)));
        startMinuteField.setText(String.valueOf(selected.getStart().get(ChronoField.MINUTE_OF_HOUR)));

        endDateField.setValue(selected.getEnd().toLocalDate());
        endHourField.setText(String.valueOf(selected.getEnd().get(ChronoField.HOUR_OF_DAY)));
        endMinuteField.setText(String.valueOf(selected.getEnd().get(ChronoField.MINUTE_OF_HOUR)));

        populateUserCombo();
        populateCustomerCombo();
        populateContactCombo();

    }
}
