package main;

import AudioFiles.Album;

import java.util.ArrayList;

public class PublicAlbums {
	private static ArrayList<Album> publicAlbums = new ArrayList<>();

	/**
	 * @return the albums
	 */
	public static ArrayList<Album> getPublicAlbums() {
		return publicAlbums;
	}

}
