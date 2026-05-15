package net.zimi.midiflux.Key;

import net.zimi.midiflux.Sound.Note;
import net.zimi.midiflux.Sound.SoundEvent;

import java.awt.event.KeyEvent;

public class Key {
    private final int keyCode;
    private final int baseMidi;
    private final String name;

    public Key(int keyCode, int baseMidi) {
        this.keyCode = keyCode;
        this.baseMidi = baseMidi;
        this.name = KeyEvent.getKeyText(keyCode);
    }

    public String name() {
        return name;
    }

    public SoundEvent getSoundEvent() {
        return new SoundEvent(new Note(keyCode, baseMidi));
    }
}