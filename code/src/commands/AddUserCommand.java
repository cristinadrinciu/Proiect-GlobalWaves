package commands;
import stream.JsonOutputStream;
import audiofiles.Album;
import audiofiles.AudioFile;
import audiofiles.Library;
import audiofiles.Playlist;
import audiofiles.Podcast;
import audiofiles.Song;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import designpatterns.commandPattern.Command;
import pagecontent.Announcement;
import main.InputCommands;
import pages.HomePage;
import player.Player;
import platformdata.OnlineUsers;
import users.Artist;
import users.Host;
import users.User;

import java.util.ArrayList;

public class AddUserCommand implements Command {
    private String username;
    private int age;
    private String city;

    private String type;

    private String message;

    public AddUserCommand() {
    }

    /**
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username the username to set
     */
    public void setUsername(final String username) {
        this.username = username;
    }

    /**
     * @return the age
     */
    public int getAge() {
        return age;
    }

    /**
     * @param age the age to set
     */
    public void setAge(final int age) {
        this.age = age;
    }

    /**
     * @return the city
     */
    public String getCity() {
        return city;
    }

    /**
     * @param city the city to set
     */
    public void setCity(final String city) {
        this.city = city;
    }

    /**
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(final String type) {
        this.type = type;
    }

    /**
     * @return the message
     */
    public String getMessage() {
        return this.message;
    }

    /**
     * Adds a user to the library
     * @param library the library
     */
    public void addUser(final Library library) {
        // check if the user already exists
        for (User user : library.getUsers()) {
            if (user.getUsername().equals(this.username)) {
                this.message = "The username " + this.username
                        + " is already taken.";
                return;
            }
        }

        if (this.type.equals("user")) {
            User user = new User();
            user.setUsername(this.username);
            user.setAge(this.age);
            user.setCity(this.city);
            user.setType("user");
            user.setStatusOnline(true);
            user.setPlayer(Player.getInstance(user, library));
            user.setPlaylists(new ArrayList<Playlist>());
            user.setLikedSongs(new ArrayList<Song>());
            user.setFollowedPlaylists(new ArrayList<Playlist>());
            user.setLastSearch(new ArrayList<AudioFile>());
            user.setLastSearchUsers(new ArrayList<User>());
            user.setHomePage(new HomePage());
            user.setCurrentPage(user.getHomePage());
            user.getNavigationHistory().add(user.getCurrentPage());

            // add the user to the library
            library.getUsers().add(user);

            // add the user to the OnlineUsers
            OnlineUsers.getOnlineUsers().add(user);
        }
        if (this.type.equals("artist")) {
            Artist user = new Artist();
            user.setUsername(this.username);
            user.setAge(this.age);
            user.setCity(this.city);
            user.setType("artist");
            user.setStatusOnline(true);
            user.setPlayer(Player.getInstance(user, library));
            user.setPlaylists(new ArrayList<Playlist>());
            user.setLikedSongs(new ArrayList<Song>());
            user.setFollowedPlaylists(new ArrayList<Playlist>());
            user.setAlbums(new ArrayList<Album>());
            user.setHomePage(new HomePage());
            user.setCurrentPage(user.getHomePage());
            user.getNavigationHistory().add(user.getCurrentPage());

            // add the artist to the library
            library.getUsers().add(user);
        }
        if (this.type.equals("host")) {
            Host user = new Host();
            user.setUsername(this.username);
            user.setAge(this.age);
            user.setCity(this.city);
            user.setType("host");
            user.setStatusOnline(true);
            user.setPlayer(Player.getInstance(user, library));
            user.setPlaylists(new ArrayList<Playlist>());
            user.setLikedSongs(new ArrayList<Song>());
            user.setFollowedPlaylists(new ArrayList<Playlist>());
            user.setPodcasts(new ArrayList<Podcast>());
            user.setAnnouncements(new ArrayList<Announcement>());
            user.setHomePage(new HomePage());
            user.setCurrentPage(user.getHomePage());
            user.getNavigationHistory().add(user.getCurrentPage());

            // add the host to the library
            library.getUsers().add(user);
        }

        this.message = "The username " + this.username + " has been added successfully.";
    }

    /**
     * Executes the command
     * @param command the input command
     * @param library the library
     */
    @Override
    public void execute(final InputCommands command, final Library library) {
        addUser(library);

        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode commandJson = objectMapper.createObjectNode()
                .put("command", "addUser")
                .put("user", username)
                .put("timestamp", command.getTimestamp())
                .put("message", message);

        // Add to the stream
        JsonOutputStream.addJsonNode(commandJson);
    }
}
