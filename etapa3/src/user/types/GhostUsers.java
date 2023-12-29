package user.types;

import java.util.ArrayList;

public class GhostUsers {
    private static ArrayList<Artist> ghostArtists = new ArrayList<>();

    public static ArrayList<Artist> getGhostArtists() {
        return ghostArtists;
    }

    public static void setGhostArtists(ArrayList<Artist> ghostArtists) {
        GhostUsers.ghostArtists = ghostArtists;
    }
}
