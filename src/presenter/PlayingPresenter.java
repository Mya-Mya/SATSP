package presenter;

import model.guide.GuideLoader;
import model.sa.SAModel;
import model.sa.SAModelListener;
import repository.GuideLoaderByFile;
import view.playing.IPlayingView;
import view.SATSPUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PlayingPresenter implements SAModelListener, ActionListener {
    private IPlayingView view;
    private SAModel mSAModel;
    private javax.swing.Timer timer;

    public PlayingPresenter(IPlayingView you) {
        this.view = you;
        mSAModel = new SAModel(this);
        timer = new javax.swing.Timer(10, this);
        updateView();
        view.setMaxTimeTextList(new String[]{"0.01","0.1","0.5","1","2","3","4","5","10","15","20","30"});
        view.setDefaultMaxTimeText("3");
        view.setFirstTempTextList(new String[]{"0.1","1","3","5","10","20","50","100","200","300","400"});
        view.setDefaultFirstTempText("10");

        GuideLoader mGuideLoader=new GuideLoaderByFile();
        GuidePresenter mGuidePresenter=new GuidePresenter(mGuideLoader);
        view.showGuideDialog(mGuidePresenter);
    }

    public void pressedStart() {
        mSAModel.setMaxTime(view.getMaxTime());
        mSAModel.setFirstTemp(view.getFirstTemp());
        timer.start();
        mSAModel.start();
    }

    public void pressedReset() {
        mSAModel.resetMap();
    }

    public void pressedLoad() {
        PlotsDataChoosingPresenter presenter=new PlotsDataChoosingPresenter(mSAModel);
        view.showOpenCSVDialog(presenter);
    }

    public void pressedOpenGuide() {
        GuideLoader mGuideLoader=new GuideLoaderByFile();
        GuidePresenter mGuidePresenter=new GuidePresenter(mGuideLoader);
        view.showGuideDialog(mGuidePresenter);
    }

    public void pressedMakeRandomRoute() {
        mSAModel.makeRandomRoute();
    }

    public void touchedAt(double x, double y) {
        mSAModel.addPlot(x, y);
    }

    @Override
    public void changedSAModel() {
        switch (mSAModel.getSAModelState()) {
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
        if(mSAModel.getNumPlot()<4)view.setStartButtonAndMakeRandomRouteButtonEnabled(false);
        view.setNumPlotText(Integer.toString(mSAModel.getNumPlot()));
        view.setNowCostText(String.format("%.2f", mSAModel.getNowCost()));
        view.setBestCostText(String.format("%.3f", mSAModel.getBestCost()));
        view.setNowStepText(String.format("%,d", mSAModel.getNowStep()));
        view.setNowTempText(String.format("%.3f", mSAModel.getNowTemp()));
        view.setRemainTimeText(Double.toString(mSAModel.getRemainTime()));
        view.drawPlotMap(mSAModel.getMapX(), mSAModel.getMapY());
        view.drawRouteMap(mSAModel.getRoute());
    }



}
