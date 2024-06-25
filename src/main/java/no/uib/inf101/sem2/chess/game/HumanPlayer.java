package no.uib.inf101.sem2.chess.game;

/**
 * Class that inherits from Player and represents a human player.
 * 
 * @author Njal.Pedersen
 *
 */

public class HumanPlayer extends Player {
    
    public HumanPlayer(boolean whiteSide) {
        this.whiteSide = whiteSide;
        this.humanPlayer = true;
    }
}
