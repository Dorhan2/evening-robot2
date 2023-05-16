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
import javax.swing.JFrame;

public class WindowStateManager {
    private static final String SAVE_FILE_PATH = "window_state.ser";

    /**
     * Сохраняет состояние внутренних окон.
     *
     * @param mainFrame Главное окно приложения
     */
    public static void saveWindowStates(MainApplicationFrame mainFrame) {
        Map<String, WindowState> windowStates = new HashMap<>();

        // Проходимся по всем внутренним окнам и сохраняем их состояние
        for (JInternalFrame window : mainFrame.getInternalFrames()) {
            String windowId = window.getName();
            Point windowLocation = window.getLocation();
            Dimension windowSize = window.getSize();
            boolean windowMaximized = window.isMaximum();

            WindowState state = new WindowState(windowLocation, windowSize, windowMaximized);
            windowStates.put(windowId, state);
        }

        try (FileOutputStream fileOut = new FileOutputStream(SAVE_FILE_PATH);
             ObjectOutputStream out = new ObjectOutputStream(fileOut)) {
            // Сериализуем и сохраняем объект windowStates в файл
            out.writeObject(windowStates);
            System.out.println("Window states saved successfully.");
        } catch (IOException e) {
            System.out.println("Error saving window states: " + e.getMessage());
        }
    }


    /**
     * Проверяет, есть ли сохраненные состояния окон.
     *
     * @return true, если файл сохранения существует; иначе false.
     */
    public static boolean hasSavedWindowStates() {
        File saveFile = new File(SAVE_FILE_PATH);
        return saveFile.exists();
    }

    /**
     * Восстанавливает состояние внутренних окон.
     *
     * @param mainFrame Главное окно приложения
     */
    public static void restoreWindowStates(MainApplicationFrame mainFrame) {
        try (FileInputStream fileIn = new FileInputStream(SAVE_FILE_PATH);
             ObjectInputStream in = new ObjectInputStream(fileIn)) {
            // Восстанавливаем объект windowStates из файла
            Map<String, WindowState> windowStates = (Map<String, WindowState>) in.readObject();

            // Проходимся по всем внутренним окнам и восстанавливаем их состояние
            for (JInternalFrame window : mainFrame.getInternalFrames()) {
                String windowId = window.getName();
                if (windowStates.containsKey(windowId)) {
                    WindowState state = windowStates.get(windowId);
                    window.setLocation(state.getLocation());
                    window.setSize(state.getSize());
                    if (state.isMaximized()) {
                        try {
                            // Максимизируем окно, если оно было максимизировано ранее
                            window.setMaximum(true);
                        } catch (PropertyVetoException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
            System.out.println("Window states restored successfully.");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error restoring window states: " + e.getMessage());
        }
    }

}


