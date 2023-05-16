package gui;

import java.awt.Dimension;
import java.awt.Point;
import java.io.Serializable;

class WindowState implements Serializable {
    private Point location;
    private Dimension size;
    private boolean maximized;

    public WindowState(Point location, Dimension size, boolean maximized) {
        this.location = location;
        this.size = size;
        this.maximized = maximized;
    }

    public Point getLocation() {
        return location;
    }

    public Dimension getSize() {
        return size;
    }

    public boolean isMaximized() {
        return maximized;
    }

    public void setMaximized(boolean maximized) {
        this.maximized = maximized;
    }
}

