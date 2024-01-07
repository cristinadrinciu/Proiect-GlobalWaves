package commands;

import audio.files.*;
import main.InputCommands;
import user.types.Artist;
import user.types.Host;
import user.types.User;
import visit.pattern.Visitable;
import visit.pattern.Visitor;

public class LoadRecommendationCommand implements Visitable {
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
                if(user.getPlayer().playingNow != null)
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

    @Override
    public void accept(final InputCommands command, final Visitor visitor, final Library library) {
        visitor.visit(command, this, library);
    }
}
