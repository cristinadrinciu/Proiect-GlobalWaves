package main;

import fileio.input.LibraryInput;
import fileio.input.SongInput;
import fileio.input.UserInput;

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

	public void shufflePlayer(UserInput user) {
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
	public String message(UserInput user) {
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

	public ArrayList<SongInput> shufflePlaylist(Playlist playlist) {
		ArrayList<SongInput> originalPlaylist = new ArrayList<>();
		ArrayList<SongInput> shuffledPlaylist = new ArrayList<>();

		// add the original order of the songs
		for(SongInput song : playlist.songs)
			originalPlaylist.add(song);
		Collections.shuffle(originalPlaylist, new Random(seed));
		shuffledPlaylist.addAll(originalPlaylist);
		return shuffledPlaylist;
	}
}
