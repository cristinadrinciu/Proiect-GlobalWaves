package main;

import fileio.input.AudioFile;

import java.util.ArrayList;

public class Playlist extends AudioFile {
    private User owner;
    private boolean isPublic;
    ArrayList<Song> songs;
    private int followers;

    public boolean getPublic() {
        return isPublic;
    }

    public void setPublic(boolean aPublic) {
        isPublic = aPublic;
    }

    public Playlist() {
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public User getOwner() {
        return owner;
    }

    public boolean isPublic() {
        return isPublic;
    }

    public int getFollowers() {
        return followers;
    }

    public void setFollowers(int followers) {
        this.followers = followers;
    }
}


