package main;

import fileio.input.AudioFile;
import fileio.input.SongInput;
import fileio.input.UserInput;

import java.util.ArrayList;

public class Playlist extends AudioFile {
    private UserInput owner;
    private boolean isPublic;
    ArrayList<SongInput> songs;
    private int followers;

    public boolean getPublic() {
        return isPublic;
    }

    public void setPublic(boolean aPublic) {
        isPublic = aPublic;
    }

    public Playlist() {
    }

    public void setOwner(UserInput owner) {
        this.owner = owner;
    }

    public UserInput getOwner() {
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


