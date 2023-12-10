package main;

import AudioFiles.Announcement;
import AudioFiles.Host;
import AudioFiles.Library;
import AudioFiles.User;

public class AddAnnouncementCommand {
	private String name;
	private String description;
	private String message;

	public AddAnnouncementCommand() {
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void getDescription(String description) {
		this.description = description;
	}

	public String getMessage() {
		return message;
	}

	public void addAnnouncement(User user, Library library) {
		// check if the user is a host
		if(!user.getType().equals("host")) {
			message = user.getUsername() + " is not a host.";
			return;
		}

		// check if the announcement already exists
		for (Announcement announcement : ((Host) user).getAnnouncements()) {
			if (announcement.getName().equals(this.name)) {
				message = "Announcement " + this.name + " already exists.";
				return;
			}
		}

		// create the announcement
		Announcement announcement = new Announcement(this.name, this.description);

		// add the announcement to the host's announcements
		((Host) user).getAnnouncements().add(announcement);

		// set the host page
		((Host) user).setHostPage();

		// set the message
		message = user.getUsername() + " has successfully added new announcement.";
	}
}
