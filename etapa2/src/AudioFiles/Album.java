package AudioFiles;

import java.util.ArrayList;

public class Album extends AudioFile{
	private int releaseYear;
	private String description;
	private ArrayList<Song> songs;

	private Artist owner;
	private int likes;

	public int getReleaseYear() {
		return releaseYear;
	}

	public void setReleaseYear(int releaseYear) {
		this.releaseYear = releaseYear;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public ArrayList<Song> getSongs() {
		return songs;
	}

	public void setSongs(ArrayList<Song> songs) {
		this.songs = songs;
	}

	public Artist getOwner() {
		return owner;
	}

	public void setOwner(Artist owner) {
		this.owner = owner;
	}

	public int getLikes() {
		return likes;
	}

	public void setLikes() {
		// count the number of likes
		int likes = 0;
		for (Song song : this.songs) {
			likes += song.getLikes();
		}
	}
}
