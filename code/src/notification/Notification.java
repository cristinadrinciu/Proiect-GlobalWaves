package notification;

public class Notification {
    private String name;
    private String description;

    public Notification() {
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(final String name) {
        this.name = name;
    }

    /**
     * @return the message
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the message to set
     */
    public void setDescription(final String description) {
        this.description = description;
    }
}
