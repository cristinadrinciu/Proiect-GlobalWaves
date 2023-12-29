package commands;

import audio.files.AudioFile;
import audio.files.Library;
import main.InputCommands;
import visit.pattern.Visitable;
import visit.pattern.Visitor;
import user.types.User;

public class SelectCommand implements Visitable {
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
     * Accepts the visitor
     * @param command the command to be executed
     * @param visitor the visitor
     * @param library the library of the user
     */
    @Override
    public void accept(final InputCommands command, final Visitor visitor, final Library library) {
        visitor.visit(command, this, library);
    }
}
