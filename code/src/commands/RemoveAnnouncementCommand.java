package commands;

import stream.JsonOutputStream;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import designpatterns.commandPattern.Command;
import pagecontent.Announcement;
import main.InputCommands;

import users.Host;
import audiofiles.Library;
import users.User;

public class RemoveAnnouncementCommand implements Command {
    private String name;
    private String message;

    public RemoveAnnouncementCommand() {
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name of the announcement to set
     */
    public void setName(final String name) {
        this.name = name;
    }

    /**
     * @return the message
     */
    public String getMessage() {
        return this.message;
    }

    /**
     * Removes the announcement with the given name from the host's announcements
     * @param user the host
     */
    public void removeAnnouncement(final User user) {
        // check if the user is a host
        if (!user.getType().equals("host")) {
            message = user.getUsername() + " is not a host.";
            return;
        }

        // check if the announcement exists
        boolean found = false;
        for (Announcement announcement : ((Host) user).getAnnouncements()) {
            if (announcement.getName().equals(this.name)) {
                found = true;
                break;
            }
        }

        if (!found) {
            message = user.getUsername() + " has no announcement with the given name.";
            return;
        }

        // remove the announcement
        ((Host) user).getAnnouncements().removeIf(announcement -> announcement.
                getName().equals(this.name));

        // set the host page
        ((Host) user).setHostPage();

        // set the message
        message = user.getUsername()
                + " has successfully deleted the announcement.";
    }

    /**
     * Executes the command
     * @param command the input command
     * @param library the library
     */
    @Override
    public void execute(final InputCommands command, final Library library) {
        User user = command.getUser();
        removeAnnouncement(user);

        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode commandJson = objectMapper.createObjectNode()
                .put("command", "removeAnnouncement")
                .put("user", command.getUsername())
                .put("timestamp", command.getTimestamp())
                .put("message", message);

        JsonOutputStream.getCommandOutputs().add(commandJson);
    }
}
