package commands;

import main.InputCommands;
import visit.pattern.Visitable;
import visit.pattern.Visitor;
import user.types.Host;
import audio.files.Library;
import audio.files.Podcast;
import user.types.User;

public class RemovePodcastCommand implements Visitable {
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
     * Accept the visitor.
     * @param command the command to be executed
     * @param visitor the visitor
     * @param library the library of the platform
     */
    @Override
    public void accept(final InputCommands command, final Visitor visitor,
                       final Library library) {
        visitor.visit(command, this, library);
    }
}
