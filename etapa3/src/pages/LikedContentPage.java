package pages;

import audio.files.Playlist;
import audio.files.Song;
import user.types.User;

import java.util.ArrayList;

public class LikedContentPage implements Page {
	private ArrayList<Song> likedSongs;
	private ArrayList<Playlist> followedPlaylists;

	public LikedContentPage() {
		this.likedSongs = new ArrayList<>();
		this.followedPlaylists = new ArrayList<>();
	}

    /**
     * @return the liked songs
     */
	public ArrayList<Song> getLikedSongs() {
		return likedSongs;
	}

    /**
     * @return the followed playlists
     */
	public ArrayList<Playlist> getFollowedPlaylists() {
		return followedPlaylists;
	}

    /**
     * @param likedSongs the liked songs to set
     */
	public void setLikedSongs(final ArrayList<Song> likedSongs) {
		this.likedSongs.clear();
		this.likedSongs.addAll(likedSongs);
	}

    /**
     * @param followedPlaylists the followed playlists to set
     */
	public void setFollowedPlaylists(final ArrayList<Playlist> followedPlaylists) {
		this.followedPlaylists.clear();
		this.followedPlaylists.addAll(followedPlaylists);
	}

    /**
     * Sets the liked songs and followed playlists of the user
     * @param user the user whose liked songs and followed playlists will be set
     */
	public void setLikedContentPage(final User user) {
		this.setLikedSongs(user.getLikedSongs());
		this.setFollowedPlaylists(user.getFollowedPlaylists());
	}

    /**
     * Prints the liked songs and followed playlists of the user
     * @return the message to be printed
     */
	@Override
	public String printPage() {
		// Liked Songs:\n\t[songname1 - songartist1, songname2 - songartist2, …]\n\nFollowed Playlists:\n\t[playlistname1 - owner1, playlistname2 - owner2, …]
		String message = "Liked songs:\n\t[";
		for (Song song : this.likedSongs) {
			message += song.getName() + " - " + song.getArtist() + ", ";
		}
		// remove the last comma if there are songs
		if (!this.likedSongs.isEmpty()) {
			message = message.substring(0, message.length() - 2);
		}
		message += "]\n\nFollowed playlists:\n\t[";
		for (Playlist playlist : this.followedPlaylists) {
			message += playlist.getName() + " - "
                    + playlist.getOwner().getUsername() + ", ";
		}
		// remove the last comma if there are playlists
		if (!this.followedPlaylists.isEmpty()) {
			message = message.substring(0, message.length() - 2);
		}
		message += "]";
		return message;
	}
}