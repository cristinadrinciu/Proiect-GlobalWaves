package commands;

import audio.files.Library;
import audio.files.Playlist;
import main.InputCommands;
import visit.pattern.Visitable;
import visit.pattern.Visitor;
import user.types.User;

import java.util.ArrayList;

public class ShowPlaylistsCommand implements Visitable {
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

    @Override
    public void accept(final InputCommands command, final Visitor visitor, final Library library) {
        visitor.visit(command, this, library);
    }
}
