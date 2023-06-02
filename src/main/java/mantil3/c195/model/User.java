package mantil3.c195.model;

/**
 * Class for users in database
 */
public class User {
    private int id;
    private String name;
    private String password;

    /**
     * Constructor for the user class
     * @param id        int user ID
     * @param name      String username
     * @param password  String user password
     */
    public User(int id, String name, String password) {
        this.id = id;
        this.name = name;
        this.password = password;
    }

    /**
     * Getter for the user ID
     * @return  int user ID
     */
    public int getId() {
        return id;
    }

    /**
     * Setter for the user ID
     * @param id    int user ID
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Getter for the username
     * @return  String username
     */
    public String getName() {
        return name;
    }

    /**
     * Setter for the username
     * @param name String username
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Getter for the user password
     * @return  String user password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Setter for the user password
     * @param password  String user password
     */
    public void setPassword(String password) {
        this.password = password;
    }
}
