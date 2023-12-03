package main;

import AudioFiles.AudioFile;
import AudioFiles.Playlist;
import AudioFiles.User;

public class FollowCommand {
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
     * @param audioFile the playlist that the user wants to follow or unfollow
     */
    public void followPlaylist(final User user, final AudioFile audioFile) {
        if (audioFile == null) {
            message = "Please select a source before following or unfollowing.";
            return;
        }
        if (!(audioFile instanceof Playlist)) {
            message = "The selected source is not a playlist.";
            return;
        }
        Playlist playlist = (Playlist) audioFile;
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
    }
}
