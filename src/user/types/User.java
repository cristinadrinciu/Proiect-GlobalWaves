package user.types;

import audio.files.*;
import pages.LikedContentPage;
import platform.data.PublicAlbums;
import player.Player;
import pages.HomePage;
import pages.Page;

import java.util.ArrayList;

public class User {
    private String username;
    private int age;
    private String city;

    private String type;
    private boolean statusOnline = true;
    private ArrayList<Playlist> playlists = new ArrayList<>();

    private ArrayList<Song> likedSongs = new ArrayList();

    private ArrayList<Playlist> followedPlaylists = new ArrayList<>();
    private Player player;

    private ArrayList<AudioFile> lastSearch;
    private ArrayList<User> lastSearchUsers;
    private HomePage homePage = new HomePage();
    private LikedContentPage likedContentPage = new LikedContentPage();
    private Page currentPage;
    private AudioFile selectedItem;
    private StatisticsUser statistics = new StatisticsUser();
    private boolean premium = false;
    private ArrayList<Song> premiumSongs = new ArrayList<>();

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

    /**
     *
     * @return the statusOnline
     */
    public boolean getStatusOnline() {
        return statusOnline;
    }

    /**
     *
     * @param statusOnline the statusOnline to set
     */
    public void setStatusOnline(final boolean statusOnline) {
        this.statusOnline = statusOnline;
    }

    /**
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(final String type) {
        this.type = type;
    }

    /**
     * @return the homePage
     */
    public HomePage getHomePage() {
        return homePage;
    }

    /**
     * @param homePage the homePage to set
     */
    public void setHomePage(final HomePage homePage) {
        this.homePage = homePage;
    }

    /**
     * @return the currentPage
     */
    public Page getCurrentPage() {
        return currentPage;
    }

    /**
     * @param currentPage the currentPage to set
     */
    public void setCurrentPage(final Page currentPage) {
        this.currentPage = currentPage;
    }

    /**
     * This method sets the home page of the user
     */
    public void setHomePage() {
        this.homePage.setLikedSongs(this.likedSongs);
        this.homePage.setFollowedPlaylists(this.followedPlaylists);
    }

    /**
     * @return the lastSearchUsers
     */
    public ArrayList<User> getLastSearchUsers() {
        return lastSearchUsers;
    }

    /**
     * @param lastSearchUsers the lastSearchUsers to set
     */
    public void setLastSearchUsers(final ArrayList<User> lastSearchUsers) {
        this.lastSearchUsers = lastSearchUsers;
    }

    /**
     * @return the likedContentPage
     */
    public LikedContentPage getLikedContentPage() {
        return likedContentPage;
    }

    /**
     * set the likedContentPage
     */
    public void setLikedContentPage() {
        this.likedContentPage.setLikedContentPage(this);
    }

    /**
     * @return the selectedItem
     */
    public AudioFile getSelectedItem() {
        return selectedItem;
    }

    /**
     * @param selectedItem the selectedItem to set
     */
    public void setSelectedItem(final AudioFile selectedItem) {
        this.selectedItem = selectedItem;
    }

    /**
     * @return the statistics
     */
    public StatisticsUser getStatistics() {
        return statistics;
    }

    /**
     * @param album the album to set the number of listens to in the statistics
     */
    public void setListensToAlbum(final String album) {
        // add the album to the topAlbums hashmap if it is not already there
        if (!statistics.getTopAlbums().containsKey(album)) {
            statistics.getTopAlbums().put(album, 1);
        } else {
            // if the album is already in the hashmap, increment the number of listens
            statistics.getTopAlbums().put(album, statistics.getTopAlbums().get(album) + 1);
        }
    }

    /**
     * @param song the song to set the number of listens to in the statistics
     */
    public void setListensToSong(final String song) {
        // add the song to the topSongs hashmap if it is not already there
        if (!statistics.getTopSongs().containsKey(song)) {
            statistics.getTopSongs().put(song, 1);
        } else {
            // if the song is already in the hashmap, increment the number of listens
            statistics.getTopSongs().put(song, statistics.getTopSongs().get(song) + 1);
        }
    }

    /**
     * @param podcast the podcast to set the number of listens to in the statistics
     */
    public void setListensToPodcast(final String podcast) {
        // add the podcast to the topPodcasts hashmap if it is not already there
        if (!statistics.getTopPodcasts().containsKey(podcast)) {
            statistics.getTopPodcasts().put(podcast, 1);
        } else {
            // if the podcast is already in the hashmap, increment the number of listens
            statistics.getTopPodcasts().put(podcast, statistics.getTopPodcasts().get(podcast) + 1);
        }
    }

    /**
     * @param artist the artist to set the number of listens to in the statistics
     */
    public void setListensToArtist(final String artist) {
        // add the artist to the topArtists hashmap if it is not already there
        if (!statistics.getTopArtists().containsKey(artist)) {
            statistics.getTopArtists().put(artist, 1);
        } else {
            // if the artist is already in the hashmap, increment the number of listens
            statistics.getTopArtists().put(artist, statistics.getTopArtists().get(artist) + 1);
        }
    }

    /**
     * @param genre the genre to set the number of listens to in the statistics
     */
    public void setListensToGenre(final String genre) {
        // add the genre to the topGenres hashmap if it is not already there
        if (!statistics.getTopGenres().containsKey(genre)) {
            statistics.getTopGenres().put(genre, 1);
        } else {
            // if the genre is already in the hashmap, increment the number of listens
            statistics.getTopGenres().put(genre, statistics.getTopGenres().get(genre) + 1);
        }
    }

    /**
     * @return the premium
     */
    public boolean isPremium() {
        return premium;
    }

    /**
     * @param premium the premium to set
     */
    public void setPremium(final boolean premium) {
        this.premium = premium;
    }

    /**
     * @return the premiumSongs
     */
    public ArrayList<Song> getPremiumSongs() {
        return premiumSongs;
    }

    /**
     * Add in the premium songs list
     * @param song the song to be added
     */
    public void addPremiumSongs(final Song song) {
        // check if the song is already in the list
        for (Song premiumSong : this.premiumSongs) {
            if (premiumSong.getName().equals(song.getName())) {
                return;
            }
        }
        this.premiumSongs.add(song);
    }
}
