package user.types;

import audio.files.Album;
import page.content.Merch;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ArtistStatistics {
    private HashMap<String, Integer> topAlbums; // the key is the album and the value is the number of listens
    private HashMap<String, Integer> topSongs; // the key is the song and the value is the number of listens
    private HashMap<String, Integer> topFans; // the key is the fan and the value is the number of listens
    private int listeners; // the number of listeners
    private HashMap<Merch, Integer> boughtMerch; // the key is the merch and the value is the number of buys

    public ArtistStatistics() {
            topAlbums = new HashMap<>();
            topSongs = new HashMap<>();
            topFans = new HashMap<>();
            boughtMerch = new HashMap<>();
            listeners = 0;
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
    public void setTopAlbums(HashMap<String, Integer> topAlbums) {
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
    public void setTopSongs(HashMap<String, Integer> topSongs) {
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
    public void setTopFans(HashMap<String, Integer> topFans) {
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
     * @return the top albums
     */
    public ArrayList<String> topAlbums() {
        // get the top 5 albums or all of them if there are less than 5
        ArrayList<String> topAlbums = new ArrayList<>();
        List<String> albums = new ArrayList<>(this.topAlbums.keySet());

        // sort the albums by the number of listens
        albums.sort((album1, album2) -> this.topAlbums.get(album2) - this.topAlbums.get(album1));

        // in case of a tie, sort by name
        albums.sort((album1, album2) -> {
            if(this.topAlbums.get(album1).equals(this.topAlbums.get(album2))) {
                return album1.compareTo(album2);
            }
            return 0;
        });

        if(albums.size() > 5) {
            for(int i = 0; i < 5; i++) {
                topAlbums.add(albums.get(i));
            }
        } else {
            topAlbums.addAll(albums);
        }

        return topAlbums;
    }

    /**
     * @return the top songs
     */
    public ArrayList<String> topSongs() {
        // get the top 5 songs or all of them if there are less than 5
        ArrayList<String> topSongs = new ArrayList<>();
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

        if (songs.size() > 5) {
            for (int i = 0; i < 5; i++) {
                topSongs.add(songs.get(i));
            }
        } else {
            topSongs.addAll(songs);
        }

        return topSongs;
    }

    /**
     * @return the top fans
     */
    public ArrayList<String> topFans() {
        // get the top 5 fans or all of them if there are less than 5
        ArrayList<String> topFans = new ArrayList<>();
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

        if (fans.size() > 5) {
            for (int i = 0; i < 5; i++) {
                topFans.add(fans.get(i));
            }
        } else {
            topFans.addAll(fans);
        }

        return topFans;
    }

    /**
     * @return the boughtMerch
     */
    public HashMap<Merch, Integer> getBoughtMerch() {
        return boughtMerch;
    }
}
