import model.CSVReader;
import model.Evaluator;
import model.TeethFinder;

import java.io.IOException;

/**
 * Reads the CSV data and performs an analysis to find teeth
 */
public class Runner {

    /**
     * Reads the CSV data and performs an analysis to find teeth
     * At the end the results are printed on the console
     * @throws IOException When one of the label or data files are not found
     */
    public static void main(String[] args) throws IOException {
        // First data set
        CSVReader csv = new CSVReader().labels("resources/labels0.csv").data("resources/data0.csv");
        TeethFinder finder = new TeethFinder(csv.getData())
                .find(18, 71, 14, 15, 150);
        Evaluator e = new Evaluator(csv.getLabels(), finder.getFoundTeeth(), 15);
        CSVReader.writeToCsv(finder.getFoundTeeth(), "foundall.csv");
        printResults(csv, e);

        // Second data set
        CSVReader csv2 = new CSVReader().labels("resources/labels1.csv").data("resources/data1.csv");
        TeethFinder finder2 = new TeethFinder(csv2.getData())
                .find(18, 71, 14, 15, 150);
        Evaluator e2 = new Evaluator(csv2.getLabels(), finder2.getFoundTeeth(), 15);
        CSVReader.writeToCsv(finder2.getFoundTeeth(), "foundall2.csv");
        System.out.println("\nSecond data set: \n");
        printResults(csv2, e2);
    }

    /**
     * Prints the results from the two data sets to the console
     * @param csv   The CSV file reader
     * @param e     The Evaluator of the found teeth
     */
    private static void printResults(CSVReader csv, Evaluator e) {
        System.out.println("Precision               : " + e.getPrecision());
        System.out.println("Recall                  : " + e.getRecall());
        System.out.println("FScore                  : " + e.getfScore());

        System.out.println("Number of labels        : " + e.numberOfLables());
        System.out.println("Number of found teeth   : " + e.numberOfFoundTeeth());
        System.out.println("Number of correct teeth : " + e.numberOfCorrectTeeth());

        System.out.println("Number of rows          : " + csv.getRowNumbers());
        System.out.println("Number of columns       : " + csv.getColumnNumbers());
    }
}

