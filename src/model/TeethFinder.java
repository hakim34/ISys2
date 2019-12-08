package model;

import java.awt.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * This takes a 2d array as data and tries to find all teeth.
 * Teeth are defined as peaks relative to the surrounding data points
 */
public class TeethFinder {

    /** Set containing the coordinates of the found teeth */
    private Set<Point> foundTeeth = new HashSet<>();

    /**
     * A 2d array of doubles of the height data.
     * The values are height points.
     * The inner arrays represent the data of a ring. [y]
     * The outer arrays represent the rings along the length of the rostrum. [x]
     */
    private List<List<Double>> data;

    /**
     * Creates a new instance of the TeethFinder.
     * Expects a 2d array of the raw data.
     * @param data The 2d array containing the raw data.
     */
    public TeethFinder(List<List<Double>> data) {
        this.data = data;
    }

    /**
     * Finds all teeth (peaks) searching from scattered points which have a distance of searchDistance to the next point.
     * The found teeth are added to the Set foundTeeth.
     * @param searchDistance The distance between two points where searching will start at
     * @param angle          The minimum angle for a local maximum to be considered as a tooth
     * @param teethRadius    The minimum radius of a teeth
     * @param minimumHeight  The minimum height at which we search for teeth. This is used to filter the very edges  of the data.
     * @return This object with the populated data of found teeth.
     */
    public TeethFinder find(int searchDistance, double angle, int teethRadius, int minimumHeight) {
        Climber climber = new Climber(data, searchDistance, minimumHeight);
        for (int i = 0; i < data.size(); i = i + searchDistance) {
            for (int j = 0; j < data.get(i).size(); j = j + searchDistance) {
                Point p = climber.start(new Point(i, j));
                if (p == null) {
                    continue;
                }
                // Only add a found peak if it is unique and qualifies as a teeth
                if (foundTeeth.stream().noneMatch(f ->
                        Math.abs(f.x - p.x) < teethRadius
                            && Math.abs(f.y - p.y) < teethRadius)
                            && fitsSharpness(p, angle, teethRadius)) {
                    foundTeeth.add(p);
                }
            }
        }
        return this;
    }

    /**
     * Gets the angle of peak adjacent to y-axis of the peak.
     * @param p the point of supposedly the perimeter of the smallest teeth
     * @return the sharpness of the teeth respective to the perimeter of the teeth.
     */
    private double getSharpness(Point peak, Point p) {
        double ac = Math.abs(p.x - peak.x);
        double cb = Math.abs(p.y - peak.y);
        return Math.toDegrees(Math.atan(Math.hypot(ac, cb) / Math.abs(getHeightForPoint(peak) - getHeightForPoint(p))));
    }

    /**
     * Gets the average angle of the peak with respect to the (if applicable) four-way points in its surroundings
     * and compares it to the maximum gradient of a tooth.
     * @param angle         The maximum gradient a teeth should have
     * @param teethRadius   The minimum radius of a teeth
     * @return              True if the angles of the found peak match a teeth
     */
    private boolean fitsSharpness(Point peak, double angle, int teethRadius) {
        Point point1 = new Point(peak.x - teethRadius, peak.y - teethRadius);
        Point point2 = new Point(peak.x + teethRadius, peak.y - teethRadius);
        Point point3 = new Point(peak.x - teethRadius, peak.y + teethRadius);
        Point point4 = new Point(peak.x + teethRadius, peak.y + teethRadius);

        int possibleEdge = 0;

        if(getSharpness(peak, point1) >= angle) possibleEdge++;
        if(getSharpness(peak, point2) >= angle) possibleEdge++;
        if(getSharpness(peak, point3) >= angle) possibleEdge++;
        if(getSharpness(peak, point4) >= angle) possibleEdge++;

        if (possibleEdge > 1) return false;

        return (getSharpness(peak, point1) +
                getSharpness(peak, point2) +
                getSharpness(peak, point3) +
                getSharpness(peak, point4))/4 < angle;
    }

    /**
     * Gets the value from the raw data for a specified Point.
     * @param p The point where we want the height from.
     * @return  The height from the specified point.
     */
    private double getHeightForPoint(Point p) {
        if (p.x >= data.size() || p.x < 0)    return 0;
            // Wrap to the right
        else if (p.y >= data.get(p.x).size()) return data.get(p.x).get(p.y - data.get(0).size());
            // Wrap to the left
        else if (p.y < 0)                     return data.get(p.x).get(p.y + data.get(0).size());
        else                                  return data.get(p.x).get(p.y);
    }

    /**
     * Getter for the Set of found teeth.
     * @return  The Set containing the found teeth.
     */
    public Set<Point> getFoundTeeth() {
        return foundTeeth;
    }
}

