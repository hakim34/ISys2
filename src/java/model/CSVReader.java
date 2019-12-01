package model;

import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * This class reads the required csv files and fills the corresponding data structures or later use elsewhere
 */
public class CSVReader {

    /** List of all the labels */
    private Set<Point> labels = new HashSet<>();

    /**
     * A 2d array of doubles of the height data.
     * The values are height points.
     * The inner arrays represent the data of a ring. [y]
     * The outer arrays represent the rings along the length of the rostrum. [x]
     */
    private List<List<Double>> data = new ArrayList<>();

    /** The character used to split the csv data points */
    private String csvSplitBy = ",";

    /**
     * Creates a new CSVReader instance without any data
     */
    public CSVReader() {}

    /**
     * Reads the labels from a provided csv file and fills the corresponding data structure
     * @param filePath  Path to the csv file containing the labels
     * @return          This object with the updated data
     * @throws IOException In case file doesn't exist
     */
    public CSVReader labels(String filePath) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(filePath));
        String line;
        while ((line = br.readLine()) != null) {
            int[] coordinate = Arrays.stream(line.split(csvSplitBy)).mapToInt(Integer::parseInt).toArray();
            labels.add(new Point(coordinate[0], coordinate[1]));
        }
        return this;
    }

    /**
     * Reads the data from a provided csv file and fills the corresponding data structure
     * @param filePath  Path to the csv file containing the labels
     * @return          This object with the updated data
     * @throws IOException In case file doesn't exist
     */
    public CSVReader data(String filePath) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(filePath));
        String line;
        while ((line = br.readLine()) != null) {
            List<Double> points = Stream.of(line.split(csvSplitBy)).map(Double::valueOf).collect(Collectors.toList());
            data.add(points);
        }
        return this;
    }

    /**
     * Throws away some data and corresponding labels.
     * @param keep  The ratio of the data to keep.
     * @return      This object with the changed data.
     */
    public CSVReader slice(double keep) {
        return this;
    }

    /**
     * Getter for the list containing the labels
     * @return  The list containing the labels
     */
    public Set<Point> getLabels() {
        return labels;
    }

    /**
     * Getter for the 2d array containing the raw data
     * @return  The 2d array containing the raw data
     */
    public List<List<Double>> getData() {
        return data;
    }

    public int getRowNumbers() {
        return data.size();
    }

    public int getColumnNumbers() {
        return data.get(0).size();
    }

    public static void writeToCsv(Set<Point> points, String fileName) throws IOException {
        FileWriter csvWriter = new FileWriter(fileName);
        csvWriter.append("x");
        csvWriter.append(",");
        csvWriter.append("y");
        csvWriter.append(",");
        csvWriter.append("height");
        csvWriter.append("\n");
        for (Point p : points) {
            csvWriter.append(String.valueOf(p.x)).append(", ").append(String.valueOf(p.y));
        }
        csvWriter.flush();
        csvWriter.close();
    }
}

