package commands;

import stream.JsonOutputStream;
import audiofiles.Library;
import audiofiles.Playlist;
import audiofiles.Song;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import designpatterns.commandPattern.Command;
import main.InputCommands;
import users.User;

import java.util.ArrayList;

public class ShowPlaylistsCommand implements Command {
    private ArrayList<Playlist> playlists;

    /**
     * @return the playlists
     */
    public ArrayList<Playlist> getPlaylists() {
        return playlists;
    }

    /**
     * @param user the user to set
     */
    public void setPlaylists(final User user) {
        this.playlists = user.getPlaylists();
    }

    /**
     * Execute the command
     * @param command the input command
     * @param library the main library
     */
    @Override
    public void execute(final InputCommands command, final Library library) {
        User user = command.getUser();
        setPlaylists(user);

        ArrayNode resultsArray = JsonNodeFactory.instance.arrayNode();

        for (Playlist playlist : playlists) {
            ObjectNode playlistNode = JsonNodeFactory.instance.objectNode()
                    .put("name", playlist.getName());

            ArrayNode songsArray = JsonNodeFactory.instance.arrayNode();
            for (Song song : playlist.getSongs()) {
                songsArray.add(song.getName());
            }

            playlistNode.set("songs", songsArray);
            resultsArray.add(playlistNode);
            playlistNode.put("visibility", playlist.isPublic() ? "public" : "private")
                    .put("followers", playlist.getFollowers());
        }

        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode commandJson = objectMapper.createObjectNode()
                .put("command", "showPlaylists")
                .put("user", command.getUsername())
                .put("timestamp", command.getTimestamp())
                .set("result", resultsArray);

        JsonOutputStream.addJsonNode(commandJson);
    }
}
