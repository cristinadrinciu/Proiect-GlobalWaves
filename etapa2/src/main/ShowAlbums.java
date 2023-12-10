package main;

import AudioFiles.Album;
import AudioFiles.Artist;
import AudioFiles.Library;

import java.util.ArrayList;

public class ShowAlbums {
	private String username;
	private ArrayList<Album> albums;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public ArrayList<Album> getAlbums() {
		return albums;
	}

	public void setAlbums(Library library) {
		// find the user
		for (int i = 0; i < library.getUsers().size(); i++) {
			if (library.getUsers().get(i).getUsername().equals(this.username)) {
				this.albums = ((Artist)(library.getUsers().get(i))).getAlbums();
				return;
			}
		}
	}
}
