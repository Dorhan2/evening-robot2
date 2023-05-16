package gui;

import java.awt.event.KeyEvent;
import javax.swing.*;

import log.Logger;

public class MenuBuilder {

    private MenuBuilder() {
        // private constructor to prevent instantiation
    }

    public static JMenuBar buildMenuBar(MainApplicationFrame frame) {
        JMenuBar menuBar = new JMenuBar();
        menuBar.add(buildLookAndFeelMenu(frame));
        menuBar.add(buildTestMenu());
        menuBar.add(buildExitMenu(frame)); // Добавляем пункт меню "Выйти"
        return menuBar;
    }

    private static JMenuItem buildExitMenu(MainApplicationFrame frame) {
        JMenuItem exitMenuItem = new JMenuItem("Выйти");
        exitMenuItem.addActionListener(e -> {
            int result = JOptionPane.showConfirmDialog(frame, "Вы действительно хотите выйти из приложения?", "Подтверждение закрытия", JOptionPane.YES_NO_OPTION);
            if (result == JOptionPane.YES_OPTION) {

                System.exit(0);
            }
        });
        return exitMenuItem;
    }


    private static JMenu buildLookAndFeelMenu(MainApplicationFrame frame) {
        JMenu lookAndFeelMenu = new JMenu("Режим отображения");
        lookAndFeelMenu.setMnemonic(KeyEvent.VK_V);
        lookAndFeelMenu.getAccessibleContext().setAccessibleDescription(
                "Управление режимом отображения приложения");

        JMenuItem systemLookAndFeel = new JMenuItem("Системная схема", KeyEvent.VK_S);
        systemLookAndFeel.addActionListener((event) -> {
            setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            frame.invalidate();
        });
        lookAndFeelMenu.add(systemLookAndFeel);

        JMenuItem crossPlatformLookAndFeel = new JMenuItem("Универсальная схема", KeyEvent.VK_U);
        crossPlatformLookAndFeel.addActionListener((event) -> {
            setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
            frame.invalidate();
        });
        lookAndFeelMenu.add(crossPlatformLookAndFeel);

        return lookAndFeelMenu;
    }

    private static JMenu buildTestMenu() {
        JMenu testMenu = new JMenu("Тесты");
        testMenu.setMnemonic(KeyEvent.VK_T);
        testMenu.getAccessibleContext().setAccessibleDescription(
                "Тестовые команды");

        JMenuItem addLogMessageItem = new JMenuItem("Сообщение в лог", KeyEvent.VK_S);
        addLogMessageItem.addActionListener((event) -> {
            Logger.debug("Новая строка");
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
