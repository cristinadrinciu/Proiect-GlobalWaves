package commands;

import audio.files.Album;
import audio.files.Library;
import audio.files.Playlist;
import audio.files.Song;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import main.InputCommands;
import user.types.Artist;
import user.types.User;
import visit.pattern.Visitable;
import visit.pattern.Visitor;

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
    public void setPrice(int price1) {
        price = price1;
    }

    /**
     * @return the price
     */
    public int getPrice() {
        return price;
    }

    public void adBreak(User user, Library library) {
        if (user.getPlayer().playingNow == null || !(user.getPlayer().playingNow instanceof Song)) {
            message = user.getUsername() + " is not playing any music.";
            return;
        }
        if(user.isPremium())
            return;
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
    public void execute(InputCommands command, Library library) {
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
        command.getCommandList().add(commandJson);
    }
}
