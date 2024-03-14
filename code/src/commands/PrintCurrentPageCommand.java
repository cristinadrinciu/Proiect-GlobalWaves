package commands;

import stream.JsonOutputStream;
import audiofiles.Library;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import designpatterns.commandPattern.Command;
import main.InputCommands;
import users.User;

import pages.Page;

public class PrintCurrentPageCommand implements Command {
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
     * Execute the command
     * @param command the input command
     * @param library the library to set
     */
    @Override
    public void execute(final InputCommands command, final Library library) {
        User user = command.getUser();

        // update before printing the pages
        user.setHomePage();
        user.setLikedContentPage();

        setUsername(user.getUsername());
        setCurrentPage(library);

        String message = getCurrentPage().printPage();

        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode commandJson = objectMapper.createObjectNode()
                .put("user", username)
                .put("command", "printCurrentPage")
                .put("timestamp", command.getTimestamp())
                .put("message", message);

        JsonOutputStream.getCommandOutputs().add(commandJson);
    }
}
