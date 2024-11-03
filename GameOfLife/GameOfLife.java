package GameOfLife;

import java.util.Random;

public class GameOfLife {
    private int[][] gameOfLifeBoard;

    private int[][] liveNeighbours(int[][] gameOfLifeBoard) {

        // Creates an array that keeps track of live neighbours
        int n = gameOfLifeBoard.length;
        int[][] neighbourArray = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                int aliveNeighbourCounter = 0;

                for (int k = i-1; k <= i+1; k++) {
                    for (int l = j-1; l <= j+1; l++) {
                        if (k >= 0 && l >= 0 && k < n && l < n) {
                            aliveNeighbourCounter += gameOfLifeBoard[k][l];
                        }
                    }
                }
                // avoids counting itself as alive
                if (gameOfLifeBoard[i][j] == 1) {
                    aliveNeighbourCounter--;
                }
                neighbourArray[i][j] = aliveNeighbourCounter;   
            }
        }

        return neighbourArray;
    }

    public int[][] generateGameOfLifeBoard (int x, int y) {

        // Generates n x n board
        this.gameOfLifeBoard = new int[x][y];
        return this.gameOfLifeBoard;
    }

    public int[][] initialGameOfLifeBoard (int[][] gameOfLifeBoard) {

        // Copies initial state of the game
        int[][] initialBoard = gameOfLifeBoard; 
        return initialBoard;
    }

    public void nextState (int[][] gameOfLifeBoard, int n) {

        int[][] liveNeighboursCount = liveNeighbours(gameOfLifeBoard);
        // creates next state of the board
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                // Rules for game of life simplfied
                if (((gameOfLifeBoard[i][j] == 1) && ((liveNeighboursCount[i][j] < 2) || (liveNeighboursCount[i][j] > 3)))) {
                    gameOfLifeBoard[i][j] = 0;
                } else if ((gameOfLifeBoard[i][j] == 0) && (liveNeighboursCount[i][j] == 3)) {
                    gameOfLifeBoard[i][j] = 1;
                }
            }
        }
    }

    public void changeValue (int i, int j, int newValue) {

        // Change value for a specific cell in the game board array
        gameOfLifeBoard[i][j] = newValue;
    }

    public void randomBoard () {
        
        // Code borrowed from Random Walk in Assignment 2
        Random rand = new Random();
        
        for (int i = 0; i < gameOfLifeBoard[0].length ; i++) {
            for (int j = 0; j < gameOfLifeBoard.length; j++) {
                int randomNum = rand.nextInt(2);
                gameOfLifeBoard[i][j] = randomNum;
            }
        }
    }

    public int[][] getBoard () {

        // Gets Game of Life Board
        return gameOfLifeBoard;
    }

    public boolean cellIsAlive (int i, int j) {
        if (gameOfLifeBoard[i][j] == 1) {
            return true;
        }
            return false;
    }
}