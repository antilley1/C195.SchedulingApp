package mantil3.c195.controller;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import mantil3.c195.DBAccess.DBAppointments;
import mantil3.c195.helper.Utils;
import mantil3.c195.model.Appointments;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * Controller for appointment-view
 * @see ApptEditController
 */
public class ApptController implements Initializable {

    @FXML private TableColumn contactCol;
    @FXML private TableColumn customerCol;
    @FXML private TableColumn userCol;
    @FXML private Label message;
    @FXML private TableView<Appointments> tableview;
    @FXML private TableColumn<Appointments, Integer> idCol;
    @FXML private TableColumn<Appointments, String> titleCol;
    @FXML private TableColumn<Appointments, String> descCol;
    @FXML private TableColumn<Appointments, String> locationCol;
    @FXML private TableColumn<Appointments, String> typeCol;
    @FXML private TableColumn<Appointments, Date> startCol;
    @FXML private TableColumn<Appointments, Date> endCol;
    @FXML private ToggleGroup dateFilter;
    @FXML private RadioButton allAppts;
    @FXML private RadioButton monthlyAppts;
    @FXML private RadioButton weeklyAppts;

    /**
     * Navigates to appt-edit-view with selected appointment from tableview
     * @param actionEvent   the button click
     * @throws IOException  thrown when navigating screens
     * @see Utils#navigate(String, String, ActionEvent)
     * @see ApptEditController#setSelected(Appointments)
     */
    @FXML private void modify(ActionEvent actionEvent) throws IOException {
        if (tableview.getSelectionModel().getSelectedItem() != null){
            ApptEditController.setSelected(tableview.getSelectionModel().getSelectedItem());
            Utils.navigate("appt-edit-view.fxml", "Modify Appointment", actionEvent);
        }
    }

    /**
     * Navigates to appt-edit-view with a null appointment
     * @param actionEvent   the button click
     * @throws IOException  thrown when navigating screens
     * @see Appointments
     * @see Utils#navigate(String, String, ActionEvent)
     * @see ApptEditController#setSelected(Appointments)
     */
    @FXML private void add(ActionEvent actionEvent) throws IOException {
        Appointments newAppt = new Appointments(0, "", "", "", "", LocalDateTime.now(), LocalDateTime.now(), 0, 0, 0);
        ApptEditController.setSelected(newAppt);
        Utils.navigate("appt-edit-view.fxml", "Add Appointment", actionEvent);
    }

    /**
     * Deletes the selected appointment from the tableview </br>
     * Shows alert for successful or unsuccessful deletion
     * @see DBAppointments#deleteAppointmentFromDatabase(Appointments)
     */
    @FXML private void delete() {
        if (tableview.getSelectionModel().getSelectedItem() != null){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirm Delete");
            String apptType = tableview.getSelectionModel().getSelectedItem().getType();
            int apptID = tableview.getSelectionModel().getSelectedItem().getId();
            alert.setContentText("Delete Appointment " + apptID + " of type: " + apptType + "?");
            Optional<ButtonType> result = alert.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK){
                DBAppointments.deleteAppointmentFromDatabase(tableview.getSelectionModel().getSelectedItem());
                filterAppts();
                message.setText("Appointment: " + apptType + " ID: " + apptID + " successfully deleted.");
            }
            else{
                message.setText("Appointment: " + apptType + " ID: " + apptID + " not deleted.");
            }
        }
    }

    /**
     * Navigates to home-view
     * @param actionEvent
     * @throws IOException
     * @see Utils#navigate(String, String, ActionEvent)
     */
    @FXML private void backToHome(ActionEvent actionEvent) throws IOException {
        Utils.navigate("home-view.fxml", "Home", actionEvent);
    }

    /**
     * Populates tableview with appointments
     * @param apptList  Observable list of appointments
     */
    @FXML private void populateTable (ObservableList<Appointments> apptList) {
        tableview.setItems(apptList);
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        descCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        locationCol.setCellValueFactory(new PropertyValueFactory<>("location"));
        typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        startCol.setCellValueFactory(new PropertyValueFactory<>("start"));
        endCol.setCellValueFactory(new PropertyValueFactory<>("end"));
        contactCol.setCellValueFactory(new PropertyValueFactory<>("contactID"));
        customerCol.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        userCol.setCellValueFactory(new PropertyValueFactory<>("userID"));
    }

    /**
     * Filters the tableview based on the toggle selected
     */
    @FXML private void filterAppts() {
        if (allAppts.isSelected()) {
            tableview.getItems().clear();
            populateTable(DBAppointments.getAllAppointments());
        } else if (monthlyAppts.isSelected()) {
            tableview.getItems().clear();
            populateTable(DBAppointments.getMonthlyAppointments());
        } else if (weeklyAppts.isSelected()) {
            tableview.getItems().clear();
            populateTable(DBAppointments.getWeeklyAppointments());
        }
    }

    /**
     * Navigates to contact-report-view
     * @param actionEvent   the button click
     * @throws IOException  thrown when navigating screens
     * @see Utils#navigate(String, String, ActionEvent)
     */
    @FXML private void toContactReport(ActionEvent actionEvent) throws IOException {
        Utils.navigate("contact-report-view.fxml", "Contact Reports", actionEvent);
    }

    /**
     * Shows an alert with a custom report</br>
     * The report shows all appointments with same location as </br>
     * the appointment selected in the tableview
     * @see DBAppointments#getAppointmentsByLocation(String)
     */
    @FXML private void customReport() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Custom Report");
        alert.setHeaderText("Appointments in Same Location");
        String content = "";
        if (tableview.getSelectionModel().getSelectedItem() != null){
            String location = tableview.getSelectionModel().getSelectedItem().getLocation();
            ObservableList<Appointments> apptList = DBAppointments.getAppointmentsByLocation(location);
            for (int i = 0; i < apptList.size(); i++) {
                content = content + "Appointment ID: " + apptList.get(i).getId() + "\n";
            }
            alert.setContentText(content);
            alert.showAndWait();
        }
    }

    /**
     * Initializes ApptController by populating the tableview
     * @param url               The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resourceBundle    The resources used to localize the root object, or null if the root object was not localized.
     * @see ApptController#populateTable(ObservableList) 
     */
    @Override public void initialize(URL url, ResourceBundle resourceBundle) {
        populateTable(DBAppointments.getAllAppointments());
    }
}
