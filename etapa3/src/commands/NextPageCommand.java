package commands;

import audio.files.Library;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import main.InputCommands;
import user.types.User;
import visit.pattern.Visitable;
import visit.pattern.Visitor;

public class NextPageCommand implements Command {
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

    /**
     * Executes the command
     * @param command the input command
     * @param library the main library
     */
    @Override
    public void execute(InputCommands command, Library library) {
        User user = command.getUser();
        goToNextPage(user);

        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode commandJson = objectMapper.createObjectNode()
                .put("command", "nextPage")
                .put("user", command.getUsername())
                .put("timestamp", command.getTimestamp())
                .put("message", message);
        command.getCommandList().add(commandJson);
    }
}
