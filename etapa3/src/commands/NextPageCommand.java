package commands;

import audio.files.Library;
import main.InputCommands;
import user.types.User;
import visit.pattern.Visitable;
import visit.pattern.Visitor;

public class NextPageCommand implements Visitable {
    private String message;

    public NextPageCommand() {
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
    public void goToNextPage(User user) {
        if(user.getIndexNavigationHistory() == user.getNavigationHistory().size() - 1) {
            this.message = "There are no pages left to go forward.";
            return;
        }

        // go forward to the next page
        user.setCurrentPage(user.getNavigationHistory().get(user.getIndexNavigationHistory() + 1));
        user.setIndexNavigationHistory(user.getIndexNavigationHistory() + 1);
        message = "The user " + user.getUsername() + " has navigated successfully to the next page.";
    }

    @Override
    public void accept(final InputCommands command, final Visitor visitor, final Library library) {
        visitor.visit(command, this, library);
    }
}
