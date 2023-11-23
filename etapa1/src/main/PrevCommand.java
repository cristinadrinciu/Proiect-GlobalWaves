package main;

import fileio.input.EpisodeInput;

public class PrevCommand {
	String message;

	public PrevCommand() {
	}


	public void goToPrevSong (User user) {
		if (user.player.listenedTime == 0) {
			// can't go to the previous
			user.player.loadedItem = null;
			user.player.playingNow = null;
			user.player.remainingTime = 0;
			user.player.switchedTime = user.player.timestamp;
			user.player.paused = true;
			message = "Please load a source before returning to the previous track.";
		} else {
			// restart the song
			user.player.remainingTime = ((Song) user.player.playingNow).getDuration();
			user.player.listenedTime = 0;
			user.player.switchedTime = user.player.timestamp;
			user.player.paused = false;
			message = "Returned to previous track successfully. The current track is " + user.player.playingNow.getName() + ".";
		}
	}

	public void goToPrevPlaylist (User user) {
		if(user.player.listenedTime > 0) {
			// restart the song
			user.player.remainingTime = ((Song) user.player.playingNow).getDuration();
			user.player.listenedTime = 0;
			user.player.switchedTime = user.player.timestamp;
			user.player.paused = false;
			message = "Returned to previous track successfully. The current track is " + user.player.playingNow.getName() + ".";
		} else {
			// check if it is the first song in the playlist
			if(user.player.playingNow == ((Playlist) user.player.loadedItem).songs.get(0)) {
				// we pause the player
				user.player.loadedItem = null;
				user.player.playingNow = null;
				user.player.listenedTime = 0;
				user.player.switchedTime = user.player.timestamp;
				user.player.remainingTime = 0;
				user.player.paused = true;
				message = "Please load a source before returning to the previous track.";
			} else {
				// we go to the previous song
				user.player.playingNow = ((Playlist) user.player.loadedItem).songs.get(((Playlist) user.player.loadedItem).songs.indexOf((Song) user.player.playingNow) - 1);
				user.player.listenedTime = 0;
				user.player.remainingTime = ((Song) user.player.playingNow).getDuration();
				user.player.switchedTime = user.player.timestamp;
				user.player.paused = false;
				message = "Returned to previous track successfully. The current track is " + user.player.playingNow.getName() + ".";
			}
		}
	}

	public void goToPrevPodcast (User user) {
		if(user.player.listenedTime > 0) {
			// restart the episode
			user.player.remainingTime = ((EpisodeInput) user.player.playingNow).getDuration();
			user.player.listenedTime = 0;
			user.player.switchedTime = user.player.timestamp;
			user.player.paused = false;
			user.player.playingNow = ((Podcast) user.player.loadedItem).getEpisodes().get(((Podcast) user.player.loadedItem).getEpisodes().size() - 1);
			message = "Returned to previous track successfully. The current track is " + user.player.playingNow.getName() + ".";
		} else {
			// check if it is the first episode in the podcast
			if(user.player.playingNow == ((Podcast) user.player.loadedItem).getEpisodes().get(0)) {
				// stop the player
				user.player.loadedItem = null;
				user.player.playingNow = null;
				user.player.listenedTime = 0;
				user.player.switchedTime = user.player.timestamp;
				user.player.remainingTime = 0;
				user.player.paused = true;
				message = "Please load a source before returning to the previous track.";
			} else {
				// go to the previous episode
				user.player.playingNow = ((Podcast) user.player.loadedItem).getEpisodes().get(((Podcast) user.player.loadedItem).getEpisodes().indexOf((EpisodeInput) user.player.playingNow) - 1);
				user.player.listenedTime = 0;
				user.player.remainingTime = ((EpisodeInput) user.player.playingNow).getDuration();
				user.player.switchedTime = user.player.timestamp;
				user.player.paused = false;
				message = "Returned to previous track successfully. The current track is " + user.player.playingNow.getName() + ".";
			}
		}
	}
}
