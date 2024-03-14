package commands;

import stream.JsonOutputStream;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import designpatterns.commandPattern.Command;
import main.InputCommands;
import platformdata.OnlineUsers;

import users.Host;
import audiofiles.Library;
import audiofiles.Podcast;
import users.User;

public class RemovePodcastCommand implements Command {
    private String name;
    private String message;

    public RemovePodcastCommand() {
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @return the message
     */
    public String getMessage() {
        return message;
    }

    /**
     * @param name the name to set
     */
    public void setName(final String name) {
        this.name = name;
    }

    /**
     * Remove a podcast from the platform.
     * @param user the user that wants to remove the podcast
     * @param library the library of the platform
     */
    public void removePodcast(final User user, final Library library) {
        // check if the user is a host
        if (!user.getType().equals("host")) {
            message = user.getUsername() + " is not a host.";
            return;
        }

        // check if the podcast exists
        boolean found = false;
        for (Podcast podcast : ((Host) user).getPodcasts()) {
            if (podcast.getName().equals(this.name)) {
                found = true;
                break;
            }
        }

        if (!found) {
            message = user.getUsername() + " doesn't have a podcast with the given name.";
            return;
        }

        // get the podcast
        Podcast deletePodcast = new Podcast();
        for (Podcast podcast : ((Host) user).getPodcasts()) {
            if (podcast.getName().equals(this.name)) {
                deletePodcast = podcast;
                break;
            }
        }

        // check if the podcast can be removed
        if (!CanBeDeleted.canDeletePodcast(deletePodcast, library)) {
            message = user.getUsername() + " can't delete this podcast.";
            return;
        }

        // delete the podcast
        ((Host) user).getPodcasts().removeIf(podcast -> podcast.getName().equals(this.name));

        // remove the podcast from the platform
        library.getPodcasts().removeIf(podcast -> podcast.getName().equals(this.name));

        // set the host page
        ((Host) user).setHostPage();

        // set the message
        message = user.getUsername() + " deleted the podcast successfully.";
    }

    private static class CanBeDeleted {
        public static boolean canDeletePodcast(final Podcast podcast,
                                               final Library library) {
            // check if any user had loaded in the player the podcast
            for (User user : library.getUsers()) {
                if (user.getPlayer().loadedItem != null && user.getPlayer().
                        loadedItem.equals(podcast)) {
                    return false;
                }
            }
            return true;
        }
    }

    /**
     * Execute the remove podcast command.
     * @param command the input command
     * @param library the library of the platform
     */
    @Override
    public void execute(final InputCommands command, final Library library) {
        User user = command.getUser();

        // update the player for each user
        for (User user1 : OnlineUsers.getOnlineUsers()) {
            user1.getPlayer().timestamp = command.getTimestamp();
            if (user1.getPlayer().repeatState == 0) {
                user1.getPlayer().setRemainingTime();
            } else if (user1.getPlayer().repeatState == 1) {
                user1.getPlayer().setRemainingTimeRepeat1();
            } else if (user1.getPlayer().repeatState == 2) {
                user1.getPlayer().setRemainingTimeRepeat2();
            }
        }

        removePodcast(user, library);

        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode commandJson = objectMapper.createObjectNode()
                .put("command", "removePodcast")
                .put("user", command.getUsername())
                .put("timestamp", command.getTimestamp())
                .put("message", message);

        // add the command to the JsonStream
        JsonOutputStream.getCommandOutputs().add(commandJson);
    }
}
