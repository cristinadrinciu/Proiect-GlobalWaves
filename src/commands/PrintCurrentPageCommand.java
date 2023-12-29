package commands;

import audio.files.Library;
import main.InputCommands;
import visit.pattern.Visitable;
import visit.pattern.Visitor;
import pages.Page;

public class PrintCurrentPageCommand implements Visitable {
    private String username;
    private Page page;

    public PrintCurrentPageCommand() {
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
     * @param library for finding the user
     */
    public void setCurrentPage(final Library library) {
        // find the user
        for (int i = 0; i < library.getUsers().size(); i++) {
            if (library.getUsers().get(i).getUsername().equals(this.username)) {
                this.page = library.getUsers().get(i).getCurrentPage();
                return;
            }
        }
    }

    /**
     * @return the page
     */
    public Page getCurrentPage() {
        return this.page;
    }

    /**
     * Accept method for the visitor
     * @param command the command to set
     * @param visitor the visitor to set
     * @param library the library to set
     */
    @Override
    public void accept(final InputCommands command, final Visitor visitor, final Library library) {
        visitor.visit(command, this, library);
    }
}
