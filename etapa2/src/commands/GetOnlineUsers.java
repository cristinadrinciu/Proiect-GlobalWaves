package commands;

import audio.files.Library;
import main.InputCommands;
import publicFiles.OnlineUsers;
import visit.pattern.Visitable;
import visit.pattern.Visitor;
import user.types.User;

import java.util.ArrayList;

public class GetOnlineUsers implements Visitable {
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

	@Override
	public void accept(InputCommands command, Visitor visitor, Library library) {
		visitor.visit(command, this, library);
	}
}
