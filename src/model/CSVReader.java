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
 * This class reads the required csv files and fills the corresponding data structures for later use elsewhere
 */
public class CSVReader {

    /** List of all the labels */
    private Set<Point> labels = new HashSet<>();

    /**
     * A 2d list of doubles of the height data.
     * The values are height points.
     * The inner list represent the data of a ring. [y]
     * The outer list represent the rings along the length of the rostrum. [x]
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

    /**
     * Getter for the number of rows in the raw data
     * @return The number of rows in the raw data
     */
    public int getRowNumbers() {
        return data.size();
    }

    /**
     * Getter for the number of columns in the raw data
     * @return The number of columns in the raw data
     */
    public int getColumnNumbers() {
        return data.get(0).size();
    }

    /**
     * Writes the given Set of found teeth to a CSV file
     * @param fileName  The name of the file to be created
     * @param points    The Set containing points of found teeth
     */
    public static void writeToCsv(Set<Point> points, String fileName) throws IOException {
        FileWriter csvWriter = new FileWriter(fileName);
        csvWriter.append("x");
        csvWriter.append(",");
        csvWriter.append("y");
        csvWriter.append("\n");
        for (Point p : points) {
            csvWriter.append(String.valueOf(p.x)).append(", ").append(String.valueOf(p.y)).append("\n");
        }
        csvWriter.flush();
        csvWriter.close();
    }
}

