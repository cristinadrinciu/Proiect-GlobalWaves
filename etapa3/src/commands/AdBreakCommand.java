package commands;

import audio.files.Album;
import audio.files.Library;
import audio.files.Playlist;
import audio.files.Song;
import user.types.Artist;
import user.types.User;
import visit.pattern.Visitable;
import visit.pattern.Visitor;

import java.util.ArrayList;

public class AdBreakCommand implements Visitable {
    private static int price;
    private String message;

    public AdBreakCommand() {
    }

    /**
     * @return the message
     */
    public String getMessage() {
        return message;
    }

    /**
     * @param price1 the price to set
     */
    public static void setPrice(int price1) {
        price = price1;
    }

    /**
     * @return the price
     */
    public static int getPrice() {
        return price;
    }

    public void adBreak(User user, Library library) {
        if (user.getPlayer().playingNow == null || !(user.getPlayer().playingNow instanceof Song)) {
            message = user.getUsername() + " is not playing any music.";
            return;
        }
        // set the ad from the library
        Song ad = new Song();
        for (Song song : library.getSongs()) {
            if (song.getName().equals("Ad Break")) {
                ad = song;
                break;
            }
        }

        // add the ad in the player queue, after the current song
        if (user.getPlayer().loadedItem instanceof Album) {
            // add the ad after the current song
            // get the index of the current song
            int index = ((Album) user.getPlayer().loadedItem).getSongs().indexOf(user.getPlayer().playingNow);
            // create a new album with the same songs as the current one
            Album album = new Album();
            album.setName(((Album) user.getPlayer().loadedItem).getName());
            album.setReleaseYear(((Album) user.getPlayer().loadedItem).getReleaseYear());
            album.setOwner(((Album) user.getPlayer().loadedItem).getOwner());
            album.setSongs(new ArrayList<>());
            album.getSongs().addAll(((Album) user.getPlayer().loadedItem).getSongs());

            // change the loaded item to the new album
            user.getPlayer().loadedItem = album;

            // add the ad after the current song, without modifying the list of the album
            ((Album) user.getPlayer().loadedItem).getSongs().add(index + 1, ad);
        } else if (user.getPlayer().loadedItem instanceof Playlist) {
            // add the ad after the current song
            // get the index of the current song
            int index = ((Playlist) user.getPlayer().loadedItem).getSongs().indexOf(user.getPlayer().playingNow);

            // create a new playlist with the same songs as the current one
            Playlist playlist = new Playlist();
            playlist.setName(((Playlist) user.getPlayer().loadedItem).getName());
            playlist.setSongs(new ArrayList<>());
            playlist.getSongs().addAll(((Playlist) user.getPlayer().loadedItem).getSongs());

            // change the loaded item to the new playlist
            user.getPlayer().loadedItem = playlist;

            // add the ad after the current song
            ((Playlist) user.getPlayer().loadedItem).getSongs().add(index + 1, ad);
        } else if (user.getPlayer().loadedItem instanceof Song) {
            // add the ad after the current song
            // being a song, transform it into a playlist
            Playlist playlist = new Playlist();
            playlist.setName("Ad Break");
            playlist.setSongs(new ArrayList<>());
            playlist.getSongs().add((Song) user.getPlayer().loadedItem);
            playlist.getSongs().add(ad);
            user.getPlayer().loadedItem = playlist;
        }

        message = "Ad inserted successfully.";
    }

    @Override
    public void accept(main.InputCommands command, Visitor visitor, audio.files.Library library) {
        visitor.visit(command, this, library);
    }
}
