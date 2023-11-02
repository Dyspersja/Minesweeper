# Minesweeper
Java Minesweeper game created using JavaFX.

After running application you are greeted by welcome screen, here you can choose the board size and the difficulty level, which allows us to set the density of mines hidden on the board.

![main_menu](https://github.com/Dyspersja/Minesweeper/assets/146620220/34b119d8-3bca-441d-be92-41b52909b9e4)

Game offers 3 difficulty levels and board sizes from 5x5 to 40x70.

![game](https://github.com/Dyspersja/Minesweeper/assets/146620220/aa8475f8-5411-433c-b71d-033f2daaee45)

One of the features of this project is that each first guess guarantees that there won't be a mine. This happens because the board is generated after the player's first guess, ensuring that the game won't end after the player's first try. The game uses a algorithm to generate random board, making each game different.

![game_lost](https://github.com/Dyspersja/Minesweeper/assets/146620220/eb180e7e-e01c-47d3-89ce-3c0e9f685d5a)

If, during the game, a player chooses a tile containing a mine, a message indicating a loss will be displayed. The game also shows all other mines on the board. The player will have the option to restart the game and try again with the same board settings or return to the main menu.

![game_won](https://github.com/Dyspersja/Minesweeper/assets/146620220/05b9e5e4-2b43-42f7-956a-53e1e378b396)

However, when a player uncovers all the tiles on the board without hitting a bomb, a message is displayed to inform them that they have won the game, and the tiles under which the bombs were located are revealed and highlighted in green.
