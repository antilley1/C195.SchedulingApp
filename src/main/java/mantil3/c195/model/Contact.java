package mantil3.c195.model;

/**
 * Class for Contacts in Database
 */
public class Contact {

    private int id;
    private String name;
    private String email;

    /**
     * Constructor for Contacts class
     * @param id    contact ID
     * @param name  contact name
     * @param email contact email address
     */
    public Contact(int id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    /**
     * Getter for the contact ID
     * @return  int contact ID
     */
    public int getId() {
        return id;
    }

    /**
     * Setter for the contact ID
     * @param id    int contact ID
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Getter for the contact name
     * @return  String contact name
     */
    public String getName() {
        return name;
    }

    /**
     * Setter for the contact name
     * @param name String contact name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Getter for the contact email address
     * @return  String contact email address
     */
    public String getEmail() {
        return email;
    }

    /**
     * Setter for the contact email address
     * @param email String contact email address
     */
    public void setEmail(String email) {
        this.email = email;
    }
}
