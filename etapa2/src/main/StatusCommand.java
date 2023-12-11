package main;

import AudioFiles.Playlist;
import AudioFiles.Podcast;
import AudioFiles.Song;

import java.util.ArrayList;

public class StatusCommand {
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
        if (player.repeatState == 1 && (player.loadedItem instanceof Song
                || player.loadedItem instanceof Podcast)) {
            message = "Repeat Once";
        }
        if (player.repeatState == 2 && (player.loadedItem instanceof Song
                || player.loadedItem instanceof Podcast)) {
            message = "Repeat Infinite";
        }
        if (player.repeatState == 1 && (player.loadedItem instanceof Playlist)) {
            message = "Repeat All";
        }
        if (player.repeatState == 2 && (player.loadedItem instanceof Playlist)) {
            message = "Repeat Current Song";
        }

        return message;
    }
}
