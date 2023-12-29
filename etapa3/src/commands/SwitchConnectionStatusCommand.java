package commands;

import audio.files.Library;
import main.InputCommands;
import visit.pattern.Visitable;
import visit.pattern.Visitor;
import platform.data.OnlineUsers;
import user.types.User;

public class SwitchConnectionStatusCommand implements Visitable {
    private String message;

    public SwitchConnectionStatusCommand() {
    }

    /**
     * @return the message
     */
    public String getMessage() {
        return message;
    }

    /**
     * Switches the connection status of a user.
     * @param user the user to switch the connection status
     * @param timestamp the timestamp of the command
     */
    public void switchConnectionStatus(final User user, final int timestamp) {
        if (!user.getType().equals("user")) {
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

    /**
     * Accepts the command.
     * @param command the command to accept
     * @param visitor the visitor that visits the command
     * @param library the library that contains the users
     */
    @Override
    public void accept(final InputCommands command, final Visitor visitor, final Library library) {
        visitor.visit(command, this, library);
    }
}
