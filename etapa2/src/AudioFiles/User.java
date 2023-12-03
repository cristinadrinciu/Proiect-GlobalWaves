package AudioFiles;

import main.Player;

import java.util.ArrayList;

public class User {
    private String username;
    private int age;
    private String city;
    private ArrayList<Playlist> playlists = new ArrayList<>();

    private ArrayList<Song> likedSongs = new ArrayList();

    private ArrayList<Playlist> followedPlaylists = new ArrayList<>();
    private Player player;

    private ArrayList<AudioFile> lastSearch = new ArrayList<>();

    public User() {
    }

    /**
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username the name to set
     */
    public void setUsername(final String username) {
        this.username = username;
    }

    /**
     * @return the age
     */
    public int getAge() {
        return age;
    }

    /**
     *
     * @param age the age to set
     */
    public void setAge(final int age) {
        this.age = age;
    }

    /**
     *
     * @return the city
     */
    public String getCity() {
        return city;
    }

    /**
     *
     * @param city the city to set
     */
    public void setCity(final String city) {
        this.city = city;
    }

    /**
     *
     * @return the playlists
     */
    public ArrayList<Playlist> getPlaylists() {
        return playlists;
    }

    /**
     *
     * @param playlists the playlists to set
     */
    public void setPlaylists(final ArrayList<Playlist> playlists) {
        this.playlists = playlists;
    }

    /**
     *
     * @return the likedSongs
     */
    public ArrayList<Song> getLikedSongs() {
        return likedSongs;
    }

    /**
     *
     * @param likedSongs the likedSongs to set
     */
    public void setLikedSongs(final ArrayList<Song> likedSongs) {
        this.likedSongs = likedSongs;
    }

    /**
     *
     * @return the followedPlaylists
     */
    public ArrayList<Playlist> getFollowedPlaylists() {
        return followedPlaylists;
    }

    /**
     *
     * @param followedPlaylists the followedPlaylists to set
     */
    public void setFollowedPlaylists(final ArrayList<Playlist> followedPlaylists) {
        this.followedPlaylists = followedPlaylists;
    }

    /**
     *
     * @return the player
     */
    public Player getPlayer() {
        return player;
    }

    /**
     *
     * @param player the player to set
     */
    public void setPlayer(final Player player) {
        this.player = player;
    }

    /**
     *
     * @return the lastSearch
     */
    public ArrayList<AudioFile> getLastSearch() {
        return lastSearch;
    }

    /**
     *
     * @param lastSearch the lastSearch to set
     */
    public void setLastSearch(final ArrayList<AudioFile> lastSearch) {
        this.lastSearch = lastSearch;
    }
}
