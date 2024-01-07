package observer;

public interface Subject {
    public void addObserver(Observer observer);
    public void notifyObservers(final String name, final String description);
}
