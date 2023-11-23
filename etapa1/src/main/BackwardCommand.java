package main;

import fileio.input.UserInput;
import fileio.input.EpisodeInput;

public class BackwardCommand {
	String message;

	public BackwardCommand() {
	}

	public void backwardPodcast (User user) {
		if(user.player.loadedItem == null) {
			message = "Please load a source before skipping backward.";
			return;
		}

		if(!(user.player.loadedItem instanceof Podcast)) {
			message = "The loaded source is not a podcast.";
			return;
		}

		// otherwise, we skip backward 90 seconds
		user.player.listenedTime -= 90;

		// check if we are at the beginning of the episode
		if(user.player.listenedTime < 0) {
			// is is at the beginning of the podcast, so restart the episode
			user.player.remainingTime = ((EpisodeInput) user.player.playingNow).getDuration();
			user.player.listenedTime = 0;
			user.player.switchedTime = user.player.timestamp;
			message = "Rewound successfully..";
		} else {
			// we didn't finish the episode
			user.player.remainingTime += 90;
			user.player.switchedTime = user.player.timestamp;
			message = "Rewound successfully.";
		}
	}
}
