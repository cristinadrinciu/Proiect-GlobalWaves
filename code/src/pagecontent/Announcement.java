package pagecontent;

public class Announcement {
    private String name;
    private String description;

    public Announcement() {
    }

    public Announcement(final String name, final String description) {
        this.name = name;
        this.description = description;
    }

    /**
     * getter for the name of the announcement
     * @return name
     */
    public String getName() {
        return this.name;
    }

    /**
     * getter for the description of the announcement
     * @return description
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * setter for the name of the announcement
     * @param name for the name of the announcement
     */
    public void setName(final String name) {
        this.name = name;
    }

    /**
     * setter for the description of the announcement
     * @param description for the description of the announcement
     */
    public void setDescription(final String description) {
        this.description = description;
    }
}
