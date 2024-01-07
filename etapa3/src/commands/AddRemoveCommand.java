package commands;

import audio.files.Album;
import audio.files.Library;
import audio.files.Playlist;
import audio.files.Song;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import main.InputCommands;
import visit.pattern.Visitable;
import visit.pattern.Visitor;
import user.types.User;

public class AddRemoveCommand implements Command {
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
     * Execute the add or remove command
     * @param command the input command
     * @param library the library
     */
    @Override
    public void execute(final InputCommands command, final Library library) {
        User user = command.getUser();
        String message = addRemoveMessage(user);

        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode commandJson = objectMapper.createObjectNode()
                .put("command", "addRemoveInPlaylist")
                .put("user", command.getUsername())
                .put("timestamp", command.getTimestamp())
                .put("message", message);

        command.getCommandList().add(commandJson);
    }
}
