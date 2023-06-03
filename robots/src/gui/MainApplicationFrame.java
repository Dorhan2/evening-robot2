package gui;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.PropertyVetoException;

import javax.swing.*;

import log.Logger;

public class MainApplicationFrame extends JFrame {

    public JInternalFrame[] getInternalFrames() {
        return desktopPane.getAllFrames();
    }

    private final JDesktopPane desktopPane = new JDesktopPane();
    private LogWindow logWindow;
    private GameWindow gameWindow;
    //для закрытия
    private int childFramesCount = 0;

    public LogWindow getLogWindow() {
        return logWindow;
    }

    public GameWindow getGameWindow() {
        return gameWindow;
    }

    public JInternalFrame getWindowById(String windowId) {
        for (JInternalFrame window : desktopPane.getAllFrames()) {
            if (window.getName().equals(windowId)) {
                return window;
            }
        }
        return null;
    }

    public MainApplicationFrame() {
        int inset = 50;
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setBounds(inset, inset, screenSize.width - inset * 2, screenSize.height - inset * 2);

        setContentPane(desktopPane);

        logWindow = createLogWindow();
        logWindow.setName("logWindow");
        addWindow(logWindow);

        gameWindow = new GameWindow();
        gameWindow.setName("gameWindow");
        gameWindow.setSize(400, 400);
        addWindow(gameWindow);

        setJMenuBar(MenuBuilder.buildMenuBar(this));
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                handleWindowClosing();
            }
        });

        // Проверяем наличие сохраненных состояний окон
        if (WindowStateManager.hasSavedWindowState("logWindow")) {
            int result = JOptionPane.showConfirmDialog(this, "Найдено сохраненное состояние окна логов. Хотите восстановить?", "Восстановление окна логов", JOptionPane.YES_NO_OPTION);
            if (result == JOptionPane.YES_OPTION) {
                restoreWindowState("logWindow", logWindow);
            }
        }

        if (WindowStateManager.hasSavedWindowState("gameWindow")) {
            int result = JOptionPane.showConfirmDialog(this, "Найдено сохраненное состояние игрового окна. Хотите восстановить?", "Восстановление игрового окна", JOptionPane.YES_NO_OPTION);
            if (result == JOptionPane.YES_OPTION) {
                restoreWindowState("gameWindow", gameWindow);
            }
        }

    }

    //для закрытия глав окна
    private void handleWindowClosing() {
        int result = JOptionPane.showConfirmDialog(this, "Вы действительно хотите выйти из приложения?", "Подтверждение закрытия", JOptionPane.YES_NO_OPTION);
        if (result == JOptionPane.YES_OPTION) {
            // Сохраняем состояния окон перед выходом
            WindowStateManager.saveWindowState("logWindow", logWindow.getLocation(), logWindow.getSize(), logWindow.isMaximum(), logWindow.isIcon());
            WindowStateManager.saveWindowState("gameWindow", gameWindow.getLocation(), gameWindow.getSize(), gameWindow.isMaximum(), gameWindow.isIcon());
            System.exit(0);
        }
    }


    //для закрытия
    public void handleChildWindowClosing() {
        int result = JOptionPane.showConfirmDialog(this, "Вы действительно хотите закрыть окно?", "Подтверждение закрытия", JOptionPane.YES_NO_OPTION);
        if (result == JOptionPane.YES_OPTION) {
            JInternalFrame[] childFrames = desktopPane.getAllFrames();
            for (JInternalFrame frame : childFrames) {
                if (frame.isClosable()) {
                    frame.dispose();
                }
            }
            if (--childFramesCount == 0) {
                setVisible(false);
            }
        }
    }



    private void restoreWindowState(String windowId, JInternalFrame window) {
        WindowStateManager.restoreWindowState(windowId, this);
    }
    protected LogWindow createLogWindow() {
        LogWindow logWindow = new LogWindow(Logger.getDefaultLogSource());
        logWindow.setLocation(10, 10);
        logWindow.setSize(300, 800);
        setMinimumSize(logWindow.getSize());
        logWindow.pack();
        Logger.debug("Протокол работает");
        return logWindow;
    }

    protected void addWindow(JInternalFrame frame) {
        desktopPane.add(frame);
        frame.setVisible(true);
        childFramesCount++;
    }

    private void setLookAndFeel(String className) {
        try {
            UIManager.setLookAndFeel(className);
            SwingUtilities.updateComponentTreeUI(this);
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException
                | UnsupportedLookAndFeelException e) {
            // just ignore
        }
    }
}