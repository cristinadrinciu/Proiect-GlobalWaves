package commands;

import audio.files.Library;
import main.InputCommands;
import page.content.Merch;
import pages.ArtistPage;
import user.types.Artist;
import user.types.User;
import visit.pattern.Visitable;
import visit.pattern.Visitor;

public class BuyMerch implements Visitable {
    private String name;
    private String message;

    public BuyMerch() {
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(final String name) {
        this.name = name;
    }

    /**
     * @return the message
     */
    public String getMessage() {
        return message;
    }

    public void buyMerch(User user) {
        // check if the user is on an artist page
        if(!(user.getCurrentPage() instanceof ArtistPage)) {
            message = "Cannot buy merch from this page.";
            return;
        }

        // check if the merch exists on this page
        ArtistPage artistPage = (ArtistPage) user.getCurrentPage();
        if(artistPage.getMerch().stream().noneMatch(merch -> merch.getName().equals(name))) {
            message = "The merch " + name + " doesn't exist.";
            return;
        }

        // buy the merch
        user.getBoughtMerch().add(name);

        // update the artist's revenue
        Artist artist = artistPage.getArtist();
        Merch merch = artistPage.getMerch().stream().filter(m -> m.getName().equals(name)).findFirst().get();
        artist.setMerchRevenue(artist.getMerchRevenue() + merch.getPrice());

        message = user.getUsername() + " has added new merch successfully.";
    }

    @Override
    public void accept(final InputCommands command, final Visitor visitor, final Library library) {
        visitor.visit(command, this, library);
    }
}
