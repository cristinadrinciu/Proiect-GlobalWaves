package users;

import audiofiles.Album;
import audiofiles.Song;
import designpatterns.observer.Observer;
import designpatterns.observer.Subject;
import pagecontent.Event;
import pagecontent.Merch;
import pages.ArtistPage;
import statistics.ArtistStatistics;
import designpatterns.visitorPattern.Visitable;
import designpatterns.visitorPattern.Visitor;

import java.util.ArrayList;

public class Artist extends User implements Subject, Visitable {
    // add the new fields of the artist
    private ArrayList<Album> albums = new ArrayList<Album>();
    private ArrayList<Event> events = new ArrayList<Event>();
    private ArrayList<Merch> merchs = new ArrayList<Merch>();
    private ArtistPage artistPage = new ArtistPage(this);
    private ArtistStatistics artistStatistics = new ArtistStatistics();
    private double merchRevenue = 0.0;
    private double songRevenue = 0.0;
    private ArrayList<User> subscribers = new ArrayList<User>();

    public Artist() {
    }

    /**
     * @return the albums
     */
    public ArrayList<Album> getAlbums() {
        return albums;
    }

    /**
     * @param albums the albums to set
     */
    public void setAlbums(final ArrayList<Album> albums) {
        this.albums = albums;
    }

    /**
     * @return the events
     */
    public ArrayList<Event> getEvents() {
        return events;
    }

    /**
     * @param events the events to set
     */
    public void setEvents(final ArrayList<Event> events) {
        this.events = events;
    }

    /**
     * @return the merch
     */
    public ArrayList<Merch> getMerch() {
        return merchs;
    }

    /**
     * @param merch the merch to set
     */
    public void setMerch(final ArrayList<Merch> merch) {
        this.merchs = merchs;
    }

    /**
     * @return the artistPage
     */
    public ArtistPage getArtistPage() {
        return artistPage;
    }

    /**
     * This method sets the artist page
     */
    public void setArtistPage() {
        this.artistPage.setAlbums(this.albums);
        this.artistPage.setEvents(this.events);
        this.artistPage.setMerch(this.merchs);
    }

    /**
     * @return the statistics
     */
    public ArtistStatistics getArtistStatistics() {
        return artistStatistics;
    }

    /**
     * @param statistics the statistics to set
     */
    public void setArtistStatistics(final ArtistStatistics statistics) {
        this.artistStatistics = statistics;
    }

    /**
     * @param album the album to set the number of listens to in the statistics
     */
    public void setListensToAlbum(final String album) {
        // add the album to the topAlbums hashmap if it is not already there
        if (!artistStatistics.getTopAlbums().containsKey(album)) {
            artistStatistics.getTopAlbums().put(album, 1);
        } else {
            // if the album is already in the hashmap, increment the number of listens
            artistStatistics.getTopAlbums().put(album, artistStatistics.
                    getTopAlbums().get(album) + 1);
        }
    }

    /**
     * @param song the song to set the number of listens to in the statistics
     */
    public void setListensToSong(final String song) {
        // add the song to the topSongs hashmap if it is not already there
        if (!artistStatistics.getTopSongs().containsKey(song)) {
            artistStatistics.getTopSongs().put(song, 1);
        } else {
            // if the song is already in the hashmap, increment the number of listens
            artistStatistics.getTopSongs().put(song, artistStatistics.getTopSongs().get(song) + 1);
        }
    }

    /**
     * @param fan the fan to set the number of listens to in the statistics
     */
    public void setListensToFan(final String fan) {
        // add the fan to the topFans hashmap if it is not already there
        if (!artistStatistics.getTopFans().containsKey(fan)) {
            artistStatistics.getTopFans().put(fan, 1);
        } else {
            // if the fan is already in the hashmap, increment the number of listens
            artistStatistics.getTopFans().put(fan, artistStatistics.getTopFans().get(fan) + 1);
        }
    }

    /**
     * @return the merchRevenue
     */
    public double getMerchRevenue() {
        return merchRevenue;
    }

    /**
     * Calculates the Song Revenue
     */
    public void calculateSongRevenue(final User user) {
        // check first of the artist has any songs listened or any merch bought
        if (artistStatistics.getTopSongs().isEmpty() && merchRevenue == 0) {
            return;
        }

        // get the songs listened while premium by the user
        ArrayList<Song> songs = user.getPremiumSongs();

        int totalSongs = songs.size();

        if (totalSongs == 0) {
            return;
        }

        // calculate the song revenue for each song
        for (Song song : songs) {
            if (song.getArtist().equals(this.getUsername())) {
                // calculate the revenue for the song
                final double premiumPrice = 1000000;
                double revenue = premiumPrice * 1.0 / totalSongs;

                // add the revenue to the hashmap
                if (!artistStatistics.getSongsRevenue().containsKey(song.getName())) {
                    artistStatistics.getSongsRevenue().put(song.getName(), revenue);
                } else {
                    artistStatistics.getSongsRevenue().put(song.getName(),
                            artistStatistics.getSongsRevenue().get(song.getName()) + revenue);
                }
            }
        }
    }

    /**
     * @return the songRevenue
     */
    public double getSongRevenue() {
        return songRevenue;
    }

    /**
     * @return the merchs
     */
    public ArrayList<Merch> getMerchs() {
        return merchs;
    }

    /**
     * @return the mostProfitableSong
     */
    public String getMostProfitableSong() {
        return artistStatistics.mostProfitableSong();
    }

    /**
     *
     */
    public void calculateAddRevenue(final int price, final User user) {
        // calculate the revenue for the artist for each song
        for (Song song : user.getSongsBetweenAds()) {
            if (song.getArtist().equals(this.getUsername())) {
                double revenue = ((double) price) * 1.0 / user.getSongsBetweenAds().size();

                // add the revenue to the hashmap
                if (!artistStatistics.getSongsRevenue().containsKey(song.getName())) {
                    artistStatistics.getSongsRevenue().put(song.getName(), revenue);
                } else {
                    artistStatistics.getSongsRevenue().put(song.getName(),
                            artistStatistics.getSongsRevenue().get(song.getName()) + revenue);
                }
            }
        }
    }

    /**
     * @return the subscribers
     */
    public ArrayList<User> getSubscribers() {
        return subscribers;
    }

    /**
     * @param value the merchRevenue to set
     */
    public void setMerchRevenue(final double value) {
        this.merchRevenue = value;
    }

    /**
     * @param value the songRevenue to set
     */
    public void setSongRevenue(final double value) {
        this.songRevenue = value;
    }

    /**
     * @param observer the designpatterns.observer to add
     */
    @Override
    public void addObserver(final Observer observer) {
        subscribers.add((User) observer);
    }

    /**
     * Notify the observers
     * @param name the naame of the notification
     * @param description the description of the notification
     */
    @Override
    public void notifyObservers(final String name, final String description) {
        for (User user : subscribers) {
            user.update(name, description);
        }
    }

    /**
     * Accept the visitor
     * @param visitor the visitor
     */
    @Override
    public void accept(final Visitor visitor) {
        visitor.visit(this);
    }
}
