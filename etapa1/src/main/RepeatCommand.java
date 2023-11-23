package main;


public class RepeatCommand {
	public void setRepeatMode(User user) {
		if (user.player.repeatState == 0) {
			user.player.repeatState = 1;
			user.player.repeatedOnce = 0;
		} else if (user.player.repeatState == 1) {
			user.player.repeatState = 2;
		} else if (user.player.repeatState == 2) {
			user.player.repeatState = 0;
		}
	}

	public String message(User user) {
		String message;

		switch (user.player.repeatState) {
			case 0:
				message = "Repeat mode changed to no repeat.";
				break;
			case 1:
				if(user.player.loadedItem instanceof Song || user.player.loadedItem instanceof Podcast)
					message = "Repeat mode changed to repeat once.";
				else
					message = "Repeat mode changed to repeat all.";
				break;
			case 2:
				if(user.player.loadedItem instanceof Song || user.player.loadedItem instanceof Podcast)
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
