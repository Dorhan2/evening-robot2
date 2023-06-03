package gui;
import java.awt.event.KeyEvent;
import java.util.concurrent.TransferQueue;


public class Player extends Sprite {

    private int dx;
    private int dy;
    private boolean right = false;
    private boolean left = false;
    private boolean up = false;
    private boolean down = false;


    public Player(int x, int y, int weight) {
        super(x, y, weight);
        initPlayer();
    }

    private void initPlayer() {
        loadImage("C:\\Users\\USER\\IdeaProjects\\evening-robot2\\robots\\src\\gui\\zaba.jpg");
        getImageDimensions();
    }

    public void move() {
//        if (collisionToX)
//        {
//            x=x;
//        }
//        else {
//            y += dy;
//        }
//
//        if (collisionToY)
//        {
//            y=y;
//        }
//        else {
//            x += dx;
//        }


        if (x < 1) {
            x = 1;
        }

        if (y < 1) {
            y = 1;
        }
        x += dx;
        y += dy;
    }
    public void noMoveToRight(boolean coll){

        right = coll;
//        right = true;
    }

    public void noMoveToLeft(boolean coll){
        left = coll;
//        left = true;
    }
    public void noMoveToDown(boolean coll){
        down = coll;
//        down = true;
    }
    public void noMoveToUp(boolean coll){
        up = coll;
//        up = true;
    }

    public void keyPressed(KeyEvent e) {

        int key = e.getKeyCode();

        if (key == KeyEvent.VK_A && !left) {
            dx = -1;
        }

        if (key == KeyEvent.VK_D && !right) {
            dx = 1;
        }

        if (key == KeyEvent.VK_W && !up) {
            dy = -1;
        }

        if (key == KeyEvent.VK_S && !down) {
            dy = 1;
        }
    }

    public void keyReleased(KeyEvent e) {

        int key = e.getKeyCode();

        if (key == KeyEvent.VK_A) {
            dx = 0;
        }

        if (key == KeyEvent.VK_D) {
            dx = 0;
        }

        if (key == KeyEvent.VK_W) {
            dy = 0;
        }

        if (key == KeyEvent.VK_S) {
            dy = 0;
        }
    }
}
