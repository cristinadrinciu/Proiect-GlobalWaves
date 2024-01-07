package commands;

import audio.files.Library;
import main.InputCommands;
import notification.Notification;
import user.types.User;
import visit.pattern.Visitable;
import visit.pattern.Visitor;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class GetNotifications implements Visitable {
    private ArrayList<Notification> notifications = new ArrayList<>();
    public GetNotifications() {
    }

    /**
     * @return the notifications
     */
    public ArrayList<Notification> getNotifications() {
        return notifications;
    }

    public void showNotifications(final User user) {
        notifications.addAll(user.getNotifications());
        // clear the notifications of the user
        user.getNotifications().clear();
    }

    @Override
    public void accept(final InputCommands command, final Visitor visitor, final Library library) {
        visitor.visit(command, this, library);
    }
}
