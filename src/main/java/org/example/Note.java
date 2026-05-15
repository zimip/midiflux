package org.example;

public class Note {

    private final int keyCode;
    private final int baseMidi;

    public Note(int keyCode, int baseMidi) {
        this.keyCode = keyCode;
        this.baseMidi = baseMidi;
    }

    public byte getValueMapped() {
        int semitoneOffset = KeyLayout.getSemitoneOffset(keyCode);
        if (semitoneOffset < 0) {
            return -1;
        }

        int value = baseMidi + semitoneOffset;

        return (byte) value;
    }
}