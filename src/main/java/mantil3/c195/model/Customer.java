package mantil3.c195.model;

/**
 * Class for customers in the Database
 */
public class Customer {
    private int id;
    private String name;
    private String address;
    private String postalCode;
    private String phoneNumber;
    private int divisionID;

    /**
     * Constructor for the Customer Class
     * @param id            int customer ID
     * @param name          String customer name
     * @param address       String customer address
     * @param postalCode    String customer postal code
     * @param phoneNumber   String customer phone number
     * @param divisionID    int customer's first level division ID
     */
    public Customer(int id, String name, String address, String postalCode, String phoneNumber, int divisionID) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.postalCode = postalCode;
        this.phoneNumber = phoneNumber;
        this.divisionID = divisionID;
    }

    /**
     * Getter for the customer ID
     * @return  int customer ID
     */
    public int getId() { return id; }

    /**
     * Setter for the customer ID
     * @param id    int customer ID
     */
    public void setId(int id) { this.id = id; }

    /**
     * Getter for the customer name
     * @return  String customer name
     */
    public String getName() { return name; }

    /**
     * Setter for the customer name
     * @param name  String customer name
     */
    public void setName(String name) { this.name = name; }

    /**
     * Getter for the customer address
     * @return  String customer address
     */
    public String getAddress() { return address; }

    /**
     * Setter for the customer address
     * @param address   String customer address
     */
    public void setAddress(String address) { this.address = address; }

    /**
     * Getter for the customer postal code
     * @return  String customer postal code
     */
    public String getPostalCode() { return postalCode; }

    /**
     * Setter for the customer postal code
     * @param postalCode    String customer postal code
     */
    public void setPostalCode(String postalCode) { this.postalCode = postalCode; }

    /**
     * Getter for the customer phone number
     * @return  String customer phone number
     */
    public String getPhoneNumber() { return phoneNumber; }

    /**
     * Setter for the customer phone number
     * @param phoneNumber   String customer phone number
     */
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber;}

    /**
     * Getter for the customer first level division ID
     * @return  int customer first level division ID
     */
    public int getDivisionID() { return divisionID; }

    /**
     * Setter for the customer first level division ID
     * @param divisionID    int customer first level division ID
     */
    public void setDivisionID(int divisionID) { this.divisionID = divisionID; }
}
