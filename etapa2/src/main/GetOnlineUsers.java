package main;

import AudioFiles.User;

import java.util.ArrayList;

public class GetOnlineUsers {
	private ArrayList<String> onlineUsersNames = new ArrayList<>();

	public GetOnlineUsers() {
	}

	/**
	 * @return the onlineUsersNames
	 */
	public ArrayList<String> getOnlineUsersNames() {
		return onlineUsersNames;
	}

	public void setOnlineUsersNames() {
		onlineUsersNames = new ArrayList<>();
		for (User user : OnlineUsers.getOnlineUsers()) {
			onlineUsersNames.add(user.getUsername());
		}

		// sort the list by name of the users
		onlineUsersNames.sort(String::compareToIgnoreCase);
	}
}
