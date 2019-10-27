package view.playing;

import model.guide.GuideLoader;
import presenter.GuidePresenter;
import presenter.PlayingPresenter;
import presenter.PlotsDataChoosingPresenter;
import repository.GuideLoaderByFile;
import view.fragment.GuideView;
import view.fragment.IGuideView;
import view.fragment.PlotMapView;
import view.SATSPUI;
import view.SceneChanger;
import view.fragment.PlotsDataChoosingView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class PlayingView extends JPanel
implements IPlayingView, ActionListener {
    //プレセンター
    private PlayingPresenter presenter;
    //シーンチェンジャー
    private SceneChanger sceneChanger;
    //ビューコンポーネント
    private JLabel lNumPlot;
    private JTextField tMaxTime;
    private JTextField tFirstTemp;
    private JButton bStart;
    private JButton bReset;
    private JButton bLoad;
    private JButton bGuide;
    private JLabel lNowStep;
    private JLabel lNowTemp;
    private JLabel lNowCost;
    private JLabel lBestCost;
    private JLabel lRemainTime;
    private PlotMapView vPlotMapView;

    public PlayingView(SceneChanger sceneChanger){
        super();
        setLayout(new BorderLayout());
        setFocusable(true);

        vPlotMapView = new PlotMapView();
        add(vPlotMapView, BorderLayout.CENTER);

        JPanel pUpper = SATSPUI.createPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel lNumPlotHelper=SATSPUI.createLabel("NumPlot=");
        lNumPlot = SATSPUI.createLabel();
        lNumPlot.setForeground(SATSPUI.cyan);
        JLabel lMaxTimeHelper = SATSPUI.createLabel("MaxTime=");
        tMaxTime = SATSPUI.createTextField();
        tMaxTime.setPreferredSize(new Dimension(50, 25));
        tMaxTime.setForeground(SATSPUI.cyan);
        JLabel lFirstTempHelper = SATSPUI.createLabel("FirstTemp=");
        tFirstTemp = SATSPUI.createTextField();
        tFirstTemp.setPreferredSize(new Dimension(50, 25));
        tFirstTemp.setForeground(SATSPUI.cyan);
        bStart = SATSPUI.createButton("Start");
        bStart.setActionCommand("START");
        bStart.addActionListener(this);
        bReset = SATSPUI.createButton("Reset");
        bReset.setActionCommand("RESET");
        bReset.addActionListener(this);
        bLoad = SATSPUI.createButton("Load");
        bLoad.setActionCommand("LOAD");
        bLoad.addActionListener(this);
        bGuide=SATSPUI.createButton("Guide");
        bGuide.setActionCommand("GUIDE");
        bGuide.addActionListener(this);
        pUpper.add(lNumPlotHelper);
        pUpper.add(lNumPlot);
        pUpper.add(lMaxTimeHelper);
        pUpper.add(tMaxTime);
        pUpper.add(lFirstTempHelper);
        pUpper.add(tFirstTemp);
        pUpper.add(bStart);
        pUpper.add(bReset);
        pUpper.add(bLoad);
        pUpper.add(bGuide);
        add(pUpper, BorderLayout.NORTH);

        JPanel pDowner = SATSPUI.createPanel(new GridLayout(1, -1, 0, 10));
        JLabel lNowCostHelper=SATSPUI.createLabel("NowDist=");
        lNowCost = SATSPUI.createLabel();
        lNowCost.setForeground(SATSPUI.cyan);
        JLabel lNowStepHelper=SATSPUI.createLabel("Step=");
        lNowStep = SATSPUI.createLabel();
        lNowStep.setForeground(SATSPUI.cyan);
        JLabel lBestCostHelper=SATSPUI.createLabel("BestDist=");
        lBestCost = SATSPUI.createLabel();
        lBestCost.setForeground(SATSPUI.cyan);
        JLabel lNowTempHelper=SATSPUI.createLabel("Temp=");
        lNowTemp = SATSPUI.createLabel();
        lNowTemp.setForeground(SATSPUI.cyan);
        JLabel lRemainTimeHelper=SATSPUI.createLabel("Time=");
        lRemainTime = SATSPUI.createLabel();
        lRemainTime.setForeground(SATSPUI.cyan);
        pDowner.add(lNowCostHelper);
        pDowner.add(lNowCost);
        pDowner.add(lNowStepHelper);
        pDowner.add(lNowStep);
        pDowner.add(lBestCostHelper);
        pDowner.add(lBestCost);
        pDowner.add(lNowTempHelper);
        pDowner.add(lNowTemp);
        pDowner.add(lRemainTimeHelper);
        pDowner.add(lRemainTime);
        add(pDowner, BorderLayout.SOUTH);

        presenter=new PlayingPresenter(this);
        vPlotMapView.setPresenter(presenter);

    }

    @Override
    public void setNumPlotText(String t) {
        lNumPlot.setText(t);
    }

    @Override
    public void setNowStepText(String t) {
        lNowStep.setText(t);
    }

    @Override
    public void setNowTempText(String t) {
        lNowTemp.setText(t);
    }

    @Override
    public void setNowCostText(String t) {
        lNowCost.setText(t);
    }

    @Override
    public void setBestCostText(String s) {
        lBestCost.setText(s);
    }

    @Override
    public void setRemainTimeText(String t) {
        lRemainTime.setText(t);
    }

    @Override
    public void drawPlotMap(List<Double> mapX, List<Double> mapY) {
        vPlotMapView.drawPlotMap(mapX, mapY);
    }

    @Override
    public void drawRouteMap(int[] route) {
        vPlotMapView.drawRouteMap(route);
    }

    @Override
    public void setBackgroundColor(Color c) {
        vPlotMapView.setBackground(c);
    }

    @Override
    public double getMaxTime() {
        return Double.parseDouble(tMaxTime.getText());
    }

    @Override
    public void setMaxTimeText(String t) {
        tMaxTime.setText(t);
    }

    @Override
    public void setFirstTempText(String t) {
        tFirstTemp.setText(t);
    }

    @Override
    public double getFirstTemp() {
        return Double.parseDouble(tFirstTemp.getText());
    }

    @Override
    public void setUIEnabled(boolean b) {
        bStart.setEnabled(b);
        bReset.setEnabled(b);
        bLoad.setEnabled(b);
        tMaxTime.setEnabled(b);
        tFirstTemp.setEnabled(b);
    }

    @Override
    public void setStartButtonEnabled(boolean b) {
        bStart.setEnabled(b);
    }

    @Override
    public void forceRepaint() {
        vPlotMapView.repaint(1);
        repaint(1);
    }

    @Override
    public void showOpenCSVDialog(PlotsDataChoosingPresenter presenter) {
        new PlotsDataChoosingView(presenter);
    }

    @Override
    public void showGuideDialog(GuidePresenter mGuidePresenter) {
        IGuideView mGuideView=new GuideView(mGuidePresenter);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String acco = e.getActionCommand();
        if (acco.equals(bStart.getActionCommand())) {
            presenter.pressedStart();
        }
        else if (acco.equals(bReset.getActionCommand())) {
            presenter.pressedReset();
        }
        else if (acco.equals(bLoad.getActionCommand())) {
            presenter.pressedLoad();
        }
        else if(acco.equals(bGuide.getActionCommand())){
            presenter.pressedOpenGuide();
        }
    }


}
