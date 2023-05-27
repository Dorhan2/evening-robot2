package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainMenu extends JFrame {

    public MainMenu() {
        setTitle("Меню");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 200);

        JMenuBar menuBar = new JMenuBar();
        JMenu gameMenu = new JMenu("Игра");

        JMenuItem startGameItem = new JMenuItem("Начать игру");
        startGameItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Обработка нажатия на пункт "Начать игру"
                startGame();
            }
        });
        gameMenu.add(startGameItem);

        JMenuItem exitItem = new JMenuItem("Выход");
        exitItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Обработка нажатия на пункт "Выход"
                exitGame();
            }
        });
        gameMenu.add(exitItem);

        menuBar.add(gameMenu);
        setJMenuBar(menuBar);

        // Центрирование окна на экране
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation(screenSize.width / 2 - getWidth() / 2, screenSize.height / 2 - getHeight() / 2);
    }

    private void startGame() {
        //для начала игры
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                MainApplicationFrame frame = new MainApplicationFrame();
                frame.pack();
                frame.setVisible(true);
                frame.setExtendedState(Frame.MAXIMIZED_BOTH);
            }
        });
    }

    private void exitGame() {
        //для выхода
        System.exit(0);
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Создание и отображение главного меню
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                MainMenu menuWindow = new MainMenu();
                menuWindow.setVisible(true);
            }
        });
    }
}
