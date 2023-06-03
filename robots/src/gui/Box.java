package gui;

import java.awt.event.KeyEvent;

public class Box extends Sprite{
    private int dx;
    private int dy;

    public Box(int x, int y, int weight) {
        super(x, y, weight);
        initBox();
    }
    private void initBox() {
        loadImage("C:\\Users\\USER\\IdeaProjects\\evening-robot2\\robots\\src\\gui\\box.jpg");
        getImageDimensions();
    }
}
