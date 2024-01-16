package commands;

import audioFiles.Library;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import designPatterns.commandPattern.Command;
import main.InputCommands;
import pageContent.Merch;
import pages.ArtistPage;
import users.Artist;
import users.User;

public class BuyMerch implements Command {
    private String name;
    private String message;

    public BuyMerch() {
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(final String name) {
        this.name = name;
    }

    /**
     * @return the message
     */
    public String getMessage() {
        return message;
    }

    /**
     * Buy merch from the current page.
     * @param user the user that buys the merch
     */
    public void buyMerch(final User user) {
        // check if the user is on an artist page
        if (!(user.getCurrentPage().getType().equals("artist"))) {
            message = "Cannot buy merch from this page.";
            return;
        }

        // check if the merch exists on this page
        ArtistPage artistPage = (ArtistPage) user.getCurrentPage();
        if (artistPage.getMerch().stream().noneMatch(merch -> merch.getName().equals(name))) {
            message = "The merch " + name + " doesn't exist.";
            return;
        }

        // buy the merch
        user.getBoughtMerch().add(name);

        // update the artist's revenue
        Artist artist = artistPage.getArtist();
        Merch merch = artistPage.getMerch().stream().filter(m -> m.getName().
                equals(name)).findFirst().get();
        artist.setMerchRevenue(artist.getMerchRevenue() + merch.getPrice());

        message = user.getUsername() + " has added new merch successfully.";
    }

    /**
     * Execute the buy merch command.
     * @param command the input command
     * @param library the main library
     */
    @Override
    public void execute(final InputCommands command, final Library library) {
        User user = command.getUser();
        buyMerch(user);

        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode commandJson = objectMapper.createObjectNode()
                .put("command", "buyMerch")
                .put("user", command.getUsername())
                .put("timestamp", command.getTimestamp())
                .put("message", message);
        command.getCommandList().add(commandJson);
    }
}
