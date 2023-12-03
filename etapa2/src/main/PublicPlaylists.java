package main;

import AudioFiles.Playlist;

import java.util.ArrayList;

public class PublicPlaylists {
    private static ArrayList<Playlist> playlists = new ArrayList<>();

    /**
     * Adds a playlist to the list of playlists
     * @return the playlist that was added
     */
    public static ArrayList<Playlist> getPlaylists() {
        return playlists;
    }
}
