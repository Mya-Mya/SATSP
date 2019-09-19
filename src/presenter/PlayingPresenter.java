package presenter;

import model.SAModel;
import model.SAModelListener;
import repository.PlotLoader;
import ui.IPlayingView;
import ui.SATSPUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class PlayingPresenter implements SAModelListener, ActionListener {
    private IPlayingView view;
    private SAModel model;
    private javax.swing.Timer timer;

    public PlayingPresenter(IPlayingView you) {
        this.view = you;
        model = new SAModel(this);
        timer = new javax.swing.Timer(10, this);
        updateView();
        view.setMaxTimeText("3");
        view.setFirstTempText("10");
    }

    public void pressedStart() {
        model.setMaxTime(view.getMaxTime());
        model.setFirstTemp(view.getFirstTemp());
        timer.start();
        model.start();
    }

    public void pressedReset() {
        model.resetMap();
    }

    public void pressedLoad() {
        view.showOpenCSVDialog();
    }

    public void selectedFile(File file) {
        model.resetMap();
        model.addPlots(PlotLoader.loadPlotsFromCSVFile(file));
    }

    public void touchedAt(double x, double y) {
        model.addPlot(x, y);
    }

    @Override
    public void changedSAModel() {
        switch (model.getSAModelState()) {
            case resting:
                view.setBackgroundColor(SATSPUI.white);
                if (timer.isRunning()) timer.stop();
                view.setUIEnabled(true);
                break;
            case working:
                view.setBackgroundColor(SATSPUI.gray2);
                view.setUIEnabled(false);
                break;
        }
        updateView();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        updateView();
        view.forceRepaint();
    }

    protected void updateView() {
        if(model.getNumPlot()<4)view.setStartButtonEnabled(false);
        view.setNumPlotText(Integer.toString(model.getNumPlot()));
        view.setNowCostText(String.format("%.2f", model.getNowCost()));
        view.setBestCostText(String.format("%.4f", model.getBestCost()));
        view.setNowStepText(String.format("%,d", model.getNowStep()));
        view.setNowTempText(String.format("%.4f", model.getNowTemp()));
        view.setRemainTimeText(Double.toString(model.getRemainTime()));
        view.drawPlotMap(model.getMapX(), model.getMapY());
        view.drawRouteMap(model.getRoute());
    }
}
