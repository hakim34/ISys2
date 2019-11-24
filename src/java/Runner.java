import model.CSVReader;
import model.Evaluator;
import model.TeethFinder;

import java.io.IOException;

public class Runner {

    public static void main(String[] args) throws IOException {
        CSVReader csv = new CSVReader().labels("src/resources/labels0.csv").data("src/resources/data0.csv");
        TeethFinder finder = new TeethFinder(csv.getData()).find();
        Evaluator e = new Evaluator(csv.getLabels(), finder.getFoundTeeth());

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
