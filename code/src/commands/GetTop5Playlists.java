package commands;

import stream.JsonOutputStream;
import audiofiles.Library;
import audiofiles.Playlist;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import designpatterns.commandPattern.Command;
import main.InputCommands;
import platformdata.PublicPlaylists;

import java.util.ArrayList;

public class GetTop5Playlists implements Command {
    private final ArrayList<Playlist> top5Playlists = new ArrayList<>();

    public GetTop5Playlists() {
    }

    /**
     * Sort the playlists by the number of followers and by the creation timestamp
     * and add the first 5 playlists to the top5Playlists array
     */
    public void setTop5Playlists() {
        final int maxSize = 5;
        ArrayList<Playlist> playlists = new ArrayList<>(PublicPlaylists.getPlaylists());

        // sort the array of playlists
        for (int i = 0; i < playlists.size() - 1; i++) {
            for (int j = i + 1; j < playlists.size(); j++) {
                if (playlists.get(i).getFollowers() < playlists.get(j).getFollowers()) {
                    Playlist aux = playlists.get(i);
                    playlists.set(i, playlists.get(j));
                    playlists.set(j, aux);
                }
            }
        }

        for (int i = 0; i < playlists.size() - 1; i++) {
            for (int j = i + 1; j < playlists.size(); j++) {
                if (playlists.get(i).getFollowers() == playlists.get(j).getFollowers()) {
                    if (playlists.get(i).getCreatedTimestamp()
                            > playlists.get(j).getCreatedTimestamp()) {
                        Playlist aux = playlists.get(i);
                        playlists.set(i, playlists.get(j));
                        playlists.set(j, aux);
                    }
                }
            }
        }

        this.top5Playlists.clear();

        // add the first 5 playlists
        if (playlists.size() < maxSize) {
            this.top5Playlists.addAll(playlists);
            return;
        }

        for (int i = 0; i < maxSize; i++) {
            this.top5Playlists.add(playlists.get(i));
        }
    }

    /**
     * @return the top5Playlists array
     */
    public ArrayList<Playlist> getTop5Playlists() {
        return this.top5Playlists;
    }

    /**
     * Execute the getTop5Playlists command
     * @param command the input command
     * @param library the main library
     */
    @Override
    public void execute(final InputCommands command, final Library library) {
        setTop5Playlists();

        ArrayList<Playlist> playlists = getTop5Playlists();

        // Create an array node for the results
        ArrayNode resultsArray = JsonNodeFactory.instance.arrayNode();

        // Add the names of the playlists to the results array
        for (Playlist playlist : playlists) {
            resultsArray.add(playlist.getName());
        }

        // Create the command JSON structure
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode commandJson = objectMapper.createObjectNode()
                .put("command", "getTop5Playlists")
                .put("timestamp", command.getTimestamp())
                .set("result", resultsArray);

        // Add the commandJson to the commandList
        JsonOutputStream.getCommandOutputs().add(commandJson);
    }
}
