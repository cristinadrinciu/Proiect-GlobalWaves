package commands;

import audio.files.Library;
import audio.files.Podcast;
import audio.files.*;
import fileio.input.EpisodeInput;
import main.InputCommands;
import visit.pattern.Visitable;
import visit.pattern.Visitor;
import user.types.Host;
import user.types.User;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class AddPodcastCommand implements Visitable {
	private String name;
	private ArrayList<EpisodeInput> episodes;
	private String message;

	public AddPodcastCommand() {
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ArrayList<EpisodeInput> getEpisodes() {
		return episodes;
	}

	public void setEpisodes(ArrayList<EpisodeInput> episodes) {
		this.episodes = episodes;
	}

	public String getMessage() {
		return message;
	}

	public void addPodcast(User user, Library library) {
		// check if the user is a host
		if(!user.getType().equals("host")) {
			message = user.getUsername() + " is not a host.";
			return;
		}

		// if it is a host, assign the host to a variable
		Host host = (Host) user;

		// check if the podcast already exists
		for (Podcast podcast : host.getPodcasts()) {
			if (podcast.getName().equals(this.name)) {
				// it exists
				message = user.getUsername() + " has another podcast with the same name.";
				return;
			}
		}

		// see if the podcast has any duplicate episodes
		if (hasDuplicateEpisodes(this.episodes)) {
			message = "Podcast " + this.name + " has duplicate episodes.";
			return;
		}

		// create the podcast
		Podcast podcast = new Podcast();
		podcast.setName(this.name);
		podcast.setEpisodes(this.episodes);
		podcast.setOwner(host.getUsername());

		// add the podcast to the library
		library.getPodcasts().add(podcast);

		// add the podcast to the host
		host.getPodcasts().add(podcast);

		// set the host page
		host.setHostPage();

		// set the message
		message = host.getUsername() + " has added new podcast successfully.";

	}

	private boolean hasDuplicateEpisodes(ArrayList<EpisodeInput> episodes) {
		Set<String> seenEpisodeNames = new HashSet<>();
		for (EpisodeInput episode : episodes) {
			// Check if the episode name has been seen before
			if (!seenEpisodeNames.add(episode.getName())) {
				// Duplicate found
				return true;
			}
		}

		// No duplicates found
		return false;
	}

	@Override
	public void accept(InputCommands command, Visitor visitor, Library library) {
		visitor.visit(command, this, library);
	}
}
