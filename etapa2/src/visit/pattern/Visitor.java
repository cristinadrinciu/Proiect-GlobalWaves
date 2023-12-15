package visit.pattern;

import audio.files.Library;
import commands.*;
import main.InputCommands;

public interface Visitor {
	public void visit(InputCommands command, AddAlbumCommand addAlbumCommand, Library library);
	public void visit(InputCommands command, AddAnnouncementCommand addAnnouncementCommand, Library library);
	public void visit(InputCommands command, AddEventCommand addEventCommand, Library library);
	public void visit(InputCommands command, AddMerchCommand addMerchCommand, Library library);
	public void visit(InputCommands command, AddPodcastCommand addPodcastCommand, Library library);
	public void visit(InputCommands command, AddRemoveCommand addSongCommand, Library library);
	public void visit(InputCommands command, AddUserCommand addUserCommand, Library library);
	public void visit(InputCommands command, BackwardCommand backwardCommand, Library library);
	public void visit(InputCommands command, ChangePageCommand changePageCommand, Library library);
	public void visit(InputCommands command, CreatePlaylistCommand createPlaylistCommand, Library library);
	public void visit(InputCommands command, DeleteUser deleteUserCommand, Library library);
	public void visit(InputCommands command, FollowCommand followCommand, Library library);
	public void visit(InputCommands command, ForwardCommand forwardCommand, Library library);
	public void visit(InputCommands command, GetAllUsers getAllUsers, Library library);
	public void visit(InputCommands command, GetOnlineUsers getOnlineUsers, Library library);
	public void visit(InputCommands command, GetTop5Albums getTop5Albums, Library library);
	public void visit(InputCommands command, GetTop5Artists getTop5Artists, Library library);
	public void visit(InputCommands command, GetTop5Playlists getTop5Playlists, Library library);
	public void visit(InputCommands command, GetTop5Songs getTop5Podcasts, Library library);
	public void visit(InputCommands command, LikeCommand likeCommand, Library library);
	public void visit(InputCommands command, LoadCommand loadCommand, Library library);
	public void visit(InputCommands command, NextCommand nextCommand, Library library);
	public void visit(InputCommands command, PlayPauseCommand playPauseCommand, Library library);
	public void visit(InputCommands command, PrevCommand prevCommand, Library library);
	public void visit(InputCommands command, PrintCurrentPageCommand printCurrentPageCommand, Library library);
	public void visit(InputCommands command, RemoveAlbumCommand removeAlbumCommand, Library library);
	public void visit(InputCommands command, RemoveAnnouncementCommand removeAnnouncementCommand, Library library);
	public void visit(InputCommands command, RemoveEventCommand removeEventCommand, Library library);
	public void visit(InputCommands command, RemovePodcastCommand removePodcastCommand, Library library);
	public void visit(InputCommands command, RepeatCommand repeatCommand, Library library);
	public void visit(InputCommands command, SearchCommand searchCommand, Library library);
	public void visit(InputCommands command, SelectCommand selectCommand, Library library);
	public void visit(InputCommands command, ShowAlbums showAlbums, Library library);
	public void visit(InputCommands command, ShowPlaylistsCommand showPlaylistsCommand, Library library);
	public void visit(InputCommands command, ShowPodcasts showPodcasts, Library library);
	public void visit(InputCommands command, ShowPreferredSongsCommand showPreferredSongsCommand, Library library);
	public void visit(InputCommands command, ShuffleCommand shuffleCommand, Library library);
	public void visit(InputCommands command, StatusCommand statusCommand, Library library);
	public void visit(InputCommands command, SwitchConnectionStatusCommand switchConnectionStatusCommand, Library library);
	public void visit(InputCommands command, SwitchVisibilityCommand switchVisibilityCommand, Library library);
}
