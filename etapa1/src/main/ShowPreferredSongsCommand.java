package main;

import java.util.ArrayList;

public class ShowPreferredSongsCommand {
	private ArrayList<Song> preferredSongs = new ArrayList<>();

	public ShowPreferredSongsCommand() {
	}

	public ArrayList<Song> getPreferredSongs() {
		return preferredSongs;
	}

	public void setPreferredSongs(User user) {
		this.preferredSongs = user.getLikedSongs();
	}
}
