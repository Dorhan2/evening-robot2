package gui;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.*;

import log.Logger;

public class MainApplicationFrame extends JFrame {

    public JInternalFrame[] getInternalFrames() {
        return desktopPane.getAllFrames();
    }

    private final JDesktopPane desktopPane = new JDesktopPane();
    //для закрытия
    private int childFramesCount = 0;

    public MainApplicationFrame() {
        int inset = 50;
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setBounds(inset, inset, screenSize.width - inset * 2, screenSize.height - inset * 2);

        setContentPane(desktopPane);

//        LogWindow logWindow = createLogWindow();
//        addWindow(logWindow);

        GameWindow gameWindow = new GameWindow();
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
        if (WindowStateManager.hasSavedWindowStates()) {
            int result = JOptionPane.showConfirmDialog(this, "Найдено сохраненное состояние окон. Хотите восстановить?", "Восстановление окон", JOptionPane.YES_NO_OPTION);
            if (result == JOptionPane.YES_OPTION) {
                WindowStateManager.restoreWindowStates(this);
            }
        }

    }

    //для закрытия глав окна
    private void handleWindowClosing() {
        int result = JOptionPane.showConfirmDialog(this, "Вы действительно хотите выйти из приложения?", "Подтверждение закрытия", JOptionPane.YES_NO_OPTION);
        if (result == JOptionPane.YES_OPTION) {
            // Сохраняем состояния окон перед выходом
            WindowStateManager.saveWindowStates(this);
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
