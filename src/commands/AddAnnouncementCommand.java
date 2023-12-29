package commands;

import page.content.Announcement;
import main.InputCommands;
import visit.pattern.Visitable;
import visit.pattern.Visitor;
import user.types.Host;
import audio.files.Library;
import user.types.User;

public class AddAnnouncementCommand implements Visitable {
    private String name;
    private String description;
    private String message;

    public AddAnnouncementCommand() {
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
     * @param description the description to set
     */
    public void setDescription(final String description) {
        this.description = description;
    }

    /**
     * @
     */
    public String getDescription() {
        return description;
    }

    /**
     * @return the message
     */
    public String getMessage() {
        return message;
    }

    /**
     * @param user the user to set
     */
    public void addAnnouncement(final User user, final Library library) {
        // check if the user is a host
        if (!user.getType().equals("host")) {
            message = user.getUsername() + " is not a host.";
            return;
        }

        // check if the announcement already exists
        for (Announcement announcement : ((Host) user).getAnnouncements()) {
            if (announcement.getName().equals(this.name)) {
                message = "Announcement " + this.name + " already exists.";
                return;
            }
        }

        // create the announcement
        Announcement announcement = new Announcement(this.name, this.description);

        // add the announcement to the host's announcements
        ((Host) user).getAnnouncements().add(announcement);

        // set the host page
        ((Host) user).setHostPage();

        // set the message
        message = user.getUsername() + " has successfully added new announcement.";
    }

    /**
     * Accepts the command to be executed
     * @param command the command to be executed
     * @param visitor the visitor
     * @param library the library
     */
    @Override
    public void accept(final InputCommands command, final Visitor visitor, final Library library) {
        visitor.visit(command, this, library);
    }
}
