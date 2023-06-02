package mantil3.c195.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import mantil3.c195.DBAccess.DBCustomer;
import mantil3.c195.DBAccess.DBFirstLevelDivisions;
import mantil3.c195.helper.Utils;
import mantil3.c195.model.Customer;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controller for cust-edit-view
 * @see CustomerController
 */
public class CustEditController implements Initializable {
    private static Customer selected;
    @FXML private Label errorMsg;
    @FXML private ComboBox cboCountry;
    @FXML private ComboBox cboDivision;
    @FXML private TextField nameField;
    @FXML private TextField addressField;
    @FXML private TextField postalField;
    @FXML private TextField phoneField;
    @FXML private TextField IDField;

    /**
     * sets the selected Customer object
     * @param c the customer to be selected
     */
    public static void setSelected(Customer c) {
        selected = c;
    }

    /**
     * Navigates to customer-view
     * @param actionEvent   the button click
     * @throws IOException  thrown when navigating screens
     * @see Utils#navigate(String, String, ActionEvent)
     */
    @FXML private void cancel(ActionEvent actionEvent) throws IOException {
        Utils.navigate("customer-view.fxml", "Customers", actionEvent);
    }

    /**
     * Saves the selected Customer object to the database</br>
     * Navigates to customer-view
     * @param actionEvent   the button click
     * @throws IOException  thrown when navigating screens
     * @see DBCustomer#addCustomerToDatabase(Customer)
     * @see DBCustomer#modifyCustomerInDatabase(Customer)
     * @see Utils#navigate(String, String, ActionEvent)
     */
    @FXML private void save(ActionEvent actionEvent) throws IOException {

        if (cboDivision.getSelectionModel().getSelectedItem() != null && cboCountry.getSelectionModel().getSelectedItem() != null){
            selected.setName(nameField.getText());
            selected.setAddress(addressField.getText());
            selected.setPostalCode(postalField.getText());
            selected.setPhoneNumber(phoneField.getText());
            selected.setDivisionID(DBFirstLevelDivisions.getDivisionID(cboDivision.getSelectionModel().getSelectedItem().toString()));

            // add new or modify existing customer
            if (selected.getId() == 0) {
                selected.setId(Integer.parseInt(IDField.getText()));
                DBCustomer.addCustomerToDatabase(selected);
            }
            else {
                DBCustomer.modifyCustomerInDatabase(selected);
            }
            Utils.navigate("customer-view.fxml", "Customers", actionEvent);
        }
        else {
            errorMsg.setText("Location!");
        }
    }

    /**
     * Populates the Division combobox with the first level divisions of the selected country</br>
     * Then selects the first level division associated with the selected Customer object
     * @see Utils#populateDivisionCombo(ComboBox, ComboBox)
     */
    @FXML private void populateDivisionCombo() {
        Utils.populateDivisionCombo(cboDivision, cboCountry);
        cboDivision.getSelectionModel().selectFirst();
        String divName = DBFirstLevelDivisions.getDivisionNameFromDivisionID(selected.getDivisionID());
        while (selected.getId() !=0 && !cboDivision.getSelectionModel().getSelectedItem().toString().contains(divName)){
            cboDivision.getSelectionModel().selectNext();
        }
    }

    /**
     * Initializes CustEditController by populating the country combobox</br>
     * Also populates all attribute fields for the selected Customer object
     * @param url               The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resourceBundle    The resources used to localize the root object, or null if the root object was not localized.
     * @see Utils#populateCountryCombo(ComboBox)
     */
    @Override public void initialize(URL url, ResourceBundle resourceBundle) {
        errorMsg.setText("");
        Utils.populateCountryCombo(cboCountry);
        if (selected.getId() == 0) {
            IDField.setText(String.valueOf(DBCustomer.getAllCustomers().get(DBCustomer.getAllCustomers().size()-1).getId() + 1));
        }
        else {
            IDField.setText(String.valueOf(selected.getId()));
            cboCountry.getSelectionModel().select(DBFirstLevelDivisions.getCountryIDFromDivisionID(selected.getDivisionID())-1);
            populateDivisionCombo();
        }
        nameField.setText(selected.getName());
        addressField.setText(selected.getAddress());
        postalField.setText(selected.getPostalCode());
        phoneField.setText(selected.getPhoneNumber());
    }
}
