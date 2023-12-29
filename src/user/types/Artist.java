package user.types;

import audio.files.Album;
import audio.files.Library;
import audio.files.Song;
import page.content.Event;
import page.content.Merch;
import pages.ArtistPage;

import java.util.ArrayList;

public class Artist extends User {
    // add the new fields of the artist
    private ArrayList<Album> albums = new ArrayList<Album>();
    private ArrayList<Event> events = new ArrayList<Event>();
    private ArrayList<Merch> merchs = new ArrayList<Merch>();
    private ArtistPage artistPage = new ArtistPage();
    private ArtistStatistics artistStatistics = new ArtistStatistics();
    private double merchRevenue = 0.0;
    private double songRevenue = 0.0;
    private Song mostProfitableSong; // melodie pe care artistul a scos cei mai multi bani (bazat pe suma revenue-urilor)

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
     * @param merch the merch to set the number of buys to in the statistics
     */
    public void setBuysMerch(final Merch merch) {
        // add the merch to the boughtMerch hashmap if it is not already there
        if (!artistStatistics.getBoughtMerch().containsKey(merch)) {
            artistStatistics.getBoughtMerch().put(merch, 1);
        } else {
            // if the merch is already in the hashmap, increment the number of buys
            artistStatistics.getBoughtMerch().put(merch, artistStatistics.getBoughtMerch().get(merch) + 1);
        }
    }

    /**
     * Calculates the Song Revenue
     */
    public void calculateMerchRevenue() {
        // check first of the artist has any songs listened or any merch bought
        if (artistStatistics.getTopSongs().isEmpty() && artistStatistics.getBoughtMerch().isEmpty()) {
            return;
        }

        merchRevenue = 0.0;
        for (Merch merch : artistStatistics.getBoughtMerch().keySet()) {
            merchRevenue += merch.getPrice() * artistStatistics.getBoughtMerch().get(merch);
        }
    }

    /**
     * Calculates the Song Revenue
     */
    public void calculateSongRevenue(Library library) {
        // check first of the artist has any songs listened or any merch bought
        if (artistStatistics.getTopSongs().isEmpty() && artistStatistics.getBoughtMerch().isEmpty()) {
            return;
        }

        songRevenue = 0.0;
        double max = 0.0;
        for (User user : library.getUsers()) {
            // get the total number of songs listened by the user
            int songTotal = user.getPremiumSongs().size();

            // get the number of songs listened by the user from this artist
            int songListened = 0;
            for (Song song : user.getPremiumSongs()) {
                if (song.getArtist().equals(this.getUsername())) {
                    songListened++;
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
    public Song getMostProfitableSong() {
        return mostProfitableSong;
    }

    /**
     * Find the most profitable song
     */
    public void findMostProfitableSong(Library library) {
        // get the most listened song from this artist from the premium users
        ArrayList<Song> mostListenedSongs = new ArrayList<>();
        for (User user : library.getUsers()) {
            if (user.isPremium()) {
                Song mostListenedSong = null;
                int max = 0;
                // get the most listened song from this artist from the premium user
                for (String song : user.getStatistics().getTopSongs().keySet()) {
                    // find the song in the library
                    for (Song song1 : library.getSongs()) {
                        if (song1.getName().equals(song)) {
                            if (song1.getArtist().equals(this.getUsername())) {
                                if (user.getStatistics().getTopSongs().get(song) > max) {
                                    max = user.getStatistics().getTopSongs().get(song);
                                    mostListenedSong = song1;
                                }
                            }
                        }
                    }
                }
                if (mostListenedSong != null) {
                    mostListenedSongs.add(mostListenedSong);
                }
            }
        }

        // get the most profitable song from the most listened songs(the song with the most appearances)
        int max = 0;
        for (Song song : mostListenedSongs) {
            int appearances = 0;
            for (Song song1 : mostListenedSongs) {
                if (song1.getName().equals(song.getName())) {
                    appearances++;
                }
            }
            if (appearances > max) {
                max = appearances;
                mostProfitableSong = song;
            }
        }
    }
}
