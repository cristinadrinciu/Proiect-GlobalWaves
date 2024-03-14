package commands;

import stream.JsonOutputStream;
import audiofiles.Library;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import designpatterns.commandPattern.Command;
import main.InputCommands;
import users.User;


public class LoadRecommendationCommand implements Command {
    private String message;

    public LoadRecommendationCommand() {
    }

    /**
     * @return the message
     */
    public String getMessage() {
        return message;
    }

    /**
     * Loads the recommendation of the user
     * @param user the user that wants to load the recommendation
     */
    public void loadRecommendation(final User user) {
        if (user.getLastRecommendation() == null) {
            this.message = "No recommendations available.";
            return;
        }

        if (!user.getStatusOnline()) {
            this.message = user.getUsername() + " is not offline.";
            return;
        }

        // load the last recommendation
        user.getPlayer().loadedItem = user.getLastRecommendation();
        user.getPlayer().setPlayingNow();
        user.getPlayer().shuffle = false;
        user.getPlayer().repeatState = 0;

        // update the listens
        if (user.getPlayer().loadedItem != null) {
            if (user.getPlayer().loadedItem.getType().equals("song")
                    || user.getPlayer().loadedItem.getType().equals("playlist")) {
                user.getPlayer().updateStatistics();
            }
        }

        message = "Playback loaded successfully.";
    }

    /**
     * Execute Load the recommendation command
     * @param command the input command
     * @param library the main library
     */
    @Override
    public void execute(final InputCommands command, final Library library) {
        User user = command.getUser();
        loadRecommendation(user);

        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode commandJson = objectMapper.createObjectNode()
                .put("command", "loadRecommendations")
                .put("user", command.getUsername())
                .put("timestamp", command.getTimestamp())
                .put("message", message);

        JsonOutputStream.getCommandOutputs().add(commandJson);
    }
}
