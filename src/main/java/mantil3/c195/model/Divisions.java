package mantil3.c195.model;

/**
 * Class for First Level Country Divisions in the Database
 */
public class Divisions {
    int id;
    String division;
    int countryID;

    /**
     * Constructor for the Divisions Class
     * @param id        int division ID
     * @param division  String division  name
     * @param countryID int associated country ID
     */
    public Divisions(int id, String division, int countryID) {
        this.id = id;
        this.division = division;
        this.countryID = countryID;
    }

    /**
     * Getter for the division ID
     * @return  int division ID
     */
    public int getId() {
        return id;
    }

    /**
     * Setter for the division ID
     * @param id    int division ID
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Getter for the division name
     * @return  String division name
     */
    public String getDivision() {
        return division;
    }

    /**
     * Setter for the division name
     * @param division  String division name
     */
    public void setDivision(String division) {
        this.division = division;
    }

    /**
     * Getter for the associated country ID
     * @return  int the associated country ID
     */
    public int getCountryID() {
        return countryID;
    }

    /**
     * Setter for the associated country ID
     * @param countryID int associated country ID
     */
    public void setCountryID(int countryID) {
        this.countryID = countryID;
    }
}
