package mantil3.c195.model;

/**
 * Class for Countries in the Database
 */
public class Country {
    private int id;
    private String name;

    /**
     * Constructor for the Country class
     * @param id    int country ID
     * @param name  String country name
     */
    public Country(int id, String name) {
        this.id= id;
        this.name = name;
    }

    /**
     * Getter for the country ID
     * @return int country ID
     */
    public int getId() { return id; }

    /**
     * Getter for the country name
     * @return  String country name
     */
    public String getName() { return name; }
}
