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

	public void switchConnectionStatus(User user, int timestamp) {
		if(!user.getType().equals("user")) {
			message = user.getUsername() + " is not a normal user.";
			return;
		}

		if (user.getStatusOnline()) {
			user.setStatusOnline(false);

			// remove the user from the online users list
			OnlineUsers.getOnlineUsers().remove(user);
			user.getPlayer().timestamp = timestamp;
			user.getPlayer().switchedTime = user.getPlayer().timestamp;
			message = user.getUsername() + " has changed status successfully.";
		} else {
			user.setStatusOnline(true);

			// add the user to the online users list
			OnlineUsers.getOnlineUsers().add(user);
			user.getPlayer().timestamp = timestamp;
			user.getPlayer().switchedTime = user.getPlayer().timestamp;
			message = user.getUsername() + " has changed status successfully.";
		}
	}
}
