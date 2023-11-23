package main;
import fileio.input.*;
import main.Filter;

import java.time.format.SignStyle;
import java.util.ArrayList;

public class SearchCommand {
    private String type;
    private Filter filters = new Filter();

    public static ArrayList<AudioFile> searchResults;

    // setters and getters
    public void setType(String type) {
        this.type = type;
    }

    public SearchCommand() {
    }

    public void setFilters(Filter filters) {
        this.filters = filters;
    }

    public String getType() {
        return type;
    }

    public Filter getFilters() {
        return filters;
    }

    public ArrayList<AudioFile> getSearchResults(Library library, User user) {

        filters.filterArray = filters.buildFilterArray();
        searchResults = new ArrayList<>();

        switch (type) {
            case "song": {
                ArrayList<Song> songs = new ArrayList<>();
                songs.addAll(library.getSongs());

                // searchResults.addAll(songs);
                for (int i = 0; i < filters.filterArray.size(); i++) {
                    if (filters.fields.get(i).equals("name")) {
                        final int index = i;
                        songs.removeIf((v) -> !v.getName().startsWith(filters.filterArray.get(index).toString()));
                    }
                    if (filters.fields.get(i).equals("album")) {
                        final int index = i;
                        songs.removeIf((v) -> !v.getAlbum().equals(filters.filterArray.get(index).toString()));
                    }
                    if (filters.fields.get(i).equals("tags")) {
                        final int index = i;
                        songs.removeIf((v) -> !v.getTags().containsAll((ArrayList<String>) filters.filterArray.get(index)));
                    }
                    if (filters.fields.get(i).equals("lyrics")) {
                        final int index = i;
                        songs.removeIf((v) -> !v.getLyrics().toLowerCase().contains(filters.filterArray.get(index).toString().toLowerCase()));
                    }
                    if (filters.fields.get(i).equals("genre")) {
                        final int index = i;
                        songs.removeIf((v) -> !v.getGenre().toLowerCase().equals(filters.filterArray.get(index).toString().toLowerCase()));
                    }
                    if (filters.fields.get(i).equals("releaseYear")) {
                        final int index = i;
                        char sign = filters.filterArray.get(i).toString().charAt(0);
                        String numericPart = filters.filterArray.get(i).toString().replaceAll("[^\\d]", "");
                        int year = Integer.parseInt(numericPart);
                        if (sign == '<')
                            songs.removeIf((v) -> (v.getReleaseYear() > year));
                        else if (sign == '>')
                            songs.removeIf((v) -> (v.getReleaseYear() < year));
                    }
                    if (filters.fields.get(i).equals("artist")) {
                        final int index = i;
                        songs.removeIf((v) -> !v.getArtist().equals(filters.filterArray.get(index).toString()));
                    }
                }
                searchResults.addAll(songs);
                break;
            }
            case "podcast": {
                ArrayList<Podcast> podcasts = new ArrayList<>();
                podcasts.addAll(library.getPodcasts());
                for (int i = 0; i < filters.filterArray.size(); i++) {
                    if (filters.fields.get(i).equals("name")) {
                        final int index = i;
                        podcasts.removeIf((v) -> !v.getName().startsWith(filters.filterArray.get(index).toString()));
                    }
                    if (filters.fields.get(i).equals("owner")) {
                        final int index = i;
                        podcasts.removeIf((v) -> !(v.getOwner().equals(filters.filterArray.get(index).toString())));
                    }
                }
                searchResults.addAll(podcasts);
                break;
            }
            case "playlist": {
                ArrayList<Playlist> playLists = new ArrayList<>();
                playLists.addAll(user.player.playlists);
                for(int i = 0; i < filters.filterArray.size(); i++) {
                    if(filters.fields.get(i).equals("name")) {
                        final int index = i;
                        playLists.removeIf((v) -> !v.getName().toLowerCase().startsWith(filters.filterArray.get(index).toString().toLowerCase()));
                    }
                    if (filters.fields.get(i).equals("owner")) {
                        final int index = i;
                        playLists.removeIf((v) -> !(v.getOwner().getUsername().equals(filters.filterArray.get(index).toString())));
                    }
                    searchResults.addAll(playLists);
                    break;
                }

                break;
            }
        }
        if (searchResults.size() > 5) {
            ArrayList<AudioFile> tmp = new ArrayList<>(searchResults.subList(0, 5));
            searchResults.clear();
            searchResults.addAll(tmp);
        }

        return searchResults;
    }
}

