package no.uib.inf101.sem2.chess.ruter;

import no.uib.inf101.sem2.chess.model.Brikke;

/**
 * A class representing a square on the chess board and it's piece in that position.
 * 
 * @author Njal.Pedersen
 *
 */

public class Rute {
    private int x;
    private int y;
    private Brikke brikke;

    public Rute(int x, int y, Brikke brikke) {
        this.x = x;
        this.y = y;
        this.brikke = brikke;
    }

    /**
     * @return the x coordinate
     */

    public int getX() {
        return x;
    }

    /**
     * @return the y coordinate
     */

    public int getY() {
        return y;
    }


    /**
     * @return the Brikke
     */

    public Brikke getBrikke() {
        return brikke;
    }

    /**
     * sets the Brikke / Piece
     * @param brikke 
     */

    public void setBrikke(Brikke brikke) {
        this.brikke = brikke;
    }

    /**
     * sets the x coordinate
     * @param x
     */

    public void setX(int x) {
        this.x = x;
    }

    /**
     * sets the y coordinate
     * @param y
     */

    public void setY(int y) {
        this.y = y;
    }
}



