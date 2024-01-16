package designPatterns.observer;

public interface Subject {
    /**
     * Adds an designPatterns.observer to the list of observers
     * @param observer the designPatterns.observer to be added
     */
    void addObserver(Observer observer);

    /**
     * Notifies all the observers
     * @param name the name of the notification
     * @param description the description of the notification
     */
    void notifyObservers(String name, String description);
}
