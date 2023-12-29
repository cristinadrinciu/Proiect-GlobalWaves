package commands;

import main.InputCommands;
import visit.pattern.Visitable;
import visit.pattern.Visitor;
import user.types.Host;
import audio.files.Library;
import audio.files.Podcast;

import java.util.ArrayList;

public class ShowPodcasts implements Visitable {
    private String username;
    private ArrayList<Podcast> podcasts;

    public ShowPodcasts() {
    }

    /**
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username the user to set
     */
    public void setUsername(final String username) {
        this.username = username;
    }

    /**
     * @return the podcasts
     */
    public ArrayList<Podcast> getPodcasts() {
        return podcasts;
    }

    /**
     * @param library the library to set
     */
    public void setPodcasts(final Library library) {
        // find the user
        for (int i = 0; i < library.getUsers().size(); i++) {
            if (library.getUsers().get(i).getUsername().equals(this.username)) {
                this.podcasts = ((Host) (library.getUsers().get(i))).getPodcasts();
                return;
            }
        }
    }

    /**
     * Accept method for the visitor
     * @param command the command to be executed
     * @param visitor the visitor
     * @param library the library
     */
    @Override
    public void accept(final InputCommands command, final Visitor visitor, final Library library) {
        visitor.visit(command, this, library);
    }
}
