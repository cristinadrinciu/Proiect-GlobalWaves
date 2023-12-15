package commands;

import audio.files.Library;
import audio.files.Playlist;
import main.InputCommands;
import visit.pattern.Visitable;
import visit.pattern.Visitor;
import publicFiles.PublicPlaylists;
import user.types.User;

import java.util.ArrayList;

public class SwitchVisibilityCommand implements Visitable {
    private int playlistId;
    private Playlist playlist;
    public SwitchVisibilityCommand() {
    }

    /**
     * @return the playlistId
     */
    public int getPlaylistId() {
        return playlistId;
    }

    /**
     * @param playlistId the playlistId to set
     */
    public void setPlaylistId(final int playlistId) {
        this.playlistId = playlistId;
    }

    /**
     * @param user the user that wants to switch the visibility of the playlist
     * @param library the library that contains the users
     */
    public void switchVisibility(final User user, final Library library) {
        if (user.getPlaylists().size() < playlistId) {
            return;
        }
        playlist = user.getPlaylists().get(playlistId - 1);
        if (playlist.getPublic()) {
            playlist.setPublic(false);
            ArrayList<User> users = library.getUsers();

            // remove the playlist from the public player of all users, except the owner
            for (User userParse : users) {
                if (userParse != user) {
                    userParse.getPlayer().playlists.remove(playlist);
                }
            }

            // remove the playlist from the public player
            PublicPlaylists.getPlaylists().remove(playlist);
        } else {
            playlist.setPublic(true);
            // add the playlist in the public player of all users, except the owner
            ArrayList<User> users = library.getUsers();

            for (User userParse : users) {
                if (userParse != user) {
                    userParse.getPlayer().playlists.add(playlist);
                }
            }

            // add the playlist in the public player
            PublicPlaylists.getPlaylists().add(playlist);
        }
    }

    /**
     * @param user the user that wants to switch the visibility of the playlist
     * @return the message that will be displayed
     */
    public String message(final User user) {
        if (user.getPlaylists().size() < playlistId) {
            return "The specified playlist ID is too high.";
        }
        if (playlist.getPublic()) {
            return "Visibility status updated successfully to public.";
        }
        return "Visibility status updated successfully to private.";
    }

    @Override
    public void accept(final InputCommands command, final Visitor visitor, final Library library) {
        visitor.visit(command, this, library);
    }
}
