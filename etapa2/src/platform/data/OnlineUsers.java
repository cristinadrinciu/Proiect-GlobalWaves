package platform.data;

import user.types.User;

import java.util.ArrayList;

public class OnlineUsers {
    private static ArrayList<User> onlineUsers = new ArrayList<>();


    /**
     * @return the onlineUsers
     */
    public static ArrayList<User> getOnlineUsers() {
        return onlineUsers;
    }

    /**
     * @param onlineUsers the onlineUsers to set
     */
    public static void setOnlineUsers(final ArrayList<User> onlineUsers) {
        OnlineUsers.onlineUsers = onlineUsers;
    }
}
