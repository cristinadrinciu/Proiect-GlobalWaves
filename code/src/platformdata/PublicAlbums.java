package platformdata;

import audiofiles.Album;

import java.util.ArrayList;

public final class PublicAlbums {
    private static ArrayList<Album> publicAlbums = new ArrayList<>();

    private PublicAlbums() {
    }
    /**
     * @return the albums
     */
    public static ArrayList<Album> getPublicAlbums() {
        return publicAlbums;
    }

}
