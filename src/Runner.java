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
        CSVReader csv = new CSVReader().labels("/Users/hakim.id/Documents/ISys2New/src/resources/labels0.csv").data("/Users/hakim.id/Documents/ISys2New/src/resources/data0.csv");
        TeethFinder finder = new TeethFinder(csv.getData())
                .find(14, 71, 14, 181);
        Evaluator e = new Evaluator(csv.getLabels(), finder.getFoundTeeth(), 5);
        CSVReader.writeToCsv(finder.getFoundTeeth(), "foundall.csv");
        CSVReader.writeToCsv(e.getCorrectTeeth(), "correctTeeth.csv");
        printResults(csv, e);

        // Second data set
        CSVReader csv2 = new CSVReader().labels("/Users/hakim.id/Documents/ISys2New/src/resources/labels1.csv").data("/Users/hakim.id/Documents/ISys2New/src/resources/data1.csv");
        TeethFinder finder2 = new TeethFinder(csv2.getData())
                .find(14, 71, 14, 181);
        Evaluator e2 = new Evaluator(csv2.getLabels(), finder2.getFoundTeeth(), 5);
        CSVReader.writeToCsv(finder2.getFoundTeeth(), "foundall2.csv");
        CSVReader.writeToCsv(e2.getCorrectTeeth(), "correctTeeth2.csv");
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

