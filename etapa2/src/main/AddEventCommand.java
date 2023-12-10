package main;

import AudioFiles.Artist;
import AudioFiles.Event;
import AudioFiles.User;

import java.util.ArrayList;

public class AddEventCommand {
	private String name;
	private String description;
	private String date;
	private String message;

	public AddEventCommand() {
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getMessage() {
		return this.message;
	}

	public void addEvent(User user) {
		// check if user is an artist
		if(!user.getType().equals("artist")) {
			message = user.getUsername() + " is not an artist.";
			return;
		}

		// check if there is already an event with the same name
		ArrayList<Event> events = ((Artist) user).getEvents();
		for(Event event : events) {
			if (event.getName().equals(name)) {
				message = user.getUsername() + " has another event with the same name.";
				return;
			}
		}

		// check the date;
		if (!VerifyDate.isValid(date)) {
			message = "Event for "  + user.getUsername() + " does not have a valid date.";
			return;
		}

		// add the event
		Event event = new Event();
		event.setName(name);
		event.setDescription(description);
		event.setDate(date);
		events.add(event);
		message = user.getUsername() + " has added new event successfully.";

		// update the artist page
		((Artist) user).setArtistPage();
	}

	private static class VerifyDate {
		public static boolean isValid(String date) {
			// date = "dd-mm-yyyy", verify if the date is valid
			// check if the date is in the correct format
			if(!date.matches("\\d{2}-\\d{2}-\\d{4}")) {
				return false;
			}

			// check if the date is valid
			String[] dateParts = date.split("-");
			int day = Integer.parseInt(dateParts[0]);
			int month = Integer.parseInt(dateParts[1]);
			int year = Integer.parseInt(dateParts[2]);
			if(month > 12 || month < 1 || year < 1900 || year > 2023) {
				return false;
			}

			// check if the day is valid
			if(month == 2) {
				if(day > 28) {
					return false;
				}
			} else if(month == 4 || month == 6 || month == 9 || month == 11) {
				if(day > 30) {
					return false;
				}
			} else {
				if(day > 31) {
					return false;
				}
			}

			return true;
		}
	}
}
