### Problem Statements: Chess LLD
We have to design simple chess game. Chess is actually a very popular game. You can read more about it online on wikipedia or on some other places.
 
#### Entities
If you have already played it already then you might already know this. But to ensure that we all are on the same page, I am defining some here:
* Board: Board is the one entity represents an actual board on which which you play this game.
* Cell: A board consists of a grid of cells.
* Player: Someone who is actually playing right.
* Piece: There are various types of pieces as explained below.

#### Pieces and their moves:
* King: Key entity in chess. If your king is killed then you lose. Its also called checkmate.
* Queen: It can move any number of steps in a single move and in any direction.
* Rook: It only moves in horizontal and vertical direction but can move any number of steps in single move.
* Bishop: It only moves in diagonal direction but can move any number of steps in single move.
* Knight: It makes L shaped moves. Check online for more details about it.
* Pawn: It can move 1 step forward vertically. If it is its first turn, then it can also choose to make 2 steps in single move.  
Note: All pieces except Knight cannot jump any other piece. Knight can jump over other pieces. 

## Expectations
* Code should be functionally correct.
* Code should be modular and readable. Clean and professional level code.
* Code should be extesible and scalable. Means it should be able to accomodate new requirements with minimal changes.
* Code should have good OOPs design.

solution approach
Design a chess game

- what my understanding is every peice is having their rules for the movement, like for example the king can move in some direction where as the queen can move in other direction and so on...

- This should be configurable in some class, where we don't have to define the logic for each player and we can have a common entity peice and each peice can have its own way to move.

- a peice cannot occupy a cell if there  is a cell of the same player peice. it can be of the opponent peice

- Entities
	- Board
	- Players
	- cells
	- Game

- peice is the smallest object - abstract class
	- peice type
	- color
	- isKilled
	- Move() - this is the function specific for each of the peice.


- cell
	- row
	- col
	- isEmpty
	- current peice

- Board
	- cells [][]
	- boardSize

- player
	- name
	- ranking
	- color choosen

- Game
 	- peices
 	- turn of which player
 	- score
 	- winner
 	- gameStatus
 	- board

move(peice startCell, endCell)
isAllowedToMove(peice, startCell, endCell)
