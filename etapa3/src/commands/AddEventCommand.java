package commands;

import main.InputCommands;
import notification.Notification;
import visit.pattern.Visitable;
import visit.pattern.Visitor;
import user.types.Artist;
import page.content.Event;
import audio.files.Library;
import user.types.User;

import java.util.ArrayList;

public class AddEventCommand implements Visitable {
    private String name;
    private String description;
    private String date;
    private String message;

    public AddEventCommand() {
    }

    /**
     * @return the message
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(final String name) {
        this.name = name;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the name to set
     */
    public void setDescription(final String description) {
        this.description = description;
    }

    /**
     * @return the date
     */
    public String getDate() {
        return date;
    }

    /**
     * @param date the name to set
     */
    public void setDate(final String date) {
        this.date = date;
    }

    /**
     * @return the message
     */
    public String getMessage() {
        return this.message;
    }

    /**
     * @param user the user to add the event to
     */
    public void addEvent(final User user) {
        // check if user is an artist
        if (!user.getType().equals("artist")) {
            message = user.getUsername() + " is not an artist.";
            return;
        }

        // check if there is already an event with the same name
        ArrayList<Event> events = ((Artist) user).getEvents();
        for (Event event : events) {
            if (event.getName().equals(name)) {
                message = user.getUsername()
                        + " has another event with the same name.";
                return;
            }
        }

        // check the date;
        if (!VerifyDate.isValid(date)) {
            message = "Event for "  + user.getUsername()
                        + " does not have a valid date.";
            return;
        }

        // add the event
        Event event = new Event();
        event.setName(name);
        event.setDescription(description);
        event.setDate(date);
        events.add(event);
        message = user.getUsername()
                    + " has added new event successfully.";

        // send notification to subscribers
        for (User subscriber : ((Artist) user).getSubscribers()) {
            Notification newNotification = new Notification();
            newNotification.setName("New Event");
            newNotification.setDescription("New Event from " + user.getUsername() + ".");
            subscriber.getNotifications().add(newNotification);
        }

        // update the artist page
        ((Artist) user).setArtistPage();
    }

    private static class VerifyDate {
        public static boolean isValid(final String date) {
            // date = "dd-mm-yyyy", verify if the date is valid
            // check if the date is in the correct format
            if (!date.matches("\\d{2}-\\d{2}-\\d{4}")) {
                return false;
            }

            final int december = 12;
            final int january = 1;
            final int beginYear = 1900;
            final int endYear = 2023;

            // check if the date is valid
            String[] dateParts = date.split("-");
            int day = Integer.parseInt(dateParts[0]);
            int month = Integer.parseInt(dateParts[1]);
            int year = Integer.parseInt(dateParts[2]);
            if (month > december || month < january
                    || year < beginYear || year > endYear) {
                return false;
            }

            final int february = 2;
            final int april = 4;
            final int june = 6;
            final int september = 9;
            final int november = 11;
            final int thirty = 30;
            final int thirtyOne = 31;
            final int twentyEight = 28;
            // check if the day is valid
            if (month == february) {
                if (day > twentyEight) {
                    return false;
                }
            } else if (month == april || month == june
                    || month == september || month == november) {
                if (day > thirty) {
                    return false;
                }
            } else {
                if (day > thirtyOne) {
                    return false;
                }
            }

            return true;
        }
    }


    /**
     * The accept method for the visitor
     * @param command the command to execute
     * @param visitor the visitor
     * @param library the library of the application
     */
    @Override
    public void accept(final InputCommands command,
                        final Visitor visitor, final Library library) {
        visitor.visit(command, this, library);
    }
}
