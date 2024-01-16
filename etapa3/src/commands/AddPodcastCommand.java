package commands;

import audioFiles.Library;
import audioFiles.Podcast;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import designPatterns.commandPattern.Command;
import fileio.input.EpisodeInput;
import main.InputCommands;

import users.Host;
import users.User;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class AddPodcastCommand implements Command {
    private String name;
    private ArrayList<EpisodeInput> episodes;
    private String message;

    public AddPodcastCommand() {
    }

    /**
     * @return the message
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the new name
     */
    public void setName(final String name) {
        this.name = name;
    }

    /**
     * @return the episodes
     */
    public ArrayList<EpisodeInput> getEpisodes() {
        return episodes;
    }

    /**
     * @param episodes the new episodes
     */
    public void setEpisodes(final ArrayList<EpisodeInput> episodes) {
        this.episodes = episodes;
    }

    /**
     * @return the message
     */
    public String getMessage() {
        return message;
    }

    /**
     * Adds a podcast to the library
     * @param user the user that wants to add the podcast
     * @param library the library
     */
    public void addPodcast(final User user, final Library library) {
        // check if the user is a host
        if (!user.getType().equals("host")) {
            message = user.getUsername() + " is not a host.";
            return;
        }

        // if it is a host, assign the host to a variable
        Host host = (Host) user;

        // check if the podcast already exists
        for (Podcast podcast : host.getPodcasts()) {
            if (podcast.getName().equals(this.name)) {
                // it exists
                message = user.getUsername() + " has another podcast with the same name.";
                return;
            }
        }

        // see if the podcast has any duplicate episodes
        if (hasDuplicateEpisodes(this.episodes)) {
            message = "Podcast " + this.name + " has duplicate episodes.";
            return;
        }

        // create the podcast
        Podcast podcast = new Podcast();
        podcast.setName(this.name);
        podcast.setEpisodes(this.episodes);
        podcast.setOwner(host.getUsername());

        // add the podcast to the library
        library.getPodcasts().add(podcast);

        // add the podcast to the host
        host.getPodcasts().add(podcast);

        // set the host page
        host.setHostPage();

        // set the message
        message = host.getUsername() + " has added new podcast successfully.";

        // send  notification to all subscribers
        host.notifyObservers("New Podcast",
                "New Podcast from " + host.getUsername() + ".");

    }

    /**
     * Checks if a podcast has duplicate episodes
     * @param podcastEpisodes the episodes of the podcast
     * @return true if the podcast has duplicate episodes, false otherwise
     */
    private boolean hasDuplicateEpisodes(final ArrayList<EpisodeInput> podcastEpisodes) {
        Set<String> seenEpisodeNames = new HashSet<>();
        for (EpisodeInput episode : podcastEpisodes) {
            // Check if the episode name has been seen before
            if (!seenEpisodeNames.add(episode.getName())) {
                // Duplicate found
                return true;
            }
        }

        // No duplicates found
        return false;
    }

    /**
     * Executes the command
     * @param command the input command
     * @param library the main library
     */
    @Override
    public void execute(final InputCommands command, final Library library) {
        User user = command.getUser();
        addPodcast(user, library);

        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode commandJson = objectMapper.createObjectNode()
                .put("command", "addPodcast")
                .put("user", command.getUsername())
                .put("timestamp", command.getTimestamp())
                .put("message", message);

        command.getCommandList().add(commandJson);
    }
}
