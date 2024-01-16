package commands;

import audioFiles.Album;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import designPatterns.commandPattern.Command;
import main.InputCommands;
import users.Artist;
import audioFiles.Library;
import users.User;

import java.util.ArrayList;

public class GetTop5Artists implements Command {
    private final ArrayList<Artist> top5Artists = new ArrayList<>();

    public GetTop5Artists() {
    }

    /**
     * Sort the artists by the number of likes and by the creation timestamp
     * and add the first 5 artists to the top5Artists array
     */
    public void setTop5Artists(final Library library) {
        final int maxSize = 5;

        // get the artists from the library
        ArrayList<Artist> artists = new ArrayList<>();

        for (User user : library.getUsers()) {
            if (user.getType().equals("artist")) {
                artists.add((Artist) user);
            }
        }

        // sort the array of artists
        for (int i = 0; i < artists.size() - 1; i++) {
            for (int j = i + 1; j < artists.size(); j++) {
                if (GetTotalLikes.getTotalLikes(artists.get(i))
                        < GetTotalLikes.getTotalLikes(artists.get(j))) {
                    Artist aux = artists.get(i);
                    artists.set(i, artists.get(j));
                    artists.set(j, aux);
                }
            }
        }

        for (int i = 0; i < artists.size() - 1; i++) {
            for (int j = i + 1; j < artists.size(); j++) {
                if (GetTotalLikes.getTotalLikes(artists.get(i))
                        == GetTotalLikes.getTotalLikes(artists.get(j))) {
                    // sort by name
                    if (artists.get(i).getUsername().compareTo(artists.get(j).getUsername()) > 0) {
                        Artist aux = artists.get(i);
                        artists.set(i, artists.get(j));
                        artists.set(j, aux);
                    }
                }
            }
        }

        this.top5Artists.clear();

        // add the first 5 artists
        if (artists.size() < maxSize) {
            this.top5Artists.addAll(artists);
            return;
        }

        for (int i = 0; i < maxSize; i++) {
            this.top5Artists.add(artists.get(i));
        }
    }

    /**
     * @return the top5Artists array
     */
    public ArrayList<Artist> getTop5Artists() {
        return top5Artists;
    }

    private static class GetTotalLikes {
        /**
         * @param artist the artist for which we want to calculate the total number of likes
         * @return the total number of likes for the artist
         */
        public static int getTotalLikes(final Artist artist) {
            int totalLikes = 0;

            for (Album album : artist.getAlbums()) {
                album.setLikes();
                totalLikes += album.getLikes();
            }

            return totalLikes;
        }
    }

    /**
     * Execute the getTop5Artists command
     * @param command the input command
     * @param library the library
     */
    @Override
    public void execute(final InputCommands command, final Library library) {
        setTop5Artists(library);

        ArrayList<Artist> artists = getTop5Artists();

        // Create an array node for the results
        ArrayNode resultsArray = JsonNodeFactory.instance.arrayNode();

        // Add the names of the artists
        for (Artist artist : artists) {
            resultsArray.add(artist.getUsername());
        }

        // Create the command JSON structure
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode commandJson = objectMapper.createObjectNode()
                .put("command", "getTop5Artists")
                .put("timestamp", command.getTimestamp())
                .set("result", resultsArray);

        // Add the commandJson to the commandList
        command.getCommandList().add(commandJson);
    }
}
