package org.example;

import javax.swing.SwingUtilities;

public class Main {
    static void main() {
        SwingUtilities.invokeLater(() -> {
            PianoGui gui = new PianoGui();
            gui.show();

            int baseMidi = 60;
            new KeyboardInput(gui.getFrame(), gui.getPianoPanel(), baseMidi);
        });
    }
}