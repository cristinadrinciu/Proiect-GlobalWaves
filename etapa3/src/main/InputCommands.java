package main;

import audio.files.Library;
import audio.files.Song;
import com.fasterxml.jackson.databind.node.ObjectNode;
import commands.*;
import fileio.input.EpisodeInput;
import fileio.input.Filter;
import user.types.User;
import visit.pattern.Visitable;
import visit.pattern.Visitor;

import java.util.ArrayList;
import java.util.List;

public class InputCommands {
    private String command;
    private String username;
    private int timestamp;
    private List<ObjectNode> commandList = new ArrayList<>();

    // User for this command
    private User user;

    private Visitable commandToExecute = new Visitable() {
        @Override
        public void accept(final InputCommands inputCommands, final Visitor visitor,
                           final Library library) {
            // do nothing
        }
    };


    /**
     * This method gets the user from the library
     * @param library the library from which the user is taken
     */
    void getUser(final Library library) {
        for (User userContor : library.getUsers()) {
            if (userContor.getUsername().equals(username)) {
                user = userContor;
                break;
            }
        }
    }

    // constructor
    public InputCommands() {
    }

    // Player

    // Search Command
    private SearchCommand searchCommand = new SearchCommand();

    /**
     * This method sets the search command
     * @param type the type for the search
     */
    public void setType(final String type) {
        if (command.equals("search")) {
            this.searchCommand.setType(type);
        } else if (command.equals("addUser")) {
            this.addUserCommand.setType(type);
        }
    }

    /**
     * This method sets the criteria for the search
     * @param filters the filters for the search
     */
    public void setFilters(final Filter filters) {
        this.searchCommand.setFilters(filters);
    }


    // Select Command
    private SelectCommand selectCommand = new SelectCommand();

    /**
     * This method sets the item number for the select command
     * @param itemNumber the item number for the select command
     */
    public void setItemNumber(final int itemNumber) {
        this.selectCommand.setItemNumber(itemNumber);
    }

    // Load Command
    private LoadCommand loadCommand = new LoadCommand();

    // PlayPause Command
    private PlayPauseCommand playPauseCommand = new PlayPauseCommand();

    // Status Command
    private StatusCommand statusCommand = new StatusCommand();

    // CreatePlaylist Command

    private CreatePlaylistCommand createPlaylistCommand = new CreatePlaylistCommand();

    /**
     * This method sets the playlist name for the create playlist command
     * @param playlistName the playlist name for the create playlist command
     */
    public void setPlaylistName(final String playlistName) {
        this.createPlaylistCommand.setPlaylistName(playlistName);
    }

    // Add/Remove Command

    private AddRemoveCommand addRemoveCommand = new AddRemoveCommand();

    /**
     * This method sets the operation for the add/remove command
     * @param playlistId the playlist id for the add/remove command and switch visibility command
     */
    public void setPlaylistId(final int playlistId) {
        if (command.equals("addRemoveInPlaylist")) {
            this.addRemoveCommand.setPlaylistId(playlistId);
        }
        if (command.equals("switchVisibility")) {
            this.switchVisibilityCommand.setPlaylistId(playlistId);
        }
    }

    // Show Preferred Songs Command
    private ShowPreferredSongsCommand showPreferredSongsCommand = new ShowPreferredSongsCommand();

    // Show Playlists Command
    private ShowPlaylistsCommand showPlaylistsCommand = new ShowPlaylistsCommand();

    // Repeat Command
    private RepeatCommand repeatCommand = new RepeatCommand();

    // Shuffle Command
    private ShuffleCommand shuffleCommand = new ShuffleCommand();

    /**
     * This method sets the seed for the shuffle command
     * @param seed the seed for the shuffle command
     */
    public void setSeed(final int seed) {
        this.shuffleCommand.setSeed(seed);
    }

    // Switch Visibility
    private SwitchVisibilityCommand switchVisibilityCommand = new SwitchVisibilityCommand();

    // Follow Command
    private FollowCommand followCommand = new FollowCommand();

    // Next Command
    private NextCommand nextCommand = new NextCommand();

    // Prev Command
    private PrevCommand prevCommand = new PrevCommand();

    // Forward Command
    private ForwardCommand forwardCommand = new ForwardCommand();

    // Backward Command
    private BackwardCommand backwardCommand = new BackwardCommand();

    // Get Top 5 Playlists Command
    private GetTop5Playlists getTop5PlaylistsCommand = new GetTop5Playlists();

    // Get Top 5 Songs Command
    private GetTop5Songs getTop5SongsCommand = new GetTop5Songs();

    // Switch Connection Status Command
    private SwitchConnectionStatusCommand switchConnectionStatusCommand
            = new SwitchConnectionStatusCommand();

    // Get Online Users Command
    private GetOnlineUsers getOnlineUsersCommand = new GetOnlineUsers();

    // Add User Command
    private AddUserCommand addUserCommand = new AddUserCommand();

    /**
    * This method sets the age for the add user command
    * @param age the age for the add user command
    */
    public void setAge(final int age) {
        this.addUserCommand.setAge(age);
    }

    /**
     * This method sets the city for the add user command
     * @param city the city for the add user command
     */
    public void setCity(final String city) {
        this.addUserCommand.setCity(city);
    }

    // Add Album Command
    private AddAlbumCommand addAlbumCommand = new AddAlbumCommand();

    /**
     * This method sets the name field for the following commands:
     * @param name the name field for the following commands:
     */
    public void setName(final String name) {
        if (command.equals("addAlbum")) {
            this.addAlbumCommand.setName(name);
        } else if (command.equals("addEvent")) {
            this.addEventCommand.setName(name);
        } else if (command.equals("addMerch")) {
            this.addMerchCommand.setName(name);
        } else if (command.equals("addPodcast")) {
            this.addPodcastCommand.setName(name);
        } else if (command.equals("addAnnouncement")) {
            this.addAnnouncementCommand.setName(name);
        } else if (command.equals("removeAnnouncement")) {
            this.removeAnnouncementCommand.setName(name);
        } else if (command.equals("removeAlbum")) {
            this.removeAlbumCommand.setName(name);
        } else if (command.equals("removePodcast")) {
            this.removePodcastCommand.setName(name);
        } else if (command.equals("removeEvent")) {
            this.removeEventCommand.setName(name);
        } else if (command.equals("buyMerch")) {
            this.buyMerchCommand.setName(name);
        }
    }

    /**
     * This method sets the release year for the add album command
     * @param releaseYear the release year for the add album command
     */
    public void setReleaseYear(final int releaseYear) {
        this.addAlbumCommand.setReleaseYear(releaseYear);
    }

    /**
     * This method sets the description field for the following commands:
     * @param description the description field for the following commands:
     */
    public void setDescription(final String description) {
        if (command.equals("addEvent")) {
            this.addEventCommand.setDescription(description);
        } else if (command.equals("addAlbum")) {
            this.addAlbumCommand.setDescription(description);
        } else if (command.equals("addMerch")) {
            this.addMerchCommand.setDescription(description);
        } else if (command.equals("addAnnouncement")) {
            this.addAnnouncementCommand.setDescription(description);
        }
    }

    /**
     * This method sets the songs for the add album command
     * @param songs the songs for the add album command
     */
    public void setSongs(final ArrayList<Song> songs) {
        this.addAlbumCommand.setSongs(songs);
    }

    // Show Albums Command
    private ShowAlbums showAlbumsCommand = new ShowAlbums();

    // Print Current Page Command
    private PrintCurrentPageCommand printCurrentPageCommand = new PrintCurrentPageCommand();

    // Add Event Command
    private AddEventCommand addEventCommand = new AddEventCommand();

    /**
     * This method sets the date for the add event command
     * @param date the date for the add event command
     */
    public void setDate(final String date) {
        this.addEventCommand.setDate(date);
    }

    // Add Merch Command
    private AddMerchCommand addMerchCommand = new AddMerchCommand();

    /**
     * This method sets the price for the add merch command
     * @param price the price for the add merch command
     */
    public void setPrice(final int price) {
        if(command.equals("addMerch"))
            this.addMerchCommand.setPrice(price);
        else if(command.equals("adBreak"))
            AdBreakCommand.setPrice(price);
    }

    // Get All Users Command
    private GetAllUsers getAllUsersCommand = new GetAllUsers();

    // Delete User Command
    private DeleteUser deleteUserCommand = new DeleteUser();

    // Add Podcast Command
    private AddPodcastCommand addPodcastCommand = new AddPodcastCommand();

    /**
     * This method sets the episodes for the add podcast command
     * @param episodes the episodes for the add podcast command
     */
    public void setEpisodes(final ArrayList<EpisodeInput> episodes) {
        this.addPodcastCommand.setEpisodes(episodes);
    }

    // Add Announcement Command
    private AddAnnouncementCommand addAnnouncementCommand = new AddAnnouncementCommand();

    // Remove Announcement Command
    private RemoveAnnouncementCommand removeAnnouncementCommand = new RemoveAnnouncementCommand();

    // Show Podcasts Command
    private ShowPodcasts showPodcastsCommand = new ShowPodcasts();

    // Remove Album Command
    private RemoveAlbumCommand removeAlbumCommand = new RemoveAlbumCommand();

    // Change Current Page Command
    private ChangePageCommand changeCurrentPageCommand = new ChangePageCommand();

    /**
     * This method sets the next page for the change current page command
     * @param nextPage the next page for the change current page command
     */
    public void setNextPage(final String nextPage) {
        this.changeCurrentPageCommand.setNextPage(nextPage);
    }

    // Remove Podcast Command
    private RemovePodcastCommand removePodcastCommand = new RemovePodcastCommand();

    // Remove Event Command
    private RemoveEventCommand removeEventCommand = new RemoveEventCommand();

    // Get Top 5 Artists Command
    private GetTop5Artists getTop5ArtistsCommand = new GetTop5Artists();

    // Get Top 5 Albums Command
    private GetTop5Albums getTop5AlbumsCommand = new GetTop5Albums();

    // Wrapped Command
    private WrappedCommand wrappedCommand = new WrappedCommand();

    // Buy Premium Command
    private BuyPremiumCommand buyPremiumCommand = new BuyPremiumCommand();

    // Cancel Premium Command
    private CancelPremiumCommand cancelPremiumCommand = new CancelPremiumCommand();

    // Ad Break Command
    private AdBreakCommand adBreakCommand = new AdBreakCommand();

    // Subscribe Command
    private SubscribeCommand subscribeCommand = new SubscribeCommand();

    // Get Notification Command
    private GetNotifications getNotificationCommand = new GetNotifications();

    // Buy Merch Command
    private BuyMerch buyMerchCommand = new BuyMerch();

    // See My Merch Command
    private SeeMyMerchCommand seeMyMerchCommand = new SeeMyMerchCommand();

    // Update Recommendations Command
    private UpdateRecommendationsCommand updateRecommendationsCommand = new UpdateRecommendationsCommand();

    // Previous Page Command
    private PreviousPageCommand previousPageCommand = new PreviousPageCommand();

    // Next Page Command
    private NextPageCommand nextPageCommand = new NextPageCommand();

    // Load Recommendation Command
    private LoadRecommendationCommand loadRecommendationsCommand = new LoadRecommendationCommand();



    // setters and getters

    /**
     * This method gets the user
     * @return the user
     */
    public User getUser() {
        return user;
    }

    /**
     * This method sets the user
     * @param user the user
     */
    public void setUser(final User user) {
        this.user = user;
    }

    private LikeCommand likeCommand = new LikeCommand();

    /**
     * This method gets the command list
     * @return the command list
     */
    public List<ObjectNode> getCommandList() {
        return commandList;
    }

    /**
     * This method sets the command list
     * @param command the command name
     */
    public void setCommand(final String command) {
        this.command = command;
    }

    /**
     * This method sets the timestamp
     * @param timestamp the timestamp
     */
    public void setTimestamp(final int timestamp) {
        this.timestamp = timestamp;
    }

    /**
     * This method sets the username
     * @param username the username
     */
    public void setUsername(final String username) {
        if (command.equals("addUser")) {
            this.addUserCommand.setUsername(username);
            return;
        }
        if (command.equals("showAlbums")) {
            this.showAlbumsCommand.setUsername(username);
            return;
        }
        if (command.equals("deleteUser")) {
            this.deleteUserCommand.setUsername(username);
            return;
        }
        if (command.equals("showPodcasts")) {
            this.showPodcastsCommand.setUsername(username);
            return;
        }
        this.username = username;
    }

    /**
     * This method gets the command
     * @return the command
     */
    public String getCommand() {
        return this.command;
    }

    /**
     * This method gets the timestamp
     * @return the timestamp
     */
    public int getTimestamp() {
        return timestamp;
    }

    /**
     * This method gets the username
     * @return the username
     */
    public String getUsername() {
        return username;
    }
    public void setRecommendationType(String recommendationType) {
        this.updateRecommendationsCommand.setRecommendationType(recommendationType);
    }

    /**
     * This method sets the command to execute
     */
    public void setCommandToExecute() {
        if (command.equals("addAlbum")) {
            commandToExecute = addAlbumCommand;
        }
        if (command.equals("addAnnouncement")) {
            commandToExecute = addAnnouncementCommand;
        }
        if (command.equals("addEvent")) {
            commandToExecute = addEventCommand;
        }
        if (command.equals("addMerch")) {
            commandToExecute = addMerchCommand;
        }
        if (command.equals("addPodcast")) {
            commandToExecute = addPodcastCommand;
        }
        if (command.equals("addRemoveInPlaylist")) {
            commandToExecute = addRemoveCommand;
        }
        if (command.equals("addUser")) {
            commandToExecute = addUserCommand;
        }
        if (command.equals("backward")) {
            commandToExecute = backwardCommand;
        }
        if (command.equals("changePage")) {
            commandToExecute = changeCurrentPageCommand;
        }
        if (command.equals("createPlaylist")) {
            commandToExecute = createPlaylistCommand;
        }
        if (command.equals("deleteUser")) {
            commandToExecute = deleteUserCommand;
        }
        if (command.equals("follow")) {
            commandToExecute = followCommand;
        }
        if (command.equals("forward")) {
            commandToExecute = forwardCommand;
        }
        if (command.equals("getAllUsers")) {
            commandToExecute = getAllUsersCommand;
        }
        if (command.equals("getOnlineUsers")) {
            commandToExecute = getOnlineUsersCommand;
        }
        if (command.equals("getTop5Albums")) {
            commandToExecute = getTop5AlbumsCommand;
        }
        if (command.equals("getTop5Artists")) {
            commandToExecute = getTop5ArtistsCommand;
        }
        if (command.equals("getTop5Playlists")) {
            commandToExecute = getTop5PlaylistsCommand;
        }
        if (command.equals("getTop5Songs")) {
            commandToExecute = getTop5SongsCommand;
        }
        if (command.equals("like")) {
            commandToExecute = likeCommand;
        }
        if (command.equals("load")) {
            commandToExecute = loadCommand;
        }
        if (command.equals("next")) {
            commandToExecute = nextCommand;
        }
        if (command.equals("playPause")) {
            commandToExecute = playPauseCommand;
        }
        if (command.equals("prev")) {
            commandToExecute = prevCommand;
        }
        if (command.equals("printCurrentPage")) {
            commandToExecute = printCurrentPageCommand;
        }
        if (command.equals("removeAlbum")) {
            commandToExecute = removeAlbumCommand;
        }
        if (command.equals("removeAnnouncement")) {
            commandToExecute = removeAnnouncementCommand;
        }
        if (command.equals("removeEvent")) {
            commandToExecute = removeEventCommand;
        }
        if (command.equals("removePodcast")) {
            commandToExecute = removePodcastCommand;
        }
        if (command.equals("repeat")) {
            commandToExecute = repeatCommand;
        }
        if (command.equals("search")) {
            commandToExecute = searchCommand;
        }
        if (command.equals("select")) {
            commandToExecute = selectCommand;
        }
        if (command.equals("showAlbums")) {
            commandToExecute = showAlbumsCommand;
        }
        if (command.equals("showPlaylists")) {
            commandToExecute = showPlaylistsCommand;
        }
        if (command.equals("showPodcasts")) {
            commandToExecute = showPodcastsCommand;
        }
        if (command.equals("showPreferredSongs")) {
            commandToExecute = showPreferredSongsCommand;
        }
        if (command.equals("shuffle")) {
            commandToExecute = shuffleCommand;
        }
        if (command.equals("status")) {
            commandToExecute = statusCommand;
        }
        if (command.equals("switchConnectionStatus")) {
            commandToExecute = switchConnectionStatusCommand;
        }
        if (command.equals("switchVisibility")) {
            commandToExecute = switchVisibilityCommand;
        }
        if (command.equals("wrapped")) {
            commandToExecute = wrappedCommand;
        }
        if (command.equals("buyPremium")) {
            commandToExecute = buyPremiumCommand;
        }
        if (command.equals("cancelPremium")) {
            commandToExecute = cancelPremiumCommand;
        }
        if (command.equals("adBreak")) {
            commandToExecute = adBreakCommand;
        }
        if (command.equals("subscribe")) {
            commandToExecute = subscribeCommand;
        }
        if (command.equals("getNotifications")) {
            commandToExecute = getNotificationCommand;
        }
        if (command.equals("buyMerch")) {
            commandToExecute = buyMerchCommand;
        }
        if (command.equals("seeMerch")) {
            commandToExecute = seeMyMerchCommand;
        }
        if (command.equals("updateRecommendations")) {
            commandToExecute = updateRecommendationsCommand;
        }
        if (command.equals("previousPage")) {
            commandToExecute = previousPageCommand;
        }
        if (command.equals("nextPage")) {
            commandToExecute = nextPageCommand;
        }
        if (command.equals("loadRecommendations")) {
            commandToExecute = loadRecommendationsCommand;
        }
    }

    /**
     * This method executes the command
     * @param library the library
     * @param visitor the visitor
     */
    public void executeCommand(final Library library, final Visitor visitor) {
        commandToExecute.accept(this, visitor, library);
    }
}
