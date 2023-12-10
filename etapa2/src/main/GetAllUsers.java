package main;

import AudioFiles.Library;
import AudioFiles.User;

import java.util.ArrayList;

public class GetAllUsers {
	private ArrayList<String> allUsersNames = new ArrayList<>();

	public GetAllUsers() {
	}

	/**
	 * @return the allUsersNames
	 */
	public ArrayList<String> getAllUsersNames() {
		return allUsersNames;
	}

	public void setAllUsersNames(Library library) {
		allUsersNames = new ArrayList<>();
		// order: normal users, artists, hosts
		for (User user : library.getUsers()) {
			if (user.getType().equals("user")) {
				allUsersNames.add(user.getUsername());
			}
		}
		for (User user : library.getUsers()) {
			if (user.getType().equals("artist")) {
				allUsersNames.add(user.getUsername());
			}
		}
		for (User user : library.getUsers()) {
			if (user.getType().equals("host")) {
				allUsersNames.add(user.getUsername());
			}
		}
	}
}
