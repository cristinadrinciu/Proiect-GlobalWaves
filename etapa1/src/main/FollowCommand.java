package main;

import fileio.input.AudioFile;

public class FollowCommand {
	String message;

	public FollowCommand() {
	}

	public void followPlaylist(User user, AudioFile audioFile) {
		if(audioFile == null) {
			message = "Please select a source before following or unfollowing.";
			return;
		}
		if(!(audioFile instanceof Playlist)) {
			message = "The selected source is not a playlist.";
			return;
		}
		Playlist playlist = (Playlist) audioFile;
		if(user.getPlaylists().contains(playlist)) {
			message = "You cannot follow or unfollow your own playlist.";
			return;
		}
		if (user.getFollowedPlaylists().contains(playlist)) {
			// unfollow the playlist
			int followers = playlist.getFollowers();
			followers --;
			playlist.setFollowers(followers);

			// remove from the list of followed playlists
			user.getFollowedPlaylists().remove(playlist);
			message = "Playlist unfollowed successfully.";
		} else {
			// otherwise follow the playlist
			int followers = playlist.getFollowers();
			followers ++;
			playlist.setFollowers(followers);

			// add in the list of followed playlist
			user.getFollowedPlaylists().add(playlist);
			message = "Playlist followed successfully.";
		}
	}
}
