package designpatterns.visitorPattern;

public interface Visitable {
    /**
     * Accepts the visitor
     * @param visitor the visitor
     */
    void accept(Visitor visitor);
}
