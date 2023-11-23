package main;

import java.util.ArrayList;
import java.util.Objects;

public class Filter {
    String name = null;
    String album = null;
    ArrayList<String> tags = null;
    String lyrics = null;
    String genre = null;
    String releaseYear = null;
    String artist = null;
    String owner = null;


    ArrayList<String> fields =  new ArrayList<>();
    ArrayList<Object> filterArray = new ArrayList<>();

    public Filter(){
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public ArrayList<String> getTags() {
        return tags;
    }

    public void setTags(ArrayList<String> tags) {
        this.tags = tags;
    }

    public String getLyrics() {
        return lyrics;
    }

    public void setLyrics(String lyrics) {
        this.lyrics = lyrics;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(String releaseYear) {
        this.releaseYear = releaseYear;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public ArrayList<Object> buildFilterArray() {
        if(name != null) {
            filterArray.add(name);
            fields.add("name");
        }
        if(album != null) {
            filterArray.add(album);
            fields.add("album");
        }
        if(tags != null) {
            filterArray.add(tags);
            fields.add("tags");
        }
        if(lyrics != null) {
            filterArray.add(lyrics);
            fields.add("lyrics");
        }
        if(genre != null) {
            filterArray.add(genre);
            fields.add("genre");
        }
        if(releaseYear != null) {
            filterArray.add(releaseYear);
            fields.add("releaseYear");
        }
        if(artist != null) {
            filterArray.add(artist);
            fields.add("artist");
        }
        if(owner != null) {
            filterArray.add(owner);
            fields.add("owner");
        }
        return filterArray;
    }
}
