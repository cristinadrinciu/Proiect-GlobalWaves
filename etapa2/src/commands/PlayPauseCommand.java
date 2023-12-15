package commands;

import audio.files.Library;
import main.InputCommands;
import player.Player;
import visit.pattern.Visitable;
import visit.pattern.Visitor;

public class PlayPauseCommand implements Visitable {
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

    @Override
    public void accept(InputCommands command, final Visitor visitor, Library library) {
        visitor.visit(command, this, library);
    }
}
