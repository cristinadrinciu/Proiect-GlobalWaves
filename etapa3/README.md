# Proiect GlobalWaves  - Etapa 3

## Drinciu Cristina 324CA

#### For this assignment, I used my own implementation of the previous 2 assignments

### For this assignment, I implemented the new commands and made some changes for the flow of the program, adding new design patterns:
- added the Command pattern for the commands given
- added a Singleton patter for the player, so that a player has a map of instances for each user. The player is unique for each user and is declared only once at the beginning of the program, so I made it Singleton
- added an Observer pattern for the notification system. The "Subject" is the artist, as well as the host, the "Observers" are the users that are subscribed to the artist or the host. The notifications are sent to the users when the artist or the host uploads a new song or a new podcast and so on.
- added a Visitor pattern for the flow of the Wrapped command. The visitor visits the User, the Artist and the Host and executes the Wrapped command for each of them, setting the statistics for each of them.
- added a Stream of Json nodes for the output of the commands

### For the commands, I implemented the following:
- Wrapped: this command is used to give the statistics for the users, artists and hosts. For that I implemented separate classes for the statistics of each of them. The statistics are saved in hashmaps, with the Key the name of the specific criteria and the number of listens for each. The keys are String, so that it will not be a problem such as same songs but different album and so on. THe statistics are updated in the methods in the player that calculate the remainingTime of the audio file currently playing. The statistics are updated for each user, artist and host. Each time the audio files are parsed to get to the current one (the update of the player), the statistics are updated also.
- EndProgram: the monetization part was the most important here. The artists have an additional hashmap of the song revenues. At the end, the values in the hashmap are summed for the total song revenue.
- AdBreak: for the ad, I created a copy of the audio file where I added the ad after the current song playing. That way, I do not modify the original audio file. For the case where it is loaded just a song, a temporary playlist is created with the song and the ad. If the ad is loaded in the player, is at second 0, it means it is time for the monetization part. For that, each user has a list of songs played in between adds, based on is calculated the song revenue for each.
- BuyPremium & CancelPremium: for the premium feature of the program, each user has also a list of songs listened while premium, based on the monetization is calculated. Each time the user buys the premium, the list is refreshed and when it is cancelled, the list is cleared and the song revenues are calculated.
- GetNotifications: for this, each user has a list of notifications, which is printed when the given command is called. The notifications are added to the list when the artist or the host uploads a new song or a new podcast and so on. For this it was used the Observatory pattern.
- Navigation: An user has also a list of the pages he has navigated to, which actually represents the history of the user. The list is updated each time the user has the command "changePage". So when the user had the command nextPage or previousPage, the index of the current page is changed in the list of the navigation.

### Other modifications:
- Page is now a class, not an interface, to add the type field. So that it is not necessary to use "instanceof" to check the type of the page.
- The audio files also have a type field, so that it is not necessary to use "instanceof" to check the type of the audio file. So no more "instanceof" in the whole project!
- The flow of the output is now through a Stream of Json nodes(actually Object nodes).
- Commands are not anymore "Visitable", converted to the Command pattern.

*** Note: please consider the lambda expressions used in the other assignments and also in this one (noticed that many people received bonus points for that, but I did not :D). (Search Command for example and others I do not remember every place right now :)) ***

### DONE
![Example GIF](https://media.giphy.com/media/26u4lOMA8JKSnL9Uk/giphy.gif)