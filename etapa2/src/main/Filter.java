package main;

import java.util.ArrayList;

public class Filter {
    private String name = null;
    private String album = null;
    private ArrayList<String> tags = null;
    private String lyrics = null;
    private String genre = null;
    private String releaseYear = null;
    private String artist = null;
    private String owner = null;


    private ArrayList<String> fields =  new ArrayList<>();
    private ArrayList<Object> filterArray = new ArrayList<>();

    public Filter() {
    }

    /**
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name
     * @param name the name of the song
     */
    public void setName(final String name) {
        this.name = name;
    }

    /**
     *
     * @return the album
     */
    public String getAlbum() {
        return album;
    }

    /**
     * Sets the album
     * @param album the album of the song
     */
    public void setAlbum(final String album) {
        this.album = album;
    }

    /**
     *
     * @return the tags
     */
    public ArrayList<String> getTags() {
        return tags;
    }

    /**
     * Sets the tags
     * @param tags the tags of the song
     */
    public void setTags(final ArrayList<String> tags) {
        this.tags = tags;
    }

    /**
     *
     * @return the lyrics
     */
    public String getLyrics() {
        return lyrics;
    }

    /**
     * Sets the lyrics
     * @param lyrics the lyrics of the song
     */
    public void setLyrics(final String lyrics) {
        this.lyrics = lyrics;
    }

    /**
     *
     * @return the genre
     */
    public String getGenre() {
        return genre;
    }

    /**
     * Sets the genre
     * @param genre the genre of the song
     */
    public void setGenre(final String genre) {
        this.genre = genre;
    }

    /**
     *
     * @return the release year
     */
    public String getReleaseYear() {
        return releaseYear;
    }

    /**
     * Sets the release year
     * @param releaseYear the release year of the song
     */
    public void setReleaseYear(final String releaseYear) {
        this.releaseYear = releaseYear;
    }

    /**
     *
     * @return the artist
     */
    public String getArtist() {
        return artist;
    }

    /**
     * Sets the artist
     * @param artist the artist of the song
     */
    public void setArtist(final String artist) {
        this.artist = artist;
    }

    /**
     *
     * @return the owner
     */
    public String getOwner() {
        return owner;
    }

    /**
     * Sets the owner
     * @param owner the owner of the song
     */
    public void setOwner(final String owner) {
        this.owner = owner;
    }

    /**
     *
     * @return the fields
     */
    public ArrayList<Object> buildFilterArray() {
        if (name != null) {
            filterArray.add(name);
            fields.add("name");
        }
        if (album != null) {
            filterArray.add(album);
            fields.add("album");
        }
        if (tags != null) {
            filterArray.add(tags);
            fields.add("tags");
        }
        if (lyrics != null) {
            filterArray.add(lyrics);
            fields.add("lyrics");
        }
        if (genre != null) {
            filterArray.add(genre);
            fields.add("genre");
        }
        if (releaseYear != null) {
            filterArray.add(releaseYear);
            fields.add("releaseYear");
        }
        if (artist != null) {
            filterArray.add(artist);
            fields.add("artist");
        }
        if (owner != null) {
            filterArray.add(owner);
            fields.add("owner");
        }
        return filterArray;
    }

    /**
     * Sets the fields
     * @return the fields
     */
    public ArrayList<String> getFields() {
        return fields;
    }

    /**
     * Sets the fields
     * @param fields the fields of the song
     */
    public void setFields(final ArrayList<String> fields) {
        this.fields = fields;
    }

    /**
     *
     * @return the filter array
     */
    public ArrayList<Object> getFilterArray() {
        return filterArray;
    }

    /**
     * Sets the filter array
     * @param filterArray the filter array
     */
    public void setFilterArray(final ArrayList<Object> filterArray) {
        this.filterArray = filterArray;
    }
}
