package commands;

import audioFiles.Library;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import designPatterns.commandPattern.Command;
import main.InputCommands;
import users.User;

public class BuyPremiumCommand implements Command {
    private String message;

    public BuyPremiumCommand() {
    }

    /**
     * Getter for the message
     * @return the message
     */
    public String getMessage() {
        return message;
    }

    /**
     * Buys the premium subscription for the user
     * @param user the user that buys the subscription
     */
    public void buyPremium(final User user) {
        if (user.isPremium()) {
            message = user.getUsername() + " is already a premium user.";
        } else {
            user.setPremium(true);
            message = user.getUsername() + " bought the subscription successfully.";
        }
    }

    /**
     * Executes the command
     * @param command the input command
     * @param library the main library
     */
    @Override
    public void execute(final InputCommands command, final Library library) {
        User user = command.getUser();

        // update the player of the user
        if (user.getPlayer().loadedItem != null) {
            if (user.getPlayer().repeatState == 0) {
                user.getPlayer().setRemainingTime();
            }
            if (user.getPlayer().repeatState == 1) {
                user.getPlayer().setRemainingTimeRepeat1();
            }
            if (user.getPlayer().repeatState == 2) {
                user.getPlayer().setRemainingTimeRepeat2();
            }
        }

        buyPremium(user);

        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode commandJson = objectMapper.createObjectNode()
                .put("command", "buyPremium")
                .put("user", command.getUsername())
                .put("timestamp", command.getTimestamp())
                .put("message", message);
        command.getCommandList().add(commandJson);
    }
}
