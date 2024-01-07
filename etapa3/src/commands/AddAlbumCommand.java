package commands;

import audio.files.Album;
import audio.files.Library;
import audio.files.Song;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import main.InputCommands;
import notification.Notification;
import platform.data.PublicAlbums;
import visit.pattern.Visitable;
import visit.pattern.Visitor;
import user.types.Artist;
import user.types.User;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class AddAlbumCommand implements Command {
    private String name;
    private int releaseYear;
    private String description;
    private ArrayList<Song> songs;
    private String message;

    public AddAlbumCommand() {
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the username to set
     */
    public void setName(final String name) {
        this.name = name;
    }

    /**
     * @return the releaseYear
     */
    public int getReleaseYear() {
        return releaseYear;
    }

    /**
     * @param releaseYear the releaseYear to set
     */
    public void setReleaseYear(final int releaseYear) {
        this.releaseYear = releaseYear;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the username to set
     */
    public void setDescription(final String description) {
        this.description = description;
    }

    /**
     * @return the songs
     */
    public ArrayList<Song> getSongs() {
        return songs;
    }

    /**
     * @param songs the songs to set
     */
    public void setSongs(final ArrayList<Song> songs) {
        this.songs = songs;
    }

    /**
     * @return the message
     */
    public String getMessage() {
        return message;
    }

    /**
     * Adds an album to the user's albums
     * @param user the user that adds the album
     * @param library the library that contains the songs
     */
    public void addAlbum(final User user, final Library library) {
        // check if the user is an artist
        if (!user.getType().equals("artist")) {
            message = user.getUsername() + " is not an artist.";
            return;
        }

        // if it is an artist, assign a variable for it
        Artist artist = (Artist) user;

        // check if the album already exists
        for (Album album : artist.getAlbums()) {
            if (album.getName().equals(name)) {
                // it exists
                message = artist.getUsername() + " has another album with the same name.";
                return;
            }
        }

        // check is the input songs for the album has duplicate songs
        if (DuplicateSongChecker.hasDuplicateSongs(songs)) {
            message = artist.getUsername() + " has the same song at least twice in this album.";
            return;
        }

        // otherwise, create the album
        Album album = new Album();
        album.setName(name);
        album.setReleaseYear(releaseYear);
        album.setDescription(description);
        album.setSongs(songs);
        album.setOwner(artist);

        // add the new songs in the library
        for (Song song : songs) {
            if (!library.getSongs().contains(song)) {
                song.setLikes(0);
                library.getSongs().add(song);
            }
        }

        // add the album to the artist
        artist.getAlbums().add(album);

        // add the album to the public albums
        PublicAlbums.getPublicAlbums().add(album);

        message = artist.getUsername() + " has added new album successfully.";

        // send notification to the subscribers
        artist.notifyObservers("New Album", "New Album from " + artist.getUsername() + ".");

        // update the artist page
        artist.setArtistPage();

    }

    // Inner class for checking duplicate songs
    // Source: https://www.baeldung.com/java-check-duplicate-list
    /**
     * Inner class for checking duplicate songs
     */
    private static class DuplicateSongChecker {
        /**
         * Checks if the input songs have duplicate songs
         * @param songs the songs to check
         * @return true if there are duplicate songs, false otherwise
         */
        public static boolean hasDuplicateSongs(final ArrayList<Song> songs) {
            Set<String> seenSongNames = new HashSet<>();

            for (Song song : songs) {
                // Check if the song name has been seen before
                if (!seenSongNames.add(song.getName())) {
                    // Duplicate found
                    return true;
                }
            }

            // No duplicates found
            return false;
        }
    }

    /**
     * Executes the command
     * @param command the input command
     * @param library the library that contains the songs
     */
    @Override
    public void execute(final InputCommands command, final Library library) {
        User user = command.getUser();
        addAlbum(user, library);

        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode commandJson = objectMapper.createObjectNode()
                .put("command", "addAlbum")
                .put("user", command.getUsername())
                .put("timestamp", command.getTimestamp())
                .put("message", message);

        command.getCommandList().add(commandJson);
    }
}
