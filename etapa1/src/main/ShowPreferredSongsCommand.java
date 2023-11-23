package main;

import fileio.input.SongInput;
import fileio.input.UserInput;

import java.util.ArrayList;

public class ShowPreferredSongsCommand {
	private ArrayList<SongInput> preferredSongs = new ArrayList<>();

	public ShowPreferredSongsCommand() {
	}

	public ArrayList<SongInput> getPreferredSongs() {
		return preferredSongs;
	}

	public void setPreferredSongs(UserInput user) {
		this.preferredSongs = user.getLikedSongs();
	}
}
