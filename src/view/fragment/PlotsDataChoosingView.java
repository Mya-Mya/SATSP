package view.fragment;

import presenter.PlotsDataChoosingPresenter;
import view.SATSPUI;

import javax.swing.*;
import java.awt.*;

public class PlotsDataChoosingView extends JDialog
        implements IPlotsDataChoosingView
{
    private PlotsDataChoosingPresenter presenter;
    private JPanel pPlotsDataList;

    public PlotsDataChoosingView(PlotsDataChoosingPresenter presenter){
        super();
        setModal(true);
        setPreferredSize(new Dimension(500,600));
        this.presenter=presenter;

        setLayout(new BorderLayout());

        JPanel pUpper=SATSPUI.createPanel(new BorderLayout());
        pUpper.setPreferredSize(new Dimension(400,80));
        JLabel lGuide=SATSPUI.createLabel("読み込むプロットデータを選択してください。");
        lGuide.setHorizontalAlignment(SwingConstants.CENTER);
        pUpper.add(lGuide,BorderLayout.CENTER);
        add(pUpper,BorderLayout.NORTH);

        pPlotsDataList=SATSPUI.createPanel();
        pPlotsDataList.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        pPlotsDataList.setLayout(new GridLayout(6,1,0,10));
        pPlotsDataList.setBackground(SATSPUI.white);
        add(pPlotsDataList,BorderLayout.CENTER);

        JButton bClose=SATSPUI.createButton("キャンセル");
        bClose.addActionListener(e->{
            presenter.onCloseButtonClick();
        });
        add(bClose,BorderLayout.SOUTH);

        presenter.setView(this);
        pack();
        setVisible(true);
    }
    @Override
    public void addPlotsDataName(String plotsDataName) {
        JButton button=SATSPUI.createButton();
        button.setPreferredSize(new Dimension(400,30));
        button.setText(plotsDataName);
        button.setForeground(SATSPUI.cyan);
        button.addActionListener(e->{
            presenter.onPlotsDataNameClick(plotsDataName);
        });
        pPlotsDataList.add(button);
    }

    @Override
    public void destroy() {
        setVisible(false);
    }
}
