package commands;

import audio.files.Library;
import main.InputCommands;

public interface Command {
    public void execute(InputCommands command, Library library);
}
