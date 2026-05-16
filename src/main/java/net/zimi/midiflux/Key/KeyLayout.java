package net.zimi.midiflux.Key;

import java.awt.event.KeyEvent;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

public final class KeyLayout {
    public static final int[] FIRST_OCTAVE = {KeyEvent.VK_A, KeyEvent.VK_W, KeyEvent.VK_S, KeyEvent.VK_E, KeyEvent.VK_D, KeyEvent.VK_F, KeyEvent.VK_T, KeyEvent.VK_G};

    public static final int[] SECOND_OCTAVE = {KeyEvent.VK_Y, KeyEvent.VK_H, KeyEvent.VK_U, KeyEvent.VK_J, KeyEvent.VK_K};

    private static final Map<Integer, Integer> SEMITONE_BY_KEY = buildSemitoneMap();
    private static final Set<Integer> SUPPORTED_KEYS = Collections.unmodifiableSet(SEMITONE_BY_KEY.keySet());

    private static Map<Integer, Integer> buildSemitoneMap() {
        Map<Integer, Integer> map = new LinkedHashMap<>();
        int semitone = 0;

        for (int keyCode : FIRST_OCTAVE) {
            map.put(keyCode, semitone++);
        }
        for (int keyCode : SECOND_OCTAVE) {
            map.put(keyCode, semitone++);
        }

        return Collections.unmodifiableMap(map);
    }

    public static int getSemitoneOffset(int keyCode) {
        return SEMITONE_BY_KEY.getOrDefault(keyCode, -1);
    }

    public static boolean isSupported(int keyCode) {
        return SUPPORTED_KEYS.contains(keyCode);
    }
}