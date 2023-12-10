package main;

import AudioFiles.*;

import java.util.ArrayList;

public class DeleteUser {
	private String username;

	private String message;

	public DeleteUser() {
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getMessage() {
		return this.message;
	}

	public void deleteUser(Library library) {
		// check if the user exists
		for (User user : library.getUsers()) {
			if (user.getUsername().equals(this.username)) {
				// check if it can be removed
				if(user.getType().equals("user")) {
					if(canBeDeleted.canDeleteUser(user, library)) {
						// remove all the connections of the user from the platform
						// remove user's playlists from the platform
						for(Playlist playlist : user.getPlaylists()) {
							PublicPlaylists.getPlaylists().remove(playlist);

							// delete also from each user's player's playlists
							for(User user1 : library.getUsers()) {
								Player player = user1.getPlayer();
								player.playlists.remove(playlist);
							}
						}

						// remove the playlist from the users' followed playlists
						for(User user1 : library.getUsers()) {
							user1.getFollowedPlaylists().removeAll(user.getPlaylists());
						}

						// remove the likes of the user from the platform
						for(Song song : user.getLikedSongs()) {
							song.setLikes(song.getLikes() - 1);
						}

						// remove the follows of the user from the platform
						for(Playlist playlist : user.getFollowedPlaylists()) {
							playlist.setFollowers(playlist.getFollowers() - 1);
						}

						// remove the user from the platform
						library.getUsers().remove(user);
						OnlineUsers.getOnlineUsers().remove(user);
						user = null;
						this.message = this.username + " was successfully deleted.";
						return;
					}
				} else if(user.getType().equals("artist")) {
					if(canBeDeleted.canDeleteArtist((Artist) user, library)) {
						// remove all the connections of the artist from the platform

						// remove artist's songs from the platform
						ArrayList<Song> songsToRemove = new ArrayList<>();
						for(Song song : library.getSongs()) {
							if(song.getArtist().equals(user.getUsername())) {
								songsToRemove.add(song);
							}
						}
						for(Song song : songsToRemove) {
							library.getSongs().remove(song);
						}

						// remove artist's albums from the platform
						for(Album album : ((Artist) user).getAlbums()) {
							PublicAlbums.getPublicAlbums().remove(album);
						}

						// remove the liked songs of each user owned by the artist
						for(User user1 : library.getUsers()) {
							songsToRemove.clear();
							for(Song song : user1.getLikedSongs()) {
								if(song.getArtist().equals(user.getUsername())) {
									songsToRemove.add(song);
								}
							}
							for(Song song : songsToRemove) {
								user1.getLikedSongs().remove(song);
							}
						}

						// remove the artist's playlists from the platform
						ArrayList<Playlist> playlistsToRemove = new ArrayList<>();
						for(Playlist playlist : PublicPlaylists.getPlaylists()) {
							if(playlist.getOwner().equals(user.getUsername())) {
								playlistsToRemove.add(playlist);
							}
						}
						for(Playlist playlist : playlistsToRemove) {
							PublicPlaylists.getPlaylists().remove(playlist);
						}

						// remove the artists playlists from each user's player
						for(User user1 : library.getUsers()) {
							playlistsToRemove.clear();
							for(Playlist playlist : user1.getPlayer().playlists) {
								if(playlist.getOwner().equals(user.getUsername())) {
									playlistsToRemove.add(playlist);
								}
							}
							for(Playlist playlist : playlistsToRemove) {
								user1.getPlayer().playlists.remove(playlist);
							}
						}

						// remove the artists playlists from each user's followed playlists
						for(User user1 : library.getUsers()) {
							playlistsToRemove.clear();
							for(Playlist playlist : user1.getFollowedPlaylists()) {
								if(playlist.getOwner().equals(user.getUsername())) {
									playlistsToRemove.add(playlist);
								}
							}
							for(Playlist playlist : playlistsToRemove) {
								user1.getFollowedPlaylists().remove(playlist);
							}
						}

						// remove the likes of the user from the platform
						for(Song song : user.getLikedSongs()) {
							song.setLikes(song.getLikes() - 1);
						}

						// remove the follows of the user from the platform
						for(Playlist playlist : user.getFollowedPlaylists()) {
							playlist.setFollowers(playlist.getFollowers() - 1);
						}

						// remove the artist from the platform
						library.getUsers().remove(user);
						OnlineUsers.getOnlineUsers().remove(user);
						user = null;
						this.message = this.username + " was successfully deleted.";
						return;
					}
				} else if (user.getType().equals("host")) {
					if(canBeDeleted.canDeleteHost((Host) user, library)) {
						// remove the connections of the host from the platform
						//System.out.println("here " + library.getUsers().get(0).getPlayer().timestamp);
						// remove the host's podcasts from the platform
						ArrayList<Podcast> podcastsToRemove = new ArrayList<>();
						for(Podcast podcast : library.getPodcasts()) {
							if(podcast.getOwner().equals(user.getUsername())) {
								podcastsToRemove.add(podcast);
							}
						}

						for(Podcast podcast : podcastsToRemove) {
							library.getPodcasts().remove(podcast);
						}

						// remove the host from the platform
						library.getUsers().remove(user);

						// remove the host from the online users
						OnlineUsers.getOnlineUsers().remove(user);

						user = null;
						this.message = this.username + " was successfully deleted.";
						return;
					}
				}
				// the user cannot be deleted
				this.message = this.username + " can't be deleted.";
				return;
			}
		}
		// the user doesn't exist
		this.message = "The username " + this.username + " doesn't exist.";
	}

	static class canBeDeleted {
		public static boolean canDeleteUser(User deleteUser, Library library) {
			// check if any user has interactions with this user
			// check if a user has in player a playlist owned by this user
			for(User user : library.getUsers()) {
				if(user.getUsername().equals(deleteUser.getUsername())) {
					continue;
				}
				Player player = user.getPlayer();
				if(player.loadedItem instanceof Playlist) {
					System.out.println(player.timestamp);
					if(deleteUser.getPlaylists().contains((Playlist) player.loadedItem)) {
						// cannot delete the user
						return false;
					}
				}
			}
			return true;
		}

		public static boolean canDeleteArtist(Artist deleteArtist, Library library) {
			// check if any user has interactions with this artist
			// check if a user has in player an album owned by this artist
			for(User user : library.getUsers()) {
				if(user.getUsername().equals(deleteArtist.getUsername())) {
					continue;
				}
				Player player = user.getPlayer();

				if(player.loadedItem instanceof Album) {
					if(deleteArtist.getAlbums().contains((Album) player.loadedItem)) {
						// cannot delete the artist
						return false;
					}
				}

				// if there is a song in the player that is owned by this artist
				if(player.playingNow instanceof Song) {
					if(((Song) player.playingNow).getArtist().equals(deleteArtist.getUsername())) {
						// cannot delete the artist
						return false;
					}
				}

				// if any user's current page is the artist's page
				if(user.getCurrentPage() == deleteArtist.getArtistPage()) {
					return false;
				}
			}
			return true;
		}

		public static boolean canDeleteHost(Host deleteHost, Library library) {
			// check if any user has interactions with this host
			// check if a user has in player a podcast owned by this host
			for(User user : library.getUsers()) {
				if(user.getUsername().equals(deleteHost.getUsername())) {
					continue;
				}
				Player player = user.getPlayer();

				if(player.loadedItem instanceof Podcast) {
					if(deleteHost.getPodcasts().contains((Podcast) player.loadedItem)) {
						// cannot delete the host
						return false;
					}
				}

				// if any user's current page is the host's page
				if(user.getCurrentPage() == deleteHost.getHostPage()) {
					return false;
				}
			}
			return true;
		}
	}

}
