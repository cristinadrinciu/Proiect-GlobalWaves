package audiofiles;

import java.util.ArrayList;

public class Song extends AudioFile {
    private Integer duration;
    private String album;
    private ArrayList<String> tags;
    private String lyrics;
    private String genre;
    private Integer releaseYear;
    private String artist;
    private int likes;

    public Song() {
        super("song");
    }

    /**
     *
     * @return duration
     */
    public Integer getDuration() {
        return duration;
    }

    /**
     * set duration
     * @param duration duration
     */
    public void setDuration(final Integer duration) {
        this.duration = duration;
    }

    /**
     * get album
     * @return album
     */
    public String getAlbum() {
        return album;
    }

    /**
     * set album
     * @param album album
     */
    public void setAlbum(final String album) {
        this.album = album;
    }

    /**
     * get tags
     * @return tags
     */
    public ArrayList<String> getTags() {
        return tags;
    }

    /**
     * set tags
     * @param tags tags
     */
    public void setTags(final ArrayList<String> tags) {
        this.tags = tags;
    }

    /**
     * get lyrics
     * @return lyrics
     */
    public String getLyrics() {
        return lyrics;
    }

    /**
     * set lyrics
     * @param lyrics lyrics
     */
    public void setLyrics(final String lyrics) {
        this.lyrics = lyrics;
    }

    /**
     * get genre
     * @return genre
     */
    public String getGenre() {
        return genre;
    }

    /**
     * set genre
     * @param genre genre
     */
    public void setGenre(final String genre) {
        this.genre = genre;
    }

    /**
     * get release year
     * @return release year
     */
    public int getReleaseYear() {
        return releaseYear;
    }

    /**
     * set release year
     * @param releaseYear release year
     */
    public void setReleaseYear(final int releaseYear) {
        this.releaseYear = releaseYear;
    }

    /**
     * get artist
     * @return artist
     */
    public String getArtist() {
        return artist;
    }

    /**
     * set artist
     * @param artist artist
     */
    public void setArtist(final String artist) {
        this.artist = artist;
    }

    /**
     * get likes
     * @return likes
     */
    public int getLikes() {
        return likes;
    }

    /**
     * set likes
     * @param likes likes
     */
    public void setLikes(final int likes) {
        this.likes = likes;
    }

}
