import org.javatuples.Pair;

import java.awt.*;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.List;

public class Test {
    public static void main(String[] args) {
        System.out.println("Hello World");
        String csvFile = "src/main/resources/labels0.csv";
        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = ",";

        String[][] total = new String[5572][];
        Double[][] test =  {{0.0, 1.0, 0.0, 0.0, 0.0},
                            {0.0, 1.0, 0.0, 0.0, 0.0},
                            {0.0, 1.0, 0.5, 0.75, 0.75},
                            {0.0, 1.0, 0.75, 1.0, 1.0},
                            {0.0, 1.0, 1.0, 2.0, 2.0},
                            {0.0, 1.0, 1.0, 2.0, 3.0},
                            } ;

        List<Point> labels = new ArrayList<>();

        int midRow = test.length/2;
        System.out.println("midRow : " + midRow);
        int midColumn = test[midRow].length/2;
        System.out.println("midColumn : " + midColumn);
        Point startingPoint = new Point(midRow, midColumn);
        System.out.println("value of starting point : " + test[midRow][midColumn]);
        Point maximum = findMaxima(test, startingPoint);
        System.out.println(maximum + " with value of : " + test[maximum.x][maximum.y]);

        try {
            br = new BufferedReader(new FileReader(csvFile));
            while ((line = br.readLine()) != null) {

                // use comma as separator
//                String[] country = line.split(cvsSplitBy);
//                total[i] = country;
                int[] coordinate = Arrays.stream(line.split(cvsSplitBy)).mapToInt(Integer::parseInt).toArray();
                labels.add(new Point(coordinate[0], coordinate[1]));
            }
            System.out.println(labels);
            int closestDistance = 999;
            for(int i = 0; i < labels.size(); i++) {
                for(int i2 = i+1; i2 < labels.size(); i2++) {
                    int distance = (int) calculateDistance(labels.get(i), labels.get(i2));
                    if (distance < closestDistance && distance >= 10) {
                        closestDistance = distance;
                    }
                }
            }
            System.out.println(closestDistance);

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

    //TODO: x-axis is horizontal or vertical?

    public static Point findMaxima(Double[][] field, Point startingPoint) {
        int x = startingPoint.x;
        int y = startingPoint.y;

        //imagine the field to be rotated by 90 degrees to the right

        int startPosX = (x - 1 < 0) ? x : x-1;
        int startPosY = (y - 1 < 0) ? y : y-1;
        int endPosX =   (x + 1 > field.length -1) ? x : x+1;
        int endPosY =   (y + 1 > field[0].length - 1) ? y : y+1;

        double maximum = field[x][y];
        Point oldCoordinate = new Point(x, y); //starts in the middle
        Point coordinate =  new Point(x, y); //starts in the middle
        for (int rowNum=startPosX; rowNum<=endPosX; rowNum++) {
            for (int colNum=startPosY; colNum<=endPosY; colNum++) {
                if(maximum <= field[rowNum][colNum]) {
                    maximum = field[rowNum][colNum];
                    coordinate.setLocation(rowNum, colNum);
                }
            }
        }
        return coordinate.equals(oldCoordinate) ? coordinate : findMaxima(field, coordinate);
    }

    public static double calculateDistance(Point a, Point b) {
        double ac = Math.abs(a.y - b.y);
        double cb = Math.abs(a.x - a.x);

        return Math.hypot(ac, cb);
    }
}
