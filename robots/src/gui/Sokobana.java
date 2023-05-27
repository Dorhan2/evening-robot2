package gui;

import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.Dimension;

@SuppressWarnings("serial")
public class Sokobana extends JPanel {
    public static final int PANEL_WIDTH = 400;
    public static final int PANEL_HEIGHT = 375;
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
        g.fillRect(100, 100, SQUARE_SIZE, SQUARE_SIZE);
    }
}