package no.uib.inf101.sem2.chess.view;


import no.uib.inf101.sem2.chess.model.Brikke;

/**
* FollowingBrikke class
* a class that holds the x and y coordinates of the piece that is clicked.
* if no piece is clicked, brikke is null
* @author Njal.Pedersen
*/

public class FollowingBrikke {

    private int x;
    private int y;
    private Brikke brikke;

    public FollowingBrikke(int x, int y, Brikke brikke) {
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
     * @return the piece / brikke
     */

    public Brikke getBrikke() {
        return brikke;
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

    /**
     * sets the piece / brikke
     * if no piece is clicked, set brikke / piece to null
     * @param brikke
     */

    public void setBrikke(Brikke brikke) {
        this.brikke = brikke;
    }
    
}
