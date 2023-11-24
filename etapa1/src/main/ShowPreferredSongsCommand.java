package main;

import AudioFiles.Song;
import AudioFiles.User;

import java.util.ArrayList;

public class ShowPreferredSongsCommand {
    private ArrayList<Song> preferredSongs = new ArrayList<>();

    public ShowPreferredSongsCommand() {
    }

    /**
     * @return the preferredSongs
     */
    public ArrayList<Song> getPreferredSongs() {
        return preferredSongs;
    }

    /**
     * @param user the user to set
     */
    public void setPreferredSongs(final User user) {
        this.preferredSongs = user.getLikedSongs();
    }
}
