package visit.pattern;

import audio.files.Library;
import audio.files.Album;
import audio.files.AudioFile;
import audio.files.Playlist;
import audio.files.Podcast;
import audio.files.Song;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import commands.*;
import fileio.input.EpisodeInput;
import main.InputCommands;
import platform.data.OnlineUsers;
import user.types.Artist;
import user.types.Host;
import user.types.User;

import java.util.ArrayList;

public class CommandExecute implements Visitor {
    /**
     * This method is used to execute the search command received from the client.
     * @param command the command received from the client
     * @param library the library of the application
     * @param searchCommand the search command
     */
    @Override
    public void visit(final InputCommands command, final SearchCommand searchCommand,
                      final Library library) {
        ObjectMapper objectMapper = new ObjectMapper(); // Instantiate ObjectMapper here
        User user = command.getUser();
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

        if (searchCommand.getType().equals("song")
                || searchCommand.getType().equals("podcast")
                || searchCommand.getType().equals("playlist")
                || searchCommand.getType().equals("album")) {
            SearchCommand.setSearchResults(searchCommand.getSearchResults(library, user));
            if (SearchCommand.getSearchResults() != null) {
                user.setLastSearch(SearchCommand.getSearchResults());
                for (AudioFile audioFile : SearchCommand.getSearchResults()) {
                    results.add(audioFile.getName());
                }
                size = SearchCommand.getSearchResults().size();
                // clear the user search
                user.setLastSearchUsers(null);
            }
        } else if (searchCommand.getType().equals("artist")
                || searchCommand.getType().equals("host")) {
            SearchCommand.setSearchUsers(searchCommand.getSearchUsers(library, user));
            if (SearchCommand.getSearchUsers() != null) {
                user.setLastSearchUsers(SearchCommand.getSearchUsers());
                for (User user1 : SearchCommand.getSearchUsers()) {
                    results.add(user1.getUsername());
                }
                size = SearchCommand.getSearchUsers().size();

                // clear the audiofile search
                user.setLastSearch(null);
            }
        }
        ObjectNode commandJson = objectMapper.createObjectNode()
                .put("command", "search")
                .put("user", command.getUsername())
                .put("timestamp", command.getTimestamp())
                .put("message", "Search returned "
                        + size + " results")
                .set("results", results);

        command.getCommandList().add(commandJson);
    }

    /**
     * This method is used to execute the addAlbum command received from the client.
     * @param command the command received from the client
     * @param addAlbumCommand the addAlbum command
     * @param library the library of the application
     */
    @Override
    public void visit(final InputCommands command, final AddAlbumCommand addAlbumCommand,
                      final Library library) {
        User user = command.getUser();
        addAlbumCommand.addAlbum(user, library);

        String message = addAlbumCommand.getMessage();

        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode commandJson = objectMapper.createObjectNode()
                .put("command", "addAlbum")
                .put("user", command.getUsername())
                .put("timestamp", command.getTimestamp())
                .put("message", message);

        command.getCommandList().add(commandJson);
    }

    /**
     * This method is used to execute the addAnnouncement command received from the client.
     * @param command the command received from the client
     * @param addAnnouncementCommand the addAnnouncement command
     * @param library the library of the application
     */
    @Override
    public void visit(final InputCommands command,
                      final AddAnnouncementCommand addAnnouncementCommand, final Library library) {
        User user = command.getUser();
        addAnnouncementCommand.addAnnouncement(user, library);

        String message = addAnnouncementCommand.getMessage();

        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode commandJson = objectMapper.createObjectNode()
                .put("command", "addAnnouncement")
                .put("user", command.getUsername())
                .put("timestamp", command.getTimestamp())
                .put("message", message);

        command.getCommandList().add(commandJson);
    }

    /**
     * This method is used to execute the addEvent command received from the client.
     * @param command the command received from the client
     * @param addEventCommand the addEvent command
     * @param library the library of the application
     */
    @Override
    public void visit(final InputCommands command,
                      final AddEventCommand addEventCommand, final Library library) {
        User user = command.getUser();
        addEventCommand.addEvent(user);

        String message = addEventCommand.getMessage();

        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode commandJson = objectMapper.createObjectNode()
                .put("command", "addEvent")
                .put("user", command.getUsername())
                .put("timestamp", command.getTimestamp())
                .put("message", message);

        command.getCommandList().add(commandJson);
    }

    /**
     * This method is used to execute the addMerch command received from the client.
     * @param command the command received from the client
     * @param addMerchCommand the addMerch command
     * @param library the library of the application
     */
    @Override
    public void visit(final InputCommands command,
                      final AddMerchCommand addMerchCommand, final Library library) {
        User user = command.getUser();
        addMerchCommand.addMerch(user);

        String message = addMerchCommand.getMessage();

        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode commandJson = objectMapper.createObjectNode()
                .put("command", "addMerch")
                .put("user", command.getUsername())
                .put("timestamp", command.getTimestamp())
                .put("message", message);

        command.getCommandList().add(commandJson);
    }

    /**
     * This method is used to execute the addPodcast command received from the client.
     * @param command the command received from the client
     * @param addPodcastCommand the addPodcast command
     * @param library the library of the application
     */
    @Override
    public void visit(final InputCommands command,
                      final AddPodcastCommand addPodcastCommand, final Library library) {
        User user = command.getUser();
        addPodcastCommand.addPodcast(user, library);

        String message = addPodcastCommand.getMessage();

        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode commandJson = objectMapper.createObjectNode()
                .put("command", "addPodcast")
                .put("user", command.getUsername())
                .put("timestamp", command.getTimestamp())
                .put("message", message);

        command.getCommandList().add(commandJson);
    }

    /**
     * This method is used to execute the addRemove command received from the client.
     * @param command the command received from the client
     * @param addRemoveCommand the addRemove command
     * @param library the library of the application
     */
    @Override
    public void visit(final InputCommands command,
                      final AddRemoveCommand addRemoveCommand, final Library library) {
        User user = command.getUser();
        String message = addRemoveCommand.addRemoveMessage(user);

        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode commandJson = objectMapper.createObjectNode()
                .put("command", "addRemoveInPlaylist")
                .put("user", command.getUsername())
                .put("timestamp", command.getTimestamp())
                .put("message", message);

        command.getCommandList().add(commandJson);
    }

    /**
     * This method is used to execute the addUser command received from the client.
     * @param command the command received from the client
     * @param addUserCommand the addUser command
     * @param library the library of the application
     */
    @Override
    public void visit(final InputCommands command,
                      final AddUserCommand addUserCommand, final Library library) {
        addUserCommand.addUser(library);

        String message = addUserCommand.getMessage();

        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode commandJson = objectMapper.createObjectNode()
                .put("command", "addUser")
                .put("user", addUserCommand.getUsername())
                .put("timestamp", command.getTimestamp())
                .put("message", message);

        command.getCommandList().add(commandJson);
    }

    /**
     * This method is used to execute the backward command received from the client.
     * @param command the command received from the client
     * @param backwardCommand the backward command
     * @param library the library of the application
     */
    @Override
    public void visit(final InputCommands command,
                      final BackwardCommand backwardCommand, final Library library) {
        User user = command.getUser();
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
                .put("user", command.getUsername())
                .put("timestamp", command.getTimestamp())
                .put("message", message);

        command.getCommandList().add(commandJson);
    }

    /**
     * This method is used to execute the changePassword command received from the client.
     * @param command the command received from the client
     * @param changePageCommand the changePage command
     * @param library the library of the application
     */
    @Override
    public void visit(final InputCommands command,
                      final ChangePageCommand changePageCommand, final Library library) {
        User user = command.getUser();
        changePageCommand.changePage(user);

        String message = changePageCommand.getMessage();

        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode commandJson = objectMapper.createObjectNode()
                .put("command", "changePage")
                .put("user", command.getUsername())
                .put("timestamp", command.getTimestamp())
                .put("message", message);

        command.getCommandList().add(commandJson);
    }

    /**
     * This method is used to execute the createPlaylist command received from the client.
     * @param command the command received from the client
     * @param createPlaylistCommand the createPlaylist command
     * @param library the library of the application
     */
    @Override
    public void visit(final InputCommands command,
                      final CreatePlaylistCommand createPlaylistCommand, final Library library) {
        User user = command.getUser();
        String message = createPlaylistCommand.message(user, library);

        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode commandJson = objectMapper.createObjectNode()
                .put("command", "createPlaylist")
                .put("user", command.getUsername())
                .put("timestamp", command.getTimestamp())
                .put("message", message);

        command.getCommandList().add(commandJson);

    }

    /**
     * This method is used to execute the deleteUser command received from the client.
     * @param command the command received from the client
     * @param deleteUserCommand the deleteUser command
     * @param library the library of the application
     */
    @Override
    public void visit(final InputCommands command,
                      final DeleteUser deleteUserCommand, final Library library) {
        // update the player for each user
        for (User user1 : OnlineUsers.getOnlineUsers()) {
            user1.getPlayer().timestamp = command.getTimestamp();
            if (user1.getPlayer().repeatState == 0) {
                user1.getPlayer().setRemainingTime();
            } else if (user1.getPlayer().repeatState == 1) {
                user1.getPlayer().setRemainingTimeRepeat1();
            } else if (user1.getPlayer().repeatState == 2) {
                user1.getPlayer().setRemainingTimeRepeat2();
            }
        }

        deleteUserCommand.deleteUser(library);

        String message = deleteUserCommand.getMessage();

        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode commandJson = objectMapper.createObjectNode()
                .put("command", "deleteUser")
                .put("user", deleteUserCommand.getUsername())
                .put("timestamp", command.getTimestamp())
                .put("message", message);

        command.getCommandList().add(commandJson);
    }

    /**
     * This method is used to execute the follow command received from the client.
     * @param command the command received from the client
     * @param followCommand the follow command
     * @param library the library of the application
     */
    @Override
    public void visit(final InputCommands command,
                      final FollowCommand followCommand, final Library library) {
        User user = command.getUser();
        followCommand.followPlaylist(user);

        String message = followCommand.getMessage();

        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode commandJson = objectMapper.createObjectNode()
                .put("command", "follow")
                .put("user", command.getUsername())
                .put("timestamp", command.getTimestamp())
                .put("message", message);

        command.getCommandList().add(commandJson);
    }

    /**
     * This method is used to execute the forward command received from the client.
     * @param command the command received from the client
     * @param forwardCommand the forward command
     * @param library the library of the application
     */
    @Override
    public void visit(final InputCommands command,
                      final ForwardCommand forwardCommand, final Library library) {
        User user = command.getUser();
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
                .put("user", command.getUsername())
                .put("timestamp", command.getTimestamp())
                .put("message", message);

        command.getCommandList().add(commandJson);

    }

    /**
     * This method is used to execute the getAllUsers command received from the client.
     * @param command the command received from the client
     * @param getAllUsersCommand the getAllUsers command
     * @param library the library of the application
     */
    @Override
    public void visit(final InputCommands command,
                      final GetAllUsers getAllUsersCommand, final Library library) {
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
                .put("timestamp", command.getTimestamp())
                .set("result", resultsArray);

        // Add the commandJson to the commandList
        command.getCommandList().add(commandJson);
    }

    /**
     * This method is used to execute the getOnlineUsers command received from the client.
     * @param command the command received from the client
     * @param getOnlineUsersCommand the getOnlineUsers command
     * @param library the library of the application
     */
    @Override
    public void visit(final InputCommands command,
                      final GetOnlineUsers getOnlineUsersCommand, final Library library) {
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
                .put("timestamp", command.getTimestamp())
                .set("result", resultsArray);

        // Add the commandJson to the commandList
        command.getCommandList().add(commandJson);
    }

    /**
     * This method is used to execute the getTop5Albums command received from the client.
     * @param command the command received from the client
     * @param getTop5AlbumsCommand the getTop5Albums command
     * @param library the library of the application
     */
    @Override
    public void visit(final InputCommands command,
                      final GetTop5Albums getTop5AlbumsCommand, final Library library) {
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
                .put("timestamp", command.getTimestamp())
                .set("result", resultsArray);

        // Add the commandJson to the commandList
        command.getCommandList().add(commandJson);
    }

    /**
     * This method is used to execute the getTop5Artists command received from the client.
     * @param command the command received from the client
     * @param getTop5ArtistsCommand the getTop5Artists command
     * @param library the library of the application
     */
    @Override
    public void visit(final InputCommands command,
                      final GetTop5Artists getTop5ArtistsCommand, final Library library) {
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
                .put("timestamp", command.getTimestamp())
                .set("result", resultsArray);

        // Add the commandJson to the commandList
        command.getCommandList().add(commandJson);
    }

    /**
     * This method is used to execute the getTop5Playlists command received from the client.
     * @param command the command received from the client
     * @param getTop5PlaylistsCommand the getTop5Playlists command
     * @param library the library of the application
     */
    @Override
    public void visit(final InputCommands command,
                      final GetTop5Playlists getTop5PlaylistsCommand, final Library library) {
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
                .put("timestamp", command.getTimestamp())
                .set("result", resultsArray);

        // Add the commandJson to the commandList
        command.getCommandList().add(commandJson);
    }

    /**
     * This method is used to execute the getTop5Songs command received from the client.
     * @param command the command received from the client
     * @param getTop5SongsCommand the getTop5Songs command
     * @param library the library of the application
     */
    @Override
    public void visit(final InputCommands command,
                      final GetTop5Songs getTop5SongsCommand, final Library library) {
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
                .put("timestamp", command.getTimestamp())
                .set("result", resultsArray);

        // Add the commandJson to the commandList
        command.getCommandList().add(commandJson);
    }

    /**
     * This method is used to execute the like command received from the client.
     * @param command the command received from the client
     * @param likeCommand the like command
     * @param library the library of the application
     */
    @Override
    public void visit(final InputCommands command,
                      final LikeCommand likeCommand, final Library library) {
        User user = command.getUser();
        user.getPlayer().timestamp = command.getTimestamp();
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
                .put("user", command.getUsername())
                .put("timestamp", command.getTimestamp())
                .put("message", likeCommand.getMessage());

        command.getCommandList().add(commandJson);
    }

    /**
     * This method is used to execute the load command received from the client.
     * @param command the command received from the client
     * @param loadCommand the load command
     * @param library the library of the application
     */
    @Override
    public void visit(final InputCommands command,
                        final LoadCommand loadCommand, final Library library) {
        User user = command.getUser();
        String message = loadCommand.buildMessage(user.getSelectedItem());
        if (LoadCommand.getLoadedItem() != null) {
            user.getPlayer().loadedItem = LoadCommand.getLoadedItem();
            user.getPlayer().setPlayingNow();
            user.getPlayer().shuffle = false;
            user.getPlayer().repeatState = 0;
        }
        user.setSelectedItem(null);
        LoadCommand.setLoadedItem(null);

        // update the listens
        if(user.getPlayer().loadedItem != null) {
            if(user.getPlayer().loadedItem instanceof Song
                || user.getPlayer().loadedItem instanceof Playlist
                || user.getPlayer().loadedItem instanceof Album) {
                // update the listens of the song
                user.setListensToSong(user.getPlayer().playingNow.getName());
                user.setListensToArtist(((Song) user.getPlayer().playingNow).getArtist());
                user.setListensToGenre(((Song) user.getPlayer().playingNow).getGenre());
                user.setListensToAlbum(((Song) user.getPlayer().playingNow).getAlbum());

                // update the artist statistics
                Artist artist = user.getPlayer().findArtist((Song) user.getPlayer().playingNow);
                if (artist != null) {
                    artist.setListensToSong(user.getPlayer().playingNow.getName());
                    artist.setListensToAlbum(((Song) user.getPlayer().playingNow).getAlbum());
                    artist.setListensToFan(user.getUsername());
                }
            }
            if(user.getPlayer().loadedItem instanceof Podcast) {
                // update the listens of the podcast
                user.setListensToPodcast(user.getPlayer().loadedItem.getName());
            }
        }

        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode commandJson = objectMapper.createObjectNode()
                .put("command", "load")
                .put("user", command.getUsername())
                .put("timestamp", command.getTimestamp())
                .put("message", message);

        command.getCommandList().add(commandJson);
    }

    /**
     * This method is used to execute the next command received from the client.
     * @param command the command received from the client
     * @param nextCommand the next command
     * @param library the library of the application
     */
    @Override
    public void visit(final InputCommands command,
                      final NextCommand nextCommand, final Library library) {
        User user = command.getUser();
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
                .put("user", command.getUsername())
                .put("timestamp", command.getTimestamp())
                .put("message", message);

        command.getCommandList().add(commandJson);
    }

    /**
     * This method is used to execute the playPause command received from the client.
     * @param command the command received from the client
     * @param playPauseCommand the play command
     * @param library the library of the application
     */
    @Override
    public void visit(final InputCommands command,
                      final PlayPauseCommand playPauseCommand, final Library library) {
        User user = command.getUser();

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
                user.getPlayer().switchedTime = command.getTimestamp();
            } else {
                // If currently paused, resume the playback
                user.getPlayer().paused = false;
                user.getPlayer().switchedTime = command.getTimestamp();
            }
        }

        // Build the message and generate the corresponding JSON representation
        String message = playPauseCommand.buildMessage(user.getPlayer());
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode commandJson = objectMapper.createObjectNode()
                .put("command", "playPause")
                .put("user", command.getUsername())
                .put("timestamp", command.getTimestamp())
                .put("message", message);

        command.getCommandList().add(commandJson);
    }

    /**
     * This method is used to execute the prev command received from the client.
     * @param command the command received from the client
     * @param prevCommand the prev command
     * @param library the library of the application
     */
    @Override
    public void visit(final InputCommands command,
                      final PrevCommand prevCommand, final Library library) {
        User user = command.getUser();
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
                .put("user", command.getUsername())
                .put("timestamp", command.getTimestamp())
                .put("message", message);

        command.getCommandList().add(commandJson);
    }

    /**
     * This method is used to execute the printPage command received from the client.
     * @param command the command received from the client
     * @param printCurrentPageCommand the print command
     * @param library the library of the application
     */
    @Override
    public void visit(final InputCommands command,
                      final PrintCurrentPageCommand printCurrentPageCommand,
                      final Library library) {
        User user = command.getUser();

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
                .put("timestamp", command.getTimestamp())
                .put("message", message);

        command.getCommandList().add(commandJson);
    }

    /**
     * This method is used to execute the removeAlbum command received from the client.
     * @param command the command received from the client
     * @param removeAlbumCommand the removeAlbum command
     * @param library the library of the application
     */
    @Override
    public void visit(final InputCommands command,
                      final RemoveAlbumCommand removeAlbumCommand, final Library library) {
        User user = command.getUser();

        // update the player for each user
        for (User user1 : OnlineUsers.getOnlineUsers()) {
            user1.getPlayer().timestamp = command.getTimestamp();
            if (user1.getPlayer().repeatState == 0) {
                user1.getPlayer().setRemainingTime();
            } else if (user1.getPlayer().repeatState == 1) {
                user1.getPlayer().setRemainingTimeRepeat1();
            } else if (user1.getPlayer().repeatState == 2) {
                user1.getPlayer().setRemainingTimeRepeat2();
            }
        }

        removeAlbumCommand.removeAlbum(user, library);

        String message = removeAlbumCommand.getMessage();

        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode commandJson = objectMapper.createObjectNode()
                .put("command", "removeAlbum")
                .put("user", command.getUsername())
                .put("timestamp", command.getTimestamp())
                .put("message", message);

        command.getCommandList().add(commandJson);
    }

    /**
     * This method is used to execute the removeAnnouncement command received from the client.
     * @param command the command received from the client
     * @param removeAnnouncementCommand the removeAnnouncement command
     * @param library the library of the application
     */
    @Override
    public void visit(final InputCommands command,
                      final RemoveAnnouncementCommand removeAnnouncementCommand,
                      final Library library) {
        User user = command.getUser();
        removeAnnouncementCommand.removeAnnouncement(user);

        String message = removeAnnouncementCommand.getMessage();

        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode commandJson = objectMapper.createObjectNode()
                .put("command", "removeAnnouncement")
                .put("user", command.getUsername())
                .put("timestamp", command.getTimestamp())
                .put("message", message);

        command.getCommandList().add(commandJson);
    }

    /**
     * This method is used to execute the removeEvent command received from the client.
     * @param command the command received from the client
     * @param removeEventCommand the removeEvent command
     * @param library the library of the application
     */
    @Override
    public void visit(final InputCommands command,
                      final RemoveEventCommand removeEventCommand, final Library library) {
        User user = command.getUser();
        removeEventCommand.removeEvent(user);

        String message = removeEventCommand.getMessage();

        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode commandJson = objectMapper.createObjectNode()
                .put("command", "removeEvent")
                .put("user", command.getUsername())
                .put("timestamp", command.getTimestamp())
                .put("message", message);

        command.getCommandList().add(commandJson);
    }

    /**
     * This method is used to execute the removePodcast command received from the client.
     * @param command the command received from the client
     * @param removePodcastCommand the removePodcast command
     * @param library the library of the application
     */
    @Override
    public void visit(final InputCommands command,
                      final RemovePodcastCommand removePodcastCommand, final Library library) {
        User user = command.getUser();

        // update the player for each user
        for (User user1 : OnlineUsers.getOnlineUsers()) {
            user1.getPlayer().timestamp = command.getTimestamp();
            if (user1.getPlayer().repeatState == 0) {
                user1.getPlayer().setRemainingTime();
            } else if (user1.getPlayer().repeatState == 1) {
                user1.getPlayer().setRemainingTimeRepeat1();
            } else if (user1.getPlayer().repeatState == 2) {
                user1.getPlayer().setRemainingTimeRepeat2();
            }
        }

        removePodcastCommand.removePodcast(user, library);

        String message = removePodcastCommand.getMessage();

        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode commandJson = objectMapper.createObjectNode()
                .put("command", "removePodcast")
                .put("user", command.getUsername())
                .put("timestamp", command.getTimestamp())
                .put("message", message);
        command.getCommandList().add(commandJson);
    }

    /**
     * This method is used to execute the repeat command received from the client.
     * @param command the command received from the client
     * @param repeatCommand the repeat command
     * @param library the library of the application
     */
    @Override
    public void visit(final InputCommands command,
                      final RepeatCommand repeatCommand, final Library library) {
        User user = command.getUser();
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
                .put("user", command.getUsername())
                .put("timestamp", command.getTimestamp())
                .put("message", message);

        command.getCommandList().add(commandJson);
    }

    /**
     * This method is used to execute the select command received from the client.
     * @param command the command received from the client
     * @param selectCommand the select command
     * @param library the library of the application
     */
    @Override
    public void visit(final InputCommands command,
                      final SelectCommand selectCommand, final Library library) {
        User user = command.getUser();
        String message = null;
        if (selectCommand != null) {
            if (user.getLastSearch() == null && user.getLastSearchUsers() == null) {
                message = "Please conduct a search before making a selection.";
            } else if (user.getLastSearch() != null && user.getLastSearchUsers() == null) {
                // searched an audio file
                selectCommand.provideSelectedItem(user);
                if (SelectCommand.getSelectedItem() != null) {
                    user.setSelectedItem(SelectCommand.getSelectedItem());
                    message = "Successfully selected "
                            + SelectCommand.getSelectedItem().getName() + ".";
                    user.setLastSearch(null);
                    SelectCommand.setSelectedItem(null);
                } else {
                    if (selectCommand.getItemNumber() > user.getLastSearch().size()) {
                        message = "The selected ID is too high.";
                        SelectCommand.setSelectedItem(null);
                        user.setLastSearch(null);
                        user.setSelectedItem(null);
                    } else {
                        message = "Please conduct a search before making a selection.";
                    }
                }
            } else if (user.getLastSearchUsers() != null && user.getLastSearch() == null) {
                // searched a user
                selectCommand.provideSelectedUser(user);
                if (SelectCommand.getSelectedUser() != null) {
                    message = "Successfully selected "
                            + SelectCommand.getSelectedUser().getUsername() + "'s page.";
                    user.setLastSearchUsers(null);
                    // set the current page
                    if (SelectCommand.getSelectedUser() instanceof Artist) {
                        user.setCurrentPage(((Artist) SelectCommand.
                                getSelectedUser()).getArtistPage());
                    } else if (SelectCommand.getSelectedUser() instanceof Host) {
                        user.setCurrentPage(((Host) SelectCommand.getSelectedUser()).
                                getHostPage());
                    }
                } else {
                    if (selectCommand.getItemNumber() > user.getLastSearchUsers().size()) {
                        message = "The selected ID is too high.";
                    } else {
                        message = "Please conduct a search before making a selection.";
                    }
                }
            }
        }

        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode commandJson = objectMapper.createObjectNode()
                .put("command", "select")
                .put("user", command.getUsername())
                .put("timestamp", command.getTimestamp())
                .put("message", message);

        command.getCommandList().add(commandJson);
    }

    /**
     * This method is used to execute the showAlbums command received from the client.
     * @param command the command received from the client
     * @param showAlbumsCommand the showAlbums command
     * @param library the library of the application
     */
    @Override
    public void visit(final InputCommands command,
                      final ShowAlbums showAlbumsCommand, final Library library) {

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
                .put("timestamp", command.getTimestamp())
                .set("result", resultsArray);

        // Add the commandJson to the commandList
        command.getCommandList().add(commandJson);
    }

    /**
     * This method is used to execute the showPlaylists command received from the client.
     * @param command the command received from the client
     * @param showPlaylistsCommand the showPlaylists command
     * @param library the library of the application
     */
    @Override
    public void visit(final InputCommands command,
                      final ShowPlaylistsCommand showPlaylistsCommand, final Library library) {
        User user = command.getUser();
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
                .put("user", command.getUsername())
                .put("timestamp", command.getTimestamp())
                .set("result", resultsArray);

        command.getCommandList().add(commandJson);
    }

    /**
     * This method is used to execute the showPodcasts command received from the client.
     * @param command the command received from the client
     * @param showPodcastsCommand the showPodcasts command
     * @param library the library of the application
     */
    @Override
    public void visit(final InputCommands command,
                      final ShowPodcasts showPodcastsCommand, final Library library) {
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
                .put("timestamp", command.getTimestamp())
                .set("result", resultsArray);

        // Add the commandJson to the commandList
        command.getCommandList().add(commandJson);
    }

    /**
     * This method is used to execute the showPreferredSongs command received from the client.
     * @param command the command received from the client
     * @param showPreferredSongsCommand the showPreferredSongs command
     * @param library the library of the application
     */
    @Override
    public void visit(final InputCommands command,
                      final ShowPreferredSongsCommand showPreferredSongsCommand,
                      final Library library) {
        User user = command.getUser();
        showPreferredSongsCommand.setPreferredSongs(user);
        ArrayList<Song> songs = showPreferredSongsCommand.getPreferredSongs();

        ArrayNode results = JsonNodeFactory.instance.arrayNode();

        for (Song song : songs) {
            results.add(song.getName());
        }

        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode commandJson = objectMapper.createObjectNode()
                .put("command", "showPreferredSongs")
                .put("user", command.getUsername())
                .put("timestamp", command.getTimestamp())
                .set("result", results);

        command.getCommandList().add(commandJson);
    }

    /**
     * This method is used to execute the shuffle command received from the client.
     * @param command the command received from the client
     * @param shuffleCommand the shuffle command
     * @param library the library of the application
     */
    @Override
    public void visit(final InputCommands command,
                      final ShuffleCommand shuffleCommand, final Library library) {
        User user = command.getUser();

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
        if (user.getPlayer().loadedItem != null) {
            if (user.getPlayer().shuffle) {
                // Update the shuffled Playlist from player
                if (user.getPlayer().loadedItem instanceof Playlist) {
                    user.getPlayer().shuffledPlaylist = shuffleCommand.
                            shufflePlaylist((Playlist) user.getPlayer().loadedItem);
                }
                if (user.getPlayer().loadedItem instanceof Album) {
                    user.getPlayer().shuffledPlaylist = shuffleCommand.
                            shuffleAlbum((Album) user.getPlayer().loadedItem);
                }
                shuffleCommand.setSeed(0);
            }
        }

        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode commandJson = objectMapper.createObjectNode()
                .put("command", "shuffle")
                .put("user", command.getUsername())
                .put("timestamp", command.getTimestamp())
                .put("message", message);

        command.getCommandList().add(commandJson);
    }

    /**
     * This method is used to execute the status command received from the client.
     * @param command the command received from the client
     * @param statusCommand the status command
     * @param library the library of the application
     */
    @Override
    public void visit(final InputCommands command,
                      final StatusCommand statusCommand, final Library library) {
        User user = command.getUser();
        if (user.getStatusOnline()) {
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
                .put("user", command.getUsername())
                .put("timestamp", command.getTimestamp());

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
        command.getCommandList().add(commandJson);
    }

    /**
     * This method is used to execute the switchConnectionStatus command received from the client.
     * @param command the command received from the client
     * @param switchConnectionStatusCommand the switchConnectionStatus command
     * @param library the library of the application
     */
    @Override
    public void visit(final InputCommands command, final
                      SwitchConnectionStatusCommand switchConnectionStatusCommand,
                      final Library library) {
        User user = command.getUser();
        if (user.getStatusOnline()) {
            if (user.getPlayer().repeatState == 0) {
                user.getPlayer().setRemainingTime();
            } else if (user.getPlayer().repeatState == 1) {
                user.getPlayer().setRemainingTimeRepeat1();
            } else if (user.getPlayer().repeatState == 2) {
                user.getPlayer().setRemainingTimeRepeat2();
            }
        }

        switchConnectionStatusCommand.switchConnectionStatus(user, command.getTimestamp());

        String message = switchConnectionStatusCommand.getMessage();

        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode commandJson = objectMapper.createObjectNode()
                .put("command", "switchConnectionStatus")
                .put("user", command.getUsername())
                .put("timestamp", command.getTimestamp())
                .put("message", message);

        command.getCommandList().add(commandJson);
    }

    /**
     * This method is used to execute the switchVisibility command received from the client.
     * @param command the command received from the client
     * @param switchVisibilityCommand the switchVisibility command
     * @param library the library of the application
     */
    @Override
    public void visit(final InputCommands command,
                      final SwitchVisibilityCommand switchVisibilityCommand,
                      final Library library) {
        User user = command.getUser();
        switchVisibilityCommand.switchVisibility(user, library);
        String message = switchVisibilityCommand.message(user);

        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode commandJson = objectMapper.createObjectNode()
                .put("command", "switchVisibility")
                .put("user", command.getUsername())
                .put("timestamp", command.getTimestamp())
                .put("message", message);

        command.getCommandList().add(commandJson);
    }

    @Override
    public void visit(final InputCommands command,
                         final WrappedCommand wrappedCommand, final Library library) {
        User user = command.getUser();

        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode commandJson = objectMapper.createObjectNode()
                .put("command", "wrapped")
                .put("user", command.getUsername())
                .put("timestamp", command.getTimestamp());
        if (user.getType().equals("user")) {
            // update the player before the statistics
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

            wrappedCommand.setStatisticsUser(user);

            String message = wrappedCommand.getMessage();

            if (message != null) {
                commandJson.put("message", message);
            } else {
                ObjectNode result = objectMapper.createObjectNode();
                ObjectNode topArtists = objectMapper.createObjectNode();
                ObjectNode topGenres = objectMapper.createObjectNode();
                ObjectNode topSongs = objectMapper.createObjectNode();
                ObjectNode topAlbums = objectMapper.createObjectNode();
                ObjectNode topPodcasts = objectMapper.createObjectNode();

                ArrayList<String> topArtistsList = wrappedCommand.getTopArtists();
                ArrayList<String> topGenresList = wrappedCommand.getTopGenres();
                ArrayList<String> topSongsList = wrappedCommand.getTopSongs();
                ArrayList<String> topAlbumsList = wrappedCommand.getTopAlbums();
                ArrayList<String> topPodcastsList = wrappedCommand.getTopPodcasts();

                for (String artist : topArtistsList) {
                    topArtists.put(artist, user.getStatistics().getTopArtists().get(artist));
                }
                for (String genre : topGenresList) {
                    topGenres.put(genre, user.getStatistics().getTopGenres().get(genre));
                }
                for (String song : topSongsList) {
                    topSongs.put(song, user.getStatistics().getTopSongs().get(song));
                }
                for (String album : topAlbumsList) {
                    topAlbums.put(album, user.getStatistics().getTopAlbums().get(album));
                }
                for (String podcast : topPodcastsList) {
                    topPodcasts.put(podcast, user.getStatistics().getTopPodcasts().get(podcast));
                }

                result.set("topArtists", topArtists);
                result.set("topGenres", topGenres);
                result.set("topSongs", topSongs);
                result.set("topAlbums", topAlbums);
                result.set("topEpisodes", topPodcasts);

                commandJson.set("result", result);
            }
        } else if (user.getType().equals("artist")) {
            // update the player of each user in the library
            for (User user1 : OnlineUsers.getOnlineUsers()) {
                user1.getPlayer().timestamp = command.getTimestamp();
                if (user1.getPlayer().repeatState == 0) {
                    user1.getPlayer().setRemainingTime();
                } else if (user1.getPlayer().repeatState == 1) {
                    user1.getPlayer().setRemainingTimeRepeat1();
                } else if (user1.getPlayer().repeatState == 2) {
                    user1.getPlayer().setRemainingTimeRepeat2();
                }
            }

            wrappedCommand.setStatisticsArtist((Artist) user);

            String message = wrappedCommand.getMessage();

            if (message != null) {
                commandJson.put("message", message);
            } else {
                ObjectNode result = objectMapper.createObjectNode();
                ObjectNode topAlbums = objectMapper.createObjectNode();
                ObjectNode topSongs = objectMapper.createObjectNode();
                ArrayNode topFans = objectMapper.createArrayNode();

                ArrayList<String> topAlbumsList = wrappedCommand.getTopAlbums();
                ArrayList<String> topSongsList = wrappedCommand.getTopSongs();
                ArrayList<String> topFansList = wrappedCommand.getTopFans();

                for (String album : topAlbumsList) {
                    topAlbums.put(album, ((Artist) user).getArtistStatistics().getTopAlbums().get(album));
                }
                for (String song : topSongsList) {
                    topSongs.put(song, ((Artist) user).getArtistStatistics().getTopSongs().get(song));
                }
                for (String fan : topFansList) {
                    topFans.add(fan);
                }

                result.set("topAlbums", topAlbums);
                result.set("topSongs", topSongs);
                result.set("topFans", topFans);
                result.put("listeners", (wrappedCommand.getListeners()));

                commandJson.set("result", result);
            }
        }

        command.getCommandList().add(commandJson);
    }

    /**
     * This method is used to execute the buyPremium command received from the client.
     * @param command the command received from the client
     * @param buyPremiumCommand the stop comma
     * @param library the library of the application
     */
    @Override
    public void visit(final InputCommands command,
                      final BuyPremiumCommand buyPremiumCommand, final Library library) {
        User user = command.getUser();
        buyPremiumCommand.buyPremium(user);

        String message = buyPremiumCommand.getMessage();

        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode commandJson = objectMapper.createObjectNode()
                .put("command", "buyPremium")
                .put("user", command.getUsername())
                .put("timestamp", command.getTimestamp())
                .put("message", message);
        command.getCommandList().add(commandJson);
    }

    /**
     * This method is used to execute the createPlaylist command received from the client.
     * @param command the command received from the client
     * @param cancelPremiumCommand the stop comma
     * @param library the library of the application
     */
    @Override
    public void visit(final InputCommands command,
                      final CancelPremiumCommand cancelPremiumCommand, final Library library) {
        User user = command.getUser();
        cancelPremiumCommand.cancelPremium(user);

        String message = cancelPremiumCommand.getMessage();

        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode commandJson = objectMapper.createObjectNode()
                .put("command", "cancelPremium")
                .put("user", command.getUsername())
                .put("timestamp", command.getTimestamp())
                .put("message", message);
        command.getCommandList().add(commandJson);
    }
}
