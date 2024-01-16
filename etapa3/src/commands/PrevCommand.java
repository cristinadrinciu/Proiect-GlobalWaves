package commands;

import audioFiles.Album;
import audioFiles.Library;
import audioFiles.Playlist;
import audioFiles.Podcast;
import audioFiles.Song;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import designPatterns.commandPattern.Command;
import fileio.input.EpisodeInput;
import main.InputCommands;
import users.User;

public class PrevCommand implements Command {
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

    /**
     * This method is used to go to the previous item in the player
     * @param user the user that executes the command
     */
    public void goToPrevAlbum(final User user) {
        // exactly the same as the playlist
        if (user.getPlayer().listenedTime > 0) {
            // restart the song
            user.getPlayer().remainingTime = ((Song) user.getPlayer().playingNow).getDuration();
            user.getPlayer().listenedTime = 0;
            user.getPlayer().switchedTime = user.getPlayer().timestamp;
            user.getPlayer().paused = false;
            message = "Returned to previous track successfully. "
                    + "The current track is " + user.getPlayer().playingNow.getName() + ".";
        } else {
            // check if it is the first song of the album
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
     * Execute the command
     * @param command the input command
     * @param library the main library
     */
    @Override
    public void execute(final InputCommands command, final Library library) {
        User user = command.getUser();

        if (user.getPlayer().loadedItem != null) {
            if (user.getPlayer().repeatState == 0) {
                user.getPlayer().setRemainingTime();
            }
            if (user.getPlayer().repeatState == 1) {
                user.getPlayer().setRemainingTimeRepeat1();
            }
            if (user.getPlayer().repeatState == 2) {
                user.getPlayer().setRemainingTimeRepeat2();
            }
        }

        if (user.getPlayer().loadedItem == null) {
            message = "Please load a source before returning to the previous track.";
        } else {
            if (user.getPlayer().loadedItem instanceof Song) {
                goToPrevSong(user);
            } else if (user.getPlayer().loadedItem instanceof Playlist) {
                goToPrevPlaylist(user);
            } else if (user.getPlayer().loadedItem instanceof Podcast) {
                goToPrevPodcast(user);
            } else if (user.getPlayer().loadedItem instanceof Album) {
                goToPrevAlbum(user);
            }
        }

        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode commandJson = objectMapper.createObjectNode()
                .put("command", "prev")
                .put("user", command.getUsername())
                .put("timestamp", command.getTimestamp())
                .put("message", message);

        command.getCommandList().add(commandJson);
    }
}
