package no.uib.inf101.sem2.chess.view;


import java.awt.Color;

public class ColorTheme implements DefaultColorTheme {

    private int colorTheme = 0;

    @Override
    public int getColorTheme() {
        return colorTheme;
    }

    @Override
    public void setColorTheme(int colorTheme) {
        this.colorTheme = colorTheme;
    }

    @Override
    public Color[] theColors() {
        Color[] colors = new Color[4];
        if (colorTheme == 0) {
            colors[0] = new Color(0x009933);
            colors[1] = new Color(0xe6e6e6);
            colors[2] = new Color(0x00ffff);
            colors[3] = new Color((new Color(0x00ffff)).getRed(), (new Color(0x00ffff)).getGreen(), (new Color(0x00ffff)).getBlue(), 80);
        } else if (colorTheme == 1) {
            colors[0] = new Color(0x0066ff);
            colors[1] = new Color(0xe6e6e6);
            colors[2] = new Color(0x33cc33);
            colors[3] = new Color((new Color(0x33cc33)).getRed(), (new Color(0x33cc33)).getGreen(), (new Color(0x33cc33)).getBlue(), 80);
        } else if (colorTheme == 2) {
            colors[0] = new Color(0x703201);
            colors[1] = new Color(0xcfe9db);
            colors[2] = new Color(0xa89ee0);
            colors[3] = new Color((new Color(0xa89ee0)).getRed(), (new Color(0xa89ee0)).getGreen(), (new Color(0xa89ee0)).getBlue(), 80);
        } else if (colorTheme == 3) {
            colors[0] = new Color(0x120e0e);
            colors[1] = new Color(0xe6e6e6);
            colors[2] = new Color(0x8e9791);
            colors[3] = new Color((new Color(0x8e9791)).getRed(), (new Color(0x8e9791)).getGreen(), (new Color(0x8e9791)).getBlue(), 80);
        } else if (colorTheme == 4) {
            colors[0] = new Color(0xe990c5);
            colors[1] = new Color(0xd8e9f7);
            colors[2] = new Color(0xe1c7c4);
            colors[3] = new Color((new Color(0xe1c7c4)).getRed(), (new Color(0xe1c7c4)).getGreen(), (new Color(0xe1c7c4)).getBlue(), 80);
        }
        return colors;

    }



}
