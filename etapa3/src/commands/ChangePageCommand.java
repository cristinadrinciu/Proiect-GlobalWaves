package commands;

import audio.files.Library;
import audio.files.Podcast;
import audio.files.Song;
import main.InputCommands;
import user.types.Artist;
import user.types.Host;
import visit.pattern.Visitable;
import visit.pattern.Visitor;
import user.types.User;

public class ChangePageCommand implements Visitable {
    private String nextPage;
    private String message;

    public ChangePageCommand() {
    }

    /**
     * @return the nextPage
     */
    public String getNextPage() {
        return nextPage;
    }

    /**
     * @param nextPage the nextPage to set
     */
    public void setNextPage(final String nextPage) {
        this.nextPage = nextPage;
    }

    /**
     * @return the message
     */
    public String getMessage() {
        return this.message;
    }

    /**
     * Changes the current page of the user
     * @param user the user that wants to change the page
     */
    public void changePage(final User user) {
        switch (this.nextPage) {
            case "Home":
                user.setCurrentPage(user.getHomePage());
                this.message = user.getUsername() + " accessed " + this.nextPage + " successfully.";
                break;
            case "LikedContent":
                user.setCurrentPage(user.getLikedContentPage());
                this.message = user.getUsername() + " accessed " + this.nextPage + " successfully.";
                break;
            case "Artist":
                // change to artist's page of the current Song
                if(user.getPlayer().playingNow == null) {
                    this.message = "You are not listening to any song.";
                    return;
                }
                Artist artist = user.getPlayer().findArtist((Song) user.getPlayer().playingNow);
                user.setCurrentPage(artist.getArtistPage());
                this.message = user.getUsername() + " accessed " + this.nextPage + " successfully.";
                break;
            case "Host":
                // change to host's page of the current Podcast
                if(user.getPlayer().playingNow == null) {
                    this.message = "You are not listening to any song.";
                    return;
                }
                Host host = user.getPlayer().findHost((Podcast) user.getPlayer().loadedItem);
                user.setCurrentPage(host.getHostPage());
                this.message = user.getUsername() + " accessed " + this.nextPage + " successfully.";
                break;
        }
        // put in the navigation history the current page
        user.getNavigationHistory().add(user.getCurrentPage());

        // update the index of the navigation history
        user.setIndexNavigationHistory(user.getNavigationHistory().size() - 1);
    }

    /**
     * Accepts the visitor
     * @param command the command that will be accepted
     * @param visitor the visitor that will visit the command
     * @param library the library that will be used
     */
    @Override
    public void accept(final InputCommands command, final Visitor visitor, final Library library) {
        visitor.visit(command, this, library);
    }
}
