package user.types;

import fileio.input.EpisodeInput;
import observer.Observer;
import observer.Subject;
import page.content.Announcement;
import audio.files.Podcast;
import pages.HostPage;
import visit.pattern.Visitable;
import visit.pattern.Visitor;

import java.util.ArrayList;

public class Host extends User implements Subject, Visitable {
    private ArrayList<Podcast> podcasts;
    private ArrayList<Announcement> announcements;
    private HostPage hostPage = new HostPage(this);
    private ArrayList<User> subscribers = new ArrayList<User>();
    private HostStatistics hostStatistics = new HostStatistics();


    public Host() {
    }

    /**
     * @return the podcasts
     */
    public ArrayList<Podcast> getPodcasts() {
        return podcasts;
    }

    /**
     * @param podcasts the podcasts to set
     */
    public void setPodcasts(final ArrayList<Podcast> podcasts) {
        this.podcasts = podcasts;
    }

    /**
     * @return the announcements
     */
    public ArrayList<Announcement> getAnnouncements() {
        return announcements;
    }

    /**
     * @param announcements the announcements to set
     */
    public void setAnnouncements(final ArrayList<Announcement> announcements) {
        this.announcements = announcements;
    }

    /**
     * @return the hostPage
     */
    public HostPage getHostPage() {
        return hostPage;
    }

    /**
     * This method sets the host page
     */
    public void setHostPage() {
        this.hostPage.setPodcasts(this.podcasts);
        this.hostPage.setAnnouncements(this.announcements);
    }

    /**
     * @return the subscribers
     */
    public ArrayList<User> getSubscribers() {
        return subscribers;
    }

    /**
     * @param subscribers the subscribers to set
     */
    public void setSubscribers(final ArrayList<User> subscribers) {
        this.subscribers = subscribers;
    }

    /**
     * @param episode the episode to set the number of listens to in the statistics
     */
    public void setListensToEpisode(final String episode) {
        // add the episode to the topEpisodes hashmap if it is not already there
        if (!hostStatistics.getTopEpisodes().containsKey(episode)) {
            hostStatistics.getTopEpisodes().put(episode, 1);
        } else {
            // if the episode is already in the hashmap, increment the number of listens
            hostStatistics.getTopEpisodes().put(episode, hostStatistics.getTopEpisodes().get(episode) + 1);
        }
    }

    /**
     * @param fan the fan to set the number of listens to in the statistics
     */
    public void setListensToFan(final String fan) {
        // add the fan to the topFans hashmap if it is not already there
        if (!hostStatistics.getTopFans().containsKey(fan)) {
            hostStatistics.getTopFans().put(fan, 1);
        } else {
            // if the fan is already in the hashmap, increment the number of listens
            hostStatistics.getTopFans().put(fan, hostStatistics.getTopFans().get(fan) + 1);
        }
    }

    /**
     * @return the statistics
     */
    public HostStatistics getHostStatistics() {
        return hostStatistics;
    }

    @Override
    public void notifyObservers(final String name, final String description) {
        for (User subscriber : subscribers) {
            subscriber.update(name, description);
        }
    }

    @Override
    public void addObserver(Observer observer) {
        subscribers.add((User) observer);
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
