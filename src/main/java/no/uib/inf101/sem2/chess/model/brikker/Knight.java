package no.uib.inf101.sem2.chess.model.brikker;

import no.uib.inf101.sem2.chess.model.Brikke;
import no.uib.inf101.sem2.chess.ruter.Rute;
import no.uib.inf101.sem2.chess.board.Board;

public class Knight extends Brikke {
    
        private String name;
        private String fen;
    
        public Knight(boolean isWhite) {
            super(isWhite);
            this.name = isWhite ? "whiteknight" : "blackknight";
            this.fen = isWhite ? "N" : "n";
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
            if (x * y == 2) {
                return true;
            }
            return false;
        }

        @Override
        public boolean pathCheck(Board board, Rute start, Rute end, int x, int y) {
            return true;
        }
    }
