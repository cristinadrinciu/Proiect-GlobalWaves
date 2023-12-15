package commands;

import audio.files.Library;
import main.InputCommands;
import visit.pattern.Visitable;
import visit.pattern.Visitor;
import pages.Page;

public class PrintCurrentPageCommand implements Visitable {
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

	@Override
	public void accept(InputCommands command, Visitor visitor, Library library) {
		visitor.visit(command, this, library);
	}
}
