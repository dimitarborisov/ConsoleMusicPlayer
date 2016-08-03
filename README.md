# ConsoleMusicPlayer
Simple music player controlled from the console (no GUI)

- `quit`, `q`      : Quit the application
- `help`           : Get all commands
- `load`, `l`      : Loads all mp3 files within a folder
  -                  expects a valid path for folder
- `play`           : Plays the current song (starts from 0s)
- `stop`, `s`      : Stops the current song
- `pause`, `p`     : Pauses the current song
- `resume`, `r`    : Resumes a paused song
- `cSong`, `stats` : Displays information about the current song
- `playlist`, `pl` : Displays information about the current playlist
- `volume`, `v`    : Changes the volume, range is [0:100]
   -                 expects a valid integer
- `select`         : Allows to select a song from the playlist
  -                  expects a valid integer (number of the song in the playlist)
- `seek`           : Allows to skip to given time (seconds)
   -                 expects a valid integer (seconds to jump to)
