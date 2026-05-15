package net.zimi.midiflux.Key;

import net.zimi.midiflux.Gui.PianoKeyboardPanel;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.util.HashSet;
import java.util.Set;

public class KeyboardInput {
    private final JFrame inputFrame;
    private final PianoKeyboardPanel pianoPanel;
    private int baseMidi;
    private final Set<Integer> pressedKeys = new HashSet<>();

    public KeyboardInput(JFrame inputFrame, PianoKeyboardPanel pianoPanel, int baseMidi) {
        this.inputFrame = inputFrame;
        this.pianoPanel = pianoPanel;
        this.baseMidi = baseMidi;
        this.pianoPanel.setBaseMidi(baseMidi);
        this.init();
    }

    private void init() {
        inputFrame.addKeyListener(new KeyboardInputEvent() {
            @Override
            public void keyPressed(KeyEvent e) {
                int code = e.getKeyCode();

                switch (code) {
                    case KeyEvent.VK_UP:
                        baseMidi = Math.min(baseMidi + 12, 108);
                        pianoPanel.setBaseMidi(baseMidi);
                        IO.println("Offset MIDI Base (Positive) to: " + baseMidi);
                        break;
                    case KeyEvent.VK_DOWN:
                        baseMidi = Math.max(baseMidi - 12, 0);
                        pianoPanel.setBaseMidi(baseMidi);
                        IO.println("Offset MIDI Base (Negative) to: " + baseMidi);
                        break;
                }

                if (!KeyLayout.isSupported(code)) {
                    return;
                }
                if (!pressedKeys.add(code)) {
                    return;
                }

                Key key = new Key(code, baseMidi);
                key.getSoundEvent().playNote();

                SwingUtilities.invokeLater(() -> pianoPanel.setPressed(code, true));
            }

            @Override
            public void keyReleased(KeyEvent e) {
                int code = e.getKeyCode();

                if (!KeyLayout.isSupported(code)) {
                    return;
                }
                pressedKeys.remove(code);

                Key key = new Key(code, baseMidi);
                key.getSoundEvent().stopNote();

                SwingUtilities.invokeLater(() -> pianoPanel.setPressed(code, false));
            }
        });

        SwingUtilities.invokeLater(() -> {
            inputFrame.setFocusable(true);
            inputFrame.requestFocusInWindow();
        });
    }
}