# FinalProject_Chess
Final Project for CSCI2020U
Contributors Aleem Alibhai, Taabish Jeshani, Karan Damodar


Repository Link: https://github.com/aleemalibhai/FinalProject_Chess

## Contributions

Aleem Alibhai - Game driver. All move functionality and board updates. Visitors to check for legal moves and Mate. all logic behind where a piece can move and how a piece can move so that all rules are followed. Worked on lobby, board and create account controllers. Also lots of debugging. helped with some threads and gui.

Taabish Jeshani - Worked on implementing Server sockets, client sockets, and all thread related programming. Whenever a user would 
call on the server that code was implemented by Taabish. Login Controller, Lobby controller, Create account controller and all
server calls within board controller was worked on by Taabish.

Karan Damodar - GUI for all scenes, board creation and board population based on game driver


## How to use

Program will have to be downloaded and compiled with IntelliJ
- Set the run configuration to Server (from the drop down beside the run button) run an instance of the Server.

- Set the run configuration to Main (from the drop down beside the run button) run two instances of Main

- Log in to the server on each client using our premade test accounts test:1, test2:1 (Username:Password) or make your own accounts

- Logging into an account takes that client to the lobby, wait for the other player to be automatically added to the lobby when the second client is logged in.

- From one client double click on a username to challenge them to a game. then confirm your selection

- the second client will automatically receive a notification that they have been challenged. Accept it

- Accepting the challenge brings you to the game stage where the player who is randomly selected to be white will make the first move.

- play a game of chess where your client is updated automatically when your opponent makes a move.


## Bugs

1. If a User has a pending challenge or has challenged another user they should be removed from the lobby.
2. Checkmate breaks the board so the mate checker is removed, however game can be played till completion and no moves can be made after. There's just no pop up for it. 
3. On any Compilation errors rebuild the project
4. Small chance for refresher breaking

## Caveats
- chessboards and alerts will appear on top of one another. This would not be an issue if being played on seperate clients.
- the server ports are hard coded to work over localhost for your testing purposes. the port selection scene was omitted intentionally
- Unable to get gradle to work with project


