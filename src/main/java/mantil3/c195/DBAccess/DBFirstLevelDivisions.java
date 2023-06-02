package mantil3.c195.DBAccess;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import mantil3.c195.helper.JDBC;
import mantil3.c195.model.Country;
import mantil3.c195.model.Divisions;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Data Access Object for the Divisions class
 */
public class DBFirstLevelDivisions {
    /**
     * Returns ObservableList of all Divisions of a Country
     * @param countryID int ID of the country
     * @return          ObservableList of Divisions
     */
    public static ObservableList<Divisions> getFirstLevelDivisions(int countryID) {
        ObservableList<Divisions> divList = FXCollections.observableArrayList();

        try {
            String sql = "select * from first_level_divisions WHERE Country_ID = " + countryID;
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                int divID = rs.getInt("Division_ID");
                String divName = rs.getString("Division");
                Divisions d = new Divisions(divID, divName, countryID);
                divList.add(d);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return divList;
    }

    /**
     * Returns ID of Division given its name
     * @param divisionName  String name of the division
     * @return              int ID of the division
     */
    public static int getDivisionID(String divisionName) {
        int divID = 0;
        try {
            String sql = "SELECT Division_ID FROM first_level_divisions WHERE Division = '" + divisionName + "'";

            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                divID = rs.getInt("Division_ID");

            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return divID;
    }

    /**
     * Returns Country ID from the Division ID
     * @param divID int ID of Division
     * @return      int ID of Country
     */
    public static int getCountryIDFromDivisionID (int divID) {
        int countryID = 0;
        try {
            String sql = "SELECT Country_ID FROM first_level_divisions WHERE Division_ID = " + divID;
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            if (rs.next()){
                countryID = rs.getInt("Country_ID");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return countryID;
    }

    /**
     * Returns name of Division given its ID
     * @param divID int ID of Division
     * @return      String name of Division
     */
    public static String getDivisionNameFromDivisionID (int divID) {
        String divName = "";
        try {
            String sql = "SELECT Division FROM first_level_divisions WHERE Division_ID = " + divID;
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            if (rs.next()){
                divName = rs.getString("Division");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return divName;
    }
}
