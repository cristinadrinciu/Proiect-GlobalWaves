package commands;

import audio.files.Library;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import main.InputCommands;
import user.types.User;
import visit.pattern.Visitable;
import visit.pattern.Visitor;

public class PreviousPageCommand implements Command {
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

    /**
     * Executes the command
     * @param command the input command
     * @param library the library that will be modified
     */
    @Override
    public void execute(InputCommands command, Library library) {
        User user = command.getUser();
        goToPreviousPage(user);

        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode commandJson = objectMapper.createObjectNode()
                .put("command", "previousPage")
                .put("user", command.getUsername())
                .put("timestamp", command.getTimestamp())
                .put("message", message);
        command.getCommandList().add(commandJson);
    }
}
