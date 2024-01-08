package visit.pattern;

import user.types.Artist;
import user.types.Host;
import user.types.User;

public interface Visitor {
    void visit(User user);
    void visit(Artist artist);
    void visit(Host host);
}