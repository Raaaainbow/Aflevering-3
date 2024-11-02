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
    
    public void drawGrid(int x, int y) {
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius();
        StdDraw.line(x-.5+.5,y,x+.5+.5,y);
        StdDraw.line(x,y-.5-.5,x,y+.5-.5);
    }
    
    public void drawChunk(int x, int y) {
        StdDraw.setPenColor(StdDraw.LIGHT_GRAY);
        StdDraw.filledSquare(x+.5, y-.5, 0.5);
    }
    
    public boolean checkOnEdge(int x, int y) {
        return ((x == 0 || x == this.data[0].length*this.scale-1) || (y == 0 || y == this.data.length*this.scale-1));
    }

    public void outlineMap() {
        StdDraw.setPenColor(StdDraw.BLACK);
        int xScale = this.data[0].length*this.scale;
        int yScale = this.data.length*this.scale;
        for (int i = 0 ; i < this.data[0].length*this.scale ; i++) {
            for (int j = 0; j < this.data.length*this.scale ; j++) {
                if (this.data[j/this.scale][i/this.scale] >= 1 ) { 
                    // Tegn venstre border
                    if (i == 0) {
                        drawThickLine(i, yScale-j, i, yScale-j-1, 0.01);
                    } else if (this.data[j/this.scale][(i-1)/this.scale] == 0) {
                        drawThickLine(i, yScale-j, i, yScale-j-1, 0.01);
                    }
                    // Tegn højre border
                    if (i == this.data[0].length*this.scale-1) {
                        drawThickLine(i+1, yScale-j, i+1, yScale-j-1, 0.01);
                    } else if (this.data[j/this.scale][(i+1)/this.scale] == 0) {
                        drawThickLine(i+1, yScale-j, i+1, yScale-j-1, 0.01);
                    }
                    // Tegn top border
                    if (j == 0) {
                        drawThickLine(i, yScale-j, i+1, yScale-j, 0.01);
                    } else if (this.data[(j-1)/this.scale][i/this.scale] == 0) {
                        drawThickLine(i, yScale-j, i+1, yScale-j, 0.01);
                    }
                    // Tegn nederste border
                    if (j == this.data.length*this.scale-1) {
                        drawThickLine(i, yScale-j-1, i+1, yScale-j-1, 0.01);
                    } else if (this.data[(j+1)/this.scale][i/this.scale] == 0) {
                        drawThickLine(i, yScale-j-1, i+1, yScale-j-1, 0.01);
                    }
                }
                
            }
        }
        
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
                if (this.data[j/this.scale][i/this.scale] >= 1) { 
                    drawChunk(i, yScale-j);
                    drawGrid(i, yScale-j);
                    
                }
                
            }
        }
        outlineMap();
    }

    
}

class Player {
    int[] position = {0,0};
    int[] acceleration = {0,0};
    Scanner playerScanner = new Scanner(System.in);
    public Player(int[] startPosition) { // Konstruktør
        position = startPosition;
    }

    public void movePlayer() {
        System.out.print("Enter a direction(1-9): ");
        int input = playerScanner.nextInt();
        acceleration[0] += (input % 3 == 0 ? 1 : 0) * 1 + (input % 3 == 1 ? 1 : 0) * -1;
        acceleration[1] += (input >= 7 ? 1: 0) * 1 + (input <= 3 ? 1 : 0) * -1;
        System.out.println("Acceleration is now {" + Arrays.toString(acceleration) + "}" );
    }

    public void closeScanner() {
        playerScanner.close();
    }
}

public class RaceTrack {
    public static void main(String[] args) throws FileNotFoundException {
        Map firstMap = new Map(3);
        firstMap.loadMap("RaceTrack\\firstMap.dat");
        System.out.println(firstMap);
        firstMap.buildMap();
        Player one = new Player(new int[] {0,0});
        while (true) {
            one.movePlayer();
        }
    }
}


/// TODO
/// 1) Spilleren skal laves
/// 2) gør så chunkscale scaler med spiller-antal: n, dvs. 2+n og at spillerne automatisk spawner på grøn streg
/// og "Lav  kode  som  registrerer  når  racerbilen  passerer  målstregen,  og  derefter  angiver  hvormange træk bilen har foretaget" - som så printer et leaderboard over spillerne
/// 3) Win condition, "collisions" dvs out of bounds og til sidst Menu
/// 4) Kommentarer
/// 5) gør så de lyse grå tiles bliver mørkere, altså grå, når de er over 5 tiles væk (måske skal den tile som man skal hen til blive grå-grøn agtig) new Color(103, 198, 243)
/// 6) hvis man går baglæns bliver tilen rød
/// HVIS DU VIRKELIGT HAR MEGET TID:
/// 7) Spil mod pc
/// 8) Power-ups