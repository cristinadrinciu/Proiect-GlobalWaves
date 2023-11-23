package main;

import java.util.ArrayList;

public class SwitchVisibilityCommand {
	private int playlistId;
	private Playlist playlist;
	public SwitchVisibilityCommand() {
	}

	public int getPlaylistId() {
		return playlistId;
	}

	public void setPlaylistId(int playlistId) {
		this.playlistId = playlistId;
	}

	public void switchVisibility(User user, Library library) {
		if(user.getPlaylists().size() < playlistId) {
			return;
		}
		playlist = user.getPlaylists().get(playlistId - 1);
		if(playlist.getPublic()) {
			playlist.setPublic(false);
			ArrayList<User> users = library.getUsers();
			if(user.player.timestamp == 1400)
				System.out.println(playlist.getName());

			// remove the playlist from the public player of all users, except the owner
			for(User userParse : users) {
				if(userParse != user)
					userParse.player.playlists.remove(playlist);
			}

			// remove the playlist from the public player
			PublicPlaylists.playlists.remove(playlist);
		}
		else {
			playlist.setPublic(true);
			// add the playlist in the public player of all users, except the owner
			ArrayList<User> users = library.getUsers();

			for(User userParse : users) {
				if(userParse != user)
					userParse.player.playlists.add(playlist);
			}

			// add the playlist in the public player
			PublicPlaylists.playlists.add(playlist);
		}
	}

	public String message(User user) {
		if(user.getPlaylists().size() < playlistId) {
			return "The specified playlist ID is too high.";
		}
		if(playlist.getPublic())
			return "Visibility status updated successfully to public.";
		return "Visibility status updated successfully to private.";
	}

}
