import model.CSVReader;
import model.Evaluator;
import model.TeethFinder;

import java.io.IOException;

public class Runner {

    public static void main(String[] args) throws IOException {
        CSVReader csv = new CSVReader().labels("src/resources/labels0.csv").data("src/resources/data0.csv"); //20 64 75
        TeethFinder finder = new TeethFinder(csv.getData(), 16, 63, 73).find();
        Evaluator e = new Evaluator(csv.getLabels(), finder.getFoundTeeth(), 10);
        CSVReader.writeToCsv(finder.getFoundTeeth(), "foundall.csv");
        printResults(csv, e);

        CSVReader csv2 = new CSVReader().labels("src/resources/labels1.csv").data("src/resources/data1.csv");
        TeethFinder finder2 = new TeethFinder(csv2.getData(), 20, 64, 75).find();
        Evaluator e2 = new Evaluator(csv2.getLabels(), finder2.getFoundTeeth(), 10);
        CSVReader.writeToCsv(finder2.getFoundTeeth(), "foundall2.csv");
        System.out.println("\nSecond datasets: \n");
        printResults(csv2, e2);
    }

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

