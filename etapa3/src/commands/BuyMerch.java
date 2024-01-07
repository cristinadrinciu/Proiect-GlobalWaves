package commands;

import audio.files.Library;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import main.InputCommands;
import page.content.Merch;
import pages.ArtistPage;
import user.types.Artist;
import user.types.User;
import visit.pattern.Visitable;
import visit.pattern.Visitor;

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

    public void buyMerch(User user) {
        // check if the user is on an artist page
        if(!(user.getCurrentPage() instanceof ArtistPage)) {
            message = "Cannot buy merch from this page.";
            return;
        }

        // check if the merch exists on this page
        ArtistPage artistPage = (ArtistPage) user.getCurrentPage();
        if(artistPage.getMerch().stream().noneMatch(merch -> merch.getName().equals(name))) {
            message = "The merch " + name + " doesn't exist.";
            return;
        }

        // buy the merch
        user.getBoughtMerch().add(name);

        // update the artist's revenue
        Artist artist = artistPage.getArtist();
        Merch merch = artistPage.getMerch().stream().filter(m -> m.getName().equals(name)).findFirst().get();
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
