package view.fragment;

public interface IGuideView {
    void setNowPageNumText(String t);
    void setTitleText(String t);
    void setContentText(String t);
    void banish();
    void openAboutMePage();
    void setGoNextButtonEnabled(boolean b);
    void setGoPrevButtonEnabled(boolean b);

}
