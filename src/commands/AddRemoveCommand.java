package commands;

import audio.files.Album;
import audio.files.Library;
import audio.files.Playlist;
import audio.files.Song;
import main.InputCommands;
import visit.pattern.Visitable;
import visit.pattern.Visitor;
import user.types.User;

public class AddRemoveCommand implements Visitable {
    private int playlistId;

    /**
     *
     * @return the id of the playlist
     */
    public int getPlaylistId() {
        return playlistId;
    }

    /**
     *
     * @param playlistId the id of the playlist
     */
    public void setPlaylistId(final int playlistId) {
        this.playlistId = playlistId;
    }


    public AddRemoveCommand() {
    }

    /**
     * @param user the user that wants to add or remove a song from a playlist
     * @return the message that will be displayed
     */
    public String addRemoveMessage(final User user) {
        String message;
        if (user.getPlayer().loadedItem == null) {
            message = "Please load a source before adding to or removing from the playlist.";
            return message;
        }
        if (!(user.getPlayer().loadedItem instanceof Song)
                && !(user.getPlayer().loadedItem instanceof Album)
                && !(user.getPlayer().loadedItem instanceof Playlist)) {
            message = "The loaded source is not a song.";
            return message;
        }
        if (user.getPlaylists() == null) {
            message = "The specified playlist does not exist.";
            return message;
        }
        if (playlistId > user.getPlaylists().size() || playlistId < 0) {
            message = "The specified playlist does not exist.";
            return message;
        }

        // search if the song is in the playlist
        boolean isInPlaylist = false;
        for (Song song : user.getPlaylists().get(playlistId - 1).getSongs()) {
            if (song == user.getPlayer().playingNow) {
                isInPlaylist = true;
                break;
            }
        }

        if (isInPlaylist) {
            // remove the song from the playlist
            user.getPlaylists().get(playlistId - 1).getSongs().
                    remove((Song) user.getPlayer().playingNow);
            message = "Successfully removed from playlist.";
        } else {
            // add in the playlist
            user.getPlaylists().get(playlistId - 1).getSongs().
                    add((Song) user.getPlayer().playingNow);
            message = "Successfully added to playlist.";
        }

        return message;
    }

    /**
     * This method is used to add or remove a song from a playlist
     * @param command the command to be executed
     * @param visitor the visitor
     * @param library the library
     */
    @Override
    public void accept(final InputCommands command,
                       final Visitor visitor, final Library library) {
        visitor.visit(command, this, library);
    }
}
