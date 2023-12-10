package main;

import AudioFiles.Host;
import AudioFiles.Library;
import AudioFiles.Podcast;
import AudioFiles.User;

public class RemovePodcastCommand {
	private String name;
	private String message;

	public RemovePodcastCommand() {
	}

	public String getName() {
		return name;
	}

	public String getMessage() {
		return message;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void removePodcast(User user, Library library) {
		// check if the user is a host
		if(!user.getType().equals("host")) {
			message = user.getUsername() + " is not a host.";
			return;
		}

		// check if the podcast exists
		boolean found = false;
		for (AudioFiles.Podcast podcast : ((AudioFiles.Host) user).getPodcasts()) {
			if (podcast.getName().equals(this.name)) {
				found = true;
				break;
			}
		}

		if (!found) {
			message = user.getUsername() + " doesn't have a podcast with the given name.";
			return;
		}

		// get the podcast
		Podcast deletePodcast = new Podcast();
		for (Podcast podcast : ((Host) user).getPodcasts()) {
			if (podcast.getName().equals(this.name)) {
				deletePodcast = podcast;
				break;
			}
		}

		// check if the podcast can be removed
		if(!canBeDeleted.canDeletePodcast(deletePodcast, library)) {
			message = user.getUsername() + " can't delete this podcast.";
			return;
		}

		// delete the podcast
		((Host) user).getPodcasts().removeIf(podcast -> podcast.getName().equals(this.name));

		// remove the podcast from the platform
		library.getPodcasts().removeIf(podcast -> podcast.getName().equals(this.name));

		// set the host page
		((Host) user).setHostPage();

		// set the message
		message = user.getUsername() + " deleted the podcast successfully.";
	}

	private static class canBeDeleted {
		public static boolean canDeletePodcast(Podcast podcast, Library library) {
			// check if any user had loaded in the player the podcast
			for (User user : library.getUsers()) {
				if (user.getPlayer().loadedItem != null && user.getPlayer().loadedItem.equals(podcast)) {
					return false;
				}
			}
			return true;
		}
	}
}
