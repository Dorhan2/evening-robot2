package gui;

import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.Dimension;

import java.util.HashMap;
import java.util.Vector;



@SuppressWarnings("serial")
public class Sokobana extends JPanel {
    public static final int PANEL_WIDTH = 380;
    public static final int PANEL_HEIGHT = 355;
    public static final int SQUARE_SIZE = 20;
    public static final int SPEED = 10;
    public static final Color SQUARE_COLOR = Color.RED;

    private int xPos = 0;
    private int yPos = 0;

    public Sokobana() {
        super();

        this.setOpaque(true);
        this.setFocusable(true);
        this.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent key) {
                switch (key.getKeyCode()) {
                    case KeyEvent.VK_W -> {
                        yPos -= SPEED;
                        if (yPos < 0)
                            yPos = 0;
                        repaint();
                    }
                    case KeyEvent.VK_A -> {
                        xPos -= SPEED;
                        if (xPos < 0)
                            xPos = 0;
                        repaint();
                    }
                    case KeyEvent.VK_S -> {
                        yPos += SPEED;
                        if ((yPos + SQUARE_SIZE) > PANEL_HEIGHT)
                            yPos = PANEL_HEIGHT - SQUARE_SIZE;
                        repaint();
                    }
                    case KeyEvent.VK_D -> {
                        xPos += SPEED;
                        if ((xPos + SQUARE_SIZE) > PANEL_WIDTH)
                            xPos = PANEL_WIDTH - SQUARE_SIZE;
                        repaint();
                    }
                }
            }
        });
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(PANEL_WIDTH, PANEL_HEIGHT);
    }
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(SQUARE_COLOR);
        g.fillRect(xPos, yPos, SQUARE_SIZE, SQUARE_SIZE);
        g.setColor(Color.ORANGE);
        g.fillRect(PANEL_WIDTH, 0, 10, PANEL_HEIGHT);
        g.fillRect(0, PANEL_HEIGHT, PANEL_WIDTH + 10, 10);
    }
//    public boolean AABBvsAABB(AABB a, AABB b ){
//        // Выходим без пересечения, потому что найдена разделяющая ось
//        if (a.max.x < b.min.x or a.min.x > b.max.x) return false;
//        if(a.max.y < b.min.y or a.min.y > b.max.y) return false;
//
//        // Разделяющая ось не найдена, поэтому существует по крайней мере одна пересекающая ось
//        return true
//    }
}





//    class AABB {
//        Vector min;
//        Vector max;
//        };
//
//
//    class Manifold{
//        Object A;
//        Object B;
//        float penetration;
//        Vector normal;
//        };
