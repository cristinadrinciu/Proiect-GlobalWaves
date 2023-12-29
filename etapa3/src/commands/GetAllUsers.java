package commands;

import audio.files.Library;
import main.InputCommands;
import visit.pattern.Visitable;
import visit.pattern.Visitor;
import user.types.User;

import java.util.ArrayList;

public class GetAllUsers implements Visitable {
    private ArrayList<String> allUsersNames = new ArrayList<>();

    public GetAllUsers() {
    }

    /**
     * @return the allUsersNames
     */
    public ArrayList<String> getAllUsersNames() {
        return allUsersNames;
    }

    /**
     * set the list of all users names
     * @param library the library
     */
    public void setAllUsersNames(final Library library) {
        allUsersNames = new ArrayList<>();
        // order: normal users, artists, hosts
        for (User user : library.getUsers()) {
            if (user.getType().equals("user")) {
                allUsersNames.add(user.getUsername());
            }
        }
        for (User user : library.getUsers()) {
            if (user.getType().equals("artist")) {
                allUsersNames.add(user.getUsername());
            }
        }
        for (User user : library.getUsers()) {
            if (user.getType().equals("host")) {
                allUsersNames.add(user.getUsername());
            }
        }
    }

    /**
     * accept the visitor
     * @param command the command
     * @param visitor the visitor
     * @param library the library
     */
    @Override
    public void accept(final InputCommands command,
                       final Visitor visitor, final Library library) {
        visitor.visit(command, this, library);
    }
}
