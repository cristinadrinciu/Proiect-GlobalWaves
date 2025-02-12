package pages;

import pagecontent.Announcement;
import audiofiles.Podcast;
import fileio.input.EpisodeInput;
import users.Host;

import java.util.ArrayList;

public class HostPage extends Page {
    private ArrayList<Podcast> podcasts = new ArrayList<>();
    private ArrayList<Announcement> announcements = new ArrayList<>();
    private Host host;

    public HostPage(final Host host) {
        super("host");
        this.host = host;
    }

    /**
     * @return the podcasts array
     */
    public ArrayList<Podcast> getPodcasts() {
        return this.podcasts;
    }

    /**
     * @return the announcements array
     */
    public ArrayList<Announcement> getAnnouncements() {
        return this.announcements;
    }

    /**
     * builds the list of podcasts for the host page
     * @param podcasts the podcasts to set
     */
    public void setPodcasts(final ArrayList<Podcast> podcasts) {
        this.podcasts.clear();
        this.podcasts.addAll(podcasts);
    }

    /**
     * builds the list of announcements for the host page
     * @param announcements the announcements to set
     */
    public void setAnnouncements(final ArrayList<Announcement> announcements) {
        this.announcements.clear();
        this.announcements.addAll(announcements);
    }

    /**
     * This method prints the page
     */
    @Override
    public String printPage() {
        // add the podcasts
        String message = "Podcasts:\n\t[";
        for (Podcast podcast : this.podcasts) {
            message += podcast.getName() + ":\n\t[";
            for (EpisodeInput episode : podcast.getEpisodes()) {
                message += episode.getName() + " - " + episode.getDescription() + ", ";
            }
            // remove the last comma if there are episodes
            if (!podcast.getEpisodes().isEmpty()) {
                message = message.substring(0, message.length() - 2);
            }
            message += "]\n, ";
        }
        // remove the last comma if there are podcasts
        if (!this.podcasts.isEmpty()) {
            message = message.substring(0, message.length() - 2);
        }
        message += "]\n\nAnnouncements:\n\t[";
        for (Announcement announcement : this.announcements) {
            message += announcement.getName() + ":\n\t" + announcement.getDescription() + "\n, ";
        }
        // remove the last comma if there are announcements
        if (!this.announcements.isEmpty()) {
            message = message.substring(0, message.length() - 2);
        }
        message += "]";
        return message;
    }

    /**
     * @return the host
     */
    public Host getHost() {
        return host;
    }

}
