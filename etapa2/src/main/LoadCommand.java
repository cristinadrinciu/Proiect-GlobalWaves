package main;

import AudioFiles.Album;
import AudioFiles.AudioFile;
import AudioFiles.Playlist;
import AudioFiles.Podcast;

public class LoadCommand {
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
}