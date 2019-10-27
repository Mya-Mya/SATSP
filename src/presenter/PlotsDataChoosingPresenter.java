package presenter;

import model.sa.SAModel;
import repository.PlotRepository;
import view.fragment.IPlotsDataChoosingView;

import java.awt.geom.Point2D;
import java.util.List;

public class PlotsDataChoosingPresenter {
    private IPlotsDataChoosingView view;
    private SAModel mSAModel;
    public PlotsDataChoosingPresenter(SAModel mSAModel){
        this.mSAModel=mSAModel;
    }
    public void setView(IPlotsDataChoosingView view){
        this.view=view;
        for (PlotRepository.PlotSet p:PlotRepository.PlotSet.values()){
            view.addPlotsDataName(p.toString());
        }
    }
    public void onPlotsDataNameClick(String plotsDataName){
        for(PlotRepository.PlotSet p:PlotRepository.PlotSet.values()){
            if(plotsDataName.equals(p.toString())){
                List<Point2D>plots= PlotRepository.getInstance().getPlots(p);
                mSAModel.resetMap();
                mSAModel.addPlots(plots);
                view.destroy();
                return;
            }
        }

    }
    public void onCloseButtonClick(){
        view.destroy();
    }
}
