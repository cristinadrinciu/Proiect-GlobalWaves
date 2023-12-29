package commands;

import audio.files.Library;
import audio.files.Song;
import main.InputCommands;
import visit.pattern.Visitable;
import visit.pattern.Visitor;
import user.types.User;

public class LikeCommand implements Visitable {

    private String message;

    /**
     * @return the message
     */
    public String getMessage() {
        return message;
    }

    /**
     * Sets the message
     * @param user the user that wants to like or unlike
     */
    public void setMessage(final User user) {
        if (user.getPlayer().loadedItem == null) {
            message = "Please load a source before liking or unliking.";
            return;
        }
        if (!(user.getPlayer().playingNow instanceof Song)) {
            message = "Loaded source is not a song.";
            return;
        }

        boolean isLiked = false;

        // see if it is in the Liked Songs list
        if (user.getLikedSongs().contains((Song) user.getPlayer().playingNow)) {
            isLiked = true;
        }

        if (isLiked) {
            // remove from the liked list
            user.getLikedSongs().remove((Song) user.getPlayer().playingNow);
            message = "Unlike registered successfully.";
            int likes = ((Song) user.getPlayer().playingNow).getLikes();
            ((Song) user.getPlayer().playingNow).setLikes(likes - 1);
        } else {
            // add to the liked list
            user.getLikedSongs().add((Song) user.getPlayer().playingNow);
            message = "Like registered successfully.";
            int likes = ((Song) user.getPlayer().playingNow).getLikes();
            ((Song) user.getPlayer().playingNow).setLikes(likes + 1);
        }

        user.setHomePage();
    }

    /**
     * the accept method for the visitor
     * @param command the command that is being accepted
     * @param visitor the visitor that visits the command
     * @param library the library that contains the users
     */
    @Override
    public void accept(final InputCommands command, final Visitor visitor, final Library library) {
        visitor.visit(command, this, library);
    }
}