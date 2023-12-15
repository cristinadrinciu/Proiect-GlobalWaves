package commands;

import audio.files.Album;
import main.InputCommands;
import visit.pattern.Visitable;
import visit.pattern.Visitor;
import user.types.Artist;
import audio.files.Library;
import user.types.User;

import java.util.ArrayList;

public class GetTop5Artists implements Visitable {
    private final ArrayList<Artist> top5Artists = new ArrayList<>();

    public GetTop5Artists() {
    }

    /**
     * Sort the artists by the number of likes and by the creation timestamp
     * and add the first 5 artists to the top5Artists array
     */
    public void setTop5Artists(final Library library) {
        final int maxSize = 5;

        // get the artists from the library
        ArrayList<Artist> artists = new ArrayList<>();

        for (User user : library.getUsers()) {
            if (user.getType().equals("artist")) {
                artists.add((Artist) user);
            }
        }

        // sort the array of artists
        for (int i = 0; i < artists.size() - 1; i++) {
            for (int j = i + 1; j < artists.size(); j++) {
                if (GetTotalLikes.getTotalLikes(artists.get(i))
                        < GetTotalLikes.getTotalLikes(artists.get(j))) {
                    Artist aux = artists.get(i);
                    artists.set(i, artists.get(j));
                    artists.set(j, aux);
                }
            }
        }

        for (int i = 0; i < artists.size() - 1; i++) {
            for (int j = i + 1; j < artists.size(); j++) {
                if (GetTotalLikes.getTotalLikes(artists.get(i))
                        == GetTotalLikes.getTotalLikes(artists.get(j))) {
                    // sort by name
                    if (artists.get(i).getUsername().compareTo(artists.get(j).getUsername()) > 0) {
                        Artist aux = artists.get(i);
                        artists.set(i, artists.get(j));
                        artists.set(j, aux);
                    }
                }
            }
        }

        this.top5Artists.clear();

        // add the first 5 artists
        if (artists.size() < maxSize) {
            this.top5Artists.addAll(artists);
            return;
        }

        for (int i = 0; i < maxSize; i++) {
            this.top5Artists.add(artists.get(i));
        }
    }

    /**
     * @return the top5Artists array
     */
    public ArrayList<Artist> getTop5Artists() {
        return top5Artists;
    }

    private static class GetTotalLikes {
        /**
         * @param artist the artist for which we want to calculate the total number of likes
         * @return the total number of likes for the artist
         */
        public static int getTotalLikes(final Artist artist) {
            int totalLikes = 0;

            for (Album album : artist.getAlbums()) {
                album.setLikes();
                totalLikes += album.getLikes();
            }

            return totalLikes;
        }
    }

    /**
     * Accept method for the visitor
     * @param command the command to be executed
     * @param visitor the visitor
     * @param library the library
     */
    @Override
    public void accept(final InputCommands command, final Visitor visitor, final Library library) {
        visitor.visit(command, this, library);
    }
}