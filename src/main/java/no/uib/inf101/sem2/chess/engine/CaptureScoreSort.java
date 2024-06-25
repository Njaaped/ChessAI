package no.uib.inf101.sem2.chess.engine;


import no.uib.inf101.sem2.chess.game.Move;
import no.uib.inf101.sem2.chess.board.Board;
import no.uib.inf101.sem2.chess.ruter.Rute;
import no.uib.inf101.sem2.chess.model.Brikke;
import no.uib.inf101.sem2.chess.model.brikker.Pawn;
import no.uib.inf101.sem2.chess.model.brikker.Knight;
import no.uib.inf101.sem2.chess.model.brikker.Bishop;
import no.uib.inf101.sem2.chess.model.brikker.Rook;
import no.uib.inf101.sem2.chess.model.brikker.Queen;
import no.uib.inf101.sem2.chess.model.brikker.King;

import java.util.ArrayList;
import java.util.List;


/**
 * 
 * capturescoreSort sorts the moves by the value the position
 * 
 * @author Njal.Pedersen
 * 
 */


public class CaptureScoreSort {
    private List<Move> moves;
    private Board board;
    private List<Integer> moveScores;

    private final int CAPTUREDPIECEMULTIPLIER = 10;

    public CaptureScoreSort (List<Move> moves, Board board) {
        this.board = board;
        this.moves = moves;
        this.moveScores = new ArrayList<Integer>();
    }

    /**
     * 
     * @return a list of Rutes that is attacked by a pawn
     */

    private List<Rute> attackedbyPawn() {
        List<Rute> attacked = new ArrayList<Rute>();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Rute rute = board.getRute(i, j);
                if (rute.getBrikke() instanceof Pawn) {
                    if (rute.getBrikke().isWhite()) {
                        if (i > 0 && j > 0) {
                            attacked.add(board.getRute(i - 1, j + 1));
                        }
                        if (i < 7 && j > 0) {
                            attacked.add(board.getRute(i + 1, j + 1));
                        }
                    } else {
                        if (i > 0 && j < 7) {
                            attacked.add(board.getRute(i - 1, j - 1));
                        }
                        if (i < 7 && j < 7) {
                            attacked.add(board.getRute(i + 1, j - 1));
                        }
                    }
                }
            }
        }
        return attacked;
    }

    /**
     * 
     * @param piece
     * @return the value of the piece
     */

    private int getPieceValue(Brikke piece) {
        if (piece instanceof Pawn) {
            return 100;
        } else if (piece instanceof Knight) {
            return 280;
        } else if (piece instanceof Bishop) {
            return 320;
        } else if (piece instanceof Rook) {
            return 500;
        } else if (piece instanceof Queen) {
            return 900;
        } else if (piece instanceof King) {
            return 6000;
        }
        return 0;

    }

    /**
     * 
     * @return a list of moves sorted by the value of the position
     */

    public List<Move> sortMoves() {
        List<Rute> attacked = attackedbyPawn();
        for (Move move : moves) {
            int score = 0;
            Brikke movePieceType = move.getPieceMoved();
            Brikke movePieceTarget = move.getPieceKilled();
            Rute startRute = move.getStart();


            if (movePieceTarget != null) {
                score += CAPTUREDPIECEMULTIPLIER * getPieceValue(movePieceTarget) - getPieceValue(movePieceType);
            }
            
            if (!(movePieceType instanceof Pawn)) {
                for (Rute rute : attacked) {
                    if (rute == startRute) {
                        score += 350;
                    }
                }
            }
            
            moveScores.add(score);

        }
        sort();
        return moves;
    }

    /**
     * 
     * sorting from highest to lowest score
     */

    private void sort() {
        for (int i = 0; i < moves.size(); i++) {
            for (int j = 0; j < moves.size() - 1; j++) {
                if (moveScores.get(j) < moveScores.get(j + 1)) {
                    int temp = moveScores.get(j);
                    moveScores.set(j, moveScores.get(j + 1));
                    moveScores.set(j + 1, temp);

                    Move tempMove = moves.get(j);
                    moves.set(j, moves.get(j + 1));
                    moves.set(j + 1, tempMove);
                }
            }
        }

    }

    
}

