package view.playing;

import presenter.GuidePresenter;
import presenter.PlotsDataChoosingPresenter;

import java.awt.*;
import java.util.List;

public interface IPlayingView {
    void setMaxTimeTextList(String[]t);
    void setDefaultMaxTimeText(String t);
    void setFirstTempTextList(String[]t);
    void setDefaultFirstTempText(String t);
    void setNumPlotText(String t);
    void setNowStepText(String t);
    void setNowTempText(String t);
    void setNowCostText(String t);
    void setBestCostText(String s);
    void setRemainTimeText(String t);

    void drawPlotMap(List<Double> mapX, List<Double> mapY);
    void drawRouteMap(int[] route);
    void forceRepaint();

    void setBackgroundColor(Color c);
    double getMaxTime();
    double getFirstTemp();

    void setUIEnabled(boolean b);
    void setStartButtonAndMakeRandomRouteButtonEnabled(boolean b);
    void showOpenCSVDialog(PlotsDataChoosingPresenter presenter);
    void showGuideDialog(GuidePresenter mGuidePresenter);
}
