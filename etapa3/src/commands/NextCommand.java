package commands;

import audio.files.Album;
import audio.files.Library;
import audio.files.Playlist;
import audio.files.Podcast;
import audio.files.Song;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.input.EpisodeInput;
import main.InputCommands;
import visit.pattern.Visitable;
import visit.pattern.Visitor;
import user.types.User;

import java.util.ArrayList;

public class NextCommand implements Command {
    private String message;

    public NextCommand() {
    }

    /**
     *
     * @return the message of the command
     */
    public String getMessage() {
        return message;
    }

    /**
     *
     * @param message the message of the command
     */
    public void setMessage(final String message) {
        this.message = message;
    }

    /**
     *
     * @param user from which we get the player
     */
    public void goToNextSong(final User user) {
        // verify what we are currently playing
        if (user.getPlayer().loadedItem instanceof Song) {
            if (user.getPlayer().repeatState == 0) {
                // if we are not repeating, then we stop the player
                user.getPlayer().playingNow = null;
                user.getPlayer().loadedItem = null;
                user.getPlayer().listenedTime = 0;
                user.getPlayer().remainingTime = 0;
                user.getPlayer().paused = true;
                user.getPlayer().switchedTime = user.getPlayer().timestamp;
                message = "Please load a source before skipping to the next track.";
            } else if (user.getPlayer().repeatState == 2) {
                // if we are repeating the song, then we reset the listened time
                user.getPlayer().listenedTime = 0;
                user.getPlayer().remainingTime = ((Song) user.getPlayer().playingNow).getDuration();
                user.getPlayer().switchedTime = user.getPlayer().timestamp;
                user.getPlayer().paused = false;
                message = "Skipped to next track successfully. "
                        + "The current track is " + user.getPlayer().playingNow.getName() + ".";
            } else if (user.getPlayer().repeatState == 1) {
                // check the repeatOnce state
                if (user.getPlayer().repeatedOnce == 0) {
                    // if we are not repeating the song, then we repeat it
                    user.getPlayer().repeatedOnce = 1;
                    user.getPlayer().listenedTime = 0;
                    user.getPlayer().remainingTime = ((Song) user.getPlayer().
                            playingNow).getDuration();
                    user.getPlayer().switchedTime = user.getPlayer().timestamp;
                    user.getPlayer().paused = false;
                    message = "Skipped to next track successfully. "
                            + "The current track is " + user.getPlayer().playingNow.getName() + ".";
                } else {
                    // if we are repeating the song, then we stop the player
                    user.getPlayer().playingNow = null;
                    user.getPlayer().loadedItem = null;
                    user.getPlayer().listenedTime = 0;
                    user.getPlayer().remainingTime = 0;
                    user.getPlayer().paused = true;
                    user.getPlayer().switchedTime = user.getPlayer().timestamp;
                    message = "Please load a source before skipping to the next track.";
                }
            }
        }
    }

    /**
     *
     * @param user from which we get the player
     */


    public void goToNextPlaylist(final User user) {
        // verify what we are currently playing
        if (user.getPlayer().loadedItem instanceof Playlist) {
            Playlist playlist = (Playlist) user.getPlayer().loadedItem;
            if (user.getPlayer().repeatState == 0) {
                if (!user.getPlayer().shuffle) {
                    // check if we are at the end of the playlist
                    if (playlist.getSongs().indexOf((Song) user.getPlayer().playingNow)
                            == playlist.getSongs().size() - 1) {
                        // if we are at the end, then we stop the player
                        user.getPlayer().playingNow = null;
                        user.getPlayer().loadedItem = null;
                        user.getPlayer().listenedTime = 0;
                        user.getPlayer().remainingTime = 0;
                        user.getPlayer().paused = true;
                        user.getPlayer().shuffle = false;
                        user.getPlayer().switchedTime = user.getPlayer().timestamp;
                        message = "Please load a source before skipping to the next track.";
                    } else {
                        // if we are not at the end, then we go to the next song
                        user.getPlayer().playingNow = playlist.getSongs().get(playlist.getSongs().
                                indexOf((Song) user.getPlayer().playingNow) + 1);
                        user.getPlayer().listenedTime = 0;
                        user.getPlayer().remainingTime = ((Song) user.getPlayer().
                                playingNow).getDuration();
                        user.getPlayer().switchedTime = user.getPlayer().timestamp;
                        user.getPlayer().paused = false;
                        message = "Skipped to next track successfully. "
                                + "The current track is " + user.getPlayer().
                                playingNow.getName() + ".";
                    }
                } else {
                    // check if we are at the end of the playlist
                    ArrayList<Song> shuffledPlaylist = user.getPlayer().shuffledPlaylist;
                    if (shuffledPlaylist.indexOf((Song) user.getPlayer().playingNow)
                            == shuffledPlaylist.size() - 1) {
                        // if we are at the end, then we stop the player
                        user.getPlayer().playingNow = null;
                        user.getPlayer().loadedItem = null;
                        user.getPlayer().listenedTime = 0;
                        user.getPlayer().remainingTime = 0;
                        user.getPlayer().paused = true;
                        user.getPlayer().shuffle = false;
                        user.getPlayer().switchedTime = user.getPlayer().timestamp;
                        message = "Please load a source before skipping to the next track.";
                    } else {
                        // if we are not at the end, then we go to the next song
                        user.getPlayer().playingNow = shuffledPlaylist.get(shuffledPlaylist.indexOf(
                                (Song) user.getPlayer().playingNow) + 1);
                        user.getPlayer().listenedTime = 0;
                        user.getPlayer().remainingTime = ((Song) user.getPlayer().
                                playingNow).getDuration();
                        user.getPlayer().switchedTime = user.getPlayer().timestamp;
                        user.getPlayer().paused = false;
                        message = "Skipped to next track successfully. "
                                + "The current track is " + user.getPlayer().playingNow.
                                getName() + ".";
                    }

                }
            }
            if (user.getPlayer().repeatState == 1) {
                // the playlist repeats itself
                if (!user.getPlayer().shuffle) {
                    // check if we are at the end of the playlist
                    if (playlist.getSongs().indexOf((Song) user.getPlayer().playingNow)
                            == playlist.getSongs().size() - 1) {
                        // if we are at the end, then we go to the first song
                        user.getPlayer().playingNow = playlist.getSongs().get(0);
                        user.getPlayer().listenedTime = 0;
                        user.getPlayer().remainingTime = ((Song) user.getPlayer().playingNow).
                                getDuration();
                        user.getPlayer().switchedTime = user.getPlayer().timestamp;
                        user.getPlayer().paused = false;
                        message = "Skipped to next track successfully. "
                                + "The current track is " + user.getPlayer().playingNow.
                                getName() + ".";
                    } else {
                        // if we are not at the end, then we go to the next song
                        user.getPlayer().playingNow = playlist.getSongs().get(playlist.
                                getSongs().indexOf((Song) user.getPlayer().playingNow) + 1);
                        user.getPlayer().listenedTime = 0;
                        user.getPlayer().remainingTime = ((Song) user.getPlayer().playingNow).
                                getDuration();
                        user.getPlayer().switchedTime = user.getPlayer().timestamp;
                        user.getPlayer().paused = false;
                        message = "Skipped to next track successfully. "
                                + "The current track is " + user.getPlayer().playingNow.
                                getName() + ".";
                    }
                } else {
                    // check if we are at the end of the playlist
                    ArrayList<Song> shuffledPlaylist = user.getPlayer().shuffledPlaylist;
                    if (shuffledPlaylist.indexOf((Song) user.getPlayer().playingNow)
                            == shuffledPlaylist.size() - 1) {
                        // if we are at the end, then we go to the first song
                        user.getPlayer().playingNow = shuffledPlaylist.get(0);
                        user.getPlayer().listenedTime = 0;
                        user.getPlayer().remainingTime = ((Song) user.getPlayer().playingNow).
                                getDuration();
                        user.getPlayer().switchedTime = user.getPlayer().timestamp;
                        user.getPlayer().paused = false;
                        message = "Skipped to next track successfully. "
                                + "The current track is " + user.getPlayer().playingNow.
                                getName() + ".";
                    } else {
                        // if we are not at the end, then we go to the next song
                        user.getPlayer().playingNow = shuffledPlaylist.get(shuffledPlaylist.indexOf(
                                (Song) user.getPlayer().playingNow) + 1);
                        user.getPlayer().listenedTime = 0;
                        user.getPlayer().remainingTime = ((Song) user.getPlayer().playingNow).
                                getDuration();
                        user.getPlayer().switchedTime = user.getPlayer().timestamp;
                        user.getPlayer().paused = false;
                        message = "Skipped to next track successfully. "
                                + "The current track is " + user.getPlayer().playingNow.
                                getName() + ".";
                    }
                }
            }
            if (user.getPlayer().repeatState == 2) {
                // the song repeats itself
                user.getPlayer().listenedTime = 0;
                user.getPlayer().remainingTime = ((Song) user.getPlayer().playingNow).
                        getDuration();
                user.getPlayer().switchedTime = user.getPlayer().timestamp;
                user.getPlayer().paused = false;
                message = "Skipped to next track successfully. "
                        + "The current track is " + user.getPlayer().playingNow.
                        getName() + ".";
            }
        }
    }

    /**
     *
     * @param user from which we get the player
     */

    public void goToNextPodcast(final User user) {
        // verify what we are currently playing
        if (user.getPlayer().loadedItem instanceof Podcast) {
            if (user.getPlayer().repeatState == 0) {
                // check if it is the last episode
                EpisodeInput episode = (EpisodeInput) user.getPlayer().playingNow;
                if (((Podcast) user.getPlayer().loadedItem).getEpisodes().indexOf(episode)
                        == ((Podcast) user.getPlayer().loadedItem).getEpisodes().size() - 1) {
                    // if it is the last episode, then we stop the player
                    user.getPlayer().playingNow = null;
                    user.getPlayer().loadedItem = null;
                    user.getPlayer().listenedTime = 0;
                    user.getPlayer().remainingTime = 0;
                    user.getPlayer().paused = true;
                    user.getPlayer().switchedTime = user.getPlayer().timestamp;
                    message = "Please load a source before skipping to the next track.";
                } else {
                    // if it is not the last episode, then we go to the next episode
                    user.getPlayer().playingNow = ((Podcast) user.getPlayer().loadedItem).
                            getEpisodes().get(((Podcast) user.getPlayer().loadedItem).getEpisodes().
                                    indexOf(episode) + 1);
                    user.getPlayer().listenedTime = 0;
                    user.getPlayer().remainingTime = ((EpisodeInput) user.getPlayer().playingNow).
                            getDuration();
                    user.getPlayer().switchedTime = user.getPlayer().timestamp;
                    user.getPlayer().paused = false;
                    message = "Skipped to next track successfully. "
                            + "The current track is " + user.getPlayer().playingNow.getName() + ".";
                }
            }
            if (user.getPlayer().repeatState == 2) {
                // the podcast repeats itself
                // check if it is the last episode
                EpisodeInput episode = (EpisodeInput) user.getPlayer().playingNow;
                if (((Podcast) user.getPlayer().loadedItem).getEpisodes().indexOf(episode)
                        == ((Podcast) user.getPlayer().loadedItem).getEpisodes().size() - 1) {
                    // if it is the last episode, then we go to the first episode
                    user.getPlayer().playingNow = ((Podcast) user.getPlayer().loadedItem).
                            getEpisodes().get(0);
                    user.getPlayer().listenedTime = 0;
                    user.getPlayer().remainingTime = ((EpisodeInput) user.getPlayer().playingNow).
                            getDuration();
                    user.getPlayer().switchedTime = user.getPlayer().timestamp;
                    user.getPlayer().paused = false;
                    message = "Skipped to next track successfully. "
                            + "The current track is " + user.getPlayer().playingNow.getName() + ".";
                } else {
                    // if it is not the last episode, then we go to the next episode
                    user.getPlayer().playingNow = ((Podcast) user.getPlayer().loadedItem).
                            getEpisodes().get(((Podcast) user.getPlayer().loadedItem).getEpisodes().
                                    indexOf(episode) + 1);
                    user.getPlayer().listenedTime = 0;
                    user.getPlayer().remainingTime = ((EpisodeInput) user.getPlayer().playingNow).
                            getDuration();
                    user.getPlayer().switchedTime = user.getPlayer().timestamp;
                    user.getPlayer().paused = false;
                    message = "Skipped to next track successfully. "
                            + "The current track is " + user.getPlayer().playingNow.getName() + ".";
                }
            }
            if (user.getPlayer().repeatState == 1) {
                // the podcast repeats itself
                if (user.getPlayer().repeatedOnce == 0) {
                    // not repeating
                    // check if it is the last episode
                    EpisodeInput episode = (EpisodeInput) user.getPlayer().playingNow;
                    if (((Podcast) user.getPlayer().loadedItem).getEpisodes().indexOf(episode)
                           == ((Podcast) user.getPlayer().loadedItem).getEpisodes().size() - 1) {
                        // if it is the last episode, then we go to the first episode
                        user.getPlayer().playingNow = ((Podcast) user.getPlayer().loadedItem).
                                getEpisodes().get(0);
                        user.getPlayer().listenedTime = 0;
                        user.getPlayer().remainingTime = ((EpisodeInput) user.getPlayer().
                                playingNow).
                                getDuration();
                        user.getPlayer().switchedTime = user.getPlayer().timestamp;
                        user.getPlayer().repeatedOnce = 1;
                        user.getPlayer().paused = false;
                        message = "Skipped to next track successfully. "
                                + "The current track is " + user.getPlayer().playingNow.
                                getName() + ".";
                    } else {
                        // if it is not the last episode, then we go to the next episode
                        user.getPlayer().playingNow = ((Podcast) user.getPlayer().loadedItem).
                                getEpisodes().get(((Podcast) user.getPlayer().loadedItem).
                                        getEpisodes().indexOf(episode) + 1);
                        user.getPlayer().listenedTime = 0;
                        user.getPlayer().remainingTime = ((EpisodeInput) user.getPlayer().
                                playingNow).
                                getDuration();
                        user.getPlayer().switchedTime = user.getPlayer().timestamp;
                        user.getPlayer().repeatedOnce = 1;
                        user.getPlayer().paused = false;
                        message = "Skipped to next track successfully. "
                                + "The current track is " + user.getPlayer().playingNow.
                                getName() + ".";
                    }
                } else {
                    // is repeating
                    //check if it is the last episode
                    EpisodeInput episode = (EpisodeInput) user.getPlayer().playingNow;
                    if (((Podcast) user.getPlayer().loadedItem).getEpisodes().indexOf(episode)
                           == ((Podcast) user.getPlayer().loadedItem).getEpisodes().size() - 1) {
                        // if it is the last episode, then we stop the player
                        user.getPlayer().playingNow = null;
                        user.getPlayer().loadedItem = null;
                        user.getPlayer().listenedTime = 0;
                        user.getPlayer().remainingTime = 0;
                        user.getPlayer().paused = true;
                        user.getPlayer().switchedTime = user.getPlayer().timestamp;
                        message = "Please load a source before skipping to the next track.";
                    } else {
                        // if it is not the last episode, then we go to the next episode
                        user.getPlayer().playingNow = ((Podcast) user.getPlayer().loadedItem).
                                getEpisodes().get(((Podcast) user.getPlayer().loadedItem).
                                        getEpisodes().indexOf(episode) + 1);
                        user.getPlayer().listenedTime = 0;
                        user.getPlayer().remainingTime = ((EpisodeInput) user.getPlayer().
                                playingNow).getDuration();
                        user.getPlayer().switchedTime = user.getPlayer().timestamp;
                        user.getPlayer().paused = false;
                        message = "Skipped to next track successfully. "
                                + "The current track is " + user.getPlayer().playingNow.
                                getName() + ".";
                    }
                }
            }
        }
    }

    /**
     *
     * @param user from which we get the player
     */
    public void goToNextAlbum(final User user) {
        // exactly the same as the playlist
        if (user.getPlayer().loadedItem instanceof Album) {
            Album album = (Album) user.getPlayer().loadedItem;
            if (user.getPlayer().repeatState == 0) {
                if (!user.getPlayer().shuffle) {
                    if (album.getSongs().indexOf((Song) user.getPlayer().playingNow)
                            == album.getSongs().size() - 1) {
                        user.getPlayer().playingNow = null;
                        user.getPlayer().loadedItem = null;
                        user.getPlayer().listenedTime = 0;
                        user.getPlayer().remainingTime = 0;
                        user.getPlayer().paused = true;
                        user.getPlayer().shuffle = false;
                        user.getPlayer().switchedTime = user.getPlayer().timestamp;
                        message = "Please load a source before skipping to the next track.";
                    } else {
                        user.getPlayer().playingNow = album.getSongs().get(album.getSongs().
                                indexOf((Song) user.getPlayer().playingNow) + 1);
                        user.getPlayer().listenedTime = 0;
                        user.getPlayer().remainingTime = ((Song) user.getPlayer().
                                playingNow).getDuration();
                        user.getPlayer().switchedTime = user.getPlayer().timestamp;
                        user.getPlayer().paused = false;
                        message = "Skipped to next track successfully. "
                                + "The current track is " + user.getPlayer().
                                playingNow.getName() + ".";
                    }
                } else {
                    ArrayList<Song> shuffledAlbum = user.getPlayer().shuffledPlaylist;
                    if (shuffledAlbum.indexOf((Song) user.getPlayer().playingNow)
                            == shuffledAlbum.size() - 1) {
                        user.getPlayer().playingNow = null;
                        user.getPlayer().loadedItem = null;
                        user.getPlayer().listenedTime = 0;
                        user.getPlayer().remainingTime = 0;
                        user.getPlayer().paused = true;
                        user.getPlayer().shuffle = false;
                        user.getPlayer().switchedTime = user.getPlayer().timestamp;
                        message = "Please load a source before skipping to the next track.";
                    } else {
                        user.getPlayer().playingNow = shuffledAlbum.get(shuffledAlbum.indexOf(
                                (Song) user.getPlayer().playingNow) + 1);
                        user.getPlayer().listenedTime = 0;
                        user.getPlayer().remainingTime = ((Song) user.getPlayer().
                                playingNow).getDuration();
                        user.getPlayer().switchedTime = user.getPlayer().timestamp;
                        user.getPlayer().paused = false;
                        message = "Skipped to next track successfully. "
                                + "The current track is " + user.getPlayer().playingNow.
                                getName() + ".";
                    }
                }
            }
            if (user.getPlayer().repeatState == 1) {
                if (!user.getPlayer().shuffle) {
                    if (album.getSongs().indexOf((Song) user.getPlayer().playingNow)
                            == album.getSongs().size() - 1) {
                        user.getPlayer().playingNow = album.getSongs().get(0);
                        user.getPlayer().listenedTime = 0;
                        user.getPlayer().remainingTime = ((Song) user.getPlayer().
                                playingNow).getDuration();
                        user.getPlayer().switchedTime = user.getPlayer().timestamp;
                        user.getPlayer().paused = false;
                        message = "Skipped to next track successfully. "
                                + "The current track is " + user.getPlayer().
                                playingNow.getName() + ".";
                    } else {
                        user.getPlayer().playingNow = album.getSongs().get(album.getSongs().
                                indexOf((Song) user.getPlayer().playingNow) + 1);
                        user.getPlayer().listenedTime = 0;
                        user.getPlayer().remainingTime = ((Song) user.getPlayer().
                                playingNow).getDuration();
                        user.getPlayer().switchedTime = user.getPlayer().timestamp;
                        user.getPlayer().paused = false;
                        message = "Skipped to next track successfully. "
                                + "The current track is " + user.getPlayer().playingNow.
                                getName() + ".";
                    }
                } else {
                    ArrayList<Song> shuffledAlbum = user.getPlayer().shuffledPlaylist;
                    if (shuffledAlbum.indexOf((Song) user.getPlayer().playingNow)
                            == shuffledAlbum.size() - 1) {
                        user.getPlayer().playingNow = shuffledAlbum.get(0);
                        user.getPlayer().listenedTime = 0;
                        user.getPlayer().remainingTime = ((Song) user.getPlayer().
                                playingNow).getDuration();
                        user.getPlayer().switchedTime = user.getPlayer().timestamp;
                        user.getPlayer().paused = false;
                        message = "Skipped to next track successfully. "
                                + "The current track is " + user.getPlayer().playingNow.
                                getName() + ".";
                    } else {
                        user.getPlayer().playingNow = shuffledAlbum.get(shuffledAlbum.indexOf(
                                (Song) user.getPlayer().playingNow) + 1);
                        user.getPlayer().listenedTime = 0;
                        user.getPlayer().remainingTime = ((Song) user.getPlayer().
                                playingNow).getDuration();
                        user.getPlayer().switchedTime = user.getPlayer().timestamp;
                        user.getPlayer().paused = false;
                        message = "Skipped to next track successfully. "
                                + "The current track is " + user.getPlayer().playingNow.
                                getName() + ".";
                    }
                }
            }
            if (user.getPlayer().repeatState == 2) {
                user.getPlayer().listenedTime = 0;
                user.getPlayer().remainingTime = ((Song) user.getPlayer().playingNow).
                        getDuration();
                user.getPlayer().switchedTime = user.getPlayer().timestamp;
                user.getPlayer().paused = false;
                message = "Skipped to next track successfully. "
                        + "The current track is " + user.getPlayer().playingNow.
                        getName() + ".";
            }
        }
    }

    /**
     * Executes the next command
     * @param command the input command
     * @param library the main database
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
            message = "Please load a source before skipping to the next track.";
        } else {
            if (user.getPlayer().loadedItem instanceof Song) {
                goToNextSong(user);
            } else if (user.getPlayer().loadedItem instanceof Playlist) {
                goToNextPlaylist(user);
            } else if (user.getPlayer().loadedItem instanceof Podcast) {
                goToNextPodcast(user);
            } else if (user.getPlayer().loadedItem instanceof Album) {
                goToNextAlbum(user);
            }
        }

        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode commandJson = objectMapper.createObjectNode()
                .put("command", "next")
                .put("user", command.getUsername())
                .put("timestamp", command.getTimestamp())
                .put("message", message);

        command.getCommandList().add(commandJson);
    }
}
