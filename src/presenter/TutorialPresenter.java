package presenter;

import model.SAModel;
import model.SAModelListener;
import model.Tutorial;
import model.TutorialLoader;
import repository.PlotRepository;
import view.tutorial.ITutorialView;
import view.SATSPUI;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class TutorialPresenter implements PropertyChangeListener, SAModelListener, ActionListener {
    //モデル
    private Tutorial tutorialModel;
    private SAModel saModel;
    //ビュー
    private ITutorialView view;
    //ユーティリティ
    private javax.swing.Timer timer;

    public TutorialPresenter(ITutorialView view, TutorialLoader tutorialLoader){
        this.view=view;
        view.setBackgroundColor(SATSPUI.white);

        tutorialModel =tutorialLoader.execute();
        tutorialModel.addPCL(this);

        saModel=new SAModel(this);
        saModel.setFirstTemp(3);
        saModel.setMaxTime(3);

        timer=new Timer(20,this);

        view.setMaxPageText(Integer.toString(tutorialModel.getMaxPageIndex()+1));
        propertyChange(null);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        view.setContentText(tutorialModel.getContent());
        view.setGoToNextActionAvailable(tutorialModel.canGoToNext());
        view.setGoToPrevActionAvailable(tutorialModel.canGoToPrev());
        view.setNowPageText(Integer.toString(tutorialModel.getPageIndex()+1));
    }

    public void onNextButtonClicked(){
        if(tutorialModel.canGoToNext()) tutorialModel.goToNext();

        if (tutorialModel.shouldLoadPlots()){
            saModel.addPlots(PlotRepository.getInstance().getPlots(PlotRepository.PlotSet.PLOT_200));
        }
        if(tutorialModel.shouldMakeRandomRoute()){
            saModel.makeRandomRoute();
        }
        if(tutorialModel.shouldDoAnnealing()){
            //全てのUI操作を無効
            setAllUIEnabled(false);
            //画面更新用のタイマーを起動
            timer.start();
            //焼きなましを開始
            saModel.start();
        }

    }
    public void onPrevButtonClicked(){
        if(tutorialModel.canGoToPrev()) tutorialModel.goToPrev();
    }
    public void onExitTutorialButtonClicked(){
        view.exitTutorial();
    }

    private void setAllUIEnabled(boolean b){
        view.setGoToPrevActionAvailable(b);
        view.setGoToNextActionAvailable(b);
    }

    @Override
    public void changedSAModel() {
        view.drawPlots(saModel.getMapX(),saModel.getMapY());
        view.drawRouteMap(saModel.getRoute());
        if (saModel.getSAModelState()== SAModel.SAModelState.working){
            setAllUIEnabled(false);
            view.setBackgroundColor(SATSPUI.gray2);
        }
        if(saModel.getSAModelState()== SAModel.SAModelState.resting
        && tutorialModel.shouldDoAnnealing()){
            timer.stop();
            setAllUIEnabled(true);
            onNextButtonClicked();
            view.setBackgroundColor(SATSPUI.white);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        view.drawPlots(saModel.getMapX(),saModel.getMapY());
        view.drawRouteMap(saModel.getRoute());
    }
}
