package main;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.input.*;
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
    UserInput user;

    void getUser(LibraryInput library) {
        for(UserInput userContor : library.getUsers())
            if(userContor.getUsername().equals(username)) {
                user = userContor;
                break;
            }
    }

    // constructor
    public InputCommands() {
    }

    // Player

    // Search Command
    boolean createdSearch = false;
    public SearchCommand searchCommand;

    public void setType(String type) {
        if(!createdSearch) {
            searchCommand = new SearchCommand();
            createdSearch = true;
        }
        this.searchCommand.setType(type);
    }

    public void setFilters(Filter filters) {
        if(!createdSearch) {
            searchCommand = new SearchCommand();
            createdSearch = true;
        }
        this.searchCommand.setFilters(filters);
    }


    // Select Command
    boolean createdSelect = false;
    public SelectCommand selectCommand;

    public void setItemNumber(int itemNumber) {
        if(!createdSelect) {
            selectCommand = new SelectCommand();
            createdSelect = true;
        }
        this.selectCommand.setItemNumber(itemNumber);
    }

    // Load Command
    boolean createdLoad = false;
    public LoadCommand loadCommand;

    // PlayPause Command
    boolean createdPlayPause = false;
    public PlayPauseCommand playPauseCommand;

    // Status Command

    boolean createdStatus = false;
    public StatusCommand statusCommand;

    // CreatePlaylist Command

    boolean createdCreatePlaylist = false;
    public CreatePlaylistCommand createPlaylistCommand;

    public void setPlaylistName(String playlistName){
        if(!createdCreatePlaylist) {
            createPlaylistCommand = new CreatePlaylistCommand();
            createdCreatePlaylist = true;
        }
        this.createPlaylistCommand.setPlaylistName(playlistName);
    }

    // Add/Remove Command

    boolean createdAddRemove = false;
    public AddRemoveCommand addRemoveCommand;

    public void setPlaylistId(int playlistId) {
        if(command.equals("addRemoveInPlaylist")) {
            if (!createdAddRemove) {
                addRemoveCommand = new AddRemoveCommand();
                createdAddRemove = true;
            }
            this.addRemoveCommand.setPlaylistId(playlistId);
        }
        if(command.equals("switchVisibility")) {
            if(!createdSwitchVisibility) {
                switchVisibilityCommand = new SwitchVisibilityCommand();
                createdSwitchVisibility = true;
            }
            this.switchVisibilityCommand.setPlaylistId(playlistId);
        }
    }

    // Show Preferred Songs Command
    boolean createdShowPreferred = false;
    public ShowPreferredSongsCommand showPreferredSongsCommand;

    // Show Playlists Command
    boolean createdShowPlaylists = false;
    public ShowPlaylistsCommand showPlaylistsCommand;

    // Repeat Command
    boolean createdRepeat = false;
    public RepeatCommand repeatCommand;

    // Shuffle Command
    boolean createdShuffle = false;
    public ShuffleCommand shuffleCommand;
    public void setSeed(int seed) {
        if(!createdShuffle) {
            shuffleCommand = new ShuffleCommand();
            createdShuffle = true;
        }
        this.shuffleCommand.setSeed(seed);
    }

    // Switch Visibility
    boolean createdSwitchVisibility = false;
    public SwitchVisibilityCommand switchVisibilityCommand;

    // Follow Command
    boolean createdFollow = false;
    public FollowCommand followCommand;

    // setters and getters

    boolean createdLikeCommand = false;
    public LikeCommand likeCommand;

    public List<ObjectNode> getCommandList() {
        return commandList;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public void setTimestamp(int timestamp) {
        this.timestamp = timestamp;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getCommand(){
        return this.command;
    }

    public int getTimestamp() {
        return timestamp;
    }

    public String getUsername() {
        return username;
    }

    public void SearchExecute(LibraryInput library) {
        ObjectMapper objectMapper = new ObjectMapper(); // Instantiate ObjectMapper here
        SearchCommand.searchResults = searchCommand.getSearchResults(library, user);
        // initialize the player or get out the track that is there
        if(user.player.repeatState == 0)
            user.player.setRemainingTime();
        else if (user.player.repeatState == 1)
            user.player.setRemainingTimeRepeat1();
        else if (user.player.repeatState == 2)
            user.player.setRemainingTimeRepeat2();
        user.player.initializePlayer();

        ArrayNode results = JsonNodeFactory.instance.arrayNode();

        for (AudioFile audioFile : SearchCommand.searchResults) {
            results.add(audioFile.getName());
        }

        ObjectNode commandJson = objectMapper.createObjectNode()
                .put("command", "search")
                .put("user", getUsername())
                .put("timestamp", getTimestamp())
                .put("message", "Search returned " + SearchCommand.searchResults.size() + " results")
                .set("results", results);

        commandList.add(commandJson);
    }

    public void SelectExecute() {
        String message = null;
        if (selectCommand != null) {
            SelectCommand.selectedItem = selectCommand.getSelectedItem();

            if (SelectCommand.selectedItem == null) {
                if(SearchCommand.searchResults == null)
                    message = "Please conduct a search before making a selection.";
                else if(selectCommand.getItemNumber() > SearchCommand.searchResults.size())
                    message = "The selected ID is too high.";
            } else {
                message = "Successfully selected " + SelectCommand.selectedItem.getName() + ".";
                SearchCommand.searchResults = null;
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

    public void LoadExecute() {
        if(!createdLoad) {
            loadCommand = new LoadCommand();
            createdLoad = true;
        }

        String message = loadCommand.buildMessage(SelectCommand.selectedItem);
        if(LoadCommand.loadedItem != null) {
            user.player.loadedItem = LoadCommand.loadedItem;
            user.player.setPlayingNow();
        }
        SelectCommand.selectedItem = null;
        LoadCommand.loadedItem = null;

        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode commandJson = objectMapper.createObjectNode()
                .put("command", "load")
                .put("user", getUsername())
                .put("timestamp", getTimestamp())
                .put("message", message);

        commandList.add(commandJson);
    }

    public void PlayPauseExecute() {
        if (!createdPlayPause) {
            playPauseCommand = new PlayPauseCommand();
            createdPlayPause = true;
        }

        if (user.player.loadedItem != null) {
            // Calculate remaining time before making any state changes
            if(user.player.repeatState == 0)
                user.player.setRemainingTime();
            else if (user.player.repeatState == 1)
                user.player.setRemainingTimeRepeat1();
            else if (user.player.repeatState == 2)
                user.player.setRemainingTimeRepeat2();

            if (!user.player.paused) {
                // If currently playing, pause the playback
                user.player.paused = true;
                user.player.switchedTime = timestamp;
            } else {
                // If currently paused, resume the playback
                user.player.paused = false;
                user.player.switchedTime = timestamp;
            }
        }

        // Build the message and generate the corresponding JSON representation
        String message = playPauseCommand.buildMessage(user.player);
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode commandJson = objectMapper.createObjectNode()
                .put("command", "playPause")
                .put("user", getUsername())
                .put("timestamp", getTimestamp())
                .put("message", message);

        commandList.add(commandJson);
    }

    public void StatusExecute() {
        if(!createdStatus) {
            statusCommand = new StatusCommand();
            createdStatus = true;
        }

        if(user.player.repeatState == 0)
            user.player.setRemainingTime();
        else if (user.player.repeatState == 1)
            user.player.setRemainingTimeRepeat1();
        else if (user.player.repeatState == 2)
            user.player.setRemainingTimeRepeat2();

        ArrayList<Object> statusArray = statusCommand.buildStatusArray(user.player);

        // Create the status JSON structure
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode commandJson = objectMapper.createObjectNode()
                .put("command", "status")
                .put("user", getUsername())
                .put("timestamp", getTimestamp());

        // Create the "stats" object within the JSON structure
        ObjectNode statsNode = objectMapper.createObjectNode()
                .put("name", (String) statusArray.get(0))
                .put("remainedTime", (int) statusArray.get(1))
                .put("repeat", (String) statusArray.get(2))
                .put("shuffle", (boolean) statusArray.get(3))
                .put("paused", (boolean) statusArray.get(4));

        // Add the "stats" object to the main JSON structure
        commandJson.set("stats", statsNode);

        // Add the commandJson to the commandList
        commandList.add(commandJson);
    }

    public void CreatePlaylistExecute(LibraryInput library) {
        String message = createPlaylistCommand.message(user, library);

        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode commandJson = objectMapper.createObjectNode()
                .put("command", "createPlaylist")
                .put("user", getUsername())
                .put("timestamp", getTimestamp())
                .put("message", message);

        commandList.add(commandJson);
    }

    public void AddRemoveExecute() {
        String message = addRemoveCommand.addRemoveMessage(user);

        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode commandJson = objectMapper.createObjectNode()
                .put("command", "addRemoveInPlaylist")
                .put("user", getUsername())
                .put("timestamp", getTimestamp())
                .put("message", message);

        commandList.add(commandJson);
    }

    public void LikeExecute() {
        if(!createdLikeCommand) {
            likeCommand = new LikeCommand();
            createdLikeCommand = true;
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

    public void ShowPreferredSongsExecute() {
        if(!createdShowPreferred) {
            showPreferredSongsCommand = new ShowPreferredSongsCommand();
            createdShowPreferred = true;
        }
        showPreferredSongsCommand.setPreferredSongs(user);
        ArrayList<SongInput> songs = showPreferredSongsCommand.getPreferredSongs();

        ArrayNode results = JsonNodeFactory.instance.arrayNode();

        for (SongInput song : songs) {
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

    public void ShowPlaylistsExecute() {
        if(!createdShowPlaylists) {
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
            for (SongInput song : playlist.songs) {
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

    public void RepeatExecute() {
        if(!createdRepeat) {
            repeatCommand = new RepeatCommand();
            createdRepeat = true;
        }

        String message;
        if(user.player.loadedItem == null)
            message = "Please load a source before setting the repeat status.";
        else {
            if(user.player.repeatState == 0)
                user.player.setRemainingTime();
            if(user.player.repeatState == 1)
                user.player.setRemainingTimeRepeat1();
            if(user.player.repeatState == 2)
                user.player.setRemainingTimeRepeat2();
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

    public void ShuffleExecute() {
        // Initialize shuffleCommand if not already created
        if (!createdShuffle) {
            shuffleCommand = new ShuffleCommand();
            createdShuffle = true;
        }

        if(user.player.repeatState == 0)
            user.player.setRemainingTime();
        if(user.player.repeatState == 1)
            user.player.setRemainingTimeRepeat1();
        if(user.player.repeatState == 2)
            user.player.setRemainingTimeRepeat2();

        shuffleCommand.shufflePlayer(user);
        String message = shuffleCommand.message(user);

        // Check if shuffleCommand is not null before accessing its methods
        if (shuffleCommand != null && user.player.loadedItem != null) {
            if (user.player.shuffle) {
                // Update the shuffled Playlist from player
                user.player.shuffledPlaylist = shuffleCommand.shufflePlaylist((Playlist) user.player.loadedItem);
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

    public void SwitchVisibilityExecute(LibraryInput library) {
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

    public void FollowExecute() {
        if(!createdFollow) {
            followCommand = new FollowCommand();
            createdFollow = true;
        }

        followCommand.followPlaylist(user, SelectCommand.selectedItem);

        String message = followCommand.message;

        if(message.equals("Playlist unfollowed successfully.") ||
            message.equals("Playlist followed successfully."))
            SelectCommand.selectedItem = null;

        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode commandJson = objectMapper.createObjectNode()
                .put("command", "follow")
                .put("user", getUsername())
                .put("timestamp", getTimestamp())
                .put("message", message);

        commandList.add(commandJson);
    }
}

