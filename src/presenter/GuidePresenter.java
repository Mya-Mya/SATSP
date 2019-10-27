package presenter;

import model.guide.Guide;
import model.guide.GuideLoader;
import view.fragment.IGuideView;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class GuidePresenter implements PropertyChangeListener {
    //ビュー
    private IGuideView mGuideView;
    //モデル
    private Guide mGuideModel;

    public GuidePresenter(GuideLoader loader,IGuideView view){
        mGuideModel=loader.execute();
        mGuideModel.addPCL(this);
        this.mGuideView=view;
    }

    public void onGoNextButtonClicked(){
        mGuideModel.goToNext();
    }

    public void onGoPrevButtonClicked(){
        mGuideModel.goToPrev();
    }

    public void onOpenAboutMeButtonClicked(){
        mGuideView.openAboutMePage();
    }

    public void onCloseButtonClicked(){
        mGuideView.banish();
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        mGuideView.setNowPageNumText(Integer.toString(mGuideModel.getNowPageIndex()));
        mGuideView.setTitleText(mGuideModel.getNowPage().title);
        mGuideView.setContentText(mGuideModel.getNowPage().content);
    }
}
