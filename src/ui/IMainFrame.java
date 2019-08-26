package ui;

import java.awt.*;
import java.util.List;

public interface IMainFrame {
    void setNumCityText(String t);

    void setNowStepText(String t);

    void setNowTempText(String t);

    void setNowCostText(String t);

    void setBestCostText(String s);

    void setRemainTimeText(String t);

    void drawCityMap(List<Double> mapX, List<Double> mapY);

    void drawRouteMap(int[] route);

    void setBackgroundColor(Color c);

    double getMaxTime();

    void setMaxTimeText(String t);

    double getFirstTemp();

    void setFirstTempText(String t);

    void setUIEnabled(boolean b);

    void setStartButtonEnabled(boolean b);

    void forceRepaint();

    void showOpenCSVDialog();
}
