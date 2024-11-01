package GameOfLife;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Random;
import java.util.Scanner;

public class GameOfLifeMain {
    public static void main(String[] args) throws FileNotFoundException{
        
        GameOfLife game = new GameOfLife(); 
        importInitialState(game);
    }

    public static void Graphics (int i, int j) {

        // Draws point (i,j)
        StdDraw.point(i, j);
    }

    
    public static void importInitialState (GameOfLife game) throws FileNotFoundException{
        
        // Reads file and makes it the intial state of the GOL board
        File initialStateFile = new File("GameOfLife/acorn.gol");
        Scanner input = new Scanner(initialStateFile);
        if (initialStateFile.exists()) {
            int boardwidth = 0;
            
            while (input.hasNextLine()) {
                boardwidth++;
            }
            
            // Creates array based on size of file
            game.generateGameOfLifeBoard(boardwidth);
            
            //reads each line in file and adds to array
            for (int i=0; i<boardwidth; i++) {
                for (int j=0; j<boardwidth; j++) {
                    if (input.equals(1)) {
                        game.changeValue(i, j, 1);
                    }
                }
            }
            animateNextState(game, game.getBoard(), boardwidth);
        } else {
            System.out.println("The file you specified wasn't found, so a random board was generated instead");
            randomBoardGenerator(game);
        }
    }
    
    public static void randomBoardGenerator (GameOfLife game) {
        
        // Generates a random board 100 x 100
        int n = 100;
        int[][] gameBoard = game.generateGameOfLifeBoard(n);
        setupStdDraw(n);
        game.randomBoard();
        gameBoard = game.getBoard();
        for (int i = 0; i < gameBoard.length; i++) {
            for (int j = 0; j < gameBoard.length; j++) {
                if (game.cellIsAlive(i, j)) {
                    Graphics(i, j);
                }
            }
        }
        animateNextState(game, gameBoard, n);
    }
    
    public static void animateNextState (GameOfLife game, int[][] gameBoard, int n) {

        // Usese the next state
        for (;;) {
            game.nextState(gameBoard,n);
            StdDraw.show(50);
            StdDraw.clear();
        }
        
            
    }

    public static void setupStdDraw (int n) {

        // Sets prefered values for scale and penradius        
        StdDraw.setCanvasSize(1000,1000);
        StdDraw.setYscale(0,n);
        StdDraw.setXscale(0,n);
        StdDraw.setPenRadius(2/n);
    }
}
