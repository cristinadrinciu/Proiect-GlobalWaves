package commands;

import audio.files.Library;
import audio.files.Podcast;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import main.InputCommands;
import visit.pattern.Visitable;
import visit.pattern.Visitor;
import user.types.User;
import fileio.input.EpisodeInput;

public class BackwardCommand implements Command {
    private String message;

    public BackwardCommand() {
    }

    /**
     * This method is used to skip backward 90 seconds in the current podcast.
     * @param user the user that is currently logged in
     */
    public void backwardPodcast(final User user) {
        if (user.getPlayer().loadedItem == null) {
            message = "Please load a source before skipping backward.";
            return;
        }

        if (!(user.getPlayer().loadedItem instanceof Podcast)) {
            message = "The loaded source is not a podcast.";
            return;
        }
        final int backwardTime = 90;
        // otherwise, we skip backward 90 seconds
        user.getPlayer().listenedTime -= backwardTime;

        // check if we are at the beginning of the episode
        if (user.getPlayer().listenedTime < 0) {
            // is is at the beginning of the podcast, so restart the episode
            user.getPlayer().remainingTime = ((EpisodeInput) user.getPlayer().
                    playingNow).getDuration();
            user.getPlayer().listenedTime = 0;
            user.getPlayer().switchedTime = user.getPlayer().timestamp;
            message = "Rewound successfully..";
        } else {
            // we didn't finish the episode
            user.getPlayer().remainingTime += backwardTime;
            user.getPlayer().switchedTime = user.getPlayer().timestamp;
            message = "Rewound successfully.";
        }
    }

    /**
     *
     * @return the message that is displayed after the command is executed
     */
    public String getMessage() {
        return message;
    }

    /**
     *
     * @param message the message that is displayed after the command is executed
     */
    public void setMessage(final String message) {
        this.message = message;
    }

    /**
     * This method is used to execute the backward command.
     * @param command the input command
     * @param library the main library
     */
    @Override
    public void execute(final InputCommands command, final Library library) {
        User user = command.getUser();

        if (user.getPlayer().loadedItem != null) {
            if (user.getPlayer().repeatState == 0) {
                user.getPlayer().setRemainingTime();
            }
            if (user.getPlayer().repeatState == 1) {
                user.getPlayer().setRemainingTimeRepeat1();
            }
            if (user.getPlayer().repeatState == 2) {
                user.getPlayer().setRemainingTimeRepeat2();
            }
        }

        backwardPodcast(user);

        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode commandJson = objectMapper.createObjectNode()
                .put("command", "backward")
                .put("user", command.getUsername())
                .put("timestamp", command.getTimestamp())
                .put("message", message);

        command.getCommandList().add(commandJson);
    }
}
