package net.zimi.midiflux;

import net.zimi.midiflux.Gui.PianoGui;
import net.zimi.midiflux.Key.KeyboardInput;

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