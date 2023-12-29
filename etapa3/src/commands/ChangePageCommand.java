package commands;

import audio.files.Library;
import main.InputCommands;
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
            default:
                this.message = user.getUsername() + " is trying to access a non-existent page.\n";
                break;
        }
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
