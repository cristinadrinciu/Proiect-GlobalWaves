package commands;

import audio.files.Library;
import audio.files.Playlist;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import main.InputCommands;
import notification.Notification;
import platform.data.PublicPlaylists;
import visit.pattern.Visitable;
import visit.pattern.Visitor;
import user.types.User;

public class FollowCommand implements Command {
    private String message;

    public FollowCommand() {
    }

    /**
     * @return the message
     */
    public String getMessage() {
        return message;
    }

    /**
     * Follows or unfollows a playlist
     * @param user the user that wants to follow or unfollow a playlist
     */
    public void followPlaylist(final User user) {
        if (user.getSelectedItem() == null) {
            message = "Please select a source before following or unfollowing.";
            return;
        }
        if (!(user.getSelectedItem() instanceof Playlist)) {
            message = "The selected source is not a playlist.";
            return;
        }
        Playlist playlist = (Playlist) user.getSelectedItem();
        if (user.getPlaylists().contains(playlist)) {
            message = "You cannot follow or unfollow your own playlist.";
            return;
        }
        if (user.getFollowedPlaylists().contains(playlist)) {
            // unfollow the playlist
            int followers = playlist.getFollowers();
            followers--;
            int index = PublicPlaylists.getPlaylists().indexOf(playlist);
            playlist.setFollowers(followers);
            PublicPlaylists.getPlaylists().get(index).setFollowers(followers);

            // remove from the list of followed playlists
            user.getFollowedPlaylists().remove(playlist);
            message = "Playlist unfollowed successfully.";
        } else {
            // otherwise follow the playlist
            int followers = playlist.getFollowers();
            followers++;
            int index = PublicPlaylists.getPlaylists().indexOf(playlist);
            playlist.setFollowers(followers);
            PublicPlaylists.getPlaylists().get(index).setFollowers(followers);

            // add in the list of followed playlist
            user.getFollowedPlaylists().add(playlist);
            message = "Playlist followed successfully.";

            // send notification to the owner of the playlist
            Notification notification = new Notification();
            notification.setName("New Follower");
            notification.setDescription(user.getUsername() + " followed your playlist " + playlist.getName() + ".");
            playlist.getOwner().getNotifications().add(notification);
        }

        user.setHomePage();
    }

    /**
     * Executes the follow command
     * @param command the input command
     * @param library the main library
     */
    @Override
    public void execute(final InputCommands command, final Library library) {
        User user = command.getUser();
        followPlaylist(user);

        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode commandJson = objectMapper.createObjectNode()
                .put("command", "follow")
                .put("user", command.getUsername())
                .put("timestamp", command.getTimestamp())
                .put("message", message);

        command.getCommandList().add(commandJson);
    }
}
