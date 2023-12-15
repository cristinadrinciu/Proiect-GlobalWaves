package commands;

import main.InputCommands;
import visit.pattern.Visitable;
import visit.pattern.Visitor;
import user.types.Host;
import audio.files.Library;
import audio.files.Podcast;

import java.util.ArrayList;

public class ShowPodcasts implements Visitable {
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

	@Override
	public void accept(InputCommands command, Visitor visitor, Library library) {
		visitor.visit(command, this, library);
	}
}
