package ui;

import presenter.SAPresenter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CityMap extends JPanel implements MouseListener {
    private SAPresenter presenter;
    private List<Double> mapX;
    private List<Double> mapY;
    private int[] route;

    public CityMap() {
        setFocusable(true);
        addMouseListener(this);
        setBackground(SATSPUI.white);
    }

    public void setPresenter(SAPresenter p) {
        this.presenter = p;
    }

    public void drawCityMap(List<Double> mapX, List<Double> mapY) {
        this.mapX = mapX;
        this.mapY = mapY;
        updateUI();
    }

    public void drawRouteMap(int[] route) {
        this.route = route;
        updateUI();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(SATSPUI.black);
        double width = getWidth();
        double height = getHeight();
        if (mapX == null || mapY == null) return;
        int numCity = mapX.size();
        List<Double> modifiedMapX = new ArrayList<>(numCity);
        List<Double> modifiedMapY = new ArrayList<>(numCity);
        for (int i = 0; i < numCity; i++) {
            double x = mapX.get(i) * width;
            double y = mapY.get(i) * height;
            modifiedMapX.add(x);
            modifiedMapY.add(y);
            g.fillOval((int) x - 3, (int) y - 3, 6, 6);
        }
        if (route == null) return;
        for (int i = 0; i < route.length; i++) {
            int city1 = route[i];
            int city2 = route[i == route.length - 1 ? 0 : i + 1];
            double x1 = modifiedMapX.get(city1);
            double y1 = modifiedMapY.get(city1);
            double x2 = modifiedMapX.get(city2);
            double y2 = modifiedMapY.get(city2);
            g.drawLine((int) x1, (int) y1, (int) x2, (int) y2);
        }

    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        double width = getWidth();
        double height = getHeight();
        Point p = e.getPoint();
        presenter.touchedAt((double) p.x / width, (double) p.y / height);
    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
