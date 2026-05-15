package org.example;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;

public class PianoGui {
    private final JFrame frame;
    private final PianoKeyboardPanel pianoPanel;

    public PianoGui() {
        frame = new JFrame("MIDI QWERTY Piano");
        pianoPanel = new PianoKeyboardPanel();

        JLabel help = new JLabel("Tasti: A W S E D F T G Y H U J K  |  Freccia SU/GIÙ cambia ottava", SwingConstants.CENTER);
        help.setFont(new Font("Arial", Font.BOLD, 16));
        help.setForeground(Color.WHITE);
        help.setBorder(BorderFactory.createEmptyBorder(12, 0, 8, 0));

        JButton loadSheetButton = new JButton("Carica spartito");
        stylePianoButton(loadSheetButton);
        loadSheetButton.addActionListener(_ -> openSheetImageChooser());

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        buttonPanel.setOpaque(false);
        buttonPanel.add(loadSheetButton);

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(new Color(30, 30, 30));
        topPanel.add(help, BorderLayout.NORTH);
        topPanel.add(buttonPanel, BorderLayout.CENTER);
        topPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.add(topPanel, BorderLayout.NORTH);
        frame.add(pianoPanel, BorderLayout.CENTER);
        frame.setSize(1150, 760);
        frame.setLocationRelativeTo(null);
    }

    private void stylePianoButton(JButton button) {
        button.setPreferredSize(new Dimension(190, 52));
        button.setFont(new Font("Arial", Font.BOLD, 16));
        button.setForeground(new Color(35, 35, 35));
        button.setBackground(Color.WHITE);
        button.setFocusPainted(false);
        button.setFocusable(false);
        button.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.BLACK, 2, true), BorderFactory.createEmptyBorder(8, 16, 8, 16)));
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }

    private void openSheetImageChooser() {
        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle("Scegli immagine spartito");
        chooser.setFileFilter(new FileNameExtensionFilter("Immagini", "png", "jpg", "jpeg", "bmp", "gif"));

        int result = chooser.showOpenDialog(frame);

        if (result == JFileChooser.APPROVE_OPTION) {
            pianoPanel.loadSheetImage(chooser.getSelectedFile());
            frame.requestFocusInWindow();
        }
    }

    public JFrame getFrame() {
        return frame;
    }

    public PianoKeyboardPanel getPianoPanel() {
        return pianoPanel;
    }

    public void show() {
        frame.setVisible(true);
    }
}