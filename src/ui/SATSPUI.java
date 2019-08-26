package ui;

import javax.swing.*;
import java.awt.*;

public class SATSPUI {
    static public final Font mainFont=new Font("メイリオ",Font.PLAIN,15);
    static public final Color black=new Color(52, 49, 49);
    static public final Color gray1=new Color(82, 82, 82);
    static public final Color gray2=new Color(156, 156, 156);
    static public final Color white=new Color(240,240,240);
    static public final Color cyan=new Color(33, 201, 248);
    static public final JButton createButton(String text){
        JButton b=new JButton(text);
        b.setFont(mainFont);
        b.setForeground(white);
        b.setBackground(gray1);
        b.setContentAreaFilled(false);
        return b;
    }
    static public final JButton createButton(){
        return createButton("");
    }
    static public final JLabel createLabel(String text){
        JLabel l=new JLabel(text);
        l.setFont(mainFont);
        l.setForeground(white);
        l.setBackground(black);
        return l;
    }
    static public final JLabel createLabel(){
        return createLabel("");
    }
    static public final JPanel createPanel(LayoutManager l){
        JPanel p=new JPanel(l);
        p.setForeground(white);
        p.setBackground(black);
        return p;
    }
    static public final JPanel createPanel(){
        return createPanel(null);
    }
    static public final JTextField createTextField(){
        JTextField t=new JTextField();
        t.setFont(mainFont);
        t.setForeground(white);
        t.setBackground(black);
        return t;
    }
}
