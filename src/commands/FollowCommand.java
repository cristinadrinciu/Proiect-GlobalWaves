package commands;

import audio.files.Library;
import audio.files.Playlist;
import main.InputCommands;
import platform.data.PublicPlaylists;
import visit.pattern.Visitable;
import visit.pattern.Visitor;
import user.types.User;

public class FollowCommand implements Visitable {
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
        }

        user.setHomePage();
    }

    /**
     * Accepts the visitor
     * @param command the command to be accepted
     * @param visitor the visitor
     * @param library the library
     */
    @Override
    public void accept(final InputCommands command, final Visitor visitor, final Library library) {
        visitor.visit(command, this, library);
    }
}
