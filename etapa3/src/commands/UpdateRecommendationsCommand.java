package commands;

import audioFiles.Library;
import audioFiles.Playlist;
import audioFiles.Song;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import designPatterns.commandPattern.Command;
import main.InputCommands;
import users.Artist;
import users.User;

import java.util.ArrayList;
import java.util.Random;

public class UpdateRecommendationsCommand implements Command {
    private String recommendationType;
    private String message;

    public UpdateRecommendationsCommand() {
    }

    /**
     * @return the recommendationType
     */
    public String getRecommendationType() {
        return recommendationType;
    }

    /**
     * @param recommendationType the recommendationType to set
     */
    public void setRecommendationType(final String recommendationType) {
        this.recommendationType = recommendationType;
    }

    /**
     * @return the message
     */
    public String getMessage() {
        return message;
    }

    /**
     * This method updates the recommendations for a user for type fans playlist
     * @param user the user to update the recommendations for
     */
    public void updateFansPlaylist(final User user, final Library library) {
        if (!user.getType().equals("user")) {
            message = user.getUsername() + " is not a normal user.";
            return;
        }

        // create a new Playlist
        Playlist playlist = new Playlist();
        playlist.setOwner(user);

        // get the artist of the current song
        Artist artist = user.getPlayer().findArtist(((Song) user.getPlayer().playingNow));
        playlist.setName(artist.getUsername() + " Fan Club recommendations");

        // get the top 5 fans of the artist
        ArrayList<String> topFansNames = artist.getArtistStatistics().topFans();

        // get the users from the top fans names
        ArrayList<User> topFans = new ArrayList<>();
        for (String fanName : topFansNames) {
            // find the user in the library
            for (User user1 : library.getUsers()) {
                if (user1.getUsername().equals(fanName)) {
                    topFans.add(user1);
                    break;
                }
            }
        }

        // for each fan, get the top 5 Songs from likedSongs
        ArrayList<Song> topSongs = new ArrayList<>();
        for (User fan : topFans) {
            // get the top 5 songs from the fan's likedSongs
            ArrayList<Song> fanTopSongs = new ArrayList<>(fan.getLikedSongs());

            // remove the songs from fanTopSongs that are already added in topSongs
            ArrayList<Song> songsToRemove = new ArrayList<>();
            for (Song song : fanTopSongs) {
                if (topSongs.contains(song)) {
                    songsToRemove.add(song);
                }
            }
            fanTopSongs.removeAll(songsToRemove);

            // sort the list by the number of likes of the songs
            fanTopSongs.sort((song1, song2) -> song2.getLikes() - song1.getLikes());

            final int maxSize1 = 5;
            // get the top 5 or all of them if there are less than 5
            if (fanTopSongs.size() > maxSize1) {
                for (int i = 0; i < maxSize1; i++) {
                    topSongs.add(fanTopSongs.get(i));
                }
            } else {
                topSongs.addAll(fanTopSongs);
            }
        }

        if (topSongs.isEmpty()) {
            message = "No new recommendations were found";
            return;
        }

        // add the songs to the playlist
        playlist.getSongs().addAll(topSongs);
        playlist.setCreatedTimestamp(user.getPlayer().timestamp);

        // add the playlist to the user's playlists
        user.getPlaylists().add(playlist);

        // add to the recommendations list in the homepage
        user.getHomePage().getPlaylistRecommendations().add(playlist);

        // set the last recommendation
        user.setLastRecommendation(playlist);

        message = "The recommendations for user " + user.getUsername()
                + " have been updated successfully.";
    }

    /**
     * This method updates the recommendations for a user for type random song
     * @param user the user to update the recommendations for
     */
    public void updateRandomSong(final User user, final Library library) {
        if (!user.getType().equals("user")) {
            message = user.getUsername() + " is not a normal user.";
            return;
        }
        if (user.getPlayer().loadedItem == null) {
            message = "No new recommendations were found";
            return;
        }
        final int listenedTime = 30;
        if (user.getPlayer().listenedTime < listenedTime) {
            message = "No new recommendations were found";
            return;
        }

        // get the genre of the song
        String genre = ((Song) user.getPlayer().loadedItem).getGenre();

        // find all the songs from the library with the same genre
        ArrayList<Song> songs = new ArrayList<>();

        for (Song song : library.getSongs()) {
            if (song.getGenre().equals(genre)) {
                songs.add(song);
            }
        }

        // generate a random song with the see of the listened time
        int seed = user.getPlayer().listenedTime;

        // get the random song with Random
        Song randomSong = songs.get(new Random(seed).nextInt(songs.size()));

        // add the song to the recommendations list in the homepage
        user.getHomePage().getSongRecommendations().add(randomSong);

        // set the last recommendation
        user.setLastRecommendation(randomSong);

        message = "The recommendations for user " + user.getUsername()
                + " have been updated successfully.";
    }

    /**
     * This method updates the recommendations for a user for type random Playlist
     * @param user the user to update the recommendations for
     */
    public void updateRandomPlaylist(final User user, final Library library) {
        if (!user.getType().equals("user")) {
            message = user.getUsername() + " is not a normal user.";
            return;
        }

        // create a playlist
        Playlist playlist = new Playlist();
        playlist.setOwner(user);
        playlist.setName(user.getUsername() + "'s recommendations");

        // get the top 3 genre from songs from liked songs,
        // created playlists and playlists that the user followed
        ArrayList<String> topGenres = new ArrayList<>();
        ArrayList<String> genres = new ArrayList<>();

        for (Song song : user.getLikedSongs()) {
            genres.add(song.getGenre());
        }
        for (Playlist playlist1 : user.getPlaylists()) {
            for (Song song : playlist1.getSongs()) {
                genres.add(song.getGenre());
            }
        }
        for (Playlist playlist1 : user.getFollowedPlaylists()) {
            for (Song song : playlist1.getSongs()) {
                genres.add(song.getGenre());
            }
        }

        // sort the list by the number of occurrences of the genres
        genres.sort((genre1, genre2) -> {
            int occurrences1 = 0;
            int occurrences2 = 0;
            for (String genre : genres) {
                if (genre.equals(genre1)) {
                    occurrences1++;
                }
                if (genre.equals(genre2)) {
                    occurrences2++;
                }
            }
            return occurrences2 - occurrences1;
        });

        final int maxSize = 3;
        // get the top 3 genres or all of them if there are less than 3
        if (genres.size() > maxSize) {
            for (int i = 0; i < maxSize; i++) {
                topGenres.add(genres.get(i));
            }
        } else {
            topGenres.addAll(genres);
        }

        if (topGenres.isEmpty()) {
            message = "No new recommendations were found";
            return;
        }

        ArrayList<Song> playlistSongs = new ArrayList<>();
        // for the first genre, get top 5 songs of the genre based on song's number of likes
        // for the second genre, get top 3 songs of the genre based on song's number of likes
        // for the third genre, get top 2 songs of the genre based on song's number of likes

        // get the songs from the first genre
        ArrayList<Song> songs = new ArrayList<>();
        for (Song song : library.getSongs()) {
            if (song.getGenre().equals(topGenres.get(0))) {
                songs.add(song);
            }
        }
        // sort the list by the number of likes of the songs
        songs.sort((song1, song2) -> song2.getLikes() - song1.getLikes());

        final int maxSize1 = 5;
        // get the top 5 or all of them if there are less than 5
        if (songs.size() > maxSize1) {
            for (int i = 0; i < maxSize1; i++) {
                playlistSongs.add(songs.get(i));
            }
        } else {
            playlistSongs.addAll(songs);
        }

        // get the songs from the second genre if there is one
        if (topGenres.size() > 1) {
            songs.clear();
            for (Song song : library.getSongs()) {
                if (song.getGenre().equals(topGenres.get(1))) {
                    songs.add(song);
                }
            }
            // sort the list by the number of likes of the songs
            songs.sort((song1, song2) -> song2.getLikes() - song1.getLikes());

            final int maxSize2 = 3;
            // get the top 3 or all of them if there are less than 3
            if (songs.size() > maxSize2) {
                for (int i = 0; i < maxSize2; i++) {
                    playlistSongs.add(songs.get(i));
                }
            } else {
                playlistSongs.addAll(songs);
            }
        }

        // get the songs from the third genre if there is one
        if (topGenres.size() > 2) {
            songs.clear();
            for (Song song : library.getSongs()) {
                if (song.getGenre().equals(topGenres.get(2))) {
                    songs.add(song);
                }
            }
            // sort the list by the number of likes of the songs
            songs.sort((song1, song2) -> song2.getLikes() - song1.getLikes());

            final int maxSize3 = 2;
            // get the top 2 or all of them if there are less than 2
            if (songs.size() > maxSize3) {
                for (int i = 0; i < maxSize3; i++) {
                    playlistSongs.add(songs.get(i));
                }
            } else {
                playlistSongs.addAll(songs);
            }
        }

        // add the songs to the playlist
        playlist.getSongs().addAll(playlistSongs);
        playlist.setCreatedTimestamp(user.getPlayer().timestamp);

        // add the playlist to the user's playlists
        user.getPlaylists().add(playlist);

        // add to the recommendations list in the homepage
        user.getHomePage().getPlaylistRecommendations().add(playlist);

        // set the last recommendation
        user.setLastRecommendation(playlist);

        message = "The recommendations for user " + user.getUsername()
                + " have been updated successfully.";
    }

    /**
     * Execute the command
     * @param command the command to execute
     * @param library the library to execute the command on
     */
    @Override
    public void execute(final InputCommands command, final Library library) {
        User user = command.getUser();

        // update the player for the user
        if (user.getPlayer().loadedItem != null) {
            if (user.getPlayer().repeatState == 0) {
                user.getPlayer().setRemainingTime();
            }
            if (user.getPlayer().repeatState == 1) {
                user.getPlayer().setRemainingTimeRepeat1();
            }
            if (user.getPlayer().repeatState == 2) {
                user.getPlayer().setRemainingTimeRepeat2();
            }
        }

        if (recommendationType.equals("fans_playlist")) {
            updateFansPlaylist(user, library);
        } else if (recommendationType.equals("random_song")) {
            updateRandomSong(user, library);
        } else if (recommendationType.equals("random_playlist")) {
            updateRandomPlaylist(user, library);
        }

        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode commandJson = objectMapper.createObjectNode()
                .put("command", "updateRecommendations")
                .put("user", command.getUsername())
                .put("timestamp", command.getTimestamp())
                .put("message", message);
        command.getCommandList().add(commandJson);
    }
}
