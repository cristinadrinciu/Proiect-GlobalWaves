package AudioFiles;

import java.util.ArrayList;

public class ArtistPage implements Page{
	private ArrayList<Album> albums = new ArrayList<>();
	private ArrayList<Merch> merch = new ArrayList<>();
	private ArrayList<Event> events = new ArrayList<>();

	public ArtistPage() {
	}

	/**
	 * builds the list of albums for the artist page
	 */
	public void setAlbums(ArrayList<Album> albums) {
		this.albums.clear();
		this.albums.addAll(albums);
	}

	/**
	 * builds the list of merch for the artist page
	 */
	public void setMerch(ArrayList<Merch> merch) {
		this.merch.clear();
		this.merch.addAll(merch);
	}

	/**
	 * builds the list of events for the artist page
	 */
	public void setEvents(ArrayList<Event> events) {
		this.events.clear();
		this.events.addAll(events);
	}

	/**
	 * @return the albums array
	 */
	public ArrayList<Album> getAlbums() {
		return albums;
	}

	/**
	 * @return the merch array
	 */
	public ArrayList<Merch> getMerch() {
		return merch;
	}

	/**
	 * @return the events array
	 */
	public ArrayList<Event> getEvents() {
		return events;
	}

	@Override
	public String printPage() {
		// Albums:\n\t[albumname1, albumname2, …]\n\nMerch:\n\t[merchname1 - merchprice1:\n\tmerchdescription1, merchname2 - merchprice2:\n\tmerchdescription2, …]\n\nEvent:\n\t[eventname1 - eventdate1:\n\teventdescription1, eventname2 - eventdate2:\n\teventdescription2, …]
		// same manner as home page printing
		String message = "";
		message = message.concat("Albums:\n\t[");
		for (Album album : albums) {
			message = message.concat(album.getName() + ", ");
		}

		// delete the last "," if it exists
		if (!albums.isEmpty())
			message = message.substring(0, message.length() - 2);

		message = message.concat("]\n\nMerch:\n\t[");
		for (Merch merch : merch) {
			message = message.concat(merch.getName() + " - " + merch.getPrice() + ":\n\t" + merch.getDescription() + ", ");
		}

		// delete the last "," if it exists
		if (!merch.isEmpty())
			message = message.substring(0, message.length() - 2);

		message = message.concat("]\n\nEvents:\n\t[");
		for (Event event : events) {
			message = message.concat(event.getName() + " - " + event.getDate() + ":\n\t" + event.getDescription() + ", ");
		}

		// delete the last "," if it exists
		if (!events.isEmpty())
			message = message.substring(0, message.length() - 2);

		message = message.concat("]");
		return message;
	}
}
