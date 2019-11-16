package model;

import java.awt.*;
import java.util.HashSet;
import java.util.Set;

/**
 * This class provides methods for evaluating the precision, recall and fscore.
 */
public class Evaluator {
    /** recall value */
    private double recall;
    /** precision value */
    private double precision;
    /** fScore value */
    private double fScore;

    /**
     * This method calculates the recall by comparing the intersection of the labels
     * and the teeth found and the total number of the actual teeth.
     * @param labels the labeled teeth
     * @param found the teeth found
     */
    public void calculateRecall(Set<Point> labels, Set<Point> found) {
        Set<Point> set = intersection(labels, found);
        recall = (double) set.size()/labels.size();
    }

    /**
     * This method calculates the precision by comparing the intersection of the labels
     * and the teeth found and the total number of the teeth found.
     * @param labels the labeled teeth
     * @param found the teeth found
     */
    public void calculatePrecision(Set<Point> labels, Set<Point> found) {
        Set<Point> set = intersection(labels, found);
        precision = (double) set.size()/found.size();
    }

    /**
     * This method calculates the f score using (2PR/(P+R)) formula.
     * @param labels the labeled teeth
     * @param found the teeth found
     */
    public void calculateFScore(Set<Point> labels, Set<Point> found) {
        fScore = (2*precision*recall)/(precision+recall);
    }

    /**
     * This method finds the intersections of two sets
     * @param a first set
     * @param b second set
     * @return set of the common elements of both sets
     */
    private static Set<Point> intersection(Set<Point> a, Set<Point> b) {
        if (a.size() > b.size()) {
            return intersection(b, a);
        }

        Set<Point> results = new HashSet<>();

        for (Point element : a) {
            if (b.contains(element)) {
                results.add(element);
            }
        }

        return results;
    }

    /**
     * Getter of the recall value.
     * @return recall
     */
    public double getRecall() {
        return recall;
    }

    /**
     * Getter of the precision value.
     * @return precision
     */
    public double getPrecision() {
        return precision;
    }

    /**
     * Getter of the f-score value.
     * @return fScore
     */
    public double getfScore() {
        return fScore;
    }
}
