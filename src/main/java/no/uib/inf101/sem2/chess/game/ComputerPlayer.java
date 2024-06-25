package no.uib.inf101.sem2.chess.game;

/**
 * Class that inherits from Player and represents a computer player.
 * 
 * @author Njal.Pedersen
 *
 */

public class ComputerPlayer extends Player {
    
    public ComputerPlayer(boolean whiteSide) {
        this.whiteSide = whiteSide;
        this.humanPlayer = false;
    }
}
