package model;

import java.awt.*;
import java.util.HashSet;
import java.util.Set;

public class Evaluator {
    private double recall;
    private double precision;
    private double fScore;

    private Set<Point> labels;
    private Set<Point> found;

    public Evaluator(Set<Point> labels, Set<Point> found) {
        this.labels = labels;
        this.found = found;

        calculatePrecision();
        calculateRecall();
        calculateFScore();
    }

    private void calculateRecall() {
        Set<Point> set = intersection(labels, found);
        recall = (double) set.size()/labels.size();
    }

    private void calculatePrecision() {
        Set<Point> set = intersection(labels, found);
        precision = (double) set.size()/found.size();
    }

    private void calculateFScore() {
        fScore = (2*precision*recall)/(precision+recall);
    }

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

    private static boolean closeEnough(Point a, Point b) {
        double ac = Math.abs(a.x - b.x);
        double cb = Math.abs(a.y - b.y);

        double tolerance = 5.0;
        return Math.hypot(ac, cb) < tolerance;
    }

    public double getRecall() {
        return recall;
    }

    public double getPrecision() {
        return precision;
    }

    public double getfScore() {
        return fScore;
    }

    public int numberOfLables() {
        return labels.size();
    }

    public int numberOfFoundTeeth() {
        return found.size();
    }

    public int numberOfCorrectTeeth() {
        return intersection(labels, found).size();
    }
}
