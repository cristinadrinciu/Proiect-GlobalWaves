package commands;

import audio.files.Library;
import main.InputCommands;
import user.types.User;
import visit.pattern.Visitable;
import visit.pattern.Visitor;

public class BuyPremiumCommand implements Visitable {
    private String message;

    public BuyPremiumCommand() {
    }

    public String getMessage() {
        return message;
    }

    public void buyPremium(User user) {
        if(user.isPremium()) {
            message = user.getUsername() + " is already a premium user.";
        } else {
            user.setPremium(true);
            message = user.getUsername() + " bought the subscription successfully.";
        }
    }

    @Override
    public void accept(InputCommands command, Visitor visitor, Library library) {
        visitor.visit(command, this, library);
    }
}
