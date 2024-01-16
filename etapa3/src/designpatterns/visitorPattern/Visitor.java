package designpatterns.visitorPattern;

import users.Artist;
import users.Host;
import users.User;

public interface Visitor {
    /**
     * Visit a user
     * @param user the user to be visited
     */
    void visit(User user);

    /**
     * Visit an artist
     * @param artist the artist to be visited
     */
    void visit(Artist artist);

    /**
     * Visit a host
     * @param host the host to be visited
     */
    void visit(Host host);
}
