package main;

import main.Player;

public class PlayPauseCommand {
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
}
