package platformdata;

import audiofiles.Playlist;

import java.util.ArrayList;

public final class PublicPlaylists {
    private static ArrayList<Playlist> playlists = new ArrayList<>();

    private PublicPlaylists() {
    }

    /**
     * Adds a playlist to the list of playlists
     * @return the playlist that was added
     */
    public static ArrayList<Playlist> getPlaylists() {
        return playlists;
    }
}
