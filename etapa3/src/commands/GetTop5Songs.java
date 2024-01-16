package commands;

import stream.JsonOutputStream;
import audiofiles.Library;
import audiofiles.Song;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import designpatterns.commandPattern.Command;
import main.InputCommands;

import java.util.ArrayList;

public class GetTop5Songs implements Command {
    private ArrayList<Song> top5Songs = new ArrayList<>();

    public GetTop5Songs() {
    }

    /**
     * Sorts the songs in the library by the number of likes and adds the first 5 songs
     * to the top5Songs array
     * @param library the library of the user
     */
    public void setTop5Songs(final Library library) {
        ArrayList<Song> songs = new ArrayList<>();
        songs.addAll(library.getSongs());

        // sort the array of songs
        for (int i = 0; i < songs.size() - 1; i++) {
            for (int j = i + 1; j < songs.size(); j++) {
                if (songs.get(i).getLikes() < songs.get(j).getLikes()) {
                    Song aux = songs.get(i);
                    songs.set(i, songs.get(j));
                    songs.set(j, aux);
                }
            }
        }

        for (int i = 0; i < songs.size() - 1; i++) {
            for (int j = i + 1; j < songs.size(); j++) {
                // if the number of likes is the same, sort by index in library
                if (songs.get(i).getLikes() == songs.get(j).getLikes()) {
                    Song song1 = songs.get(i);
                    Song song2 = songs.get(j);
                    if (library.getSongs().indexOf(song1) > library.getSongs().indexOf(song2)) {
                        Song aux = songs.get(i);
                        songs.set(i, songs.get(j));
                        songs.set(j, aux);
                    }
                }
            }
        }

        this.top5Songs.clear();

        final int maxSongs = 5;
        // add the first 5 songs
        if (songs.size() < maxSongs) {
            this.top5Songs.addAll(songs);
            return;
        }

        for (int i = 0; i < maxSongs; i++) {
            this.top5Songs.add(songs.get(i));
        }
    }

    /**
     * @return the top5Songs array
     */
    public ArrayList<Song> getTop5Songs() {
        return this.top5Songs;
    }

    /**
     * Executes the command
     * @param command the input command
     * @param library the library of the user
     */
    @Override
    public void execute(final InputCommands command, final Library library) {
        setTop5Songs(library);

        ArrayList<Song> songs = getTop5Songs();

        // Create an array node for the results
        ArrayNode resultsArray = JsonNodeFactory.instance.arrayNode();

        // Add the names of the songs to the results array
        for (Song song : songs) {
            resultsArray.add(song.getName());
        }

        // Create the command JSON structure
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode commandJson = objectMapper.createObjectNode()
                .put("command", "getTop5Songs")
                .put("timestamp", command.getTimestamp())
                .set("result", resultsArray);

        // Add the commandJson to the commandList
        JsonOutputStream.getCommandOutputs().add(commandJson);
    }
}
