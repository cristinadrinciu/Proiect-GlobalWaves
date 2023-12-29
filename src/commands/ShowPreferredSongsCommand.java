package commands;

import audio.files.Library;
import audio.files.Song;
import main.InputCommands;
import visit.pattern.Visitable;
import visit.pattern.Visitor;
import user.types.User;

import java.util.ArrayList;

public class ShowPreferredSongsCommand implements Visitable {
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

    /**
     * Accept method for the visitor
     * @param command the command to accept
     * @param visitor the visitor that will visit the command
     * @param library the library that contains the users
     */
    @Override
    public void accept(final InputCommands command, final Visitor visitor, final Library library) {
        visitor.visit(command, this, library);
    }
}
