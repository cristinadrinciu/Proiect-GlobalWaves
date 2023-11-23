package main;

import fileio.input.AudioFile;

public class PlayPauseCommand {
	private String message;
	public PlayPauseCommand(){
	}

	public String buildMessage(Player player) {
		if(player.loadedItem != null) {
			if(!player.paused) {
				message = "Playback resumed successfully.";
			} else
				message = "Playback paused successfully.";
		} else
			message = "Please load a source before attempting to pause or resume playback.";
		return message;
	}
}
