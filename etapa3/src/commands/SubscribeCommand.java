package commands;

import audio.files.Library;
import main.InputCommands;
import pages.*;
import user.types.Artist;
import user.types.Host;
import user.types.User;
import visit.pattern.Visitable;
import visit.pattern.Visitor;

public class SubscribeCommand implements Visitable {
    private String message;

    public SubscribeCommand() {
    }

    /**
     * @return the message
     */
    public String getMessage() {
        return message;
    }

    public void subscribe(User user) {
        // check if the user is on an artist page or host page
        if(user.getCurrentPage() instanceof HomePage
                || user.getCurrentPage() instanceof LikedContentPage) {
            message = "To subscribe you need to be on the page of an artist or host.";
            return;
        }

        Page page = user.getCurrentPage();

        if(page instanceof ArtistPage) {
            // check if the user is already subscribed to the artist
            Artist artist = ((ArtistPage) page).getArtist();
            if(artist.getSubscribers().contains(user)) {
                // unsubscribe
                artist.getSubscribers().remove(user);
                message = user.getUsername() + " unsubscribed from " + artist.getUsername() + " successfully.";
            } else {
                // subscribe
                artist.getSubscribers().add(user);
                message = user.getUsername() + " subscribed to " + artist.getUsername() + " successfully.";
            }
        } else if (page instanceof HostPage) {
            // check if the user is already subscribed to the host
            Host host = ((HostPage) page).getHost();
            if(host.getSubscribers().contains(user)) {
                // unsubscribe
                host.getSubscribers().remove(user);
                message = user.getUsername() + " unsubscribed from " + host.getUsername() + " successfully.";
            } else {
                // subscribe
                host.getSubscribers().add(user);
                message = user.getUsername() + " subscribed to " + host.getUsername() + " successfully.";
            }
        }
    }

    @Override
    public void accept(final InputCommands command, final Visitor visitor, final Library library) {
        visitor.visit(command, this, library);
    }
}
