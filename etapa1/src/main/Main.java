package main;

import checker.Checker;
import checker.CheckerConstants;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.node.ArrayNode;
import fileio.input.LibraryInput;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import fileio.input.UserInput;
import main.InputCommands;
import main.SearchCommand;
import main.Playlist;
import main.SelectCommand;


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
							  final String filePathOutput) throws IOException {
		ObjectMapper objectMapper = new ObjectMapper();
		LibraryInput library = objectMapper.readValue(new File(LIBRARY_PATH), LibraryInput.class);

		ArrayNode outputs = objectMapper.createArrayNode();

		// TODO add your implementation
		List<ObjectNode> commandList = new ArrayList<>();

		// create the new library
		Library newLibrary = new Library();
		newLibrary.createLibrary(library);

		for(User user : newLibrary.getUsers())
			user.player = new Player();

		InputCommands[] commands = objectMapper.readValue(new File("input/" + filePathInput), InputCommands[].class);

		for(int i = 0; i < commands.length; i++) {
			commands[i].getUser(newLibrary);
			if(commands[i].user == null)
				continue;
			commands[i].user.player.timestamp = commands[i].getTimestamp();

			if (commands[i].getCommand().equals("search")) {
				commands[i].SearchExecute(newLibrary);
			}
			if (commands[i].getCommand().equals("select")) {
				commands[i].SelectExecute();
			}
			if (commands[i].getCommand().equals("load")) {
				commands[i].LoadExecute();
			}
			if (commands[i].getCommand().equals("playPause")) {
				commands[i].PlayPauseExecute();
			}
			if (commands[i].getCommand().equals("status")) {
				commands[i].StatusExecute();
			}
			if (commands[i].getCommand().equals("createPlaylist")) {
				commands[i].CreatePlaylistExecute(newLibrary);
			}
			if (commands[i].getCommand().equals("addRemoveInPlaylist")) {
				commands[i].AddRemoveExecute();
			}
			if (commands[i].getCommand().equals("like")) {
				commands[i].LikeExecute();
			}
			if (commands[i].getCommand().equals("showPreferredSongs")) {
				commands[i].ShowPreferredSongsExecute();
			}
			if (commands[i].getCommand().equals("showPlaylists")) {
				commands[i].ShowPlaylistsExecute();
			}
			if (commands[i].getCommand().equals("repeat")) {
				commands[i].RepeatExecute();
			}
			if (commands[i].getCommand().equals("shuffle")) {
				commands[i].ShuffleExecute();
			}
			if (commands[i].getCommand().equals("switchVisibility")) {
				commands[i].SwitchVisibilityExecute(newLibrary);
			}
			if (commands[i].getCommand().equals("follow")) {
				commands[i].FollowExecute();
			}
			if (commands[i].getCommand().equals("next")) {
				commands[i].NextExecute();
			}
			if (commands[i].getCommand().equals("prev")) {
				commands[i].PrevExecute();
			}
			if(commands[i].getCommand().equals("forward")) {
				commands[i].ForwardExecute();
			}
			if(commands[i].getCommand().equals("backward")) {
				commands[i].BackwardExecute();
			}
			if(commands[i].getCommand().equals("getTop5Playlists")) {
				commands[i].GetTop5PlaylistsExecute();
			}
			commandList.addAll(commands[i].getCommandList());
		}

		System.out.println();
		ObjectWriter objectWriter = objectMapper.writerWithDefaultPrettyPrinter();
		objectWriter.writeValue(new File(filePathOutput), commandList);
	}
}
