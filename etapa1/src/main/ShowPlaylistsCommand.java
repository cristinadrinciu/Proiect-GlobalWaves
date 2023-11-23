package main;

import java.util.ArrayList;

public class ShowPlaylistsCommand {
	private ArrayList<Playlist> playlists;

	public ArrayList<Playlist> getPlaylists() {
		return playlists;
	}

	public void setPlaylists(User user) {
		this.playlists = user.getPlaylists();
	}
}
