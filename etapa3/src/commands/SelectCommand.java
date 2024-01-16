package commands;

import audioFiles.AudioFile;
import audioFiles.Library;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import designPatterns.commandPattern.Command;
import main.InputCommands;
import users.Artist;
import users.Host;

import users.User;

public class SelectCommand implements Command {
    private int itemNumber;
    private static AudioFile selectedItem;
    private static User selectedUser;

    /**
     *
     * @return the number of the item to be selected
     */
    public int getItemNumber() {
        return itemNumber;
    }

    /**
     *
     * @param itemNumber the number of the item to be selected
     */
    public void setItemNumber(final int itemNumber) {
        this.itemNumber = itemNumber;
    }

    // need the last search
    /**
     * Selects the item with the given number from the last search
     */
    public void provideSelectedItem(final User user) {
        if (user.getLastSearch() != null && itemNumber > 0
                && itemNumber <= user.getLastSearch().size()) {
            selectedItem = user.getLastSearch().get(itemNumber - 1);
        } else {
            selectedItem = null;
        }
    }

    /**
     *
     * @return the selected item
     */
    public static AudioFile getSelectedItem() {
        return selectedItem;
    }

    /**
     *
     * @param selectedItem the selected item
     */
    public static void setSelectedItem(final AudioFile selectedItem) {
        SelectCommand.selectedItem = selectedItem;
    }

    /**
     * Selects the user with the given number from the last search
     */
    public void provideSelectedUser(final User user) {
        if (user.getLastSearchUsers() != null && itemNumber > 0
                && itemNumber <= user.getLastSearchUsers().size()) {
            selectedUser = user.getLastSearchUsers().get(itemNumber - 1);
        } else {
            selectedUser = null;
        }
    }

    /**
     *
     * @return the selected user
     */
    public static User getSelectedUser() {
        return selectedUser;
    }

    /**
     *
     * @param selectedUser the selected user
     */
    public static void setSelectedUser(final User selectedUser) {
        SelectCommand.selectedUser = selectedUser;
    }

    /**
     * Executes the select command
     * @param command the input command
     * @param library the library
     */
    @Override
    public void execute(final InputCommands command, final Library library) {
        User user = command.getUser();
        String message = null;

        if (user.getLastSearch() == null && user.getLastSearchUsers() == null) {
            message = "Please conduct a search before making a selection.";
        } else if (user.getLastSearch() != null && user.getLastSearchUsers() == null) {
            // searched an audio file
            provideSelectedItem(user);
            if (SelectCommand.getSelectedItem() != null) {
                user.setSelectedItem(SelectCommand.getSelectedItem());
                message = "Successfully selected "
                        + SelectCommand.getSelectedItem().getName() + ".";
                user.setLastSearch(null);
                SelectCommand.setSelectedItem(null);
            } else {
                if (itemNumber > user.getLastSearch().size()) {
                    message = "The selected ID is too high.";
                    SelectCommand.setSelectedItem(null);
                    user.setLastSearch(null);
                    user.setSelectedItem(null);
                } else {
                    message = "Please conduct a search before making a selection.";
                }
            }
        } else if (user.getLastSearchUsers() != null && user.getLastSearch() == null) {
            // searched a user
            provideSelectedUser(user);
            if (SelectCommand.getSelectedUser() != null) {
                message = "Successfully selected "
                        + SelectCommand.getSelectedUser().getUsername() + "'s page.";
                user.setLastSearchUsers(null);
                // set the current page
                if (SelectCommand.getSelectedUser().getType().equals("artist")) {
                    user.setCurrentPage(((Artist) SelectCommand.
                            getSelectedUser()).getArtistPage());
                } else if (SelectCommand.getSelectedUser().getType().equals("host")) {
                    user.setCurrentPage(((Host) SelectCommand.getSelectedUser()).
                            getHostPage());
                }
            } else {
                if (itemNumber > user.getLastSearchUsers().size()) {
                    message = "The selected ID is too high.";
                } else {
                    message = "Please conduct a search before making a selection.";
                }
            }
        }

        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode commandJson = objectMapper.createObjectNode()
                .put("command", "select")
                .put("user", command.getUsername())
                .put("timestamp", command.getTimestamp())
                .put("message", message);

        command.getCommandList().add(commandJson);
    }
}
