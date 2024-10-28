package RaceTrack;
public class RaceTrack {
    public static void main(String[] args) {
        drawLevelOne(20);
    }

    public static void levelScale(int scale) {
        StdDraw.setXscale(-scale/2-.5, scale/2+.5);
        StdDraw.setYscale(-scale/2-.5, scale/2+.5);
    }

    public static void drawThickLine(int x0,int y0, int x1, int y1, double r) {
        StdDraw.setPenRadius(r);
        StdDraw.line(x0,y0,x1,y1);
        StdDraw.setPenRadius(); // Tilbage til default
    }
    
    public static void drawGrid(int scale) {
        StdDraw.setPenColor(StdDraw.BLACK);
        for (int i = -scale / 2; i <= scale / 2; i++) {
            StdDraw.line(i, -scale / 2, i, scale / 2);
        }
        for (int j = -scale / 2; j <= scale / 2; j++) {
            StdDraw.line(-scale / 2, j, scale / 2, j);
        }
    }

    public static void outlineArea(int x0,int y0, int x1, int y1) {
        StdDraw.setPenColor(StdDraw.BLACK);
        drawThickLine(x0, y0, x0, y1, 0.01);
        drawThickLine(x1, y0, x1, y1, 0.01);
        drawThickLine(x0, y0, x1, y0, 0.01);
        drawThickLine(x0, y1, x1, y1, 0.01);
    }

    public static void drawLevelOne(int scale) { // ER IKKE NØJAGTIG ENDNU, MEN ALTSÅ DET VAR MEGET SJOVT AT LAVE INDTIL VIDERE :D
        levelScale(scale+1);
        StdDraw.setPenColor(StdDraw.LIGHT_GRAY);
        StdDraw.filledSquare(0,0,scale/2);
        StdDraw.setPenColor(StdDraw.WHITE);
        StdDraw.filledSquare(0,0,scale/3);
        outlineArea(-scale/2, -scale/2, scale/2, scale/2);
        outlineArea(-scale/3, -scale/3, scale/3, scale/3);
        drawGrid(scale);
        
    }
}