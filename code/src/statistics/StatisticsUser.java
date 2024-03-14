package statistics;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class StatisticsUser {
    private HashMap<String, Integer> topArtists;
    private HashMap<String, Integer> topGenres;
    private HashMap<String, Integer> topSongs;
    private HashMap<String, Integer> topAlbums;
    private HashMap<String, Integer> topEpisodes;

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
    public void setTopArtists(final HashMap<String, Integer> topArtists) {
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
    public void setTopGenres(final HashMap<String, Integer> topGenres) {
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
    public void setTopSongs(final HashMap<String, Integer> topSongs) {
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
    public void setTopAlbums(final HashMap<String, Integer> topAlbums) {
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
    public void setTopEpisodes(final HashMap<String, Integer> topEpisodes) {
        this.topEpisodes = topEpisodes;
    }

    /**
     * @return the list of top artists
     */
    public ArrayList<String> topArtists() {
        ArrayList<String> topArtists1 = new ArrayList<>();

        // get the list of artists from the hashmap
        List<String> artists = new ArrayList<>(this.topArtists.keySet());

        // sort the list of artists by the number of listens they have (the value in the hashmap)
        artists.sort((artist1, artist2) -> this.topArtists.get(artist2)
                - this.topArtists.get(artist1));

        // in case of a tie, sort by name
        artists.sort((artist1, artist2) -> {
            if (this.topArtists.get(artist1).equals(this.topArtists.get(artist2))) {
                return artist1.compareTo(artist2);
            }
            return 0;
        });

        final int maxArtists = 5;
        // add the top 5 artists to the list or all of them if there are less than 5
        if (artists.size() > maxArtists) {
            for (int i = 0; i < maxArtists; i++) {
                topArtists1.add(artists.get(i));
            }
        } else {
            topArtists1.addAll(artists);
        }

        return topArtists1;
    }

    /**
     * @return the list of top genres
     */
    public ArrayList<String> topGenres() {
        ArrayList<String> topGenres1 = new ArrayList<>();

        // get the list of genres from the hashmap
        List<String> genres = new ArrayList<>(this.topGenres.keySet());

        // sort the list of genres by the number of listens they have (the value in the hashmap)
        genres.sort((genre1, genre2) -> this.topGenres.get(genre2) - this.topGenres.get(genre1));

        // in case of a tie, sort by name
        genres.sort((genre1, genre2) -> {
            if (this.topGenres.get(genre1).equals(this.topGenres.get(genre2))) {
                return genre1.compareTo(genre2);
            }
            return 0;
        });

        final int maxGenres = 5;
        // add the top 5 genres to the list or all of them if there are less than 5
        if (genres.size() > maxGenres) {
            for (int i = 0; i < maxGenres; i++) {
                topGenres1.add(genres.get(i));
            }
        } else {
            topGenres1.addAll(genres);
        }

        return topGenres1;
    }

    /**
     * @return the list of top songs
     */
    public ArrayList<String> topSongs() {
        ArrayList<String> topSongs1 = new ArrayList<>();

        // get the list of songs from the hashmap
        List<String> songs = new ArrayList<>(this.topSongs.keySet());

        // sort the list of songs by the number of listens they have (the value in the hashmap)
        songs.sort((song1, song2) -> this.topSongs.get(song2) - this.topSongs.get(song1));

        // in case of a tie, sort by name
        songs.sort((song1, song2) -> {
            if (this.topSongs.get(song1).equals(this.topSongs.get(song2))) {
                return song1.compareTo(song2);
            }
            return 0;
        });

        final int maxSongs = 5;
        // add the top 5 songs to the list or all of them if there are less than 5
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
     * @return the list of top albums
     */
    public ArrayList<String> topAlbums() {
        ArrayList<String> topAlbums1 = new ArrayList<>();
        // get the list of albums from the hashmap
        List<String> albums = new ArrayList<>(this.topAlbums.keySet());

        // sort the list of albums by the number of listens they have (the value in the hashmap)
        albums.sort((album1, album2) -> this.topAlbums.get(album2) - this.topAlbums.get(album1));

        // in case of a tie, sort by name
        albums.sort((album1, album2) -> {
            if (this.topAlbums.get(album1).equals(this.topAlbums.get(album2))) {
                return album1.compareTo(album2);
            }
            return 0;
        });

        final int maxAlbums = 5;
        // add the top 5 albums to the list or all of them if there are less than 5
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
     * @return the list of top podcasts
     */
    public ArrayList<String> topEpisodes() {
        ArrayList<String> topEpisodes1 = new ArrayList<>();

        // get the list of podcasts from the hashmap
        List<String> episodes = new ArrayList<>(this.topEpisodes.keySet());

        // sort the list of podcasts by the number of listens they have (the value in the hashmap)
        episodes.sort((episode1, episode2) -> this.topEpisodes.get(episode2)
                - this.topEpisodes.get(episode1));

        // in case of a tie, sort by name
        episodes.sort((episode1, episode2) -> {
            if (this.topEpisodes.get(episode1).equals(this.topEpisodes.get(episode2))) {
                return episode1.compareTo(episode2);
            }
            return 0;
        });

        final int maxEpisodes = 5;
        // add the top 5 podcasts to the list or all of them if there are less than 5
        if (episodes.size() > maxEpisodes) {
            for (int i = 0; i < maxEpisodes; i++) {
                topEpisodes1.add(episodes.get(i));
            }
        } else {
            topEpisodes1.addAll(episodes);
        }

        return topEpisodes1;
    }
}
