package presenter;

import javafx.stage.FileChooser;
import model.SAModel;
import model.SAModelListener;
import ui.IMainFrame;
import ui.SATSPUI;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class SAPresenter implements SAModelListener, ActionListener {
    private IMainFrame view;
    private SAModel model;
    private javax.swing.Timer timer;

    public SAPresenter(IMainFrame you) {
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
        try {
            model.loadCSV(new FileInputStream(file));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        //pressedStart();
    }

    public void touchedAt(double x, double y) {
        model.addCity(x, y);
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
        if(model.getNumCity()<4)view.setStartButtonEnabled(false);
        view.setNumCityText(Integer.toString(model.getNumCity()));
        view.setNowCostText(String.format("%.2f", model.getNowCost()));
        view.setBestCostText(String.format("%.4f", model.getBestCost()));
        view.setNowStepText(String.format("%,d", model.getNowStep()));
        view.setNowTempText(String.format("%.4f", model.getNowTemp()));
        view.setRemainTimeText(Double.toString(model.getRemainTime()));
        view.drawCityMap(model.getMapX(), model.getMapY());
        view.drawRouteMap(model.getRoute());
    }
}
