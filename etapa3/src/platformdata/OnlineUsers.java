package platformdata;

import users.User;

import java.util.ArrayList;

public final class OnlineUsers {
    private static ArrayList<User> onlineUsers = new ArrayList<>();

    private OnlineUsers() {
    }

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
