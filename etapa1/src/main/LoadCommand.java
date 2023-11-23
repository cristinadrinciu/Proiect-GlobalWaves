package main;

import fileio.input.AudioFile;
import fileio.input.PodcastInput;
import fileio.input.SongInput;

public class LoadCommand {
	static AudioFile loadedItem;
	private String resultMessage;

	public LoadCommand() {
	}

	public String getResultMessage() {
		return resultMessage;
	}

	public String buildMessage (AudioFile audiofile) {
		if (audiofile != null) {
			if (audiofile instanceof PodcastInput)
				if (((PodcastInput) audiofile).getEpisodes().isEmpty()) {
					return resultMessage = "You can't load an empty audio collection!";
				}
			if (audiofile instanceof Playlist)
				if (((Playlist) audiofile).songs.isEmpty())
					return resultMessage = "You can't load an empty audio collection!";
			loadedItem = audiofile;
			resultMessage = "Playback loaded successfully.";
		} else
			resultMessage = "Please select a source before attempting to load.";

		return resultMessage;
	}
}
