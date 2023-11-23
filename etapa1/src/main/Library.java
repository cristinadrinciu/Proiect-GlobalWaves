package main;

import fileio.input.LibraryInput;
import fileio.input.PodcastInput;
import fileio.input.SongInput;
import fileio.input.UserInput;

import java.util.ArrayList;

public class Library {
	private ArrayList<Song> songs;
	private ArrayList<Podcast> podcasts;
	private ArrayList<User> users;

	public Library() {
	}

	public ArrayList<Song> getSongs() {
		return songs;
	}

	public void setSongs(final ArrayList<Song> songs) {
		this.songs = songs;
	}

	public ArrayList<Podcast> getPodcasts() {
		return podcasts;
	}

	public void setPodcasts(final ArrayList<Podcast> podcasts) {
		this.podcasts = podcasts;
	}

	public ArrayList<User> getUsers() {
		return users;
	}

	public void setUsers(final ArrayList<User> users) {
		this.users = users;
	}

	public void createLibrary(LibraryInput library) {
		songs = new ArrayList<>();
		podcasts = new ArrayList<>();
		users = new ArrayList<>();

		for (SongInput song : library.getSongs()) {
			Song newSong = new Song();
			newSong.setName(song.getName());
			newSong.setArtist(song.getArtist());
			newSong.setAlbum(song.getAlbum());
			newSong.setReleaseYear(song.getReleaseYear());
			newSong.setTags(song.getTags());
			newSong.setDuration(song.getDuration());
			newSong.setLyrics(song.getLyrics());
			newSong.setGenre(song.getGenre());

			songs.add(newSong);
		}

		for (PodcastInput podcast : library.getPodcasts()) {
			Podcast newPodcast = new Podcast();
			newPodcast.setName(podcast.getName());
			newPodcast.setOwner(podcast.getOwner());
			newPodcast.setEpisodes(podcast.getEpisodes());

			podcasts.add(newPodcast);
		}

		for (UserInput user : library.getUsers()) {
			User newUser = new User();
			newUser.setUsername(user.getUsername());
			newUser.setAge(user.getAge());
			newUser.setCity(user.getCity());

			users.add(newUser);
		}


	}
}
