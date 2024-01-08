package commands;

import audio.files.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import main.InputCommands;
import user.types.Artist;
import user.types.Host;
import user.types.User;
import visit.pattern.Visitable;
import visit.pattern.Visitor;

public class LoadRecommendationCommand implements Command {
    private String message;

    public LoadRecommendationCommand() {
    }

    /**
     * @return the message
     */
    public String getMessage() {
        return message;
    }

    /**
     * Loads the recommendation of the user
     * @param user the user that wants to load the recommendation
     */
    public void loadRecommendation(User user) {
        if(user.getLastRecommendation() == null) {
            this.message = "No recommendations available.";
            return;
        }

        if(!user.getStatusOnline()) {
            this.message = user.getUsername() + " is not offline.";
            return;
        }

        // load the last recommendation
        user.getPlayer().loadedItem = user.getLastRecommendation();
        user.getPlayer().setPlayingNow();
        user.getPlayer().shuffle = false;
        user.getPlayer().repeatState = 0;

        // update the listens
        if(user.getPlayer().loadedItem != null) {
            if(user.getPlayer().loadedItem instanceof Song
                    || user.getPlayer().loadedItem instanceof Playlist) {
                // add in the list of songs between ads
                if(user.getPlayer().playingNow != null && !user.isPremium())
                    user.getSongsBetweenAds().add((Song) user.getPlayer().playingNow);

                // add in the list of songs listened while premium
                if(user.isPremium())
                    user.addPremiumSongs((Song) user.getPlayer().playingNow);

                // update the listens of the song
                user.setListensToSong(user.getPlayer().playingNow.getName());
                user.setListensToArtist(((Song) user.getPlayer().playingNow).getArtist());
                user.setListensToGenre(((Song) user.getPlayer().playingNow).getGenre());
                user.setListensToAlbum(((Song) user.getPlayer().playingNow).getAlbum());

                // update the artist statistics
                Artist artist = user.getPlayer().findArtist((Song) user.getPlayer().playingNow);
                if (artist != null) {
                    artist.setListensToSong(user.getPlayer().playingNow.getName());
                    artist.setListensToAlbum(((Song) user.getPlayer().playingNow).getAlbum());
                    artist.setListensToFan(user.getUsername());
                }
            }
        }

        message = "Playback loaded successfully.";
    }

    /**
     * Execute Load the recommendation command
     * @param command the input command
     * @param library the main library
     */
    @Override
    public void execute(InputCommands command, Library library) {
        User user = command.getUser();
        loadRecommendation(user);

        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode commandJson = objectMapper.createObjectNode()
                .put("command", "loadRecommendations")
                .put("user", command.getUsername())
                .put("timestamp", command.getTimestamp())
                .put("message", message);
        command.getCommandList().add(commandJson);
    }
}
