package AudioFiles;

import java.util.ArrayList;

public class HomePage implements Page{
	private ArrayList<Song> likedSongs = new ArrayList<>();
	private ArrayList<Playlist> followedPlaylists = new ArrayList<>();

	public HomePage() {
	}

	/**
	 * builds the list of liked songs for the home page
	 */
	public void setLikedSongs(ArrayList<Song> likedSongs) {
		this.likedSongs.clear();
		// get maximum the first 5 liked songs
		if (likedSongs.size() < 5) {
			this.likedSongs.addAll(likedSongs);
			return;
		}
		likedSongs.addAll(likedSongs.subList(0, 5));
	}


	/**
	 * builds the list of followed playlists for the home page
	 */
	public void setFollowedPlaylists(ArrayList<Playlist> followedPlaylists) {
		this.followedPlaylists.clear();
		// get maximum the first 5 followed playlists ordered by the total number of likes from the songs
		if (followedPlaylists.size() < 5) {
			this.followedPlaylists.addAll(followedPlaylists);
			this.followedPlaylists.sort((Playlist p1, Playlist p2) -> p2.getTotalLikes() - p1.getTotalLikes());
			return;
		}
		followedPlaylists.sort((Playlist p1, Playlist p2) -> p2.getTotalLikes() - p1.getTotalLikes());
		this.followedPlaylists.addAll(followedPlaylists.subList(0, 5));
	}

	/**
	 * @return the likedSongs array
	 */
	public ArrayList<Song> getLikedSongs() {
		return likedSongs;
	}

	/**
	 * @return the followedPlaylists array
	 */
	public ArrayList<Playlist> getFollowedPlaylists() {
		return followedPlaylists;
	}

	@Override
	public String printPage() {
		ArrayList<String> songNames = new ArrayList<>();
		ArrayList<String> playlistNames = new ArrayList<>();
		for (Song song : likedSongs) {
			songNames.add(song.getName());
		}
		for (Playlist playlist : followedPlaylists) {
			playlistNames.add(playlist.getName());
		}
		return "Liked songs:\n\t" + songNames.toString() + "\n\nFollowed playlists:\n\t" + playlistNames.toString();
	}

}
