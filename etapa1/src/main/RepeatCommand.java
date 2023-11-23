package main;

import fileio.input.PodcastInput;
import fileio.input.SongInput;
import fileio.input.UserInput;

public class RepeatCommand {
	public void setRepeatMode(UserInput user) {
		if (user.player.repeatState == 0) {
			user.player.repeatState = 1;
			user.player.repeatedOnce = 0;
		} else if (user.player.repeatState == 1) {
			user.player.repeatState = 2;
		} else if (user.player.repeatState == 2) {
			user.player.repeatState = 0;
		}
	}

	public String message(UserInput user) {
		String message;

		switch (user.player.repeatState) {
			case 0:
				message = "Repeat mode changed to no repeat.";
				break;
			case 1:
				if(user.player.loadedItem instanceof SongInput || user.player.loadedItem instanceof PodcastInput)
					message = "Repeat mode changed to repeat once.";
				else
					message = "Repeat mode changed to repeat all.";
				break;
			case 2:
				if(user.player.loadedItem instanceof SongInput || user.player.loadedItem instanceof PodcastInput)
					message = "Repeat mode changed to repeat infinite.";
				else
					message = "Repeat mode changed to repeat current song.";
				break;
			default:
				message = "Invalid repeat state.";
		}

		return message;
	}
}
