package commands;

import audio.files.Library;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import main.InputCommands;
import visit.pattern.Visitable;
import visit.pattern.Visitor;
import user.types.User;

import java.util.ArrayList;

public class GetAllUsers implements Command {
    private ArrayList<String> allUsersNames = new ArrayList<>();

    public GetAllUsers() {
    }

    /**
     * @return the allUsersNames
     */
    public ArrayList<String> getAllUsersNames() {
        return allUsersNames;
    }

    /**
     * set the list of all users names
     * @param library the library
     */
    public void setAllUsersNames(final Library library) {
        allUsersNames = new ArrayList<>();
        // order: normal users, artists, hosts
        for (User user : library.getUsers()) {
            if (user.getType().equals("user")) {
                allUsersNames.add(user.getUsername());
            }
        }
        for (User user : library.getUsers()) {
            if (user.getType().equals("artist")) {
                allUsersNames.add(user.getUsername());
            }
        }
        for (User user : library.getUsers()) {
            if (user.getType().equals("host")) {
                allUsersNames.add(user.getUsername());
            }
        }
    }

    /**
     * Execute the command
     * @param command the input command
     * @param library the library
     */
    @Override
    public void execute(final InputCommands command, final Library library) {
        setAllUsersNames(library);

        // Create an array node for the results
        ArrayNode resultsArray = JsonNodeFactory.instance.arrayNode();

        ArrayList<String> allUsers = getAllUsersNames();

        // Add the names of the online users to the results array
        for (String username : allUsers) {
            resultsArray.add(username);
        }

        // Create the command JSON structure
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode commandJson = objectMapper.createObjectNode()
                .put("command", "getAllUsers")
                .put("timestamp", command.getTimestamp())
                .set("result", resultsArray);

        // Add the commandJson to the commandList
        command.getCommandList().add(commandJson);
    }
}
