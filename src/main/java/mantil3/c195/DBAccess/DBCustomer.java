package mantil3.c195.DBAccess;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import mantil3.c195.helper.JDBC;
import mantil3.c195.model.Customer;
import mantil3.c195.model.Divisions;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Data Access Object for the Customer class
 */
public class DBCustomer {
    /**
     * Returns an ObservableList of all Customers
     * @return  ObservableList of Customers
     */
    public static ObservableList<Customer> getAllCustomers() {
        ObservableList<Customer> customerList = FXCollections.observableArrayList();

        try {
            String sql = "select * from customers";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int custID = rs.getInt("Customer_ID");
                String name = rs.getString("Customer_Name");
                String address = rs.getString("Address");
                String postalCode = rs.getString("Postal_Code");
                String phone = rs.getString("Phone");
                int divisionID = rs.getInt("Division_ID");
                Customer c = new Customer(custID, name, address, postalCode, phone, divisionID);
                customerList.add(c);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return customerList;
    }

    /**
     * Returns an ObservableList of Customers in a country
     * @param countryID int ID of the country
     * @return          ObservableList of Customers
     */
    public static ObservableList<Customer> getCustomersByCountry(int countryID) {
        ObservableList<Customer> customerList = FXCollections.observableArrayList();
        ObservableList<Divisions> divisionList = DBFirstLevelDivisions.getFirstLevelDivisions(countryID);

        for (int i = 0; i < divisionList.size(); i++) {
            try {
                String sql = "select * from customers where Division_ID = " + divisionList.get(i).getId();
                PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);

                ResultSet rs = ps.executeQuery();

                while (rs.next()) {
                    int custID = rs.getInt("Customer_ID");
                    String name = rs.getString("Customer_Name");
                    String address = rs.getString("Address");
                    String postalCode = rs.getString("Postal_Code");
                    String phone = rs.getString("Phone");
                    int divisionID = rs.getInt("Division_ID");
                    Customer c = new Customer(custID, name, address, postalCode, phone, divisionID);
                    customerList.add(c);
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return customerList;
    }

    /**
     * Returns an ObservableList of Customers in a given first level division
     * @param selectedDivisionID    int ID of the division
     * @return                      ObservableList of Customers
     */
    public static ObservableList<Customer> getCustomersByDivision(int selectedDivisionID) {
        ObservableList<Customer> customerList = FXCollections.observableArrayList();

        try {
            String sql = "select * from customers where Division_ID = " + selectedDivisionID;
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int custID = rs.getInt("Customer_ID");
                String name = rs.getString("Customer_Name");
                String address = rs.getString("Address");
                String postalCode = rs.getString("Postal_Code");
                String phone = rs.getString("Phone");
                int divisionID = rs.getInt("Division_ID");
                Customer c = new Customer(custID, name, address, postalCode, phone, divisionID);
                customerList.add(c);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return customerList;

    }

    /**
     * Returns customer name given the customer ID
     * @param customerID    int ID of the customer
     * @return              String name of customer
     */
    public static String getCustomerNameFromCustomerID(int customerID) {
        String customerName = "";
        try {
            String sql = "select Customer_Name from customers where Customer_ID = " + customerID;
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);

            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                customerName = rs.getString("Customer_Name");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return customerName;
    }

    /**
     * Adds the passed customer to the database
     * @param c the customer to be added
     */
    public static void addCustomerToDatabase (Customer c) {
        try{
            String sql = "INSERT INTO customers VALUES("
                    + c.getId() + ", "
                    + "'" + c.getName() + "', "
                    + "'" + c.getAddress() + "', "
                    + "'" + c.getPostalCode() + "', "
                    + "'" + c.getPhoneNumber() + "', "
                    + "NOW(), 'application', NOW(), 'application', "
                    + c.getDivisionID() + ")";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ps.executeUpdate();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    /**
     * Deletes the passed customer from the database
     * @param c the customer to be deleted
     */
    public static void deleteCustomerFromDatabase (Customer c) {
        try{
            String sql = "DELETE FROM customers WHERE (Customer_ID = " + c.getId() + ")";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ps.executeUpdate();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    /**
     * Updates the passed customer in the database
     * @param c the customer to be updated
     */
    public static void modifyCustomerInDatabase(Customer c) {
        try{
            String sql = "UPDATE customers SET "
                    + "Customer_Name = '" + c.getName() + "', "
                    + "Address = '" + c.getAddress() + "', "
                    + "Postal_Code = '" + c.getPostalCode() + "', "
                    + "Phone = '" + c.getPhoneNumber() + "', "
                    + "Last_Update = NOW(), Last_Updated_By = 'application', "
                    + "Division_ID = " + c.getDivisionID() + " "
                    + "WHERE Customer_ID = " + c.getId();
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ps.executeUpdate();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
