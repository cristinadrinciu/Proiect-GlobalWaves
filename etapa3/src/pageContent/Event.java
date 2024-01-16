package pageContent;

public class Event {
    private String name;
    private String description;
    private String date;

    public Event() {
    }

    /**
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name for the name of the event
     */
    public void setName(final String name) {
        this.name = name;
    }

    /**
     * @return description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description for the description of the event
     */
    public void setDescription(final String description) {
        this.description = description;
    }

    /**
     * @return date
     */
    public String getDate() {
        return date;
    }

    /**
     * @param date for the date of the event
     */
    public void setDate(final String date) {
        this.date = date;
    }
}
