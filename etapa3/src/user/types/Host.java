package user.types;

import page.content.Announcement;
import audio.files.Podcast;
import pages.HostPage;

import java.util.ArrayList;

public class Host extends User {
    private ArrayList<Podcast> podcasts;
    private ArrayList<Announcement> announcements;
    private HostPage hostPage = new HostPage();

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
}
