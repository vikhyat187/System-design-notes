Snake and ladder Amazon LLD question

Requirements Gathering
-  How many dice?
   - 1 for now, should be scalable

- How many snakes and ladders
  - should be configurable at setup time

- what should be the winning condition (business condition)
   - when one user reaches the end of the board
   - when two users out of five reaches the end of the board
   - when all except one player reaches the end
   


Objects
- dice
- snake, ladder
- Board
- Players
- cells 


class Player{
   String id;
   int CurrentPosition; //should be ideally (X,Y) position
 }
 
class Dice{
   int diceCount;
   
   rollDice(){
      int totalSum = 0;
      int diceUsed = 0;
      
      
}   

