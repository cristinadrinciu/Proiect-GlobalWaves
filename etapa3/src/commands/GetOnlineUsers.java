package commands;

import audioFiles.Library;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import designPatterns.commandPattern.Command;
import main.InputCommands;
import platformData.OnlineUsers;
import users.User;

import java.util.ArrayList;

public class GetOnlineUsers implements Command {
    private ArrayList<String> onlineUsersNames = new ArrayList<>();

    public GetOnlineUsers() {
    }

    /**
     * @return the onlineUsersNames
     */
    public ArrayList<String> getOnlineUsersNames() {
        return onlineUsersNames;
    }

    /**
     * This method sets the onlineUsersNames list with the usernames of the online users.
     */
    public void setOnlineUsersNames() {
        onlineUsersNames = new ArrayList<>();
        for (User user : OnlineUsers.getOnlineUsers()) {
            onlineUsersNames.add(user.getUsername());
        }

        // sort the list by name of the users
        onlineUsersNames.sort(String::compareToIgnoreCase);
    }

    /**
     * Execute the command
     * @param command the input command
     * @param library the main library
     */
    @Override
    public void execute(final InputCommands command, final Library library) {
        setOnlineUsersNames();

        ArrayList<String> onlineUsers = getOnlineUsersNames();

        // Create an array node for the results
        ArrayNode resultsArray = JsonNodeFactory.instance.arrayNode();

        // Add the names of the online users to the results array
        for (String username : onlineUsers) {
            resultsArray.add(username);
        }

        // Create the command JSON structure
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode commandJson = objectMapper.createObjectNode()
                .put("command", "getOnlineUsers")
                .put("timestamp", command.getTimestamp())
                .set("result", resultsArray);

        // Add the commandJson to the commandList
        command.getCommandList().add(commandJson);
    }
}
