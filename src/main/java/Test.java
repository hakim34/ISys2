import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.List;

public class Test {
    public static void main(String[] args) {
        System.out.println("Hello World");
        String csvFile = "src/main/resources/labels0.csv";
        BufferedReader br = null;
        String line;
        String cvsSplitBy = ",";

        List<Point> labels = new ArrayList<>();

        try {
            br = new BufferedReader(new FileReader(csvFile));
            while ((line = br.readLine()) != null) {
                int[] coordinate = Arrays.stream(line.split(cvsSplitBy)).mapToInt(Integer::parseInt).toArray();
                labels.add(new Point(coordinate[0], coordinate[1]));
            }
            System.out.println(labels);
            int closestDistance = 999;
            for(int i = 0; i < labels.size(); i++) {
                for(int i2 = i+1; i2 < labels.size(); i2++) {
                    int distance = (int) calculateDistance(labels.get(i), labels.get(i2));
                    if (distance < closestDistance && distance > 0) {
                        closestDistance = distance;
                    }
                }
            }
            System.out.println("closest distance: " + closestDistance);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private static double calculateDistance(Point a, Point b) {
        double ac = Math.abs(a.y - b.y);
        double cb = Math.abs(a.x - b.x);

        return Math.hypot(ac, cb);
    }
}
