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
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
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
    private int[][] pos;


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
        File file = new File("C:\\Users\\USER\\IdeaProjects\\evening-robot2\\robots\\src\\gui\\level.txt");
        Scanner s = null;
        try {
            s = new Scanner(file).useDelimiter("\\Z");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        assert s != null;
        String contents = s.next();
        System.out.println("level: "+contents);
        if (Integer.parseInt(contents) == 1){
            pos = new int[][]{
                    {0, SIZE_BOX*8}, {SIZE_BOX, SIZE_BOX*8}, {SIZE_BOX*2, SIZE_BOX*8},
                    {SIZE_BOX*3, SIZE_BOX*8}, {SIZE_BOX*4, SIZE_BOX*8}, {SIZE_BOX*5, SIZE_BOX*8},
                    {SIZE_BOX*6, SIZE_BOX*8}, {SIZE_BOX*7, SIZE_BOX*8}, {SIZE_BOX*8, SIZE_BOX*8},//нижние

                    {SIZE_BOX*8, 0}, {SIZE_BOX*8, SIZE_BOX}, {SIZE_BOX*8, SIZE_BOX*2},
                    {SIZE_BOX*8, SIZE_BOX*3}, {SIZE_BOX*8, SIZE_BOX*4}, {SIZE_BOX*8, SIZE_BOX*5},//боковые
                    {SIZE_BOX*8, SIZE_BOX*6}, {SIZE_BOX*8, SIZE_BOX*7}, {SIZE_BOX*8, SIZE_BOX*8}
            };
        }
        else if (Integer.parseInt(contents) == 2){
            pos = new int[][]{
                    {0, SIZE_BOX * 11}, {SIZE_BOX, SIZE_BOX * 11}, {SIZE_BOX * 2, SIZE_BOX * 11},
                    {SIZE_BOX * 3, SIZE_BOX * 11}, {SIZE_BOX * 4, SIZE_BOX * 11}, {SIZE_BOX * 5, SIZE_BOX * 11},
                    {SIZE_BOX * 6, SIZE_BOX * 11}, {SIZE_BOX * 7, SIZE_BOX * 11}, {SIZE_BOX * 8, SIZE_BOX * 11},//нижние

                    {SIZE_BOX * 8, 0}, {SIZE_BOX * 8, SIZE_BOX}, {SIZE_BOX * 8, SIZE_BOX * 2},
                    {SIZE_BOX * 8, SIZE_BOX * 3}, {SIZE_BOX * 8, SIZE_BOX * 4}, {SIZE_BOX * 8, SIZE_BOX * 5},//боковые
                    {SIZE_BOX * 8, SIZE_BOX * 6}, {SIZE_BOX * 8, SIZE_BOX * 7}, {SIZE_BOX * 8, SIZE_BOX * 8},
                    {SIZE_BOX * 8, SIZE_BOX * 9}, {SIZE_BOX * 8, SIZE_BOX * 10}, {SIZE_BOX * 8, SIZE_BOX * 11},
            };
        }

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
            }
            else if (r3.intersects(r2) && weightR2 < player.getWeight()) {
                if (r2.getX()-SIZE_PLAYER+1 >= r3.getX()){//слева
                    CollisionBoxs(box);
                    box.x += 1;
                }
                else if (r2.getX()+SIZE_PLAYER <= r3.getX()){//справа
                    CollisionBoxs(box);
                    box.x -= 1;
                }
                else if (r2.getY() > r3.getY()){//сверху
                    CollisionBoxs(box);
                    box.y += 1;
                }
                else if (r2.getY() < r3.getY()){//снизу
                    CollisionBoxs(box);
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
    public void CollisionBoxs(Box moveBox){
        Rectangle r3 = moveBox.getBounds();
        for (Box box : boxs) {
            if (box != moveBox){
                Rectangle r2 = box.getBounds();
                if (r3.intersects(r2)){
                    if (r2.getX()-SIZE_BOX+1 >= r3.getX()){//слева
//                    box.x += 1;
                        player.noMoveToRight(true);
                    }
                    else if (r2.getX()+SIZE_BOX <= r3.getX()){//справа
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
                }
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
