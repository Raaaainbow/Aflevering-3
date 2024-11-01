package RaceTrack;
import java.io.*;
import java.util.*;

class Map {
    private int scale;
    private int[][] data;

    public Map(int chunkScale) {
        this.scale = chunkScale > 3 ? chunkScale : 3; // Man kan mindst have en bane med 3x3 blocks | dette er scalen på hver chunk,
        // så scale = 3 er en 3x3 chunk
    }

    public int getScale() {
        return this.scale;
    }
    
    public void setScale() {
        this.scale = scale;
    }

    public void setData(int[][] data) {
        this.data = data;
    }

    public int[][] getData() {
        return this.data;
    }

    public void loadMap(String file) throws FileNotFoundException {
        File inputFile = new File(file);
        int rows = 0;
        int cols = 0;
        
        // Her bestemmer den dimensionerne, da vi bruger Array datastrukturen og ikke ArrayList
        try (Scanner scanner = new Scanner(inputFile)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                rows++;
                if (cols == 0) {
                    cols = line.split(" ").length;  
                }
            }
        }
    
        // Nu kan vi lave et array med de korrekte dimensioner
        this.data = new int[rows][cols];
        
        // Her indlæser den tekstfilen så vi kan loade vores maps
        try (Scanner scanner = new Scanner(inputFile)) {
            int row = 0;
            while (scanner.hasNextLine()) {
                String[] values = scanner.nextLine().split(" ");
                for (int col = 0; col < cols; col++) {
                    this.data[row][col] = Integer.parseInt(values[col]);
                }
                row++;
            }
        }
    }
    

    public String toString() {
        String result = "";
        for (int i = 0; i < this.data.length; i++) {
            for (int j = 0; j < this.data[i].length; j++) {
                result += this.data[i][j];
                if (j < this.data[i].length - 1) { // Tilføj mellemrum hvis det ikke er sidste (fencepost problem løst)
                    result += " ";
                }
            }
            if (i < this.data.length - 1) { // Det samme her!
                result += "\n";
            }
        }
        return result;
    }

    public void levelScale(int scale) {
        StdDraw.setScale(-.5, scale+.5);
    }

    public void drawThickLine(int x0,int y0, int x1, int y1, double r) {
        StdDraw.setPenRadius(r);
        StdDraw.line(x0,y0,x1,y1);
        StdDraw.setPenRadius(); // Tilbage til default
    }
    
    public void drawGrid(int xScale, int yScale) {
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius();
        for (int i = 0 ; i <= yScale; i++) {
            StdDraw.line(0, i, xScale, i);
        }
        for (int i = 0 ; i <= xScale; i++) {
            StdDraw.line(i, 0, i, yScale);
        }
    }
    
    public void drawChunk(int x, int y) {
        StdDraw.setPenColor(StdDraw.LIGHT_GRAY);
        StdDraw.filledSquare(x+.5, y-.5, 0.5);
    }
    
    public void outlineArea(int x0,int y0, int x1, int y1) {
        StdDraw.setPenColor(StdDraw.BLACK);
        drawThickLine(x0, y0, x0, y1, 0.01);
        drawThickLine(x1, y0, x1, y1, 0.01);
        drawThickLine(x0, y0, x1, y0, 0.01);
        drawThickLine(x0, y1, x1, y1, 0.01);
    }

    public void buildMap() { 
        int xScale = this.data[0].length*this.scale;
        int yScale = this.data.length*this.scale;
        int largest = xScale >= yScale ? xScale : yScale;
        levelScale(largest);
        System.out.println("xScale:" + xScale);
        System.out.println("yScale:" + yScale);
        for (int i = 0 ; i < this.data[0].length*this.scale ; i++) {
            for (int j = 0; j < this.data.length*this.scale ; j++) {
                if (this.data[j/this.scale][i/this.scale] == 1) { 
                    drawChunk(i, yScale-j);
                }
            }
        }
        drawGrid(xScale, yScale);
    }

    
}

class Player {
    int[] position = {0,0};
    int[] acceleration = {0,0};
    public Player() {
        
    }
}

public class RaceTrack {
    public static void main(String[] args) throws FileNotFoundException {
        Map firstMap = new Map(3);
        firstMap.loadMap("RaceTrack\\secondMap.dat");
        System.out.println(firstMap);
        firstMap.buildMap();
    }
}