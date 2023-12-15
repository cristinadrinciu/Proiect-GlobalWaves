# Proiect GlobalWaves  - Etapa 2
## Drinciu Cristina 324CA

### Description
In this project I used my implementation of the previous assignment. The program is now changed, including a visitor patter for the execution of the commands.
Using this patter, the main method is cleaner, and also the InputCommands class.

### Visitor Pattern
The visitor pattern is used to execute the commands. The main method is cleaner, and the InputCommands class is also cleaner.
The commands implement the interface visitable, and override the accept method.
The visitor interface is implemented by the class *CommandExecute*, which has a method for execution for each command.
Is command is an object, has its own class, and the visit methods differ by the type of the command.
The execute method constructs the command output into JSON format.

### InputCommands
This class receives the input from for each command and the necessary data for the execution of the command(fields like type, description and so on).
This class has an object for each command and a Visitable commandToExecute, which is set by the command name.

### Main
The main method is cleaner, and the execution of the commands is done by the visitor pattern. There are added the cases of the offline user and also the nonexistent user.
The visitor executeCommand is created and the InputCommands.commandToExecute calls the accept visitor method.

### Packages
Now the code is structured in more packages with suggestive names.
* **user.types** - contains the classes for each type of user
* **pages** - contains the classes for each type of page
* **commands** - contains the classes for each type of command
* **visit.pattern** - contains the classes for the visitor pattern
* **player** - contains the class for the player
* **platfrom.data** - contains the classes for the data of the platform, like public data(Albums, Playlists, OnlineUsers)
* **audio.files** - contains the classes for the audio files(Songs, Podcasts, Albums, Playlists)
* **page.content** - contains the classes for the content of the pages(Merch, Events, Announcements)