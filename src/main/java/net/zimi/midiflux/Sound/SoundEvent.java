package net.zimi.midiflux.Sound;

import javax.sound.midi.MidiChannel;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.Synthesizer;

public class SoundEvent {
    private final Note note;

    private static MidiChannel channel;

    static {
        try {
            Synthesizer synthesizer = MidiSystem.getSynthesizer();

            synthesizer.open();
            channel = synthesizer.getChannels()[0];
        } catch (Exception _) {
        }
    }

    public SoundEvent(Note note) {
        this.note = note;
    }

    public void playNote() {
        byte midiValue = note.getValueMapped();
        if (midiValue != -1 && channel != null) {
            channel.noteOn(midiValue, 50);
        }
    }

    public void stopNote() {
        byte midiValue = note.getValueMapped();
        if (midiValue != -1 && channel != null) {
            channel.noteOff(midiValue);
        }
    }
}