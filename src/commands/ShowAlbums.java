package commands;

import audio.files.Album;
import main.InputCommands;
import visit.pattern.Visitable;
import visit.pattern.Visitor;
import user.types.Artist;
import audio.files.Library;

import java.util.ArrayList;

public class ShowAlbums implements Visitable {
    private String username;
    private ArrayList<Album> albums;

    public ShowAlbums() {
    }

    /**
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username the username to set
     */
    public void setUsername(final String username) {
        this.username = username;
    }

    /**
     * getter for albums
     * @return the albums
     */
    public ArrayList<Album> getAlbums() {
        return albums;
    }

    /**
     * setter for albums
     * @param library the library
     */
    public void setAlbums(final Library library) {
        // find the user
        for (int i = 0; i < library.getUsers().size(); i++) {
            if (library.getUsers().get(i).getUsername().equals(this.username)) {
                this.albums = ((Artist) (library.getUsers().get(i))).getAlbums();
                return;
            }
        }
    }

    /**
     * Accept method for visitor
     * @param command the command
     * @param visitor the visitor
     * @param library the library
     */
    @Override
    public void accept(final InputCommands command, final Visitor visitor, final Library library) {
        visitor.visit(command, this, library);
    }
}
