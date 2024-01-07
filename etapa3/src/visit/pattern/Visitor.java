package visit.pattern;

import audio.files.Library;
import commands.*;
import main.InputCommands;

public interface Visitor {
    /**
     * @param command the command to be visited
     * @param addAlbumCommand the command to be visited
     * @param library the library
     */
    void visit(InputCommands command, AddAlbumCommand addAlbumCommand, Library library);

    /**
     * @param command the command to be visited
     * @param addAnnouncementCommand the command to be visited
     * @param library the library
     */
    void visit(InputCommands command, AddAnnouncementCommand addAnnouncementCommand,
                      Library library);

    /**
     * @param command the command to be visited
     * @param addEventCommand the command to be visited
     * @param library the library
     */
    void visit(InputCommands command, AddEventCommand addEventCommand, Library library);

    /**
     * @param command the command to be visited
     * @param addMerchCommand the command to be visited
     * @param library the library
     */
    void visit(InputCommands command, AddMerchCommand addMerchCommand, Library library);

    /**
     * @param command the command to be visited
     * @param addPodcastCommand the command to be visited
     * @param library the library
     */
    void visit(InputCommands command, AddPodcastCommand addPodcastCommand,
                      Library library);

    /**
     * @param command the command to be visited
     * @param addRemoveCommand the command to be visited
     * @param library the library
     */
    void visit(InputCommands command, AddRemoveCommand addRemoveCommand, Library library);

    /**
     * @param command the command to be visited
     * @param addUserCommand the command to be visited
     * @param library the library
     */
    void visit(InputCommands command, AddUserCommand addUserCommand, Library library);

    /**
     * @param command the command to be visited
     * @param backwardCommand the command to be visited
     * @param library the library
     */
    void visit(InputCommands command, BackwardCommand backwardCommand, Library library);

    /**
     * @param command the command to be visited
     * @param changePageCommand the command to be visited
     * @param library the library
     */
    void visit(InputCommands command, ChangePageCommand changePageCommand, Library library);

    /**
     * @param command the command to be visited
     * @param createPlaylistCommand the command to be visited
     * @param library the library
     */
    void visit(InputCommands command, CreatePlaylistCommand createPlaylistCommand,
                      Library library);

    /**
     * @param command the command to be visited
     * @param deleteUserCommand the command to be visited
     * @param library the library
     */
    void visit(InputCommands command, DeleteUser deleteUserCommand, Library library);

    /**
     * @param command the command to be visited
     * @param followCommand the command to be visited
     * @param library the library
     */
    void visit(InputCommands command, FollowCommand followCommand, Library library);

    /**
     * @param command the command to be visited
     * @param forwardCommand the command to be visited
     * @param library the library
     */
    void visit(InputCommands command, ForwardCommand forwardCommand, Library library);

    /**
     * @param command the command to be visited
     * @param getAllUsers the command to be visited
     * @param library the library
     */
    void visit(InputCommands command, GetAllUsers getAllUsers, Library library);

    /**
     * @param command the command to be visited
     * @param getOnlineUsers the command to be visited
     * @param library the library
     */
    void visit(InputCommands command, GetOnlineUsers getOnlineUsers, Library library);

    /**
     * @param command the command to be visited
     * @param getTop5Albums the command to be visited
     * @param library the library
     */
    void visit(InputCommands command, GetTop5Albums getTop5Albums, Library library);

    /**
     * @param command the command to be visited
     * @param getTop5Artists the command to be visited
     * @param library the library
     */
    void visit(InputCommands command, GetTop5Artists getTop5Artists, Library library);

    /**
     * @param command the command to be visited
     * @param getTop5Playlists the command to be visited
     * @param library the library
     */
    void visit(InputCommands command, GetTop5Playlists getTop5Playlists, Library library);

    /**
     * @param command the command to be visited
     * @param getTop5Podcasts the command to be visited
     * @param library the library
     */
    void visit(InputCommands command, GetTop5Songs getTop5Podcasts, Library library);

    /**
     * @param command the command to be visited
     * @param likeCommand the command to be visited
     * @param library the library
     */
    void visit(InputCommands command, LikeCommand likeCommand, Library library);

    /**
     * @param command the command to be visited
     * @param loadCommand the command to be visited
     * @param library the library
     */
    void visit(InputCommands command, LoadCommand loadCommand, Library library);

    /**
     * @param command the command to be visited
     * @param nextCommand the command to be visited
     * @param library the library
     */
    void visit(InputCommands command, NextCommand nextCommand, Library library);

    /**
     * @param command the command to be visited
     * @param playPauseCommand the command to be visited
     * @param library the library
     */
    void visit(InputCommands command, PlayPauseCommand playPauseCommand, Library library);

    /**
     * @param command the command to be visited
     * @param prevCommand the command to be visited
     * @param library the library
     */
    void visit(InputCommands command, PrevCommand prevCommand, Library library);

    /**
     * @param command the command to be visited
     * @param printCurrentPageCommand the command to be visited
     * @param library the library
     */
    void visit(InputCommands command, PrintCurrentPageCommand printCurrentPageCommand,
                      Library library);

    /**
     * @param command the command to be visited
     * @param removeAlbumCommand the command to be visited
     * @param library the library
     */
    void visit(InputCommands command, RemoveAlbumCommand removeAlbumCommand, Library library);

    /**
     * @param command the command to be visited
     * @param removeAnnouncementCommand the command to be visited
     * @param library the library
     */
    void visit(InputCommands command, RemoveAnnouncementCommand removeAnnouncementCommand,
                      Library library);

    /**
     * @param command the command to be visited
     * @param removeEventCommand the command to be visited
     * @param library the library
     */
    void visit(InputCommands command, RemoveEventCommand removeEventCommand, Library library);

    /**
     * @param command the command to be visited
     * @param removePodcastCommand the command to be visited
     * @param library the library
     */
    void visit(InputCommands command, RemovePodcastCommand removePodcastCommand,
                      Library library);

    /**
     * @param command the command to be visited
     * @param repeatCommand the command to be visited
     * @param library the library
     */
    void visit(InputCommands command, RepeatCommand repeatCommand, Library library);

    /**
     * @param command the command to be visited
     * @param searchCommand the command to be visited
     * @param library the library
     */
    void visit(InputCommands command, SearchCommand searchCommand, Library library);

    /**
     * @param command the command to be visited
     * @param selectCommand the command to be visited
     * @param library the library
     */
    void visit(InputCommands command, SelectCommand selectCommand, Library library);

    /**
     * @param command the command to be visited
     * @param showAlbums the command to be visited
     * @param library the library
     */
    void visit(InputCommands command, ShowAlbums showAlbums, Library library);

    /**
     * @param command the command to be visited
     * @param showPlaylistsCommand the command to be visited
     * @param library the library
     */
    void visit(InputCommands command, ShowPlaylistsCommand showPlaylistsCommand, Library library);

    /**
     * @param command the command to be visited
     * @param showPodcasts the command to be visited
     * @param library the library
     */
    void visit(InputCommands command, ShowPodcasts showPodcasts, Library library);

    /**
     * @param command the command to be visited
     * @param showPreferredSongsCommand the command to be visited
     * @param library the library
     */
    void visit(InputCommands command, ShowPreferredSongsCommand showPreferredSongsCommand,
               Library library);

    /**
     * @param command the command to be visited
     * @param shuffleCommand the command to be visited
     * @param library the library
     */
    void visit(InputCommands command, ShuffleCommand shuffleCommand, Library library);

    /**
     * @param command the command to be visited
     * @param statusCommand the command to be visited
     * @param library the library
     */
    void visit(InputCommands command, StatusCommand statusCommand, Library library);

    /**
     * @param command the command to be visited
     * @param switchConnectionStatusCommand the command to be visited
     * @param library the library
     */
    void visit(InputCommands command, SwitchConnectionStatusCommand switchConnectionStatusCommand,
               Library library);

    /**
     * @param command the command to be visited
     * @param switchVisibilityCommand the command to be visited
     * @param library the library
     */
    void visit(InputCommands command, SwitchVisibilityCommand switchVisibilityCommand,
               Library library);

    /**
     * @param command the command to be visited
     * @param wrappedCommand the command to be visited
     * @param library the library
     */
    void visit(InputCommands command, WrappedCommand wrappedCommand,
               Library library);

    /**
     * @param command the command to be visited
     * @param buyPremiumCommand the command to be visited
     * @param library the library
     */
    void visit(InputCommands command, BuyPremiumCommand buyPremiumCommand,
               Library library);

    /**
     * @param command the command to be visited
     * @param cancelPremiumCommand the command to be visited
     * @param library the library
     */
    void visit(InputCommands command, CancelPremiumCommand cancelPremiumCommand,
               Library library);

    /**
     * @param command the command to be visited
     * @param adBreakCommand the command to be visited
     * @param library the library
     */
    void visit(InputCommands command, AdBreakCommand adBreakCommand,
               Library library);

    /**
     * @param command the command to be visited
     * @param subscribeCommand the command to be visited
     * @param library the library
     */
    void visit(InputCommands command, SubscribeCommand subscribeCommand,
               Library library);

    /**
     * @param command the command to be visited
     * @param getNotifications the command to be visited
     * @param library the library
     */
    void visit(InputCommands command, GetNotifications getNotifications,
               Library library);

    /**
     * @param command the command to be visited
     * @param buyMerch the command to be visited
     * @param library the library
     */
    void visit(InputCommands command, BuyMerch buyMerch,
               Library library);

    /**
     * @param command the command to be visited
     * @param seeMyMerch the command to be visited
     * @param library the library
     */
    void visit(InputCommands command, SeeMyMerchCommand seeMyMerch,
               Library library);

    /**
     * @param command the command to be visited
     * @param updateRecommendationsCommand the command to be visited
     * @param library the library
     */
    void visit(InputCommands command, UpdateRecommendationsCommand updateRecommendationsCommand,
               Library library);

    /**
     * @param command the command to be visited
     * @param previousPageCommand the command to be visited
     * @param library the library
     */
    void visit(InputCommands command, PreviousPageCommand previousPageCommand,
               Library library);

    /**
     * @param command the command to be visited
     * @param nextPageCommand the command to be visited
     * @param library the library
     */
    void visit(InputCommands command, NextPageCommand nextPageCommand,
               Library library);

    /**
     * @param command the command to be visited
     * @param loadRecommendationCommand the command to be visited
     * @param library the library
     */
    void visit(InputCommands command, LoadRecommendationCommand loadRecommendationCommand,
               Library library);
}
