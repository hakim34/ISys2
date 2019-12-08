package model;

import java.awt.*;
import java.util.List;

/**
 * An implementation of the mountain climber algorithm
 */
class Climber {

    /**
     * A 2d array of doubles of the height data.
     * The values are height points.
     * The inner arrays represent the data of a ring. [y]
     * The outer arrays represent the rings along the length of the rostrum. [x]
     */
    private List<List<Double>> data;

    /** The steps we take when climbing uphill */
    private int step;

    /** The minimum height at which we search for teeth. This is used to filter the very edges  of the data. */
    private int minimumHeight;

    /**
     * Constructor for the climber with the data set
     * @param data      The raw data
     */
    Climber(List<List<Double>> data, int step, int minimumHeight) {
        this.data = data;
        this.step = step;
        this.minimumHeight = minimumHeight;
    }

    /**
     * Finds the peak in the vicinity of the start point
     * Start point below the minimum height will be ignored
     * @param start The start point of the search
     * @return The local maximum
     */
    Point start(Point start) {
        if (data.get(start.x).get(start.y) < minimumHeight) {
            return null;
        }
        return findMaximum(start);
    }

    /**
     * Finds local maximum using the mountain climber algorithm.
     * @param startingPoint for the mountain climber.
     * @return the point where the local maximum is located.
     */
    private Point findMaximum(Point startingPoint) {
        int x = startingPoint.x;
        int y = startingPoint.y;

        int startPosX = (x - step < 0) ? x : x - step;
        int startPosY = (y - step < 0) ? y: y - step;
        int endPosX =   (x + 1 > data.size() - step) ? x : x + step;
        int endPosY =   (y + 1 > data.get(0).size() - step) ? y : y + step;

        double maximum = data.get(x).get(y);
        Point oldCoordinate = new Point(x, y); //starts in the middle
        Point coordinate =  new Point(x, y); //starts in the middle
        for (int rowNum = startPosX; rowNum <= endPosX; rowNum++) {
            for (int colNum = startPosY; colNum <= endPosY; colNum++) {
                if(maximum <= data.get(rowNum).get(colNum)) {
                    // We found a higher point
                    maximum = data.get(rowNum).get(colNum);
                    coordinate.setLocation(rowNum, colNum);
                }
            }
        }
        return coordinate.equals(oldCoordinate) ? coordinate : findMaximum(coordinate);
    }
}

