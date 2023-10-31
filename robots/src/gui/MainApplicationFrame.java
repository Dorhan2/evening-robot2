package gui;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.PropertyVetoException;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.*;

import log.Logger;

public class MainApplicationFrame extends JFrame {


    private final JDesktopPane desktopPane = new JDesktopPane();
    private LogWindow logWindow;
    private GameWindow gameWindow;
    private int childFramesCount = 0;
    private ResourceBundle messages;
    private String selectedLanguage;
//    private String cancelButton;
//    private String yesButton;
//    private String[] buttons;

    public JInternalFrame[] getInternalFrames() {
        return desktopPane.getAllFrames();
    }

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

        // Локализация
        selectedLanguage = LanguageSettings.getLanguage();
        setLanguage();

        logWindow = createLogWindow();
        logWindow.setName("logWindow");
        addWindow(logWindow);

        gameWindow = new GameWindow();
        gameWindow.setName("gameWindow");
        gameWindow.setSize(400, 400);
        addWindow(gameWindow);

        setJMenuBar(MenuBuilder.buildMenuBar(this, messages));

        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                handleWindowClosing();
            }
        });

        if (WindowStateManager.hasSavedWindowState("logWindow")) {
            int result = JOptionPane.showConfirmDialog(this, messages.getString("restoreWindowStateTitle"), messages.getString("restoreLogWindowStateMessage"), JOptionPane.YES_NO_OPTION);
            if (result == JOptionPane.YES_OPTION) {
                restoreWindowState("logWindow", logWindow);
            }
        }

        if (WindowStateManager.hasSavedWindowState("gameWindow")) {
            int result = JOptionPane.showConfirmDialog(this, messages.getString("restoreWindowStateTitle"), messages.getString("restoreGameWindowStateMessage"), JOptionPane.YES_NO_OPTION);
            if (result == JOptionPane.YES_OPTION) {
                restoreWindowState("gameWindow", gameWindow);
            }
        }
    }

    private void handleWindowClosing() {
        String yesButton = messages.getString("Yes");
        String cancelButton = messages.getString("Cancel");
        String[] buttons = new String[]{yesButton, cancelButton};
        int result = JOptionPane.showOptionDialog(this, messages.getString("exitConfirmationMessage"), messages.getString("exitConfirmationTitle"), JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, buttons, buttons[0]);
        if (result == JOptionPane.YES_OPTION) {
            WindowStateManager.saveWindowState("logWindow", logWindow.getLocation(), logWindow.getSize(), logWindow.isMaximum(), logWindow.isIcon());
            WindowStateManager.saveWindowState("gameWindow", gameWindow.getLocation(), gameWindow.getSize(), gameWindow.isMaximum(), gameWindow.isIcon());
            System.exit(0);
        }
    }

    public void handleChildWindowClosing() {
        String yesButton = messages.getString("Yes");
        String cancelButton = messages.getString("Cancel");
        String[] buttons = new String[]{yesButton, cancelButton};
        int result = JOptionPane.showOptionDialog(this, messages.getString("closeChildWindowConfirmationMessage"), messages.getString("closeChildWindowConfirmationTitle"), JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, buttons, buttons[0]);
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
        Logger.debug(messages.getString("logWindowMessage"));
        return logWindow;
    }

    protected void addWindow(JInternalFrame frame) {
        desktopPane.add(frame);
        frame.setVisible(true);
        childFramesCount++;
    }

    private void setLanguage() {
        Locale locale = Locale.getDefault();

        if (selectedLanguage != null) {
            if (selectedLanguage.equals("English")) {
                locale = new Locale("en", "US");
            } else if (selectedLanguage.equals("Deutsch")) {
                locale = new Locale("de", "DE");
            }
        }

        messages = ResourceBundle.getBundle("gui.lang.lang", locale);
    }
}