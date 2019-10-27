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

    public GuidePresenter(GuideLoader loader){
        mGuideModel=loader.execute();
        mGuideModel.addPCL(this);
    }

    public void setView(IGuideView view){
        this.mGuideView=view;
        propertyChange(null);
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
        mGuideView.setNowPageNumText(new StringBuilder()
                .append(mGuideModel.getNowPageIndex()+1).append('/').append(mGuideModel.getNumPage())
                .toString()
        );
        mGuideView.setTitleText(mGuideModel.getNowPage().title);
        mGuideView.setContentText(mGuideModel.getNowPage().content);
        mGuideView.setGoNextButtonEnabled(mGuideModel.canGoNext());
        mGuideView.setGoPrevButtonEnabled(mGuideModel.canGoPrev());
    }
}
