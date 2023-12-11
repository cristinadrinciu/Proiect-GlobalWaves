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
		// get maximum the first 5 liked songs ordered by the total number of likes
		if (likedSongs.size() < 5) {
			this.likedSongs.addAll(likedSongs);
			this.likedSongs.sort((Song s1, Song s2) -> s2.getLikes() - s1.getLikes());

			// in case of equality, order by the order in the given list(the given list is ordered by the time they were likes, from oldest to newest)
			for (int i = 0; i < this.likedSongs.size() - 1; i++) {
				if (this.likedSongs.get(i).getLikes() == this.likedSongs.get(i + 1).getLikes()) {
					int index1 = likedSongs.indexOf(this.likedSongs.get(i));
					int index2 = likedSongs.indexOf(this.likedSongs.get(i + 1));
					if (index1 > index2) {
						Song aux = this.likedSongs.get(i);
						this.likedSongs.set(i, this.likedSongs.get(i + 1));
						this.likedSongs.set(i + 1, aux);
					}
				}
			}
			return;
		}

		this.likedSongs.addAll(likedSongs);
		this.likedSongs.sort((Song s1, Song s2) -> s2.getLikes() - s1.getLikes());

		// in case of equality, order by the order in the given list(the given list is ordered by the time they were likes, from oldest to newest)
		for (int i = 0; i < this.likedSongs.size() - 1; i++) {
			if (this.likedSongs.get(i).getLikes() == this.likedSongs.get(i + 1).getLikes()) {
				int index1 = likedSongs.indexOf(this.likedSongs.get(i));
				int index2 = likedSongs.indexOf(this.likedSongs.get(i + 1));
				if (index1 > index2) {
					Song aux = this.likedSongs.get(i);
					this.likedSongs.set(i, this.likedSongs.get(i + 1));
					this.likedSongs.set(i + 1, aux);
				}
			}
		}

		// keep just the first 5 songs
		this.likedSongs = new ArrayList<>(this.likedSongs.subList(0, 5));
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

		this.followedPlaylists.addAll(followedPlaylists);
		this.followedPlaylists.sort((Playlist p1, Playlist p2) -> p2.getTotalLikes() - p1.getTotalLikes());

		// in case of equality, order by the order in the given list(the given list is ordered by the time they were followed, from oldest to newest)
		for (int i = 0; i < this.followedPlaylists.size() - 1; i++) {
			if (this.followedPlaylists.get(i).getTotalLikes() == this.followedPlaylists.get(i + 1).getTotalLikes()) {
				int index1 = followedPlaylists.indexOf(this.followedPlaylists.get(i));
				int index2 = followedPlaylists.indexOf(this.followedPlaylists.get(i + 1));
				if (index1 > index2) {
					Playlist aux = this.followedPlaylists.get(i);
					this.followedPlaylists.set(i, this.followedPlaylists.get(i + 1));
					this.followedPlaylists.set(i + 1, aux);
				}
			}
		}

		// keep just the first 5 playlists
		this.followedPlaylists = new ArrayList<>(this.followedPlaylists.subList(0, 5));
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
