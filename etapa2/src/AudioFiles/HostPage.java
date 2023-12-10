package AudioFiles;

import fileio.input.EpisodeInput;

import java.util.ArrayList;

public class HostPage implements Page{
	private ArrayList<Podcast> podcasts = new ArrayList<>();
	private ArrayList<Announcement> announcements = new ArrayList<>();

	public HostPage() {
	}

	public ArrayList<Podcast> getPodcasts() {
		return this.podcasts;
	}

	public ArrayList<Announcement> getAnnouncements() {
		return this.announcements;
	}

	public void setPodcasts(ArrayList<Podcast> podcasts) {
		this.podcasts.clear();
		this.podcasts.addAll(podcasts);
	}

	public void setAnnouncements(ArrayList<Announcement> announcements) {
		this.announcements.clear();
		this.announcements.addAll(announcements);
	}

	@Override
	public String printPage() {
		// Podcasts:\n\t[podcastname1:\n\t[episodenam1 - episodedescription1, episodenam2 - episodedescription2, … ], …]\n\nAnnouncements\n\t[announcementname1 - announcementdescription1, announcementname2 - announcementdescription2, …]
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
}
