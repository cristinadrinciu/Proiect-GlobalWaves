package main;

import AudioFiles.*;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;

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
        if (!createdSearch) {
            searchCommand = new SearchCommand();
            createdSearch = true;
        }
        this.searchCommand.setType(type);
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
    private SelectCommand selectCommand;

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
    private PlayPauseCommand playPauseCommand;

    // Status Command

    private boolean createdStatus = false;
    private StatusCommand statusCommand;

    // CreatePlaylist Command

    private boolean createdCreatePlaylist = false;
    private CreatePlaylistCommand createPlaylistCommand;

    /**
     * This method sets the playlist name for the create playlist command
     * @param playlistName the playlist name for the create playlist command
     */
    public void setPlaylistName(final String playlistName) {
        if (!createdCreatePlaylist) {
            createPlaylistCommand = new CreatePlaylistCommand();
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
                switchVisibilityCommand = new SwitchVisibilityCommand();
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
    private RepeatCommand repeatCommand;

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
    private SwitchVisibilityCommand switchVisibilityCommand;

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
    private ForwardCommand forwardCommand;

    // Backward Command
    private boolean createdBackward = false;
    private BackwardCommand backwardCommand;

    // Get Top 5 Playlists Command
    private boolean createdGetTop5Playlists = false;
    private GetTop5Playlists getTop5PlaylistsCommand;

    // Get Top 5 Songs Command
    private boolean createdGetTop5Songs = false;
    private GetTop5Songs getTop5SongsCommand;

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
        SearchCommand.setSearchResults(searchCommand.getSearchResults(library, user));

        // initialize the player or get out the track that is there
        if (user.getPlayer().repeatState == 0) {
            user.getPlayer().setRemainingTime();
        } else if (user.getPlayer().repeatState == 1) {
            user.getPlayer().setRemainingTimeRepeat1();
        } else if (user.getPlayer().repeatState == 2) {
            user.getPlayer().setRemainingTimeRepeat2();
        }

        if(SearchCommand.getSearchResults() != null)
            user.setLastSearch(SearchCommand.getSearchResults());

        user.getPlayer().initializePlayer();

        ArrayNode results = JsonNodeFactory.instance.arrayNode();

        for (AudioFile audioFile : SearchCommand.getSearchResults()) {
            results.add(audioFile.getName());
        }

        ObjectNode commandJson = objectMapper.createObjectNode()
                .put("command", "search")
                .put("user", getUsername())
                .put("timestamp", getTimestamp())
                .put("message", "Search returned "
                        + SearchCommand.getSearchResults().size() + " results")
                .set("results", results);

        commandList.add(commandJson);
    }

    /**
     * This method executes the command
     */
    public void selectExecute() {
        String message = null;
        if (selectCommand != null) {
            //SelectCommand.selectedItem = selectCommand.getSelectedItem();
            selectCommand.provideSelectedItem(user);

            if (SelectCommand.getSelectedItem() == null) {
                if (user.getLastSearch() == null) {
                    message = "Please conduct a search before making a selection.";
                } else if (selectCommand.getItemNumber()
                        > SearchCommand.getSearchResults().size()) {
                    message = "The selected ID is too high.";
                }
            } else {
                message = "Successfully selected "
                        + SelectCommand.getSelectedItem().getName() + ".";
                SearchCommand.setSearchResults(null);
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

        String message = loadCommand.buildMessage(SelectCommand.getSelectedItem());
        if (LoadCommand.getLoadedItem() != null) {
            user.getPlayer().loadedItem = LoadCommand.getLoadedItem();
            user.getPlayer().setPlayingNow();
            user.setLastSearch(null);
        }
        SelectCommand.setSelectedItem(null);
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
            playPauseCommand = new PlayPauseCommand();
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
            statusCommand = new StatusCommand();
            createdStatus = true;
        }

        if (user.getPlayer().repeatState == 0) {
            user.getPlayer().setRemainingTime();
        } else if (user.getPlayer().repeatState == 1) {
            user.getPlayer().setRemainingTimeRepeat1();
        } else if (user.getPlayer().repeatState == 2) {
            user.getPlayer().setRemainingTimeRepeat2();
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
            showPreferredSongsCommand = new ShowPreferredSongsCommand();
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
            repeatCommand = new RepeatCommand();
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
                user.getPlayer().shuffledPlaylist = shuffleCommand.
                        shufflePlaylist((Playlist) user.getPlayer().loadedItem);
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

        followCommand.followPlaylist(user, SelectCommand.getSelectedItem());

        String message = followCommand.getMessage();

        if (message.equals("Playlist unfollowed successfully.")
            || message.equals("Playlist followed successfully.")) {
            SelectCommand.setSelectedItem(null);
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
            forwardCommand = new ForwardCommand();
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
            getTop5PlaylistsCommand = new GetTop5Playlists();
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
            getTop5SongsCommand = new GetTop5Songs();
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
}

