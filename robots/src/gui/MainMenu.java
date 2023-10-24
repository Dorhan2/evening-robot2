package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Locale;
import java.util.ResourceBundle;

public class MainMenu extends JFrame {

    private JMenu gameMenu;
    private JMenuItem startGameItem;
    private JMenuItem exitItem;
    private JMenu languageMenu;
    private JMenuItem languageItem;
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

        languageMenu = createLanguageMenu();
        menuBar.add(languageMenu);

        JMenu levelMenu = createLevelMenu();
        menuBar.add(levelMenu);

        setJMenuBar(menuBar);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation(screenSize.width / 2 - getWidth() / 2, screenSize.height / 2 - getHeight() / 2);


        setLanguage("English");
        setLanguage(LanguageSettings.getLanguage());



    }

    private JMenu createLevelMenu() {
        JMenu levelMenu = new JMenu(getLocalizedString("Plan"));

        JMenuItem one = new JMenuItem(getLocalizedString("1"));
        JMenuItem two = new JMenuItem(getLocalizedString("2"));

        one.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Files.writeString(Paths.get("C:\\.2021 КН\\jaba\\qwer\\robots\\src\\gui\\level.txt"), "1", StandardOpenOption.TRUNCATE_EXISTING);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });

        two.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Files.writeString(Paths.get("C:\\.2021 КН\\jaba\\qwer\\robots\\src\\gui\\level.txt"), "2", StandardOpenOption.TRUNCATE_EXISTING);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });

        levelMenu.add(one);
        levelMenu.add(two);

        return levelMenu;
    }

    private JMenu createLanguageMenu() {
        JMenu languageMenu = new JMenu(getLocalizedString("languageMenu"));

        languageItem = new JMenuItem(getLocalizedString("languageItem"));
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
            LanguageSettings.setLanguage(selectedLanguage);
        }
    }

    private void setLanguage(String language) {
        Locale locale;
        if (language.equals("English")) {
            locale = new Locale("en", "US");
        } else if (language.equals("Deutsch")) {
            locale = new Locale("ru", "RU");
        } else {
            // Default language if the selected language is not recognized
            locale = new Locale("en", "US");
        }

        resourceBundle = ResourceBundle.getBundle("gui.lang.lang", locale);

        // Update the text elements based on the selected language
        setTitle(getLocalizedString("menuTitle"));
        gameMenu.setText(getLocalizedString("gameMenu"));
        startGameItem.setText(getLocalizedString("startGameItem"));
        exitItem.setText(getLocalizedString("exitItem"));
        languageMenu.setText(getLocalizedString("languageMenu"));
        languageItem.setText(getLocalizedString("languageItem"));
        // Additional updates to text elements
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

    public String getLocalizedString(String key) {
        try {
            return resourceBundle.getString(key);
        } catch (Exception e) {
            return key; // Возвращаем ключ, если строка не найдена
        }
    }
}