package main;

import fileio.input.LibraryInput;
import fileio.input.UserInput;

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

	public void switchVisibility(UserInput user, LibraryInput library) {
		if(user.getPlaylists().size() < playlistId) {
			return;
		}
		playlist = user.getPlaylists().get(playlistId - 1);
		if(playlist.getPublic()) {
			playlist.setPublic(false);
			ArrayList<UserInput> users = library.getUsers();
			if(user.player.timestamp == 1400)
				System.out.println(playlist.getName());

			// remove the playlist from the public player of all users, except the owner
			for(UserInput userParse : users) {
				if(userParse != user)
					userParse.player.playlists.remove(playlist);
			}
		}
		else {
			playlist.setPublic(true);
			// add the playlist in the public player of all users, except the owner
			ArrayList<UserInput> users = library.getUsers();

			for(UserInput userParse : users) {
				if(userParse != user)
					userParse.player.playlists.add(playlist);
			}
		}
	}

	public String message(UserInput user) {
		if(user.getPlaylists().size() < playlistId) {
			return "The specified playlist ID is too high.";
		}
		if(playlist.getPublic())
			return "Visibility status updated successfully to public.";
		return "Visibility status updated successfully to private.";
	}

}
