package commands;

import audio.files.Library;
import audio.files.Song;
import user.types.Artist;
import user.types.User;
import visit.pattern.Visitable;
import visit.pattern.Visitor;

import java.util.ArrayList;

public class CancelPremiumCommand implements Visitable {
    private String message;

    public CancelPremiumCommand() {
    }

    public String getMessage() {
        return message;
    }

    public void cancelPremium(User user) {
        if(!user.isPremium()) {
            message = user.getUsername() + " is not a premium user.";
        } else {
            user.setPremium(false);

            // calculate the monetization for the songs listened while premium
            ArrayList<Song> songs = user.getPremiumSongs();

            // get the artists of the songs listened while premium
            ArrayList<Artist> artists = new ArrayList<>();
            for(Song song : songs) {
                if(!artists.contains(user.getPlayer().findArtist(song))) {
                    artists.add(user.getPlayer().findArtist(song));
                }
            }

            // for each artist get the song revenue
            for(Artist artist : artists) {
                artist.calculateSongRevenue(user);
            }

            // clear the list of premium songs
            user.getPremiumSongs().clear();

            message = user.getUsername() + " cancelled the subscription successfully.";
        }
    }

    @Override
    public void accept(main.InputCommands command, Visitor visitor, audio.files.Library library) {
        visitor.visit(command, this, library);
    }
}
