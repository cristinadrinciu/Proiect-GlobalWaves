package commands;

import audio.files.Album;
import main.InputCommands;
import visit.pattern.Visitable;
import visit.pattern.Visitor;
import user.types.Artist;
import audio.files.Library;

import java.util.ArrayList;

public class ShowAlbums implements Visitable {
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

	@Override
	public void accept(InputCommands command, Visitor visitor, Library library) {
		visitor.visit(command, this, library);
	}
}
