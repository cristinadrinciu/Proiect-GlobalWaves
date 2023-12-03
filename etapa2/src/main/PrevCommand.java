package main;

import AudioFiles.Playlist;
import AudioFiles.Podcast;
import AudioFiles.Song;
import AudioFiles.User;
import fileio.input.EpisodeInput;

public class PrevCommand {
    private String message;

    public PrevCommand() {
    }

    /**
     * @return the message
     */
    public String getMessage() {
        return message;
    }

    /**
     * This method is used to go to the previous item in the player
     * @param user the user that executes the command
     */
    public void goToPrevSong(final User user) {
        if (user.getPlayer().listenedTime == 0) {
            // can't go to the previous
            user.getPlayer().loadedItem = null;
            user.getPlayer().playingNow = null;
            user.getPlayer().remainingTime = 0;
            user.getPlayer().switchedTime = user.getPlayer().timestamp;
            user.getPlayer().paused = true;
            message = "Please load a source before returning to the previous track.";
        } else {
            // restart the song
            user.getPlayer().remainingTime = ((Song) user.getPlayer().playingNow).getDuration();
            user.getPlayer().listenedTime = 0;
            user.getPlayer().switchedTime = user.getPlayer().timestamp;
            user.getPlayer().paused = false;
            message = "Returned to previous track successfully. "
                    + "The current track is " + user.getPlayer().playingNow.getName() + ".";
        }
    }

    /**
     * This method is used to go to the previous item in the player
     * @param user the user that executes the command
     */
    public void goToPrevPlaylist(final User user) {
        if (user.getPlayer().listenedTime > 0) {
            // restart the song
            user.getPlayer().remainingTime = ((Song) user.getPlayer().playingNow).getDuration();
            user.getPlayer().listenedTime = 0;
            user.getPlayer().switchedTime = user.getPlayer().timestamp;
            user.getPlayer().paused = false;
            message = "Returned to previous track successfully. "
                    + "The current track is " + user.getPlayer().playingNow.getName() + ".";
        } else {
            // check if it is the first song in the playlist
            if (!user.getPlayer().shuffle) {
                if (user.getPlayer().playingNow == ((Playlist) user.getPlayer().
                        loadedItem).getSongs().get(0)) {
                    // restart the song
                    user.getPlayer().remainingTime = ((Song) user.getPlayer().
                            playingNow).getDuration();
                    user.getPlayer().listenedTime = 0;
                    user.getPlayer().switchedTime = user.getPlayer().timestamp;
                    user.getPlayer().paused = false;
                    message = "Returned to previous track successfully. "
                            + "The current track is " + user.getPlayer().playingNow.getName() + ".";
                } else {
                    // we go to the previous song
                    user.getPlayer().playingNow = ((Playlist) user.getPlayer().loadedItem).
                            getSongs().get(((Playlist) user.
                                    getPlayer().loadedItem).getSongs().
                                    indexOf((Song) user.getPlayer().playingNow) - 1);
                    user.getPlayer().listenedTime = 0;
                    user.getPlayer().remainingTime = ((Song) user.getPlayer().playingNow).
                            getDuration();
                    user.getPlayer().switchedTime = user.getPlayer().timestamp;
                    user.getPlayer().paused = false;
                    message = "Returned to previous track successfully. "
                            + "The current track is " + user.getPlayer().playingNow.getName() + ".";
                }
            } else {
                if (user.getPlayer().playingNow == user.getPlayer().shuffledPlaylist.get(0)) {
                    // restart the song
                    user.getPlayer().remainingTime = ((Song) user.getPlayer().playingNow).
                            getDuration();
                    user.getPlayer().listenedTime = 0;
                    user.getPlayer().switchedTime = user.getPlayer().timestamp;
                    user.getPlayer().paused = false;
                    message = "Returned to previous track successfully. "
                            + "The current track is " + user.getPlayer().playingNow.getName() + ".";
                } else {
                    // we go to the previous song
                    user.getPlayer().playingNow = user.getPlayer().shuffledPlaylist.get(user.
                            getPlayer().shuffledPlaylist.indexOf(
                                    (Song) user.getPlayer().playingNow) - 1);
                    user.getPlayer().listenedTime = 0;
                    user.getPlayer().remainingTime = ((Song) user.getPlayer().playingNow).
                            getDuration();
                    user.getPlayer().switchedTime = user.getPlayer().timestamp;
                    user.getPlayer().paused = false;
                    message = "Returned to previous track successfully. "
                            + "The current track is " + user.getPlayer().playingNow.getName() + ".";
                }
            }
        }
    }

    /**
     * This method is used to go to the previous item in the player
     * @param user the user that executes the command
     */
    public void goToPrevPodcast(final User user) {
        if (user.getPlayer().listenedTime > 0) {
            // restart the episode
            user.getPlayer().remainingTime = ((EpisodeInput) user.getPlayer().playingNow).
                    getDuration();
            user.getPlayer().listenedTime = 0;
            user.getPlayer().switchedTime = user.getPlayer().timestamp;
            user.getPlayer().paused = false;
            user.getPlayer().playingNow = ((Podcast) user.getPlayer().loadedItem).getEpisodes().
                    get(((Podcast) user.getPlayer().loadedItem).getEpisodes().size() - 1);
            message = "Returned to previous track successfully. "
                    + "The current track is " + user.getPlayer().playingNow.getName() + ".";
        } else {
            // check if it is the first episode in the podcast
            if (user.getPlayer().playingNow == ((Podcast) user.getPlayer().loadedItem).
                    getEpisodes().get(0)) {
                // restart the episode
                user.getPlayer().remainingTime = ((EpisodeInput) user.
                        getPlayer().playingNow).getDuration();
                user.getPlayer().listenedTime = 0;
                user.getPlayer().switchedTime = user.getPlayer().timestamp;
                user.getPlayer().paused = false;
                message = "Returned to previous track successfully. "
                        + "The current track is " + user.getPlayer().playingNow.getName() + ".";
            } else {
                // go to the previous episode
                user.getPlayer().playingNow = ((Podcast) user.getPlayer().loadedItem).
                        getEpisodes().get(((Podcast) user.getPlayer().loadedItem).getEpisodes().
                                indexOf((EpisodeInput) user.getPlayer().playingNow) - 1);
                user.getPlayer().listenedTime = 0;
                user.getPlayer().remainingTime = ((EpisodeInput) user.getPlayer().playingNow).
                        getDuration();
                user.getPlayer().switchedTime = user.getPlayer().timestamp;
                user.getPlayer().paused = false;
                message = "Returned to previous track successfully. "
                        + "The current track is " + user.getPlayer().playingNow.getName() + ".";
            }
        }
    }
}
