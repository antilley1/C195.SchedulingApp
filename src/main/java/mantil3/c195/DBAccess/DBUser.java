package mantil3.c195.DBAccess;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import mantil3.c195.helper.JDBC;
import mantil3.c195.model.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Data Access Object for the User class
 */
public class DBUser {
    /**
     * Verifies that the username - password combo is valid
     * @param username  String username
     * @param password  String password
     * @return          true if username and password match, false otherwise
     */
    public static boolean checkUsernamePassword(String username, String password) {
        try {
            String sql = "SELECT User_Name FROM users where User_Name = '" + username + "'";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            if (rs.next() == false) {
                return false;
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        String correctPassword = "";
        try {
            String sql = "SELECT Password FROM users where User_Name = '" + username + "'";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            rs.next();
            correctPassword = rs.getString("Password");

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        if (password.contentEquals(correctPassword)) {
            return true;
        }
        else return false;
    }

    /**
     * Returns User object given its username
     * @param username  String username
     * @return          User object
     */
    public static User getUserFromUsername (String username) {
        int userID = 0;
        String password = "";
        try {
            String sql = "SELECT * from users WHERE User_Name = '" + username + "'";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                userID = rs.getInt("User_ID");
                password = rs.getString("Password");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return new User(userID, username, password);
    }

    /**
     * Returns ObservableList of all users in database
     * @return  ObservableList of Users
     */
    public static ObservableList<User> getAllUsers() {
        ObservableList<User> userList = FXCollections.observableArrayList();
        try {
            String sql = "SELECT * from users";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("User_ID");
                String pw = rs.getString("Password");
                String username = rs.getString("User_Name");
                userList.add(new User(id, username, pw));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return userList;
    }

    /**
     * Returns username of a user give the user ID
     * @param userID    int user ID
     * @return          String username
     */
    public static String getUserNameFromID(int userID) {
        String username = "";

        try {
            String sql = "SELECT User_Name FROM users WHERE User_ID + " + userID;
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            if (rs.next()){
                username = rs.getString("User_Name");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return username;
    }
}
