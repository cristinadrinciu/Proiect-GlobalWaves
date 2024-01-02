package commands;

import visit.pattern.Visitable;
import visit.pattern.Visitor;

public class CancelPremiumCommand implements Visitable {
    private String message;

    public CancelPremiumCommand() {
    }

    public String getMessage() {
        return message;
    }

    public void cancelPremium(user.types.User user) {
        if(!user.isPremium()) {
            message = user.getUsername() + " is not a premium user.";
        } else {
            user.setPremium(false);
            message = user.getUsername() + " cancelled the subscription successfully.";
        }
    }

    @Override
    public void accept(main.InputCommands command, Visitor visitor, audio.files.Library library) {
        visitor.visit(command, this, library);
    }
}
