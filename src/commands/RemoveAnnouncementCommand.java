package commands;

import page.content.Announcement;
import main.InputCommands;
import visit.pattern.Visitable;
import visit.pattern.Visitor;
import user.types.Host;
import audio.files.Library;
import user.types.User;

public class RemoveAnnouncementCommand implements Visitable {
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
     * Accepts the visitor
     * @param command the command to be executed
     * @param visitor the visitor that visits the command
     * @param library the library of the application
     */
    @Override
    public void accept(final InputCommands command, final Visitor visitor, final Library library) {
        visitor.visit(command, this, library);
    }
}
