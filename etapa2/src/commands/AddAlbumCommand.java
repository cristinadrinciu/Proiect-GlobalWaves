package commands;

import audio.files.Album;
import audio.files.Library;
import audio.files.Song;
import audio.files.*;
import main.InputCommands;
import publicFiles.PublicAlbums;
import visit.pattern.Visitable;
import visit.pattern.Visitor;
import user.types.Artist;
import user.types.User;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class AddAlbumCommand implements Visitable {
	private String name;
	private int releaseYear;
	private String description;
	private ArrayList<Song> songs;
	private String message;

	public AddAlbumCommand() {
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

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

	public String getMessage() {
		return message;
	}

	public void addAlbum(User user, Library library) {
		// check if the user is an artist
		if(!user.getType().equals("artist")) {
			message = user.getUsername() + " is not an artist.";
			return;
		}

		// if it is an artist, assign a variable for it
		Artist artist = (Artist) user;

		// check if the album already exists
		for(Album album : artist.getAlbums()) {
			if(album.getName().equals(name)) {
				// it exists
				message = artist.getUsername() + " has another album with the same name.";
				return;
			}
		}

		// check is the input songs for the album has duplicate songs
		if (DuplicateSongChecker.hasDuplicateSongs(songs)) {
			message = artist.getUsername() + " has the same song at least twice in this album.";
			return;
		}

		// otherwise, create the album
		Album album = new Album();
		album.setName(name);
		album.setReleaseYear(releaseYear);
		album.setDescription(description);
		album.setSongs(songs);
		album.setOwner(artist);

		// add the new songs in the library
		for(Song song : songs) {
			if (!library.getSongs().contains(song)) {
				song.setLikes(0);
				library.getSongs().add(song);
			}
		}

		// add the album to the artist
		artist.getAlbums().add(album);

		// add the album to the public albums
		PublicAlbums.getPublicAlbums().add(album);

		message = artist.getUsername() + " has added new album successfully.";

		// update the artist page
		artist.setArtistPage();

	}

	// Inner class for checking duplicate songs
	private static class DuplicateSongChecker {
		public static boolean hasDuplicateSongs(ArrayList<Song> songs) {
			Set<String> seenSongNames = new HashSet<>();

			for (Song song : songs) {
				// Check if the song name has been seen before
				if (!seenSongNames.add(song.getName())) {
					// Duplicate found
					return true;
				}
			}

			// No duplicates found
			return false;
		}
	}

	@Override
	public void accept(InputCommands command, Visitor visitor, Library library) {
		visitor.visit(command, this, library);
	}
}
