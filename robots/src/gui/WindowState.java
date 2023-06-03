package gui;

import java.awt.Dimension;
import java.awt.Point;
import java.io.Serializable;

public class WindowState implements Serializable {
    private Point location;
    private Dimension size;
    private boolean maximized;
    private boolean minimized;

    public WindowState(Point location, Dimension size, boolean maximized) {
        this(location, size, maximized, false); // Call the constructor with default minimized value
    }

    public WindowState(Point location, Dimension size, boolean maximized, boolean minimized) {
        this.location = location;
        this.size = size;
        this.maximized = maximized;
        this.minimized = minimized;
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

    public boolean isMinimized() {
        return minimized;
    }

    public void setMinimized(boolean minimized) {
        this.minimized = minimized;
    }
}
