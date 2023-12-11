package main;

import AudioFiles.*;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import fileio.input.EpisodeInput;
import fileio.input.SongInput;

import java.util.ArrayList;
import java.util.List;

public class InputCommands {
    private String command;
    private String username;
    private int timestamp;
    private List<ObjectNode> commandList = new ArrayList<>();

    // User for this command
    private User user;

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
    private boolean createdSearch = false;
    private SearchCommand searchCommand;

    /**
     * This method sets the search command
     * @param type the type for the search
     */
    public void setType(final String type) {
        if(command.equals("search")) {
            if (!createdSearch) {
                searchCommand = new SearchCommand();
                createdSearch = true;
            }
            this.searchCommand.setType(type);
        } else if (command.equals("addUser")) {
            if (!createdAddUser) {
                addUserCommand = new AddUserCommand();
                createdAddUser = true;
            }
            this.addUserCommand.setType(type);
        }
    }

    /**
     * This method sets the criteria for the search
     * @param filters the filters for the search
     */
    public void setFilters(final Filter filters) {
        if (!createdSearch) {
            searchCommand = new SearchCommand();
            createdSearch = true;
        }
        this.searchCommand.setFilters(filters);
    }


    // Select Command
    private boolean createdSelect = false;
    private main.SelectCommand selectCommand;

    /**
     * This method sets the item number for the select command
     * @param itemNumber the item number for the select command
     */
    public void setItemNumber(final int itemNumber) {
        if (!createdSelect) {
            selectCommand = new SelectCommand();
            createdSelect = true;
        }
        this.selectCommand.setItemNumber(itemNumber);
    }

    // Load Command
    private boolean createdLoad = false;
    private LoadCommand loadCommand;

    // PlayPause Command
    private boolean createdPlayPause = false;
    private main.PlayPauseCommand playPauseCommand;

    // Status Command

    private boolean createdStatus = false;
    private main.StatusCommand statusCommand;

    // CreatePlaylist Command

    private boolean createdCreatePlaylist = false;
    private main.CreatePlaylistCommand createPlaylistCommand;

    /**
     * This method sets the playlist name for the create playlist command
     * @param playlistName the playlist name for the create playlist command
     */
    public void setPlaylistName(final String playlistName) {
        if (!createdCreatePlaylist) {
            createPlaylistCommand = new main.CreatePlaylistCommand();
            createdCreatePlaylist = true;
        }
        this.createPlaylistCommand.setPlaylistName(playlistName);
    }

    // Add/Remove Command

    private boolean createdAddRemove = false;
    private AddRemoveCommand addRemoveCommand;

    /**
     * This method sets the operation for the add/remove command
     * @param playlistId the playlist id for the add/remove command and switch visibility command
     */
    public void setPlaylistId(final int playlistId) {
        if (command.equals("addRemoveInPlaylist")) {
            if (!createdAddRemove) {
                addRemoveCommand = new AddRemoveCommand();
                createdAddRemove = true;
            }
            this.addRemoveCommand.setPlaylistId(playlistId);
        }
        if (command.equals("switchVisibility")) {
            if (!createdSwitchVisibility) {
                switchVisibilityCommand = new main.SwitchVisibilityCommand();
                createdSwitchVisibility = true;
            }
            this.switchVisibilityCommand.setPlaylistId(playlistId);
        }
    }

    // Show Preferred Songs Command
    private boolean createdShowPreferred = false;
    private ShowPreferredSongsCommand showPreferredSongsCommand;

    // Show Playlists Command
    private boolean createdShowPlaylists = false;
    private ShowPlaylistsCommand showPlaylistsCommand;

    // Repeat Command
    private boolean createdRepeat = false;
    private main.RepeatCommand repeatCommand;

    // Shuffle Command
    private boolean createdShuffle = false;
    private ShuffleCommand shuffleCommand;

    /**
     * This method sets the seed for the shuffle command
     * @param seed the seed for the shuffle command
     */
    public void setSeed(final int seed) {
        if (!createdShuffle) {
            shuffleCommand = new ShuffleCommand();
            createdShuffle = true;
        }
        this.shuffleCommand.setSeed(seed);
    }

    // Switch Visibility
    private boolean createdSwitchVisibility = false;
    private main.SwitchVisibilityCommand switchVisibilityCommand;

    // Follow Command
    private boolean createdFollow = false;
    private FollowCommand followCommand;

    // Next Command
    private boolean createdNext = false;
    private NextCommand nextCommand;

    // Prev Command
    private boolean createdPrev = false;
    private PrevCommand prevCommand;

    // Forward Command
    private boolean createdForward = false;
    private main.ForwardCommand forwardCommand;

    // Backward Command
    private boolean createdBackward = false;
    private BackwardCommand backwardCommand;

    // Get Top 5 Playlists Command
    private boolean createdGetTop5Playlists = false;
    private main.GetTop5Playlists getTop5PlaylistsCommand;

    // Get Top 5 Songs Command
    private boolean createdGetTop5Songs = false;
    private main.GetTop5Songs getTop5SongsCommand;

    // Switch Connection Status Command
    private boolean createdSwitchConnectionStatus = false;
    private main.SwitchConnectionStatusCommand switchConnectionStatusCommand;

    // Get Online Users Command
    private boolean createdGetOnlineUsers = false;
    private GetOnlineUsers getOnlineUsersCommand;

    // Add User Command
    private boolean createdAddUser = false;
    private AddUserCommand addUserCommand;

    public void setAge(int age) {
        if (!createdAddUser) {
            addUserCommand = new AddUserCommand();
            createdAddUser = true;
        }
        this.addUserCommand.setAge(age);
    }

    public void setCity(String city) {
        if (!createdAddUser) {
            addUserCommand = new AddUserCommand();
            createdAddUser = true;
        }
        this.addUserCommand.setCity(city);
    }

    // Add Album Command
    private boolean createdAddAlbum = false;
    private AddAlbumCommand addAlbumCommand;

    public void setName(String name) {
        if(command.equals("addAlbum")) {
            if (!createdAddAlbum) {
                addAlbumCommand = new AddAlbumCommand();
                createdAddAlbum = true;
            }
            this.addAlbumCommand.setName(name);
        } else if(command.equals("addEvent")) {
            if (!createdAddEvent) {
                addEventCommand = new AddEventCommand();
                createdAddEvent = true;
            }
            this.addEventCommand.setName(name);
        } else if(command.equals("addMerch")) {
            if (!createdAddMerch) {
                addMerchCommand = new AddMerchCommand();
                createdAddMerch = true;
            }
            this.addMerchCommand.setName(name);
        } else if (command.equals("addPodcast")) {
            if (!createdAddPodcast) {
                addPodcastCommand = new AddPodcastCommand();
                createdAddPodcast = true;
            }
            this.addPodcastCommand.setName(name);
        } else if (command.equals("addAnnouncement")) {
            if (!createdAddAnnouncement) {
                addAnnouncementCommand = new AddAnnouncementCommand();
                createdAddAnnouncement = true;
            }
            this.addAnnouncementCommand.setName(name);
        } else if (command.equals("removeAnnouncement")) {
            if (!createdRemoveAnnouncement) {
                removeAnnouncementCommand = new RemoveAnnouncementCommand();
                createdRemoveAnnouncement = true;
            }
            this.removeAnnouncementCommand.setName(name);
        } else if (command.equals("removeAlbum")) {
            if (!createdRemoveAlbum) {
                removeAlbumCommand = new RemoveAlbumCommand();
                createdRemoveAlbum = true;
            }
            this.removeAlbumCommand.setName(name);
        } else if (command.equals("removePodcast")) {
            if (!createdRemovePodcast) {
                removePodcastCommand = new RemovePodcastCommand();
                createdRemovePodcast = true;
            }
            this.removePodcastCommand.setName(name);
        } else if (command.equals("removeEvent")) {
            if (!createdRemoveEvent) {
                removeEventCommand = new RemoveEventCommand();
                createdRemoveEvent = true;
            }
            this.removeEventCommand.setName(name);
        }
    }

    public void setReleaseYear(int releaseYear) {
        if (!createdAddAlbum) {
            addAlbumCommand = new AddAlbumCommand();
            createdAddAlbum = true;
        }
        this.addAlbumCommand.setReleaseYear(releaseYear);
    }

    public void setDescription(String description) {
        if(command.equals("addEvent")) {
            if (!createdAddEvent) {
                addEventCommand = new AddEventCommand();
                createdAddEvent = true;
            }
            this.addEventCommand.setDescription(description);
        } else if(command.equals("addAlbum")) {
            if (!createdAddAlbum) {
                addAlbumCommand = new AddAlbumCommand();
                createdAddAlbum = true;
            }
            this.addAlbumCommand.setDescription(description);
        } else if(command.equals("addMerch")) {
            if (!createdAddMerch) {
                addMerchCommand = new AddMerchCommand();
                createdAddMerch = true;
            }
            this.addMerchCommand.setDescription(description);
        } else if (command.equals("addAnnouncement")) {
            if (!createdAddAnnouncement) {
                addAnnouncementCommand = new AddAnnouncementCommand();
                createdAddAnnouncement = true;
            }
            this.addAnnouncementCommand.setDescription(description);
        }
    }

    public void setSongs(ArrayList<Song> songs) {
        if (!createdAddAlbum) {
            addAlbumCommand = new AddAlbumCommand();
            createdAddAlbum = true;
        }
        this.addAlbumCommand.setSongs(songs);
    }

    // Show Albums Command
    private boolean createdShowAlbums = false;
    private ShowAlbums showAlbumsCommand;

    // Print Current Page Command
    private boolean createdPrintCurrentPage = false;
    private PrintCurrentPageCommand printCurrentPageCommand;

    // Add Event Command
    private boolean createdAddEvent = false;
    private AddEventCommand addEventCommand;

    public void setDate(String date) {
        if (!createdAddEvent) {
            addEventCommand = new AddEventCommand();
            createdAddEvent = true;
        }
        this.addEventCommand.setDate(date);
    }

    // Add Merch Command
    private boolean createdAddMerch = false;
    private AddMerchCommand addMerchCommand;

    public void setPrice(int price) {
        if (!createdAddMerch) {
            addMerchCommand = new AddMerchCommand();
            createdAddMerch = true;
        }
        this.addMerchCommand.setPrice(price);
    }

    // Get All Users Command
    private boolean createdGetAllUsers = false;
    private GetAllUsers getAllUsersCommand;

    // Delete User Command
    private boolean createdDeleteUser = false;
    private DeleteUser deleteUserCommand;

    // Add Podcast Command
    private boolean createdAddPodcast = false;
    private AddPodcastCommand addPodcastCommand;

    public void setEpisodes(ArrayList<EpisodeInput> episodes) {
        if (!createdAddPodcast) {
            addPodcastCommand = new AddPodcastCommand();
            createdAddPodcast = true;
        }
        this.addPodcastCommand.setEpisodes(episodes);
    }

    // Add Announcement Command
    private boolean createdAddAnnouncement = false;
    private AddAnnouncementCommand addAnnouncementCommand;

    // Remove Announcement Command
    private boolean createdRemoveAnnouncement = false;
    private RemoveAnnouncementCommand removeAnnouncementCommand;

    // Show Podcasts Command
    private boolean createdShowPodcasts = false;
    private ShowPodcasts showPodcastsCommand;

    // Remove Album Command
    private boolean createdRemoveAlbum = false;
    private RemoveAlbumCommand removeAlbumCommand;

    // Change Current Page Command
    private boolean createdChangeCurrentPage = false;
    private ChangePageCommand changeCurrentPageCommand;

    public void setNextPage(String nextPage) {
        if (!createdChangeCurrentPage) {
            changeCurrentPageCommand = new ChangePageCommand();
            createdChangeCurrentPage = true;
        }
        this.changeCurrentPageCommand.setNextPage(nextPage);
    }

    // Remove Podcast Command
    private boolean createdRemovePodcast = false;
    private RemovePodcastCommand removePodcastCommand;

    // Remove Event Command
    private boolean createdRemoveEvent = false;
    private RemoveEventCommand removeEventCommand;

    // Get Top 5 Artists Command
    private boolean createdGetTop5Artists = false;
    private main.GetTop5Artists getTop5ArtistsCommand;

    // Get Top 5 Albums Command
    private boolean createdGetTop5Albums = false;
    private main.GetTop5Albums getTop5AlbumsCommand;

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

    private boolean createdLikeCommand = false;
    private LikeCommand likeCommand;

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
        if(command.equals("addUser")) {
            if (!createdAddUser) {
                addUserCommand = new AddUserCommand();
                createdAddUser = true;
            }
            this.addUserCommand.setUsername(username);
            return;
        }
        if(command.equals("showAlbums")) {
            if (!createdShowAlbums) {
                showAlbumsCommand = new ShowAlbums();
                createdShowAlbums = true;
            }
            this.showAlbumsCommand.setUsername(username);
            return;
        }
        if(command.equals("deleteUser")) {
            if (!createdDeleteUser) {
                deleteUserCommand = new DeleteUser();
                createdDeleteUser = true;
            }
            this.deleteUserCommand.setUsername(username);
            return;
        }
        if(command.equals("showPodcasts")) {
            if (!createdShowPodcasts) {
                showPodcastsCommand = new ShowPodcasts();
                createdShowPodcasts = true;
            }
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

    /**
     * This method executes the command
     * @param library the library
     */
    public void searchExecute(final Library library) {
        ObjectMapper objectMapper = new ObjectMapper(); // Instantiate ObjectMapper here
        // initialize the new search
        user.setLastSearch(new ArrayList<>());
        user.setLastSearchUsers(new ArrayList<>());

        // initialize the player or get out the track that is there
        if (user.getPlayer().repeatState == 0) {
            user.getPlayer().setRemainingTime();
        } else if (user.getPlayer().repeatState == 1) {
            user.getPlayer().setRemainingTimeRepeat1();
        } else if (user.getPlayer().repeatState == 2) {
            user.getPlayer().setRemainingTimeRepeat2();
        }


        user.getPlayer().initializePlayer();
        int size = 0;

        ArrayNode results = JsonNodeFactory.instance.arrayNode();

        if(searchCommand.getType().equals("song") || searchCommand.getType().equals("podcast")
            || searchCommand.getType().equals("playlist") || searchCommand.getType().equals("album")) {
            SearchCommand.setSearchResults(searchCommand.getSearchResults(library, user));
            if(SearchCommand.getSearchResults() != null) {
                user.setLastSearch(SearchCommand.getSearchResults());
                for (AudioFile audioFile : SearchCommand.getSearchResults()) {
                    results.add(audioFile.getName());
                }
                size = SearchCommand.getSearchResults().size();
                // clear the user search
                user.setLastSearchUsers(null);
            }
        } else if (searchCommand.getType().equals("artist") || searchCommand.getType().equals("host")) {
            SearchCommand.setSearchUsers(searchCommand.getSearchUsers(library, user));
            if(SearchCommand.getSearchUsers() != null) {
                user.setLastSearchUsers(SearchCommand.getSearchUsers());
                for (User user : SearchCommand.getSearchUsers()) {
                    results.add(user.getUsername());
                }
                size = SearchCommand.getSearchUsers().size();
                // clear the audiofile search
                user.setLastSearch(null);
            }
        }
        ObjectNode commandJson = objectMapper.createObjectNode()
                .put("command", "search")
                .put("user", getUsername())
                .put("timestamp", getTimestamp())
                .put("message", "Search returned "
                        + size + " results")
                .set("results", results);

        commandList.add(commandJson);
    }

    /**
     * This method executes the command
     */
    public void selectExecute() {
        String message = null;
        if (selectCommand != null) {
            if(user.getLastSearch() == null && user.getLastSearchUsers() == null) {
                message = "Please conduct a search before making a selection.";
            } else if(user.getLastSearch() != null && user.getLastSearchUsers() == null) {
                // searched an audio file
                selectCommand.provideSelectedItem(user);
                if (SelectCommand.getSelectedItem() != null) {
                    user.setSelectedItem(SelectCommand.getSelectedItem());
                    message = "Successfully selected " + SelectCommand.getSelectedItem().getName() + ".";
                    user.setLastSearch(null);
                    SelectCommand.setSelectedItem(null);
                } else {
                    if(selectCommand.getItemNumber() > user.getLastSearch().size()) {
                        message = "The selected ID is too high.";
                        SelectCommand.setSelectedItem(null);
                        user.setLastSearch(null);
                        user.setSelectedItem(null);
                    }
                    else {
                        message = "Please conduct a search before making a selection.";
                    }
                }
            } else if(user.getLastSearchUsers() != null && user.getLastSearch() == null) {
                // searched a user
                selectCommand.provideSelectedUser(user);
                if (SelectCommand.getSelectedUser() != null) {
                    message = "Successfully selected " + SelectCommand.getSelectedUser().getUsername() + "'s page.";
                    user.setLastSearchUsers(null);
                    // set the current page
                    if(SelectCommand.getSelectedUser() instanceof Artist) {
                        user.setCurrentPage(((Artist) SelectCommand.getSelectedUser()).getArtistPage());
                    } else if(SelectCommand.getSelectedUser() instanceof Host) {
                        user.setCurrentPage(((Host) SelectCommand.getSelectedUser()).getHostPage());
                    }
                } else {
                    if(selectCommand.getItemNumber() > user.getLastSearchUsers().size())
                        message = "The selected ID is too high.";
                    else
                        message = "Please conduct a search before making a selection.";
                }
            }
        }

        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode commandJson = objectMapper.createObjectNode()
                .put("command", "select")
                .put("user", getUsername())
                .put("timestamp", getTimestamp())
                .put("message", message);

        commandList.add(commandJson);
    }

    /**
     * This method executes the command
     */
    public void loadExecute() {
        if (!createdLoad) {
            loadCommand = new LoadCommand();
            createdLoad = true;
        }

        String message = loadCommand.buildMessage(user.getSelectedItem());
        if (LoadCommand.getLoadedItem() != null) {
            user.getPlayer().loadedItem = LoadCommand.getLoadedItem();
            user.getPlayer().setPlayingNow();
            user.getPlayer().shuffle = false;
            user.getPlayer().repeatState = 0;
        }
        user.setSelectedItem(null);
        LoadCommand.setLoadedItem(null);

        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode commandJson = objectMapper.createObjectNode()
                .put("command", "load")
                .put("user", getUsername())
                .put("timestamp", getTimestamp())
                .put("message", message);

        commandList.add(commandJson);
    }

    /**
     * This method executes the command
     */
    public void playPauseExecute() {
        if (!createdPlayPause) {
            playPauseCommand = new main.PlayPauseCommand();
            createdPlayPause = true;
        }

        if (user.getPlayer().loadedItem != null) {
            // Calculate remaining time before making any state changes
            if (user.getPlayer().repeatState == 0) {
                user.getPlayer().setRemainingTime();
            } else if (user.getPlayer().repeatState == 1) {
                user.getPlayer().setRemainingTimeRepeat1();
            } else if (user.getPlayer().repeatState == 2) {
                user.getPlayer().setRemainingTimeRepeat2();
            }

            if (!user.getPlayer().paused) {
                // If currently playing, pause the playback
                user.getPlayer().paused = true;
                user.getPlayer().switchedTime = timestamp;
            } else {
                // If currently paused, resume the playback
                user.getPlayer().paused = false;
                user.getPlayer().switchedTime = timestamp;
            }
        }

        // Build the message and generate the corresponding JSON representation
        String message = playPauseCommand.buildMessage(user.getPlayer());
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode commandJson = objectMapper.createObjectNode()
                .put("command", "playPause")
                .put("user", getUsername())
                .put("timestamp", getTimestamp())
                .put("message", message);

        commandList.add(commandJson);
    }

    /**
     * This method executes the command
     */
    public void statusExecute() {
        if (!createdStatus) {
            statusCommand = new main.StatusCommand();
            createdStatus = true;
        }

        if(user.getStatusOnline()) {
            if (user.getPlayer().repeatState == 0) {
                user.getPlayer().setRemainingTime();
            } else if (user.getPlayer().repeatState == 1) {
                user.getPlayer().setRemainingTimeRepeat1();
            } else if (user.getPlayer().repeatState == 2) {
                user.getPlayer().setRemainingTimeRepeat2();
            }
        }

        ArrayList<Object> statusArray = statusCommand.buildStatusArray(user.getPlayer());

        // Create the status JSON structure
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode commandJson = objectMapper.createObjectNode()
                .put("command", "status")
                .put("user", getUsername())
                .put("timestamp", getTimestamp());

        final int index1 = 1;
        final int index2 = 2;
        final int index3 = 3;
        final int index4 = 4;
        final int index0 = 0;

        // Create the "stats" object within the JSON structure
        ObjectNode statsNode = objectMapper.createObjectNode()
                .put("name", (String) statusArray.get(index0))
                .put("remainedTime", (int) statusArray.get(index1))
                .put("repeat", (String) statusArray.get(index2))
                .put("shuffle", (boolean) statusArray.get(index3))
                .put("paused", (boolean) statusArray.get(index4));

        // Add the "stats" object to the main JSON structure
        commandJson.set("stats", statsNode);

        // Add the commandJson to the commandList
        commandList.add(commandJson);
    }

    /**
     * This method executes the command
     * @param library the library
     */
    public void createPlaylistExecute(final Library library) {
        String message = createPlaylistCommand.message(user, library);

        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode commandJson = objectMapper.createObjectNode()
                .put("command", "createPlaylist")
                .put("user", getUsername())
                .put("timestamp", getTimestamp())
                .put("message", message);

        commandList.add(commandJson);
    }

    /**
     * This method executes the command
     */
    public void addRemoveExecute() {
        String message = addRemoveCommand.addRemoveMessage(user);

        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode commandJson = objectMapper.createObjectNode()
                .put("command", "addRemoveInPlaylist")
                .put("user", getUsername())
                .put("timestamp", getTimestamp())
                .put("message", message);

        commandList.add(commandJson);
    }

    /**
     * This method executes the command
     */
    public void likeExecute() {
        if (!createdLikeCommand) {
            likeCommand = new LikeCommand();
            createdLikeCommand = true;
        }

        user.getPlayer().timestamp = timestamp;
        if (user.getPlayer().loadedItem != null) {
            if (user.getPlayer().repeatState == 0) {
                user.getPlayer().setRemainingTime();
            }
            if (user.getPlayer().repeatState == 1) {
                user.getPlayer().setRemainingTimeRepeat1();
            }
            if (user.getPlayer().repeatState == 2) {
                user.getPlayer().setRemainingTimeRepeat2();
            }
        }

        likeCommand.setMessage(user);

        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode commandJson = objectMapper.createObjectNode()
                .put("command", "like")
                .put("user", getUsername())
                .put("timestamp", getTimestamp())
                .put("message", likeCommand.getMessage());

        commandList.add(commandJson);
    }

    /**
     * This method executes the command
     */
    public void showPreferredSongsExecute() {
        if (!createdShowPreferred) {
            showPreferredSongsCommand = new main.ShowPreferredSongsCommand();
            createdShowPreferred = true;
        }
        showPreferredSongsCommand.setPreferredSongs(user);
        ArrayList<Song> songs = showPreferredSongsCommand.getPreferredSongs();

        ArrayNode results = JsonNodeFactory.instance.arrayNode();

        for (Song song : songs) {
            results.add(song.getName());
        }

        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode commandJson = objectMapper.createObjectNode()
                .put("command", "showPreferredSongs")
                .put("user", getUsername())
                .put("timestamp", getTimestamp())
                .set("result", results);

        commandList.add(commandJson);
    }

    /**
     * This method executes the command
     */
    public void showPlaylistsExecute() {
        if (!createdShowPlaylists) {
            showPlaylistsCommand = new ShowPlaylistsCommand();
            createdShowPlaylists = true;
        }

        showPlaylistsCommand.setPlaylists(user);

        ArrayList<Playlist> playlists = showPlaylistsCommand.getPlaylists();

        ArrayNode resultsArray = JsonNodeFactory.instance.arrayNode();

        for (Playlist playlist : playlists) {
            ObjectNode playlistNode = JsonNodeFactory.instance.objectNode()
                    .put("name", playlist.getName());

            ArrayNode songsArray = JsonNodeFactory.instance.arrayNode();
            for (Song song : playlist.getSongs()) {
                songsArray.add(song.getName());
            }

            playlistNode.set("songs", songsArray);
            resultsArray.add(playlistNode);
            playlistNode.put("visibility", playlist.isPublic() ? "public" : "private")
                    .put("followers", playlist.getFollowers());
        }

        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode commandJson = objectMapper.createObjectNode()
                .put("command", "showPlaylists")
                .put("user", getUsername())
                .put("timestamp", getTimestamp())
                .set("result", resultsArray);

        commandList.add(commandJson);
    }

    /**
     * This method executes the command
     */
    public void repeatExecute() {
        if (!createdRepeat) {
            repeatCommand = new main.RepeatCommand();
            createdRepeat = true;
        }

        String message;
        if (user.getPlayer().loadedItem == null) {
            message = "Please load a source before setting the repeat status.";
        } else {
            if (user.getPlayer().repeatState == 0) {
                user.getPlayer().setRemainingTime();
            }
            if (user.getPlayer().repeatState == 1) {
                user.getPlayer().setRemainingTimeRepeat1();
            }
            if (user.getPlayer().repeatState == 2) {
                user.getPlayer().setRemainingTimeRepeat2();
            }
            repeatCommand.setRepeatMode(user);
            message = repeatCommand.message(user);
        }

        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode commandJson = objectMapper.createObjectNode()
                .put("command", "repeat")
                .put("user", getUsername())
                .put("timestamp", getTimestamp())
                .put("message", message);

        commandList.add(commandJson);
    }

    /**
     * This method executes the command
     */
    public void shuffleExecute() {
        // Initialize shuffleCommand if not already created
        if (!createdShuffle) {
            shuffleCommand = new ShuffleCommand();
            createdShuffle = true;
        }

        if (user.getPlayer().repeatState == 0) {
            user.getPlayer().setRemainingTime();
        }
        if (user.getPlayer().repeatState == 1) {
            user.getPlayer().setRemainingTimeRepeat1();
        }
        if (user.getPlayer().repeatState == 2) {
            user.getPlayer().setRemainingTimeRepeat2();
        }

        shuffleCommand.shufflePlayer(user);
        String message = shuffleCommand.message(user);

        // Check if shuffleCommand is not null before accessing its methods
        if (shuffleCommand != null && user.getPlayer().loadedItem != null) {
            if (user.getPlayer().shuffle) {
                // Update the shuffled Playlist from player
                if(user.getPlayer().loadedItem instanceof Playlist)
                    user.getPlayer().shuffledPlaylist = shuffleCommand.
                        shufflePlaylist((Playlist) user.getPlayer().loadedItem);
                if(user.getPlayer().loadedItem instanceof Album)
                    user.getPlayer().shuffledPlaylist = shuffleCommand.
                            shuffleAlbum((Album) user.getPlayer().loadedItem);
                shuffleCommand.setSeed(0);
            }
        }

        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode commandJson = objectMapper.createObjectNode()
                .put("command", "shuffle")
                .put("user", getUsername())
                .put("timestamp", getTimestamp())
                .put("message", message);

        commandList.add(commandJson);
    }

    /**
     * This method executes the command
     * @param library the library
     */
    public void switchVisibilityExecute(final Library library) {
        switchVisibilityCommand.switchVisibility(user, library);
        String message = switchVisibilityCommand.message(user);

        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode commandJson = objectMapper.createObjectNode()
                .put("command", "switchVisibility")
                .put("user", getUsername())
                .put("timestamp", getTimestamp())
                .put("message", message);

        commandList.add(commandJson);
    }

    /**
     * This method executes the command
     */
    public void followExecute() {
        if (!createdFollow) {
            followCommand = new FollowCommand();
            createdFollow = true;
        }

        followCommand.followPlaylist(user);

        String message = followCommand.getMessage();

        if (message.equals("Playlist unfollowed successfully.")
            || message.equals("Playlist followed successfully.")) {
        }

        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode commandJson = objectMapper.createObjectNode()
                .put("command", "follow")
                .put("user", getUsername())
                .put("timestamp", getTimestamp())
                .put("message", message);

        commandList.add(commandJson);
    }

    /**
     * This method executes the command
     */
    public void nextExecute() {
        if (!createdNext) {
            nextCommand = new NextCommand();
            createdNext = true;
        }

        String message;

        if (user.getPlayer().loadedItem != null) {
            if (user.getPlayer().repeatState == 0) {
                user.getPlayer().setRemainingTime();
            }
            if (user.getPlayer().repeatState == 1) {
                user.getPlayer().setRemainingTimeRepeat1();
            }
            if (user.getPlayer().repeatState == 2) {
                user.getPlayer().setRemainingTimeRepeat2();
            }
        }

        if (user.getPlayer().loadedItem == null) {
            message = "Please load a source before skipping to the next track.";
        } else {
            if (user.getPlayer().loadedItem instanceof Song) {
                nextCommand.goToNextSong(user);
            } else if (user.getPlayer().loadedItem instanceof Playlist) {
                nextCommand.goToNextPlaylist(user);
            } else if (user.getPlayer().loadedItem instanceof Podcast) {
                nextCommand.goToNextPodcast(user);
            } else if (user.getPlayer().loadedItem instanceof Album) {
                nextCommand.goToNextAlbum(user);
            }
            message = nextCommand.getMessage();
        }

        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode commandJson = objectMapper.createObjectNode()
                .put("command", "next")
                .put("user", getUsername())
                .put("timestamp", getTimestamp())
                .put("message", message);

        commandList.add(commandJson);
    }

    /**
     * This method executes the command
     */
    public void prevExecute() {
        if (!createdPrev) {
            prevCommand = new PrevCommand();
            createdPrev = true;
        }

        String message;

        if (user.getPlayer().loadedItem != null) {
            if (user.getPlayer().repeatState == 0) {
                user.getPlayer().setRemainingTime();
            }
            if (user.getPlayer().repeatState == 1) {
                user.getPlayer().setRemainingTimeRepeat1();
            }
            if (user.getPlayer().repeatState == 2) {
                user.getPlayer().setRemainingTimeRepeat2();
            }
        }

        if (user.getPlayer().loadedItem == null) {
            message = "Please load a source before returning to the previous track.";
        } else {
            if (user.getPlayer().loadedItem instanceof Song) {
                prevCommand.goToPrevSong(user);
            } else if (user.getPlayer().loadedItem instanceof Playlist) {
                prevCommand.goToPrevPlaylist(user);
            } else if (user.getPlayer().loadedItem instanceof Podcast) {
                prevCommand.goToPrevPodcast(user);
            } else if (user.getPlayer().loadedItem instanceof Album) {
                prevCommand.goToPrevAlbum(user);
            }
            message = prevCommand.getMessage();
        }

        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode commandJson = objectMapper.createObjectNode()
                .put("command", "prev")
                .put("user", getUsername())
                .put("timestamp", getTimestamp())
                .put("message", message);

        commandList.add(commandJson);
    }

    /**
     * This method executes the command
     */
    public void forwardExecute() {
        if (!createdForward) {
            forwardCommand = new main.ForwardCommand();
            createdForward = true;
        }

        String message;

        if (user.getPlayer().loadedItem != null) {
            if (user.getPlayer().repeatState == 0) {
                user.getPlayer().setRemainingTime();
            }
            if (user.getPlayer().repeatState == 1) {
                user.getPlayer().setRemainingTimeRepeat1();
            }
            if (user.getPlayer().repeatState == 2) {
                user.getPlayer().setRemainingTimeRepeat2();
            }
        }

        forwardCommand.forwardPodcast(user);
        message = forwardCommand.getMessage();

        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode commandJson = objectMapper.createObjectNode()
                .put("command", "forward")
                .put("user", getUsername())
                .put("timestamp", getTimestamp())
                .put("message", message);

        commandList.add(commandJson);
    }

    /**
     * This method executes the command
     */
    public void backwardExecute() {
        if (!createdBackward) {
            backwardCommand = new BackwardCommand();
            createdBackward = true;
        }

        String message;

        if (user.getPlayer().loadedItem != null) {
            if (user.getPlayer().repeatState == 0) {
                user.getPlayer().setRemainingTime();
            }
            if (user.getPlayer().repeatState == 1) {
                user.getPlayer().setRemainingTimeRepeat1();
            }
            if (user.getPlayer().repeatState == 2) {
                user.getPlayer().setRemainingTimeRepeat2();
            }
        }

        backwardCommand.backwardPodcast(user);
        message = backwardCommand.getMessage();

        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode commandJson = objectMapper.createObjectNode()
                .put("command", "backward")
                .put("user", getUsername())
                .put("timestamp", getTimestamp())
                .put("message", message);

        commandList.add(commandJson);
    }

    /**
     * This method executes the command
     */
    public void getTop5PlaylistsExecute() {
        if (!createdGetTop5Playlists) {
            getTop5PlaylistsCommand = new main.GetTop5Playlists();
            createdGetTop5Playlists = true;
        }

        getTop5PlaylistsCommand.setTop5Playlists();

        ArrayList<Playlist> playlists = getTop5PlaylistsCommand.getTop5Playlists();

        // Create an array node for the results
        ArrayNode resultsArray = JsonNodeFactory.instance.arrayNode();

        // Add the names of the playlists to the results array
        for (Playlist playlist : playlists) {
            resultsArray.add(playlist.getName());
        }

        // Create the command JSON structure
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode commandJson = objectMapper.createObjectNode()
                .put("command", "getTop5Playlists")
                .put("timestamp", getTimestamp())
                .set("result", resultsArray);

        // Add the commandJson to the commandList
        commandList.add(commandJson);
    }

    /**
     * This method executes the command
     * @param library the library
     */
    public void getTop5SongsExecute(final Library library) {
        if (!createdGetTop5Songs) {
            getTop5SongsCommand = new main.GetTop5Songs();
            createdGetTop5Songs = true;
        }

        getTop5SongsCommand.setTop5Songs(library);

        ArrayList<Song> songs = getTop5SongsCommand.getTop5Songs();

        // Create an array node for the results
        ArrayNode resultsArray = JsonNodeFactory.instance.arrayNode();

        // Add the names of the songs to the results array
        for (Song song : songs) {
            resultsArray.add(song.getName());
        }

        // Create the command JSON structure
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode commandJson = objectMapper.createObjectNode()
                .put("command", "getTop5Songs")
                .put("timestamp", getTimestamp())
                .set("result", resultsArray);

        // Add the commandJson to the commandList
        commandList.add(commandJson);
    }

    /**
     * This method executes the command SwitchConnectionStatus
     */
    public void switchConnectionStatusExecute() {
        if (!createdSwitchConnectionStatus) {
            switchConnectionStatusCommand = new main.SwitchConnectionStatusCommand();
            createdSwitchConnectionStatus = true;
        }

        if(user.getStatusOnline()) {
            if (user.getPlayer().repeatState == 0) {
                user.getPlayer().setRemainingTime();
            } else if (user.getPlayer().repeatState == 1) {
                user.getPlayer().setRemainingTimeRepeat1();
            } else if (user.getPlayer().repeatState == 2) {
                user.getPlayer().setRemainingTimeRepeat2();
            }
        }

        switchConnectionStatusCommand.switchConnectionStatus(user, timestamp);

        String message = switchConnectionStatusCommand.getMessage();

        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode commandJson = objectMapper.createObjectNode()
                .put("command", "switchConnectionStatus")
                .put("user", getUsername())
                .put("timestamp", getTimestamp())
                .put("message", message);

        commandList.add(commandJson);
    }

    /**
     * This method executes the command GetOnlineUsers
     */
    public void getOnlineUsersExecute() {
        if (!createdGetOnlineUsers) {
            getOnlineUsersCommand = new GetOnlineUsers();
            createdGetOnlineUsers = true;
        }

        getOnlineUsersCommand.setOnlineUsersNames();

        ArrayList<String> onlineUsers = getOnlineUsersCommand.getOnlineUsersNames();

        // Create an array node for the results
        ArrayNode resultsArray = JsonNodeFactory.instance.arrayNode();

        // Add the names of the online users to the results array
        for (String username : onlineUsers) {
            resultsArray.add(username);
        }

        // Create the command JSON structure
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode commandJson = objectMapper.createObjectNode()
                .put("command", "getOnlineUsers")
                .put("timestamp", getTimestamp())
                .set("result", resultsArray);

        // Add the commandJson to the commandList
        commandList.add(commandJson);
    }

    /**
     * This method executes the command AddUser
     * @param library the library
     */
    public void addUserExecute(final Library library) {
        if (!createdAddUser) {
            addUserCommand = new AddUserCommand();
            createdAddUser = true;
        }

        addUserCommand.addUser(library);

        String message = addUserCommand.getMessage();

        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode commandJson = objectMapper.createObjectNode()
                .put("command", "addUser")
                .put("user", addUserCommand.getUsername())
                .put("timestamp", getTimestamp())
                .put("message", message);

        commandList.add(commandJson);
    }

    /**
     * This method executes the command AddAlbum
     *
     */
    public void addAlbumExecute(Library library) {
        if (!createdAddAlbum) {
            addAlbumCommand = new AddAlbumCommand();
            createdAddAlbum = true;
        }

        addAlbumCommand.addAlbum(user, library);

        String message = addAlbumCommand.getMessage();

        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode commandJson = objectMapper.createObjectNode()
                .put("command", "addAlbum")
                .put("user", getUsername())
                .put("timestamp", getTimestamp())
                .put("message", message);

        commandList.add(commandJson);
    }

    /**
     * This method executes the command ShowAlbums
     *
     */
    public void showAlbumsExecute(Library library) {
        if (!createdShowAlbums) {
            showAlbumsCommand = new ShowAlbums();
            createdShowAlbums = true;
        }

        showAlbumsCommand.setAlbums(library);

        ArrayList<Album> albums = showAlbumsCommand.getAlbums();

        // Create an array node for the results
        ArrayNode resultsArray = JsonNodeFactory.instance.arrayNode();

        // the results array has the name of the album and the list of songs
        for (Album album : albums) {
            ObjectNode albumNode = JsonNodeFactory.instance.objectNode()
                    .put("name", album.getName());

            ArrayNode songsArray = JsonNodeFactory.instance.arrayNode();
            for (Song song : album.getSongs()) {
                songsArray.add(song.getName());
            }

            albumNode.set("songs", songsArray);
            resultsArray.add(albumNode);
        }

        // Create the command JSON structure
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode commandJson = objectMapper.createObjectNode()
                .put("command", "showAlbums")
                .put("user", showAlbumsCommand.getUsername())
                .put("timestamp", getTimestamp())
                .set("result", resultsArray);

        // Add the commandJson to the commandList
        commandList.add(commandJson);
    }

    /**
     * This method executes the command PrintCurrentPage
     *
     */
    public void printCurrentPageExecute(Library library) {
        if (!createdPrintCurrentPage) {
            printCurrentPageCommand = new PrintCurrentPageCommand();
            createdPrintCurrentPage = true;
        }

        // update before printing the pages
        user.setHomePage();
        user.setLikedContentPage();

        printCurrentPageCommand.setUsername(user.getUsername());
        printCurrentPageCommand.setCurrentPage(library);

        String message = printCurrentPageCommand.getCurrentPage().printPage();

        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode commandJson = objectMapper.createObjectNode()
                .put("user", printCurrentPageCommand.getUsername())
                .put("command", "printCurrentPage")
                .put("timestamp", getTimestamp())
                .put("message", message);

        commandList.add(commandJson);
    }

    /**
     * This method executes the command AddEvent
     *
     */
    public void addEventExecute() {
        if (!createdAddEvent) {
            addEventCommand = new AddEventCommand();
            createdAddEvent = true;
        }

        addEventCommand.addEvent(user);

        String message = addEventCommand.getMessage();

        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode commandJson = objectMapper.createObjectNode()
                .put("command", "addEvent")
                .put("user", getUsername())
                .put("timestamp", getTimestamp())
                .put("message", message);

        commandList.add(commandJson);
    }

    /**
     * This method executes the command AddMerch
     *
     */
    public void addMerchExecute() {
        if (!createdAddMerch) {
            addMerchCommand = new AddMerchCommand();
            createdAddMerch = true;
        }

        addMerchCommand.addMerch(user);

        String message = addMerchCommand.getMessage();

        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode commandJson = objectMapper.createObjectNode()
                .put("command", "addMerch")
                .put("user", getUsername())
                .put("timestamp", getTimestamp())
                .put("message", message);

        commandList.add(commandJson);
    }

    /**
     * This method executes the command Get All Users
     *
     */
    public void getAllUsersExecute(Library library) {
        if (!createdGetAllUsers) {
            getAllUsersCommand = new GetAllUsers();
            createdGetAllUsers = true;
        }

        getAllUsersCommand.setAllUsersNames(library);

        // Create an array node for the results
        ArrayNode resultsArray = JsonNodeFactory.instance.arrayNode();

        ArrayList<String> allUsers = getAllUsersCommand.getAllUsersNames();

        // Add the names of the online users to the results array
        for (String username : allUsers) {
            resultsArray.add(username);
        }

        // Create the command JSON structure
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode commandJson = objectMapper.createObjectNode()
                .put("command", "getAllUsers")
                .put("timestamp", getTimestamp())
                .set("result", resultsArray);

        // Add the commandJson to the commandList
        commandList.add(commandJson);
    }

    /**
     * This method executes the command Delete User
     *
     */
    public void deleteUserExecute(Library library) {
        if (!createdDeleteUser) {
            deleteUserCommand = new DeleteUser();
            createdDeleteUser = true;
        }

        // update the player for each user
        for(User user : OnlineUsers.getOnlineUsers()) {
            user.getPlayer().timestamp = timestamp;
            if(user.getPlayer().repeatState == 0) {
                user.getPlayer().setRemainingTime();
            } else if(user.getPlayer().repeatState == 1) {
                user.getPlayer().setRemainingTimeRepeat1();
            } else if(user.getPlayer().repeatState == 2) {
                user.getPlayer().setRemainingTimeRepeat2();
            }
        }

        deleteUserCommand.deleteUser(library);

        String message = deleteUserCommand.getMessage();

        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode commandJson = objectMapper.createObjectNode()
                .put("command", "deleteUser")
                .put("user", deleteUserCommand.getUsername())
                .put("timestamp", getTimestamp())
                .put("message", message);

        commandList.add(commandJson);
    }

    /**
     * This method executes the command AddPodcast
     *
     */
    public void addPodcastExecute(Library library) {
        if (!createdAddPodcast) {
            addPodcastCommand = new AddPodcastCommand();
            createdAddPodcast = true;
        }

        addPodcastCommand.addPodcast(user, library);

        String message = addPodcastCommand.getMessage();

        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode commandJson = objectMapper.createObjectNode()
                .put("command", "addPodcast")
                .put("user", getUsername())
                .put("timestamp", getTimestamp())
                .put("message", message);

        commandList.add(commandJson);
    }

    /**
     * This method executes the command AddAnnouncement
     * @param library the library
     */
    public void addAnnouncementExecute(Library library) {
        if (!createdAddAnnouncement) {
            addAnnouncementCommand = new AddAnnouncementCommand();
            createdAddAnnouncement = true;
        }

        addAnnouncementCommand.addAnnouncement(user, library);

        String message = addAnnouncementCommand.getMessage();

        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode commandJson = objectMapper.createObjectNode()
                .put("command", "addAnnouncement")
                .put("user", getUsername())
                .put("timestamp", getTimestamp())
                .put("message", message);

        commandList.add(commandJson);
    }

    /**
     * This method executes the command RemoveAnnouncement
     */
    public void removeAnnouncementExecute() {
        if (!createdRemoveAnnouncement) {
            removeAnnouncementCommand = new RemoveAnnouncementCommand();
            createdRemoveAnnouncement = true;
        }

        removeAnnouncementCommand.removeAnnouncement(user);

        String message = removeAnnouncementCommand.getMessage();

        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode commandJson = objectMapper.createObjectNode()
                .put("command", "removeAnnouncement")
                .put("user", getUsername())
                .put("timestamp", getTimestamp())
                .put("message", message);

        commandList.add(commandJson);
    }

    /**
     * This method executes the command ShowPodcasts
     * @param library the library
     */
    public void showPodcastsExecute(Library library) {
        if (!createdShowPodcasts) {
            showPodcastsCommand = new ShowPodcasts();
            createdShowPodcasts = true;
        }

        showPodcastsCommand.setPodcasts(library);

        ArrayList<Podcast> podcasts = showPodcastsCommand.getPodcasts();

        // Create an array node for the results
        ArrayNode resultsArray = JsonNodeFactory.instance.arrayNode();

        // the results array has the name of the podcast and the list of episodes
        for (Podcast podcast : podcasts) {
            ObjectNode podcastNode = JsonNodeFactory.instance.objectNode()
                    .put("name", podcast.getName());

            ArrayNode episodesArray = JsonNodeFactory.instance.arrayNode();
            for (EpisodeInput episode : podcast.getEpisodes()) {
                episodesArray.add(episode.getName());
            }

            podcastNode.set("episodes", episodesArray);
            resultsArray.add(podcastNode);
        }

        // Create the command JSON structure
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode commandJson = objectMapper.createObjectNode()
                .put("command", "showPodcasts")
                .put("user", showPodcastsCommand.getUsername())
                .put("timestamp", getTimestamp())
                .set("result", resultsArray);

        // Add the commandJson to the commandList
        commandList.add(commandJson);
    }

    /**
     * This method executes the command RemoveAlbum
     * @param library the library
     */
    public void removeAlbumExecute(Library library) {
        if (!createdRemoveAlbum) {
            removeAlbumCommand = new RemoveAlbumCommand();
            createdRemoveAlbum = true;
        }

        // update the player for each user
        for(User user : OnlineUsers.getOnlineUsers()) {
            user.getPlayer().timestamp = timestamp;
            if(user.getPlayer().repeatState == 0) {
                user.getPlayer().setRemainingTime();
            } else if(user.getPlayer().repeatState == 1) {
                user.getPlayer().setRemainingTimeRepeat1();
            } else if(user.getPlayer().repeatState == 2) {
                user.getPlayer().setRemainingTimeRepeat2();
            }
        }

        removeAlbumCommand.removeAlbum(user, library);

        String message = removeAlbumCommand.getMessage();

        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode commandJson = objectMapper.createObjectNode()
                .put("command", "removeAlbum")
                .put("user", getUsername())
                .put("timestamp", getTimestamp())
                .put("message", message);

        commandList.add(commandJson);
    }

    /**
     * This method executes the command Change Page
     */
    public void changePageExecute() {
        if (!createdChangeCurrentPage) {
            changeCurrentPageCommand = new ChangePageCommand();
            createdChangeCurrentPage = true;
        }

        changeCurrentPageCommand.changePage(user);

        String message = changeCurrentPageCommand.getMessage();

        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode commandJson = objectMapper.createObjectNode()
                .put("command", "changePage")
                .put("user", getUsername())
                .put("timestamp", getTimestamp())
                .put("message", message);

        commandList.add(commandJson);
    }

    /**
     * This method executes the command Remove Podcast
     */
    public void removePodcastExecute(Library library) {
        if (!createdRemovePodcast) {
            removePodcastCommand = new RemovePodcastCommand();
            createdRemovePodcast = true;
        }

        // update the player for each user
        for(User user : OnlineUsers.getOnlineUsers()) {
            user.getPlayer().timestamp = timestamp;
            if(user.getPlayer().repeatState == 0) {
                user.getPlayer().setRemainingTime();
            } else if(user.getPlayer().repeatState == 1) {
                user.getPlayer().setRemainingTimeRepeat1();
            } else if(user.getPlayer().repeatState == 2) {
                user.getPlayer().setRemainingTimeRepeat2();
            }
        }

        removePodcastCommand.removePodcast(user, library);

        String message = removePodcastCommand.getMessage();

        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode commandJson = objectMapper.createObjectNode()
                .put("command", "removePodcast")
                .put("user", getUsername())
                .put("timestamp", getTimestamp())
                .put("message", message);

        commandList.add(commandJson);
    }

    /**
     * This method executes the command Remove Event
     */
    public void removeEventExecute() {
        if (!createdRemoveEvent) {
            removeEventCommand = new RemoveEventCommand();
            createdRemoveEvent = true;
        }

        removeEventCommand.removeEvent(user);

        String message = removeEventCommand.getMessage();

        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode commandJson = objectMapper.createObjectNode()
                .put("command", "removeEvent")
                .put("user", getUsername())
                .put("timestamp", getTimestamp())
                .put("message", message);

        commandList.add(commandJson);
    }

    /**
     * This method executes the command Get Top 5 Artists
     */
    public void getTop5ArtistsExecute(Library library) {
        if (!createdGetTop5Artists) {
            getTop5ArtistsCommand = new GetTop5Artists();
            createdGetTop5Artists = true;
        }

        getTop5ArtistsCommand.setTop5Artists(library);

        ArrayList<Artist> artists = getTop5ArtistsCommand.getTop5Artists();

        // Create an array node for the results
        ArrayNode resultsArray = JsonNodeFactory.instance.arrayNode();

        // Add the names of the artists
        for (Artist artist : artists) {
            resultsArray.add(artist.getUsername());
        }

        // Create the command JSON structure
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode commandJson = objectMapper.createObjectNode()
                .put("command", "getTop5Artists")
                .put("timestamp", getTimestamp())
                .set("result", resultsArray);

        // Add the commandJson to the commandList
        commandList.add(commandJson);
    }

    /**
     * This method executes the command Get Top 5 Albums
     */
    public void getTop5AlbumsExecute() {
        if (!createdGetTop5Albums) {
            getTop5AlbumsCommand = new GetTop5Albums();
            createdGetTop5Albums = true;
        }

        getTop5AlbumsCommand.setTop5Albums();

        ArrayList<Album> albums = getTop5AlbumsCommand.getTop5Albums();

        // Create an array node for the results
        ArrayNode resultsArray = JsonNodeFactory.instance.arrayNode();

        // Add the names of the online users to the results array
        for (Album album : albums) {
            resultsArray.add(album.getName());
        }

        // Create the command JSON structure
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode commandJson = objectMapper.createObjectNode()
                .put("command", "getTop5Albums")
                .put("timestamp", getTimestamp())
                .set("result", resultsArray);

        // Add the commandJson to the commandList
        commandList.add(commandJson);
    }
}
