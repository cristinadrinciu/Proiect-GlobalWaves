package commands;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import designPatterns.commandPattern.Command;
import main.InputCommands;
import users.Artist;
import pageContent.Event;
import audioFiles.Library;
import users.User;

import java.util.ArrayList;

public class AddEventCommand implements Command {
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
        ((Artist) user).notifyObservers("New Event",
                "New Event from " + user.getUsername() + ".");

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
     * Executes the command
     * @param command the input command
     * @param library the main library
     */
    @Override
    public void execute(final InputCommands command, final Library library) {
        User user = command.getUser();
        addEvent(user);

        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode commandJson = objectMapper.createObjectNode()
                .put("command", "addEvent")
                .put("user", command.getUsername())
                .put("timestamp", command.getTimestamp())
                .put("message", message);

        command.getCommandList().add(commandJson);
    }
}
