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