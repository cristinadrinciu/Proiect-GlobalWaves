package commands;

import stream.JsonOutputStream;
import audiofiles.Library;
import audiofiles.Song;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import designpatterns.commandPattern.Command;
import main.InputCommands;
import users.User;

public class LikeCommand implements Command {

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
        if (!(user.getPlayer().playingNow.getType().equals("song"))) {
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
     * Executes the like command
     * @param command the input command
     * @param library the library
     */
    @Override
    public void execute(final InputCommands command, final Library library) {
        User user = command.getUser();
        user.getPlayer().timestamp = command.getTimestamp();
        if (user.getPlayer().loadedItem != null) {
            if (user.getPlayer().repeatState == 0) {
                user.getPlayer().setRemainingTime();
            }
            if (user.getPlayer().repeatState == 1) {
                user.getPlayer().setRemainingTimeRepeat1();
            }
            if (user.getPlayer().repeatState == 2) {
                user.getPlayer().setRemainingTimeRepeat2();
            }
        }

        setMessage(user);

        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode commandJson = objectMapper.createObjectNode()
                .put("command", "like")
                .put("user", command.getUsername())
                .put("timestamp", command.getTimestamp())
                .put("message", message);

        JsonOutputStream.getCommandOutputs().add(commandJson);
    }
}
