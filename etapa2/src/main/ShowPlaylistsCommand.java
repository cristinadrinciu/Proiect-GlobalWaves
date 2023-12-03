package main;

import AudioFiles.Playlist;
import AudioFiles.User;

import java.util.ArrayList;

public class ShowPlaylistsCommand {
    private ArrayList<Playlist> playlists;

    /**
     * @return the playlists
     */
    public ArrayList<Playlist> getPlaylists() {
        return playlists;
    }

    /**
     * @param user the user to set
     */
    public void setPlaylists(final User user) {
        this.playlists = user.getPlaylists();
    }
}
