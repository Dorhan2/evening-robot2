package gui;

import javax.swing.JDialog;
import javax.swing.JOptionPane;

public class ConfirmationDialog {
    public static boolean showConfirmationDialog() {
        int result = JOptionPane.showConfirmDialog(null, "Вы действительно хотите закрыть окно?", "Подтверждение закрытия", JOptionPane.YES_NO_OPTION);
        return result == JOptionPane.YES_OPTION;
    }
}
