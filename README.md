# GlobalWaves Project

## Overview

The GlobalWaves Project is a comprehensive simulation of a music streaming platform, similar to Spotify. It encompasses functionalities ranging from basic audio playback to complex features like analytics, recommendations, and monetization strategies. The project is divided into three main stages, each adding a layer of complexity and functionality to the simulation.

## Stage 1: Audio Player

- **Objective**: Implement the core functionalities of the audio player, including play, pause, and skip for songs, albums, and podcasts.
- **Features**:
  - Basic command-line interface for interaction.
  - Playback features for various media types.

## Stage 2: Pagination and Extended Features

- **Objective**: Enhance the platform with pagination for listings and additional features for user engagement.
- **Features**:
  - Pagination for browsing songs, albums, and artists.
  - Implementation of user profiles, including premium and free accounts.

## Stage 3: Analytics, Recommendations, and Monetization

- **Objective**: Introduce analytics and recommendations for users and artists, alongside a monetization system.
- **Features**:
  - GlobalWaves Wrapped: personalized musical year in review for users and artists.
  - Recommendations system for songs and playlists based on user preferences.
  - Monetization through subscription models, advertising, and merchandise sales.


## Implementation Details

## AudioFile Package Implementation Details

This section outlines the implementation of the audio functionalities within the GlobalWaves project, focusing on the `AudioFile` package. The package includes classes designed to represent and manage various audio content types, such as songs, albums, playlists, and podcasts.

- **AudioFile**: Serves as the base class for all audio content, encapsulating common properties and methods.
- **Song**: Extends `AudioFile` to represent individual songs, adding song-specific attributes and functionalities.
- **Album**: Groups songs, extending `AudioFile` with album-related properties and methods for managing album popularity.
- **Podcast**: Specializes in handling podcast content, extending `AudioFile` with attributes for episodes, ownership, and listen time.
- **Playlist**: Manages user-created playlists, including attributes for ownership, visibility, and content.
- **Library**: Central repository organizing the platform's audio content, facilitating content management and access.

Each class contributes to the rich feature set of the GlobalWaves platform, enabling comprehensive audio media management and interaction.

## Users Package Implementation Details

### User
Represents a platform user with functionalities for managing playlists, liked songs, followed playlists, and observing notifications. Supports premium account features and ad management. Implements observer and visitor patterns for notifications and analytics.

### Artist
Extends `User`, tailored for artist profiles, handling albums, events, merchandise, and financial analytics. Tracks revenues and fan engagement, leveraging observer and visitor patterns for dynamic content updates and comprehensive statistics.

### Host
A specific user type for podcast hosts, managing podcasts, announcements, and subscriber interactions. Provides detailed analytics on listener engagement and implements observer and visitor patterns for efficient notification and data processing.

## Player Package Implementation Details

The `Player` class encapsulates the functionality for audio playback within the GlobalWaves project, supporting a variety of audio content including songs, playlists, albums, and podcasts. It utilizes a Singleton pattern to ensure a unique player instance per user, incorporating features like play, pause, shuffle, and repeat. The player tracks the current and loaded audio items, managing play states and durations, and integrates closely with the library to access audio content.

Key functionalities include:
- Dynamic content loading and playback initiation based on audio file type.
- Calculates the flow of the playbacks, based on the timestamps of the commands.
- Shuffle and repeat modes with nuanced control over playback order and repetition.
- Playback statistics update, integrating user and artist statistics for played songs.
- Support for playing advertisements and managing premium content playback without interruptions.

## Pages Package Implementation Details

The `pages` package structures the user interface of the GlobalWaves project by defining several page types that users interact with:

### Page
A generic base class for pages within the application, defining the core structure and functionalities common across all pages.

### HomePage
Represents the user's home page, showcasing liked songs, followed playlists, and personalized recommendations. It dynamically updates to reflect the user's current preferences and interactions within the platform.

### LikedContentPage
A specialized page displaying the user's liked songs and followed playlists, facilitating easy access to preferred content.

### ArtistPage
Dedicated to artist profiles, this page displays albums, merchandise, and upcoming events, offering fans a comprehensive view of their favorite artists.

### HostPage
Serves podcast hosts by listing their podcasts and announcements, enabling them to engage with their audience directly through the platform.

## Design Patterns Implementation Details

### Visitor Pattern
It was used for the flow of the Wrapped command. The visitor visits the User, the Artist and the Host and executes the Wrapped command for each of them, setting the statistics for each of them.

### Observer Pattern
It was used for the notification system. The "Subject" is the artist, as well as the host, the "Observers" are the users that are subscribed to the artist or the host. The notifications are sent to the users when the artist or the host uploads a new song or a new podcast and so on.

### Command Pattern
This design pattern was used for the commands of this program. Being lots of commands, it was better to handle them by using this design.

### Singleton Patter
It was used for the player, so that a player has a map of instances for each user. The player is unique for each user and is declared only once at the beginning of the program, so I made it Singleton.

These patterns collectively contribute to the robustness, flexibility, and maintainability of the GlobalWaves project, enabling sophisticated user interactions and efficient data handling.

## Other

### Commands package
Each command is implemented in its own file with its own class.

### Stream
Used Stream of Json nodes for the output of the commands

### Main Class Overview
The `Main` class acts as the application's entry point for GlobalWaves, setting up the environment, parsing JSON input files, and facilitating the execution of user commands. It initializes the library and orchestrates the flow of actions based on the inputs provided.

### InputCommands Class Functionality
`InputCommands` dynamically maps user commands to specific actions within the application, such as content search, playback control, and user management. It ensures accurate argument passing for each command, leveraging dedicated fields for each command's data. This approach allows for efficient command execution and interaction with the system's library and user base.

