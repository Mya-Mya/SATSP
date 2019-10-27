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
    private JTextArea tContent;
    private JButton bPrev;
    private JLabel lPageIndicator;
    private JButton bNext;
    private JButton bAboutMe;
    private JButton bClose;

    public GuideView(GuidePresenter mGuidePresenter){
        super();
        this.mGuidePresenter=mGuidePresenter;
        setModal(false);
        setPreferredSize(new Dimension(500,400));
        getContentPane().setBackground(SATSPUI.cyan);

        lTitle=SATSPUI.createLabel();
        lTitle.setBackground(SATSPUI.black);
        lTitle.setFont(SATSPUI.largeFont);
        lTitle.setForeground(SATSPUI.white);

        tContent =SATSPUI.createUnEditableTextArea();
        tContent.setBackground(SATSPUI.black);
        tContent.setOpaque(false);
        tContent.setFont(SATSPUI.largeFont);
        tContent.setForeground(SATSPUI.white);

        bPrev=SATSPUI.createButton("<<");
        bPrev.setFont(SATSPUI.mainFont);
        bPrev.setForeground(SATSPUI.white);
        bPrev.setActionCommand("GO_PREV");
        bPrev.addActionListener(this);

        lPageIndicator=SATSPUI.createLabel();
        lPageIndicator.setFont(SATSPUI.mainFont);
        lPageIndicator.setForeground(SATSPUI.white);

        bNext=SATSPUI.createButton(">>");
        bNext.setFont(SATSPUI.mainFont);
        bNext.setForeground(SATSPUI.white);
        bNext.setActionCommand("GO_NEXT");
        bNext.addActionListener(this);

        bAboutMe=SATSPUI.createButton("<HTML><U>このアプリについて</U></HTML>");
        bAboutMe.setHorizontalAlignment(SwingConstants.RIGHT);
        bAboutMe.setContentAreaFilled(false);
        bAboutMe.setOpaque(false);
        bAboutMe.setFont(SATSPUI.mainFont);
        bAboutMe.setForeground(SATSPUI.black);
        bAboutMe.setActionCommand("ABOUT_ME");
        bAboutMe.addActionListener(this);

        bClose=SATSPUI.createButton("<HTML><U>閉じる</U></HTML>");
        bClose.setHorizontalAlignment(SwingConstants.RIGHT);
        bClose.setContentAreaFilled(false);
        bClose.setOpaque(false);
        bClose.setFont(SATSPUI.mainFont);
        bClose.setForeground(SATSPUI.black);
        bClose.setActionCommand("CLOSE");
        bClose.addActionListener(this);

        JPanel pBottom=SATSPUI.createPanel();
        pBottom.setLayout(new GridLayout(2,1));
        pBottom.setOpaque(false);

        JPanel pPageController=SATSPUI.createPanel();
        pPageController.setLayout(new GridLayout(1,3));
        pPageController.setOpaque(false);
        pPageController.add(bPrev);
        pPageController.add(lPageIndicator);
        pPageController.add(bNext);

        JPanel pOperator=SATSPUI.createPanel();
        pOperator.setLayout(new GridLayout(2,1));
        pOperator.setOpaque(false);
        pOperator.add(bAboutMe);
        pOperator.add(bClose);

        pBottom.add(pPageController);
        pBottom.add(pOperator);

        getContentPane().add(lTitle,BorderLayout.NORTH);
        getContentPane().add(Box.createHorizontalStrut(20),BorderLayout.WEST);
        getContentPane().add(Box.createHorizontalStrut(20),BorderLayout.EAST);
        getContentPane().add(tContent,BorderLayout.CENTER);
        getContentPane().add(pBottom,BorderLayout.SOUTH);

        pack();
        setVisible(true);

        mGuidePresenter.setView(this);
    }
    @Override
    public void setNowPageNumText(String t) {
        lPageIndicator.setText(t);
    }

    @Override
    public void setTitleText(String t) {
        lTitle.setText(t);
    }

    @Override
    public void setContentText(String t) {
        tContent.setText(t);
    }

    @Override
    public void banish() {
        setVisible(false);
    }

    @Override
    public void openAboutMePage() {
        System.out.println("GuideView.openAboutMePage");
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
