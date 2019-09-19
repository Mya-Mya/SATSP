package ui;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame
        implements SceneChanger {


    public MainFrame() {
        super("SATSP");
        setPreferredSize(new Dimension(1200, 700));
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        changeScene(Scene.Tutorial);

        pack();
        setVisible(true);
    }

    @Override
    public void changeScene(Scene newScene) {
        getContentPane().removeAll();
        switch (newScene){
            case Tutorial:
                getContentPane().add(new TutorialView(this),BorderLayout.CENTER);
                break;
            case Playing:
                getContentPane().add(new PlayingView(this),BorderLayout.CENTER);
                break;
        }
        validate();
    }
}
