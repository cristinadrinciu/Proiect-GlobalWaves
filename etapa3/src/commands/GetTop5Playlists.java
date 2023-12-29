package commands;

import audio.files.Library;
import audio.files.Playlist;
import main.InputCommands;
import platform.data.PublicPlaylists;
import visit.pattern.Visitable;
import visit.pattern.Visitor;

import java.util.ArrayList;

public class GetTop5Playlists implements Visitable {
    private final ArrayList<Playlist> top5Playlists = new ArrayList<>();

    public GetTop5Playlists() {
    }

    /**
     * Sort the playlists by the number of followers and by the creation timestamp
     * and add the first 5 playlists to the top5Playlists array
     */
    public void setTop5Playlists() {
        final int maxSize = 5;
        ArrayList<Playlist> playlists = new ArrayList<>(PublicPlaylists.getPlaylists());

        // sort the array of playlists
        for (int i = 0; i < playlists.size() - 1; i++) {
            for (int j = i + 1; j < playlists.size(); j++) {
                if (playlists.get(i).getFollowers() < playlists.get(j).getFollowers()) {
                    Playlist aux = playlists.get(i);
                    playlists.set(i, playlists.get(j));
                    playlists.set(j, aux);
                }
            }
        }

        for (int i = 0; i < playlists.size() - 1; i++) {
            for (int j = i + 1; j < playlists.size(); j++) {
                if (playlists.get(i).getFollowers() == playlists.get(j).getFollowers()) {
                    if (playlists.get(i).getCreatedTimestamp()
                            > playlists.get(j).getCreatedTimestamp()) {
                        Playlist aux = playlists.get(i);
                        playlists.set(i, playlists.get(j));
                        playlists.set(j, aux);
                    }
                }
            }
        }

        this.top5Playlists.clear();

        // add the first 5 playlists
        if (playlists.size() < maxSize) {
            this.top5Playlists.addAll(playlists);
            return;
        }

        for (int i = 0; i < maxSize; i++) {
            this.top5Playlists.add(playlists.get(i));
        }
    }

    /**
     * @return the top5Playlists array
     */
    public ArrayList<Playlist> getTop5Playlists() {
        return this.top5Playlists;
    }

    /**
     * Accept method for the visitor
     * @param command the command to be executed
     * @param visitor the visitor that executes the command
     * @param library the library of the user
     */
    @Override
    public void accept(final InputCommands command, final Visitor visitor, final Library library) {
        visitor.visit(command, this, library);
    }
}