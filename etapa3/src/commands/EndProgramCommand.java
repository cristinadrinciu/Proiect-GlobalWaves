package commands;

import stream.JsonOutputStream;
import audiofiles.Library;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import platformdata.OnlineUsers;
import users.Artist;
import users.User;

import java.util.ArrayList;

public final class EndProgramCommand {
    private static ObjectNode commandJson;

    private EndProgramCommand() {
    }

    /**
     * @param library the library of the application
     * @param timestamp the current timestamp
     * @return the json with the result of the command
     */
    public static void execute(final Library library, final int timestamp) {
        ArrayList<Artist> artists = new ArrayList<>();
        //get the list of artists for which we display the monetization report
        for (User user : library.getUsers()) {
            if (user.getType().equals("artist")) {
                if (!((Artist) user).getArtistStatistics().getTopSongs().isEmpty()
                        || ((Artist) user).getMerchRevenue() != 0) {
                    artists.add((Artist) user);
                }
            }
        }

        ObjectMapper objectMapper = new ObjectMapper();
        commandJson = objectMapper.createObjectNode();
        ObjectNode resultJson = objectMapper.createObjectNode();
        commandJson.put("command", "endProgram");

        // update the player for each user
        for (User user1 : OnlineUsers.getOnlineUsers()) {
            user1.getPlayer().timestamp = timestamp;
            if (user1.getPlayer().repeatState == 0) {
                user1.getPlayer().setRemainingTime();
            } else if (user1.getPlayer().repeatState == 1) {
                user1.getPlayer().setRemainingTimeRepeat1();
            } else if (user1.getPlayer().repeatState == 2) {
                user1.getPlayer().setRemainingTimeRepeat2();
            }
        }

        // calculate the revenue for each artist
        for (Artist artist : artists) {
            // calculate the song revenue from each user
            for (User user : library.getUsers()) {
                if (user.isPremium()) {
                    artist.calculateSongRevenue(user);
                }
            }

            // calculate the sum of the song revenue
            double songRevenue = 0.0;
            for (Double revenue : artist.getArtistStatistics().getSongsRevenue().values()) {
                songRevenue += revenue;
            }

            artist.setSongRevenue(songRevenue);
        }


        // sort the artists by their total revenue
        artists.sort((artist1, artist2) -> {
            if (artist1.getSongRevenue() + artist1.getMerchRevenue() > artist2.getSongRevenue()
                    + artist2.getMerchRevenue()) {
                return -1;
            } else if (artist1.getSongRevenue() + artist1.getMerchRevenue() < artist2.
                    getSongRevenue() + artist2.getMerchRevenue()) {
                return 1;
            } else {
                return 0;
            }
        });

        // in case of equality, sort them by their username
        artists.sort((artist1, artist2) -> {
            if (artist1.getSongRevenue() + artist1.getMerchRevenue() == artist2.getSongRevenue()
                    + artist2.getMerchRevenue()) {
                return artist1.getUsername().compareTo(artist2.getUsername());
            } else {
                return 0;
            }
        });

        for (int i = 0; i < artists.size(); i++) {
            ObjectNode artistJson = objectMapper.createObjectNode();
            resultJson.set(artists.get(i).getUsername(), artistJson);
            final double round = 100.0;
            artistJson.put("merchRevenue", artists.get(i).getMerchRevenue());
            artistJson.put("songRevenue",
                    Math.round(artists.get(i).getSongRevenue() * round) / round);
            artistJson.put("ranking", i + 1);

            if (artists.get(i).getSongRevenue() == 0 || artists.get(i).
                    getMostProfitableSong() == null) {
                artistJson.put("mostProfitableSong", "N/A");
            } else {
                artistJson.put("mostProfitableSong", artists.get(i).getMostProfitableSong());
            }
        }

        commandJson.set("result", resultJson);

        // Add the JSONPObject to the JsonOutputStream
        JsonOutputStream.addJsonNode(commandJson);
    }
}
