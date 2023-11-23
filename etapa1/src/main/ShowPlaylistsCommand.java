package main;

import fileio.input.UserInput;

import java.util.ArrayList;

public class ShowPlaylistsCommand {
	private ArrayList<Playlist> playlists;

	public ArrayList<Playlist> getPlaylists() {
		return playlists;
	}

	public void setPlaylists(UserInput user) {
		this.playlists = user.getPlaylists();
	}
}
