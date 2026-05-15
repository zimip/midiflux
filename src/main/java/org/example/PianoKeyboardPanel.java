package org.example;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseWheelEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class PianoKeyboardPanel extends JPanel {
    private final int[] keyCodes;
    private final String[] labels;
    private final boolean[] pressed;
    private final Map<Integer, Integer> keyCodeToIndex = new HashMap<>();
    private BufferedImage sheetImage;
    private int baseMidi = 60;

    private int sheetScrollX = 0;
    private int sheetScrollY = 0;

    public PianoKeyboardPanel() {
        this.keyCodes = new int[]{KeyLayout.FIRST_OCTAVE[0], KeyLayout.FIRST_OCTAVE[1], KeyLayout.FIRST_OCTAVE[2], KeyLayout.FIRST_OCTAVE[3], KeyLayout.FIRST_OCTAVE[4], KeyLayout.FIRST_OCTAVE[5], KeyLayout.FIRST_OCTAVE[6], KeyLayout.FIRST_OCTAVE[7], KeyLayout.SECOND_OCTAVE[0], KeyLayout.SECOND_OCTAVE[1], KeyLayout.SECOND_OCTAVE[2], KeyLayout.SECOND_OCTAVE[3], KeyLayout.SECOND_OCTAVE[4]};

        this.labels = new String[]{"A", "W", "S", "E", "D", "F", "T", "G", "Y", "H", "U", "J", "K"};
        this.pressed = new boolean[keyCodes.length];

        for (int i = 0; i < keyCodes.length; i++) {
            keyCodeToIndex.put(keyCodes[i], i);
        }

        addMouseWheelListener(this::scrollSheetImage);

        setPreferredSize(new Dimension(1100, 620));
        setMinimumSize(new Dimension(500, 320));
        setBackground(new Color(30, 30, 30));
    }

    public void setPressed(int keyCode, boolean isPressed) {
        Integer idx = keyCodeToIndex.get(keyCode);
        if (idx != null) {
            pressed[idx] = isPressed;
            repaint();
        }
    }

    public void setBaseMidi(int baseMidi) {
        this.baseMidi = baseMidi;
        repaint();
    }

    public void loadSheetImage(File file) {
        try {
            sheetImage = ImageIO.read(file);

            if (sheetImage == null) {
                JOptionPane.showMessageDialog(this, "Il file selezionato non è un'immagine valida.", "Errore caricamento immagine", JOptionPane.ERROR_MESSAGE);
                return;
            }

            sheetScrollX = 0;
            sheetScrollY = 0;
            repaint();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Impossibile caricare l'immagine selezionata.", "Errore caricamento immagine", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void scrollSheetImage(MouseWheelEvent e) {
        if (sheetImage == null) {
            return;
        }

        int amount = e.getWheelRotation() * 35;

        if (e.isShiftDown()) {
            sheetScrollX += amount;
        } else {
            sheetScrollY += amount;
        }

        repaint();
    }

    private boolean isBlackSemitone(int semitone) {
        int x = semitone % 12;
        return x == 1 || x == 3 || x == 6 || x == 8 || x == 10;
    }

    private int getCurrentOctave() {
        return (baseMidi / 12) - 1;
    }

    private int countWhiteKeys() {
        int whiteKeys = 0;

        for (int i = 0; i < keyCodes.length; i++) {
            if (!isBlackSemitone(i)) {
                whiteKeys++;
            }
        }

        return whiteKeys;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);

        int baseWhiteW = 82;
        int baseWhiteH = 280;
        int baseBlackW = 48;
        int baseBlackH = 170;

        int baseSheetHeight = 230;
        int baseSheetToPianoGap = 35;
        int baseOctaveGap = 20;
        int baseOctaveW = 120;
        int baseOctaveH = 90;

        int whiteKeys = countWhiteKeys();

        int baseCenteredBlockWidth = whiteKeys * baseWhiteW;
        int baseCenteredBlockHeight = baseSheetHeight + baseSheetToPianoGap + baseWhiteH;

        int horizontalPadding = 40;
        int verticalPadding = 30;

        double scaleX = (double) (getWidth() - horizontalPadding * 2) / baseCenteredBlockWidth;
        double scaleY = (double) (getHeight() - verticalPadding * 2) / baseCenteredBlockHeight;
        double scale = Math.min(scaleX, scaleY);

        scale = Math.clamp(scale, 0.35, 1.45);

        int whiteW = scaled(baseWhiteW, scale);
        int whiteH = scaled(baseWhiteH, scale);
        int blackW = scaled(baseBlackW, scale);
        int blackH = scaled(baseBlackH, scale);

        int sheetHeight = scaled(baseSheetHeight, scale);
        int sheetToPianoGap = scaled(baseSheetToPianoGap, scale);
        int octaveGap = scaled(baseOctaveGap, scale);
        int octaveW = scaled(baseOctaveW, scale);
        int octaveH = scaled(baseOctaveH, scale);

        int pianoWidth = whiteKeys * whiteW;
        int centeredBlockHeight = sheetHeight + sheetToPianoGap + whiteH;

        int pianoX = (getWidth() - pianoWidth) / 2;

        int sheetY = (getHeight() - centeredBlockHeight) / 2;
        int pianoY = sheetY + sheetHeight + sheetToPianoGap;

        int octaveX = pianoX + pianoWidth + octaveGap;
        int octaveY = pianoY + (whiteH - octaveH) / 2;

        if (octaveX + octaveW > getWidth() - scaled(20, scale)) {
            octaveX = getWidth() - octaveW - scaled(20, scale);
        }

        drawSheetImage(g2, pianoX, sheetY, pianoWidth, sheetHeight, scale);
        drawWhiteKeys(g2, pianoX, pianoY, whiteW, whiteH, scale);
        drawBlackKeys(g2, pianoX, pianoY, whiteW, blackW, blackH, scale);
        drawOctaveInfo(g2, octaveX, octaveY, octaveW, octaveH, scale);

        g2.dispose();
    }

    private int scaled(int value, double scale) {
        return Math.max(1, (int) Math.round(value * scale));
    }

    private void drawWhiteKeys(Graphics2D g2, int pianoX, int pianoY, int whiteW, int whiteH, double scale) {
        int whiteCount = 0;

        for (int i = 0; i < keyCodes.length; i++) {
            if (!isBlackSemitone(i)) {
                int x = pianoX + whiteCount * whiteW;

                g2.setColor(pressed[i] ? new Color(170, 220, 255) : Color.WHITE);
                g2.fillRoundRect(x, pianoY, whiteW - scaled(2, scale), whiteH, scaled(10, scale), scaled(10, scale));

                g2.setColor(Color.BLACK);
                g2.drawRoundRect(x, pianoY, whiteW - scaled(2, scale), whiteH, scaled(10, scale), scaled(10, scale));

                g2.setColor(new Color(40, 40, 40));
                g2.setFont(new Font("Arial", Font.BOLD, scaled(18, scale)));

                FontMetrics metrics = g2.getFontMetrics();
                int textX = x + (whiteW - metrics.stringWidth(labels[i])) / 2;
                int textY = pianoY + whiteH - scaled(18, scale);

                g2.drawString(labels[i], textX, textY);

                whiteCount++;
            }
        }
    }

    private void drawBlackKeys(Graphics2D g2, int pianoX, int pianoY, int whiteW, int blackW, int blackH, double scale) {
        int whiteCount = 0;

        for (int i = 0; i < keyCodes.length; i++) {
            if (!isBlackSemitone(i)) {
                whiteCount++;
                continue;
            }

            int x = pianoX + whiteCount * whiteW - (blackW / 2);

            g2.setColor(pressed[i] ? new Color(255, 140, 140) : new Color(25, 25, 25));
            g2.fillRoundRect(x, pianoY, blackW, blackH, scaled(8, scale), scaled(8, scale));

            g2.setColor(Color.DARK_GRAY);
            g2.drawRoundRect(x, pianoY, blackW, blackH, scaled(8, scale), scaled(8, scale));

            g2.setColor(Color.WHITE);
            g2.setFont(new Font("Arial", Font.BOLD, scaled(16, scale)));

            FontMetrics metrics = g2.getFontMetrics();
            int textX = x + (blackW - metrics.stringWidth(labels[i])) / 2;
            int textY = pianoY + blackH - scaled(16, scale);

            g2.drawString(labels[i], textX, textY);
        }
    }

    private void drawOctaveInfo(Graphics2D g2, int x, int y, int boxW, int boxH, double scale) {
        g2.setColor(new Color(245, 245, 245));
        g2.fillRoundRect(x, y, boxW, boxH, scaled(12, scale), scaled(12, scale));

        g2.setColor(Color.BLACK);
        g2.drawRoundRect(x, y, boxW, boxH, scaled(12, scale), scaled(12, scale));

        g2.setColor(new Color(35, 35, 35));

        g2.setFont(new Font("Arial", Font.BOLD, scaled(16, scale)));
        String title = "Ottava";
        FontMetrics titleMetrics = g2.getFontMetrics();
        int titleX = x + (boxW - titleMetrics.stringWidth(title)) / 2;
        int titleY = y + scaled(30, scale);
        g2.drawString(title, titleX, titleY);

        g2.setFont(new Font("Arial", Font.BOLD, scaled(34, scale)));
        String octave = String.valueOf(getCurrentOctave());
        FontMetrics octaveMetrics = g2.getFontMetrics();
        int octaveX = x + (boxW - octaveMetrics.stringWidth(octave)) / 2;
        int octaveY = y + scaled(70, scale);
        g2.drawString(octave, octaveX, octaveY);
    }

    private void drawSheetImage(Graphics2D g2, int x, int y, int width, int height, double scale) {
        if (sheetImage == null) {
            g2.setColor(new Color(48, 48, 48));
            g2.fillRoundRect(x, y, width, height, scaled(14, scale), scaled(14, scale));

            g2.setColor(new Color(90, 90, 90));
            g2.drawRoundRect(x, y, width, height, scaled(14, scale), scaled(14, scale));

            g2.setColor(Color.LIGHT_GRAY);
            g2.setFont(new Font("Arial", Font.PLAIN, Math.max(12, height / 12)));

            String message = "Nessuno spartito caricato";
            FontMetrics metrics = g2.getFontMetrics();
            int textX = x + (width - metrics.stringWidth(message)) / 2;
            int textY = y + (height / 2);

            g2.drawString(message, textX, textY);
            return;
        }

        g2.setColor(Color.WHITE);
        g2.fillRoundRect(x - scaled(6, scale), y - scaled(6, scale), width + scaled(12, scale), height + scaled(12, scale), scaled(12, scale), scaled(12, scale));

        Shape oldClip = g2.getClip();
        g2.setClip(x, y, width, height);

        double sheetZoom = 1.85;
        int imageWidth = (int) Math.round(width * sheetZoom);
        int imageHeight = (int) Math.round((double) sheetImage.getHeight() / sheetImage.getWidth() * imageWidth);

        if (imageHeight < height) {
            imageHeight = height;
        }

        sheetScrollX = clamp(sheetScrollX, 0, Math.max(0, imageWidth - width));
        sheetScrollY = clamp(sheetScrollY, 0, Math.max(0, imageHeight - height));

        int imageX = x - sheetScrollX;
        int imageY = y - sheetScrollY;

        g2.drawImage(sheetImage, imageX, imageY, imageWidth, imageHeight, null);

        g2.setClip(oldClip);

        drawSheetScrollHint(g2, x, y, width, height, scale);
    }

    private void drawSheetScrollHint(Graphics2D g2, int x, int y, int width, int height, double scale) {
        String hint = "Rotellina: scroll verticale | Shift + rotellina: orizzontale";

        g2.setFont(new Font("Arial", Font.PLAIN, scaled(12, scale)));
        FontMetrics metrics = g2.getFontMetrics();

        int paddingX = scaled(10, scale);
        int paddingY = scaled(6, scale);

        int boxW = metrics.stringWidth(hint) + paddingX * 2;
        int boxH = metrics.getHeight() + paddingY;

        int boxX = x + width - boxW - scaled(8, scale);
        int boxY = y + height - boxH - scaled(8, scale);

        g2.setColor(new Color(0, 0, 0, 150));
        g2.fillRoundRect(boxX, boxY, boxW, boxH, scaled(8, scale), scaled(8, scale));

        g2.setColor(Color.WHITE);
        g2.drawString(hint, boxX + paddingX, boxY + metrics.getAscent() + paddingY / 2);
    }

    private int clamp(int value, int min, int max) {
        return Math.clamp(value, min, max);
    }
}