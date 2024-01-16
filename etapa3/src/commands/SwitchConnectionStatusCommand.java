package commands;

import audioFiles.Library;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import designPatterns.commandPattern.Command;
import main.InputCommands;
import platformData.OnlineUsers;
import users.User;

public class SwitchConnectionStatusCommand implements Command {
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
     * Executes the command.
     * @param command the input command
     * @param library the main library
     */
    @Override
    public void execute(final InputCommands command, final Library library) {
        User user = command.getUser();
        if (user.getStatusOnline()) {
            if (user.getPlayer().repeatState == 0) {
                user.getPlayer().setRemainingTime();
            } else if (user.getPlayer().repeatState == 1) {
                user.getPlayer().setRemainingTimeRepeat1();
            } else if (user.getPlayer().repeatState == 2) {
                user.getPlayer().setRemainingTimeRepeat2();
            }
        }

        switchConnectionStatus(user, command.getTimestamp());

        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode commandJson = objectMapper.createObjectNode()
                .put("command", "switchConnectionStatus")
                .put("user", command.getUsername())
                .put("timestamp", command.getTimestamp())
                .put("message", message);

        command.getCommandList().add(commandJson);
    }
}
