package commands;

import audioFiles.Album;
import audioFiles.Library;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import designPatterns.commandPattern.Command;
import main.InputCommands;
import platformData.PublicAlbums;
import java.util.ArrayList;

public class GetTop5Albums implements Command {
    private final ArrayList<Album> top5Albums = new ArrayList<>();

    public GetTop5Albums() {
    }

    /**
     * Sort the albums by the number of times they were played and by the creation timestamp
     * and add the first 5 albums to the top5Albums array
     */
    public void setTop5Albums() {
        final int maxSize = 5;
        ArrayList<Album> albums = new ArrayList<>(PublicAlbums.getPublicAlbums());

        // set the likes for each album
        for (Album album : albums) {
            album.setLikes();
        }

        // sort the array of albums by the number of likes
        for (int i = 0; i < albums.size() - 1; i++) {
            for (int j = i + 1; j < albums.size(); j++) {
                if (albums.get(i).getLikes() < albums.get(j).getLikes()) {
                    Album aux = albums.get(i);
                    albums.set(i, albums.get(j));
                    albums.set(j, aux);
                }
            }
        }

        for (int i = 0; i < albums.size() - 1; i++) {
            for (int j = i + 1; j < albums.size(); j++) {
                if (albums.get(i).getLikes() == albums.get(j).getLikes()) {
                    // sort by name
                    if (albums.get(i).getName().compareTo(albums.
                            get(j).getName()) > 0) {
                        Album aux = albums.get(i);
                        albums.set(i, albums.get(j));
                        albums.set(j, aux);
                    }
                }
            }
        }

        this.top5Albums.clear();

        // add the first 5 albums
        if (albums.size() < maxSize) {
            this.top5Albums.addAll(albums);
            return;
        }

        for (int i = 0; i < maxSize; i++) {
            this.top5Albums.add(albums.get(i));
        }
    }

    /**
     * @return the top5Albums array
     */
    public ArrayList<Album> getTop5Albums() {
        return this.top5Albums;
    }

    /**
     * Execute the command
     * @param command the input command
     * @param library the main library
     */
    @Override
    public void execute(final InputCommands command, final Library library) {
        setTop5Albums();

        ArrayList<Album> albums = getTop5Albums();

        // Create an array node for the results
        ArrayNode resultsArray = JsonNodeFactory.instance.arrayNode();

        // Add the names of the online users to the results array
        for (Album album : albums) {
            resultsArray.add(album.getName());
        }

        // Create the command JSON structure
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode commandJson = objectMapper.createObjectNode()
                .put("command", "getTop5Albums")
                .put("timestamp", command.getTimestamp())
                .set("result", resultsArray);

        // Add the commandJson to the commandList
        command.getCommandList().add(commandJson);
    }
}
