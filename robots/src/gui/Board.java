package gui;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JPanel;
import javax.swing.Timer;



public class Board extends JPanel implements ActionListener{
    private Timer timer;
    private Player player;
    private List<Box> boxs;
    private boolean ingame;
    private final int ICRAFT_X = 40;
    private final int SIZE_BOX = 50;
    private final int SIZE_PLAYER = 40;
    private final int ICRAFT_Y = 60;
    private final int B_WIDTH = 400;
    private final int B_HEIGHT = 300;
    private final int DELAY = 15;
    private final int[][] pos = {
            {0, SIZE_BOX*8}, {SIZE_BOX, SIZE_BOX*8}, {SIZE_BOX*2, SIZE_BOX*8},
            {SIZE_BOX*3, SIZE_BOX*8}, {SIZE_BOX*4, SIZE_BOX*8}, {SIZE_BOX*5, SIZE_BOX*8},
            {SIZE_BOX*6, SIZE_BOX*8}, {SIZE_BOX*7, SIZE_BOX*8}, {SIZE_BOX*8, SIZE_BOX*8},//нижние

//            {-20, 20}, {-20, 60}, {-20, 100},
//            {-20, 140}, {-20, 180}, {-20, 220},
//            {-20, 260}, {-20, 300}, {-20, 340},

            {SIZE_BOX*8, 0}, {SIZE_BOX*8, SIZE_BOX}, {SIZE_BOX*8, SIZE_BOX*2},
            {SIZE_BOX*8, SIZE_BOX*3}, {SIZE_BOX*8, SIZE_BOX*4}, {SIZE_BOX*8, SIZE_BOX*5},//боковые
            {SIZE_BOX*8, SIZE_BOX*6}, {SIZE_BOX*8, SIZE_BOX*7}, {SIZE_BOX*8, SIZE_BOX*8}
    };



    public Board() {
        super();
        initBoard();
    }
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(B_WIDTH, B_HEIGHT);
    }

    private void initBoard() {
        this.setOpaque(true);
        this.addKeyListener(new TAdapter());
        this.setFocusable(true);

        ingame = true;

        setPreferredSize(new Dimension(B_WIDTH, B_HEIGHT));

        player = new Player(ICRAFT_X, ICRAFT_Y, 50);

        initBox();

        timer = new Timer(DELAY, (ActionListener) this);
        timer.start();
    }

    public void initBox() {

        boxs = new ArrayList<>();
//        int count = 0;
        for (int[] p : pos) {
            boxs.add(new Box(p[0], p[1], 100));
        boxs.add(new Box(SIZE_BOX*5,SIZE_BOX*5,10));
//            if (count <= 9) {
//                count += 1;
//                boxs.add(new Box(p[0], p[1], 1));
//            }
//            if (count > 9) {
//                count += 1;
//                boxs.add(new Box(p[0], p[1], 0));
//            }
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
            drawObjects(g);

        Toolkit.getDefaultToolkit().sync();
    }

    private void drawObjects(Graphics g) {

        g.drawImage(player.getImage(), player.getX(), player.getY(), this);
        for (Box box : boxs) {
            g.drawImage(box.getImage(), box.getX(), box.getY(), this);
        }


    }


    public void actionPerformed(ActionEvent e) {

        inGame();

        updatePlayer();

//        updateBox();

        checkCollisions();

        repaint();
    }
    private void inGame() {

        if (!ingame) {
            timer.stop();
        }
    }

    private void updatePlayer() {

        if (player.isVisible()) {
            player.move();
        }
    }

//    private void updateBox() {
//    }

    public void checkCollisions() {

        Rectangle r3 = player.getBounds();

        for (Box box : boxs) {

            Rectangle r2 = box.getBounds();
            int weightR2 = box.getWeight();
            if (r3.intersects(r2) && weightR2 > player.getWeight())  {
                if (r2.getX()-SIZE_PLAYER+1 >= r3.getX()){//слева
//                    box.x += 1;
                    player.noMoveToRight(true);
                }
                else if (r2.getX()+SIZE_PLAYER <= r3.getX()){//справа
//                    box.x -= 1;
                    player.noMoveToLeft(true);
                }
                else if (r2.getY() > r3.getY()){//сверху
//                    box.y += 1;
                    player.noMoveToDown(true);
                }
                else if (r2.getY() < r3.getY()){//снизу
//                    box.y -= 1;
                    player.noMoveToUp(true);
                }
//                if (r2.getY() >= player.getY()){
//                player.noMoveY(true);
//                }
//                else if (r2.getX() <= player.getX()){
//                    player.noMoveX(true);
//                }

//                if (weightR2 == 1){
//                    player.noMoveY(true);
//                }
//                if (weightR2 == 0){
//                    player.noMoveX(true);
//                }
            }
            else if (r3.intersects(r2) && weightR2 < player.getWeight()) {
                if (r2.getX()-SIZE_PLAYER+1 >= r3.getX()){//слева
                    box.x += 1;
                }
                else if (r2.getX()+SIZE_PLAYER <= r3.getX()){//справа
                    box.x -= 1;
                }
                else if (r2.getY() > r3.getY()){//сверху
                    box.y += 1;
                }
                else if (r2.getY() < r3.getY()){//снизу
                    box.y -= 1;
                }
            }
            else {
                player.noMoveToDown(false);
                player.noMoveToUp(false);
                player.noMoveToRight(false);
                player.noMoveToLeft(false);
            }
        }
    }

    private class TAdapter extends KeyAdapter {

        @Override
        public void keyReleased(KeyEvent e) {
            player.keyReleased(e);
        }

        @Override
        public void keyPressed(KeyEvent e) {
            player.keyPressed(e);
        }
    }

}