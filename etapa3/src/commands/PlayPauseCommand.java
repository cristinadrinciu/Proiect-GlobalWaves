package commands;

import audio.files.Library;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import main.InputCommands;
import player.Player;
import user.types.User;
import visit.pattern.Visitable;
import visit.pattern.Visitor;

public class PlayPauseCommand implements Command {
    private String message;
    public PlayPauseCommand() {
    }

    /**
     * @param player the player to be paused or resumed
     */
    public String buildMessage(final Player player) {
        if (player.loadedItem != null) {
            if (!player.paused) {
                message = "Playback resumed successfully.";
            } else {
                message = "Playback paused successfully.";
            }
        } else {
            message = "Please load a source before attempting to pause or resume playback.";
        }
        return message;
    }

    /**
     * Executes the command
     * @param command the input command
     * @param library the main library
     */
    @Override
    public void execute(final InputCommands command, final Library library) {
        User user = command.getUser();

        if (user.getPlayer().loadedItem != null) {
            // Calculate remaining time before making any state changes
            if (user.getPlayer().repeatState == 0) {
                user.getPlayer().setRemainingTime();
            } else if (user.getPlayer().repeatState == 1) {
                user.getPlayer().setRemainingTimeRepeat1();
            } else if (user.getPlayer().repeatState == 2) {
                user.getPlayer().setRemainingTimeRepeat2();
            }

            if (!user.getPlayer().paused) {
                // If currently playing, pause the playback
                user.getPlayer().paused = true;
                user.getPlayer().switchedTime = command.getTimestamp();
            } else {
                // If currently paused, resume the playback
                user.getPlayer().paused = false;
                user.getPlayer().switchedTime = command.getTimestamp();
            }
        }

        // Build the message and generate the corresponding JSON representation
        String message = buildMessage(user.getPlayer());
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode commandJson = objectMapper.createObjectNode()
                .put("command", "playPause")
                .put("user", command.getUsername())
                .put("timestamp", command.getTimestamp())
                .put("message", message);

        command.getCommandList().add(commandJson);
    }
}
