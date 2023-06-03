package gui;

import java.awt.Dimension;
import java.awt.Point;
import java.beans.PropertyVetoException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JInternalFrame;

public class WindowStateManager {
    private static final String SAVE_FILE_PATH = "window_state.ser";

    /*
            * Saves the state of the window with the specified id.
            *
            * @param windowId   The id of the window
     * @param location   The location of the window
     * @param size       The size of the window
     * @param maximized  Flag indicating if the window is maximized
     * @param minimized  Flag indicating if the window is minimized
     */
    public static void saveWindowState(String windowId, Point location, Dimension size, boolean maximized, boolean minimized) {
        Map<String, WindowState> windowStates = loadWindowStates();

        WindowState state = new WindowState(location, size, maximized, minimized);
        windowStates.put(windowId, state);

        try (FileOutputStream fileOut = new FileOutputStream(SAVE_FILE_PATH);
             ObjectOutputStream out = new ObjectOutputStream(fileOut)) {
            out.writeObject(windowStates);
            System.out.println("Window state saved successfully.");
        } catch (IOException e) {
            System.out.println("Error saving window state: " + e.getMessage());
        }
    }

    /*
            * Restores the state of the window with the specified id.
            *
            * @param windowId   The id of the window
     * @param mainFrame  The main application frame
     */
    public static void restoreWindowState(String windowId, MainApplicationFrame mainFrame) {
        Map<String, WindowState> windowStates = loadWindowStates();
        WindowState state = windowStates.get(windowId);

        if (state != null) {
            JInternalFrame window = mainFrame.getWindowById(windowId);
            if (window != null) {
                window.setLocation(state.getLocation());
                window.setSize(state.getSize());
                if (state.isMaximized()) {
                    try {
                        window.setMaximum(true);
                    } catch (PropertyVetoException e) {
                        e.printStackTrace();
                    }
                }
                if (state.isMinimized()) {
                    try {
                        window.setIcon(true);
                    } catch (PropertyVetoException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    /**
     * Checks if there is a saved state for the window with the specified id.
     *
     * @param windowId   The id of the window
     * @return true if a saved state exists, false otherwise
     */
    public static boolean hasSavedWindowState(String windowId) {
        Map<String, WindowState> windowStates = loadWindowStates();
        return windowStates.containsKey(windowId);
    }

    private static Map<String, WindowState> loadWindowStates() {
        Map<String, WindowState> windowStates = new HashMap<>();

        File saveFile = new File(SAVE_FILE_PATH);
        if (saveFile.exists()) {
            try (FileInputStream fileIn = new FileInputStream(SAVE_FILE_PATH);
                 ObjectInputStream in = new ObjectInputStream(fileIn)) {
                windowStates = (Map<String, WindowState>) in.readObject();
            } catch (IOException | ClassNotFoundException e) {
                System.out.println("Error loading window states: " + e.getMessage());
            }
        }

        return windowStates;
    }
}
