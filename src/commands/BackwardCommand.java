package commands;

import audio.files.Library;
import audio.files.Podcast;
import main.InputCommands;
import visit.pattern.Visitable;
import visit.pattern.Visitor;
import user.types.User;
import fileio.input.EpisodeInput;

public class BackwardCommand implements Visitable {
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
     * This method is used to accept the command and send it to the visitor.
     * @param command the command that is accepted
     * @param visitor the visitor that accepts the command
     * @param library the library that is used to execute the command
     */
    @Override
    public void accept(final InputCommands command, final Visitor visitor, final Library library) {
        visitor.visit(command, this, library);
    }
}
