package main;

import java.util.ArrayList;
import fileio.input.AudioFile;

public class Song extends AudioFile{
	private Integer duration;
	private String album;
	private ArrayList<String> tags;
	private String lyrics;
	private String genre;
	private Integer releaseYear;
	private String artist;
	private int likes;

	public Song() {
	}

	public Integer getDuration() {
		return duration;
	}

	public void setDuration(final Integer duration) {
		this.duration = duration;
	}

	public String getAlbum() {
		return album;
	}

	public void setAlbum(final String album) {
		this.album = album;
	}

	public ArrayList<String> getTags() {
		return tags;
	}

	public void setTags(final ArrayList<String> tags) {
		this.tags = tags;
	}

	public String getLyrics() {
		return lyrics;
	}

	public void setLyrics(final String lyrics) {
		this.lyrics = lyrics;
	}

	public String getGenre() {
		return genre;
	}

	public void setGenre(final String genre) {
		this.genre = genre;
	}

	public int getReleaseYear() {
		return releaseYear;
	}

	public void setReleaseYear(final int releaseYear) {
		this.releaseYear = releaseYear;
	}

	public String getArtist() {
		return artist;
	}

	public void setArtist(final String artist) {
		this.artist = artist;
	}

	public int getLikes() {
		return likes;
	}

	public void setLikes(int likes) {
		this.likes = likes;
	}
}
