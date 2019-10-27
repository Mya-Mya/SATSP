package model.tutorial;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.List;

public class Tutorial {
    PropertyChangeSupport pcs=new PropertyChangeSupport(this);
    public void addPCL(PropertyChangeListener pcl){pcs.addPropertyChangeListener(pcl);}

    private List<TutorialContent>tutorialContentList=new ArrayList<>();
    private int page=0;

    public Tutorial(){
    }

    public void addContent(TutorialContent tutorialContent){
        tutorialContentList.add(tutorialContent);
    }
    public List<TutorialContent> getTutorialContentList(){
        return tutorialContentList;
    }
    public void goToNext(){
        int oldPage=page;
        if (!canGoToNext()) {
            return;
        }
        page++;
        pcs.fireIndexedPropertyChange("PageWentToNext",0,oldPage,page);
    }
    public boolean canGoToNext(){
        return getPageIndex()< getMaxPageIndex();
    }

    public void goToPrev(){
        int oldPage=page;
        if(!canGoToPrev()){
            return;
        }
        page--;
        pcs.fireIndexedPropertyChange("PageWentToPrev",0,oldPage,page);
    }
    public boolean canGoToPrev(){
        return 0<getPageIndex();
    }
    public int getMaxPageIndex(){
        return tutorialContentList.size()-1;
    }
    public int getPageIndex(){
        return page;
    }
    public boolean shouldMakeRandomRoute(){
        return (page==5)||(page==7)||(page==8)||(page==9)||(page==10)||(page==11)||(page==12);
    }
    public boolean shouldLoadPlots(){
        return page==1;
    }
    public boolean shouldDoAnnealing(){
        return page==14;
    }
    public String getContent(){
        return tutorialContentList.get(getPageIndex()).getValue();
    }
}
