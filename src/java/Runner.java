import model.CSVReader;
import model.Evaluator;
import model.TeethFinder;

import java.io.IOException;

public class Runner {

    public static void main(String[] args) throws IOException {
        CSVReader csv = new CSVReader().labels("src/resources/labels0.csv").data("src/resources/data0.csv");
        TeethFinder finder = new TeethFinder(csv.getData()).find();
        //System.out.println(finder.getFoundTeeth().size());
        //System.out.println(finder.getFoundTeeth());
        Evaluator e = new Evaluator(csv.getLabels(), finder.getFoundTeeth());
        System.out.println(e.getPrecision());
        System.out.println(e.getRecall());
        System.out.println(e.getfScore());
    }
}
