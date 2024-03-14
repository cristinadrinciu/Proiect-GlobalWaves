package commands;

import stream.JsonOutputStream;
import audiofiles.Library;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import designpatterns.commandPattern.Command;
import main.InputCommands;
import player.Player;
import users.User;

import java.util.ArrayList;

public class StatusCommand implements Command {
    private ArrayList<Object> statusArray = new ArrayList<>();

    public StatusCommand() {
    }

    /**
     * Builds the status array for the given player
     * @param player the player for which the status array is built
     * @return the status array
     */
    public ArrayList<Object> buildStatusArray(final Player player) {
        // this array will have 5 elements: name, remainingTime, repeat, shuffle, paused
        String name;
        if (player.loadedItem == null) {
            name = "";
            player.paused = true;
        } else {
            name = player.playingNow.getName();
        }
        statusArray.add(name);
        statusArray.add(player.remainingTime);
        statusArray.add(getRepeatString(player));
        statusArray.add(player.shuffle);
        statusArray.add(player.paused);

        return statusArray;
    }

    /**
     * Returns the string corresponding to the repeat state of the player
     * @param player the player for which the string is returned
     * @return the string corresponding to the repeat state of the player
     */
    private String getRepeatString(final Player player) {
        String message = null;
        if (player.repeatState == 0 || player.loadedItem == null) {
            message = "No Repeat";
        }
        if (player.repeatState == 1 && (player.loadedItem.getType().equals("song")
                || player.loadedItem.getType().equals("podcast"))) {
            message = "Repeat Once";
        }
        if (player.repeatState == 2 && (player.loadedItem.getType().equals("song")
                || player.loadedItem.getType().equals("podcast"))) {
            message = "Repeat Infinite";
        }
        if (player.repeatState == 1 && (player.loadedItem.getType().equals("playlist"))) {
            message = "Repeat All";
        }
        if (player.repeatState == 2 && (player.loadedItem.getType().equals("playlist"))) {
            message = "Repeat Current Song";
        }

        return message;
    }

    /**
     * Executes the status command
     * @param command the input command
     * @param library the library on which the command is executed
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

        ArrayList<Object> statusArray1 = buildStatusArray(user.getPlayer());

        // Create the status JSON structure
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode commandJson = objectMapper.createObjectNode()
                .put("command", "status")
                .put("user", command.getUsername())
                .put("timestamp", command.getTimestamp());

        final int index1 = 1;
        final int index2 = 2;
        final int index3 = 3;
        final int index4 = 4;
        final int index0 = 0;

        // Create the "stats" object within the JSON structure
        ObjectNode statsNode = objectMapper.createObjectNode()
                .put("name", (String) statusArray1.get(index0))
                .put("remainedTime", (int) statusArray1.get(index1))
                .put("repeat", (String) statusArray1.get(index2))
                .put("shuffle", (boolean) statusArray1.get(index3))
                .put("paused", (boolean) statusArray1.get(index4));

        // Add the "stats" object to the main JSON structure
        commandJson.set("stats", statsNode);

        // Add the commandJson to the commandList
        JsonOutputStream.getCommandOutputs().add(commandJson);
    }
}
