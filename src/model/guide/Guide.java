package model.guide;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.List;

public class Guide {
    private PropertyChangeSupport pcs=new PropertyChangeSupport(this);
    public void addPCL(PropertyChangeListener pcl){pcs.addPropertyChangeListener(pcl);}

    private List<GuideContent>mGuideContentList=new ArrayList<>();
    int pageIndex =0;

    public Guide(){

    }

    public void addContent(GuideContent content){
        mGuideContentList.add(content);
    }

    public List<GuideContent> getmGuideContentList() {
        return mGuideContentList;
    }

    public GuideContent getPageAt(int index){
        return mGuideContentList.get(index);
    }

    public int getNowPageIndex(){
        return pageIndex;
    }

    public int getNumPage(){
        return mGuideContentList.size();
    }

    public GuideContent getNowPage(){
        return getPageAt(getNowPageIndex());
    }

    public boolean canGoNext(){
        return getNowPageIndex()<getNumPage()-1;
    }

    public boolean canGoPrev(){
        return getNowPageIndex()>0;
    }

    public void goToNext(){
        if(!canGoNext())return;
        int oldValue=getNowPageIndex();
        pageIndex++;
        pcs.fireIndexedPropertyChange("PageWentToNext",0,oldValue,getNowPageIndex());
    }

    public void goToPrev(){
        if(!canGoPrev())return;
        int oldValue=getNowPageIndex();
        pageIndex--;
        pcs.fireIndexedPropertyChange("PageWentToPrev",0,oldValue,getNowPageIndex());
    }
}
