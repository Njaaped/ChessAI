package no.uib.inf101.sem2.chess.model.brikker;

import no.uib.inf101.sem2.chess.model.Brikke;
import no.uib.inf101.sem2.chess.ruter.Rute;
import no.uib.inf101.sem2.chess.board.Board;

public class Queen extends Brikke {

    private String name;
    private String fen;

    public Queen(boolean isWhite) {
        super(isWhite);
        this.name = isWhite ? "whitequeen" : "blackqueen";
        this.fen = isWhite ? "Q" : "q";
    }

    @Override
    public String getFEN() {
        return this.fen;
    }
    

    @Override
    public boolean isValidMove(Board board, Rute start, Rute end) {
        if (end.getBrikke() != null) {
            if (end.getBrikke().isWhite() == this.isWhite()) {
                return false;
            }
        }

        int x = (end.getX() - start.getX());
        int y = (end.getY() - start.getY());

        if (x * y == 0 && x + y != 0) {
            if (!pathCheck(board, start, end, x, y)) {
                return false;
            }
            return true;
        }
        if (Math.abs(x) == Math.abs(y) && x != 0) {
            if (!pathCheck(board, start, end, x, y)) {
                return false;
            }
            return true;
        }
        return false;
    }

    @Override
    public String getName() {
        return this.name;
    }
    
    @Override
    public boolean pathCheck(Board board, Rute start, Rute end, int x, int y) {
        
        int rowStep = y == 0 ? 0 : y / Math.abs(y);
        int colStep = x == 0 ? 0 : x / Math.abs(x);

        int row = start.getY() + rowStep;
        int col = start.getX() + colStep;

        while (row != end.getY() || col != end.getX()) {
            if (board.getRute(col, row).getBrikke() != null) {
                return false;
            }
            row += rowStep;
            col += colStep;
        }
        return true;
    }
}
