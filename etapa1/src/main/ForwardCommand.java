package main;

import fileio.input.EpisodeInput;


public class ForwardCommand {
	String message;

	public ForwardCommand() {
	}

	public void forwardPodcast (User user) {
		if(user.player.loadedItem == null) {
			message = "Please load a source before attempting to forward.";
			return;
		}
		if(!(user.player.loadedItem instanceof Podcast)) {
			message = "The loaded source is not a podcast.";
			return;
		}

		// otherwise, we skip forward 90 seconds
		user.player.listenedTime += 90;

		// check if we finished the episode
		if(user.player.listenedTime >= ((EpisodeInput) user.player.playingNow).getDuration()) {
			// check if we finished the podcast
			if(user.player.playingNow == ((Podcast) user.player.loadedItem).getEpisodes().get(((Podcast) user.player.loadedItem).getEpisodes().size() - 1)) {
				// we finished the podcast
				user.player.loadedItem = null;
				user.player.playingNow = null;
				user.player.listenedTime = 0;
				user.player.switchedTime = user.player.timestamp;
				user.player.remainingTime = 0;
				user.player.paused = true;
				message = "Skipped forward successfully.";
			} else {
				// we finished the episode
				user.player.playingNow = ((Podcast) user.player.loadedItem).getEpisodes().get(((Podcast) user.player.loadedItem).getEpisodes().indexOf((EpisodeInput) user.player.playingNow) + 1);
				user.player.listenedTime = 0;
				user.player.remainingTime = ((EpisodeInput) user.player.playingNow).getDuration();
				user.player.switchedTime = user.player.timestamp;
				message = "Skipped forward successfully.";
			}
		} else {
			// we didn't finish the episode
			user.player.remainingTime -= 90;
			user.player.switchedTime = user.player.timestamp;
			message = "Skipped forward successfully.";
		}
	}
}
