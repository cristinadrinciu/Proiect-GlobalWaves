package pages;

import audio.files.Album;
import page.content.Event;
import page.content.Merch;
import user.types.Artist;

import java.util.ArrayList;

public class ArtistPage implements Page {
    private Artist artist;
    private ArrayList<Album> albums = new ArrayList<>();
    private ArrayList<Merch> merch = new ArrayList<>();
    private ArrayList<Event> events = new ArrayList<>();

    public ArtistPage(Artist artist) {
        this.artist = artist;
    }

    /**
     * builds the list of albums for the artist page
     */
    public void setAlbums(final ArrayList<Album> albums) {
        this.albums.clear();
        this.albums.addAll(albums);
    }

    /**
     * builds the list of merch for the artist page
     */
    public void setMerch(final ArrayList<Merch> merch) {
        this.merch.clear();
        this.merch.addAll(merch);
    }

    /**
     * builds the list of events for the artist page
     */
    public void setEvents(final ArrayList<Event> events) {
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

    /**
     * This method prints the artist page
     */
    @Override
    public String printPage() {
        // same manner as home page printing
        String message = "";
        message = message.concat("Albums:\n\t[");
        for (Album album : albums) {
            message = message.concat(album.getName() + ", ");
        }

        // delete the last "," if it exists
        if (!albums.isEmpty()) {
            message = message.substring(0, message.length() - 2);
        }

        message = message.concat("]\n\nMerch:\n\t[");
        for (Merch merch : merch) {
            message = message.concat(merch.getName() + " - " + merch.getPrice()
                    + ":\n\t" + merch.getDescription() + ", ");
        }

        // delete the last "," if it exists
        if (!merch.isEmpty()) {
            message = message.substring(0, message.length() - 2);
        }

        message = message.concat("]\n\nEvents:\n\t[");
        for (Event event : events) {
            message = message.concat(event.getName() + " - " + event.getDate()
                    + ":\n\t" + event.getDescription() + ", ");
        }

        // delete the last "," if it exists
        if (!events.isEmpty()) {
            message = message.substring(0, message.length() - 2);
        }

        message = message.concat("]");
        return message;
    }

    /**
     * @return the artist
     */
    public Artist getArtist() {
        return artist;
    }

}
