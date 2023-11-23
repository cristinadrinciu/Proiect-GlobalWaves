package main;

import fileio.input.AudioFile;
import fileio.input.EpisodeInput;

import java.util.ArrayList;

public class Podcast extends AudioFile{
	private String name;
	private String owner;
	private ArrayList<EpisodeInput> episodes;

	private EpisodeInput lastEpisode;

	private int watchedTime;

	public Podcast() {
	}

	public String getName() {
		return name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(final String owner) {
		this.owner = owner;
	}

	public ArrayList<EpisodeInput> getEpisodes() {
		return episodes;
	}

	public void setEpisodes(final ArrayList<EpisodeInput> episodes) {
		this.episodes = episodes;
	}

	public EpisodeInput getLastEpisode() {
		return lastEpisode;
	}

	public void setLastEpisode(EpisodeInput lastEpisode) {
		this.lastEpisode = lastEpisode;
	}

	public int getWatchedTime() {
		return watchedTime;
	}

	public void setWatchedTime(int watchedTime) {
		this.watchedTime = watchedTime;
	}
}
