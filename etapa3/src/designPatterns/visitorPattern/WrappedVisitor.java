package designPatterns.visitorPattern;

import commands.WrappedCommand;
import users.Artist;
import users.Host;
import users.User;

public class WrappedVisitor implements Visitor {
    private WrappedCommand wrappedCommand;

    public WrappedVisitor(final WrappedCommand wrappedCommand) {
        this.wrappedCommand = wrappedCommand;
    }

    /**
     * This method is used to visit the user and get the data from his statistics
     * @param user the user, artist or host
     */
    @Override
    public void visit(final User user) {
        // if there are no data in the user's statistics, the message exist
        // otherwise is null
        // check if the user has any data in his statistics
        if (user.getStatistics().getTopArtists().isEmpty()
                && user.getStatistics().getTopGenres().isEmpty()
                && user.getStatistics().getTopSongs().isEmpty()
                && user.getStatistics().getTopAlbums().isEmpty()
                && user.getStatistics().getTopEpisodes().isEmpty()) {
            wrappedCommand.setMessage("No data to show for user " + user.getUsername() + ".");
            return;
        }
        wrappedCommand.setMessage(null);
        // get the song names
        wrappedCommand.getTopSongs().addAll(user.getStatistics().topSongs());
        wrappedCommand.getTopArtists().addAll(user.getStatistics().topArtists());
        wrappedCommand.getTopGenres().addAll(user.getStatistics().topGenres());
        wrappedCommand.getTopAlbums().addAll(user.getStatistics().topAlbums());
        wrappedCommand.getTopEpisodes().addAll(user.getStatistics().topEpisodes());
    }

    /**
     * This method is used to visit the artist and get the data from his statistics
     * @param artist the artist
     */
    @Override
    public void visit(final Artist artist) {
        // if there are no data in the artist's statistics, the message exists
        // otherwise is null
        // check if the artist has any data in his statistics
        if (artist.getArtistStatistics().getTopAlbums().isEmpty()
                && artist.getArtistStatistics().getTopSongs().isEmpty()
                && artist.getArtistStatistics().getTopFans().isEmpty()
                && artist.getArtistStatistics().getListeners() == 0) {
               wrappedCommand.setMessage("No data to show for artist "
                       + artist.getUsername() + ".");
            return;
        }
        wrappedCommand.setMessage(null);
        // get the song names
        wrappedCommand.getTopSongs().addAll(artist.getArtistStatistics().topSongs());
        wrappedCommand.getTopAlbums().addAll(artist.getArtistStatistics().topAlbums());
        wrappedCommand.getTopFans().addAll(artist.getArtistStatistics().topFans());
        artist.getArtistStatistics().setListeners();
        wrappedCommand.setListeners(artist.getArtistStatistics().getListeners());
    }

    /**
     * This method is used to visit the host and get the data from his statistics
     * @param host the host
     */
    @Override
    public void visit(final Host host) {
        // if there are no data in the host's statistics, the message exists
        // otherwise is null
        // check if the host has any data in his statistics
        if (host.getHostStatistics().getTopEpisodes().isEmpty()
                && host.getHostStatistics().getTopFans().isEmpty()
                && host.getHostStatistics().getListeners() == 0) {
            wrappedCommand.setMessage("No data to show for host " + host.getUsername() + ".");
            return;
        }
        wrappedCommand.setMessage(null);
        wrappedCommand.getTopEpisodes().addAll(host.getHostStatistics().topEpisodes());
        host.getHostStatistics().setListeners();
        wrappedCommand.setListeners(host.getHostStatistics().getListeners());
    }
}
