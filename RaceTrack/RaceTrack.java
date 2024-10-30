package RaceTrack;
import java.io.*;
import java.util.*;

class Map {
    private int scale;
    private int[][] data;

    public Map(int scale) {
        this.scale = scale > 3 ? scale : 3; // Man kan mindst have en bane med 3x3 blocks | dette er scalen på hver chunk,
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

    public void loadMap(String file)  throws FileNotFoundException {
        Scanner read = new Scanner(new File(file));
        while (read.hasNextInt()) {
            System.out.println(read.nextInt());
        }
        
    }

    public void levelScale(int scale) {
        StdDraw.setXscale(-.5, scale+.5);
        StdDraw.setYscale(-.5, scale+.5);
    }

    public void drawThickLine(int x0,int y0, int x1, int y1, double r) {
        StdDraw.setPenRadius(r);
        StdDraw.line(x0,y0,x1,y1);
        StdDraw.setPenRadius(); // Tilbage til default
    }
    
    public void drawGrid(int scale) {
        StdDraw.setPenColor(StdDraw.BLACK);
        for (int i = 0; i <= scale; i++) {
            StdDraw.setPenRadius();
            StdDraw.line(i,0,i,scale);
            StdDraw.line(0,i,scale,i);
        }
        
    }
    
    public void drawChunk(int x, int y, int scale) {

    }
    
    public void outlineArea(int x0,int y0, int x1, int y1) {
        StdDraw.setPenColor(StdDraw.BLACK);
        drawThickLine(x0, y0, x0, y1, 0.01);
        drawThickLine(x1, y0, x1, y1, 0.01);
        drawThickLine(x0, y0, x1, y0, 0.01);
        drawThickLine(x0, y1, x1, y1, 0.01);
    }

    public void drawLevelOne() { // ER IKKE NØJAGTIG ENDNU, MEN ALTSÅ DET VAR MEGET SJOVT AT LAVE INDTIL VIDERE :D
        levelScale(this.scale);
        //int[][] newMap = new int[][];
        //setData(newMap);
        outlineArea(5,5,15,15);
        outlineArea(0,0,this.scale,this.scale);
        drawGrid(this.scale);
        
    }
}

class Player {

}

public class RaceTrack {
    public static void main(String[] args) throws FileNotFoundException {
        Map firstMap = new Map(20);
        firstMap.loadMap("RaceTrack\\firstMap.dat");
    }
}