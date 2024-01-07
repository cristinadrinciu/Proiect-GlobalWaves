package commands;

import audio.files.Library;
import audio.files.Song;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import main.InputCommands;
import visit.pattern.Visitable;
import visit.pattern.Visitor;
import user.types.User;

import java.util.ArrayList;

public class ShowPreferredSongsCommand implements Command {
    private ArrayList<Song> preferredSongs = new ArrayList<>();

    public ShowPreferredSongsCommand() {
    }

    /**
     * @return the preferredSongs
     */
    public ArrayList<Song> getPreferredSongs() {
        return preferredSongs;
    }

    /**
     * @param user the user to set
     */
    public void setPreferredSongs(final User user) {
        this.preferredSongs = user.getLikedSongs();
    }

    /**
     * Execute the command
     * @param command the input command
     * @param library the main library
     */
    @Override
    public void execute(final InputCommands command, final Library library) {
        User user = command.getUser();
        setPreferredSongs(user);
        ArrayList<Song> songs = getPreferredSongs();

        ArrayNode results = JsonNodeFactory.instance.arrayNode();

        for (Song song : songs) {
            results.add(song.getName());
        }

        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode commandJson = objectMapper.createObjectNode()
                .put("command", "showPreferredSongs")
                .put("user", command.getUsername())
                .put("timestamp", command.getTimestamp())
                .set("result", results);

        command.getCommandList().add(commandJson);
    }
}
