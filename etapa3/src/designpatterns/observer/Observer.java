package designpatterns.observer;

public interface Observer {
    /**
     * Update the designpatterns.observer notifications
     * @param name the name of the notification
     * @param description the description of the notification
     */
    void update(String name, String description);
}
