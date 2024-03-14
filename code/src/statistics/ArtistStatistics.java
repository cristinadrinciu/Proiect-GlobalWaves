package statistics;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ArtistStatistics {
    private HashMap<String, Integer> topAlbums;
    private HashMap<String, Integer> topSongs;
    private HashMap<String, Integer> topFans;
    private int listeners; // the number of listeners
    private HashMap<String, Double> songsRevenue;

    public ArtistStatistics() {
        topAlbums = new HashMap<>();
        topSongs = new HashMap<>();
        topFans = new HashMap<>();
        listeners = 0;
        songsRevenue = new HashMap<>();
    }

    /**
     * @return the hashmap of top albums
     */
    public HashMap<String, Integer> getTopAlbums() {
        return topAlbums;
    }

    /**
     * @param topAlbums the hashmap of top albums to set
     */
    public void setTopAlbums(final HashMap<String, Integer> topAlbums) {
        this.topAlbums = topAlbums;
    }

    /**
     * @return the hashmap of top songs
     */
    public HashMap<String, Integer> getTopSongs() {
        return topSongs;
    }

    /**
     * @param topSongs the hashmap of top songs to set
     */
    public void setTopSongs(final HashMap<String, Integer> topSongs) {
        this.topSongs = topSongs;
    }

    /**
     * @return the hashmap of top fans
     */
    public HashMap<String, Integer> getTopFans() {
        return topFans;
    }

    /**
     * @param topFans the hashmap of top fans to set
     */
    public void setTopFans(final HashMap<String, Integer> topFans) {
        this.topFans = topFans;
    }

    /**
     * @return the number of listeners
     */
    public int getListeners() {
        return listeners;
    }

    /**
     * Sets the number of listeners
     */
    public void setListeners() {
        this.listeners = topFans.size();
    }

    /**
     * @return the hashmap of songs revenue
     */
    public HashMap<String, Double> getSongsRevenue() {
        return songsRevenue;
    }

    /**
     * @param songsRevenue the hashmap of songs revenue to set
     */
    public void setSongsRevenue(final HashMap<String, Double> songsRevenue) {
        this.songsRevenue = songsRevenue;
    }

    /**
     * @return the top albums
     */
    public ArrayList<String> topAlbums() {
        // get the top 5 albums or all of them if there are less than 5
        ArrayList<String> topAlbums1 = new ArrayList<>();
        List<String> albums = new ArrayList<>(this.topAlbums.keySet());

        // sort the albums by the number of listens
        albums.sort((album1, album2) -> this.topAlbums.get(album2)
                - this.topAlbums.get(album1));

        // in case of a tie, sort by name
        albums.sort((album1, album2) -> {
            if (this.topAlbums.get(album1).equals(this.topAlbums.get(album2))) {
                return album1.compareTo(album2);
            }
            return 0;
        });

        final int maxAlbums = 5;
        if (albums.size() > maxAlbums) {
            for (int i = 0; i < maxAlbums; i++) {
                topAlbums1.add(albums.get(i));
            }
        } else {
            topAlbums1.addAll(albums);
        }

        return topAlbums1;
    }

    /**
     * @return the top songs
     */
    public ArrayList<String> topSongs() {
        // get the top 5 songs or all of them if there are less than 5
        ArrayList<String> topSongs1 = new ArrayList<>();
        List<String> songs = new ArrayList<>(this.topSongs.keySet());

        // sort the songs by the number of listens
        songs.sort((song1, song2) -> this.topSongs.get(song2) - this.topSongs.get(song1));

        // in case of a tie, sort by name
        songs.sort((song1, song2) -> {
            if (this.topSongs.get(song1).equals(this.topSongs.get(song2))) {
                return song1.compareTo(song2);
            }
            return 0;
        });

        final int maxSongs = 5;
        if (songs.size() > maxSongs) {
            for (int i = 0; i < maxSongs; i++) {
                topSongs1.add(songs.get(i));
            }
        } else {
            topSongs1.addAll(songs);
        }

        return topSongs1;
    }

    /**
     * @return the top fans
     */
    public ArrayList<String> topFans() {
        // get the top 5 fans or all of them if there are less than 5
        ArrayList<String> topFans1 = new ArrayList<>();
        List<String> fans = new ArrayList<>(this.topFans.keySet());

        // sort the fans by the number of listens
        fans.sort((fan1, fan2) -> this.topFans.get(fan2) - this.topFans.get(fan1));

        // in case of a tie, sort by name
        fans.sort((fan1, fan2) -> {
            if (this.topFans.get(fan1).equals(this.topFans.get(fan2))) {
                return fan1.compareTo(fan2);
            }
            return 0;
        });

        final int maxFans = 5;
        if (fans.size() > maxFans) {
            for (int i = 0; i < maxFans; i++) {
                topFans1.add(fans.get(i));
            }
        } else {
            topFans1.addAll(fans);
        }

        return topFans1;
    }

    /**
     * @return the most profitable song
     */
    public String mostProfitableSong() {
        String mostProfitableSong = "";

        // get the list of songs from the hashmap
        List<String> songs = new ArrayList<>(this.songsRevenue.keySet());

        // sort the list of songs by the revenue they have (the value in the hashmap)
        songs.sort((song1, song2) -> (int) (this.songsRevenue.get(song2)
                - this.songsRevenue.get(song1)));

        // in case of a tie, sort by name
        songs.sort((song1, song2) -> {
            if (this.songsRevenue.get(song1).equals(this.songsRevenue.get(song2))) {
                return song1.compareTo(song2);
            }
            return 0;
        });

        // get the most profitable song
        if (songs.size() > 0) {
            mostProfitableSong = songs.get(0);
        }

        return mostProfitableSong;
    }
}
