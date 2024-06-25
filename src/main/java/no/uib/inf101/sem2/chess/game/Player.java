package no.uib.inf101.sem2.chess.game;

/**
 * An abstract class representing the players in the game.
 * 
 * @author Njal.Pedersen
 *
 */

public abstract class Player {
    public boolean whiteSide;
    public boolean humanPlayer;

    /**
     * 
     * @return true if the player is white, false if it is black
     */
    public boolean isWhiteSide() {
        return this.whiteSide;
    }

    /**
     * sets the player to be white or black
     * @param whiteSide
     */
    public void setWhiteSide(boolean whiteSide) {
        this.whiteSide = whiteSide;
    }

    /**
     * 
     * @return true if the player is human, false if it is not
     */

    public boolean isHumanPlayer() {
        return this.humanPlayer;
    }
}

