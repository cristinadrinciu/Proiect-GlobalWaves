package commands;

import audio.files.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import main.InputCommands;
import user.types.Artist;
import user.types.Host;
import user.types.User;
import visit.pattern.Visitable;
import visit.pattern.Visitor;

public class LoadCommand implements Command {
    private static AudioFile loadedItem;
    private String resultMessage;

    public LoadCommand() {
    }

    /**
     * @return the loadedItem
     */
    public static AudioFile getLoadedItem() {
        return loadedItem;
    }

    /**
     * @param loadedItem the loadedItem to set
     */
    public static void setLoadedItem(final AudioFile loadedItem) {
        LoadCommand.loadedItem = loadedItem;
    }

    /**
     * @return the resultMessage
     */
    public String getResultMessage() {
        return resultMessage;
    }

    /**
     * @param audiofile the audiofile to load
     */
    public String buildMessage(final AudioFile audiofile) {
        if (audiofile != null) {
            if (audiofile instanceof Podcast) {
                if (((Podcast) audiofile).getEpisodes().isEmpty()) {
                    resultMessage = "You can't load an empty audio collection!";
                    return resultMessage;
                }
            }
            if (audiofile instanceof Playlist) {
                if (((Playlist) audiofile).getSongs().isEmpty()) {
                    resultMessage = "You can't load an empty audio collection!";
                    return resultMessage;
                }
            }
            if (audiofile instanceof Album) {
                if (((Album) audiofile).getSongs().isEmpty()) {
                    resultMessage = "You can't load an empty audio collection!";
                    return resultMessage;
                }
            }
            loadedItem = audiofile;
            resultMessage = "Playback loaded successfully.";
        } else {
            resultMessage = "Please select a source before attempting to load.";
        }

        return resultMessage;
    }

    /**
     * Execute the load command
     * @param command the command to execute
     * @param library the library on which the command is executed
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

        String message = buildMessage(user.getSelectedItem());
        if (LoadCommand.getLoadedItem() != null) {
            user.getPlayer().loadedItem = LoadCommand.getLoadedItem();
            user.getPlayer().setPlayingNow();
            user.getPlayer().shuffle = false;
            user.getPlayer().repeatState = 0;
        }
        user.setSelectedItem(null);
        LoadCommand.setLoadedItem(null);

        // update the listens
        if(user.getPlayer().loadedItem != null) {
            if(user.getPlayer().loadedItem instanceof Song
                    || user.getPlayer().loadedItem instanceof Playlist
                    || user.getPlayer().loadedItem instanceof Album) {
                // add in the list of songs between ads
                if(user.getPlayer().playingNow != null && !user.isPremium())
                    user.getSongsBetweenAds().add((Song) user.getPlayer().playingNow);

                // add in the list of songs listened while premium
                if(user.isPremium())
                    user.addPremiumSongs((Song) user.getPlayer().playingNow);

                // update the listens of the song
                user.setListensToSong(user.getPlayer().playingNow.getName());
                user.setListensToArtist(((Song) user.getPlayer().playingNow).getArtist());
                user.setListensToGenre(((Song) user.getPlayer().playingNow).getGenre());
                user.setListensToAlbum(((Song) user.getPlayer().playingNow).getAlbum());

                // update the artist statistics
                Artist artist = user.getPlayer().findArtist((Song) user.getPlayer().playingNow);
                if (artist != null) {
                    artist.setListensToSong(user.getPlayer().playingNow.getName());
                    artist.setListensToAlbum(((Song) user.getPlayer().playingNow).getAlbum());
                    artist.setListensToFan(user.getUsername());
                }
            }
            if(user.getPlayer().loadedItem instanceof Podcast) {
                // update the listens of the episode
                user.setListensToEpisode(user.getPlayer().playingNow.getName());

                // update the host statistics
                Host host = user.getPlayer().findHost((Podcast) user.getPlayer().loadedItem);
                if (host != null) {
                    host.setListensToEpisode(user.getPlayer().playingNow.getName());
                    host.setListensToFan(user.getUsername());
                }
            }
        }

        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode commandJson = objectMapper.createObjectNode()
                .put("command", "load")
                .put("user", command.getUsername())
                .put("timestamp", command.getTimestamp())
                .put("message", message);

        command.getCommandList().add(commandJson);
    }
}
