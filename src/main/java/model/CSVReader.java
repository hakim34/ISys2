package model;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class CSVReader {

    /** List of all the labels */
    List<Point> labels = new ArrayList<>();

    /** A 2d array of double of the data. The outer array contains each a row of height points.  */
    Double[][] data = new Double[][]{};

    Character csvSplitBy = ',';

    public CSVReader() {}

    public CSVReader labels(String filePath) {
        return this;
    }

    public CSVReader data(String filePath) {
        return this;
    }
}
