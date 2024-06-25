package no.uib.inf101.sem2.chess.model;

import no.uib.inf101.sem2.chess.board.Board;
import no.uib.inf101.sem2.chess.ruter.Rute;

/**
 * An abstract class representing a chess piece. and it's common methods.
 * 
 * @author Njal.Pedersen
 *
 */

public abstract class Brikke {

    private boolean isWhite;
    private boolean killed = false;
    
    public Brikke(boolean isWhite) {
        this.isWhite = isWhite;
    }

    /**
     * @return true if the piece is white, false if it is black
     */

    public boolean isWhite() {
        return isWhite;
    }

    /**
     * sets the color of the piece
     * @param isWhite
     */

    public void setWhite(boolean isWhite) {
        this.isWhite = isWhite;
    }

    //kan kanskje fjerne disse metodene

    /**
     * @return true if the piece is killed, false if it is not
     */

    public boolean isKilled() {
        return this.killed;
    }

    /**
     * sets the killed status of the piece
     * @param killed
     */

    public void setKilled(boolean killed) {
        this.killed = killed;
    }

    /**
     * checks if the move is valid
     * @param board
     * @param start
     * @param end
     * @return true if the move is valid, false if it is not
     */

    public abstract boolean isValidMove(Board board, Rute start, Rute end);

    /**
     * @return the name of the piece
     */

    public abstract String getName();

    /**
     * checks the path of the pieces to see where it is valid to move
     * @param board
     * @param start
     * @param end
     * @return true if the castling move is valid, false if it is not
     */

    public abstract boolean pathCheck(Board board, Rute start, Rute end, int x, int y);


    /**
     * @return String representation of fen to piece.
     */

    public abstract String getFEN();

}
