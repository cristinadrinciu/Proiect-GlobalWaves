package audio.files;

import fileio.input.LibraryInput;
import fileio.input.PodcastInput;
import fileio.input.SongInput;
import fileio.input.UserInput;
import player.Player;
import user.types.User;

import java.util.ArrayList;

public class Library {
    private ArrayList<Song> songs;
    private ArrayList<Podcast> podcasts;
    private ArrayList<User> users;

    public Library() {
    }

    /**
     * @return the songs
     */
    public ArrayList<Song> getSongs() {
        return songs;
    }

    /**
     * @param songs the songs to set
     */
    public void setSongs(final ArrayList<Song> songs) {
        this.songs = songs;
    }

    /**
     * @return the podcasts
     */
    public ArrayList<Podcast> getPodcasts() {
        return podcasts;
    }

    /**
     * @param podcasts the podcasts to set
     */
    public void setPodcasts(final ArrayList<Podcast> podcasts) {
        this.podcasts = podcasts;
    }

    /**
     * @return the users
     */
    public ArrayList<User> getUsers() {
        return users;
    }

    /**
     * @param users the users to set
     */
    public void setUsers(final ArrayList<User> users) {
        this.users = users;
    }

    /**
     * @param library the library to create
     */
    public void createLibrary(final LibraryInput library) {
        songs = new ArrayList<>();
        podcasts = new ArrayList<>();
        users = new ArrayList<>();

        for (SongInput song : library.getSongs()) {
            Song newSong = new Song();
            newSong.setName(song.getName());
            newSong.setArtist(song.getArtist());
            newSong.setAlbum(song.getAlbum());
            newSong.setReleaseYear(song.getReleaseYear());
            newSong.setTags(song.getTags());
            newSong.setDuration(song.getDuration());
            newSong.setLyrics(song.getLyrics());
            newSong.setGenre(song.getGenre());

            songs.add(newSong);
        }

        for (PodcastInput podcast : library.getPodcasts()) {
            Podcast newPodcast = new Podcast();
            newPodcast.setName(podcast.getName());
            newPodcast.setOwner(podcast.getOwner());
            newPodcast.setEpisodes(podcast.getEpisodes());

            podcasts.add(newPodcast);
        }

        for (UserInput user : library.getUsers()) {
            User newUser = new User();
            newUser.setUsername(user.getUsername());
            newUser.setAge(user.getAge());
            newUser.setCity(user.getCity());
            newUser.setType("user");
            newUser.setStatusOnline(true);
            newUser.setHomePage();
            newUser.setCurrentPage(newUser.getHomePage());
            newUser.getNavigationHistory().add(newUser.getHomePage());
            newUser.setPlayer(Player.getInstance(newUser, this));
            users.add(newUser);
        }
    }
}
