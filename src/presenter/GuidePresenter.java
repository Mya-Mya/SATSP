package presenter;

import model.guide.Guide;
import model.guide.GuideLoader;
import repository.CurrentPath;
import view.fragment.IGuideView;

import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.IOException;

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
        String path= new StringBuilder()
                .append(CurrentPath.getCurrentPath()).append("\\values\\about_me\\about_me_page.pdf")
                .toString();
        try {
            Desktop.getDesktop().open(new File(path));
        } catch (IOException e) {
            e.printStackTrace();
        }

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
