package commands;

import audio.files.Album;
import audio.files.Library;
import audio.files.Playlist;
import audio.files.Song;
import main.InputCommands;
import player.Player;
import visit.pattern.Visitable;
import visit.pattern.Visitor;
import platform.data.PublicAlbums;
import platform.data.PublicPlaylists;
import user.types.Artist;
import user.types.User;

import java.util.ArrayList;

public class RemoveAlbumCommand implements Visitable {
    private String name;
    private String message;

    public RemoveAlbumCommand() {
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(final String name) {
        this.name = name;
    }

    /**
     * @return the message
     */
    public String getMessage() {
        return this.message;
    }

    /**
     * @param user the user that wants to remove the album
     * @param library the library that contains the users
     */
    public void removeAlbum(final User user, final Library library) {
        // check if the user is an artist
        if (!user.getType().equals("artist")) {
            message = user.getUsername() + " is not an artist.";
            return;
        }

        // check if the album exists
        boolean found = false;
        for (Album album : ((Artist) user).getAlbums()) {
            if (album.getName().equals(this.name)) {
                found = true;
                break;
            }
        }

        if (!found) {
            message = user.getUsername()
                    + " doesn't have an album with the given name.";
            return;
        }

        // find the album
        Album deleteAlbum = new Album();
        for (Album album : ((Artist) user).getAlbums()) {
            if (album.getName().equals(this.name)) {
                deleteAlbum = album;
                break;
            }
        }

        // check if the album can be deleted
        if (!CanBeDeleted.canDeleteAlbum(deleteAlbum, library)) {
            message = user.getUsername() + " can't delete this album.";
            return;
        }

        // delete the album from the artist's albums
        ((Artist) user).getAlbums().remove(deleteAlbum);

        // remove the album from the platform
        PublicAlbums.getPublicAlbums().remove(deleteAlbum);

        // remove the songs from the album from the platform
        ArrayList<Song> songsToRemove = new ArrayList<>();
        for (Song song : library.getSongs()) {
            if (song.getAlbum().equals(deleteAlbum.getName())) {
                songsToRemove.add(song);
            }
        }

        for (Song song : songsToRemove) {
            library.getSongs().remove(song);
        }

        // remove from each playlist from the users the songs from the deleted album
        for (User user1 : library.getUsers()) {
            for (Playlist playlist : user1.getPlaylists()) {
                ArrayList<Song> songsToRemoveFromPlaylist = new ArrayList<>();
                for (Song song : playlist.getSongs()) {
                    if (song.getAlbum().equals(deleteAlbum.getName())) {
                        songsToRemoveFromPlaylist.add(song);
                    }
                }

                for (Song song : songsToRemoveFromPlaylist) {
                    playlist.getSongs().remove(song);
                }
            }

            // remove the album's songs also from the playlists from the player
            for (Playlist playlist : user1.getPlayer().playlists) {
                ArrayList<Song> songsToRemoveFromPlaylist = new ArrayList<>();
                for (Song song : playlist.getSongs()) {
                    if (song.getAlbum().equals(deleteAlbum.getName())) {
                        songsToRemoveFromPlaylist.add(song);
                    }
                }

                for (Song song : songsToRemoveFromPlaylist) {
                    playlist.getSongs().remove(song);
                }
            }
        }

        // remove the songs from the album from the users' liked songs
        for (User user1 : library.getUsers()) {
            ArrayList<Song> songsToRemoveFromLikedSongs = new ArrayList<>();
            for (Song song : user1.getLikedSongs()) {
                if (song.getAlbum().equals(deleteAlbum.getName())) {
                    songsToRemoveFromLikedSongs.add(song);
                }
            }

            for (Song song : songsToRemoveFromLikedSongs) {
                user1.getLikedSongs().remove(song);
            }
        }

        // remove the album's songs from the all users' shuffled list
        for (User user1 : library.getUsers()) {
            if (!user1.getPlayer().shuffle) {
                continue;
            }
            ArrayList<Song> songsToRemoveFromShuffledList = new ArrayList<>();
            for (Song song : user1.getPlayer().shuffledPlaylist) {
                if (song.getAlbum().equals(deleteAlbum.getName())) {
                    songsToRemoveFromShuffledList.add(song);
                }
            }

            for (Song song : songsToRemoveFromShuffledList) {
                user1.getPlayer().shuffledPlaylist.remove(song);
            }
        }


        // remove the album's songs from PublicPlaylists
        for (Playlist playlist : PublicPlaylists.getPlaylists()) {
            ArrayList<Song> songsToRemoveFromPlaylist = new ArrayList<>();
            for (Song song : playlist.getSongs()) {
                if (song.getAlbum().equals(deleteAlbum.getName())) {
                    songsToRemoveFromPlaylist.add(song);
                }
            }

            for (Song song : songsToRemoveFromPlaylist) {
                playlist.getSongs().remove(song);
            }
        }

        // set the artist page
        ((Artist) user).setArtistPage();

        // set the message
        message = user.getUsername() + " deleted the album successfully.";
    }

    private static class CanBeDeleted {
/**
         * @param deleteAlbum the album that will be deleted
         * @param library the library that contains the users
         * @return true if the album can be deleted, false otherwise
         */
        public static boolean canDeleteAlbum(final Album deleteAlbum, final Library library) {
            // check if any user has interactions with this album
            // check if any user has this album loaded in the player
            for (User user : library.getUsers()) {
                Player player = user.getPlayer();
                if (player.loadedItem instanceof Album) {
                    if (deleteAlbum.getName().equals(((Album) player.loadedItem).getName())) {
                        // cannot delete the album
                        return false;
                    }
                }

                // check if any user has a song from this album in the player
                if (player.playingNow instanceof Song) {
                    if (((Song) player.playingNow).getAlbum().equals(deleteAlbum.getName())) {
                        // cannot delete the album
                        return false;
                    }
                }

                // check if anu user has playlist loaded that contains a song from this album
                if (player.loadedItem instanceof Playlist) {
                    for (Song song : ((Playlist) player.loadedItem).getSongs()) {
                        if (song.getAlbum().equals(deleteAlbum.getName())) {
                            // cannot delete the album
                            return false;
                        }
                    }
                }
            }
            return true;
        }
    }

    /**
     * Accept method for the visitor
     * @param command the command that will be executed
     * @param visitor the visitor that will visit this command
     * @param library the library that contains the users
     */
    @Override
    public void accept(final InputCommands command, final Visitor visitor, final Library library) {
        visitor.visit(command, this, library);
    }
}
