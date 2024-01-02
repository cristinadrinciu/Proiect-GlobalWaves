package main;

import audio.files.Library;
import commands.EndProgramCommand;
import visit.pattern.CommandExecute;
import platform.data.OnlineUsers;
import platform.data.PublicAlbums;
import platform.data.PublicPlaylists;
import user.types.User;
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
        InputCommands[] commands = objectMapper.readValue(new File(
                "input/test04_etapa3_monetization_premium.json"), InputCommands[].class);
        for (User user : newLibrary.getUsers()) {
            user.getLikedSongs().clear();
            user.getPlaylists().clear();
        }

        for (int i = 0; i < commands.length; i++) {
            // treat the case when the user doesn't exist or is an admin command
            if (!commands[i].getCommand().equals("addUser")
                    && !commands[i].getCommand().equals("deleteUser")
                    && commands[i].getUsername() != null) {
                commands[i].getUser(newLibrary);
                if (commands[i].getUser() == null) {
                    // create the output for the command
                    ObjectNode commandOutput = objectMapper.createObjectNode();
                    commandOutput.put("command", commands[i].getCommand());
                    commandOutput.put("user", commands[i].getUsername());
                    commandOutput.put("timestamp", commands[i].getTimestamp());
                    commandOutput.put("message", "The username "
                            + commands[i].getUsername() + " doesn't exist.");
                    commandList.add(commandOutput);
                    continue;
                }
            }

            // update the timestamp of all online users' players
            for (User user : OnlineUsers.getOnlineUsers()) {
                user.getPlayer().timestamp = commands[i].getTimestamp();
            }

            // check if the user is offline, so that he can't execute the following commands
            if (commands[i].getUser() != null && !commands[i].getUser().getStatusOnline()) {
                if (commands[i].getCommand().equals("search") || commands[i].
                        getCommand().equals("select")
                        || commands[i].getCommand().equals("load") || commands[i].getCommand().
                        equals("playPause")
                        || commands[i].getCommand().equals("repeat") || commands[i].getCommand().
                        equals("shuffle")
                        || commands[i].getCommand().equals("forward") || commands[i].getCommand().
                        equals("backward")
                        || commands[i].getCommand().equals("like") || commands[i].getCommand().
                        equals("next")
                        || commands[i].getCommand().equals("prev") || commands[i].getCommand().
                        equals("createPlaylist")
                        || commands[i].getCommand().equals("addRemoveInPlaylist") || commands[i].
                        getCommand().equals("follow")
                        || commands[i].getCommand().equals("switchVisibility") || commands[i].
                        getCommand().equals("changePage")
                        || commands[i].getCommand().equals("printCurrentPage")
                        || commands[i].getCommand().equals("wrapped")) {
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

            // create Visitor to execute the command
            CommandExecute commandExecute = new CommandExecute();

            // set the command to execute
            commands[i].setCommandToExecute();

            // execute the command
            commands[i].executeCommand(newLibrary, commandExecute);

            // add the output of the command to the list of outputs
            commandList.addAll(commands[i].getCommandList());
        }

        // endProgram command
        commandList.add(EndProgramCommand.execute(newLibrary));

        ObjectWriter objectWriter = objectMapper.writerWithDefaultPrettyPrinter();
        objectWriter.writeValue(new File(filePathOutput), commandList);
        System.out.println();
    }
}
