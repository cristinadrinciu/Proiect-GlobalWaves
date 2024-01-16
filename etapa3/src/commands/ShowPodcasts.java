package commands;

import stream.JsonOutputStream;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import designpatterns.commandPattern.Command;
import fileio.input.EpisodeInput;
import main.InputCommands;
import users.Host;
import audiofiles.Library;
import audiofiles.Podcast;

import java.util.ArrayList;

public class ShowPodcasts implements Command {
    private String username;
    private ArrayList<Podcast> podcasts;

    public ShowPodcasts() {
    }

    /**
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username the user to set
     */
    public void setUsername(final String username) {
        this.username = username;
    }

    /**
     * @return the podcasts
     */
    public ArrayList<Podcast> getPodcasts() {
        return podcasts;
    }

    /**
     * @param library the library to set
     */
    public void setPodcasts(final Library library) {
        // find the user
        for (int i = 0; i < library.getUsers().size(); i++) {
            if (library.getUsers().get(i).getUsername().equals(this.username)) {
                this.podcasts = ((Host) (library.getUsers().get(i))).getPodcasts();
                return;
            }
        }
    }

    /**
     * Execute the command
     * @param command the command to execute
     * @param library the library to execute the command on
     */
    @Override
    public void execute(final InputCommands command, final Library library) {
        setPodcasts(library);

        // Create an array node for the results
        ArrayNode resultsArray = JsonNodeFactory.instance.arrayNode();

        // the results array has the name of the podcast and the list of episodes
        for (Podcast podcast : podcasts) {
            ObjectNode podcastNode = JsonNodeFactory.instance.objectNode()
                    .put("name", podcast.getName());

            ArrayNode episodesArray = JsonNodeFactory.instance.arrayNode();
            for (EpisodeInput episode : podcast.getEpisodes()) {
                episodesArray.add(episode.getName());
            }

            podcastNode.set("episodes", episodesArray);
            resultsArray.add(podcastNode);
        }

        // Create the command JSON structure
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode commandJson = objectMapper.createObjectNode()
                .put("command", "showPodcasts")
                .put("user", username)
                .put("timestamp", command.getTimestamp())
                .set("result", resultsArray);

        // Add the commandJson to the commandList
        JsonOutputStream.getCommandOutputs().add(commandJson);
    }
}
