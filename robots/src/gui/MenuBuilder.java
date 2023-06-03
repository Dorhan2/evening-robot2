package gui;
import java.awt.event.KeyEvent;
import java.util.ResourceBundle;
import javax.swing.*;

import log.Logger;

public class MenuBuilder {

    private MenuBuilder() {
        // private constructor to prevent instantiation
    }

    public static JMenuBar buildMenuBar(MainApplicationFrame frame, ResourceBundle messages) {
        JMenuBar menuBar = new JMenuBar();
        menuBar.add(buildLookAndFeelMenu(frame, messages));
        menuBar.add(buildTestMenu(messages));
        menuBar.add(buildExitMenu(frame, messages)); // Добавляем пункт меню "Выйти"
        return menuBar;
    }

    private static JMenuItem buildExitMenu(MainApplicationFrame frame, ResourceBundle messages) {
        JMenuItem exitMenuItem = new JMenuItem(messages.getString("exitItem"));
        exitMenuItem.addActionListener(e -> {
            int result = JOptionPane.showConfirmDialog(frame, messages.getString("exitConfirmationMessage"),
                    messages.getString("exitConfirmationTitle"), JOptionPane.YES_NO_OPTION);
            if (result == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
        });
        return exitMenuItem;
    }

    private static JMenu buildLookAndFeelMenu(MainApplicationFrame frame, ResourceBundle messages) {
        JMenu lookAndFeelMenu = new JMenu(messages.getString("menuTitle"));
        lookAndFeelMenu.setMnemonic(KeyEvent.VK_V);
        lookAndFeelMenu.getAccessibleContext().setAccessibleDescription(
                messages.getString("menuTitle") + " " + messages.getString("gameMenu"));

        JMenuItem systemLookAndFeel = new JMenuItem(messages.getString("startGameItem"), KeyEvent.VK_S);
        systemLookAndFeel.addActionListener((event) -> {
            setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            frame.invalidate();
        });
        lookAndFeelMenu.add(systemLookAndFeel);

        JMenuItem crossPlatformLookAndFeel = new JMenuItem(messages.getString("exitItem"), KeyEvent.VK_U);
        crossPlatformLookAndFeel.addActionListener((event) -> {
            setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
            frame.invalidate();
        });
        lookAndFeelMenu.add(crossPlatformLookAndFeel);

        return lookAndFeelMenu;
    }

    private static JMenu buildTestMenu(ResourceBundle messages) {
        JMenu testMenu = new JMenu(messages.getString("gameMenu"));
        testMenu.setMnemonic(KeyEvent.VK_T);
        testMenu.getAccessibleContext().setAccessibleDescription(
                messages.getString("gameMenu") + " " + messages.getString("gameMenu"));

        JMenuItem addLogMessageItem = new JMenuItem(messages.getString("startGameItem"), KeyEvent.VK_S);
        addLogMessageItem.addActionListener((event) -> {
            Logger.debug(messages.getString("restoreWindowStateTitle"));
        });
        testMenu.add(addLogMessageItem);

        return testMenu;
    }

    private static void setLookAndFeel(String className) {
        try {
            UIManager.setLookAndFeel(className);
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException
                | UnsupportedLookAndFeelException e) {
            // just ignore
        }
    }
}