package main;

import AudioFiles.User;

public class SwitchConnectionStatusCommand {
	private String message;

	public SwitchConnectionStatusCommand() {
	}

	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	public void switchConnectionStatus(User user) {
		if (user.getStatusOnline()) {
			user.setStatusOnline(false);

			// remove the user from the online users list
			OnlineUsers.getOnlineUsers().remove(user);
			message = user.getUsername() + " has changed status successfully.";
		} else {
			user.setStatusOnline(true);

			// add the user to the online users list
			OnlineUsers.getOnlineUsers().add(user);
			message = user.getUsername() + " has changed status successfully.";
		}
	}
}
