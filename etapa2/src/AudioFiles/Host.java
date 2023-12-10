package AudioFiles;

import java.util.ArrayList;

public class Host extends User{
	private ArrayList<Podcast> podcasts;
	private ArrayList<Announcement> announcements;
	private HostPage hostPage = new HostPage();

	public Host() {
	}

	public ArrayList<Podcast> getPodcasts() {
		return podcasts;
	}

	public void setPodcasts(ArrayList<Podcast> podcasts) {
		this.podcasts = podcasts;
	}

	public ArrayList<Announcement> getAnnouncements() {
		return announcements;
	}

	public void setAnnouncements(ArrayList<Announcement> announcements) {
		this.announcements = announcements;
	}

	public HostPage getHostPage() {
		return hostPage;
	}

	public void setHostPage() {
		this.hostPage.setPodcasts(this.podcasts);
		this.hostPage.setAnnouncements(this.announcements);
	}
}
