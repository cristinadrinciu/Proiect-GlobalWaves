package commands;
import audio.files.*;
import audio.files.*;
import page.content.Announcement;
import main.*;
import pages.HomePage;
import player.Player;
import publicFiles.OnlineUsers;
import user.types.Artist;
import user.types.Host;
import user.types.User;
import visit.pattern.Visitable;
import visit.pattern.Visitor;

import java.util.ArrayList;

public class AddUserCommand implements Visitable {
	private String username;
	private int age;
	private String city;

	private String type;

	private String message;

	public AddUserCommand() {
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getMessage() {
		return this.message;
	}

	public void addUser(Library library) {
		// check if the user already exists
		for (User user : library.getUsers()) {
			if (user.getUsername().equals(this.username)) {
				this.message = "The username " + this.username + " is already taken.";
				return;
			}
		}

		if (this.type.equals("user")) {
			User user = new User();
			user.setUsername(this.username);
			user.setAge(this.age);
			user.setCity(this.city);
			user.setType("user");
			user.setStatusOnline(true);
			user.setPlayer(new Player());
			user.setPlaylists(new ArrayList<Playlist>());
			user.setLikedSongs(new ArrayList<Song>());
			user.setFollowedPlaylists(new ArrayList<Playlist>());
			user.setLastSearch(new ArrayList<AudioFile>());
			user.setLastSearchUsers(new ArrayList<User>());
			user.setHomePage(new HomePage());
			user.setCurrentPage(user.getHomePage());

			// add the user to the library
			library.getUsers().add(user);

			// add the user to the OnlineUsers
			OnlineUsers.getOnlineUsers().add(user);
		}
		if (this.type.equals("artist")) {
			Artist user = new Artist();
			user.setUsername(this.username);
			user.setAge(this.age);
			user.setCity(this.city);
			user.setType("artist");
			user.setStatusOnline(true);
			user.setPlayer(new Player());
			user.setPlaylists(new ArrayList<Playlist>());
			user.setLikedSongs(new ArrayList<Song>());
			user.setFollowedPlaylists(new ArrayList<Playlist>());
			user.setAlbums(new ArrayList<Album>());
			user.setHomePage(new HomePage());
			user.setCurrentPage(user.getHomePage());

			// add the artist to the library
			library.getUsers().add(user);
		}
		if(this.type.equals("host")) {
			Host user = new Host();
			user.setUsername(this.username);
			user.setAge(this.age);
			user.setCity(this.city);
			user.setType("host");
			user.setStatusOnline(true);
			user.setPlayer(new Player());
			user.setPlaylists(new ArrayList<Playlist>());
			user.setLikedSongs(new ArrayList<Song>());
			user.setFollowedPlaylists(new ArrayList<Playlist>());
			user.setPodcasts(new ArrayList<Podcast>());
			user.setAnnouncements(new ArrayList<Announcement>());
			user.setHomePage(new HomePage());
			user.setCurrentPage(user.getHomePage());

			// add the host to the library
			library.getUsers().add(user);
		}

		this.message = "The username " + this.username + " has been added successfully.";
	}

	@Override
	public void accept(InputCommands command, Visitor visitor, Library library) {
		visitor.visit(command, this, library);
	}
}
