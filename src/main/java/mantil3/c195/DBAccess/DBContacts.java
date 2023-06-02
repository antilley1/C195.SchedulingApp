package mantil3.c195.DBAccess;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import mantil3.c195.helper.JDBC;
import mantil3.c195.model.Contact;
import mantil3.c195.model.Customer;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Data Access Object for the Contacts class
 */
public class DBContacts {
    /**
     * Returns an ObservableList of all Contacts
     * @return  ObservableList of Contacts
     */
    public static ObservableList<Contact> getAllContacts() {
        ObservableList<Contact> contactList = FXCollections.observableArrayList();

        try {
            String sql = "select * from contacts";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int contactID = rs.getInt("Contact_ID");
                String name = rs.getString("Contact_Name");
                String email = rs.getString("Email");
                Contact c = new Contact(contactID, name, email);
                contactList.add(c);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return contactList;
    }

    /**
     * Returns name of contact from contact ID
     * @param contactID int ID of contact
     * @return          String name of contact
     */
    public static String getContactNameFromContactID(int contactID) {
        String contactName = "";

        try {
            String sql = "select Contact_Name from contacts WHERE Contact_ID =" + contactID;
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);

            ResultSet rs = ps.executeQuery();
            if (rs.next()){
                contactName = rs.getString("Contact_Name");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return contactName;
    }
}
