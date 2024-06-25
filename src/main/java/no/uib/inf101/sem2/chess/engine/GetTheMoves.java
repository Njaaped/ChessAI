

package no.uib.inf101.sem2.chess.engine;

import no.uib.inf101.sem2.chess.game.Game;
import no.uib.inf101.sem2.chess.game.Player;
import no.uib.inf101.sem2.chess.ruter.Rute;
import no.uib.inf101.sem2.chess.model.Brikke;
import no.uib.inf101.sem2.chess.board.Board;
import no.uib.inf101.sem2.chess.game.Move;
import no.uib.inf101.sem2.chess.game.Moving;

import java.util.ArrayList;
import java.util.List;

/**
 * The class that gets the moves.
 * 
 * getLegalMoves() gets all the legal moves for a player and it's rute.
 * 
 * getAllMoves() gets all the moves for a player and it's rutes.
 * 
 * getAllCaptures() gets all the capture moves for a player and it's rutes.
 * 
 * @author Njal.Pedersen
 *
 */


public class GetTheMoves {

    private Game game;
    private Board board;
    private Moving moving;


    public GetTheMoves(Game game) {
        this.game = game;
        this.moving = game.getMoving();
    }

    /**
     * Gets all the legal moves for a player and it's rute.
     * @param player
     * @param rute
     * @return List<Rute> of legal moves
     */

    public List<Rute> getLegalMoves(Player player, Rute rute) {
        board = game.getBoard();
        List<Rute> legalMoves = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Rute rute2 = board.getRute(i, j);
                Brikke piece = rute.getBrikke();
                if (piece != null && (piece.isWhite() == player.isWhiteSide())) {
                    if (piece.isValidMove(board, rute, rute2)) {
                        Move move3 = new Move(player, rute, rute2);
                        if (moving.makeMove(move3, player)) {
                            legalMoves.add(rute2);
                            moving.undoMove(move3, player);
                        }
                        moving.undoMove(move3, player);    
                    }
                }
            }
        }
        return legalMoves; 
    }

    /**
     * Gets all the moves for a player and it's rutes.
     * @param player
     * @return List<Move> of all moves
     */

    public List<Move> getAllMoves(Player player) {
        board = game.getBoard();
        List<Move> allMoves = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Rute rute = board.getRute(i, j);
                if (rute.getBrikke() != null && rute.getBrikke().isWhite() == player.isWhiteSide()) {
                    List<Rute> possibleMoves = this.getLegalMoves(player, rute);
                    for (Rute rute2 : possibleMoves) {
                        Move move = new Move(player, rute, rute2);
                        moving.makeMove(move, player);       
                        allMoves.add(move);
                        moving.undoMove(move, player);
                    }
                }
            }
        }
        return allMoves;
    }

    /**
     * Gets all the captures for a player and it's rutes.
     * @param player
     * @return List<Move> of all captures
     */

    public List<Move> getAllCaptureMoves(Player player) {
        board = game.getBoard();
        List<Move> allCaptureMoves = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Rute rute = board.getRute(i, j);
                if (rute.getBrikke() != null && rute.getBrikke().isWhite() == player.isWhiteSide()) {
                    List<Rute> possibleMoves = this.getLegalMoves(player, rute);
                    for (Rute rute2 : possibleMoves) {
                        Move move = new Move(player, rute, rute2);
                        moving.makeMove(move, player);
                        if (move.getPieceKilled() != null) {
                            allCaptureMoves.add(move);
                        }
                        moving.undoMove(move, player);
                    }
                }
            }
        }
        return allCaptureMoves;
    }
    
}
