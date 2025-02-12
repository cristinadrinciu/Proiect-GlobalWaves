package player;

import audiofiles.Song;
import audiofiles.AudioFile;
import audiofiles.Album;
import audiofiles.Playlist;
import audiofiles.Podcast;
import fileio.input.EpisodeInput;
import audiofiles.Library;

import users.Artist;
import users.Host;
import users.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Player {
    private static Map<User, Player> instances = new HashMap<>();
    public User user;
    public int timestamp;
    public AudioFile loadedItem;
    public AudioFile playingNow;
    public int remainingTime;
    public int listenedTime;
    public int switchedTime;
    // its value is the timestamp when a user Played or Paused the loadedItem

    public boolean paused = false;
    public boolean shuffle = false;
    public int repeatState = 0;

    public int repeatedOnce = 0;

    public ArrayList<Song> shuffledPlaylist;

    public ArrayList<Podcast> playedPodcasts = new ArrayList<>();

    public ArrayList<Playlist> playlists = new ArrayList<>();
    // list of public playlists and user's created playlist

    public Library library;

    public Player(final User user, final Library library) {
        // set the user
        this.user = user;
        this.library = library;
    }


    /**
     * Function to get the instance of the player, Singleton pattern
     * @param user the user that wants to get the player
     * @param library the library of the application
     */
    public static Player getInstance(final User user, final Library library) {
        return instances.computeIfAbsent(user, u -> new Player(u, library));
    }

    /**
     *
     */
    public void setPlayingNow() {
        if (loadedItem != null && loadedItem.getType().equals("song")) {
            // from the beginning
            playingNow = loadedItem;
            remainingTime = ((Song) loadedItem).getDuration();
            paused = false;
            listenedTime = 0;
            switchedTime = timestamp;
            return;
        }
        if (loadedItem != null && loadedItem.getType().equals("playlist")) {
            // from the beginning
            playingNow = ((Playlist) loadedItem).getSongs().get(0);
            remainingTime = ((Song) playingNow).getDuration();
            paused = false;
            listenedTime = 0;
            switchedTime = timestamp;
            return;
        }
        if (loadedItem != null && loadedItem.getType().equals("album")) {
            // from the beginning
            playingNow = ((Album) loadedItem).getSongs().get(0);
            remainingTime = ((Song) playingNow).getDuration();
            paused = false;
            listenedTime = 0;
            switchedTime = timestamp;
            return;

        }
        if (loadedItem != null && loadedItem.getType().equals("podcast")) {
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

            if (found) {
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
            if (playingNow != null && playingNow.getType().equals("song")) {
                listenedTime += elapsedTime;
                remainingTime = ((Song) playingNow).getDuration() - listenedTime;
            } else if (playingNow != null && playingNow.getType().equals("episode")) {
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
        if (remainingTime <= 0) {
            if (loadedItem != null && loadedItem.getType().equals("song")) {
                paused = true;
                listenedTime = 0;
                loadedItem = null;
                remainingTime = 0;
                playingNow = null;
                return;
            }
            if (loadedItem != null && loadedItem.getType().equals("playlist")) {
                while (remainingTime <= 0) {
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

                        // skip the statistics if it is an ad
                        if (playingNow.getName().equals("Ad Break")) {
                            user.payAdRevenue(library);
                            continue;
                        }

                        updateStatistics();
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

                        // skip the statistics if it is an ad
                        if (playingNow.getName().equals("Ad Break")) {
                            user.payAdRevenue(library);
                            continue;
                        }

                        updateStatistics();
                    }
                }
            }
            if (loadedItem != null && loadedItem.getType().equals("album")) {
                // exactly the same as Playlist
                while (remainingTime <= 0) {
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

                        // skip the statistics if it is an ad
                        if (playingNow.getName().equals("Ad Break")) {
                            user.payAdRevenue(library);
                            continue;
                        }

                        updateStatistics();
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

                        // skip the statistics if it is an ad
                        if (playingNow.getName().equals("Ad Break")) {
                            user.payAdRevenue(library);
                            continue;
                        }

                        updateStatistics();
                    }
                }
            }
            if (loadedItem != null && loadedItem.getType().equals("podcast")) {
                while (remainingTime <= 0) {
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

                    // update the statistics
                    user.setListensToEpisode(playingNow.getName());

                    // update the statistics for the host
                    Host host = findHost((Podcast) loadedItem);
                    if (host != null) {
                        host.setListensToEpisode(playingNow.getName());
                        host.setListensToFan(user.getUsername());
                    }
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
            if (playingNow != null && playingNow.getType().equals("song")) {
                listenedTime += elapsedTime;
                remainingTime = ((Song) playingNow).getDuration() - listenedTime;
            } else if (playingNow != null && playingNow.getType().equals("episode")) {
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
        if (remainingTime <= 0) {
            if (loadedItem != null && loadedItem.getType().equals("song")) {
                while (remainingTime <= 0) {
                    listenedTime = -remainingTime;
                    remainingTime = ((Song) playingNow).getDuration() - listenedTime;

                    updateStatistics();
                }
            }
            if (loadedItem != null && loadedItem.getType().equals("podcast")) {
                while (remainingTime <= 0) {
                    int index = ((Podcast) loadedItem).getEpisodes().
                            indexOf((EpisodeInput) playingNow);
                    if (index == ((Podcast) loadedItem).getEpisodes().size() - 1) {
                        playingNow = ((Podcast) loadedItem).getEpisodes().get(0);
                    } else {
                        playingNow = ((Podcast) loadedItem).getEpisodes().get(index + 1);
                    }
                    listenedTime = -remainingTime; // time listened from the next track
                    remainingTime = ((EpisodeInput) playingNow).getDuration() - listenedTime;
                    // update the statistics
                    user.setListensToEpisode(playingNow.getName());

                    // update the statistics for the host
                    Host host = findHost((Podcast) loadedItem);
                    if (host != null) {
                        host.setListensToEpisode(playingNow.getName());
                        host.setListensToFan(user.getUsername());
                    }
                }
            }
            if (loadedItem != null && loadedItem.getType().equals("playlist")) {
                while (remainingTime <= 0) {
                    listenedTime = -remainingTime;
                    remainingTime = ((Song) playingNow).getDuration() - listenedTime;

                    updateStatistics();
                }
            }
            if (loadedItem != null && loadedItem.getType().equals("album")) {
                while (remainingTime <= 0) {
                    listenedTime = -remainingTime;
                    remainingTime = ((Song) playingNow).getDuration() - listenedTime;

                    updateStatistics();
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
            if (playingNow != null && playingNow.getType().equals("song")) {
                listenedTime += elapsedTime;
                remainingTime = ((Song) playingNow).getDuration() - listenedTime;
            } else if (playingNow != null && playingNow.getType().equals("episode")) {
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
        if (remainingTime <= 0) {
            if (loadedItem != null && loadedItem.getType().equals("song")) {
                while (remainingTime <= 0) {
                    if (repeatedOnce == 0) {
                        listenedTime = -remainingTime;
                        remainingTime = ((Song) playingNow).getDuration() - listenedTime;
                        repeatedOnce = 1;
                        repeatState = 0;

                        updateStatistics();
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
            if (loadedItem != null && loadedItem.getType().equals("podcast")) {
                while (remainingTime <= 0) {
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
                        // update the statistics
                        user.setListensToEpisode(playingNow.getName());

                        // update the statistics for the host
                        Host host = findHost((Podcast) loadedItem);
                        if (host != null) {
                            host.setListensToEpisode(playingNow.getName());
                            host.setListensToFan(user.getUsername());
                        }
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
                        // go to the next track
                        playingNow = ((Podcast) loadedItem).getEpisodes().get(index + 1);
                        remainingTime = ((EpisodeInput) playingNow).getDuration() - listenedTime;
                        // update the statistics
                        user.setListensToEpisode(playingNow.getName());

                        // update the statistics for the host
                        Host host = findHost((Podcast) loadedItem);
                        if (host != null) {
                            host.setListensToEpisode(playingNow.getName());
                            host.setListensToFan(user.getUsername());
                        }
                    }
                }
            }
            if (loadedItem != null && loadedItem.getType().equals("playlist")) {
                while (remainingTime <= 0) {
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

                        updateStatistics();
                    } else {
                        int index = shuffledPlaylist.indexOf((Song) playingNow);
                        if (index == shuffledPlaylist.size() - 1) {
                            playingNow = shuffledPlaylist.get(0);
                        } else {
                            playingNow = shuffledPlaylist.get(index + 1);
                        }
                        listenedTime = -remainingTime; // time listened from the next track
                        remainingTime = ((Song) playingNow).getDuration() - listenedTime;

                        updateStatistics();
                    }
                }
            }
            if (loadedItem != null && loadedItem.getType().equals("album")) {
                // exactly the same as Playlist
                while (remainingTime <= 0) {
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

                        updateStatistics();
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

                        updateStatistics();
                    }
                }
            }
        }
    }

    /**
     * @param song the song we want to find the owner to
     * @return the owner of the song
     */
    public Artist findArtist(final Song song) {
        for (User artist : library.getUsers()) {
            if (artist.getType().equals("artist")) {
                if (artist.getUsername().equals(song.getArtist())) {
                    return (Artist) artist;
                }
            }
        }
        return null;
    }

    /**
     * @param podcast the podcast we want to find the owner to
     * @return the owner of the podcast
     */
    public Host findHost(final Podcast podcast) {
        for (User host : library.getUsers()) {
            if (host.getType().equals("host")) {
                if (host.getUsername().equals(podcast.getOwner())) {
                    return (Host) host;
                }
            }
        }
        return null;
    }

    /**
     * Function to update the statistics of the user and the artist
     */
    public void updateStatistics() {
        // add in the list of songs between ads
        if (!user.isPremium()) {
            user.getSongsBetweenAds().add((Song) playingNow);
        }

        // add in the list of songs listened while premium
        if (user.isPremium()) {
            user.addPremiumSongs((Song) playingNow);
        }


        // update the listens of the song
        user.setListensToSong(playingNow.getName());
        user.setListensToArtist(((Song) playingNow).getArtist());
        user.setListensToGenre(((Song) playingNow).getGenre());
        user.setListensToAlbum(((Song) playingNow).getAlbum());

        // update the artist statistics
        Artist artist = findArtist((Song) playingNow);
        if (artist != null) {
            artist.setListensToSong(playingNow.getName());
            artist.setListensToAlbum(((Song) playingNow).getAlbum());
            artist.setListensToFan(user.getUsername());
        }
    }
}
