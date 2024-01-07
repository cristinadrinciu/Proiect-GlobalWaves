package commands;

import audio.files.Library;
import audio.files.Playlist;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import main.InputCommands;
import platform.data.PublicPlaylists;
import visit.pattern.Visitable;
import visit.pattern.Visitor;
import user.types.User;

import java.util.ArrayList;

public class CreatePlaylistCommand implements Command {
    private String playlistName;
    private Playlist playlist = new Playlist();

    public CreatePlaylistCommand() {
    }

    /**
     * @return the playlistName
     */
    public String getPlaylistName() {
        return playlistName;
    }

    /**
     * @param playlistName the playlistName to set
     */
    public void setPlaylistName(final String playlistName) {
        this.playlistName = playlistName;
    }

    /**
     * @return the playlist
     */
    public boolean createPlaylist(final User user, final Library library) {
        // search if th playlist already exists
        if (user.getPlaylists() != null) {
            for (Playlist playlistContor : user.getPlaylists()) {
                if (playlistName.equals(playlistContor.getName())) {
                    // it already exists
                    playlist = null;
                    return false;
                }
            }
        }

        // otherwise, we create it
        playlist.setName(playlistName);
        playlist.setOwner(user);
        playlist.setPublic(true);
        playlist.setSongs(new ArrayList<>());
        playlist.setCreatedTimestamp(user.getPlayer().timestamp);

        user.getPlaylists().add(playlist);
        user.getPlayer().playlists.add(playlist);

        // add in the player of the other users
        ArrayList<User> users = library.getUsers();

        for (User userParse : users) {
            if (userParse != user) {
                userParse.getPlayer().playlists.add(playlist);
            }
        }

        // add in the public playlists
        PublicPlaylists.getPlaylists().add(playlist);
        return true;
    }

    /**
     * @param user the user that executes the command
     * @param library the library of the application
     * @return the message of the command
     */
    public String message(final User user, final Library library) {
        String message;
        boolean createdPlaylist = createPlaylist(user, library);
        if (!createdPlaylist) {
            message = "A playlist with the same name already exists.";
        } else {
            message = "Playlist created successfully.";
        }
        return message;
    }

    /**
     * Execute the command.
     * @param command the input command
     * @param library the library of the application
     */
    @Override
    public void execute(final InputCommands command, final Library library) {
        User user = command.getUser();
        String message = message(user, library);

        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode commandJson = objectMapper.createObjectNode()
                .put("command", "createPlaylist")
                .put("user", command.getUsername())
                .put("timestamp", command.getTimestamp())
                .put("message", message);

        command.getCommandList().add(commandJson);
    }
}
