package commands;

import audio.files.Library;
import audio.files.Podcast;
import main.InputCommands;
import visit.pattern.Visitable;
import visit.pattern.Visitor;
import user.types.User;
import fileio.input.EpisodeInput;


public class ForwardCommand implements Visitable {
    private String message;

    public ForwardCommand() {
    }

    /**
     * @return the message
     */
    public String getMessage() {
        return message;
    }

    /**
     * @param message the new message
     */
    public void setMessage(final String message) {
        this.message = message;
    }

    /**
     * @param user the user that wants to forward
     */
    public void forwardPodcast(final User user) {
        if (user.getPlayer().loadedItem == null) {
            message = "Please load a source before attempting to forward.";
            return;
        }
        if (!(user.getPlayer().loadedItem instanceof Podcast)) {
            message = "The loaded source is not a podcast.";
            return;
        }

        final int forwardTime = 90;
        // otherwise, we skip forward 90 seconds
        user.getPlayer().listenedTime += forwardTime;

        // check if we finished the episode
        if (user.getPlayer().listenedTime >= ((EpisodeInput) user.getPlayer().playingNow).
                getDuration()) {
            // check if we finished the podcast
            if (user.getPlayer().playingNow == ((Podcast) user.getPlayer().loadedItem).
                    getEpisodes().get(((Podcast) user.getPlayer().loadedItem).
                            getEpisodes().size() - 1)) {
                // we finished the podcast
                user.getPlayer().loadedItem = null;
                user.getPlayer().playingNow = null;
                user.getPlayer().listenedTime = 0;
                user.getPlayer().switchedTime = user.getPlayer().timestamp;
                user.getPlayer().remainingTime = 0;
                user.getPlayer().paused = true;
                message = "Skipped forward successfully.";
            } else {
                // we finished the episode
                user.getPlayer().playingNow = ((Podcast) user.getPlayer().loadedItem).getEpisodes().
                        get(((Podcast) user.getPlayer().loadedItem).getEpisodes().
                        indexOf((EpisodeInput) user.getPlayer().playingNow) + 1);
                user.getPlayer().listenedTime = 0;
                user.getPlayer().remainingTime = ((EpisodeInput) user.getPlayer().
                        playingNow).getDuration();
                user.getPlayer().switchedTime = user.getPlayer().timestamp;
                message = "Skipped forward successfully.";
            }
        } else {
            // we didn't finish the episode
            user.getPlayer().remainingTime -= forwardTime;
            user.getPlayer().switchedTime = user.getPlayer().timestamp;
            message = "Skipped forward successfully.";
        }
    }

    @Override
    public void accept(final InputCommands command, final Visitor visitor, final Library library) {
        visitor.visit(command, this, library);
    }
}
