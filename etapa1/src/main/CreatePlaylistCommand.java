package main;

import java.util.ArrayList;

public class CreatePlaylistCommand {
	private String playlistName;
	private Playlist playlist = new Playlist();

	public CreatePlaylistCommand() {
	}

	public String getPlaylistName() {
		return playlistName;
	}

	public void setPlaylistName(String playlistName) {
		this.playlistName = playlistName;
	}

	public boolean createPlaylist(User user, Library library) {
		// search if th playlist already exists
		if(user.getPlaylists() != null)
			for(Playlist playlistContor : user.getPlaylists())
				if(playlistName.equals(playlistContor.getName())) {
					// it already exists
					playlist = null;
					return false;
				}

		// otherwise, we create it
		playlist.setName(playlistName);
		playlist.setOwner(user);
		playlist.setPublic(true);
		playlist.songs = new ArrayList<>();

		user.getPlaylists().add(playlist);
		user.player.playlists.add(playlist);

		// add in the player of the other users
		ArrayList<User> users = library.getUsers();

		for(User userParse : users) {
			if(userParse != user)
				userParse.player.playlists.add(playlist);
		}

		// add in the public playlists
		PublicPlaylists.playlists.add(playlist);
		return true;
	}

	public String message(User user, Library library) {
		String message;
		boolean createdPlaylist = createPlaylist(user, library);
		if(!createdPlaylist)
			message = "A playlist with the same name already exists.";
		else
			message = "Playlist created successfully.";
		return message;
	}
}
