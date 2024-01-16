package commands;

import audioFiles.Library;
import audioFiles.Song;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import designPatterns.commandPattern.Command;
import main.InputCommands;
import users.Artist;
import users.User;

import java.util.ArrayList;

public class CancelPremiumCommand implements Command {
    private String message;

    public CancelPremiumCommand() {
    }

    /**
     * Getter for the message
     * @return the message
     */
    public String getMessage() {
        return message;
    }

    /**
     * Cancel the premium subscription
     * @param user the user that wants to cancel the premium subscription
     */
    public void cancelPremium(final User user) {
        if (!user.isPremium()) {
            message = user.getUsername() + " is not a premium user.";
        } else {
            user.setPremium(false);

            // calculate the monetization for the songs listened while premium
            ArrayList<Song> songs = user.getPremiumSongs();

            // get the artists of the songs listened while premium
            ArrayList<Artist> artists = new ArrayList<>();
            for (Song song : songs) {
                if (!artists.contains(user.getPlayer().findArtist(song))) {
                    artists.add(user.getPlayer().findArtist(song));
                }
            }

            // for each artist get the song revenue
            for (Artist artist : artists) {
                artist.calculateSongRevenue(user);
            }

            // clear the list of premium songs
            user.getPremiumSongs().clear();

            message = user.getUsername()
                    + " cancelled the subscription successfully.";
        }
    }

    /**
     * Execute the command
     * @param command the input command
     * @param library the main library
     */
    @Override
    public void execute(final InputCommands command, final Library library) {
        User user = command.getUser();

        // update the player of the user
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

        cancelPremium(user);

        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode commandJson = objectMapper.createObjectNode()
                .put("command", "cancelPremium")
                .put("user", command.getUsername())
                .put("timestamp", command.getTimestamp())
                .put("message", message);
        command.getCommandList().add(commandJson);
    }
}
