package ui;

import com.sun.javafx.geom.Point2D;

import java.awt.*;
import java.util.List;

public interface ITutorialView {
    void setNowPageText(String t);
    void setMaxPageText(String t);
    void setContentText(String t);
    void setGoToNextActionAvailable(boolean b);
    void setGoToPrevActionAvailable(boolean b);

    void drawPlots(List<Double> xList,List<Double> yList);
    void drawRouteMap(int[] route);
    void setBackgroundColor(Color color);

    void exitTutorial();
}
