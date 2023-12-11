package main;

import AudioFiles.User;

public class ChangePageCommand {
	private String nextPage;
	private String message;

	public ChangePageCommand() {
	}

	public String getNextPage() {
		return nextPage;
	}

	public void setNextPage(String nextPage) {
		this.nextPage = nextPage;
	}

	public String getMessage() {
		return this.message;
	}

	public void changePage(User user) {
		switch (this.nextPage) {
			case "Home": {
				user.setCurrentPage(user.getHomePage());
				this.message = user.getUsername() + " accessed " + this.nextPage + " successfully.";
				break;
			}
			case "LikedContent": {
				user.setCurrentPage(user.getLikedContentPage());
				this.message = user.getUsername() + " accessed " + this.nextPage + " successfully.";
				break;
			}
			default:
				this.message = user.getUsername() + " is trying to access a non-existent page.\n";
				break;
		}
	}
}