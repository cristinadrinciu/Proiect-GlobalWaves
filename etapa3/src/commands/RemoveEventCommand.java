package commands;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import main.InputCommands;
import visit.pattern.Visitable;
import visit.pattern.Visitor;
import user.types.Artist;
import page.content.Event;
import audio.files.Library;
import user.types.User;

public class RemoveEventCommand implements Command {
    private String name;
    private String message;

    public RemoveEventCommand() {
    }

    /**
     * @return the name of the event to be removed
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name of the event to be removed
     */
    public void setName(final String name) {
        this.name = name;
    }

    /**
     * @return the message to be displayed after the event is removed
     */

    public String getMessage() {
        return message;
    }

    /**
     * @param user the user that wants to remove the event
     */
    public void removeEvent(final User user) {
        // check if the user is an artist
        if (!user.getType().equals("artist")) {
            message = user.getUsername() + " is not an artist.";
            return;
        }

        // check if the event exists
        boolean found = false;
        for (Event event : ((Artist) user).getEvents()) {
            if (event.getName().equals(this.name)) {
                found = true;
                break;
            }
        }

        if (!found) {
            message = user.getUsername() + " doesn't have an event with the given name.";
            return;
        }

        // find the event
        Event deleteEvent = new Event();
        for (Event event : ((Artist) user).getEvents()) {
            if (event.getName().equals(this.name)) {
                deleteEvent = event;
                break;
            }
        }

        // delete the event from the artist's events
        ((Artist) user).getEvents().remove(deleteEvent);

        // set the artist page
        ((Artist) user).setArtistPage();

        message = user.getUsername() + " deleted the event successfully.";
    }

    /**
     * Execute the command
     * @param command the input command
     * @param library the main library
     */
    @Override
    public void execute(final InputCommands command, final Library library) {
        User user = command.getUser();
        removeEvent(user);

        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode commandJson = objectMapper.createObjectNode()
                .put("command", "removeEvent")
                .put("user", command.getUsername())
                .put("timestamp", command.getTimestamp())
                .put("message", message);

        command.getCommandList().add(commandJson);
    }
}
