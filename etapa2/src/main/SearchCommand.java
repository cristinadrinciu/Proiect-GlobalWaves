package main;
import AudioFiles.*;

import java.util.ArrayList;

public class SearchCommand {
    private String type;
    private Filter filters = new Filter();

    private static ArrayList<AudioFile> searchResults;
    private static ArrayList<User> searchUsers;

    public SearchCommand() {
    }

    // setters and getters

    /**
     * @param type the type of the search
     */
    public void setType(final String type) {
        this.type = type;
    }

    /**
     * @param filters the filters of the search
     */
    public void setFilters(final Filter filters) {
        this.filters = filters;
    }

    /**
     * @return the type of the search
     */
    public String getType() {
        return type;
    }

    /**
     * @return the filters of the search
     */
    public Filter getFilters() {
        return filters;
    }

    /**
     * @return the search results
     */
    public static ArrayList<AudioFile> getSearchResults() {
        return searchResults;
    }

    /**
     * @param searchResults the search results
     */
    public static void setSearchResults(final ArrayList<AudioFile> searchResults) {
        SearchCommand.searchResults = searchResults;
    }

    /**
     * @return the search results
     */
    public static ArrayList<User> getSearchUsers() {
        return searchUsers;
    }

    /**
     * @param searchUsers the search results
     */
    public static void setSearchUsers(final ArrayList<User> searchUsers) {
        SearchCommand.searchUsers = searchUsers;
    }

    /**
     * @param library the library
     * @param user    the user
     * @return the search results
     */
    public ArrayList<AudioFile> getSearchResults(final Library library, final User user) {

        filters.setFilterArray(filters.buildFilterArray());
        searchResults = new ArrayList<>();

        if ("song".equals(type)) {
            processSongs(library, user);
        } else if ("podcast".equals(type)) {
            processPodcast(library, user);
        } else if ("playlist".equals(type)) {
            processPlaylist(library, user);
        } else if ("album".equals(type)) {
            processAlbum();
        } else {
            throw new IllegalArgumentException("Invalid type: " + type);
        }
        final int maxSize = 5;
        if (searchResults.size() > maxSize) {
            ArrayList<AudioFile> tmp = new ArrayList<>(searchResults.subList(0, maxSize));
            searchResults.clear();
            searchResults.addAll(tmp);
        }

        return searchResults;
    }

    /**
     * @param library the library
     * @param user    the user
     * @return the search results
     */
    public ArrayList<User> getSearchUsers(final Library library, final User user) {

        filters.setFilterArray(filters.buildFilterArray());
        searchUsers = new ArrayList<>();

        if ("artist".equals(type)) {
            processArtist(library, user);
        } else if("host".equals(type)) {
            processHost(library, user);
        } else {
            throw new IllegalArgumentException("Invalid type: " + type);
        }
        final int maxSize = 5;
        if (searchUsers.size() > maxSize) {
            ArrayList<User> tmp = new ArrayList<>(searchUsers.subList(0, maxSize));
            searchUsers.addAll(tmp);
        }

        return searchUsers;
    }

    /**
     * @param library the library
     * @param user    the user
     */
    public void processSongs(final Library library, final User user) {
        ArrayList<Song> songs = new ArrayList<>(library.getSongs());

        // searchResults.addAll(songs);
        for (int i = 0; i < filters.getFilterArray().size(); i++) {
            if (filters.getFields().get(i).equals("name")) {
                final int index = i;
                songs.removeIf((v) -> !v.getName().startsWith(
                        filters.getFilterArray().get(index).toString()));
            }
            if (filters.getFields().get(i).equals("album")) {
                final int index = i;
                songs.removeIf((v) -> !v.getAlbum().equals(
                        filters.getFilterArray().get(index).toString()));
            }
            if (filters.getFields().get(i).equals("tags")) {
                final int index = i;
                songs.removeIf((v) -> !v.getTags().containsAll(
                        (ArrayList<String>) filters.getFilterArray().get(index)));
            }
            if (filters.getFields().get(i).equals("lyrics")) {
                final int index = i;
                songs.removeIf((v) -> !v.getLyrics().toLowerCase().contains(
                        filters.getFilterArray().get(index).toString().toLowerCase()));
            }
            if (filters.getFields().get(i).equals("genre")) {
                final int index = i;
                songs.removeIf((v) -> !v.getGenre().toLowerCase().equals(
                        filters.getFilterArray().get(index).toString().toLowerCase()));
            }
            if (filters.getFields().get(i).equals("releaseYear")) {
                final int index = i;
                char sign = filters.getFilterArray().get(i).toString().charAt(0);
                String numericPart = filters.getFilterArray().get(i).
                        toString().replaceAll("[^\\d]", "");
                int year = Integer.parseInt(numericPart);
                if (sign == '<') {
                    songs.removeIf((v) -> (v.getReleaseYear() > year));
                } else if (sign == '>') {
                    songs.removeIf((v) -> (v.getReleaseYear() < year));
                }
            }
            if (filters.getFields().get(i).equals("artist")) {
                final int index = i;
                songs.removeIf((v) -> !v.getArtist().equals(
                        filters.getFilterArray().get(index).toString()));
            }
        }
        searchResults.addAll(songs);
    }

    /**
     * @param library the library
     * @param user    the user
     */
    public void processPodcast(final Library library, final User user) {
        ArrayList<Podcast> podcasts = new ArrayList<>();
        podcasts.addAll(library.getPodcasts());
        for (int i = 0; i < filters.getFilterArray().size(); i++) {
            if (filters.getFields().get(i).equals("name")) {
                final int index = i;
                podcasts.removeIf((v) -> !v.getName().startsWith(
                        filters.getFilterArray().get(index).toString()));
            }
            if (filters.getFields().get(i).equals("owner")) {
                final int index = i;
                podcasts.removeIf((v) -> !(v.getOwner().equals(
                        filters.getFilterArray().get(index).toString())));
            }
        }
        searchResults.addAll(podcasts);
    }

    /**
     * @param library the library
     * @param user    the user
     */
    public void processPlaylist(final Library library, final User user) {
        ArrayList<Playlist> playLists = new ArrayList<>();
        playLists.addAll(user.getPlayer().playlists);

        for (int i = 0; i < filters.getFilterArray().size(); i++) {
            if (filters.getFields().get(i).equals("name")) {
                final int index = i;
                playLists.removeIf((v) -> !v.getName().toLowerCase().startsWith(
                        filters.getFilterArray().get(index).toString().toLowerCase()));
            }
            if (filters.getFields().get(i).equals("owner")) {
                final int index = i;
                playLists.removeIf((v) -> !(v.getOwner().getUsername().equals(
                        filters.getFilterArray().get(index).toString())));
            }
            searchResults.addAll(playLists);

            // sort the playlists by created time
            searchResults.sort((v1, v2) -> {
                if (((Playlist) v1).getCreatedTimestamp()
                        == ((Playlist) v2).getCreatedTimestamp()) {
                    return 0;
                }
                return ((Playlist) v1).getCreatedTimestamp()
                        < ((Playlist) v2).getCreatedTimestamp() ? -1 : 1;
            });
            break;
        }
    }

    public void processAlbum() {
        ArrayList<Album> albums = new ArrayList<>();
        albums.addAll(PublicAlbums.getPublicAlbums());

        for (int i = 0; i < filters.getFilterArray().size(); i++) {
            if (filters.getFields().get(i).equals("name")) {
                final int index = i;
                albums.removeIf((v) -> !v.getName().toLowerCase().startsWith(
                        filters.getFilterArray().get(index).toString().toLowerCase()));
            }
            if (filters.getFields().get(i).equals("owner")) {
                final int index = i;
                albums.removeIf((v) -> !(v.getOwner().getUsername().startsWith(
                        filters.getFilterArray().get(index).toString())));
            }
            if (filters.getFields().get(i).equals("description")) {
                final int index = i;
                albums.removeIf((v) -> !v.getDescription().toLowerCase().startsWith(
                        filters.getFilterArray().get(index).toString().toLowerCase()));
            }
        }
        searchResults.addAll(albums);
    }

    /**
     * @param library the library
     * @param user    the user
     * @return the search results
     */
    public void processArtist(final Library library, final User user) {
        ArrayList<User> users = new ArrayList<>();
        ArrayList<Artist> artists = new ArrayList<>();

        users.addAll(library.getUsers());
        for (User u : users) {
            if (u.getType().equals("artist")) {
                artists.add((Artist) u);
            }
        }

        for (int i = 0; i < filters.getFilterArray().size(); i++) {
            if (filters.getFields().get(i).equals("name")) {
                final int index = i;
                artists.removeIf((v) -> !v.getUsername().toLowerCase().startsWith(
                        filters.getFilterArray().get(index).toString().toLowerCase()));
            }
        }
        searchUsers.addAll(artists);
    }

    /**
     * @param library the library
     * @param user    the user
     * @return the search results
     */
    public void processHost(final Library library, final User user) {
        ArrayList<User> users = new ArrayList<>();
        ArrayList<Host> hosts = new ArrayList<>();

        users.addAll(library.getUsers());
        for (User u : users) {
            if (u.getType().equals("host")) {
                hosts.add((Host) u);
            }
        }

        for (int i = 0; i < filters.getFilterArray().size(); i++) {
            if (filters.getFields().get(i).equals("name")) {
                final int index = i;
                hosts.removeIf((v) -> !v.getUsername().toLowerCase().startsWith(
                        filters.getFilterArray().get(index).toString().toLowerCase()));
            }
        }
        searchUsers.addAll(hosts);
    }
}

