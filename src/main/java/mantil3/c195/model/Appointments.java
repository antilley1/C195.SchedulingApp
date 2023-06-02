package mantil3.c195.model;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * Class for Appointments from the Database
 */
public class Appointments {
    private int id;
    private String title;
    private String description;
    private String location;
    private String type;
    private LocalDateTime start;
    private LocalDateTime end;
    private int customerID;
    private int userID;
    private int contactID;

    /**
     * Constructor for the Appointments class
     * @param id            appointment ID
     * @param title         title of appointment
     * @param description   description of appointment
     * @param location      location of appointment
     * @param type          type of appointment
     * @param start         start time and date of appointment
     * @param end           end time and date of appointment
     * @param customerID    associated customer ID
     * @param userID        associated user ID
     * @param contactID     associated contactID
     */
    public Appointments(int id, String title, String description, String location, String type, LocalDateTime start, LocalDateTime end, int customerID, int userID, int contactID) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.location = location;
        this.type = type;
        this.start = start;
        this.end = end;
        this.customerID = customerID;
        this.userID = userID;
        this.contactID = contactID;
    }

    /**
     * Getter for the appointment ID
     * @return  int appointment ID
     */
    public int getId() {
        return id;
    }

    /**
     * Setter for appointment ID
     * @param id    appointment ID
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Getter for appointment title
     * @return  String appointment title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Setter for appointment title
     * @param title String appointment title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Getter for the appointment description
     * @return  String appointment description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Setter for the appointment description
     * @param description   String appointment description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Getter for the appointment location
     * @return  String appointment location
     */
    public String getLocation() {
        return location;
    }

    /**
     * Setter for the appointment location
     * @param location  String appointment location
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     * Getter for the appointment type
     * @return  String appointment type
     */
    public String getType() {
        return type;
    }

    /**
     * Setter for the appointment type
     * @param type  String appointment type
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Getter for the appointment start time and date
     * @return  LocalDateTime of the appointment start
     */
    public LocalDateTime getStart() {
        return start;
    }

    /**
     * Setter for the appointment start time and date
     * @param start LocalDateTime of the appointment start
     */
    public void setStart(LocalDateTime start) {
        this.start = start;
    }

    /**
     * Getter for the appointment end time and date
     * @return  LocalDate time of the appointment end
     */
    public LocalDateTime getEnd() {
        return end;
    }

    /**
     * Setter for the appointment end time and date
     * @param end   LocalDateTime of the appointment end
     */
    public void setEnd(LocalDateTime end) {
        this.end = end;
    }

    /**
     * Getter for the customer ID
     * @return  int customer ID
     */
    public int getCustomerID() {
        return customerID;
    }

    /**
     * Setter for the customer ID
     * @param customerID    int customer ID
     */
    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    /**
     * Getter for the user ID
     * @return  int user ID
     */
    public int getUserID() {
        return userID;
    }

    /**
     * Setter for the user ID
     * @param userID    int user ID
     */
    public void setUserID(int userID) {
        this.userID = userID;
    }

    /**
     * Getter for the contact ID
     * @return  int contact ID
     */
    public int getContactID() {
        return contactID;
    }

    /**
     * Setter for the contact ID
     * @param contactID int contact ID
     */
    public void setContactID(int contactID) {
        this.contactID = contactID;
    }
}
