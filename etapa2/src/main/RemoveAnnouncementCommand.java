package main;

import AudioFiles.Announcement;
import AudioFiles.Host;
import AudioFiles.User;

public class RemoveAnnouncementCommand {
	private String name;
	private String message;

	public RemoveAnnouncementCommand() {
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMessage() {
		return this.message;
	}

	public void removeAnnouncement(User user) {
		// check if the user is a host
		if(!user.getType().equals("host")) {
			message = user.getUsername() + " is not a host.";
			return;
		}

		// check if the announcement exists
		boolean found = false;
		for (Announcement announcement : ((Host) user).getAnnouncements()) {
			if (announcement.getName().equals(this.name)) {
				found = true;
				break;
			}
		}

		if (!found) {
			message = user.getUsername() + " has no announcement with the given name.";
			return;
		}

		// remove the announcement
		((Host) user).getAnnouncements().removeIf(announcement -> announcement.getName().equals(this.name));

		// set the host page
		((Host) user).setHostPage();

		// set the message
		message = user.getUsername() + " has successfully deleted the announcement.";
	}
}
