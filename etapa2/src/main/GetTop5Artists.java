package main;

import AudioFiles.Album;
import AudioFiles.Artist;
import AudioFiles.Library;
import AudioFiles.User;

import java.util.ArrayList;

public class GetTop5Artists {
	private final ArrayList<Artist> top5Artists = new ArrayList<>();

	public GetTop5Artists() {
	}

	/**
	 * Sort the artists by the number of likes and by the creation timestamp
	 * and add the first 5 artists to the top5Artists array
	 */
	public void setTop5Artists(Library library) {
		final int maxSize = 5;

		// get the artists from the library
		ArrayList<Artist> artists = new ArrayList<>();

		for (User user : library.getUsers()) {
			if(user.getType().equals("artist")) {
				artists.add((Artist) user);
			}
		}

		// sort the array of artists
		for (int i = 0; i < artists.size() - 1; i++) {
			for (int j = i + 1; j < artists.size(); j++) {
				if (GetTotalLikes.getTotalLikes(artists.get(i))
						< GetTotalLikes.getTotalLikes(artists.get(j))) {
					Artist aux = artists.get(i);
					artists.set(i, artists.get(j));
					artists.set(j, aux);
				}
			}
		}

		for (int i = 0; i < artists.size() - 1; i++) {
			for (int j = i + 1; j < artists.size(); j++) {
				if (GetTotalLikes.getTotalLikes(artists.get(i))
						== GetTotalLikes.getTotalLikes(artists.get(j))) {
					// sort by name
					if (artists.get(i).getUsername().compareTo(artists.get(j).getUsername()) > 0) {
						Artist aux = artists.get(i);
						artists.set(i, artists.get(j));
						artists.set(j, aux);
					}
				}
			}
		}

		this.top5Artists.clear();

		// add the first 5 artists
		if (artists.size() < maxSize) {
			this.top5Artists.addAll(artists);
			return;
		}

		for (int i = 0; i < maxSize; i++) {
			this.top5Artists.add(artists.get(i));
		}
	}

	/**
	 * @return the top5Artists array
	 */
	public ArrayList<Artist> getTop5Artists() {
		return top5Artists;
	}

	private static class GetTotalLikes {
		public static int getTotalLikes(Artist artist) {
			int totalLikes = 0;

			for (Album album : artist.getAlbums()) {
				album.setLikes();
				totalLikes += album.getLikes();
			}

			return totalLikes;
		}
	}
}
