package commands;

import audio.files.Library;
import audio.files.Song;
import main.InputCommands;
import visit.pattern.Visitable;
import visit.pattern.Visitor;

import java.util.ArrayList;

public class GetTop5Songs implements Visitable {
    private ArrayList<Song> top5Songs = new ArrayList<>();

    public GetTop5Songs() {
    }

    /**
     * Sorts the songs in the library by the number of likes and adds the first 5 songs
     * to the top5Songs array
     * @param library the library of the user
     */
    public void setTop5Songs(final Library library) {
        ArrayList<Song> songs = new ArrayList<>();
        songs.addAll(library.getSongs());

        // sort the array of songs
        for (int i = 0; i < songs.size() - 1; i++) {
            for (int j = i + 1; j < songs.size(); j++) {
                if (songs.get(i).getLikes() < songs.get(j).getLikes()) {
                    Song aux = songs.get(i);
                    songs.set(i, songs.get(j));
                    songs.set(j, aux);
                }
            }
        }

        for (int i = 0; i < songs.size() - 1; i++) {
            for (int j = i + 1; j < songs.size(); j++) {
                // if the number of likes is the same, sort by index in library
                if (songs.get(i).getLikes() == songs.get(j).getLikes()) {
                    Song song1 = songs.get(i);
                    Song song2 = songs.get(j);
                    if (library.getSongs().indexOf(song1) > library.getSongs().indexOf(song2)) {
                        Song aux = songs.get(i);
                        songs.set(i, songs.get(j));
                        songs.set(j, aux);
                    }
                }
            }
        }

        this.top5Songs.clear();

        final int maxSongs = 5;
        // add the first 5 songs
        if (songs.size() < maxSongs) {
            this.top5Songs.addAll(songs);
            return;
        }

        for (int i = 0; i < maxSongs; i++) {
            this.top5Songs.add(songs.get(i));
        }
    }

    /**
     * @return the top5Songs array
     */
    public ArrayList<Song> getTop5Songs() {
        return this.top5Songs;
    }

    @Override
    public void accept(final InputCommands command, final Visitor visitor, final Library library) {
        visitor.visit(command, this, library);
    }
}
