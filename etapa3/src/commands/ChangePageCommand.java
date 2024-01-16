package commands;

import audioFiles.Library;
import audioFiles.Podcast;
import audioFiles.Song;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import designPatterns.commandPattern.Command;
import main.InputCommands;
import users.Artist;
import users.Host;

import users.User;

public class ChangePageCommand implements Command {
    private String nextPage;
    private String message;

    public ChangePageCommand() {
    }

    /**
     * @return the nextPage
     */
    public String getNextPage() {
        return nextPage;
    }

    /**
     * @param nextPage the nextPage to set
     */
    public void setNextPage(final String nextPage) {
        this.nextPage = nextPage;
    }

    /**
     * @return the message
     */
    public String getMessage() {
        return this.message;
    }

    /**
     * Changes the current page of the user
     * @param user the user that wants to change the page
     */
    public void changePage(final User user) {
        switch (this.nextPage) {
            case "Home":
                user.setCurrentPage(user.getHomePage());
                this.message = user.getUsername() + " accessed " + this.nextPage + " successfully.";
                break;
            case "LikedContent":
                user.setCurrentPage(user.getLikedContentPage());
                this.message = user.getUsername() + " accessed " + this.nextPage + " successfully.";
                break;
            case "Artist":
                // change to artist's page of the current Song
                if (user.getPlayer().playingNow == null) {
                    this.message = "You are not listening to any song.";
                    return;
                }
                Artist artist = user.getPlayer().findArtist((Song) user.getPlayer().playingNow);
                user.setCurrentPage(artist.getArtistPage());
                this.message = user.getUsername() + " accessed " + this.nextPage + " successfully.";
                break;
            case "Host":
                // change to host's page of the current Podcast
                if (user.getPlayer().playingNow == null) {
                    this.message = "You are not listening to any song.";
                    return;
                }
                Host host = user.getPlayer().findHost((Podcast) user.getPlayer().loadedItem);
                user.setCurrentPage(host.getHostPage());
                this.message = user.getUsername() + " accessed " + this.nextPage + " successfully.";
                break;
            default:
                this.message = "Page not found.";
                break;
        }
        // put in the navigation history the current page
        user.getNavigationHistory().add(user.getCurrentPage());

        // update the index of the navigation history
        user.setIndexNavigationHistory(user.getNavigationHistory().size() - 1);
    }

    /**
     * Executes the command
     * @param command the input command
     * @param library the library of the application
     */
    @Override
    public void execute(final InputCommands command, final Library library) {
        User user = command.getUser();
        changePage(user);

        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode commandJson = objectMapper.createObjectNode()
                .put("command", "changePage")
                .put("user", command.getUsername())
                .put("timestamp", command.getTimestamp())
                .put("message", message);

        command.getCommandList().add(commandJson);
    }
}
