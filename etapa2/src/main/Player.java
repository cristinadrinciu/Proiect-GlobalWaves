package main;

import AudioFiles.*;
import fileio.input.EpisodeInput;
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

    public ArrayList<Song> shuffledPlaylist;

    public ArrayList<Podcast> playedPodcasts = new ArrayList<>();

    public ArrayList<Playlist> playlists = new ArrayList<>();
    // list of public playlists and user's created playlist

    public Player() {
    }

    /**
     *
     */
    public void setPlayingNow() {
        if (loadedItem instanceof Song) {
            // from the beginning
            playingNow = loadedItem;
            remainingTime = ((Song) loadedItem).getDuration();
            paused = false;
            listenedTime = 0;
            switchedTime = timestamp;
            return;
        }
        if (loadedItem instanceof Playlist) {
            // from the beginning
            playingNow = ((Playlist) loadedItem).getSongs().get(0);
            remainingTime = ((Song) playingNow).getDuration();
            paused = false;
            listenedTime = 0;
            switchedTime = timestamp;
            return;
        }
        if (loadedItem instanceof Album) {
            // from the beginning
            playingNow = ((Album) loadedItem).getSongs().get(0);
            remainingTime = ((Song) playingNow).getDuration();
            paused = false;
            listenedTime = 0;
            switchedTime = timestamp;
            return;

        }
        if (loadedItem instanceof Podcast) {
            // check if it was played before
            boolean found = false;
            Podcast foundPodcast = null;
            for (Podcast podcast : playedPodcasts) {
                if (podcast.getName().equals(((Podcast) loadedItem).getName())) {
                    found = true;
                    foundPodcast = podcast;
                    break;
                }
            }

            if(found) {
                // if it was played before, resume from the last episode played
                playingNow = foundPodcast.getLastEpisode();
                listenedTime = foundPodcast.getWatchedTime();
                remainingTime = ((EpisodeInput) playingNow).getDuration() - listenedTime;
            } else {
                // add in the list of played podcasts
                Podcast newPodcast = new Podcast();
                newPodcast.setName(((Podcast) loadedItem).getName());
                newPodcast.setOwner(((Podcast) loadedItem).getOwner());
                newPodcast.setEpisodes(((Podcast) loadedItem).getEpisodes());
                playedPodcasts.add(newPodcast);
                playingNow = ((Podcast) loadedItem).getEpisodes().get(0);
                listenedTime = 0;
                remainingTime = ((EpisodeInput) playingNow).getDuration();
            }
            paused = false;
            switchedTime = timestamp;
        }
    }

    /**
     *
     */
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

    /**
     *
     */
    public void setRemainingTime() {
        if (!paused) {
            int elapsedTime = timestamp - switchedTime;
            if (playingNow instanceof Song) {
                listenedTime += elapsedTime;
                remainingTime = ((Song) playingNow).getDuration() - listenedTime;
            } else if (playingNow instanceof EpisodeInput) {
                listenedTime += elapsedTime;
                remainingTime = ((EpisodeInput) playingNow).getDuration() - listenedTime;
                // get the podcast from the list of played podcasts
                for (Podcast podcast : playedPodcasts) {
                    if (podcast.getName().equals(((Podcast) loadedItem).getName())) {
                        podcast.setWatchedTime(listenedTime);
                        podcast.setLastEpisode((EpisodeInput) playingNow);
                        break;
                    }
                }
            }
        }

        switchedTime = timestamp;
        if (remainingTime < 0) {
            if (loadedItem instanceof Song) {
                paused = true;
                listenedTime = 0;
                loadedItem = null;
                remainingTime = 0;
                playingNow = null;
                return;
            }
            if (loadedItem instanceof Playlist) {
                while (remainingTime < 0) {
                    if (!shuffle) {
                        int index = ((Playlist) loadedItem).getSongs().indexOf((Song) playingNow);
                        if (index == ((Playlist) loadedItem).getSongs().size() - 1) {
                            paused = true;
                            listenedTime = 0;
                            loadedItem = null;
                            remainingTime = 0;
                            playingNow = null;
                            shuffle = false;
                            return;
                        }
                        listenedTime = -remainingTime; // time listened from the next track
                        playingNow = ((Playlist) loadedItem).getSongs().get(index + 1);
                        // go to the next track
                        remainingTime = ((Song) playingNow).getDuration() - listenedTime;
                    } else {
                        int index = shuffledPlaylist.indexOf((Song) playingNow);
                        if (index == shuffledPlaylist.size() - 1) {
                            paused = true;
                            listenedTime = 0;
                            loadedItem = null;
                            remainingTime = 0;
                            playingNow = null;
                            shuffle = false;
                            return;
                        }
                        listenedTime = -remainingTime; // time listened from the next track
                        playingNow = shuffledPlaylist.get(index + 1); // go to the next track
                        remainingTime = ((Song) playingNow).getDuration() - listenedTime;
                    }
                }
            }
            if (loadedItem instanceof Album) {
                // exactly the same as Playlist
                while (remainingTime < 0) {
                    if (!shuffle) {
                        int index = ((Album) loadedItem).getSongs().indexOf((Song) playingNow);
                        if (index == ((Album) loadedItem).getSongs().size() - 1) {
                            paused = true;
                            listenedTime = 0;
                            loadedItem = null;
                            remainingTime = 0;
                            playingNow = null;
                            shuffle = false;
                            return;
                        }
                        listenedTime = -remainingTime; // time listened from the next track
                        playingNow = ((Album) loadedItem).getSongs().get(index + 1);
                        // go to the next track
                        remainingTime = ((Song) playingNow).getDuration() - listenedTime;
                    } else {
                        int index = shuffledPlaylist.indexOf((Song) playingNow);
                        if (index == shuffledPlaylist.size() - 1) {
                            paused = true;
                            listenedTime = 0;
                            loadedItem = null;
                            remainingTime = 0;
                            playingNow = null;
                            shuffle = false;
                            return;
                        }
                        listenedTime = -remainingTime; // time listened from the next track
                        playingNow = shuffledPlaylist.get(index + 1); // go to the next track
                        remainingTime = ((Song) playingNow).getDuration() - listenedTime;
                    }
                }
            }
             if (loadedItem instanceof Podcast) {
                 while (remainingTime < 0) {
                     int index = ((Podcast) loadedItem).getEpisodes().indexOf(
                             (EpisodeInput) playingNow);
                     if (index == ((Podcast) loadedItem).getEpisodes().size() - 1) {
                         paused = true;
                         listenedTime = 0;
                         loadedItem = null;
                         remainingTime = 0;
                         playingNow = null;
                         return;
                     }
                     listenedTime = -remainingTime; // time listened from the next track
                     playingNow = ((Podcast) loadedItem).getEpisodes().get(index + 1);
                     // go to the next track
                     remainingTime = ((EpisodeInput) playingNow).
                             getDuration() - listenedTime;
                 }
             }
        }
    }

    /**
     *
     */
    public void setRemainingTimeRepeat2() {
        if (!paused) {
            int elapsedTime = timestamp - switchedTime;
            if (playingNow instanceof Song) {
                listenedTime += elapsedTime;
                remainingTime = ((Song) playingNow).getDuration() - listenedTime;
            } else if (playingNow instanceof EpisodeInput) {
                listenedTime += elapsedTime;
                remainingTime = ((EpisodeInput) playingNow).getDuration() - listenedTime;
                // get the podcast from the list of played podcasts
                for (Podcast podcast : playedPodcasts) {
                    if (podcast.getName().equals(((Podcast) loadedItem).getName())) {
                        podcast.setWatchedTime(listenedTime);
                        podcast.setLastEpisode((EpisodeInput) playingNow);
                        break;
                    }
                }
            }
        }

        switchedTime = timestamp;
        if (remainingTime < 0) {
            if (loadedItem instanceof Song) {
                while (remainingTime < 0) {
                    listenedTime = -remainingTime;
                    remainingTime = ((Song) playingNow).getDuration() - listenedTime;
                }
            }
            if (loadedItem instanceof Podcast) {
                while (remainingTime < 0) {
                    int index = ((Podcast) loadedItem).getEpisodes().indexOf((EpisodeInput) playingNow);
                    if (index == ((Podcast) loadedItem).getEpisodes().size() - 1) {
                        playingNow = ((Podcast) loadedItem).getEpisodes().get(0);
                    } else {
                        playingNow = ((Podcast) loadedItem).getEpisodes().get(index + 1);
                    }
                    listenedTime = -remainingTime; // time listened from the next track
                    remainingTime = ((EpisodeInput) playingNow).getDuration() - listenedTime;
                }
            }
            if (loadedItem instanceof Playlist) {
                while (remainingTime < 0) {
                    listenedTime = -remainingTime;
                    remainingTime = ((Song) playingNow).getDuration() - listenedTime;
                }
            }
            if (loadedItem instanceof Album) {
                while (remainingTime < 0) {
                    listenedTime = -remainingTime;
                    remainingTime = ((Song) playingNow).getDuration() - listenedTime;
                }
            }
        }
    }

    /**
     *
     */
    public void setRemainingTimeRepeat1() {
        if (!paused) {
            int elapsedTime = timestamp - switchedTime;
            if (playingNow instanceof Song) {
                listenedTime += elapsedTime;
                remainingTime = ((Song) playingNow).getDuration() - listenedTime;
            } else if (playingNow instanceof EpisodeInput) {
                listenedTime += elapsedTime;
                remainingTime = ((EpisodeInput) playingNow).getDuration() - listenedTime;
                // get the podcast from the list of played podcasts
                for (Podcast podcast : playedPodcasts) {
                    if (podcast.getName().equals(((Podcast) loadedItem).getName())) {
                        podcast.setWatchedTime(listenedTime);
                        podcast.setLastEpisode((EpisodeInput) playingNow);
                        break;
                    }
                }
            }
        }

        switchedTime = timestamp;
        if (remainingTime < 0) {
            if (loadedItem instanceof Song) {
                while (remainingTime < 0) {
                    if (repeatedOnce == 0) {
                        listenedTime = -remainingTime;
                        remainingTime = ((Song) playingNow).getDuration() - listenedTime;
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
            if (loadedItem instanceof Podcast) {
                while (remainingTime < 0) {
                    if (repeatedOnce == 0) {
                        int index = ((Podcast) loadedItem).getEpisodes().
                                indexOf((EpisodeInput) playingNow);
                        if (index == ((Podcast) loadedItem).getEpisodes().size() - 1) {
                            playingNow = ((Podcast) loadedItem).getEpisodes().get(0);
                            repeatedOnce = 1;
                        } else {
                            playingNow = ((Podcast) loadedItem).getEpisodes().get(index + 1);
                        }
                        listenedTime = -remainingTime; // time listened from the next track
                        remainingTime = ((EpisodeInput) playingNow).getDuration() - listenedTime;
                    } else {
                        int index = ((Podcast) loadedItem).getEpisodes().
                                indexOf((EpisodeInput) playingNow);
                        if (index == ((Podcast) loadedItem).getEpisodes().size() - 1) {
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
                        playingNow = ((Podcast) loadedItem).getEpisodes().get(index + 1); // go to the next track
                        remainingTime = ((EpisodeInput) playingNow).getDuration() - listenedTime;
                    }
                }
            }
            if (loadedItem instanceof Playlist) {
                while (remainingTime < 0) {
                    if (!shuffle) {
                        int index = ((Playlist) loadedItem).getSongs().
                                indexOf((Song) playingNow);
                        if (index == ((Playlist) loadedItem).getSongs().size() - 1) {
                            playingNow = ((Playlist) loadedItem).getSongs().get(0);
                        } else {
                            playingNow = ((Playlist) loadedItem).getSongs().get(index + 1);
                        }
                        listenedTime = -remainingTime; // time listened from the next track
                        remainingTime = ((Song) playingNow).getDuration() - listenedTime;
                    } else {
                        int index = shuffledPlaylist.indexOf((Song) playingNow);
                        if (index == shuffledPlaylist.size() - 1) {
                            playingNow = shuffledPlaylist.get(0);
                        } else {
                            playingNow = shuffledPlaylist.get(index + 1);
                        }
                        listenedTime = -remainingTime; // time listened from the next track
                        remainingTime = ((Song) playingNow).getDuration() - listenedTime;
                    }
                }
            }
            if(loadedItem instanceof Album) {
                // exactly the same as Playlist
                while (remainingTime < 0) {
                    if (!shuffle) {
                        int index = ((Album) loadedItem).getSongs().
                                indexOf((Song) playingNow);
                        if (index == ((Album) loadedItem).getSongs().size() - 1) {
                            playingNow = ((Album) loadedItem).getSongs().get(0);
                        } else {
                            playingNow = ((Album) loadedItem).getSongs().get(index + 1);
                        }
                        listenedTime = -remainingTime; // time listened from the next track
                        remainingTime = ((Song) playingNow).getDuration() - listenedTime;
                    } else {
                        int index = shuffledPlaylist.indexOf((Song) playingNow);
                        if (index == shuffledPlaylist.size() - 1) {
                            playingNow = shuffledPlaylist.get(0);
                            repeatedOnce = 1;
                        } else {
                            playingNow = shuffledPlaylist.get(index + 1);
                        }
                        listenedTime = -remainingTime; // time listened from the next track
                        remainingTime = ((Song) playingNow).getDuration() - listenedTime;
                    }
                }
            }
        }
    }
}
