package designPatterns.commandPattern;

import audioFiles.Library;
import main.InputCommands;

public interface Command {
    /**
     * Executes the command
     * @param command the input command
     * @param library the library on which the command is executed
     */
    void execute(InputCommands command, Library library);
}
