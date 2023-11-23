package main;

import fileio.input.PodcastInput;
import fileio.input.SongInput;

import java.util.ArrayList;

public class StatusCommand {
	private ArrayList<Object> statusArray = new ArrayList<>();

	public StatusCommand(){
	}

	public ArrayList<Object> buildStatusArray(Player player) {
		// this array will have 5 elements: name, remainingTime, repeat, shuffle, paused
		String name;
		if(player.loadedItem == null)
			name = "";
		else
			name = player.playingNow.getName();
		statusArray.add(name);
		statusArray.add(player.remainingTime);
		statusArray.add(getRepeatString(player));
		statusArray.add(player.shuffle);
		statusArray.add(player.paused);

		return statusArray;
	}

	private String getRepeatString(Player player) {
		String message = null;
		if(player.repeatState == 0)
			message = "No Repeat";
		if(player.repeatState == 1 && (player.loadedItem instanceof SongInput || player.loadedItem instanceof PodcastInput))
			message = "Repeat Once";
		if(player.repeatState == 2 && (player.loadedItem instanceof SongInput || player.loadedItem instanceof PodcastInput))
			message = "Repeat Infinite";
		if(player.repeatState == 1 && (player.loadedItem instanceof Playlist))
			message = "Repeat All";
		if(player.repeatState == 2 && (player.loadedItem instanceof Playlist))
			message = "Repeat Current Song";

		return message;
	}
}
