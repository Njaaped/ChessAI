package no.uib.inf101.sem2.chess.board;

import no.uib.inf101.sem2.chess.ruter.Rute;
import no.uib.inf101.sem2.chess.model.brikker.*;

/**
 * A class representing a chess board.
 * 
 * @author Njal.Pedersen
 *
 */

public class Board {
    Rute[][] ruter = new Rute[8][8];
    private String fen;
    private boolean canCastleWhiteKingSide;
    private boolean canCastleWhiteQueenSide;
    private boolean canCastleBlackKingSide;
    private boolean canCastleBlackQueenSide;

    private Rute enPassantRute;

    public Board() {
        this.resetBoard();
    }

    /**
     * 
     * @return x value of the en passant rute
     */

    public int getEnPassantX() {
        return enPassantRute.getX();
    }

    /**
     * 
     * @return y value of the en passant rute
     */

    public int getEnPassantY() {
        return enPassantRute.getY();
    }

    /**
     * 
     * @return the en passant rute
     */

    public Rute getEnPassantRute() {
        return enPassantRute;
    }

    /**
     * sets the en passant rute
     * @param enPassantRute
     */

    public void setEnPassantRute(Rute enPassantRute) {
        this.enPassantRute = enPassantRute;
    }

    /**
     * gets the rute at the given x and y
     * @param x
     * @param y
     * @return the rute at the given x and y
     */
    public Rute getRute(int x, int y) {
        if (x < 0 || x > 7 || y < 0 || y > 7) {
            throw new IllegalArgumentException("x and y must be between 0 and 7");
        }
        return ruter[x][y];
    }

    /**
     * 
     * @return all the ruter on the board
     */

    public Rute[][] getRuter() {
        return ruter;
    }

    /**
     * 
     * @param whiteSide
     * @return the king rute of the given side
     */

    public Rute getKingRute(boolean whiteSide) {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (ruter[i][j].getBrikke() instanceof King && ruter[i][j].getBrikke().isWhite() == whiteSide) {
                    return ruter[i][j];
                }
            }
        }
        return null;
    }

    /**
     * sets the rute at the given x and y to the given rute
     * @param x
     * @param y
     * @param rute
     */

    public void setRute(int x, int y, Rute rute) {
        if (x < 0 || x > 7 || y < 0 || y > 7) {
            throw new IllegalArgumentException("x and y must be between 0 and 7");
        }
        ruter[x][y] = rute;
    }

    /**
     * creates the starting position of the board
     */

    public void resetBoard() {
        ruter[0][0] = new Rute(0, 0, new Rook(true));
        ruter[1][0] = new Rute(1, 0, new Knight(true));
        ruter[2][0] = new Rute(2, 0, new Bishop(true));
        ruter[3][0] = new Rute(3, 0, new Queen(true));
        ruter[4][0] = new Rute(4, 0, new King(true));
        ruter[5][0] = new Rute(5, 0, new Bishop(true));
        ruter[6][0] = new Rute(6, 0, new Knight(true));
        ruter[7][0] = new Rute(7, 0, new Rook(true));

        for (int i = 0; i < 8; i++) {
            ruter[i][1] = new Rute(i, 1, new Pawn(true));
        }

        for (int i = 0; i < 8; i++) {
            for (int j = 2; j < 6; j++) {
                ruter[i][j] = new Rute(i, j, null);
            }
        }

        for (int i = 0; i < 8; i++) {
            ruter[i][6] = new Rute(i, 6, new Pawn(false));
        }

        ruter[0][7] = new Rute(0, 7, new Rook(false));
        ruter[1][7] = new Rute(1, 7, new Knight(false));
        ruter[2][7] = new Rute(2, 7, new Bishop(false));
        ruter[3][7] = new Rute(3, 7, new Queen(false));
        ruter[4][7] = new Rute(4, 7, new King(false));
        ruter[5][7] = new Rute(5, 7, new Bishop(false));
        ruter[6][7] = new Rute(6, 7, new Knight(false));
        ruter[7][7] = new Rute(7, 7, new Rook(false));

        this.canCastleBlackKingSide = true;
        this.canCastleBlackQueenSide = true;
        this.canCastleWhiteKingSide = true;
        this.canCastleWhiteQueenSide = true;

    }

    /**
     * 
     * @return string representation of the board
     */

    public void printBoard() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (ruter[j][i].getBrikke() == null) {
                    System.out.print(" ");
                } else {
                    System.out.print(ruter[j][i].getBrikke().getName());
                }
            }
            System.out.println();
        }
    }

    /**
     * 
     * @return the FEN representation of the board
     */

    public String updateFEN() {
        fen = "";
        for (int i = 0; i < 8; i++) {
            int empty = 0;
            for (int j = 0; j < 8; j++) {
                if (ruter[j][i].getBrikke() == null) {
                    empty++;
                } else {
                    if (empty > 0) {
                        fen += empty;
                        empty = 0;
                    }
                    fen += ruter[j][i].getBrikke().getFEN();
                }
            }
            if (empty > 0) {
                fen += empty;
            }
            if (i < 7) {
                fen += "/";
            }
        }

        if (canCastleWhiteKingSide) {
            fen += "K";
        }

        if (canCastleWhiteQueenSide) {
            fen += "Q";
        }

        if (canCastleBlackKingSide) {
            fen += "k";
        }

        if (canCastleBlackQueenSide) {
            fen += "q";
        }

        return fen;
    }

    /**
     * 
     * @return the FEN representation of the board
     */

    public String getFEN() {
        return fen;
    }

    /**
     * true if the given side can castle kingside
     * @param canCastleWhiteKingSide
     */

    public void setCanCastleWhiteKingSide(boolean canCastleWhiteKingSide) {
        this.canCastleWhiteKingSide = canCastleWhiteKingSide;
    }

    /**
     * 
     * @return true if the given side can castle kingside
     */

    public boolean isCanCastleWhiteKingSide() {
        return canCastleWhiteKingSide;
    }

    /**
     * true if the given side can castle queenside
     * @param canCastleWhiteQueenSide
     */

    public void setCanCastleWhiteQueenSide(boolean canCastleWhiteQueenSide) {
        this.canCastleWhiteQueenSide = canCastleWhiteQueenSide;
    }

    /**
     * 
     * @return true if the given side can castle queenside
     */

    public boolean isCanCastleWhiteQueenSide() {
        return canCastleWhiteQueenSide;
    }

    /**
     * true if the given side can castle kingside
     * @param canCastleBlackKingSide
     */

    public void setCanCastleBlackKingSide(boolean canCastleBlackKingSide) {
        this.canCastleBlackKingSide = canCastleBlackKingSide;
    }

    /**
     * 
     * @return true if the given side can castle kingside
     */

    public boolean isCanCastleBlackKingSide() {
        return canCastleBlackKingSide;
    }

    /**
     * true if the given side can castle queenside
     * @param canCastleBlackQueenSide
     */

    public void setCanCastleBlackQueenSide(boolean canCastleBlackQueenSide) {
        this.canCastleBlackQueenSide = canCastleBlackQueenSide;
    }

    /**
     * 
     * @return true if the given side can castle queenside
     */

    public boolean isCanCastleBlackQueenSide() {
        return canCastleBlackQueenSide;
    }





}
