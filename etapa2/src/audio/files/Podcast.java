package audio.files;

import fileio.input.EpisodeInput;

import java.util.ArrayList;

public class Podcast extends AudioFile {
    private String name;
    private String owner;
    private ArrayList<EpisodeInput> episodes;

    private EpisodeInput lastEpisode;

    private int watchedTime;

    public Podcast() {
    }

    /**
     * @return the name of the podcast
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name of the podcast
     */
    public void setName(final String name) {
        this.name = name;
    }

    /**
     * @return the owner of the podcast
     */
    public String getOwner() {
        return owner;
    }

    /**
     * @param owner the owner of the podcast
     */
    public void setOwner(final String owner) {
        this.owner = owner;
    }

    /**
     * @return the episodes of the podcast
     */
    public ArrayList<EpisodeInput> getEpisodes() {
        return episodes;
    }

    /**
     * @param episodes the episodes of the podcast
     */
    public void setEpisodes(final ArrayList<EpisodeInput> episodes) {
        this.episodes = episodes;
    }

    /**
     * @return the last episode of the podcast
     */
    public EpisodeInput getLastEpisode() {
        return lastEpisode;
    }

    /**
     * @param lastEpisode the last episode of the podcast
     */
    public void setLastEpisode(final EpisodeInput lastEpisode) {
        this.lastEpisode = lastEpisode;
    }

    /**
     * @return the watched time of the podcast
     */
    public int getWatchedTime() {
        return watchedTime;
    }

    /**
     * @param watchedTime the watched time of the podcast
     */
    public void setWatchedTime(final int watchedTime) {
        this.watchedTime = watchedTime;
    }
}
