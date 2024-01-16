# Proiect GlobalWaves  - Etapa 3

## Drinciu Cristina 324CA

#### For this assignment, I used my own implementation of the previous 2 assignments

### For this assignment, I implemented the new commands and made some changes for the flow of the program:
- added the Command pattern for the commands given
- added a Singleton patter for the player, so that a player has a map of instances for each user. The player is unique for each user and is declared only once at the beginning of the program, so I made it Singleton
- added an Observer pattern for the notification system. The "Subject" is the artist, as well as the host, the "Observers" are the users that are subscribed to the artist or the host. The notifications are sent to the users when the artist or the host uploads a new song or a new podcast and so on.
- added a Visitor pattern for the flow of the Wrapped command. The visitor visits the User, the Artist and the Host and executes the Wrapped command for each of them, setting the statistics for each of them.