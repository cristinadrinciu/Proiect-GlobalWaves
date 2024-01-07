package commands;

import audio.files.Library;
import audio.files.Song;
import main.InputCommands;
import org.checkerframework.checker.units.qual.A;
import user.types.Artist;
import user.types.Host;
import user.types.User;
import visit.pattern.Visitable;
import visit.pattern.Visitor;

import java.util.ArrayList;

public class WrappedCommand implements Visitable {
    private String message = null;
    private ArrayList<String> topArtists = new ArrayList<>();
    private ArrayList<String> topGenres = new ArrayList<>();
    private ArrayList<String> topSongs = new ArrayList<>();
    private ArrayList<String> topAlbums = new ArrayList<>();
    private ArrayList<String> topEpisodes = new ArrayList<>();
    private ArrayList<String> topFans = new ArrayList<>();
    private int listeners;


    public WrappedCommand() {
    }


    /**
     * @return the message
     */
    public String getMessage() {
        return message;
    }

    public void setStatisticsUser(User user) {
        // if there are no data in the user's statistics, the message is "No data to show for user ${username}.", otherwise is null
        // check if the user has any data in his statistics
        if(user.getStatistics().getTopArtists().isEmpty() && user.getStatistics().getTopGenres().isEmpty() &&
                user.getStatistics().getTopSongs().isEmpty() && user.getStatistics().getTopAlbums().isEmpty() &&
                user.getStatistics().getTopEpisodes().isEmpty()) {
            message = "No data to show for user " + user.getUsername() + ".";
            return;
        }
        message = null;
        // get the song names
        topSongs.addAll(user.getStatistics().topSongs());
        topArtists.addAll(user.getStatistics().topArtists());
        topGenres.addAll(user.getStatistics().topGenres());
        topAlbums.addAll(user.getStatistics().topAlbums());
        topEpisodes.addAll(user.getStatistics().topEpisodes());
    }

    public void setStatisticsArtist(Artist artist) {
        // if there are no data in the artist's statistics, the message is "No data to show for artist ${artist_name}.", otherwise is null
        // check if the artist has any data in his statistics
        if(artist.getArtistStatistics().getTopAlbums().isEmpty() && artist.getArtistStatistics().getTopSongs().isEmpty() &&
                artist.getArtistStatistics().getTopFans().isEmpty() && artist.getArtistStatistics().getListeners() == 0) {
            message = "No data to show for artist " + artist.getUsername() + ".";
            return;
        }
        message = null;
        // get the song names
        topSongs.addAll(artist.getArtistStatistics().topSongs());
        topAlbums.addAll(artist.getArtistStatistics().topAlbums());
        topFans.addAll(artist.getArtistStatistics().topFans());
        artist.getArtistStatistics().setListeners();
        listeners = artist.getArtistStatistics().getListeners();
    }

    public void setStatisticsHost(Host host) {
        // if there are no data in the host's statistics, the message is "No data to show for host ${host_name}.", otherwise is null
        // check if the host has any data in his statistics
        if(host.getHostStatistics().getTopEpisodes().isEmpty() && host.getHostStatistics().getTopFans().isEmpty() &&
                host.getHostStatistics().getListeners() == 0) {
            message = "No data to show for host " + host.getUsername() + ".";
            return;
        }
        message = null;
        topEpisodes.addAll(host.getHostStatistics().topEpisodes());
        host.getHostStatistics().setListeners();
        listeners = host.getHostStatistics().getListeners();
    }

    /**
     * @return the topArtists
     */
    public ArrayList<String> getTopArtists() {
        return topArtists;
    }

    /**
     * @return the topGenres
     */
    public ArrayList<String> getTopGenres() {
        return topGenres;
    }

    /**
     * @return the topSongs
     */
    public ArrayList<String> getTopSongs() {
        return topSongs;
    }

    /**
     * @return the topAlbums
     */
    public ArrayList<String> getTopAlbums() {
        return topAlbums;
    }

    /**
     * @return the topPodcasts
     */
    public ArrayList<String> getTopEpisodes() {
        return topEpisodes;
    }

    /**
     * @return the topFans
     */
    public ArrayList<String> getTopFans() {
        return topFans;
    }

    /**
     * @return the listeners
     */
    public int getListeners() {
        return listeners;
    }


    @Override
    public void accept(InputCommands command, Visitor visitor, Library library) {
        visitor.visit(command, this, library);
    }
}
