package audioFiles;

import users.User;

import java.util.ArrayList;

public class Playlist extends AudioFile {
    private User owner;
    private boolean isPublic;
    private ArrayList<Song> songs = new ArrayList<>();
    private int followers;
    private int createdTimestamp;
    private int totalLikes;

    public Playlist() {
        // insert super constructor
        super("playlist");
        this.isPublic = true;
        this.followers = 0;
        this.totalLikes = 0;
    }

    /**
     *
     * @return public state
     */
    public boolean getPublic() {
        return isPublic;
    }

    /**
     *
     * @param aPublic the public parameter
     */
    public void setPublic(final boolean aPublic) {
        isPublic = aPublic;
    }

    /**
     *
     * @param owner owner of the playlist
     */
    public void setOwner(final User owner) {
        this.owner = owner;
    }

    /**
     *
     * @return owner
     */
    public User getOwner() {
        return owner;
    }

    /**
     *
     * @return public state
     */
    public boolean isPublic() {
        return isPublic;
    }

    /**
     *
     * @return followers
     */
    public int getFollowers() {
        return followers;
    }

    /**
     *
     * @param followers followers
     */
    public void setFollowers(final int followers) {
        this.followers = followers;
    }

    /**
     *
     * @return created timestamp
     */
    public int getCreatedTimestamp() {
        return createdTimestamp;
    }

    /**
     *
     * @param createdTimestamp created timestamp
     */
    public void setCreatedTimestamp(final int createdTimestamp) {
        this.createdTimestamp = createdTimestamp;
    }

    /**
     *
     * @return songs
     */
    public ArrayList<Song> getSongs() {
        return songs;
    }

    /**
     *
     * @param songs songs
     */
    public void setSongs(final ArrayList<Song> songs) {
        this.songs = songs;
    }

    /**
     *
     * @return total likes from the songs
     */
    public int getTotalLikes() {
        // total likes from the songs
        for (Song song : this.songs) {
            totalLikes += song.getLikes();
        }

        return totalLikes;
    }
}


