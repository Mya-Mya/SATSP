package view.fragment;

import presenter.GuidePresenter;
import view.SATSPUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GuideView extends JDialog implements IGuideView, ActionListener {
    //プレゼンター
    private GuidePresenter mGuidePresenter;
    //ビューコンポーネント
    private JLabel lTitle;
    private JLabel lContent;
    private JButton bPrev;
    private JLabel lPageIndicator;
    private JButton bNext;
    private JButton bAboutMe;
    private JButton bClose;

    public GuideView(GuidePresenter mGuidePresenter){
        super();
        this.mGuidePresenter=mGuidePresenter;
        setModal(false);
        setPreferredSize(new Dimension(200,400));
        setBackground(SATSPUI.black);

        lTitle=SATSPUI.createLabel();
        lTitle.setFont(SATSPUI.largeFont);
        lTitle.setForeground(SATSPUI.white);

        lContent=SATSPUI.createLabel();
        lContent.setFont(SATSPUI.largeFont);
        lContent.setForeground(SATSPUI.white);

        bPrev=SATSPUI.createButton(">>");
        bPrev.setFont(SATSPUI.mainFont);
        bPrev.setForeground(SATSPUI.white);
        bPrev.setActionCommand("GO_NEXT");
        bPrev.addActionListener(this);

        lPageIndicator=SATSPUI.createLabel();
        lPageIndicator.setFont(SATSPUI.mainFont);
        lPageIndicator.setForeground(SATSPUI.white);

        bNext=SATSPUI.createButton("<<");
        bNext.setFont(SATSPUI.mainFont);
        bNext.setForeground(SATSPUI.white);
        bNext.setActionCommand("GO_PREV");
        bNext.addActionListener(this);

        bAboutMe=SATSPUI.createButton("<HTML><U>このアプリについて</U></HTML>");
        bAboutMe.setContentAreaFilled(false);
        bAboutMe.setOpaque(false);
        bAboutMe.setFont(SATSPUI.mainFont);
        bAboutMe.setForeground(SATSPUI.white);
        bAboutMe.setActionCommand("ABOUT_ME");
        bAboutMe.addActionListener(this);

        bClose=SATSPUI.createButton("<HTML><U>閉じる</U></HTML>");
        bAboutMe.setContentAreaFilled(false);
        bAboutMe.setOpaque(false);
        bClose.setFont(SATSPUI.mainFont);
        bClose.setForeground(SATSPUI.white);
        bClose.setActionCommand("CLOSE");
        bClose.addActionListener(this);

        JPanel pBottom=SATSPUI.createPanel();
        pBottom.setLayout(new GridLayout(2,1));
        pBottom.setOpaque(false);

        JPanel pPageController=SATSPUI.createPanel();
        pPageController.setLayout(new GridLayout(3,1));
        pPageController.setOpaque(false);

        setVisible(true);

        mGuidePresenter.setView(this);
    }
    @Override
    public void setNowPageNumText(String t) {

    }

    @Override
    public void setTitleText(String t) {

    }

    @Override
    public void setContentText(String t) {

    }

    @Override
    public void banish() {
        setVisible(false);
    }

    @Override
    public void openAboutMePage() {

    }

    @Override
    public void setGoNextButtonEnabled(boolean b) {
        bNext.setEnabled(b);
    }

    @Override
    public void setGoPrevButtonEnabled(boolean b) {
        bPrev.setEnabled(b);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String acco=e.getActionCommand();
        if(acco.equals(bNext.getActionCommand())){
            mGuidePresenter.onGoNextButtonClicked();
        }
        else if(acco.equals(bPrev.getActionCommand())){
            mGuidePresenter.onGoPrevButtonClicked();
        }
        else if(acco.equals(bClose.getActionCommand())){
            mGuidePresenter.onCloseButtonClicked();
        }
        else if(acco.equals(bAboutMe.getActionCommand())){
            mGuidePresenter.onOpenAboutMeButtonClicked();
        }


    }
}
