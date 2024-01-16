package statistics;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class HostStatistics {
    private HashMap<String, Integer> topEpisodes;
    private HashMap<String, Integer> topFans;
    private int listeners; // the number of listeners

    public HostStatistics() {
        topEpisodes = new HashMap<>();
        topFans = new HashMap<>();
        listeners = 0;
    }

    /**
     * @return the hashmap of top episodes
     */
    public HashMap<String, Integer> getTopEpisodes() {
        return topEpisodes;
    }

    /**
     * @param topEpisodes the hashmap of top episodes to set
     */
    public void setTopEpisodes(final HashMap<String, Integer> topEpisodes) {
        this.topEpisodes = topEpisodes;
    }

    /**
     * @return the number of listeners
     */
    public int getListeners() {
        return listeners;
    }

    /**
     */
    public void setListeners() {
        this.listeners = topFans.size();
    }

    /**
     * @return the hashmap of top fans
     */
    public HashMap<String, Integer> getTopFans() {
        return topFans;
    }

    /**
     * @param topFans the hashmap of top fans to set
     */
    public void setTopFans(final HashMap<String, Integer> topFans) {
        this.topFans = topFans;
    }

    /**
     * This method returns the top episodes
     */
    public ArrayList<String> topEpisodes() {
        ArrayList<String> topEpisodes1 = new ArrayList<>();

        // get the list of podcasts from the hashmap
        List<String> episodes = new ArrayList<>(this.topEpisodes.keySet());

        // sort the list of podcasts by the number of listens they have (the value in the hashmap)
        episodes.sort((episode1, episode2) -> this.topEpisodes.get(episode2)
                - this.topEpisodes.get(episode1));

        // in case of a tie, sort by name
        episodes.sort((episode1, episode2) -> {
            if (this.topEpisodes.get(episode1).equals(this.topEpisodes.get(episode2))) {
                return episode1.compareTo(episode2);
            }
            return 0;
        });

        final int maxEpisodes = 5;
        // add the top 5 podcasts to the list or all of them if there are less than 5
        if (episodes.size() > maxEpisodes) {
            for (int i = 0; i < maxEpisodes; i++) {
                topEpisodes1.add(episodes.get(i));
            }
        } else {
            topEpisodes1.addAll(episodes);
        }

        return topEpisodes1;
    }
}
