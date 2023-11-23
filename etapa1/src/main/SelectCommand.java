package main;

import fileio.input.AudioFile;

public class SelectCommand {
    private int itemNumber;
    public static AudioFile selectedItem;

    public int getItemNumber() {
        return itemNumber;
    }

    public void setItemNumber(int itemNumber) {
        this.itemNumber = itemNumber;
    }

    // need the last search
    public AudioFile getSelectedItem () {
        if(SearchCommand.searchResults != null && itemNumber > 0 && itemNumber <= SearchCommand.searchResults.size()) {
            selectedItem = SearchCommand.searchResults.get(itemNumber - 1);
        } else
            selectedItem = null;
        return selectedItem;
    }
}
