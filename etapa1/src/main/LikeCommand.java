package main;

import fileio.input.SongInput;
import fileio.input.UserInput;

public class LikeCommand {

	private String message;

	public String getMessage() {
		return message;
	}

	public void setMessage(UserInput user) {
		if(user.player.loadedItem == null) {
			message = "Please load a source before liking or unliking.";
			return;
		}
		if(!(user.player.playingNow instanceof SongInput)) {
			message = "Loaded source is not a song.";
			return;
		}

		boolean isLiked = false;

		// see if it is in the Liked Songs list
		for(SongInput song : user.getLikedSongs())
			if(song == user.player.playingNow)
				isLiked = true;

		if(isLiked) {
			// remove from the liked list
			user.getLikedSongs().remove(user.player.playingNow);
			message = "Unlike registered successfully.";
		} else {
			// ad to the liked list
			user.getLikedSongs().add((SongInput) user.player.playingNow);
			message = "Like registered successfully.";
		}
	}
}
