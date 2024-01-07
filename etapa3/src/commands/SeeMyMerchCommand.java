package commands;

import audio.files.Library;
import main.InputCommands;
import user.types.User;
import visit.pattern.Visitable;
import visit.pattern.Visitor;

import java.util.ArrayList;

public class SeeMyMerchCommand implements Visitable {

    public SeeMyMerchCommand() {
    }

    public ArrayList<String> getBoughtMerch(User user) {
        return user.getBoughtMerch();
    }

    /**
     * Accepts the visitor
     * @param command the command that will be accepted
     * @param visitor the visitor that will visit the command
     */
    @Override
    public void accept(final InputCommands command, final Visitor visitor, final Library library) {
        visitor.visit(command, this, library);
    }
}
