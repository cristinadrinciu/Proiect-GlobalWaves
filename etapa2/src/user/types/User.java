package user.types;

import audio.files.AudioFile;
import pages.LikedContentPage;
import audio.files.Playlist;
import audio.files.Song;
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
}
