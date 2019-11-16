package model;

import java.awt.*;
import java.util.Set;

public class TeethFinder {

    /** set of the teeth found */
    Set<Point> teeth;

    /**
     * Finds local maximum in the given area [field].
     * @param field the area in which the local maximum is sought after
     * @param startingPoint the point where the mountain climber starts
     * @return the local maximum found
     */
    public static Point findMaxima(Double[][] field, Point startingPoint) {
        int x = startingPoint.x;
        int y = startingPoint.y;

        //imagine the field to be rotated by 90 degrees to the right

        int startPosX = (x - 1 < 0) ? x : x-1;
        int startPosY = (y - 1 < 0) ? y : y-1;
        int endPosX =   (x + 1 > field.length -1) ? x : x+1;
        int endPosY =   (y + 1 > field[0].length - 1) ? y : y+1;

        double maximum = field[x][y];
        Point oldCoordinate = new Point(x, y); //starts in the middle
        Point coordinate =  new Point(x, y); //starts in the middle
        for (int rowNum=startPosX; rowNum<=endPosX; rowNum++) {
            for (int colNum=startPosY; colNum<=endPosY; colNum++) {
                if(maximum <= field[rowNum][colNum]) {
                    maximum = field[rowNum][colNum];
                    coordinate.setLocation(rowNum, colNum);
                }
            }
        }
        return coordinate.equals(oldCoordinate) ? coordinate : findMaxima(field, coordinate);
    }
}
