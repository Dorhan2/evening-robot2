package gui;

import java.awt.BorderLayout;
import java.awt.event.WindowEvent;

import javax.swing.*;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;

import static gui.ConfirmationDialog.showConfirmationDialog;

public class GameWindow extends JInternalFrame
{
//    private final Sokobana m_visualizer;
    private final Board m_visualizer;
    public GameWindow() 
    {
        super("Spielfeld", true, true, true, true);
//        m_visualizer = new Sokobana();
        m_visualizer = new Board();
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(m_visualizer, BorderLayout.CENTER);
        getContentPane().add(panel);
        pack();

        //для закрытия окна
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addInternalFrameListener(new InternalFrameAdapter() {
            @Override
            public void internalFrameClosing(InternalFrameEvent e) {
                handleWindowClosing();
            }
        });
    }

    private void handleWindowClosing() {
        MainApplicationFrame mainFrame = (MainApplicationFrame) SwingUtilities.getWindowAncestor(this);
        mainFrame.handleChildWindowClosing();
    }

}
