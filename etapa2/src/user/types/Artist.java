package user.types;

import audio.files.Album;
import page.content.Event;
import page.content.Merch;
import pages.ArtistPage;

import java.util.ArrayList;

public class Artist extends User {
	// add the new fields of the artist
	private ArrayList<Album> albums = new ArrayList<Album>();
	private ArrayList<Event> events = new ArrayList<Event>();
	private ArrayList<Merch> merchs = new ArrayList<Merch>();
	private ArtistPage artistPage = new ArtistPage();

	public ArrayList<Album> getAlbums() {
		return albums;
	}

	public void setAlbums(ArrayList<Album> albums) {
		this.albums = albums;
	}

	public ArrayList<Event> getEvents() {
		return events;
	}

	public void setEvents(ArrayList<Event> events) {
		this.events = events;
	}

	public ArrayList<Merch> getMerch() {
		return merchs;
	}

	public void setMerch(ArrayList<Merch> merch) {
		this.merchs = merchs;
	}

	public ArtistPage getArtistPage() {
		return artistPage;
	}

	public void setArtistPage() {
		this.artistPage.setAlbums(this.albums);
		this.artistPage.setEvents(this.events);
		this.artistPage.setMerch(this.merchs);
	}
}
