package audiofiles;

import users.Artist;

import java.util.ArrayList;

public class Album extends AudioFile {
    private int releaseYear;
    private String description;
    private ArrayList<Song> songs;

    private Artist owner;
    private int likes;

    public Album() {
        super("album");
        this.songs = new ArrayList<>();
        this.likes = 0;
    }

    /**
     * @return the release year of the album
     */
    public int getReleaseYear() {
        return releaseYear;
    }

    /**
     * @param releaseYear the release year of the album
     */
    public void setReleaseYear(final int releaseYear) {
        this.releaseYear = releaseYear;
    }

    /**
     * @return the description of the album
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description of the album
     */
    public void setDescription(final String description) {
        this.description = description;
    }

    /**
     * @return the songs of the album
     */
    public ArrayList<Song> getSongs() {
        return songs;
    }

    /**
     * @param songs the songs of the album
     */
    public void setSongs(final ArrayList<Song> songs) {
        this.songs = songs;
    }

    /**
     * @return the owner of the album
     */
    public Artist getOwner() {
        return owner;
    }

    /**
     * @param owner the owner of the album
     */
    public void setOwner(final Artist owner) {
        this.owner = owner;
    }

    /**
     * @return the number of likes of the album
     */
    public int getLikes() {
        return likes;
    }

    /**
     * This method sets the number of likes of the album
     */
    public void setLikes() {
        // count the number of likes
        this.likes = 0;
        for (Song song : this.songs) {
            likes += song.getLikes();
        }
    }
}
