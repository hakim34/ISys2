package model;

import java.awt.*;
import java.util.List;

public class Climber {

    /**
     * A 2d array of doubles of the height data.
     * The values are height points.
     * The inner arrays represent the data of a ring. [y]
     * The outer arrays represent the rings along the length of the rostrum. [x]
     */
    private List<List<Double>> data;

    /** Holds the total walked distance during a single climb */
    private int totalDistance;

    /** Holds the total height difference during a single climb */
    private double totalHeightDifference;

    public Climber(List<List<Double>> data) {
        this.data = data;
    }

    public Point start(Point start) {
        totalHeightDifference = 0;
        totalDistance = -1;
        return findPeak(start);
    }

    public int getTotalDistance() {
        return totalDistance;
    }

    public double getTotalHeightDifference() {
        return totalHeightDifference;
    }

    public double getAngle() {
        return Math.toDegrees(Math.atan(totalHeightDifference / totalDistance));
    }

    /**
     * Finds the local peak starting the search at the given point.
     * @param start The starting point for the search.
     * @return      The point of the local peak.
     */
    private Point findPeak(Point start) {
        double maxHeightDifference = 0;
        Point maxPoint = start;

        // Look up
        if (start.x > 0) {
            double heightDifference = getHeight(start.x - 1, start.y) - getHeightForPoint(maxPoint);
            if (heightDifference > maxHeightDifference) {
                maxHeightDifference = heightDifference;
                maxPoint = new Point(start.x - 1, start.y);
            }
        }

        // Look left
        if (start.y > 0) {
            double heightDifference = getHeight(start.x, start.y - 1) - getHeightForPoint(maxPoint);
            if (heightDifference > maxHeightDifference) {
                maxHeightDifference = heightDifference;
                maxPoint = new Point(start.x, start.y - 1);
            }
        }

        // Look right
        if (start.y + 1 < data.get(start.x).size()) {
            double heightDifference = getHeight(start.x, start.y + 1) - getHeightForPoint(maxPoint);
            if (heightDifference > maxHeightDifference) {
                maxHeightDifference = heightDifference;
                maxPoint = new Point(start.x, start.y + 1);
            }
        }

        // Look down
        if (start.x + 1 < data.size()) {
            double heightDifference = getHeight(start.x + 1, start.y) - getHeightForPoint(maxPoint);
            if (heightDifference > maxHeightDifference) {
                maxHeightDifference = heightDifference;
                maxPoint = new Point(start.x + 1, start.y);
            }
        }
        totalDistance++;
        totalHeightDifference = totalHeightDifference + maxHeightDifference;
        return maxPoint == start ? start : findPeak(maxPoint);
    }

    /**
     * Gets the value from the raw data for a specified Point.
     * @param p The point where we want the height from.
     * @return  The height from the specified point.
     */
    private double getHeightForPoint(Point p) {
        return data.get(p.x).get(p.y);
    }

    /**
     * Gets the value from the raw data for the specified coordinates.
     * @param x X coordinate.
     * @param y Y coordinate.
     * @return  The height from the specified coordinates.
     */
    private double getHeight(int x, int y) {
        return data.get(x).get(y);
    }
}
