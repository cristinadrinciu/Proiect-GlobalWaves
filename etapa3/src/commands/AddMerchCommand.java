package commands;

import main.InputCommands;
import notification.Notification;
import visit.pattern.Visitable;
import visit.pattern.Visitor;
import user.types.Artist;
import audio.files.Library;
import page.content.Merch;
import user.types.User;

import java.util.ArrayList;

public class AddMerchCommand implements Visitable {
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
        for (User subscriber : ((Artist) user).getSubscribers()) {
            Notification newNotification = new Notification();
            newNotification.setName("New Merchandise");
            newNotification.setDescription("New Merchandise from " + user.getUsername() + ".");
            subscriber.getNotifications().add(newNotification);
        }

        // update the artist page
        ((Artist) user).setArtistPage();
    }

    /**
     * Accept method for the visitor
     * @param command  the command to be executed
     * @param visitor  the visitor
     * @param library  the library
     */
    @Override
    public void accept(final InputCommands command, final Visitor visitor, final Library library) {
        visitor.visit(command, this, library);
    }
}
