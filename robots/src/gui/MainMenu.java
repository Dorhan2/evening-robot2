package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Locale;
import java.util.ResourceBundle;

public class MainMenu extends JFrame {

    private JMenu gameMenu;
    private JMenuItem startGameItem;
    private JMenuItem exitItem;
    private JComboBox<String> languageComboBox;

    private ResourceBundle resourceBundle;

    public MainMenu() {
        setTitle("Меню");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 200);

        JMenuBar menuBar = new JMenuBar();
        gameMenu = new JMenu(getLocalizedString("gameMenu"));

        startGameItem = new JMenuItem(getLocalizedString("startGameItem"));
        startGameItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startGame();
            }
        });
        gameMenu.add(startGameItem);

        exitItem = new JMenuItem(getLocalizedString("exitItem"));
        exitItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                exitGame();
            }
        });
        gameMenu.add(exitItem);

        menuBar.add(gameMenu);

        JMenu languageMenu = createLanguageMenu();
        menuBar.add(languageMenu);

        setJMenuBar(menuBar);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation(screenSize.width / 2 - getWidth() / 2, screenSize.height / 2 - getHeight() / 2);

        setLanguage("en");
    }

    private JMenu createLanguageMenu() {
        JMenu languageMenu = new JMenu(getLocalizedString("languageMenu"));

        JMenuItem languageItem = new JMenuItem(getLocalizedString("languageItem"));
        languageItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showLanguageDialog();
            }
        });
        languageMenu.add(languageItem);

        return languageMenu;
    }

    private void showLanguageDialog() {
        String[] languages = { "English", "Deutsch" };
        String selectedLanguage = (String) JOptionPane.showInputDialog(this, "Select Language", "Language Selection", JOptionPane.PLAIN_MESSAGE, null, languages, languages[0]);
        if (selectedLanguage != null) {
            setLanguage(selectedLanguage);
        }
    }

    private void setLanguage(String language) {
        Locale locale = Locale.getDefault();

        if (language.equals("English")) {
            locale = new Locale("en", "US");
        } else if (language.equals("Deutsch")) {
            locale = new Locale("ru", "RU");
        }

        resourceBundle = ResourceBundle.getBundle("gui.lang.lang", locale);

        // Обновление текстовых элементов на основе выбранного языка
        setTitle(getLocalizedString("menuTitle"));
        gameMenu.setText(getLocalizedString("gameMenu"));
        startGameItem.setText(getLocalizedString("startGameItem"));
        exitItem.setText(getLocalizedString("exitItem"));
        // Дополнительные обновления текстовых элементов
    }

    private void startGame() {
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
        System.exit(0);
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception e) {
            e.printStackTrace();
        }
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                MainMenu menuWindow = new MainMenu();
                menuWindow.setVisible(true);
            }
        });
    }

    private String getLocalizedString(String key) {
        try {
            return resourceBundle.getString(key);
        } catch (Exception e) {
            return key; // Возвращаем ключ, если строка не найдена
        }
    }
}