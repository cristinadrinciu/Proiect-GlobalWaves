package commands;

import audio.files.Album;
import audio.files.Library;
import audio.files.Playlist;
import audio.files.Song;
import main.InputCommands;
import visit.pattern.Visitable;
import visit.pattern.Visitor;
import user.types.User;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class ShuffleCommand implements Visitable {

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
        if (!(user.getPlayer().loadedItem instanceof Playlist)
                && !(user.getPlayer().loadedItem instanceof Album)) {
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
        if (!(user.getPlayer().loadedItem instanceof Playlist)
                && !(user.getPlayer().loadedItem instanceof Album)) {
            message = "The loaded source is not a playlist or an album.";
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

    /**
     * @param album the album to be shuffled
     * @return the shuffled album
     */
    public ArrayList<Song> shuffleAlbum(final Album album) {
        ArrayList<Song> shuffledAlbum = new ArrayList<>(album.getSongs());

        Collections.shuffle(shuffledAlbum, new Random(seed));

        return shuffledAlbum;
    }

    /**
     * The accept method for the visitor pattern
     * @param command the command to be executed
     * @param visitor the visitor
     * @param library the library
     */
    @Override
    public void accept(final InputCommands command,
                       final Visitor visitor, final Library library) {
        visitor.visit(command, this, library);
    }
}
