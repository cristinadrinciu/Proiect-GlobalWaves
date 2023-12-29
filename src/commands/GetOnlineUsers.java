package commands;

import audio.files.Library;
import main.InputCommands;
import platform.data.OnlineUsers;
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

    /**
     * This method sets the onlineUsersNames list with the usernames of the online users.
     */
    public void setOnlineUsersNames() {
        onlineUsersNames = new ArrayList<>();
        for (User user : OnlineUsers.getOnlineUsers()) {
            onlineUsersNames.add(user.getUsername());
        }

        // sort the list by name of the users
        onlineUsersNames.sort(String::compareToIgnoreCase);
    }

    /**
     * Accept method for the visitor.
     * @param command the command to be executed
     * @param visitor the visitor
     * @param library the library
     */
    @Override
    public void accept(final InputCommands command, final Visitor visitor, final Library library) {
        visitor.visit(command, this, library);
    }
}
