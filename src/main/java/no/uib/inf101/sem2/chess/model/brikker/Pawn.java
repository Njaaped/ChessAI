package no.uib.inf101.sem2.chess.model.brikker;

import no.uib.inf101.sem2.chess.model.Brikke;
import no.uib.inf101.sem2.chess.ruter.Rute;
import no.uib.inf101.sem2.chess.board.Board;

public class Pawn extends Brikke {

    private String name;
    private String fen;
    

    public Pawn(boolean isWhite) {
        super(isWhite);
        this.name = isWhite ? "whitepawn" : "blackpawn";
        this.fen = isWhite ? "P" : "p";
    }

    @Override
    public String getFEN() {
        return this.fen;
    }


    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public boolean isValidMove(Board board, Rute start, Rute end) {
              
        int x = start.getX() - end.getX();
        int y = start.getY() - end.getY();

        if (!pathCheck(board, start, end, x, y)) {
            return false;
        }

        if (y == -1 && Math.abs(x) == 1) {
            if (this.isWhite()) {
                if (end.getBrikke() != null) {
                    if (end.getBrikke().isWhite() != this.isWhite()) {
                        return true;
                    }
                } else {
                    if (board.getEnPassantRute() != null) {
                        if (end.getX() == board.getEnPassantX() && end.getY() == board.getEnPassantY()) {
                            return true;
                        }
                    }
                }
            }
        } else if (y == 1 && Math.abs(x) == 1) {
            if (!this.isWhite()) {
                if (end.getBrikke() != null) {
                    if (end.getBrikke().isWhite() != this.isWhite()) {
                        return true;
                    }
                } else {
                    if (board.getEnPassantRute() != null) {
                        if (end.getX() == board.getEnPassantX() && end.getY() == board.getEnPassantY()) {
                            return true;
                        }
                    }
                }
            }
        }

        if (!this.isWhite() && (x == 0 && y == 2) && start.getY() == 6 && end.getBrikke() == null) {
            return true;
        } else if (this.isWhite() && (x == 0 && y == -2) && start.getY() == 1 && end.getBrikke() == null) {
            return true;
        }

        if (this.isWhite() && (x == 0 && y == -1) && end.getBrikke() == null) {
            return true;
        } else if (!this.isWhite() && (x == 0 && y == 1) && end.getBrikke() == null) {
            return true;
        }
            
        return false;
    }


    @Override
    public boolean pathCheck(Board board, Rute start, Rute end, int x, int y) {
        if (x == 0 && y == 2) {
            if (board.getRute(start.getX(), start.getY() - 1).getBrikke() != null) {
                return false;
            } else if (board.getRute(start.getX(), start.getY() - 2).getBrikke() != null) {
                return false;
            }
        } else if (x == 0 && y == -2) {
            if (board.getRute(start.getX(), start.getY() + 1).getBrikke() != null) {
                return false;
            } else if (board.getRute(start.getX(), start.getY() + 2).getBrikke() != null) {
                return false;
            }
        }
        return true;
    }
    
}
