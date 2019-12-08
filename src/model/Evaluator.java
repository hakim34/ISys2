package model;

import java.awt.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Evaluates the set of found teeth with the set of actual teeth
 */
public class Evaluator {

    /** The recall of our data */
    private double recall;

    /** The precision of our data */
    private double precision;

    /** The f-score of our data */
    private double fScore;

    /** The tolerance when determining if the found teeth is an actual teeth */
    private static double tolerance;

    /** The Set of actual teeth */
    private Set<Point> labels;

    /** The Set of found teeth */
    private Set<Point> found;

    /**
     * Constructor for the Evaluator for our  data
     * @param labels    The Set of actual teeth
     * @param found     The Set of found teeth
     * @param tolerance The tolerance when determining if the found teeth is an actual teeth
     */
    public Evaluator(Set<Point> labels, Set<Point> found, double tolerance) {
        this.labels = labels;
        this.found = found;
        Evaluator.tolerance = tolerance;

        calculatePrecision();
        calculateRecall();
        calculateFScore();
    }

    /**
     * Calculates the fraction of the total amount of relevant instances that were actually retrieved
     */
    private void calculateRecall() {
        Set<Point> set = intersection(labels, found);
        recall = (double) set.size()/labels.size();
    }

    /**
     * Calculates the fraction of relevant instances among the retrieved instances
     */
    private void calculatePrecision() {
        Set<Point> set = intersection(labels, found);
        precision = (double) set.size()/found.size();
    }

    /**
     * Calculates the f-score
     */
    private void calculateFScore() {
        fScore = (2*precision*recall)/(precision+recall);
    }

    /**
     * Gets the intersection of to sets
     * @param a Set a
     * @param b Set b
     * @return  The intersection of the two sets
     */
    private static Set<Point> intersection(Set<Point> a, Set<Point> b) {
        if (a.size() > b.size()) {
            return intersection(b, a);
        }

        Set<Point> results = new HashSet<>();

        for (Point element : a) {
            if (b.stream().anyMatch(f -> closeEnough(f, element))) {
                results.add(element);
            }
        }

        return results;
    }

    /**
     * Determines if the two given points are the same.
     * Here we include a tolerance so two points that are very close are seen as the same
     * @param a Point a
     * @param b Point b
     * @return  True if the two points point to the same spot
     */
    private static boolean closeEnough(Point a, Point b) {
        double ac = Math.abs(a.x - b.x);
        double cb = Math.abs(a.y - b.y);

        return Math.hypot(ac, cb) < tolerance;
    }

    /**
     * Getter for the recall
     * @return The recall
     */
    public double getRecall() {
        return recall;
    }

    /**
     * Getter for the precision
     * @return The precision
     */
    public double getPrecision() {
        return precision;
    }

    /**
     * Getter for the f-scoree
     * @return The f-score
     */
    public double getfScore() {
        return fScore;
    }

    /**
     * Getter for total number of labels (aka the actual teeth)
     * @return The total number of labels
     */
    public int numberOfLables() {
        return labels.size();
    }

    /**
     * Getter for the total number of found teeth
     * @return The number of found teeth
     */
    public int numberOfFoundTeeth() {
        return found.size();
    }

    /**
     * Getter for the total number of correctly identified teeth
     * @return The number of correctly identified teeth
     */
    public int numberOfCorrectTeeth() {
        return intersection(labels, found).size();
    }

    /**
     * Getter for the set of teeth that are correctly found
     * @return The set of correctly found teeth
     */
    public Set<Point> getCorrectTeeth() { return intersection(labels, found); }
}

