AudioPlayer Etapa 1

The program has an additional package called AudioFiles.
In this package, there are 6 clases: AudioFile, Song, Podcast, Library, User, Playlist.
The AudioFile class is the superclass of Song, Podcast, Episode and Playlist.
These classes are used to create the audio files and to test the creation of the audio files, being copies of the classes in "fileio.input", but with additional fields and methods.

The program has a main class and also another most used class, named InputCommands. The package has classes for each command, that are used as objects in InputCommands class.
Also, there are some other classes like Filters, Public Playlists, created for the ease of work.

Each user has its own MusicPlayer. The player has a field named timestamp, which is update at each command given as input.
The player works so that is updated as timestamp, so that the flow of the audio files is calculated right, such as the remaining time.
The player has the loaded item from teh Load command and the playingNow field, which either a Song or an episode.
Also, there is a field called switchedTime, which is updated at each modification of the player or status of it. It is really important at the calculation the remaining time.

Each user has its own list of playlists and liked songs. Its player has a list of playlists that are owned by the user and also the public ones from the other users.
Every time the visibility of a playlist is changed, the list of public playlists is updated for each user.

The command objects (searchCommand, selectCommand and so on) are created just once, by implementing a lazy Singleton pattern.
In SearchCommand, the results are calculated by using filters. There are used lambda expressions for the ease of the work.

The program has a class named Filters, which has static methods for each filter. The filters are used in SearchCommand class.

The program has a class named PublicPlaylists, which has static methods for each command that can be given to a public playlist. The commands are used in SelectCommand, CreatePlaylist and SwitchVisibilityCommand.

For the output I took some inspiration from ChatGBT and the following link: https://attacomsian.com/blog/jackson-create-json-array .

The command are read from the file input, in the same manner as the library. At the beginning of the program, the player of each user is initialised.
For each command is verified the name of the command. For each command name is called the method of its execution.

SearchCommand: The results are calculated by using filters. There are used lambda expressions for the ease of the work. THe result is a static field so that it is not necessary an instance for it.
SelectCommand: It is given an index and it is chosen the element with the index from the results from search.
LoadCommand: It is loaded the audio file from the select command.
PlayPause: It is played or paused the playingNow from the player.
Next: It is played the next audio file from the playlist, considering the shuffle and repeat states of the player.
Previous: It is played the previous audio file from the playlist, considering the shuffle and repeat states of the player.
Forward: It is forwarded the listened time of the playingNow field of the player with 90 seconds. 
Backward: It is backward the listened time of the playingNow field of the player with 90 seconds.
Like: It is added the playingNow field of the player to the liked songs of the user or is removed if it already liked.
Repeat: It is changed the repeat state of the player. The remainingTime of the player si calculated from now on, depending on the repeat state and the instance of the playingNow element.
Shuffle: It is changed the shuffle state of the player. The remainingTime of the player si calculated from now on, depending on the shuffle state and the instance of the playingNow element. It is created another array of the shuffled songs of the playlists.
When the shuffle is activated, when the player changes the track,the following track is chosen randomly from the shuffled array.
When the shuffle is deactivated, when the player changes the track, the following track is chosen from the playlist, considering the index of the playingNow element.
Status: Prints the info from the player, calculating before the printing the remaining time.
CreatePlaylist: It is created a playlist with the given name and it is added to the public playlists of the users, because by default it is created as public.
SwitchVisibility: It is changed the visibility of the playlist, from public to private and vice versa. When is it switched to private, it is removed from all users' playlists, expect the owner. When is it switched to public, it is added to all users' playlists, expect the owner.
Follow: It is added the playlist to the user's followed playlists and the playlist's followers are incremented and on the opoosite, it is removed the playlist from the user's followed playlists and the playlist's followers are decremented.
ShowPlaylist: It is printed the list of the songs from the all the playlists of the user.
ShowPreferredSongs: It is printed the list of the liked songs of the user.
GetTop5Songs: Are printed the top 5 most liked songs from the library of the platform.
GetTop5Playlists: Are printed the top 5 most followed playlists from the platform, in the order of the created time in case of equality.

