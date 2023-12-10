package main;

import AudioFiles.Library;
import AudioFiles.User;
import checker.Checker;
import checker.CheckerConstants;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.node.ArrayNode;
import fileio.input.LibraryInput;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.util.ArrayList;
import java.util.List;


import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

/**
 * The entry point to this homework. It runs the checker that tests your implentation.
 */
public final class Main {
    static final String LIBRARY_PATH = CheckerConstants.TESTS_PATH + "library/library.json";

    /**
     * for coding style
     */

    private Main() {
    }

    /**
     * DO NOT MODIFY MAIN METHOD
     * Call the checker
     * @param args from command line
     * @throws IOException in case of exceptions to reading / writing
     */
    public static void main(final String[] args) throws IOException {
        File directory = new File(CheckerConstants.TESTS_PATH);
        Path path = Paths.get(CheckerConstants.RESULT_PATH);

        if (Files.exists(path)) {
            File resultFile = new File(String.valueOf(path));
            for (File file : Objects.requireNonNull(resultFile.listFiles())) {
                file.delete();
            }
            resultFile.delete();
        }
        Files.createDirectories(path);

        for (File file : Objects.requireNonNull(directory.listFiles())) {
            if (file.getName().startsWith("library")) {
                continue;
            }

            String filepath = CheckerConstants.OUT_PATH + file.getName();
            File out = new File(filepath);
            boolean isCreated = out.createNewFile();
            if (isCreated) {
                action(file.getName(), filepath);
            }
        }

        Checker.calculateScore();
    }

    /**
     * @param filePathInput for input file
     * @param filePathOutput for output file
     * @throws IOException in case of exceptions to reading / writing
     */
    public static void action(final String filePathInput,
                              final String filePathOutput)
            throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        LibraryInput library = objectMapper.readValue(new File(
                LIBRARY_PATH), LibraryInput.class);

        ArrayNode outputs = objectMapper.createArrayNode();

        // TODO add your implementation
        List<ObjectNode> commandList = new ArrayList<>();
        OnlineUsers.getOnlineUsers().clear();

        // create the new library
        Library newLibrary = new Library();
        newLibrary.createLibrary(library);

        PublicAlbums.getPublicAlbums().clear();

        // add the users to the online users list
        OnlineUsers.setOnlineUsers(new ArrayList<>());
        OnlineUsers.getOnlineUsers().addAll(newLibrary.getUsers());

        PublicPlaylists.getPlaylists().clear();
        InputCommands[] commands = objectMapper.readValue(new File("input/" + filePathInput), InputCommands[].class);
        for (User user : newLibrary.getUsers()) {
            user.getLikedSongs().clear();
            user.getPlaylists().clear();
        }

        for (int i = 0; i < commands.length; i++) {
            if (!commands[i].getCommand().equals("addUser") && !commands[i].getCommand().equals("deleteUser")
                    && commands[i].getUsername() != null) {
                commands[i].getUser(newLibrary);
                if(commands[i].getUser() == null) {
                    // create the output for the command
                    ObjectNode commandOutput = objectMapper.createObjectNode();
                    commandOutput.put("command", commands[i].getCommand());
                    commandOutput.put("user", commands[i].getUsername());
                    commandOutput.put("timestamp", commands[i].getTimestamp());
                    commandOutput.put("message", "The username " + commands[i].getUsername() + " doesn't exist.");
                    commandList.add(commandOutput);
                    continue;
                }
            }

            // update the timestamp of all online users' players
            for(User user : OnlineUsers.getOnlineUsers()) {
                user.getPlayer().timestamp = commands[i].getTimestamp();
            }

            // check if the user is online
            if (commands[i].getUser() != null && !commands[i].getUser().getStatusOnline()) {
                if (commands[i].getCommand().equals("search") || commands[i].getCommand().equals("select")
                        || commands[i].getCommand().equals("load") || commands[i].getCommand().equals("playPause")
                        || commands[i].getCommand().equals("repeat") || commands[i].getCommand().equals("shuffle")
                        || commands[i].getCommand().equals("forward") || commands[i].getCommand().equals("backward")
                        || commands[i].getCommand().equals("like") || commands[i].getCommand().equals("next")
                        || commands[i].getCommand().equals("prev") || commands[i].getCommand().equals("createPlaylist")
                        || commands[i].getCommand().equals("addRemoveInPlaylist") || commands[i].getCommand().equals("follow")
                        || commands[i].getCommand().equals("switchVisibility") || commands[i].getCommand().equals("changePage")
                        || commands[i].getCommand().equals("printCurrentPage")) {
                    // create the output for this case
                    ObjectNode commandOutput = objectMapper.createObjectNode();
                    commandOutput.put("command", commands[i].getCommand());
                    commandOutput.put("user", commands[i].getUsername());
                    commandOutput.put("timestamp", commands[i].getTimestamp());
                    commandOutput.put("message", commands[i].getUsername() + " is offline.");
                    if (commands[i].getCommand().equals("search")) {
                        // the array of results is empty
                        ArrayNode results = objectMapper.createArrayNode();

                        commandOutput.put("results", results);
                    }
                    commandList.add(commandOutput);
                    continue;
                }
            }

            if (commands[i].getCommand().equals("search")) {
                commands[i].searchExecute(newLibrary);
            }
            if (commands[i].getCommand().equals("select")) {
                commands[i].selectExecute();
            }
            if (commands[i].getCommand().equals("load")) {
                commands[i].loadExecute();
            }
            if (commands[i].getCommand().equals("playPause")) {
                commands[i].playPauseExecute();
            }
            if (commands[i].getCommand().equals("status")) {
                commands[i].statusExecute();
            }
            if (commands[i].getCommand().equals("createPlaylist")) {
                commands[i].createPlaylistExecute(newLibrary);
            }
            if (commands[i].getCommand().equals("addRemoveInPlaylist")) {
                commands[i].addRemoveExecute();
            }
            if (commands[i].getCommand().equals("like")) {
                commands[i].likeExecute();
            }
            if (commands[i].getCommand().equals("showPreferredSongs")) {
                commands[i].showPreferredSongsExecute();
            }
            if (commands[i].getCommand().equals("showPlaylists")) {
                commands[i].showPlaylistsExecute();
            }
            if (commands[i].getCommand().equals("repeat")) {
                commands[i].repeatExecute();
            }
            if (commands[i].getCommand().equals("shuffle")) {
                commands[i].shuffleExecute();
            }
            if (commands[i].getCommand().equals("switchVisibility")) {
                commands[i].switchVisibilityExecute(newLibrary);
            }
            if (commands[i].getCommand().equals("follow")) {
                commands[i].followExecute();
            }
            if (commands[i].getCommand().equals("next")) {
                commands[i].nextExecute();
            }
            if (commands[i].getCommand().equals("prev")) {
                commands[i].prevExecute();
            }
            if (commands[i].getCommand().equals("forward")) {
                commands[i].forwardExecute();
            }
            if (commands[i].getCommand().equals("backward")) {
                commands[i].backwardExecute();
            }
            if (commands[i].getCommand().equals("getTop5Playlists")) {
                commands[i].getTop5PlaylistsExecute();
            }
            if (commands[i].getCommand().equals("getTop5Songs")) {
                commands[i].getTop5SongsExecute(newLibrary);
            }
            if (commands[i].getCommand().equals("switchConnectionStatus")) {
                commands[i].switchConnectionStatusExecute();
            }
            if (commands[i].getCommand().equals("getOnlineUsers")) {
                commands[i].getOnlineUsersExecute();
            }
            if(commands[i].getCommand().equals("addUser")) {
                commands[i].addUserExecute(newLibrary);
            }
            if(commands[i].getCommand().equals("addAlbum")) {
                commands[i].addAlbumExecute(newLibrary);
            }
            if(commands[i].getCommand().equals("showAlbums")) {
                commands[i].showAlbumsExecute(newLibrary);
            }
            if(commands[i].getCommand().equals("printCurrentPage")) {
                commands[i].printCurrentPageExecute(newLibrary);
            }
            if(commands[i].getCommand().equals("addEvent")) {
                commands[i].addEventExecute();
            }
            if(commands[i].getCommand().equals("addMerch")) {
                commands[i].addMerchExecute();
            }
            if(commands[i].getCommand().equals("getAllUsers")) {
                commands[i].getAllUsersExecute(newLibrary);
            }
            if(commands[i].getCommand().equals("deleteUser")) {
                commands[i].deleteUserExecute(newLibrary);
            }
            if(commands[i].getCommand().equals("addPodcast")) {
                commands[i].addPodcastExecute(newLibrary);
            }
            if(commands[i].getCommand().equals("addAnnouncement")) {
                commands[i].addAnnouncementExecute(newLibrary);
            }
            if(commands[i].getCommand().equals("removeAnnouncement")) {
                commands[i].removeAnnouncementExecute();
            }
            if(commands[i].getCommand().equals("showPodcasts")) {
                commands[i].showPodcastsExecute(newLibrary);
            }
            if(commands[i].getCommand().equals("removeAlbum")) {
                commands[i].removeAlbumExecute(newLibrary);
            }
            if(commands[i].getCommand().equals("changePage")) {
                commands[i].changePageExecute();
            }
            if(commands[i].getCommand().equals("removePodcast")) {
                commands[i].removePodcastExecute(newLibrary);
            }
            if(commands[i].getCommand().equals("removeEvent")) {
                commands[i].removeEventExecute();
            }
            if(commands[i].getCommand().equals("getTop5Artists")) {
                commands[i].getTop5ArtistsExecute(newLibrary);
            }
            if(commands[i].getCommand().equals("getTop5Albums")) {
                commands[i].getTop5AlbumsExecute();
            }
            commandList.addAll(commands[i].getCommandList());
        }

        System.out.println();
        ObjectWriter objectWriter = objectMapper.writerWithDefaultPrettyPrinter();
        objectWriter.writeValue(new File(filePathOutput), commandList);
    }
}
