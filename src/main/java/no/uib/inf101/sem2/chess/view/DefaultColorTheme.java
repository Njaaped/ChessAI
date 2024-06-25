package no.uib.inf101.sem2.chess.view;

import java.awt.Color;

/**
* Interface for the color themes in the game.
* @author Njal.Pedersen
*/

public interface DefaultColorTheme {

    /**
    * method that holds the colors and finds the right colortheme
    * @return color of the squares on the board
    */

    public Color[] theColors();

    /**
     * method that sets the colortheme number to find the right colortheme
     * @param colorTheme
     */

    public void setColorTheme(int colorTheme);

    /**
     * method that returns the colortheme number
     * @return colorTheme
     */

    public int getColorTheme();

    
}
