package GameOfLife;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class GameOfLifeMain {
    public static void main(String[] args) throws FileNotFoundException{
        
        GameOfLife game = new GameOfLife(); 
        importInitialState(game);
        // randomBoardGenerator(game);
    }
    
    public static void importInitialState (GameOfLife game) throws FileNotFoundException{
        
        // Initialises variables for board dimensions
        int boardHeight = 0; 
        int boardWidth = 0; 
        
        // Creats file object and opens the file
        File initialStateFile = new File("GameOfLife/Maps/acorn.gol"); // Edit the path between " " to change map
        Scanner input = new Scanner(initialStateFile);
        
        // Determines array dimensions
        while (input.hasNextLine()) {
            String line = input.nextLine();
            boardHeight++;
            if (boardWidth == 0) {
                boardWidth = line.split(" ").length;
            }
        }

        // setups up StdDraw after boardWidth is correct value
        setupStdDraw(boardWidth);
        
        input.close();
        input = new Scanner(initialStateFile); // Resets Scanner
        
        // Creates array with the correct dimensions
        game.generateGameOfLifeBoard(boardHeight, boardWidth);
        int[][] gameBoard = game.getBoard();
        
        // Draws the array
        for (int i = 0; i < boardHeight; i++) {
            for (int j = 0; j < boardWidth; j++) {
                if (input.hasNextInt()) {
                    int ijValue = input.nextInt();
                    game.changeValue(i, j, ijValue);
                    
                    drawAliveCell(j, i, game);
                }
            }
        }
        
        // Closing the scanner since it's no longer needed
        input.close();
        animateNextState(game, gameBoard, boardWidth);
    }
    
    public static void randomBoardGenerator (GameOfLife game) {
        
        // Generates a random board 100 x 100
        int n = 100;
        int[][] gameBoard = game.generateGameOfLifeBoard(n,n);
        setupStdDraw(n);
        game.randomBoard();
        gameBoard = game.getBoard();
        for (int i = 0; i < gameBoard.length; i++) {
            for (int j = 0; j < gameBoard.length; j++) {
                drawAliveCell(j, i, game);
            }
        }
        animateNextState(game, gameBoard, n);
    }
    
    public static void animateNextState (GameOfLife game, int[][] gameBoard, int n) { // Consider rewriting as recursive to avoid a tripple nested for loop

        // Animates the game board
        for (;;) {
            for (int i = 0 ; i < gameBoard.length; i++) {
                for (int j = 0; j < gameBoard.length; j++) {
                    drawAliveCell(j, i, game);
                }
            }
            game.nextState(gameBoard,n);
            StdDraw.show(1000);
            StdDraw.clear();
        }
        
            
    }

    public static void drawAliveCell (int i, int j, GameOfLife game) {
        if (game.cellIsAlive(i, j)) {
            StdDraw.point(j,i);
        }
    }

    public static void setupStdDraw (int n) {

        // Sets prefered values for scale and penradius        
        StdDraw.setCanvasSize(1000,1000);
        StdDraw.setYscale(n-0.5,-0.5);
        StdDraw.setXscale(-0.5,n-0.5);
        StdDraw.setPenRadius(2.0/n);
    }
}
