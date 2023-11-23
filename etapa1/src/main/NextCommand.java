package main;

import fileio.input.EpisodeInput;

import java.util.ArrayList;

public class NextCommand {
	String message;

	/**
	 *
	 * @param user from which we get the player
	 */

	public void goToNextSong(User user) {
		// verify what we are currently playing
		if (user.player.loadedItem instanceof Song) {
			if (user.player.repeatState == 0 ) {
				// if we are not repeating, then we stop the player
				user.player.playingNow = null;
				user.player.loadedItem = null;
				user.player.listenedTime = 0;
				user.player.remainingTime = 0;
				user.player.paused = true;
				user.player.switchedTime = user.player.timestamp;
				message = "Please load a source before skipping to the next track.";
			} else if (user.player.repeatState == 2) {
				// if we are repeating the song, then we reset the listened time
				user.player.listenedTime = 0;
				user.player.remainingTime = ((Song) user.player.playingNow).getDuration();
				user.player.switchedTime = user.player.timestamp;
				message = "Skipped to next track successfully. The current track is " + user.player.playingNow.getName() + ".";
			} else if (user.player.repeatState == 1) {
				// check the repeatOnce state
				if(user.player.repeatedOnce == 0) {
					// if we are not repeating the song, then we repeat it
					user.player.repeatedOnce = 1;
					user.player.listenedTime = 0;
					user.player.remainingTime = ((Song) user.player.playingNow).getDuration();
					user.player.switchedTime = user.player.timestamp;
					message = "Skipped to next track successfully. The current track is " + user.player.playingNow.getName() + ".";
				} else {
					// if we are repeating the song, then we stop the player
					user.player.playingNow = null;
					user.player.loadedItem = null;
					user.player.listenedTime = 0;
					user.player.remainingTime = 0;
					user.player.paused = true;
					user.player.switchedTime = user.player.timestamp;
					message = "Please load a source before skipping to the next track.";
				}
			}
		}
	}

	/**
	 *
	 * @param user from which we get the player
	 */


	public void goToNextPlaylist(User user) {
		// verify what we are currently playing
		if (user.player.loadedItem instanceof Playlist) {
			Playlist playlist = (Playlist) user.player.loadedItem;
			if (user.player.repeatState == 0) {
				if (!user.player.shuffle) {
					// check if we are at the end of the playlist
					if (playlist.songs.indexOf((Song) user.player.playingNow) == playlist.songs.size() - 1) {
						// if we are at the end, then we stop the player
						user.player.playingNow = null;
						user.player.loadedItem = null;
						user.player.listenedTime = 0;
						user.player.remainingTime = 0;
						user.player.paused = true;
						user.player.switchedTime = user.player.timestamp;
						message = "Please load a source before skipping to the next track.";
					} else {
						// if we are not at the end, then we go to the next song
						user.player.playingNow = playlist.songs.get(playlist.songs.indexOf((Song) user.player.playingNow) + 1);
						user.player.listenedTime = 0;
						user.player.remainingTime = ((Song) user.player.playingNow).getDuration();
						user.player.switchedTime = user.player.timestamp;
						message = "Skipped to next track successfully. The current track is " + user.player.playingNow.getName() + ".";
					}
				} else {
					// check if we are at the end of the playlist
					ArrayList<Song> shuffledPlaylist = user.player.shuffledPlaylist;
					if (shuffledPlaylist.indexOf((Song) user.player.playingNow) == shuffledPlaylist.size() - 1) {
						// if we are at the end, then we stop the player
						user.player.playingNow = null;
						user.player.loadedItem = null;
						user.player.listenedTime = 0;
						user.player.remainingTime = 0;
						user.player.paused = true;
						user.player.switchedTime = user.player.timestamp;
						message = "Please load a source before skipping to the next track.";
					} else {
						// if we are not at the end, then we go to the next song
						user.player.playingNow = shuffledPlaylist.get(shuffledPlaylist.indexOf((Song) user.player.playingNow) + 1);
						user.player.listenedTime = 0;
						user.player.remainingTime = ((Song) user.player.playingNow).getDuration();
						user.player.switchedTime = user.player.timestamp;
						message = "Skipped to next track successfully. The current track is " + user.player.playingNow.getName() + ".";
					}

				}
			}
			if (user.player.repeatState == 1) {
				// the playlist repeats itself
				if(!user.player.shuffle) {
					// check if we are at the end of the playlist
					if (playlist.songs.indexOf((Song) user.player.playingNow) == playlist.songs.size() - 1) {
						// if we are at the end, then we go to the first song
						user.player.playingNow = playlist.songs.get(0);
						user.player.listenedTime = 0;
						user.player.remainingTime = ((Song) user.player.playingNow).getDuration();
						user.player.switchedTime = user.player.timestamp;
						message = "Skipped to next track successfully. The current track is " + user.player.playingNow.getName() + ".";
					} else {
						// if we are not at the end, then we go to the next song
						user.player.playingNow = playlist.songs.get(playlist.songs.indexOf((Song) user.player.playingNow) + 1);
						user.player.listenedTime = 0;
						user.player.remainingTime = ((Song) user.player.playingNow).getDuration();
						user.player.switchedTime = user.player.timestamp;
						message = "Skipped to next track successfully. The current track is " + user.player.playingNow.getName() + ".";
					}
				} else {
					// check if we are at the end of the playlist
					ArrayList<Song> shuffledPlaylist = user.player.shuffledPlaylist;
					if (shuffledPlaylist.indexOf((Song) user.player.playingNow) == shuffledPlaylist.size() - 1) {
						// if we are at the end, then we go to the first song
						user.player.playingNow = shuffledPlaylist.get(0);
						user.player.listenedTime = 0;
						user.player.remainingTime = ((Song) user.player.playingNow).getDuration();
						user.player.switchedTime = user.player.timestamp;
						message = "Skipped to next track successfully. The current track is " + user.player.playingNow.getName() + ".";
					} else {
						// if we are not at the end, then we go to the next song
						user.player.playingNow = shuffledPlaylist.get(shuffledPlaylist.indexOf((Song) user.player.playingNow) + 1);
						user.player.listenedTime = 0;
						user.player.remainingTime = ((Song) user.player.playingNow).getDuration();
						user.player.switchedTime = user.player.timestamp;
						message = "Skipped to next track successfully. The current track is " + user.player.playingNow.getName() + ".";
					}
				}
			}
			if(user.player.repeatState == 2) {
				// the song repeats itself
				user.player.listenedTime = 0;
				user.player.remainingTime = ((Song) user.player.playingNow).getDuration();
				user.player.switchedTime = user.player.timestamp;
				message = "Skipped to next track successfully. The current track is " + user.player.playingNow.getName() + ".";
			}
		}
	}

	/**
	 *
	 * @param user from which we get the player
	 */

	public void goToNextPodcast(User user) {
		// verify what we are currently playing
		if(user.player.loadedItem instanceof Podcast) {
			if(user.player.repeatState == 0) {
				// check if it is the last episode
				EpisodeInput episode = (EpisodeInput) user.player.playingNow;
				if(((Podcast) user.player.loadedItem).getEpisodes().indexOf(episode) ==
						((Podcast) user.player.loadedItem).getEpisodes().size() - 1) {
					// if it is the last episode, then we stop the player
					user.player.playingNow = null;
					user.player.loadedItem = null;
					user.player.listenedTime = 0;
					user.player.remainingTime = 0;
					user.player.paused = true;
					user.player.switchedTime = user.player.timestamp;
					message = "Please load a source before skipping to the next track.";
				} else {
					// if it is not the last episode, then we go to the next episode
					user.player.playingNow = ((Podcast) user.player.loadedItem).getEpisodes().get(
							((Podcast) user.player.loadedItem).getEpisodes().indexOf(episode) + 1);
					user.player.listenedTime = 0;
					user.player.remainingTime = ((EpisodeInput) user.player.playingNow).getDuration();
					user.player.switchedTime = user.player.timestamp;
					message = "Skipped to next track successfully. The current track is " + user.player.playingNow.getName() + ".";
				}
			}
			if(user.player.repeatState == 2) {
				// the podcast repeats itself
				// check if it is the last episode
				EpisodeInput episode = (EpisodeInput) user.player.playingNow;
				if(((Podcast) user.player.loadedItem).getEpisodes().indexOf(episode) ==
						((Podcast) user.player.loadedItem).getEpisodes().size() - 1) {
					// if it is the last episode, then we go to the first episode
					user.player.playingNow = ((Podcast) user.player.loadedItem).getEpisodes().get(0);
					user.player.listenedTime = 0;
					user.player.remainingTime = ((EpisodeInput) user.player.playingNow).getDuration();
					user.player.switchedTime = user.player.timestamp;
					message = "Skipped to next track successfully. The current track is " + user.player.playingNow.getName() + ".";
				} else {
					// if it is not the last episode, then we go to the next episode
					user.player.playingNow = ((Podcast) user.player.loadedItem).getEpisodes().get(
							((Podcast) user.player.loadedItem).getEpisodes().indexOf(episode) + 1);
					user.player.listenedTime = 0;
					user.player.remainingTime = ((EpisodeInput) user.player.playingNow).getDuration();
					user.player.switchedTime = user.player.timestamp;
					message = "Skipped to next track successfully. The current track is " + user.player.playingNow.getName() + ".";
				}
			}
			if(user.player.repeatState == 1) {
				// the podcast repeats itself
				if(user.player.repeatedOnce == 0) {
					// not repeating
					// check if it is the last episode
					EpisodeInput episode = (EpisodeInput) user.player.playingNow;
					if(((Podcast) user.player.loadedItem).getEpisodes().indexOf(episode) ==
							((Podcast) user.player.loadedItem).getEpisodes().size() - 1) {
						// if it is the last episode, then we go to the first episode
						user.player.playingNow = ((Podcast) user.player.loadedItem).getEpisodes().get(0);
						user.player.listenedTime = 0;
						user.player.remainingTime = ((EpisodeInput) user.player.playingNow).getDuration();
						user.player.switchedTime = user.player.timestamp;
						user.player.repeatedOnce = 1;
						message = "Skipped to next track successfully. The current track is " + user.player.playingNow.getName() + ".";
					} else {
						// if it is not the last episode, then we go to the next episode
						user.player.playingNow = ((Podcast) user.player.loadedItem).getEpisodes().get(
								((Podcast) user.player.loadedItem).getEpisodes().indexOf(episode) + 1);
						user.player.listenedTime = 0;
						user.player.remainingTime = ((EpisodeInput) user.player.playingNow).getDuration();
						user.player.switchedTime = user.player.timestamp;
						user.player.repeatedOnce = 1;
						message = "Skipped to next track successfully. The current track is " + user.player.playingNow.getName() + ".";
					}
				} else {
					// is repeating
					//check if it is the last episode
					EpisodeInput episode = (EpisodeInput) user.player.playingNow;
					if(((Podcast) user.player.loadedItem).getEpisodes().indexOf(episode) ==
							((Podcast) user.player.loadedItem).getEpisodes().size() - 1) {
						// if it is the last episode, then we stop the player
						user.player.playingNow = null;
						user.player.loadedItem = null;
						user.player.listenedTime = 0;
						user.player.remainingTime = 0;
						user.player.paused = true;
						user.player.switchedTime = user.player.timestamp;
						message = "Please load a source before skipping to the next track.";
					} else {
						// if it is not the last episode, then we go to the next episode
						user.player.playingNow = ((Podcast) user.player.loadedItem).getEpisodes().get(
								((Podcast) user.player.loadedItem).getEpisodes().indexOf(episode) + 1);
						user.player.listenedTime = 0;
						user.player.remainingTime = ((EpisodeInput) user.player.playingNow).getDuration();
						user.player.switchedTime = user.player.timestamp;
						message = "Skipped to next track successfully. The current track is " + user.player.playingNow.getName() + ".";
					}
				}
			}
		}
	}
}
