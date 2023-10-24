package gui;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class LanguageSettings {
    private static final String LANGUAGE_FILE_PATH = "C:\\Users\\USER\\IdeaProjects\\evening-robot2\\robots\\src\\gui\\language.txt";

    public static void setLanguage(String language) {
        try {
            Files.writeString(Paths.get(LANGUAGE_FILE_PATH), language, StandardOpenOption.CREATE, StandardOpenOption.WRITE, StandardOpenOption.TRUNCATE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getLanguage() {
        try {
            Path path = Paths.get(LANGUAGE_FILE_PATH);
            if (Files.exists(path)) {
                return Files.readString(path);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}