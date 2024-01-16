package commands;

import stream.JsonOutputStream;
import audiofiles.Library;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import designpatterns.commandPattern.Command;
import main.InputCommands;
import notification.Notification;
import users.User;

import java.util.ArrayList;

public class GetNotifications implements Command {
    private ArrayList<Notification> notifications = new ArrayList<>();
    public GetNotifications() {
    }

    /**
     * @return the notifications
     */
    public ArrayList<Notification> getNotifications() {
        return notifications;
    }

    /**
     * @param user the user that will receive the notifications
     */
    public void showNotifications(final User user) {
        notifications.addAll(user.getNotifications());
        // clear the notifications of the user
        user.getNotifications().clear();
    }

    /**
     * Execute the command
     * @param command the input command
     * @param library the main library
     */
    @Override
    public void execute(final InputCommands command, final Library library) {
        User user = command.getUser();
        showNotifications(user);

        ObjectMapper objectMapper = new ObjectMapper();

        ObjectNode commandJson = objectMapper.createObjectNode()
                .put("command", "getNotifications")
                .put("user", command.getUsername())
                .put("timestamp", command.getTimestamp());

        ArrayNode notificationsArray = JsonNodeFactory.instance.arrayNode();

        for (Notification notification : getNotifications()) {
            ObjectNode notificationNode = JsonNodeFactory.instance.objectNode()
                    .put("name", notification.getName())
                    .put("description", notification.getDescription());
            notificationsArray.add(notificationNode);
        }

        commandJson.set("notifications", notificationsArray);

        JsonOutputStream.addJsonNode(commandJson);
    }
}
