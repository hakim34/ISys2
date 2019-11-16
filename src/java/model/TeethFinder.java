package model;

import java.awt.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    /** The distance between two points where searching will start at */
    private int searchDistance = 20;

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
     * @return This object with the populated data of found teeth.
     */
    public TeethFinder find() {
        Climber climber = new Climber(data);
        for (int i = 0; i < data.size(); i = i + searchDistance) {
            for (int j = 0; j < data.get(i).size(); j = j + searchDistance) {
                Point p = climber.findPeak(new Point(i, j));
                if (foundTeeth.stream().noneMatch(f ->
                        f.x - p.x > -1 * searchDistance
                     && f.x - p.x < searchDistance
                     && f.y - p.y > -1 * searchDistance
                     && f.y - p.y < searchDistance)) {
                    foundTeeth.add(p);
                }
            }
        }
        return this;
    }

    /**
     * Getter for the Set of found teeth.
     * @return  The Set containing the found teeth.
     */
    public Set<Point> getFoundTeeth() {
        return foundTeeth;
    }
}
