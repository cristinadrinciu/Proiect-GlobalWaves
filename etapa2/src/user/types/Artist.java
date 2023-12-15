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

    public Artist() {
    }

    /**
     * @return the albums
     */
    public ArrayList<Album> getAlbums() {
        return albums;
    }

    /**
     * @param albums the albums to set
     */
    public void setAlbums(final ArrayList<Album> albums) {
        this.albums = albums;
    }

    /**
     * @return the events
     */
    public ArrayList<Event> getEvents() {
        return events;
    }

    /**
     * @param events the events to set
     */
    public void setEvents(final ArrayList<Event> events) {
        this.events = events;
    }

    /**
     * @return the merch
     */
    public ArrayList<Merch> getMerch() {
        return merchs;
    }

    /**
     * @param merch the merch to set
     */
    public void setMerch(final ArrayList<Merch> merch) {
        this.merchs = merchs;
    }

    /**
     * @return the artistPage
     */
    public ArtistPage getArtistPage() {
        return artistPage;
    }

    /**
     * This method sets the artist page
     */
    public void setArtistPage() {
        this.artistPage.setAlbums(this.albums);
        this.artistPage.setEvents(this.events);
        this.artistPage.setMerch(this.merchs);
    }
}
