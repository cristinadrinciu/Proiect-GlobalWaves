package commands;

import audioFiles.Library;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import designPatterns.commandPattern.Command;
import main.InputCommands;
import pages.ArtistPage;
import pages.HomePage;
import pages.HostPage;
import pages.LikedContentPage;
import pages.Page;
import users.Artist;
import users.Host;
import users.User;

public class SubscribeCommand implements Command {
    private String message;

    public SubscribeCommand() {
    }

    /**
     * @return the message
     */
    public String getMessage() {
        return message;
    }

    /**
     * @param user the user that wants to subscribe
     */
    public void subscribe(final User user) {
        // check if the user is on an artist page or host page
        if (user.getCurrentPage().getType().equals("home")
                || user.getCurrentPage().getType().equals("likedContent")) {
            message = "To subscribe you need to be on the page of an artist or host.";
            return;
        }

        Page page = user.getCurrentPage();

        if (page.getType().equals("artist")) {
            // check if the user is already subscribed to the artist
            Artist artist = ((ArtistPage) page).getArtist();
            if (artist.getSubscribers().contains(user)) {
                // unsubscribe
                artist.getSubscribers().remove(user);
                message = user.getUsername() + " unsubscribed from "
                        + artist.getUsername() + " successfully.";
            } else {
                // subscribe
                artist.addObserver(user);
                message = user.getUsername() + " subscribed to "
                        + artist.getUsername() + " successfully.";
            }
        } else if (page.getType().equals("host")) {
            // check if the user is already subscribed to the host
            Host host = ((HostPage) page).getHost();
            if (host.getSubscribers().contains(user)) {
                // unsubscribe
                host.getSubscribers().remove(user);
                message = user.getUsername() + " unsubscribed from "
                        + host.getUsername() + " successfully.";
            } else {
                // subscribe
                host.addObserver(user);
                message = user.getUsername() + " subscribed to "
                        + host.getUsername() + " successfully.";
            }
        }
    }

    /**
     * @param command the input command
     * @param library the main library
     */
    @Override
    public void execute(final InputCommands command, final Library library) {
        User user = command.getUser();
        subscribe(user);

        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode commandJson = objectMapper.createObjectNode()
                .put("command", "subscribe")
                .put("user", command.getUsername())
                .put("timestamp", command.getTimestamp())
                .put("message", message);
        command.getCommandList().add(commandJson);
    }
}
