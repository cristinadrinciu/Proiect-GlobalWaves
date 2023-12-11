package main;


import AudioFiles.Podcast;
import AudioFiles.Song;
import AudioFiles.User;

public class RepeatCommand {
    /**
     * @param user the user that wants to change the repeat mode
     */
    public void setRepeatMode(final User user) {
        if (user.getPlayer().repeatState == 0) {
            user.getPlayer().repeatState = 1;
            user.getPlayer().repeatedOnce = 0;
        } else if (user.getPlayer().repeatState == 1) {
            user.getPlayer().repeatState = 2;
        } else if (user.getPlayer().repeatState == 2) {
            user.getPlayer().repeatState = 0;
        }
    }

    /**
     * @param user the user that wants to change the repeat mode
     * @return the message that will be displayed
     */
    public String message(final User user) {
        String message;
        if (user.getPlayer().loadedItem == null) {
            message = "Please load a source before setting the repeat status.";
            return message;
        }

        switch (user.getPlayer().repeatState) {
            case 0:
                message = "Repeat mode changed to no repeat.";
                break;
            case 1:
                if (user.getPlayer().loadedItem instanceof Song
                        || user.getPlayer().loadedItem instanceof Podcast) {
                    message = "Repeat mode changed to repeat once.";
                } else {
                    message = "Repeat mode changed to repeat all.";
                }
                break;
            case 2:
                if (user.getPlayer().loadedItem instanceof Song
                        || user.getPlayer().loadedItem instanceof Podcast) {
                    message = "Repeat mode changed to repeat infinite.";
                } else {
                    message = "Repeat mode changed to repeat current song.";
                }
                break;
            default:
                message = "Invalid repeat state.";
        }

        return message;
    }
}
