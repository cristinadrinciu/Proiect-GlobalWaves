package commands;

import audioFiles.Library;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import designPatterns.commandPattern.Command;
import main.InputCommands;

import platformData.OnlineUsers;
import users.Artist;
import users.Host;
import users.User;
import designPatterns.visitorPattern.Visitable;

import designPatterns.visitorPattern.WrappedVisitor;

import java.util.ArrayList;

public class WrappedCommand implements Command {
    private String message = null;
    private ArrayList<String> topArtists = new ArrayList<>();
    private ArrayList<String> topGenres = new ArrayList<>();
    private ArrayList<String> topSongs = new ArrayList<>();
    private ArrayList<String> topAlbums = new ArrayList<>();
    private ArrayList<String> topEpisodes = new ArrayList<>();
    private ArrayList<String> topFans = new ArrayList<>();
    private int listeners;


    public WrappedCommand() {
    }


    /**
     * @return the message
     */
    public String getMessage() {
        return message;
    }

    /**
     * @param message the message to set
     */
    public void setMessage(final String message) {
        this.message = message;
    }

    /**
     * @return the topArtists
     */
    public ArrayList<String> getTopArtists() {
        return topArtists;
    }

    /**
     * @return the topGenres
     */
    public ArrayList<String> getTopGenres() {
        return topGenres;
    }

    /**
     * @return the topSongs
     */
    public ArrayList<String> getTopSongs() {
        return topSongs;
    }

    /**
     * @return the topAlbums
     */
    public ArrayList<String> getTopAlbums() {
        return topAlbums;
    }

    /**
     * @return the topPodcasts
     */
    public ArrayList<String> getTopEpisodes() {
        return topEpisodes;
    }

    /**
     * @return the topFans
     */
    public ArrayList<String> getTopFans() {
        return topFans;
    }

    /**
     * @return the listeners
     */
    public int getListeners() {
        return listeners;
    }

    /**
     * @param listeners the listeners to set
     */
    public void setListeners(final int listeners) {
        this.listeners = listeners;
    }

    /**
     * @param visitable  the visitable object
     */
    public void setStatistics(final Visitable visitable) {
        WrappedVisitor visitor = new WrappedVisitor(this);
        visitable.accept(visitor);
    }

    /**
     * @param command  the command to be executed
     * @param library  the library on which the command is executed
     */
    @Override
    public void execute(final InputCommands command, final Library library) {
        User user = command.getUser();

        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode commandJson = objectMapper.createObjectNode()
                .put("command", "wrapped")
                .put("user", command.getUsername())
                .put("timestamp", command.getTimestamp());
        if (user.getType().equals("user")) {
            // update the player before the statistics
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

            setStatistics(user);

            if (message != null) {
                commandJson.put("message", message);
            } else {
                ObjectNode result = objectMapper.createObjectNode();
                ObjectNode topArtists1 = objectMapper.createObjectNode();
                ObjectNode topGenres1 = objectMapper.createObjectNode();
                ObjectNode topSongs1 = objectMapper.createObjectNode();
                ObjectNode topAlbums1 = objectMapper.createObjectNode();
                ObjectNode topEpisodes1 = objectMapper.createObjectNode();

                ArrayList<String> topArtistsList = getTopArtists();
                ArrayList<String> topGenresList = getTopGenres();
                ArrayList<String> topSongsList = getTopSongs();
                ArrayList<String> topAlbumsList = getTopAlbums();
                ArrayList<String> topEpisodesList = getTopEpisodes();

                for (String artist : topArtistsList) {
                    topArtists1.put(artist, user.getStatistics().getTopArtists().get(artist));
                }
                for (String genre : topGenresList) {
                    topGenres1.put(genre, user.getStatistics().getTopGenres().get(genre));
                }
                for (String song : topSongsList) {
                    topSongs1.put(song, user.getStatistics().getTopSongs().get(song));
                }
                for (String album : topAlbumsList) {
                    topAlbums1.put(album, user.getStatistics().getTopAlbums().get(album));
                }
                for (String episode : topEpisodesList) {
                    topEpisodes1.put(episode, user.getStatistics().getTopEpisodes().get(episode));
                }

                result.set("topArtists", topArtists1);
                result.set("topGenres", topGenres1);
                result.set("topSongs", topSongs1);
                result.set("topAlbums", topAlbums1);
                result.set("topEpisodes", topEpisodes1);

                commandJson.set("result", result);
            }
        } else if (user.getType().equals("artist")) {
            // update the player of each user in the library
            for (User user1 : OnlineUsers.getOnlineUsers()) {
                user1.getPlayer().timestamp = command.getTimestamp();
                if (user1.getPlayer().repeatState == 0) {
                    user1.getPlayer().setRemainingTime();
                } else if (user1.getPlayer().repeatState == 1) {
                    user1.getPlayer().setRemainingTimeRepeat1();
                } else if (user1.getPlayer().repeatState == 2) {
                    user1.getPlayer().setRemainingTimeRepeat2();
                }
            }

            setStatistics((Artist) user);

            if (message != null) {
                commandJson.put("message", message);
            } else {
                ObjectNode result = objectMapper.createObjectNode();
                ObjectNode topAlbums1 = objectMapper.createObjectNode();
                ObjectNode topSongs1 = objectMapper.createObjectNode();
                ArrayNode topFans1 = objectMapper.createArrayNode();

                ArrayList<String> topAlbumsList = getTopAlbums();
                ArrayList<String> topSongsList = getTopSongs();
                ArrayList<String> topFansList = getTopFans();

                for (String album : topAlbumsList) {
                    topAlbums1.put(album, ((Artist) user).getArtistStatistics().
                            getTopAlbums().get(album));
                }
                for (String song : topSongsList) {
                    topSongs1.put(song, ((Artist) user).getArtistStatistics().
                            getTopSongs().get(song));
                }
                for (String fan : topFansList) {
                    topFans1.add(fan);
                }

                result.set("topAlbums", topAlbums1);
                result.set("topSongs", topSongs1);
                result.set("topFans", topFans1);
                result.put("listeners", (getListeners()));

                commandJson.set("result", result);
            }
        } else if (user.getType().equals("host")) {
            // update the player of each user in the library
            for (User user1 : OnlineUsers.getOnlineUsers()) {
                user1.getPlayer().timestamp = command.getTimestamp();
                if (user1.getPlayer().repeatState == 0) {
                    user1.getPlayer().setRemainingTime();
                } else if (user1.getPlayer().repeatState == 1) {
                    user1.getPlayer().setRemainingTimeRepeat1();
                } else if (user1.getPlayer().repeatState == 2) {
                    user1.getPlayer().setRemainingTimeRepeat2();
                }
            }

            setStatistics((Host) user);

            if (message != null) {
                commandJson.put("message", message);
            } else {
                ObjectNode result = objectMapper.createObjectNode();
                ObjectNode topEpisodes1 = objectMapper.createObjectNode();

                ArrayList<String> topEpisodesList = getTopEpisodes();

                for (String episode : topEpisodesList) {
                    topEpisodes1.put(episode, ((Host) user).getHostStatistics().
                            getTopEpisodes().get(episode));
                }

                result.set("topEpisodes", topEpisodes1);
                result.put("listeners", (getListeners()));

                commandJson.set("result", result);
            }
        }
        command.getCommandList().add(commandJson);
    }
}
