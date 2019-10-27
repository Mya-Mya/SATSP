package client;

import model.guide.GuideLoader;
import model.tutorial.TutorialLoader;
import presenter.GuidePresenter;
import presenter.TutorialPresenter;
import repository.GuideLoaderByFile;
import repository.TutorialLoaderByFile;
import view.fragment.GuideView;
import view.fragment.IGuideView;

public class MyTest {
    public MyTest(){
        GuideLoader mGuideLoader=new GuideLoaderByFile();
        GuidePresenter mGuidePresenter=new GuidePresenter(mGuideLoader);
        IGuideView mGuideView=new GuideView(mGuidePresenter);
    }
}
