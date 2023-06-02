package mantil3.c195.controller;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import mantil3.c195.DBAccess.DBAppointments;
import mantil3.c195.DBAccess.DBCountry;
import mantil3.c195.DBAccess.DBCustomer;
import mantil3.c195.DBAccess.DBFirstLevelDivisions;
import mantil3.c195.helper.Utils;
import mantil3.c195.model.Country;
import mantil3.c195.model.Customer;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * Controller for customer-view
 * @see CustEditController
 */
public class CustomerController implements Initializable {
    public ComboBox cboFirstLevel;
    public Label message;
    @FXML private TableColumn divCol;
    @FXML private TableColumn<Customer, Integer> idCol;
    @FXML private TableColumn<Customer, String> nameCol;
    @FXML private TableColumn<Customer, String> addressCol;
    @FXML private TableColumn<Customer, String> postCol;
    @FXML private TableColumn<Customer, String> phoneNumCol;
    @FXML private TableView<Customer> tableView;
    @FXML private ComboBox cboCountry;
    private ObservableList<Country> countryObservableList = DBCountry.getAllCountries();
    private int cboCountryIndex;

    /**
     * Deletes the selected customer from the database</br>
     * Shows alert to confirm delete</br>
     * If confirmed all associated customer appointments are deleted first, then the customer</br>
     * Displays message whether the customer was deleted or not</br>
     * @see DBAppointments#deleteCustomersAppointments(Customer)
     * @see DBCustomer#deleteCustomerFromDatabase(Customer)
     */
    @FXML private void delete() {
        if (tableView.getSelectionModel().getSelectedItem() != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirm Delete");
            String custName = tableView.getSelectionModel().getSelectedItem().getName();
            alert.setContentText("Delete Customer " + custName + "?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK){
                DBAppointments.deleteCustomersAppointments(tableView.getSelectionModel().getSelectedItem());
                DBCustomer.deleteCustomerFromDatabase(tableView.getSelectionModel().getSelectedItem());
                populateTable();
                message.setText("Customer " + custName + " successfully deleted.");
            }
            else{
                message.setText("Customer " + custName + " not deleted.");
            }
        }
    }

    /**
     * Navigates to cust-edit-view with the selected customer
     * @param actionEvent   the button click
     * @throws IOException  thrown when navigating screens
     * @see CustEditController#setSelected(Customer)
     * @see Utils#navigate(String, String, ActionEvent)
     */
    @FXML private void modify(ActionEvent actionEvent) throws IOException {
        if (tableView.getSelectionModel().getSelectedItem()!= null){
            CustEditController.setSelected(tableView.getSelectionModel().getSelectedItem());
            Utils.navigate("cust-edit-view.fxml", "Modify Customer", actionEvent);
        }
    }

    /**
     * Navigates to cust-edit-view with a default customer
     * @param actionEvent   the button click
     * @throws IOException  thrown when navigating screens
     * @see CustEditController#setSelected(Customer)
     * @see Utils#navigate(String, String, ActionEvent)
     */
    @FXML private void add(ActionEvent actionEvent) throws IOException {
        Customer newCust = new Customer(0, "", "", "", "", 29);
        CustEditController.setSelected(newCust);
        Utils.navigate("cust-edit-view.fxml", "Add Customer", actionEvent);
    }

    /**
     * Navigates to home-view
     * @param actionEvent   the button click
     * @throws IOException  thrown when navigating screens
     * @see Utils#navigate(String, String, ActionEvent)
     */
    @FXML private void backToHome(ActionEvent actionEvent) throws IOException {
        Utils.navigate("home-view.fxml", "Home", actionEvent);
    }

    /**
     * Populates the tableview with all Customer data
     * @see DBCustomer#getAllCustomers()
     */
    private void populateTable() {
        tableView.setItems(DBCustomer.getAllCustomers());
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        addressCol.setCellValueFactory(new PropertyValueFactory<>("address"));
        postCol.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
        phoneNumCol.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        divCol.setCellValueFactory(new PropertyValueFactory<>("divisionID"));
    }

    /**
     * Filters the tableview by country selected in the country combobox
     * @param countryID the ID of the country selected in the combobox
     * @see DBCustomer#getCustomersByCountry(int)
     */
    private void filterTableByCountry(int countryID) {
        tableView.getItems().clear();
        tableView.setItems(DBCustomer.getCustomersByCountry(countryID));
    }

    /**
     * Populates the division combobox based on the selected country
     * @see Utils#populateDivisionCombo(ComboBox, ComboBox)
     */
    @FXML private void populateFirstLevelDivisionCombo() {
        cboCountryIndex = Utils.populateDivisionCombo(cboFirstLevel, cboCountry);
        filterTableByCountry(cboCountryIndex);
    }

    /**
     * Filters the tableview by division selected in the division combobox
     * @see DBFirstLevelDivisions#getDivisionID(String)
     * @see DBCustomer#getCustomersByDivision(int)
     */
    @FXML private void filterTableByDivision() {
        String cboDivisionName = cboFirstLevel.getSelectionModel().getSelectedItem().toString();
        int divID = DBFirstLevelDivisions.getDivisionID(cboDivisionName);
        tableView.getItems().clear();
        tableView.setItems(DBCustomer.getCustomersByDivision(divID));
    }

    /**
     * Shows an alert with the counts of a customers appointments by month and type
     * @see DBAppointments#getAppointmentTypesForCustomer(int)
     * @see DBAppointments#getAppointmentTypeCountForCustomer(int)
     * @see DBAppointments#getCustomerAppointmentMonths(int)
     * @see DBAppointments#getCustomersAppointmentCountByMonth(int)
     */
    @FXML private void customerAppointmentsReport() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Customer Appointment");
        alert.setHeaderText("Appointment Counts");
        String contextText = "";
        if (tableView.getSelectionModel().getSelectedItem() != null){
            int custID = tableView.getSelectionModel().getSelectedItem().getId();
            ObservableList<String> typeList = DBAppointments.getAppointmentTypesForCustomer(custID);
            ObservableList<Integer> typeCountList = DBAppointments.getAppointmentTypeCountForCustomer(custID);
            ObservableList<Integer> monthList = DBAppointments.getCustomerAppointmentMonths(custID);
            ObservableList<Integer> monthCountList = DBAppointments.getCustomersAppointmentCountByMonth(custID);
            for (int i = 0; i < typeList.size(); i++) {
                contextText = contextText + typeList.get(i) + ": " + typeCountList.get(i) + "\n";
            }
            for (int i = 0; i < monthList.size(); i++ ) {
                contextText = contextText + "Month " + monthList.get(i) + ": " + monthCountList.get(i) + "\n";
            }
            alert.setContentText(contextText);
            alert.showAndWait();
        }
    }

    /**
     * Initializes CustomerController
     * @param url               The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resourceBundle    The resources used to localize the root object, or null if the root object was not localized.
     * @see Utils#populateCountryCombo(ComboBox)
     */
    @Override public void initialize(URL url, ResourceBundle resourceBundle) {
        populateTable();
        Utils.populateCountryCombo(cboCountry);
    }
}
