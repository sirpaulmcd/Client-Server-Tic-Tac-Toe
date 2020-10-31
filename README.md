# Client-Server-Tic-Tac-Toe
A java based client-server tic-tac-toe application utilizing the MVC design pattern. 
Using these files, you can face your friends over the internet in the ancient art of tic-tac-toe.

## How to use
1. Download source code into java project
2. Run `Server.java`
3. Run 2 or more instances of `ClientController.java`

Note that each script can be ran from the same computer to test locally or deployed to a server such that
multiple people can challenge each other over the internet. 

## Preview
Upon launching the client, a player is asked to input their name:
<p align="center">
<img src="https://cdn.discordapp.com/attachments/751158308236165191/771895749137924096/Screen_Shot_2020-10-30_at_18.16.56.png" height=150 />
</p>

They will then wait to be matched with an opponent. Once found, the game will begin.
The first person to connect to the server is the first person to play.
<p align="center">
<img src="https://media.giphy.com/media/p4Csz2GlROWlKMEgSe/giphy.gif" height=200 />
</p>


If a person disconnects from the server prematurely, they are shamed accordingly:
<p align="center">
<img src="https://cdn.discordapp.com/attachments/751158308236165191/771910346532192306/Screen_Shot_2020-10-30_at_19.35.32.png" height=250 />
</p>

Useful logs are kept by the server while it is running:
<p align="center">
<img src="https://media.discordapp.net/attachments/751158308236165191/771900723621003264/unknown.png" height=125 />
</p>

## Learning
This was my first attempt at implementing a client-server architecture. As such, there are things I'd change about the design looking back.
Currently, the client is fat (i.e. contains game logic). 
I did this because I wanted the client to check for the validity of moves before sending them to the server.
However, since tic-tac-toe is such a simple game, I could have serialized the entire game session and passed that along. 
Using a `GameSession.cs` class would have simplified the design because the server would just pass the same self-regulating packet between users.
Instead, I used a `GameState.cs` class that contained information on the game's state and had the client and server update their local game logic accordingly.
It works but it was more complex than necessary. 
