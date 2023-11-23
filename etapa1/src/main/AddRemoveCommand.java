package main;

import fileio.input.SongInput;
import fileio.input.UserInput;

public class AddRemoveCommand {
	public int getPlaylistId() {
		return playlistId;
	}

	public void setPlaylistId(int playlistId) {
		this.playlistId = playlistId;
	}

	private int playlistId;

	public AddRemoveCommand() {
	}

	public String addRemoveMessage(UserInput user) {
		String message;
		if(user.player.loadedItem == null) {
			message = "Please load a source before adding to or removing from the playlist.";
			return message;
		}
		if(!(user.player.loadedItem instanceof SongInput)) {
			message = "The loaded source is not a song.";
			return message;
		}
		if(user.getPlaylists() == null) {
			message = "The specified playlist does not exist.";
			return message;
		}
		if(playlistId > user.getPlaylists().size() || playlistId < 0) {
			message = "The specified playlist does not exist.";
			return message;
		}

		// search if the song is in the playlist
		boolean isInPlaylist = false;
		for(SongInput song : user.getPlaylists().get(playlistId - 1).songs)
			if(song == user.player.loadedItem)
				isInPlaylist = true;
		if(isInPlaylist) {
			// remove the song from the playlist
			user.getPlaylists().get(playlistId - 1).songs.remove(user.player.loadedItem);
			message = "Successfully removed from playlist.";
		} else {
			// add in the playlist
			user.getPlaylists().get(playlistId - 1).songs.add((SongInput) user.player.loadedItem);
			message = "Successfully added to playlist.";
		}

		return message;
	}
}
