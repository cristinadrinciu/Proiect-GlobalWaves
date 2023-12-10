package main;

import AudioFiles.Artist;
import AudioFiles.Library;
import AudioFiles.Page;
import AudioFiles.User;

public class PrintCurrentPageCommand {
	private String username;
	private Page page;

	public PrintCurrentPageCommand() {
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setCurrentPage(Library library) {
		// find the user
		for (int i = 0; i < library.getUsers().size(); i++) {
			if (library.getUsers().get(i).getUsername().equals(this.username)) {
				this.page = library.getUsers().get(i).getCurrentPage();
				return;
			}
		}
	}

	public Page getCurrentPage() {
		return this.page;
	}
}
