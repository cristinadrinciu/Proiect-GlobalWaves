package main;

import AudioFiles.AudioFile;
import AudioFiles.User;

public class SelectCommand {
    private int itemNumber;
    private static AudioFile selectedItem;

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
    public void provideSelectedItem(User user) {
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
}
