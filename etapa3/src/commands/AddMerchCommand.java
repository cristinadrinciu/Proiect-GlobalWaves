package commands;

import stream.JsonOutputStream;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import designpatterns.commandPattern.Command;
import main.InputCommands;

import users.Artist;
import audiofiles.Library;
import pagecontent.Merch;
import users.User;

import java.util.ArrayList;

public class AddMerchCommand implements Command {
    private String name;
    private String description;
    private int price;
    private String message;

    public AddMerchCommand() {
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name of the merch to set
     */
    public void setName(final String name) {
        this.name = name;
    }

    /**
     * @return the description of the merch
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description of the merch to set
     */
    public void setDescription(final String description) {
        this.description = description;
    }

    /**
     * @return the price of the merch
     */
    public int getPrice() {
        return price;
    }

    /**
     * @param price of the merch to set
     */
    public void setPrice(final int price) {
        this.price = price;
    }

    /**
     * @return the message
     */
    public String getMessage() {
        return message;
    }

    /**
     * Adds a new merch to the artist's merchs
     * @param user  the user that adds the merch
     */
    public void addMerch(final User user) {
        // check if user is an artist
        if (!user.getType().equals("artist")) {
            message = user.getUsername() + " is not an artist.";
            return;
        }

        // check if there is already a merch with the same name
        ArrayList<Merch> merch = ((Artist) user).getMerch();
        for (Merch item : merch) {
            if (item.getName().equals(name)) {
                message = user.getUsername() + " has merchandise with the same name.";
                return;
            }
        }

        if (price < 0) {
            message = "Price for merchandise can not be negative.";
            return;
        }

        // create the merch
        Merch item = new Merch();
        item.setName(name);
        item.setDescription(description);
        item.setPrice(price);

        // add the merch to the artist's merchs
        merch.add(item);
        message = user.getUsername() + " has added new merchandise successfully.";

        // send notification to all subscribers
        ((Artist) user).notifyObservers("New Merchandise",
                "New Merchandise from " + user.getUsername() + ".");

        // update the artist page
        ((Artist) user).setArtistPage();
    }

    /**
     * Executes the command
     * @param command the input command
     * @param library the main library
     */
    @Override
    public void execute(final InputCommands command, final Library library) {
        User user = command.getUser();
        addMerch(user);

        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode commandJson = objectMapper.createObjectNode()
                .put("command", "addMerch")
                .put("user", command.getUsername())
                .put("timestamp", command.getTimestamp())
                .put("message", message);

        JsonOutputStream.getCommandOutputs().add(commandJson);
    }
}
