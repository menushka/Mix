# Mix

## Overview
Well the deal was that I was just sick of iTunes and I decided to make my own light-weight music player with the features that I wanted.  Currently its a work in development but I have big planes for it in the future. 

##Design
<img src="https://github.com/aggromoose/mix/blob/master/screenshot.png" width="400" /><br/><br/>
I really like material design so I decided to go mostly in that direction.  Also this is just my defualt color layout, all the colors will be customizable using the color.json included.

##Functionality

####Update-to-date music library
The thing I hated the most about the iTunes player was the fact that I had to import my music everything I downloaded something new that wasn't directly from the iTunes music library.  So I decided to make a player that would look in my Music folder making it so my library would always be up to date.

####Customizable colors and themes
I've had this feature from the start but before its only been accessable from within the jar file.  Now the colors JSON file that allows users to customize the play is located in the Music directory under a Mix folder.  Hopefully by the final release everything will be changeable.

####File location based playlists (very buggy)
I just added playlists but currently you can't exactly create your own playlists from within the Mix player.  Like the colors, it reads it from a JSON file located in the Music directory.  

####Mixing
This is the part I'm currently working, I wanted to make a player that would seamlessly mix my music into one big track.  Of course this would be an options, so you could turn this off or on but I really like the idea of a endless dynamic playlist of music.