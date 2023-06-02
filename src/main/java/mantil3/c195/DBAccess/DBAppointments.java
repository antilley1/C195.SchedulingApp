package mantil3.c195.DBAccess;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import mantil3.c195.App;
import mantil3.c195.helper.JDBC;
import mantil3.c195.model.Appointments;
import mantil3.c195.model.Customer;
import mantil3.c195.model.User;

import java.sql.*;
import java.time.*;
import java.time.format.DateTimeFormatter;

/**
 * Data Access Object for the Appointment Class
 */
public class DBAppointments
{
    /**
     * Returns an ObservableList of all Appointments in the database
     * @return ObservableList of all Appointments
     */
    public static ObservableList<Appointments> getAllAppointments() {
        ObservableList<Appointments> apptList = FXCollections.observableArrayList();

        try {
            String sql = "select * from appointments";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                int apptID = rs.getInt("Appointment_ID");
                String title = rs.getString("Title");
                String desc = rs.getString("Description");
                String location = rs.getString("Location");
                String type = rs.getString("Type");
                LocalDateTime start = rs.getTimestamp("Start").toLocalDateTime();
                LocalDateTime end = rs.getTimestamp("End").toLocalDateTime();
                int custID = rs.getInt("Customer_ID");
                int userID = rs.getInt("User_ID");
                int contactID = rs.getInt("Contact_ID");
                Appointments a = new Appointments(apptID, title, desc, location, type, start, end, custID, userID, contactID);
                apptList.add(a);
                //System.out.println(a.getStart().toString());
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return apptList;
    }

    /**
     * Enters the passed Appointment object into the database
     * @param a the appointment to add
     */
    public static void addAppointmentToDatabase(Appointments a) {
        try {
            DateTimeFormatter customFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            ZonedDateTime zonedStart = ZonedDateTime.of(a.getStart(), ZoneId.systemDefault()).withZoneSameInstant(ZoneOffset.UTC);
            String startString = customFormatter.format(zonedStart);
            ZonedDateTime zonedEnd = ZonedDateTime.of(a.getEnd(), ZoneId.systemDefault()).withZoneSameInstant(ZoneOffset.UTC);
            String endString = customFormatter.format(zonedEnd);

            String sql = "INSERT INTO appointments VALUES("
                    + a.getId() + ", "
                    + "'" + a.getTitle() + "', "
                    + "'" + a.getDescription() + "', "
                    + "'" + a.getLocation() + "', "
                    + "'" + a.getType() + "', "
                    + "'" + startString + "', "
                    + "'" + endString + "', "
                    + "NOW(), 'application', NOW(), 'application', "
                    + a.getCustomerID() + ", "
                    + a.getUserID() + ", "
                    + a.getContactID() + ")";
            //System.out.println("SQL Statement: " + sql);
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ps.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    /**
     * Deletes the passed Appointment object from the database
     * @param a the appointment to delete
     */
    public static void deleteAppointmentFromDatabase(Appointments a) {
        try {
            String sql = "DELETE FROM appointments WHERE Appointment_ID = " + a.getId();;
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ps.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    /**
     * Deletes all appointments of the Customer passed into the function
     * @param c the customers whose appointments are to be deleted
     */
    public static void deleteCustomersAppointments(Customer c){
        try {
            String sql = "DELETE FROM appointments WHERE Customer_ID = " + c.getId();;
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ps.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    /**
     * Updates the passed appointment object in the database
     * @param a the appointment to be updated
     */
    public static void modifyAppointmentInDatabase(Appointments a) {
        try {
            DateTimeFormatter customFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            ZonedDateTime zonedStart = ZonedDateTime.of(a.getStart(), ZoneId.systemDefault()).withZoneSameInstant(ZoneOffset.UTC);
            String startString = customFormatter.format(zonedStart);
            ZonedDateTime zonedEnd = ZonedDateTime.of(a.getEnd(), ZoneId.systemDefault()).withZoneSameInstant(ZoneOffset.UTC);
            String endString = customFormatter.format(zonedEnd);

            String sql = "UPDATE appointments SET "
                    + "Title = '" + a.getTitle() + "', "
                    + "Description = '" + a.getDescription() + "', "
                    + "Location = '" + a.getLocation() + "', "
                    + "Type = '" + a.getType() + "', "
                    + "Start = '" + startString + "', "
                    + "End = '" + endString + "', "
                    + "Last_Update = NOW(), Last_Updated_By = 'application', "
                    + "Customer_ID = " + a.getCustomerID() + ", "
                    + "User_ID = " + a.getUserID() + ", "
                    + "Contact_ID = " + a.getContactID() + " "
                    + "WHERE Appointment_ID = " + a.getId() + ";";
            //System.out.println("SQL Statement: " + sql);
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ps.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    /**
     * Returns an ObservableList of Appointments within the next month
     * @return  ObservableList of Appointments
     */
    public static ObservableList<Appointments> getMonthlyAppointments() {
        ObservableList<Appointments> apptList = FXCollections.observableArrayList();
        LocalDateTime today = LocalDateTime.now();
        LocalDateTime nextMonth = today.plusMonths(1);
        int todayInt = Integer.parseInt(today.format(DateTimeFormatter.BASIC_ISO_DATE));
        int nextMonthInt = Integer.parseInt(nextMonth.format(DateTimeFormatter.BASIC_ISO_DATE));
        try {
            String sql = "select * from appointments WHERE Start <= " + nextMonthInt
                    + " AND Start >= " + todayInt;
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                int apptID = rs.getInt("Appointment_ID");
                String title = rs.getString("Title");
                String desc = rs.getString("Description");
                String location = rs.getString("Location");
                String type = rs.getString("Type");
                LocalDateTime start = rs.getTimestamp("Start").toLocalDateTime();
                LocalDateTime end = rs.getTimestamp("End").toLocalDateTime();
                int custID = rs.getInt("Customer_ID");
                int userID = rs.getInt("User_ID");
                int contactID = rs.getInt("Contact_ID");
                Appointments a = new Appointments(apptID, title, desc, location, type, start, end, custID, userID, contactID);
                apptList.add(a);
                //System.out.println(a.getStart().toString());
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return apptList;
    }

    /**
     * Returns an ObservableList of Appointments within the next week
     * @return Observable list of Appointments
     */
    public static ObservableList<Appointments> getWeeklyAppointments() {
        ObservableList<Appointments> apptList = FXCollections.observableArrayList();
        LocalDateTime today = LocalDateTime.now();
        LocalDateTime nextWeek = today.plusDays(7);
        int todayInt = Integer.parseInt(today.format(DateTimeFormatter.BASIC_ISO_DATE));
        int nextWeekInt = Integer.parseInt(nextWeek.format(DateTimeFormatter.BASIC_ISO_DATE));
        try {
            String sql = "select * from appointments WHERE Start <= " + nextWeekInt
                    + " AND Start >= " + todayInt;
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                int apptID = rs.getInt("Appointment_ID");
                String title = rs.getString("Title");
                String desc = rs.getString("Description");
                String location = rs.getString("Location");
                String type = rs.getString("Type");
                LocalDateTime start = rs.getTimestamp("Start").toLocalDateTime();
                LocalDateTime end = rs.getTimestamp("End").toLocalDateTime();
                int custID = rs.getInt("Customer_ID");
                int userID = rs.getInt("User_ID");
                int contactID = rs.getInt("Contact_ID");
                Appointments a = new Appointments(apptID, title, desc, location, type, start, end, custID, userID, contactID);
                apptList.add(a);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return apptList;
    }

    /**
     * Returns a boolean after checking if there are any conflicts between the new appointment and existing ones for a specific customer
     * @param newApptStart  LocalDateTime of the start of the new appointment
     * @param newApptEnd    LocalDateTime of the end of the new appointments
     * @param customerID    int ID of the customer
     * @return              true if there are no conflicts, false otherwise
     * @see                 DBAppointments#getCustomerAppointmentsExceptThisOne(int, int)
     */
    public static boolean noConflicts (LocalDateTime newApptStart, LocalDateTime newApptEnd, int customerID, int apptID) {
        ObservableList<Appointments> apptList = getCustomerAppointmentsExceptThisOne(customerID, apptID);
//        System.out.println("Appt List Size " + apptList.size());
//        System.out.println("Appt ID: " + apptID);
//        System.out.println("Cust ID: " + customerID);

        for (Appointments appt : apptList) {

//            System.out.println("Checking for conflicts");
//            System.out.println("Proposed Start Time: " + newApptStart.toString());
//            System.out.println("Proposed End Time: " + newApptEnd.toString());
//            System.out.println("Current Appt Start: " + appt.getStart().toString());
//            System.out.println("Current Appt End: " + appt.getEnd().toString());
            //front overlap
            if(newApptStart.isBefore(appt.getStart())  && newApptEnd.isAfter(appt.getStart()) && newApptEnd.isBefore(appt.getEnd())){
//                System.out.println("Front Overlap");
                return false;
            }
            //back overlap
            else if(newApptStart.isAfter(appt.getStart()) && newApptStart.isBefore(appt.getEnd()) && newApptEnd.isAfter(appt.getEnd())){
//                System.out.println("Back Overlap");
                return false;
            }
            //inside overlap
            else if (newApptStart.isAfter(appt.getStart()) && newApptEnd.isBefore(appt.getEnd())){
//                System.out.println("Inside Overlap");
                return false;
            }
            //surrounding overlap
            else if (newApptStart.isBefore(appt.getStart()) && newApptEnd.isAfter(appt.getEnd())){
//                System.out.println("Surrounding Overlap");
                return false;
            }
            else if (newApptStart.isEqual(appt.getStart()) || newApptStart.isEqual(appt.getEnd()) || newApptEnd.isEqual(appt.getEnd()) || newApptEnd.isEqual(appt.getStart())) {
//                System.out.println("Endpoint Overlap");
                return false;
            }
        }
        return true;
    }

    /**
     * Returns list of Appointments for a customer except for the one passed
     * @param custID    the ID of the customer to check
     * @param apptID    the ID of the appointment not returned
     * @return          list of Appointments
     */
    public static ObservableList<Appointments> getCustomerAppointmentsExceptThisOne (int custID, int apptID) {
        ObservableList<Appointments> apptList = FXCollections.observableArrayList();
        try {
            String sql = "SELECT * FROM appointments WHERE Customer_ID = " + custID + " AND Appointment_ID != " + apptID;
//            System.out.println("SQ: " + sql);
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int ID = rs.getInt("Appointment_ID");
                String title = rs.getString("Title");
                String desc = rs.getString("Description");
                String location = rs.getString("Location");
                String type = rs.getString("Type");
                LocalDateTime start = rs.getTimestamp("Start").toLocalDateTime();
                LocalDateTime end = rs.getTimestamp("End").toLocalDateTime();
                int userID = rs.getInt("User_ID");
                int contactID = rs.getInt("Contact_ID");
//                System.out.println("Adding appt to list");
                Appointments a = new Appointments(ID, title, desc, location, type, start, end, custID, userID, contactID);
                apptList.add(a);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
//        System.out.println("Appt List Size: "  + apptList.size());
        return apptList;
    }

    /**
     * Returns a boolean after checking if the new appointment is in business hours
     * @param apptStartLDT  LocalDateTime of the new appointment start
     * @param apptEndLDT    LocalDateTime of the new appointment end
     * @return              true if the new appointment is in business hours, false otherwise
     */
    public static boolean isInBusinessHours (LocalDateTime apptStartLDT, LocalDateTime apptEndLDT) {

        //convert appt start and end to ZonedDateTime
        ZonedDateTime apptStart = apptStartLDT.atZone(ZoneId.systemDefault());
        ZonedDateTime apptEnd = apptEndLDT.atZone(ZoneId.systemDefault());

        //get day of appt
        LocalDate apptDay = apptStart.toLocalDate();

        //declare business start and end on day of appt
        ZonedDateTime businessStart = LocalDateTime.of(apptDay, LocalTime.of(8, 0, 0)).atZone(ZoneId.of("US/Eastern"));
        ZonedDateTime businessEnd = LocalDateTime.of(apptDay, LocalTime.of(20, 0, 0)).atZone(ZoneId.of("US/Eastern"));

        //convert business start and end to UTC
        Instant bStartInstant = businessStart.toInstant();
        ZonedDateTime bStartUTC = bStartInstant.atZone(ZoneId.of("Etc/UTC"));
        Instant bEndInstant = businessEnd.toInstant();
        ZonedDateTime bEndUTC = bEndInstant.atZone(ZoneId.of("Etc/UTC"));

        //convert appt start and end to UTC
        Instant aStartInstant = apptStart.toInstant();
        ZonedDateTime aStartUTC = aStartInstant.atZone(ZoneId.of("Etc/UTC"));
        Instant aEndInstant = apptEnd.toInstant();
        ZonedDateTime aEndUTC = aEndInstant.atZone(ZoneId.of("Etc/UTC"));

        if (
                (bStartUTC.isBefore(aStartUTC) || bStartUTC.isEqual(aStartUTC) )
                && ( bEndUTC.isAfter(aEndUTC) || bEndUTC.isEqual(aEndUTC) )
        ){
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * Returns ObservableList of Appointments for a specific customer
     * @param customerID    int ID of the customer
     * @return              ObservableList of Appointments
     */
    public static ObservableList<Appointments> getCustomerAppointments(int customerID) {
        ObservableList<Appointments> apptList = FXCollections.observableArrayList();

        try {
            String sql = "select * from appointments WHERE Customer_ID = " + customerID;
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                int apptID = rs.getInt("Appointment_ID");
                String title = rs.getString("Title");
                String desc = rs.getString("Description");
                String location = rs.getString("Location");
                String type = rs.getString("Type");
                LocalDateTime start = rs.getTimestamp("Start").toLocalDateTime();
                LocalDateTime end = rs.getTimestamp("End").toLocalDateTime();
                int custID = rs.getInt("Customer_ID");
                int userID = rs.getInt("User_ID");
                int contactID = rs.getInt("Contact_ID");
                Appointments a = new Appointments(apptID, title, desc, location, type, start, end, custID, userID, contactID);
                apptList.add(a);
                //System.out.println(a.getStart().toString());
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return apptList;
    }

    /**
     * Returns an appointment for the user within the next 15 minutes
     * @param user  the user to check for appointments for
     * @return      Appointment in the next 15 minutes, if it exists
     */
    public static Appointments getUserAppointmentsIn15Min (User user) {
        DateTimeFormatter customFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        ZonedDateTime nowPlus15Min = ZonedDateTime.now().plusMinutes(15);
        Instant nowPlus15MinInstant = nowPlus15Min.toInstant();
        ZonedDateTime nowPlus15MinUTC = nowPlus15MinInstant.atZone(ZoneId.of("Etc/UTC"));
        String nowPlus15MinStr = nowPlus15MinUTC.format(customFormatter);
        int apptID = 0;
        String title = "";
        String desc = "";
        String location = "";
        String type = "";
        LocalDateTime start = LocalDateTime.now();
        LocalDateTime end = LocalDateTime.now();
        int custID = 0;
        int userID = 0;
        int contactID = 0;
        try {
            String sql = "SELECT * FROM appointments WHERE User_ID = " + user.getId()
                    + " AND Start <= '" + nowPlus15MinStr
                    + "' AND Start >= now()";
            //System.out.println("SQL: " + sql);
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            if(rs.next()) {
                apptID = rs.getInt("Appointment_ID");
                title = rs.getString("Title");
                desc = rs.getString("Description");
                location = rs.getString("Location");
                type = rs.getString("Type");
                start = rs.getTimestamp("Start").toLocalDateTime();
                end = rs.getTimestamp("End").toLocalDateTime();
                custID = rs.getInt("Customer_ID");
                userID = rs.getInt("User_ID");
                contactID = rs.getInt("Contact_ID");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return new Appointments(apptID, title, desc, location, type, start, end, custID, userID, contactID);
    }

    /**
     * Returns an ObservableList of Integers of the counts of all types of appointments for a specific customer
     * @param customerID    int ID of the customer
     * @return              ObservableLIst of Integers
     */
    public static ObservableList<Integer> getAppointmentTypeCountForCustomer(int customerID) {
        ObservableList<Integer> list = FXCollections.observableArrayList();
        String sql = "SELECT Type, COUNT(1) as OccurenceValue FROM appointments WHERE Customer_ID = "
                + customerID + " GROUP BY Type ORDER BY OccurenceValue";
        try {
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while ( rs.next() ) {
                int count = rs.getInt("OccurenceValue");
                list.add(count);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return list;
    }

    /**
     * Returns an ObservableList of Strings of all types of appointments for a specific customer
     * @param customerID    int ID of the customer
     * @return              ObservableList of Strings
     */
    public static ObservableList<String> getAppointmentTypesForCustomer(int customerID) {
        ObservableList<String> list = FXCollections.observableArrayList();
        String sql = "SELECT Type, COUNT(1) as OccurenceValue FROM appointments WHERE Customer_ID = "
                + customerID + " GROUP BY Type ORDER BY OccurenceValue";
        try {
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while ( rs.next() ) {
                String type = rs.getString("Type");
                list.add(type);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return list;
    }

    /**
     * Returns an ObservableList of Integers for all months when appointments occur for a specific customer
     * @param customerID    int ID of the customer
     * @return              ObservableList of Integers
     */
    public static ObservableList<Integer> getCustomerAppointmentMonths(int customerID) {
        ObservableList<Integer> list = FXCollections.observableArrayList();
        String sql = "SELECT MONTH(Start) MONTH, COUNT(*) COUNT FROM appointments WHERE Customer_ID = "
                + customerID + " GROUP BY MONTH(Start)";
        try {
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while ( rs.next() ) {
                int month = rs.getInt("MONTH");
                list.add(month);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return list;
    }

    /**
     * Returns an ObservableList of Integers of counts for all months when appointments occur for a specific customer
     * @param customerID    int ID of the customer
     * @return              ObservableList of Integers
     */
    public static ObservableList<Integer> getCustomersAppointmentCountByMonth(int customerID) {
        ObservableList<Integer> list = FXCollections.observableArrayList();
        String sql = "SELECT MONTH(Start) MONTH, COUNT(*) COUNT FROM appointments WHERE Customer_ID = "
                + customerID + " GROUP BY MONTH(Start)";
        try {
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while ( rs.next() ) {
                int count = rs.getInt("COUNT");
                list.add(count);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return list;
    }

    /**
     * Returns an ObservableList of Appointments for a contact
     * @param id    int ID of the customer
     * @return      ObservableList of Appointments
     */
    public static ObservableList<Appointments> getAllContactsAppointments(int id) {
        ObservableList<Appointments> list = FXCollections.observableArrayList();
        String sql = "SELECT * FROM appointments WHERE Contact_ID = " + id;

        try {
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int apptID = rs.getInt("Appointment_ID");
                String title = rs.getString("Title");
                String desc = rs.getString("Description");
                String location = rs.getString("Location");
                String type = rs.getString("Type");
                LocalDateTime start = rs.getTimestamp("Start").toLocalDateTime();
                LocalDateTime end = rs.getTimestamp("End").toLocalDateTime();
                int custID = rs.getInt("Customer_ID");
                int userID = rs.getInt("User_ID");
                int contactID = rs.getInt("Contact_ID");
                Appointments a = new Appointments(apptID, title, desc, location, type, start, end, custID, userID, contactID);
                list.add(a);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return list;
    }

    /**
     * Returns an ObservableList of Appointments for a location
     * @param location  String name of location
     * @return          ObservableList of Appointments
     */
    public static ObservableList<Appointments> getAppointmentsByLocation(String location) {
        ObservableList<Appointments> apptList = FXCollections.observableArrayList();
        String sql = "SELECT * FROM appointments WHERE Location = '" + location + "'";
        try {
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int apptID = rs.getInt("Appointment_ID");
                String title = rs.getString("Title");
                String desc = rs.getString("Description");
                String type = rs.getString("Type");
                LocalDateTime start = rs.getTimestamp("Start").toLocalDateTime();
                LocalDateTime end = rs.getTimestamp("End").toLocalDateTime();
                int custID = rs.getInt("Customer_ID");
                int userID = rs.getInt("User_ID");
                int contactID = rs.getInt("Contact_ID");
                Appointments a = new Appointments(apptID, title, desc, location, type, start, end, custID, userID, contactID);
                apptList.add(a);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return apptList;
    }
}
