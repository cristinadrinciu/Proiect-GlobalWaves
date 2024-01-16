package commands;

import audioFiles.Album;
import audioFiles.Library;
import audioFiles.Playlist;
import audioFiles.Song;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import designPatterns.commandPattern.Command;
import main.InputCommands;
import users.User;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class ShuffleCommand implements Command {

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
     * Execute the shuffle command
     * @param command the input command
     * @param library the library
     */
    @Override
    public void execute(final InputCommands command, final Library library) {
        User user = command.getUser();

        if (user.getPlayer().repeatState == 0) {
            user.getPlayer().setRemainingTime();
        }
        if (user.getPlayer().repeatState == 1) {
            user.getPlayer().setRemainingTimeRepeat1();
        }
        if (user.getPlayer().repeatState == 2) {
            user.getPlayer().setRemainingTimeRepeat2();
        }

        shufflePlayer(user);
        String message = message(user);

        // Check if shuffleCommand is not null before accessing its methods
        if (user.getPlayer().loadedItem != null) {
            if (user.getPlayer().shuffle) {
                // Update the shuffled Playlist from player
                if (user.getPlayer().loadedItem instanceof Playlist) {
                    user.getPlayer().shuffledPlaylist =
                            shufflePlaylist((Playlist) user.getPlayer().loadedItem);
                }
                if (user.getPlayer().loadedItem instanceof Album) {
                    user.getPlayer().shuffledPlaylist =
                            shuffleAlbum((Album) user.getPlayer().loadedItem);
                }
                setSeed(0);
            }
        }

        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode commandJson = objectMapper.createObjectNode()
                .put("command", "shuffle")
                .put("user", command.getUsername())
                .put("timestamp", command.getTimestamp())
                .put("message", message);

        command.getCommandList().add(commandJson);
    }
}
