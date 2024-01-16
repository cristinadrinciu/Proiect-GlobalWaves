package commands;

import stream.JsonOutputStream;
import audiofiles.Library;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import designpatterns.commandPattern.Command;
import main.InputCommands;
import users.User;

import java.util.ArrayList;

public class SeeMyMerchCommand implements Command {

    public SeeMyMerchCommand() {
    }

    /**
     * Get the bought merch of a user
     * @param user the user
     * @return the bought merch
     */
    public ArrayList<String> getBoughtMerch(final User user) {
        return user.getBoughtMerch();
    }

    /**
     * Execute the command
     * @param command the input command
     * @param library the main library
     */
    @Override
    public void execute(final InputCommands command, final Library library) {
        User user = command.getUser();
        ArrayList<String> merch = getBoughtMerch(user);

        ObjectMapper objectMapper = new ObjectMapper();

        ObjectNode commandJson = objectMapper.createObjectNode()
                .put("command", "seeMerch")
                .put("user", command.getUsername())
                .put("timestamp", command.getTimestamp());

        ArrayNode merchArray = JsonNodeFactory.instance.arrayNode();

        for (String merchItem : merch) {
            merchArray.add(merchItem);
        }

        commandJson.set("result", merchArray);

        JsonOutputStream.addJsonNode(commandJson);
    }
}
