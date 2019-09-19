package ui;

import presenter.TutorialPresenter;
import repository.TutorialLoaderByFile;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class TutorialView extends JPanel implements ITutorialView, ActionListener {
    //ビューコンポーネント
    private JLabel tPageNum;
    private String nowPage;
    private String maxPage;
    private JTextArea tContent;
    private JButton bGoToNext;
    private JButton bGoToPrev;
    private JButton bExitTutorial;

    private PlotMapView vPlotMapView;
    //プレセンター
    private TutorialPresenter presenter;


    private SceneChanger sceneChanger;
    public TutorialView(SceneChanger sceneChanger) {
        super();
        this.sceneChanger=sceneChanger;

        tPageNum=SATSPUI.createLabel();
        tPageNum.setHorizontalAlignment(SwingConstants.CENTER);
        tContent=SATSPUI.createUnEditableTextArea();
        tContent.setMargin(new Insets(10,10,10,10));
        bGoToNext=SATSPUI.createButton("すすむ▶▶");
        bGoToNext.addActionListener(this);
        bGoToNext.setActionCommand("GO_TO_NEXT");
        bGoToNext.setForeground(SATSPUI.cyan);
        bGoToNext.setAlignmentX(JLabel.CENTER);

        bGoToPrev=SATSPUI.createButton("◀◀もどる");
        bGoToPrev.addActionListener(this);
        bGoToPrev.setActionCommand("GO_TO_PRE");
        bGoToPrev.setForeground(SATSPUI.cyan);
        bGoToPrev.setAlignmentX(JLabel.CENTER);

        bExitTutorial=SATSPUI.createButton("終了");
        bExitTutorial.addActionListener(this);
        bExitTutorial.setActionCommand("EXIT_TUTORIAL");

        setLayout(new BorderLayout());

        JPanel pContent=SATSPUI.createPanel(new BorderLayout());
        pContent.setPreferredSize(new Dimension(500,120));
        pContent.add(tContent,BorderLayout.CENTER);
        add(pContent,BorderLayout.NORTH);

        JPanel pOpe=SATSPUI.createPanel(new GridLayout(1,-1,10,10));
        pOpe.add(new javax.swing.Box(0));
        pOpe.add(bGoToPrev);
        pOpe.add(tPageNum);
        pOpe.add(bGoToNext);
        pOpe.add(bExitTutorial);
        add(pOpe,BorderLayout.SOUTH);

        vPlotMapView=new PlotMapView();
        add(vPlotMapView,BorderLayout.CENTER);

        presenter=new TutorialPresenter(this,new TutorialLoaderByFile());
    }

    @Override
    public void setNowPageText(String t) {
        this.nowPage=t;
        updateTPageNumber();
    }

    @Override
    public void setMaxPageText(String t) {
        this.maxPage=t;
        updateTPageNumber();
    }

    private void updateTPageNumber(){
        tPageNum.setText(new StringBuilder()
                .append(nowPage).append("/").append(maxPage)
                .toString()
        );
    }

    @Override
    public void setContentText(String t) {
        tContent.setText(t);
    }

    @Override
    public void setGoToNextActionAvailable(boolean b) {
        bGoToNext.setEnabled(b);
    }

    @Override
    public void setGoToPrevActionAvailable(boolean b) {
        bGoToPrev.setEnabled(b);
    }

    @Override
    public void drawPlots(List<Double> xList,List<Double> yList) {
        vPlotMapView.drawPlotMap(xList,yList);
    }

    @Override
    public void drawRouteMap(int[] route) {
        vPlotMapView.drawRouteMap(route);
    }

    @Override
    public void setBackgroundColor(Color color) {
        vPlotMapView.setBackground(color);
    }

    @Override
    public void exitTutorial() {
        sceneChanger.changeScene(SceneChanger.Scene.Playing);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String acco=e.getActionCommand();
        if(acco.equals(bGoToNext.getActionCommand())){
            presenter.onNextButtonClicked();
        }
        if(acco.equals(bGoToPrev.getActionCommand())){
            presenter.onPrevButtonClicked();
        }
        if(acco.equals(bExitTutorial.getActionCommand())){
            presenter.onExitTutorialButtonClicked();
        }
    }
}
