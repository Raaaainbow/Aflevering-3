package RaceTrack;
import java.io.*;
import java.util.*;
import java.awt.Color;

class Map {
    private int scale;
    private int[][] data;
    private int last;
    private int n;
    private Player[] players;
    private boolean done;

    public Map(int chunkScale, int playerCount) {
        this.n = playerCount >= 1 ? playerCount : 1;
        this.scale = chunkScale > 2+n ? chunkScale : 2+n; // Man kan mindst have en bane med 3x3 blocks | dette er scalen på hver chunk,
        players = new Player[n];
        // så scale = 3 er en 3x3 chunk
    }

    public int getScale() {
        return this.scale;
    }

    public int getN() {
        return  n;
    }

    public Player[] getPlayers() {
        return  players;
    }

    public void setData(int[][] data) {
        this.data = data;
    }

    public int[][] getData() {
        return data;
    }

    public boolean getDone() {
        return done;
    }

    public int getLast() {
        return last;
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

    // Til at tegne tykke linjer (f.eks. mållinjen og den yderste/indre linje rundtom tracksne)
    public void drawThickLine(int x0,int y0, int x1, int y1, double r) {
        StdDraw.setPenRadius(r);
        StdDraw.line(x0,y0,x1,y1);
        StdDraw.setPenRadius(); // Tilbage til default
    }
    
    // Til at tegne gridden
    public void drawGrid(int x, int y) {
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius();
        StdDraw.line(x-.5+.5,y,x+.5+.5,y);
        StdDraw.line(x,y-.5-.5,x,y+.5-.5);
    }
    
    // Til at tegne en chunk
    public void drawChunk(int x, int y) {
        StdDraw.setPenColor(StdDraw.LIGHT_GRAY);
        StdDraw.filledSquare(x+.5, y-.5, 0.5);
    }

    public void outlineMap() {
        StdDraw.setPenColor(StdDraw.BLACK);
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

    // Bruges til at tjekke om spillet er færdig og printe stats
    public void mapCompletedCheck() {
        int playersCompleted = 0;
        for (int i = 0 ; i < players.length ; i++) {
            if (players[i].getDone()) {
                playersCompleted++;
            }
        }
        if (playersCompleted == players.length) {
            System.out.println("All players finished/destroyed. \nPrinting statistics..");
            for (int i = 0 ; i < players.length ; i++) {
                System.out.println(players[i].toString(i+1));
            }
            done = true;
        }
    }

    public void buildMap() { 
        int xScale = this.data[0].length*this.scale;
        int yScale = this.data.length*this.scale;
        int largest = xScale >= yScale ? xScale : yScale;
        levelScale(largest);
        // Find sidste chunk
        for (int i = 0 ; i < this.data[0].length ; i++) {
            for (int j = 0; j < this.data.length ; j++) {
                if (this.data[j][i] > last) {
                    last = this.data[j][i];
                }
            }
        }
        
        // Tegn mappet
        int tempPlayerStartY = 0;
        int tempPlayerStartX = 0;
        for (int i = 0 ; i < this.data[0].length*this.scale ; i++) {
            for (int j = 0; j < this.data.length*this.scale ; j++) {
                if (this.data[j/this.scale][i/this.scale] >= 1) { 
                    drawChunk(i, yScale-j);
                    drawGrid(i, yScale-j);
                }
                if (this.data[j/this.scale][i/this.scale] == last && (i % scale) == 0) { 
                    StdDraw.setPenColor(StdDraw.GREEN);
                    drawThickLine(i, yScale-j, i, yScale-j-1, 0.01);
                    tempPlayerStartY = tempPlayerStartY == 0 ? j+scale-1 : tempPlayerStartY;
                    tempPlayerStartX = tempPlayerStartX == 0 ? i : tempPlayerStartX;
                } 
            }
        } 
        // Lav spilleren
        for (int i = 0 ; i < n ; i++) {
            players[i] = new Player(new int[] {tempPlayerStartX,yScale-tempPlayerStartY+(scale/(n+1)*(i+1))},this);
        }
        outlineMap();
    }

    
}

class Player {
    private int[] position = {0,0};
    private int[] acceleration = {0,0};
    private int moves = 0;
    private int carState = 0; // Hvor 0 er uskadt, 1 er destrueret, 2 er i mål 
    private Color playerColor;
    private Random random = new Random();
    private Map currentMap;
    private int highestChunk = 0;
    private Scanner playerScanner = new Scanner(System.in);

    public Player(int[] startPosition, Map currentMap) { // Constructor
        position = startPosition;
        playerColor = new Color(random.nextInt(256), random.nextInt(256/2), random.nextInt(256)); // divideret grøn med 2 for kontrast mellem baggrund/mål osv..
        StdDraw.setPenColor(playerColor);
        StdDraw.filledCircle(position[0], position[1], 0.35);
        this.currentMap = currentMap;
    }

    public int[] getPosition() {
        return position;
    }
    public boolean getDone() {
        return (carState == 2 || carState == 1);
    }

    public void setCarState(int carState) {
        this.carState = carState;
    }

    public void closeScanner() {
        playerScanner.close();
    }

    public void movePlayer() {
        if (carState == 0) {
            // Bevæg spilleren
            int xScale = currentMap.getData()[0].length*currentMap.getScale();
            int yScale = currentMap.getData().length*currentMap.getScale();
            System.out.print("Enter a direction(1-9): ");
            int input = playerScanner.nextInt();
            acceleration[0] += (input % 3 == 0 ? 1 : 0) * 1 + (input % 3 == 1 ? 1 : 0) * -1;
            acceleration[1] += (input >= 7 ? 1: 0) * 1 + (input <= 3 ? 1 : 0) * -1;
            System.out.println("Acceleration is now {" + Arrays.toString(acceleration) + "}" );
            if (highestChunk + 1 == currentChunk(position[0], position[1])) {
                highestChunk = currentChunk(position[0],position[1]);
            } 
            position[0] += acceleration[0];
            position[1] += acceleration[1];
            this.moves++;
            // Collision Detection
            if (currentChunk(position[0],position[1]) == 1 && highestChunk == currentMap.getLast()) { // Går over målstreg
                System.out.println("You crossed the finish line!");
                closeScanner();
                carState = 2;
            }
            if (currentChunk(position[0],position[1]) == 0 || (position[0] < xScale && position[1] < yScale && position[0] > 0 && position[1] > 0) == false) { // collision med vægge
                System.out.println("Player died.");
                closeScanner();
                carState = 1;
            }
            Player[] otherPlayers =currentMap.getPlayers(); 
            for (int i = 0 ; i < otherPlayers.length ; i++) { // Collision mellem spillere
                if (position[0] == otherPlayers[i].getPosition()[0] && position[1] == otherPlayers[i].getPosition()[1] && otherPlayers[i] != this) {
                    carState = 1;
                    otherPlayers[i].setCarState(1);
                    otherPlayers[i].closeScanner();
                    closeScanner();
                    System.out.println("Player collission!.");
                }
            }
            // Tegn spilleren og deres handlinger
            StdDraw.setPenColor(playerColor);
            StdDraw.setPenRadius(0.01);
            StdDraw.line(position[0],position[1],position[0]-acceleration[0],position[1]-acceleration[1]);
            StdDraw.setPenRadius();
            StdDraw.filledCircle(position[0], position[1], 0.35);
            currentMap.mapCompletedCheck();
        }
    }

    // Finder den nuværende chunk
    public int currentChunk(int x, int y) {
        int yScale = currentMap.getData().length*currentMap.getScale();
        return currentMap.getData()[(yScale-y)/currentMap.getScale()][(x-1)/currentMap.getScale()];
    }

    // Når spillet er ovre, kan man printe spilleren
    public String toString(int i) {
        String tempString = "";
        tempString += "\nPlayer: " + i + "\nPosition: "+Arrays.toString(position) + "\nMoves: " + moves + "\nCar State: " + (carState == 2 ? "Crossed finish line" : "Destroyed by collision");
        return tempString;
    }
}

public class RaceTrack {
    public static void main(String[] args) throws FileNotFoundException {
        // Menuen laves og du bliver stillet spørgsmål om indstillinger
        Scanner console = new Scanner(System.in);
        System.out.print("How many players? ");
        int pn = console.nextInt();
        System.out.print("How big should the map be? ");
        int size = console.nextInt();
        Map firstMap = new Map(size,pn);
        String[] maps = new String[] {"firstMap","secondMap","thirdMap", "fourthMap"};
        for (int i = 0 ; i < maps.length ; i++) {
            System.out.println(i+1 + ": " + maps[i]);
        }
        System.out.print("What map do you want to select?(1-" + maps.length +") ");
        int mapNumber = console.nextInt();
        
        firstMap.loadMap("RaceTrack\\Maps\\"+maps[mapNumber-1] + ".dat");
        firstMap.buildMap();
        while (firstMap.getDone() == false) {
            for (int i = 0 ; i < firstMap.getN() ; i++) {
                firstMap.getPlayers()[i].movePlayer();
            }
        }
        console.close();
    }
}
