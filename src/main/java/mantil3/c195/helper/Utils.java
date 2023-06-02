package mantil3.c195.helper;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;
import mantil3.c195.App;
import mantil3.c195.DBAccess.*;
import mantil3.c195.model.*;

import java.io.IOException;

/**
 * Utility class to contain methods commonly used
 */
public class Utils {
    /**
     * Navigates between screens
     * @param destination   String path of view to go to
     * @param title         String title of the next view
     * @param actionEvent   the button click
     * @throws IOException  thrown when navigating screens
     */
    public static void navigate (String destination, String title, ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(App.class.getResource(destination));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setTitle(title);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Populates a combobox with country objects
     * @param cbo   the combobox to be populated
     * @see DBCountry#getAllCountries()
     */
    public static void populateCountryCombo(ComboBox<String> cbo) {
        ObservableList<Country> countryObservableList = DBCountry.getAllCountries();
        for (Country country : countryObservableList) {
            cbo.getItems().add(country.getName());
        }
    }

    /**
     * Populates a combobox with Division objects based on the selection of a country combobox
     * @param cboDiv        the combobox to be populated
     * @param cboCountry    the country combobox
     * @return              int index of the country combobox
     * @see DBFirstLevelDivisions#getFirstLevelDivisions(int)
     */
    public static int populateDivisionCombo(ComboBox<String> cboDiv, ComboBox<String> cboCountry) {
        cboDiv.getItems().clear();
        int cboCountryIndex = cboCountry.getSelectionModel().getSelectedIndex() + 1;
        ObservableList<Divisions> divisions = DBFirstLevelDivisions.getFirstLevelDivisions(cboCountryIndex);

        for (Divisions division : divisions) {
            cboDiv.getItems().add(division.getDivision());
        }
        return cboCountryIndex;
    }

    /**
     * Populates a combobox with customer objects
     * @param cbo   the combobox to be populated
     * @see DBCustomer#getAllCustomers()
     */
    public static void populateCustomerCombo(ComboBox<String> cbo){
        ObservableList<Customer> customerObservableList = DBCustomer.getAllCustomers();
        for (Customer customer : customerObservableList) {
            cbo.getItems().add(customer.getName());
        }
    }

    /**
     * Populates a combobox with contact objects
     * @param cbo   the combobox to be populated
     * @see DBContacts#getAllContacts()
     */
    public static void populateContactCombo(ComboBox<String> cbo){
        ObservableList<Contact> contactObservableList = DBContacts.getAllContacts();
        for (Contact contact : contactObservableList) {
            cbo.getItems().add(contact.getName());
        }
    }

    /**
     * Populates a combobox with user objects
     * @param cboUser the combobox to be populated
     * @see DBUser#getAllUsers()
     */
    public static void populateUserCombo(ComboBox<String> cboUser) {
        ObservableList<User> userObservableList = DBUser.getAllUsers();
        for (User user : userObservableList) {
            cboUser.getItems().add(String.valueOf(user.getId()));
        }
    }
}
