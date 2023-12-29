package platform.data;

import audio.files.Album;

import java.util.ArrayList;

public class PublicAlbums {
    private static ArrayList<Album> publicAlbums = new ArrayList<>();

    public PublicAlbums() {
    }
    /**
     * @return the albums
     */
    public static ArrayList<Album> getPublicAlbums() {
        return publicAlbums;
    }

}
