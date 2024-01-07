package commands;

import audio.files.Library;
import audio.files.Song;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import main.InputCommands;
import org.checkerframework.checker.units.qual.A;
import platform.data.OnlineUsers;
import user.types.Artist;
import user.types.Host;
import user.types.User;
import visit.pattern.Visitable;
import visit.pattern.Visitor;

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

    public void setStatisticsUser(User user) {
        // if there are no data in the user's statistics, the message is "No data to show for user ${username}.", otherwise is null
        // check if the user has any data in his statistics
        if(user.getStatistics().getTopArtists().isEmpty() && user.getStatistics().getTopGenres().isEmpty() &&
                user.getStatistics().getTopSongs().isEmpty() && user.getStatistics().getTopAlbums().isEmpty() &&
                user.getStatistics().getTopEpisodes().isEmpty()) {
            message = "No data to show for user " + user.getUsername() + ".";
            return;
        }
        message = null;
        // get the song names
        topSongs.addAll(user.getStatistics().topSongs());
        topArtists.addAll(user.getStatistics().topArtists());
        topGenres.addAll(user.getStatistics().topGenres());
        topAlbums.addAll(user.getStatistics().topAlbums());
        topEpisodes.addAll(user.getStatistics().topEpisodes());
    }

    public void setStatisticsArtist(Artist artist) {
        // if there are no data in the artist's statistics, the message is "No data to show for artist ${artist_name}.", otherwise is null
        // check if the artist has any data in his statistics
        if(artist.getArtistStatistics().getTopAlbums().isEmpty() && artist.getArtistStatistics().getTopSongs().isEmpty() &&
                artist.getArtistStatistics().getTopFans().isEmpty() && artist.getArtistStatistics().getListeners() == 0) {
            message = "No data to show for artist " + artist.getUsername() + ".";
            return;
        }
        message = null;
        // get the song names
        topSongs.addAll(artist.getArtistStatistics().topSongs());
        topAlbums.addAll(artist.getArtistStatistics().topAlbums());
        topFans.addAll(artist.getArtistStatistics().topFans());
        artist.getArtistStatistics().setListeners();
        listeners = artist.getArtistStatistics().getListeners();
    }

    public void setStatisticsHost(Host host) {
        // if there are no data in the host's statistics, the message is "No data to show for host ${host_name}.", otherwise is null
        // check if the host has any data in his statistics
        if(host.getHostStatistics().getTopEpisodes().isEmpty() && host.getHostStatistics().getTopFans().isEmpty() &&
                host.getHostStatistics().getListeners() == 0) {
            message = "No data to show for host " + host.getUsername() + ".";
            return;
        }
        message = null;
        topEpisodes.addAll(host.getHostStatistics().topEpisodes());
        host.getHostStatistics().setListeners();
        listeners = host.getHostStatistics().getListeners();
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
     * @param command  the command to be executed
     * @param library  the library on which the command is executed
     */
    @Override
    public void execute(InputCommands command, Library library) {
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

            setStatisticsUser(user);

            if (message != null) {
                commandJson.put("message", message);
            } else {
                ObjectNode result = objectMapper.createObjectNode();
                ObjectNode topArtists = objectMapper.createObjectNode();
                ObjectNode topGenres = objectMapper.createObjectNode();
                ObjectNode topSongs = objectMapper.createObjectNode();
                ObjectNode topAlbums = objectMapper.createObjectNode();
                ObjectNode topEpisodes = objectMapper.createObjectNode();

                ArrayList<String> topArtistsList = getTopArtists();
                ArrayList<String> topGenresList = getTopGenres();
                ArrayList<String> topSongsList = getTopSongs();
                ArrayList<String> topAlbumsList = getTopAlbums();
                ArrayList<String> topEpisodesList = getTopEpisodes();

                for (String artist : topArtistsList) {
                    topArtists.put(artist, user.getStatistics().getTopArtists().get(artist));
                }
                for (String genre : topGenresList) {
                    topGenres.put(genre, user.getStatistics().getTopGenres().get(genre));
                }
                for (String song : topSongsList) {
                    topSongs.put(song, user.getStatistics().getTopSongs().get(song));
                }
                for (String album : topAlbumsList) {
                    topAlbums.put(album, user.getStatistics().getTopAlbums().get(album));
                }
                for (String episode : topEpisodesList) {
                    topEpisodes.put(episode, user.getStatistics().getTopEpisodes().get(episode));
                }

                result.set("topArtists", topArtists);
                result.set("topGenres", topGenres);
                result.set("topSongs", topSongs);
                result.set("topAlbums", topAlbums);
                result.set("topEpisodes", topEpisodes);

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

            setStatisticsArtist((Artist) user);


            if (message != null) {
                commandJson.put("message", message);
            } else {
                ObjectNode result = objectMapper.createObjectNode();
                ObjectNode topAlbums = objectMapper.createObjectNode();
                ObjectNode topSongs = objectMapper.createObjectNode();
                ArrayNode topFans = objectMapper.createArrayNode();

                ArrayList<String> topAlbumsList = getTopAlbums();
                ArrayList<String> topSongsList = getTopSongs();
                ArrayList<String> topFansList = getTopFans();

                for (String album : topAlbumsList) {
                    topAlbums.put(album, ((Artist) user).getArtistStatistics().getTopAlbums().get(album));
                }
                for (String song : topSongsList) {
                    topSongs.put(song, ((Artist) user).getArtistStatistics().getTopSongs().get(song));
                }
                for (String fan : topFansList) {
                    topFans.add(fan);
                }

                result.set("topAlbums", topAlbums);
                result.set("topSongs", topSongs);
                result.set("topFans", topFans);
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

            setStatisticsHost((Host) user);

            if (message != null) {
                commandJson.put("message", message);
            } else {
                ObjectNode result = objectMapper.createObjectNode();
                ObjectNode topEpisodes = objectMapper.createObjectNode();

                ArrayList<String> topEpisodesList = getTopEpisodes();

                for (String episode : topEpisodesList) {
                    topEpisodes.put(episode, ((Host) user).getHostStatistics().getTopEpisodes().get(episode));
                }

                result.set("topEpisodes", topEpisodes);
                result.put("listeners", (getListeners()));

                commandJson.set("result", result);
            }
        }
        command.getCommandList().add(commandJson);
    }
}
