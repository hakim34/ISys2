package model;

import java.awt.*;
import java.util.List;

class Climber {

    /**
     * A 2d array of doubles of the height data.
     * The values are height points.
     * The inner arrays represent the data of a ring. [y]
     * The outer arrays represent the rings along the length of the rostrum. [x]
     */
    private List<List<Double>> data;

    private double edgeAngle;

    /* the local maximum found */
    private Point peak;

    Climber(List<List<Double>> data, double edgeAngle) {
        this.data = data;
        this.edgeAngle = edgeAngle;
    }

    Point start(Point start) {
        peak = findMaximum(start);
        return peak;
    }

    /**
     * Gets the angle of peak adjacent to y-axis of the peak.
     * @param x the point of supposedly the perimeter of the smallest teeth
     * @return the sharpness of the teeth in respective to the perimeter of the teeth.
     */
    private double getSharpness(Point x) {
        double ac = Math.abs(x.x - peak.x);
        double cb = Math.abs(x.y - peak.y);
        return Math.toDegrees(Math.atan(Math.hypot(ac, cb)/Math.abs(getHeightForPoint(peak)-getHeightForPoint(x))));
    }

    /**
     * Gets the average angle of the peak with respect to the (if applicable) four-way points in its surroundings
     * @return average angle.
     */
    boolean sharpEnough(double angle, int teethRadius) {
        Point point1 = new Point(peak.x- teethRadius, peak.y- teethRadius);
        Point point2 = new Point(peak.x+ teethRadius, peak.y- teethRadius);
        Point point3 = new Point(peak.x- teethRadius, peak.y+ teethRadius);
        Point point4 = new Point(peak.x+ teethRadius, peak.y+ teethRadius);

        if(getSharpness(point1) >= edgeAngle) return false;
        if(getSharpness(point2) >= edgeAngle) return false;
        if(getSharpness(point3) >= edgeAngle) return false;
        if(getSharpness(point4) >= edgeAngle) return false;

        return (getSharpness(point1) +
                getSharpness(point2) +
                getSharpness(point3) +
                getSharpness(point4))/4 < angle;
    }

    /**
     * Finds local maximum using the mountain climber algorithm.
     * @param startingPoint for the mountain climber.
     * @return the point where the local maximum is located.
     */
    private Point findMaximum(Point startingPoint) {
        int x = startingPoint.x;
        int y = startingPoint.y;

        int startPosX = (x - 1 < 0) ? x : x-1;
        int startPosY = (y - 1 < 0) ? y: y-1;
        int endPosX =   (x + 1 > data.size() -1) ? x : x+1;
        int endPosY =   (y + 1 > data.get(0).size() - 1) ? y : y+1;

        double maximum = data.get(x).get(y);
        Point oldCoordinate = new Point(x, y); //starts in the middle
        Point coordinate =  new Point(x, y); //starts in the middle
        for (int rowNum=startPosX; rowNum<=endPosX; rowNum++) {
            for (int colNum=startPosY; colNum<=endPosY; colNum++) {
                if(maximum <= data.get(rowNum).get(colNum)) {
                    maximum = data.get(rowNum).get(colNum);
                    coordinate.setLocation(rowNum, colNum);
                }
            }
        }
        return coordinate.equals(oldCoordinate) ? coordinate : findMaximum(coordinate);
    }

    /**
     * Gets the value from the raw data for a specified Point.
     * @param p The point where we want the height from.
     * @return  The height from the specified point.
     */
    private double getHeightForPoint(Point p) {
        if(p.x >= data.size() || p.x < 0) return 0;
        else if(p.y >= data.get(p.x).size()) return data.get(p.x).get(p.y-3600);
        else if(p.y < 0) return data.get(p.x).get(p.y+3600);
        else return data.get(p.x).get(p.y);
    }
}

