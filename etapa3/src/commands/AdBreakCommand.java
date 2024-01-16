package commands;

import stream.JsonOutputStream;
import audiofiles.Album;
import audiofiles.Library;
import audiofiles.Playlist;
import audiofiles.Song;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import designpatterns.commandPattern.Command;
import main.InputCommands;
import users.User;


import java.util.ArrayList;

public class AdBreakCommand implements Command {
    private int price;
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
    public void setPrice(final int price1) {
        price = price1;
    }

    /**
     * @return the price
     */
    public int getPrice() {
        return price;
    }

    /**
     * Insert an ad in the player queue, after the current song
     * @param user    the user that wants to insert an ad
     * @param library the library of the application
     */
    public void adBreak(final User user, final Library library) {
        if (user.getPlayer().playingNow == null || !(user.getPlayer().playingNow.getType().
                equals("song"))) {
            message = user.getUsername() + " is not playing any music.";
            return;
        }
        if (user.isPremium()) {
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

        // set the price of the ad
        user.setAdPrice(price);

        // add the ad in the player queue, after the current song
        if (user.getPlayer().loadedItem.getType().equals("album")) {
            // add the ad after the current song
            // get the index of the current song
            int index = ((Album) user.getPlayer().loadedItem).getSongs().
                    indexOf(user.getPlayer().playingNow);
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
        } else if (user.getPlayer().loadedItem.getType().equals("playlist")) {
            // add the ad after the current song
            // get the index of the current song
            int index = ((Playlist) user.getPlayer().loadedItem).getSongs().
                    indexOf(user.getPlayer().playingNow);

            // create a new playlist with the same songs as the current one
            Playlist playlist = new Playlist();
            playlist.setName(((Playlist) user.getPlayer().loadedItem).getName());
            playlist.setSongs(new ArrayList<>());
            playlist.getSongs().addAll(((Playlist) user.getPlayer().loadedItem).getSongs());

            // change the loaded item to the new playlist
            user.getPlayer().loadedItem = playlist;

            // add the ad after the current song
            ((Playlist) user.getPlayer().loadedItem).getSongs().add(index + 1, ad);
        } else if (user.getPlayer().loadedItem.getType().equals("song")) {
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

    /**
     * Execute the ad break command
     * @param command the command to be executed
     * @param library the library of the application
     */
    @Override
    public void execute(final InputCommands command, final Library library) {
        User user = command.getUser();

        // update the player for the user
        if (user.getPlayer().loadedItem != null) {
            if (user.getPlayer().repeatState == 0) {
                user.getPlayer().setRemainingTime();
            }
            if (user.getPlayer().repeatState == 1) {
                user.getPlayer().setRemainingTimeRepeat1();
            }
            if (user.getPlayer().repeatState == 2) {
                user.getPlayer().setRemainingTimeRepeat2();
            }
        }

        adBreak(user, library);

        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode commandJson = objectMapper.createObjectNode()
                .put("command", "adBreak")
                .put("user", command.getUsername())
                .put("timestamp", command.getTimestamp())
                .put("message", message);
        JsonOutputStream.getCommandOutputs().add(commandJson);
    }
}
