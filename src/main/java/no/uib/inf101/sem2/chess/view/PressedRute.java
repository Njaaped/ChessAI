package no.uib.inf101.sem2.chess.view;



/**
* Pressed Rute class
* a class that holds the x and y coordinates of the pressed rute
* @author Njal.Pedersen
*/


public class PressedRute {
    
    private int x;
    private int y;
    
    public PressedRute(int x, int y) {
        this.x = x;
        this.y = y;        
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
