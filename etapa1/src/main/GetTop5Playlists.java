package main;

import java.util.ArrayList;

public class GetTop5Playlists {
	private ArrayList<Playlist> top5Playlists = new ArrayList<>();

	public GetTop5Playlists() {
	}

	public void setTop5Playlists() {
		ArrayList<Playlist> playlists = PublicPlaylists.playlists;

		// sort the array of playlists
		for(int i = 0; i < playlists.size() - 1; i++) {
			for(int j = i + 1; j < playlists.size(); j++) {
				if(playlists.get(i).getFollowers() < playlists.get(j).getFollowers()) {
					Playlist aux = playlists.get(i);
					playlists.set(i, playlists.get(j));
					playlists.set(j, aux);
				}
			}
		}

		this.top5Playlists.clear();
		// add the first 5 playlists
		if(playlists.size() < 5) {
			this.top5Playlists.addAll(playlists);
			return;
		}

		for(int i = 0; i < 5; i++) {
			this.top5Playlists.add(playlists.get(i));
		}
	}

	public ArrayList<Playlist> getTop5Playlists() {
		return this.top5Playlists;
	}
}
