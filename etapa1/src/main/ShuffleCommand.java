package main;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class ShuffleCommand {

	private int seed;

	public ShuffleCommand() {
	}

	public int getSeed() {
		return seed;
	}

	public void setSeed(int seed) {
		this.seed = seed;
	}

	public void shufflePlayer(User user) {
		if(user.player.loadedItem == null) {
			user.player.shuffle = false;
			return;
		}
		if(!(user.player.loadedItem instanceof Playlist)) {
			user.player.shuffle = false;
			return;
		}
		// Deactivate shuffle if no seed is provided
		if (seed == 0) {
			user.player.shuffle = false;
		} else {
			// Activate shuffle if a seed is provided
			user.player.shuffle = true;
		}
	}
	public String message(User user) {
		String message = null;
		if(user.player.loadedItem == null) {
			message = "Please load a source before using the shuffle function.";
			return message;
		}
		if(!(user.player.loadedItem instanceof Playlist)) {
			message = "The loaded source is not a playlist.";
			return message;
		}
		if(user.player.shuffle)
			message = "Shuffle function activated successfully.";
		else
			message = "Shuffle function deactivated successfully.";

		return message;
	}

	public ArrayList<Song> shufflePlaylist(Playlist playlist) {
		ArrayList<Song> originalPlaylist = new ArrayList<>();
		ArrayList<Song> shuffledPlaylist = new ArrayList<>();

		// add the original order of the songs
		for(Song song : playlist.songs)
			originalPlaylist.add(song);
		Collections.shuffle(originalPlaylist, new Random(seed));
		shuffledPlaylist.addAll(originalPlaylist);
		return shuffledPlaylist;
	}
}
