package commands;

import audio.files.Library;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import main.InputCommands;
import user.types.User;
import visit.pattern.Visitable;
import visit.pattern.Visitor;

import java.util.ArrayList;

public class SeeMyMerchCommand implements Command {

    public SeeMyMerchCommand() {
    }

    public ArrayList<String> getBoughtMerch(User user) {
        return user.getBoughtMerch();
    }

    /**
     * Execute the command
     * @param command the input command
     * @param library the main library
     */
    @Override
    public void execute(InputCommands command, Library library) {
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

        command.getCommandList().add(commandJson);
    }
}
