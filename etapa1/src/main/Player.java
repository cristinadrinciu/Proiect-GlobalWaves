package main;

import fileio.input.AudioFile;
import fileio.input.EpisodeInput;
import fileio.input.PodcastInput;
import fileio.input.SongInput;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Player {
	int timestamp;
	AudioFile loadedItem;
	AudioFile playingNow;
	int remainingTime;
	int listenedTime;
	int switchedTime; // its value is the timestamp when a user Played or Paused the loadedItem

	boolean paused = false;
	boolean shuffle = false;
	int repeatState = 0;

	int repeatedOnce = 0;

	public ArrayList<SongInput> shuffledPlaylist;

	public ArrayList<Playlist> playlists = new ArrayList<>(); // list of public playlists and user's created playlist

	public Player(){
	}

	public void setPlayingNow() {
		if(loadedItem instanceof SongInput) {
			// from the beginning
			playingNow = loadedItem;
			remainingTime = ((SongInput) loadedItem).getDuration();
			paused = false;
			listenedTime = 0;
			switchedTime = timestamp;
			return;
		}
		if(loadedItem instanceof Playlist) {
			// from the beginning
			playingNow = ((Playlist) loadedItem).songs.get(0);
			remainingTime = ((SongInput) playingNow).getDuration();
			paused = false;
			listenedTime = 0;
			switchedTime = timestamp;
			return;
		}
		if(loadedItem instanceof PodcastInput) {
			// check if it was played before
			if(((PodcastInput) loadedItem).getLastEpisode() != null) {
				playingNow = ((PodcastInput) loadedItem).getEpisodes().get(0);
				listenedTime = ((PodcastInput) loadedItem).getWatchedTime();
				remainingTime = ((EpisodeInput) playingNow).getDuration() - listenedTime;
			} else {
				playingNow = ((PodcastInput) loadedItem).getEpisodes().get(0);
				listenedTime = 0;
				remainingTime = ((EpisodeInput) playingNow).getDuration();
			}
			paused = false;
			switchedTime = timestamp;
		}
	}

	public void initializePlayer() {
		loadedItem = null;
		playingNow = null;
		remainingTime = 0;
		listenedTime = 0;
		switchedTime = 0;
		paused = true;
		repeatState = 0;
		// timestamp remains the same
	}

	public void setRemainingTime() {
		if (!paused) {
			int elapsedTime = timestamp - switchedTime;
			if (playingNow instanceof SongInput) {
				listenedTime += elapsedTime;
				remainingTime = ((SongInput) playingNow).getDuration() - listenedTime;
			} else if (playingNow instanceof EpisodeInput) {
				listenedTime += elapsedTime;
				remainingTime = ((EpisodeInput) playingNow).getDuration() - listenedTime;
				((PodcastInput) loadedItem). setLastEpisode((EpisodeInput) playingNow);
				((PodcastInput) loadedItem).setWatchedTime(listenedTime);
			}
		}

		switchedTime = timestamp;
		if (remainingTime < 0) {
			if (loadedItem instanceof SongInput) {
				paused = true;
				listenedTime = 0;
				loadedItem = null;
				remainingTime = 0;
				playingNow = null;
				return;
			}
			if (loadedItem instanceof Playlist) {
				while(remainingTime < 0) {
					if(!shuffle) {
						int index = ((Playlist) loadedItem).songs.indexOf((SongInput) playingNow);
						if (index == ((Playlist) loadedItem).songs.size() - 1) {
							paused = true;
							listenedTime = 0;
							loadedItem = null;
							remainingTime = 0;
							playingNow = null;
							return;
						}
						listenedTime = -remainingTime; // time listened from the next track
						playingNow = ((Playlist) loadedItem).songs.get(index + 1); // go to the next track
						remainingTime = ((SongInput) playingNow).getDuration() - listenedTime;
					}
					else {
						int index = shuffledPlaylist.indexOf((SongInput) playingNow);
						if (index == shuffledPlaylist.size() - 1) {
							paused = true;
							listenedTime = 0;
							loadedItem = null;
							remainingTime = 0;
							playingNow = null;
							return;
						}
						listenedTime = -remainingTime; // time listened from the next track
						playingNow = shuffledPlaylist.get(index + 1); // go to the next track
						remainingTime = ((SongInput) playingNow).getDuration() - listenedTime;
					}
				}
			}
			 if (loadedItem instanceof PodcastInput) {
				 while(remainingTime < 0) {
					 int index = ((PodcastInput) loadedItem).getEpisodes().indexOf((EpisodeInput) playingNow);
					 if(index == ((PodcastInput) loadedItem).getEpisodes().size() - 1) {
						 paused = true;
						 listenedTime = 0;
						 loadedItem = null;
						 remainingTime = 0;
						 playingNow = null;
						 return;
					 }
					 listenedTime = -remainingTime; // time listened from the next track
					 playingNow = ((PodcastInput) loadedItem).getEpisodes().get(index + 1); // go to the next track
					 remainingTime = ((EpisodeInput) playingNow).getDuration() - listenedTime;
				 }
			 }
		}
	}

	public void setRemainingTimeRepeat2() {
		if (!paused) {
			int elapsedTime = timestamp - switchedTime;
			if (playingNow instanceof SongInput) {
				listenedTime += elapsedTime;
				remainingTime = ((SongInput) playingNow).getDuration() - listenedTime;
			} else if (playingNow instanceof EpisodeInput) {
				listenedTime += elapsedTime;
				remainingTime = ((EpisodeInput) playingNow).getDuration() - listenedTime;
				((PodcastInput) loadedItem). setLastEpisode((EpisodeInput) playingNow);
				((PodcastInput) loadedItem).setWatchedTime(listenedTime);
			}
		}

		switchedTime = timestamp;
		if (remainingTime < 0) {
			if (loadedItem instanceof SongInput) {
				while(remainingTime < 0) {
					listenedTime = -remainingTime;
					remainingTime = ((SongInput) playingNow).getDuration() - listenedTime;
				}
			}
			if (loadedItem instanceof PodcastInput) {
				while (remainingTime < 0) {
					int index = ((PodcastInput) loadedItem).getEpisodes().indexOf((EpisodeInput) playingNow);
					if (index == ((PodcastInput) loadedItem).getEpisodes().size() - 1)
						playingNow = ((PodcastInput) loadedItem).getEpisodes().get(0);
					else
						playingNow = ((PodcastInput) loadedItem).getEpisodes().get(index + 1);
					listenedTime = -remainingTime; // time listened from the next track
					remainingTime = ((EpisodeInput) playingNow).getDuration() - listenedTime;
				}
			}
			if(loadedItem instanceof Playlist) {
				while (remainingTime < 0) {
					listenedTime = -remainingTime;
					remainingTime = ((SongInput) playingNow).getDuration() - listenedTime;
				}
			}
		}
	}

	public void setRemainingTimeRepeat1() {
		if (!paused) {
			int elapsedTime = timestamp - switchedTime;
			if (playingNow instanceof SongInput) {
				listenedTime += elapsedTime;
				remainingTime = ((SongInput) playingNow).getDuration() - listenedTime;
			} else if (playingNow instanceof EpisodeInput) {
				listenedTime += elapsedTime;
				remainingTime = ((EpisodeInput) playingNow).getDuration() - listenedTime;
				((PodcastInput) loadedItem). setLastEpisode((EpisodeInput) playingNow);
				((PodcastInput) loadedItem).setWatchedTime(listenedTime);
			}
		}

		switchedTime = timestamp;
		if (remainingTime < 0) {
			if (loadedItem instanceof SongInput) {
				while(remainingTime < 0) {
					if (repeatedOnce == 0) {
						listenedTime = -remainingTime;
						remainingTime = ((SongInput) playingNow).getDuration() - listenedTime;
						repeatedOnce = 1;
						repeatState = 0;
					} else {
						paused = true;
						listenedTime = 0;
						loadedItem = null;
						remainingTime = 0;
						playingNow = null;
						repeatedOnce = 0;
						repeatState = 0;
						return;
					}
				}
			}
			if (loadedItem instanceof PodcastInput) {
				while (remainingTime < 0) {
					if (repeatedOnce == 0) {
						int index = ((PodcastInput) loadedItem).getEpisodes().indexOf((EpisodeInput) playingNow);
						if (index == ((PodcastInput) loadedItem).getEpisodes().size() - 1) {
							playingNow = ((PodcastInput) loadedItem).getEpisodes().get(0);
							repeatedOnce = 1;
						}
						else
							playingNow = ((PodcastInput) loadedItem).getEpisodes().get(index + 1);
						listenedTime = -remainingTime; // time listened from the next track
						remainingTime = ((EpisodeInput) playingNow).getDuration() - listenedTime;
					} else {
						int index = ((PodcastInput) loadedItem).getEpisodes().indexOf((EpisodeInput) playingNow);
						if (index == ((PodcastInput) loadedItem).getEpisodes().size() - 1) {
							paused = true;
							listenedTime = 0;
							loadedItem = null;
							remainingTime = 0;
							playingNow = null;
							repeatedOnce = 0;
							repeatState = 0;
							return;
						}
						listenedTime = -remainingTime; // time listened from the next track
						playingNow = ((PodcastInput) loadedItem).getEpisodes().get(index + 1); // go to the next track
						remainingTime = ((EpisodeInput) playingNow).getDuration() - listenedTime;
					}
				}
			}
			if(loadedItem instanceof Playlist) {
				while (remainingTime < 0) {
					if (!shuffle) {
						if (repeatedOnce == 0) {
							int index = ((Playlist) loadedItem).songs.indexOf((SongInput) playingNow);
							if (index == ((Playlist) loadedItem).songs.size() - 1) {
								playingNow = ((Playlist) loadedItem).songs.get(0);
								repeatedOnce = 1;
							} else
								playingNow = ((Playlist) loadedItem).songs.get(index + 1);
							listenedTime = -remainingTime; // time listened from the next track
							remainingTime = ((SongInput) playingNow).getDuration() - listenedTime;
						} else {
							int index = ((Playlist) loadedItem).songs.indexOf((SongInput) playingNow);
							if (index == ((Playlist) loadedItem).songs.size() - 1) {
								paused = true;
								listenedTime = 0;
								loadedItem = null;
								remainingTime = 0;
								playingNow = null;
								repeatedOnce = 0;
								return;
							}
							listenedTime = -remainingTime; // time listened from the next track
							playingNow = ((Playlist) loadedItem).songs.get(index + 1); // go to the next track
							remainingTime = ((SongInput) playingNow).getDuration() - listenedTime;
						}
					} else {
						if (repeatedOnce == 0) {
							int index = shuffledPlaylist.indexOf((SongInput) playingNow);
							if (index == shuffledPlaylist.size() - 1) {
								playingNow = shuffledPlaylist.get(0);
								repeatedOnce = 1;
							} else
								playingNow = shuffledPlaylist.get(index + 1);
							listenedTime = -remainingTime; // time listened from the next track
							remainingTime = ((SongInput) playingNow).getDuration() - listenedTime;
						} else {
							int index = shuffledPlaylist.indexOf((SongInput) playingNow);
							if (index == shuffledPlaylist.size() - 1) {
								paused = true;
								listenedTime = 0;
								loadedItem = null;
								remainingTime = 0;
								playingNow = null;
								repeatedOnce = 0;
								return;
							}
							listenedTime = -remainingTime; // time listened from the next track
							playingNow = shuffledPlaylist.get(index + 1); // go to the next track
							remainingTime = ((SongInput) playingNow).getDuration() - listenedTime;
						}
					}
				}
			}
		}
	}
}
