package commands;

import audio.files.Library;
import main.InputCommands;
import user.types.User;
import visit.pattern.Visitable;
import visit.pattern.Visitor;

public class PreviousPageCommand implements Visitable {
    private String message;

    public PreviousPageCommand() {
    }

    /**
     * @return the message
     */
    public String getMessage() {
        return message;
    }

    /**
     * Changes the current page of the user
     * @param user the user that wants to change the page
     */
    public void goToPreviousPage(User user) {
        if(user.getIndexNavigationHistory() == 0) {
            this.message = "There are no pages left to go back.";
            return;
        }

        // go back to the previous page
        user.setCurrentPage(user.getNavigationHistory().get(user.getIndexNavigationHistory() - 1));
        user.setIndexNavigationHistory(user.getIndexNavigationHistory() - 1);
        message = "The user " + user.getUsername() + " has navigated successfully to the previous page.";
    }

    @Override
    public void accept(final InputCommands command, final Visitor visitor, final Library library) {
        visitor.visit(command, this, library);
    }
}
