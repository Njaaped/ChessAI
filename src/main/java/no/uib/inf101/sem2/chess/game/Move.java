package no.uib.inf101.sem2.chess.game;

import no.uib.inf101.sem2.chess.model.Brikke;
import no.uib.inf101.sem2.chess.ruter.Rute;


/**
 * A class representing a move in the game.
 * 
 * @author Njal.Pedersen
 *
 */

public class Move {
    // player who made the move
    private Player player;
    // the start and end rute of the move
    private Rute start;
    private Rute end;

    // the piece that was moved and the piece that was killed
    private Brikke pieceMoved;
    private Brikke pieceKilled;

    // the rook that was moved in a castling move
    private Brikke rookCastled;

    // booleans to check if the move is a castling move, en passant move, pawn promotion or first pawn move
    private boolean castlingMove = false;
    private boolean enPassantMove = false;
    private boolean pawnPromotion = false;
    private boolean firstPawnMove = false;

    // booleans to check if the move is destroying the king side or queen side castle or both
    private boolean bothCastleFalse = false;
    private boolean castleKingSide = false;
    private boolean castleQueenSide = false;

  
    public Move(Player player, Rute start, Rute end) {
        this.player = player;
        this.start = start;
        this.end = end;
        this.pieceMoved = start.getBrikke();
    }

    /**
     * @return true if the move is a castling move, false if it is not
     */
  
    public boolean isCastlingMove() {
        return this.castlingMove;
    }

    /**
     * @return the Rook that was castled
     */

    public Brikke getRookCastled() {
        return rookCastled;
    }

    /**
     * sets the rook that was castled
     * @param rookCastled
     */

    public void setRookCastled(Brikke rookCastled) {
        this.rookCastled = rookCastled;
    }

    /**
     * @return true if en passant move, else false
     */

    public boolean isEnPassantMove() {
        return this.enPassantMove;
    }

    /**
     * @return true if pawn promotion, else false
     */

    public boolean isPawnPromotion() {
        return this.pawnPromotion;
    }

    /**
     * @return true if first pawn move, and the pawn went two steps, else false
     */

    public boolean isFirstPawnMove() {
        return this.firstPawnMove;
    }

    /**
     * @param setCastling if the move is a castling move or destroyed castling oppertunity
     */
  
    public void setCastlingMove(boolean castlingMove) {
        this.castlingMove = castlingMove;
    }

    /**
     * sets true if en passant move
     * @param enPassantMove
     */

    public void setEnPassantMove(boolean enPassantMove) {
        this.enPassantMove = enPassantMove;
    }

    /**
     * sets true if pawn promotion
     * @param pawnPromotion
     */

    public void setPawnPromotion(boolean pawnPromotion) {
        this.pawnPromotion = pawnPromotion;
    }

    /**
     * sets true if first pawn move
     * @param firstPawnMove
     */

    public void setFirstPawnMove(boolean firstPawnMove) {
        this.firstPawnMove = firstPawnMove;
    }

    /**
     * 
     * @return true if the move is destroying the both side castle, false if it is not
     */
     
    public boolean isBothCastleFalse() {
        return bothCastleFalse;
    }

    /**
     * sets true if the move is destroying the both side castle
     * @param bothCastleFalse
     */

    public void setBothCastleFalse(boolean bothCastleFalse) {
        this.bothCastleFalse = bothCastleFalse;
    }

    /**
     * 
     * @return true if the move is destroying the king side castle, false if it is not
     */

    public boolean isCastleKingSide() {
        return castleKingSide;
    }

    /**
     * sets true if the move is destroying the king side castle
     * @param castleKingSide
     */

    public void setCastleKingSide(boolean castleKingSide) {
        this.castleKingSide = castleKingSide;
    }

    /**
     * 
     * @return true if the move is destroying the queen side castle, false if it is not
     */

    public boolean isCastleQueenSide() {
        return castleQueenSide;
    }

    /**
     * sets true if the move is destroying the queen side castle
     * @param castleQueenSide
     */

    public void setCastleQueenSide(boolean castleQueenSide) {
        this.castleQueenSide = castleQueenSide;
    }

    /**
     * @return the player who made the move
     */

    public Player getPlayer() {
        return player;
    }

    /**
     * @return the start rute of the move
     */

    public Rute getStart() {
        return start;
    }

    /**
     * @return the end rute of the move
     */

    public Rute getEnd() {
        return end;
    }

    /**
     * @return the piece that was moved
     */

    public Brikke getPieceMoved() {
        return pieceMoved;
    }

    /**
     * @return the piece that was killed
     */

    public Brikke getPieceKilled() {
        return pieceKilled;
    }

    /**
     * sets the piece that was killed
     * @param pieceKilled
     */

    public void setPieceKilled(Brikke pieceKilled) {
        this.pieceKilled = pieceKilled;
    }


    
}
