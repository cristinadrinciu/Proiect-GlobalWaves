package user.types;

import audio.files.Album;
import audio.files.Library;
import audio.files.Song;
import observer.Observer;
import observer.Subject;
import page.content.Event;
import page.content.Merch;
import pages.ArtistPage;

import java.util.ArrayList;

public class Artist extends User implements Subject {
    // add the new fields of the artist
    private ArrayList<Album> albums = new ArrayList<Album>();
    private ArrayList<Event> events = new ArrayList<Event>();
    private ArrayList<Merch> merchs = new ArrayList<Merch>();
    private ArtistPage artistPage = new ArtistPage(this);
    private ArtistStatistics artistStatistics = new ArtistStatistics();
    private double merchRevenue = 0.0;
    private double songRevenue = 0.0;
    private String mostProfitableSong; // melodie pe care artistul a scos cei mai multi bani (bazat pe suma revenue-urilor)
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
            artistStatistics.getTopAlbums().put(album, artistStatistics.getTopAlbums().get(album) + 1);
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
    public void calculateSongRevenue(User user) {
        // check first of the artist has any songs listened or any merch bought
        if (artistStatistics.getTopSongs().isEmpty() && merchRevenue == 0) {
            return;
        }

        // get the songs listened while premium by the user
        ArrayList<Song> songs = user.getPremiumSongs();

        int totalSongs = songs.size();

        ArrayList<Song> tmpSongs = new ArrayList<>();

        // add the songs listened while premium, but just once
        for (Song song : songs) {
            if (!tmpSongs.contains(song)) {
                tmpSongs.add(song);
            }
        }

        // calculate the song revenue for each song
        for (Song song : tmpSongs) {
            if (song.getArtist().equals(this.getUsername())) {
                // get the number of occurrences of the song in the user's premium songs list
                int occurrences = 0;
                for (Song song1 : songs) {
                    if (song1.equals(song)) {
                        occurrences++;
                    }
                }

                if (occurrences == 0) {
                    continue;
                }

                // calculate the revenue for the song
                double revenue = 1000000 * (double) occurrences / totalSongs;

                // add the revenue to the hashmap
                if (!artistStatistics.getSongsRevenue().containsKey(song.getName())) {
                    artistStatistics.getSongsRevenue().put(song.getName(), revenue);
                } else {
                    artistStatistics.getSongsRevenue().put(song.getName(), artistStatistics.getSongsRevenue().get(song.getName()) + revenue);
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
    public void calculateAddRevenue(int price, User user) {
        // calculate the revenue for the artist for each song
        for (Song song : user.getSongsBetweenAds()) {
            if (song.getArtist().equals(this.getUsername())) {
                double revenue = ((double) price) * 1.0 / user.getSongsBetweenAds().size();

                // add the revenue to the hashmap
                if (!artistStatistics.getSongsRevenue().containsKey(song.getName())) {
                    artistStatistics.getSongsRevenue().put(song.getName(), revenue);
                } else {
                    artistStatistics.getSongsRevenue().put(song.getName(), artistStatistics.getSongsRevenue().get(song.getName()) + revenue);
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
     * @param subscribers the subscribers to set
     */
    public void setSubscribers(final ArrayList<User> subscribers) {
        this.subscribers = subscribers;
    }

    public void setMerchRevenue(double value) {
        this.merchRevenue = value;
    }

    /**
     * @param value the songRevenue to set
     */
    public void setSongRevenue(double value) {
        this.songRevenue = value;
    }

    @Override
    public void addObserver(Observer observer) {
        subscribers.add((User) observer);
    }

    @Override
    public void notifyObservers(String name, String description) {
        for (User user : subscribers) {
            user.update(name, description);
        }
    }
}
