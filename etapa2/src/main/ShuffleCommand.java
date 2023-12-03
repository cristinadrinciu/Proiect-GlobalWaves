package main;

import AudioFiles.Playlist;
import AudioFiles.Song;
import AudioFiles.User;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class ShuffleCommand {

    private int seed;

    public ShuffleCommand() {
    }

    /**
     * @return the seed
     */
    public int getSeed() {
        return seed;
    }

    /**
     * @param seed the seed to set
     */
    public void setSeed(final int seed) {
        this.seed = seed;
    }

    /**
     * @param user the user that wants to shuffle
     */
    public void shufflePlayer(final User user) {
        if (user.getPlayer().loadedItem == null) {
            user.getPlayer().shuffle = false;
            return;
        }
        if (!(user.getPlayer().loadedItem instanceof Playlist)) {
            user.getPlayer().shuffle = false;
            return;
        }

        // Deactivate shuffle if no seed is provided
        if (seed == 0 || user.getPlayer().shuffle) {
            user.getPlayer().shuffle = false;
        } else {
            // Activate shuffle if a seed is provided
            user.getPlayer().shuffle = true;
        }
    }

    /**
     * @param user the user that wants to shuffle
     * @return the message to be displayed
     */
    public String message(final User user) {
        String message = null;
        if (user.getPlayer().loadedItem == null) {
            message = "Please load a source before using the shuffle function.";
            return message;
        }
        if (!(user.getPlayer().loadedItem instanceof Playlist)) {
            message = "The loaded source is not a playlist.";
            return message;
        }
        if (user.getPlayer().shuffle) {
            message = "Shuffle function activated successfully.";
        } else {
            message = "Shuffle function deactivated successfully.";
        }

        return message;
    }

    /**
     * @param playlist the playlist to be shuffled
     * @return the shuffled playlist
     */
    public ArrayList<Song> shufflePlaylist(final Playlist playlist) {
        ArrayList<Song> shuffledPlaylist = new ArrayList<>(playlist.getSongs());

        Collections.shuffle(shuffledPlaylist, new Random(seed));

        return shuffledPlaylist;
    }
}
