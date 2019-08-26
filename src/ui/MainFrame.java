package ui;

import presenter.SAPresenter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class MainFrame extends JFrame implements ActionListener, IMainFrame {
    private JLabel lNumCity;
    private JTextField tMaxTime;
    private JTextField tFirstTemp;
    private JButton bStart;
    private JButton bReset;
    private JButton bLoad;
    private JLabel lNowStep;
    private JLabel lNowTemp;
    private JLabel lNowCost;
    private JLabel lBestCost;
    private JLabel lRemainTime;
    private SAPresenter presenter;
    private CityMap vCityMap;

    public MainFrame() {
        super("SATSP");
        setPreferredSize(new Dimension(1000, 700));
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        vCityMap = new CityMap();
        add(vCityMap, BorderLayout.CENTER);

        JPanel pUpper = SATSPUI.createPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel lNumCityHelper=SATSPUI.createLabel("NumCity=");
        lNumCity = SATSPUI.createLabel();
        lNumCity.setForeground(SATSPUI.cyan);
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
        pUpper.add(lNumCityHelper);
        pUpper.add(lNumCity);
        pUpper.add(lMaxTimeHelper);
        pUpper.add(tMaxTime);
        pUpper.add(lFirstTempHelper);
        pUpper.add(tFirstTemp);
        pUpper.add(bStart);
        pUpper.add(bReset);
        pUpper.add(bLoad);
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

        presenter = new SAPresenter(this);
        vCityMap.setPresenter(presenter);

        pack();
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String acco = e.getActionCommand();
        if (acco.equals(bStart.getActionCommand())) {
            presenter.pressedStart();
        }
        if (acco.equals(bReset.getActionCommand())) {
            presenter.pressedReset();
        }
        if (acco.equals(bLoad.getActionCommand())) {
            presenter.pressedLoad();
        }
    }

    @Override
    public void setNumCityText(String t) {
        lNumCity.setText(t);
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
    public void drawCityMap(List<Double> mapX, List<Double> mapY) {
        vCityMap.drawCityMap(mapX, mapY);
    }

    @Override
    public void drawRouteMap(int[] route) {
        vCityMap.drawRouteMap(route);
    }

    @Override
    public void setBackgroundColor(Color c) {
        vCityMap.setBackground(c);
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
        vCityMap.repaint(1);
        repaint(1);
    }

    @Override
    public void showOpenCSVDialog() {
        JFileChooser c = new JFileChooser(System.getProperty("user.dir"));
        c.setFileSelectionMode(JFileChooser.FILES_ONLY);
        int ret = c.showOpenDialog(this);
        if (ret == JFileChooser.APPROVE_OPTION) {
            presenter.selectedFile(c.getSelectedFile());
        }
    }
}
