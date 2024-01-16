package commands;

import stream.JsonOutputStream;
import audiofiles.Album;
import audiofiles.Song;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import designpatterns.commandPattern.Command;
import main.InputCommands;

import users.Artist;
import audiofiles.Library;

import java.util.ArrayList;

public class ShowAlbums implements Command {
    private String username;
    private ArrayList<Album> albums;

    public ShowAlbums() {
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
     * getter for albums
     * @return the albums
     */
    public ArrayList<Album> getAlbums() {
        return albums;
    }

    /**
     * setter for albums
     * @param library the library
     */
    public void setAlbums(final Library library) {
        // find the user
        for (int i = 0; i < library.getUsers().size(); i++) {
            if (library.getUsers().get(i).getUsername().equals(this.username)) {
                this.albums = ((Artist) (library.getUsers().get(i))).getAlbums();
                return;
            }
        }
    }

    /**
     * Execute the command
     * @param command the input command
     * @param library the library
     */
    @Override
    public void execute(final InputCommands command, final Library library) {
        setAlbums(library);

        // Create an array node for the results
        ArrayNode resultsArray = JsonNodeFactory.instance.arrayNode();

        // the results array has the name of the album and the list of songs
        for (Album album : albums) {
            ObjectNode albumNode = JsonNodeFactory.instance.objectNode()
                    .put("name", album.getName());

            ArrayNode songsArray = JsonNodeFactory.instance.arrayNode();
            for (Song song : album.getSongs()) {
                songsArray.add(song.getName());
            }

            albumNode.set("songs", songsArray);
            resultsArray.add(albumNode);
        }

        // Create the command JSON structure
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode commandJson = objectMapper.createObjectNode()
                .put("command", "showAlbums")
                .put("user", username)
                .put("timestamp", command.getTimestamp())
                .set("result", resultsArray);

        // Add the commandJson to the commandList
        JsonOutputStream.getCommandOutputs().add(commandJson);
    }
}
