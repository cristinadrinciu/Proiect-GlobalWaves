package commands;


import stream.JsonOutputStream;
import audiofiles.Library;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import designpatterns.commandPattern.Command;
import main.InputCommands;
import users.User;

public class RepeatCommand implements Command {
    /**
     * @param user the user that wants to change the repeat mode
     */
    public void setRepeatMode(final User user) {
        if (user.getPlayer().repeatState == 0) {
            user.getPlayer().repeatState = 1;
            user.getPlayer().repeatedOnce = 0;
        } else if (user.getPlayer().repeatState == 1) {
            user.getPlayer().repeatState = 2;
        } else if (user.getPlayer().repeatState == 2) {
            user.getPlayer().repeatState = 0;
        }
    }

    /**
     * @param user the user that wants to change the repeat mode
     * @return the message that will be displayed
     */
    public String message(final User user) {
        String message;
        if (user.getPlayer().loadedItem == null) {
            message = "Please load a source before setting the repeat status.";
            return message;
        }

        switch (user.getPlayer().repeatState) {
            case 0:
                message = "Repeat mode changed to no repeat.";
                break;
            case 1:
                if (user.getPlayer().loadedItem.getType().equals("song")
                        || user.getPlayer().loadedItem.getType().equals("podcast")) {
                    message = "Repeat mode changed to repeat once.";
                } else {
                    message = "Repeat mode changed to repeat all.";
                }
                break;
            case 2:
                if (user.getPlayer().loadedItem.getType().equals("song")
                        || user.getPlayer().loadedItem.getType().equals("podcast")) {
                    message = "Repeat mode changed to repeat infinite.";
                } else {
                    message = "Repeat mode changed to repeat current song.";
                }
                break;
            default:
                message = "Invalid repeat state.";
        }

        return message;
    }

    /**
     * Execute the command.
     * @param command the input command
     * @param library the main library
     */
    @Override
    public void execute(final InputCommands command, final Library library) {
        User user = command.getUser();
        String message;
        if (user.getPlayer().loadedItem == null) {
            message = "Please load a source before setting the repeat status.";
        } else {
            if (user.getPlayer().repeatState == 0) {
                user.getPlayer().setRemainingTime();
            }
            if (user.getPlayer().repeatState == 1) {
                user.getPlayer().setRemainingTimeRepeat1();
            }
            if (user.getPlayer().repeatState == 2) {
                user.getPlayer().setRemainingTimeRepeat2();
            }
            setRepeatMode(user);
            message = message(user);
        }

        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode commandJson = objectMapper.createObjectNode()
                .put("command", "repeat")
                .put("user", command.getUsername())
                .put("timestamp", command.getTimestamp())
                .put("message", message);

        // add the command to the list
        JsonOutputStream.getCommandOutputs().add(commandJson);
    }
}
