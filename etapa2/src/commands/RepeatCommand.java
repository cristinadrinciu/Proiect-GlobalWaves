package commands;


import audio.files.Library;
import audio.files.Podcast;
import audio.files.Song;
import main.InputCommands;
import visit.pattern.Visitable;
import visit.pattern.Visitor;
import user.types.User;

public class RepeatCommand implements Visitable {
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

    @Override
    public void accept(final InputCommands command, final Visitor visitor, final Library library) {
        visitor.visit(command, this, library);
    }
}
