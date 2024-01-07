package user.types;

import audio.files.Album;
import audio.files.Podcast;
import audio.files.Song;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class StatisticsUser {
    private HashMap<String, Integer> topArtists; // the key is the artist and the value is the number of listens
    private HashMap<String, Integer> topGenres; // the key is the genre and the value is the number of listens
    private HashMap<String, Integer> topSongs; // the key is the song and the value is the number of listens
    private HashMap<String, Integer> topAlbums; // the key is the album and the value is the number of listens
    private HashMap<String, Integer> topEpisodes; // the key is the podcast and the value is the number of listens

    public StatisticsUser() {
        topArtists = new HashMap<>();
        topGenres = new HashMap<>();
        topSongs = new HashMap<>();
        topAlbums = new HashMap<>();
        topEpisodes = new HashMap<>();
    }

    /**
     * @return the hashmap of top artists
     */
    public HashMap<String, Integer> getTopArtists() {
        return topArtists;
    }

    /**
     * @param topArtists the hashmap of top artists to set
     */
    public void setTopArtists(HashMap<String, Integer> topArtists) {
        this.topArtists = topArtists;
    }

    /**
     * @return the hashmap of top genres
     */
    public HashMap<String, Integer> getTopGenres() {
        return topGenres;
    }

    /**
     * @param topGenres the hashmap of top genres to set
     */
    public void setTopGenres(HashMap<String, Integer> topGenres) {
        this.topGenres = topGenres;
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
     * @return the hashmap of top podcasts
     */
    public HashMap<String, Integer> getTopEpisodes() {
        return topEpisodes;
    }

    /**
     * @param topEpisodes the hashmap of top podcasts to set
     */
    public void setTopEpisodes(HashMap<String, Integer> topEpisodes) {
        this.topEpisodes = topEpisodes;
    }

    /**
     * @return the list of top artists
     */
    public ArrayList<String> topArtists() {
        ArrayList<String> topArtists = new ArrayList<>();

        // get the list of artists from the hashmap
        List<String> artists = new ArrayList<>(this.topArtists.keySet());

        // sort the list of artists by the number of listens they have (the value in the hashmap)
        artists.sort((artist1, artist2) -> this.topArtists.get(artist2) - this.topArtists.get(artist1));

        // in case of a tie, sort by name
        artists.sort((artist1, artist2) -> {
            if(this.topArtists.get(artist1).equals(this.topArtists.get(artist2))) {
                return artist1.compareTo(artist2);
            }
            return 0;
        });

        // add the top 5 artists to the list or all of them if there are less than 5
        if(artists.size() > 5) {
            for(int i = 0; i < 5; i++) {
                topArtists.add(artists.get(i));
            }
        } else {
            topArtists.addAll(artists);
        }

        return topArtists;
    }

    /**
     * @return the list of top genres
     */
    public ArrayList<String> topGenres() {
        ArrayList<String> topGenres = new ArrayList<>();

        // get the list of genres from the hashmap
        List<String> genres = new ArrayList<>(this.topGenres.keySet());

        // sort the list of genres by the number of listens they have (the value in the hashmap)
        genres.sort((genre1, genre2) -> this.topGenres.get(genre2) - this.topGenres.get(genre1));

        // in case of a tie, sort by name
        genres.sort((genre1, genre2) -> {
            if(this.topGenres.get(genre1).equals(this.topGenres.get(genre2))) {
                return genre1.compareTo(genre2);
            }
            return 0;
        });

        // add the top 5 genres to the list or all of them if there are less than 5
        if(genres.size() > 5) {
            for(int i = 0; i < 5; i++) {
                topGenres.add(genres.get(i));
            }
        } else {
            topGenres.addAll(genres);
        }

        return topGenres;
    }

    /**
     * @return the list of top songs
     */
    public ArrayList<String> topSongs() {
        ArrayList<String> topSongs = new ArrayList<>();

        // get the list of songs from the hashmap
        List<String> songs = new ArrayList<>(this.topSongs.keySet());

        // sort the list of songs by the number of listens they have (the value in the hashmap)
        songs.sort((song1, song2) -> this.topSongs.get(song2) - this.topSongs.get(song1));

        // in case of a tie, sort by name
        songs.sort((song1, song2) -> {
            if(this.topSongs.get(song1).equals(this.topSongs.get(song2))) {
                return song1.compareTo(song2);
            }
            return 0;
        });

        // add the top 5 songs to the list or all of them if there are less than 5
        if(songs.size() > 5) {
            for(int i = 0; i < 5; i++) {
                topSongs.add(songs.get(i));
            }
        } else {
            topSongs.addAll(songs);
        }

        return topSongs;
    }

    /**
     * @return the list of top albums
     */
    public ArrayList<String> topAlbums() {
        ArrayList<String> topAlbums = new ArrayList<>();
        // get the list of albums from the hashmap
        List<String> albums = new ArrayList<>(this.topAlbums.keySet());

        // sort the list of albums by the number of listens they have (the value in the hashmap)
        albums.sort((album1, album2) -> this.topAlbums.get(album2) - this.topAlbums.get(album1));

        // in case of a tie, sort by name
        albums.sort((album1, album2) -> {
            if(this.topAlbums.get(album1).equals(this.topAlbums.get(album2))) {
                return album1.compareTo(album2);
            }
            return 0;
        });

        // add the top 5 albums to the list or all of them if there are less than 5
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
     * @return the list of top podcasts
     */
    public ArrayList<String> topEpisodes() {
        ArrayList<String> topEpisodes = new ArrayList<>();

        // get the list of podcasts from the hashmap
        List<String> episodes = new ArrayList<>(this.topEpisodes.keySet());

        // sort the list of podcasts by the number of listens they have (the value in the hashmap)
        episodes.sort((episode1, episode2) -> this.topEpisodes.get(episode2) - this.topEpisodes.get(episode1));

        // in case of a tie, sort by name
        episodes.sort((episode1, episode2) -> {
            if(this.topEpisodes.get(episode1).equals(this.topEpisodes.get(episode2))) {
                return episode1.compareTo(episode2);
            }
            return 0;
        });

        // add the top 5 podcasts to the list or all of them if there are less than 5
        if(episodes.size() > 5) {
            for(int i = 0; i < 5; i++) {
                topEpisodes.add(episodes.get(i));
            }
        } else {
            topEpisodes.addAll(episodes);
        }

        return topEpisodes;
    }
}
