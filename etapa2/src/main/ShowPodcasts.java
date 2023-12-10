package main;

import AudioFiles.Host;
import AudioFiles.Library;
import AudioFiles.Podcast;

import java.util.ArrayList;

public class ShowPodcasts {
	private String username;
	private ArrayList<Podcast> podcasts;

	public ShowPodcasts() {
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public ArrayList<Podcast> getPodcasts() {
		return podcasts;
	}

	public void setPodcasts(Library library) {
		// find the user
		for (int i = 0; i < library.getUsers().size(); i++) {
			if (library.getUsers().get(i).getUsername().equals(this.username)) {
				this.podcasts = ((Host) (library.getUsers().get(i))).getPodcasts();
				return;
			}
		}
	}
}
