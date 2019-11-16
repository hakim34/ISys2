package model;

import java.awt.*;
import java.util.HashSet;
import java.util.Set;

public class Evaluator {
    private double recall;
    private double precision;
    private double fScore;

    Set<Point> labels;
    Set<Point> found;

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
            if (b.contains(element)) {
                results.add(element);
            }
        }

        return results;
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
}