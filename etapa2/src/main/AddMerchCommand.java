package main;

import AudioFiles.Artist;
import AudioFiles.Merch;
import AudioFiles.User;

import java.util.ArrayList;

public class AddMerchCommand {
	private String name;
	private String description;
	private int price;
	private String message;

	public AddMerchCommand() {
	}

	public String getName() {
		return name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(final String description) {
		this.description = description;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(final int price) {
		this.price = price;
	}

	public String getMessage() {
		return message;
	}

	public void addMerch(User user) {
		// check if user is an artist
		if(!user.getType().equals("artist")) {
			message = user.getUsername() + " is not an artist.";
			return;
		}

		// check if there is already a merch with the same name
		ArrayList<Merch> merch = ((Artist) user).getMerch();
		for(Merch item : merch) {
			if (item.getName().equals(name)) {
				message = user.getUsername() + " has merchandise with the same name.";
				return;
			}
		}

		if(price < 0) {
			message = "Price for merchandise can not be negative.";
			return;
		}

		// create the merch
		Merch item = new Merch();
		item.setName(name);
		item.setDescription(description);
		item.setPrice(price);

		// add the merch to the artist's merchs
		merch.add(item);
		message = user.getUsername() + " has added new merchandise successfully.";

		// update the artist page
		((Artist) user).setArtistPage();
	}
}
