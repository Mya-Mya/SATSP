package repository;

import java.awt.geom.Point2D;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class PlotLoader {
    static public List<Point2D> loadPlotsFromCSVFile(File file) {
        List<Point2D>out=new ArrayList<>();
        try {
            FileInputStream csvFile=new FileInputStream(file);
            BufferedReader br = new BufferedReader(new InputStreamReader(csvFile));
            String line;
            while ((line = br.readLine()) != null) {
                String[] cityData = line.split(",");
                out.add(new Point2D.Double(
                        Double.parseDouble(cityData[0])
                        ,Double.parseDouble(cityData[1])
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return out;
    }
}
