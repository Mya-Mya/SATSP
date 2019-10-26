package repository;

import java.awt.*;
import java.awt.geom.Point2D;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PlotRepository {
    public enum PlotSet{
        PLOT_120,
        PLOT_200,
        PLOT_500;
        @Override
        public String toString() {
            switch (this){
                case PLOT_120:return "120個プロット";
                case PLOT_200:return "200個プロット";
                case PLOT_500:return "500個プロット";
            }
            return "";
        }
        public String getFilePath(){
            switch (this){
                case PLOT_120:return"values\\map_data\\map_data.csv";
                case PLOT_200:return"values\\map_data\\map_data2.csv";
                case PLOT_500:return"values\\map_data\\map_data3.csv";
            }
            return "";
        }
    }
    private static PlotRepository instance;
    public static PlotRepository getInstance(){
        if(instance==null){
            instance=new PlotRepository();
        }
        return instance;
    }

    private Map<PlotSet, List<Point2D>>datas=new HashMap<>();
    private PlotRepository(){
        String jarPath=System.getProperty("java.class.path");
        String dirPath=jarPath.substring(0,jarPath.lastIndexOf(File.separator)+1);

        for(PlotSet p:PlotSet.values()){
            File dataFile=new File(dirPath+"\\"+p.getFilePath());
            List<Point2D>plots=PlotLoader.loadPlotsFromCSVFile(dataFile);
            datas.put(p,plots);
        }
    }

    public List<Point2D>getPlots(PlotSet p){
        return datas.get(p);
    }
}
