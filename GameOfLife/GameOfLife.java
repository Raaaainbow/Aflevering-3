package GameOfLife;
import java.util.Scanner;

public class GameOfLife {
    public static void main(String[] args) {

        // for now you can tweak the value of x and y to alter the size of the board
        int boardWidth = 0;
        int boardHeight = 0;
        int[][] gameOfLifeBoard = new int[boardWidth][boardHeight];
        nextStage(gameOfLifeBoard ,boardWidth, boardHeight);
    }

    public static int[] nextStage (int[][] gameOfLifeBoard,int boardWidth,  int boardHeight) {
        aliveNeighbourCounter(boardWidth, boardHeight);
        // check every integer in array and applies GOL rules to it

        // remember to add array and logic for life and death 

        for (int i = 0; i < boardWidth; i++) {
            for (int j = 0; i < boardHeight; j++) {
                if (gameOfLifeBoard[i][j] == 1 && numberOfAliveNeighbour[i][j] > 2) {
                    gameOfLifeBoard[i][j] = 0 ;
                }
            }
        }
    }

    public static int[][] aliveNeighbourCounter (int boardWidth, int boardHeight) {

        // Creates new array that keeps track of how many alive neighbours each cell has
        int numberOfAliveNeighbour[][] = new int[boardWidth][boardHeight];

        for (int i = 0; i < boardWidth; i++) {
            for (int j = 0; i < boardHeight; j++) {
                int
            }
        }
        return numberOfAliveNeighbour[][];
    }
}

// Pseudo code
// aliveNeighbourCounter
//  for each spot in gameOfLifeBoard array
//      create new array (maybe create another dimension to existing array??)
//      count "1"s  from boardWidth-1 boardHeight+1 to boardWidth+1 to boardHeight-1 
//
// nextStage
//  for each spot in gameOfLifeBoard array check same spot in aliveNeighbourCounter
//      if 1 || aliveNeighbourCounter < 2
//          swap 1 with 0 (cell dies)
//      else if 1 ||aliveNeighbourCounter > 3
//          swap 1 with 0 (cell dies)
//      else if 0 || aliveNeighbourCounter = 3
//          swap 0 with 1 (cell comes to life)