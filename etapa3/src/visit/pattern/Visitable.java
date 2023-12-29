package visit.pattern;

import audio.files.Library;
import main.InputCommands;

public interface Visitable {
    /**
     * Accept method for the visitor
     * @param command the command to be executed
     * @param visitor the visitor
     * @param library the library
     */
    void accept(InputCommands command, Visitor visitor, Library library);
}
