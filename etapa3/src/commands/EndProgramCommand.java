package commands;

import audio.files.Library;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import user.types.Artist;
import user.types.GhostUsers;
import user.types.User;

import java.util.ArrayList;

public class EndProgramCommand {
    private static ObjectNode commandJson;
    public static ObjectNode execute(Library library) {
        ArrayList<Artist> artists = new ArrayList<>();
        //get the list of artists for which we display the monetization report
        for(User user : library.getUsers()) {
            if(user.getType().equals("artist")) {
                if(!((Artist) user).getArtistStatistics().getTopSongs().isEmpty()
                        || !((Artist) user).getArtistStatistics().getBoughtMerch().isEmpty()) {
                    artists.add((Artist) user);
                }
            }
        }
        // add also the ghost artists
        for(Artist artist : GhostUsers.getGhostArtists()) {
            if(!artist.getArtistStatistics().getTopSongs().isEmpty()
                    || !artist.getArtistStatistics().getBoughtMerch().isEmpty()) {
                artists.add(artist);
            }
        }

        ObjectMapper objectMapper = new ObjectMapper();
        commandJson = objectMapper.createObjectNode();
        ObjectNode resultJson = objectMapper.createObjectNode();
        commandJson.put("command", "endProgram");

        // sort the artists by their total revenue
        artists.sort((artist1, artist2) -> {
            if(artist1.getSongRevenue() + artist1.getMerchRevenue() > artist2.getSongRevenue() + artist2.getMerchRevenue()) {
                return -1;
            }
            else if(artist1.getSongRevenue() + artist1.getMerchRevenue() < artist2.getSongRevenue() + artist2.getMerchRevenue()) {
                return 1;
            }
            else {
                return 0;
            }
        });

        // in case of equality, sort them by their username
        artists.sort((artist1, artist2) -> {
            if(artist1.getSongRevenue() + artist1.getMerchRevenue() == artist2.getSongRevenue() + artist2.getMerchRevenue()) {
                return artist1.getUsername().compareTo(artist2.getUsername());
            }
            else {
                return 0;
            }
        });

        for(int i = 0; i < artists.size(); i++) {
            artists.get(i).calculateMerchRevenue();
            artists.get(i).calculateSongRevenue(library);
            artists.get(i).findMostProfitableSong(library);

            ObjectNode artistJson = objectMapper.createObjectNode();
            resultJson.set(artists.get(i).getUsername(), artistJson);
            artistJson.put("merchRevenue", artists.get(i).getMerchRevenue());
            artistJson.put("songRevenue", artists.get(i).getSongRevenue());
            artistJson.put("ranking", i + 1);

            if(artists.get(i).getSongRevenue() == 0)
                artistJson.put("mostProfitableSong", "N/A");
            else
                artistJson.put("mostProfitableSong", artists.get(i).getMostProfitableSong().getName());
        }

        commandJson.set("result", resultJson);
        return commandJson;
    }
}
