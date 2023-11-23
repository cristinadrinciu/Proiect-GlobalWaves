package main;

import fileio.input.SongInput;

import java.util.ArrayList;

public class User {
	private String username;
	private int age;
	private String city;
	private ArrayList<Playlist> playlists = new ArrayList<>();

	private ArrayList<Song> likedSongs = new ArrayList();

	private ArrayList<Playlist> followedPlaylists = new ArrayList<>();
	public Player player;

	public User() {
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(final String username) {
		this.username = username;
	}

	public int getAge() {
		return age;
	}

	public void setAge(final int age) {
		this.age = age;
	}

	public String getCity() {
		return city;
	}

	public void setCity(final String city) {
		this.city = city;
	}

	public ArrayList<Playlist> getPlaylists() {
		return playlists;
	}

	public void setPlaylists(ArrayList<Playlist> playlists) {
		this.playlists = playlists;
	}

	public ArrayList<Song> getLikedSongs() {
		return likedSongs;
	}

	public void setLikedSongs(ArrayList<Song> likedSongs) {
		this.likedSongs = likedSongs;
	}

	public ArrayList<Playlist> getFollowedPlaylists() {
		return followedPlaylists;
	}

	public void setFollowedPlaylists(ArrayList<Playlist> followedPlaylists) {
		this.followedPlaylists = followedPlaylists;
	}
}
