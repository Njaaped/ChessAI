package no.uib.inf101.sem2.chess.model.brikker;

import no.uib.inf101.sem2.chess.model.Brikke;
import no.uib.inf101.sem2.chess.board.Board;
import no.uib.inf101.sem2.chess.ruter.Rute;

public class King extends Brikke {
    private String name;
    private String fen;
    
    public King(boolean isWhite) {
        super(isWhite);
        this.name = isWhite ? "whiteking" : "blackking";
        this.fen = isWhite ? "K" : "k";
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
        if (end.getBrikke() != null) {
            if (end.getBrikke().isWhite() == this.isWhite()) {
                return false;
            }
        }
        int x = Math.abs(start.getX() - end.getX());
        int y = Math.abs(start.getY() - end.getY());

        if (x + y == 1 || (x == 1 && y == 1)) {
            return true;
        }
        
        return isValidCastling(board, start, end);
    }


    private boolean isValidCastling(Board board, Rute start, Rute end) {

        
        if (!this.isWhite() && (!board.isCanCastleBlackKingSide() && !board.isCanCastleBlackQueenSide())) {
            return false;
        } else if (this.isWhite() && (!board.isCanCastleWhiteKingSide() && !board.isCanCastleWhiteQueenSide())) {
            return false;
        }

        
        if (start.getY() != end.getY()) {
            return false;
        }
        int y = 0;
        Rute rute = null;
        Brikke[] brikker = new Brikke[3];


        if (this.isWhite()) {
            y = 0;
        } else {
            y = 7;
        }

        if (end.getX() == 6 && ((board.isCanCastleWhiteKingSide() && this.isWhite()) || (!this.isWhite() && board.isCanCastleBlackKingSide()))) {
            rute = board.getRute(7, y);
            brikker[0] = board.getRute(5, y).getBrikke();
            brikker[1] = board.getRute(6, y).getBrikke();
        } else if (end.getX() == 2 && ((board.isCanCastleWhiteQueenSide() && this.isWhite()) || (!this.isWhite() && board.isCanCastleBlackQueenSide()))) {
            rute = board.getRute(0, y);
            brikker[0] = board.getRute(1, y).getBrikke();
            brikker[1] = board.getRute(2, y).getBrikke();
            brikker[2] = board.getRute(3, y).getBrikke();
        } else {
            return false;
        }
        
        for (Brikke brikke : brikker) {
            if (brikke != null) {
                return false;
            }
        }

        Brikke rook = rute.getBrikke();

        if (rook == null || !(rook instanceof Rook)) {
            return false;
        }

        
        return true;

    }

    @Override
    public boolean pathCheck(Board board, Rute start, Rute end, int x, int y) {
        return true;
    }
}
